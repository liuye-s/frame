
$(function () {

    // datatables 汉化
    $.fn.dataTable.defaults.oLanguage = {
        "sProcessing": "处理中...",
        "sLengthMenu": "显示 _MENU_ 项",
        "sZeroRecords": "没有匹配结果",
        "sInfo": "显示第 _START_ 至 _END_ 项，共 _TOTAL_ 项",
        "sInfoEmpty": "显示第 0 至 0 项，共 0 项",
        "sInfoFiltered": "(由 _MAX_ 项过滤)",
        "sInfoPostFix": "",
        "sSearch": "搜索：",
        "sUrl": "",
        "sEmptyTable": "表中数据为空",
        "sLoadingRecords": "载入中...",
        "sInfoThousands": ",",
        "sThousands": ",",
        "oPaginate": {
            "sFirst": "首页",
            "sPrevious": "上页",
            "sNext": "下页",
            "sLast": "末页"
        },
        "oAria": {
            "sSortAscending": ": 以升序排列此列",
            "sSortDescending": ": 以降序排列此列"
        }
    };

});

function del(id, url){
    layer.confirm('确认要删除吗？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        $.ajax({
            type:'post',
            data:{
                "ids":id
            },
            dataType:'json',//返回的数据类型
            // async:'false',
            url: url,
            success: function (data) {
                if(data.success){
                    myTable.draw(false);
                    layer.msg((data.message) ? data.message : "删除成功");
                }else{
                    layer.msg("删除失败" + ((data.message) ? "：" + data.message : ""));
                }
            },
            error:function () {
                //alert(data.responseJSON.content);
                layer.msg("删除失败，请联系管理员。");
            }

        })
    });

}

function delBatch(checkboxName, url){
    var chk_value = [];//定义一个数组
    $('input[name="' + checkboxName + '"]:checked').each(function () {//遍历每一个名字为nodes的复选框，其中选中的执行函数
        chk_value.push($(this).val());//将选中的值添加到数组chk_value中
    });


    if (chk_value.length<=0) {
        layer.msg("请选择要删除的记录");
        return false;
    }

    layer.confirm('确认要删除吗？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        $.ajax({
            traditional: true,
            type:'post',
            data:{
                "ids":chk_value
            },
            dataType:'json',//返回的数据类型
            // async:'false',
            url:url,
            success: function (data) {
                if(data.success){
                    myTable.draw(false);
                    layer.msg((data.message) ? data.message : "删除成功");
                }else{
                    layer.msg("删除失败" + ((data.message) ? "：" + data.message : ""));
                }
            },
            error:function () {
                layer.msg("删除失败，请联系管理员。");
            }

        });
    });
}

function info(title, url) {
    openTheLayerWindow(title,url);
}

function edit(title, url, saveUrl) {




    var index = layer.open({
        type: 2,
        title: title,
        // title: false,
        closeBtn: 1,
        anim: 0,
        maxmin: true, //开启最大化最小化按钮
        shadeClose: true,
        area: ['890px', '600px'],
        shade: 0.3,
        moveType: 0, //拖拽风格，0是默认，1是传统拖动
        content: [url], //iframe的url，no代表不显示滚动条
        btnAlign: 'c',
        btn: ['保存','关闭'],
        btn1: function(index1, layero){
            //alert("ok");
            var iframes = $(layero).find("iframe")[0].contentWindow;

            // 方式1获取html元素的两种方式
            //var $parent = $(iframes.document);
            // var form =  $parent.find("#inputForm")[0];

            //方式2
            var form = iframes.document.getElementById("inputForm");

            var options = {
                errorElement: 'div',
                errorClass: 'help-block',
                focusInvalid: false,
                ignore: "",
                messages: {
                    required: "这是必填字段",
                    remote: "请修正此字段",
                    email: "请输入有效的电子邮件地址",
                    url: "请输入有效的网址",
                    date: "请输入有效的日期",
                    dateISO: "请输入有效的日期 (YYYY-MM-DD)",
                    number: "请输入有效的数字",
                    digits: "只能输入数字",
                    creditcard: "请输入有效的信用卡号码",
                    equalTo: "你的输入不相同",
                    extension: "请输入有效的后缀",
                    maxlength: $.validator.format("最多可以输入 {0} 个字符"),
                    minlength: $.validator.format("最少要输入 {0} 个字符"),
                    rangelength: $.validator.format("请输入长度在 {0} 到 {1} 之间的字符串"),
                    range: $.validator.format("请输入范围在 {0} 到 {1} 之间的数值"),
                    max: $.validator.format("请输入不大于 {0} 的数值"),
                    min: $.validator.format("请输入不小于 {0} 的数值")
                },

                highlight: function (e) {
                    $(e).closest('.form-group').removeClass('has-info').addClass('has-error');
                },

                success: function (e) {
                    $(e).closest('.form-group').removeClass('has-error');//.addClass('has-info');
                    $(e).remove();
                },

                errorPlacement: function (error, element) {
                    if ($(element).next("div").hasClass("tooltip")) {
                        $(element).attr("data-original-title", $(error).text()).tooltip("show");
                    } else {
                        $(element).attr("title", $(error).text()).tooltip("show");
                    }

                },

                submitHandler: function (form) {
                    /*//保存成功后重新加载
                    $.ajax({
                        url: saveUrl,
                        type: "post",
                        data: $(form).serialize(),//序列化前需要把html元素转成jquery元素，再用jquery元素的序列化方法提交。。$(dom元素)。。
                        dataType: 'json',//返回的数据类型
                        success: function (result) {
                            if (result.success == true) {
                                myTable.draw(false);
                                layer.msg((result.message) ? result.message : "保存成功");
                                layer.close(index1);
                            } else {
                                layer.msg("保存失败" + +((result.message) ? "：" + result.message : ""));
                            }
                        },
                        error: function () {
                            layer.msg("保存失败，请联系管理员。");
                        }
                    });*/
                },
                invalidHandler: function (form) {
                }
            };
            if (iframes.formValidatorOptions) {
                options = $.extend({}, options, iframes.formValidatorOptions);
            }

            var validated = $(form).validate(options).form();

            if (validated) {

                $(layero).find('.layui-layer-btn0').text("保存中...");
                $(layero).find('.layui-layer-btn0').css('pointer-events','none');

                if (typeof(iframes.saveEditConfirmFunction) == "function") {

                    var confirmInfo = iframes.saveEditConfirmFunction(index1, layero);
                    if (confirmInfo) {
                        layer.confirm(confirmInfo, function () {

                            //保存成功后重新加载
                            $.ajax({
                                url: saveUrl,
                                type: "post",
                                data: $(form).serialize(),//序列化前需要把html元素转成jquery元素，再用jquery元素的序列化方法提交。。$(dom元素)。。
                                dataType: 'json',//返回的数据类型
                                success: function (result) {
                                    if (result.success == true) {
                                        myTable.draw(false);
                                        layer.msg((result.message) ? result.message : "保存成功");
                                        layer.close(index1);
                                    } else {
                                        $(layero).find('.layui-layer-btn0').text("保存");
                                        $(layero).find('.layui-layer-btn0').css('pointer-events','auto');
                                        layer.msg("保存失败" + ((result.message) ? "：" + result.message : ""));
                                    }
                                },
                                error: function () {
                                    $(layero).find('.layui-layer-btn0').text("保存");
                                    $(layero).find('.layui-layer-btn0').css('pointer-events','auto');
                                    layer.msg("保存失败，请联系管理员。");
                                }
                            });

                        });
                    } else {

                        //保存成功后重新加载
                        $.ajax({
                            url: saveUrl,
                            type: "post",
                            data: $(form).serialize(),//序列化前需要把html元素转成jquery元素，再用jquery元素的序列化方法提交。。$(dom元素)。。
                            dataType: 'json',//返回的数据类型
                            success: function (result) {
                                if (result.success == true) {
                                    myTable.draw(false);
                                    layer.msg((result.message) ? result.message : "保存成功");
                                    layer.close(index1);
                                } else {
                                    $(layero).find('.layui-layer-btn0').text("保存");
                                    $(layero).find('.layui-layer-btn0').css('pointer-events','auto');
                                    layer.msg("保存失败" + ((result.message) ? "：" + result.message : ""));
                                }
                            },
                            error: function () {
                                $(layero).find('.layui-layer-btn0').text("保存");
                                $(layero).find('.layui-layer-btn0').css('pointer-events','auto');
                                layer.msg("保存失败，请联系管理员。");
                            }
                        });

                    }

                } else if (typeof(saveEditConfirmFunction) != "function") {

                    //保存成功后重新加载
                    $.ajax({
                        url: saveUrl,
                        type: "post",
                        data: $(form).serialize(),//序列化前需要把html元素转成jquery元素，再用jquery元素的序列化方法提交。。$(dom元素)。。
                        dataType: 'json',//返回的数据类型
                        success: function (result) {
                            if (result.success == true) {
                                myTable.draw(false);
                                layer.msg((result.message) ? result.message : "保存成功");
                                layer.close(index1);
                            } else {
                                $(layero).find('.layui-layer-btn0').text("保存");
                                $(layero).find('.layui-layer-btn0').css('pointer-events','auto');
                                layer.msg("保存失败" + ((result.message) ? "：" + result.message : ""));
                            }
                        },
                        error: function () {
                            $(layero).find('.layui-layer-btn0').text("保存");
                            $(layero).find('.layui-layer-btn0').css('pointer-events','auto');
                            layer.msg("保存失败，请联系管理员。");
                        }
                    });

                }

            }

        }, success: function (layero) {
        }, cancel: function (index1, layero) {
            layer.close(index1);
        }
    });
    $(".layui-layer-content").find("iframe").css("padding-bottom","20px");

    layer.full(index);

}

