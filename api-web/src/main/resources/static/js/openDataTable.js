
//闭包限定命名空间
(function ($) {


    $.fn.extend({
        "openDataTable": function (options) {
            var opts = $.extend({}, defaluts, options); //使用jQuery.extend 覆盖插件默认参数

            // return this.each(function () {  //这里的this 就是 jQuery对象
            //     //遍历所有的要高亮的dom,当调用 openDataTable()插件的是一个集合的时候。
            //     var $this = $(this); //获取当前dom 的 jQuery对象，这里的this是当前循环的dom
            //
            // });


            var thiz = this;
            var myTable;

            //select/deselect all rows according to table header checkbox
            thiz.find('thead > tr > th input[type=checkbox], #dynamic-table_wrapper input[type=checkbox]').eq(0).on('click', function(){
                var th_checked = this.checked;//checkbox inside "TH" table header

                thiz.find('tbody > tr').each(function(){
                    var row = this;
                    if(th_checked) myTable.row(row).select();
                    else  myTable.row(row).deselect();
                });
            });

            // if (myTable) {
            //     //myTable.destroy();
            //     thiz.empty();
            // }

            // var smallWinH = $(window).height();
            // var searchLabelH = $(".search-label").height();
            // var dataStyleH =  $(".data-style").length >0 ? $(".data-style").height() + parseInt($(".data-style").css("paddingTop")) : 0;
            // var dataTablesInfoH =30;
            // var footerH = 56;
            // var scrollH = smallWinH -searchLabelH -dataStyleH -dataTablesInfoH -footerH - 70;
            //
            // opts.scrollY = scrollH;
            var _tableH = $(window).height() - thiz.offset().top - 42-42 -20;  //42为表格头部和尾部，20位差值率
            opts.scrollY = _tableH+"px";

            opts.scroller.loadingIndicator = true;

            myTable =
                thiz.on('error.dt', function ( e, settings, techNote, message ) {
                    if (console)
                        console.log( '表格数据装载出错：', message );
                }).DataTable(opts);



            myTable.on( 'select', function ( e, dt, type, index ) {
                if ( type === 'row' ) {
                    $( myTable.row( index ).node() ).find('input:checkbox').prop('checked', true);
                    childclick();

                }
            } );
            myTable.on( 'deselect', function ( e, dt, type, index ) {
                if ( type === 'row' ) {
                    $( myTable.row( index ).node() ).find('input:checkbox').prop('checked', false);
                    childclick();
                }
            } );

            //table checkboxes
            thiz.find('th input[type=checkbox], td input[type=checkbox]').prop('checked', false);

            //select/deselect a row when the checkbox is checked/unchecked
            thiz.on('click', 'td input[type=checkbox]' , function(){
                var row = $(this).closest('tr').get(0);
                if(this.checked) myTable.row(row).deselect();
                else myTable.row(row).select();
            });

            return myTable;
        }
    });

    //默认参数
    var defaluts = {

        formObject: "listForm",
        urlNamespace: "",
        queryPageMethodName: "queryPage",
        forTab: false,

        "fnDrawCallback": function(table) {
            $("#dynamic-table_paginate").prepend("  <div class='toPage'>到第 <input  class=' input-sm changePage' id='changePage' type='text'> 页 </div>");
            var oTable =  this.dataTable();

            $('#changePage').on("keyup",function(e) {
                var _value = $("#changePage").val();
                if($("#changePage").val() && $("#changePage").val() > 0) {
                    var redirectpage = $("#changePage").val() - 1;
                } else {
                    var redirectpage = 0;
                }
                oTable.fnPageChange(redirectpage);
                $("#changePage").val(_value)
            });

            if (table.oInit.forTab)
                $.acetab.init();
        },

        //processing: true,
        ordering: false,
        searching: false,
        serverSide: true,
        // scrollY: scrollH,
        scroller: {
            loadingIndicator: true
        },
        scrollCollapse:true,
        scrollY: scrollH,
        lengthMenu: [50, 100, 200, 500],
        //iDisplayLength: 100,
        /*lengthMenu: [
            [ 10, 25, 50, 100, 200, 500, 1000, -1 ],
            [ '10', '25', '50', '100', '200', '500', '1000', '全部' ]
        ],*/
        language:{
            "sProcessing": "处理中...",
            "sLengthMenu": "显示 _MENU_ 项结果",
            "sZeroRecords": "没有匹配结果",
            "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
            "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
            "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
            "sInfoPostFix": "",
            "sSearch": "搜索:",
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
        },
        deferRender: true,
        //deferLoading: 100,
        ajax: function (data, callback, settings) {
            for (var i = 0; i < data.columns.length; i++) {
                column = data.columns[i];
                column.searchRegex = column.search.regex;
                column.searchValue = column.search.value;
                delete(column.search);
            }
            $.ajax({
                url: settings.oInit.urlNamespace + "/" + settings.oInit.queryPageMethodName,
                type: "post",
                data: $.param(data) + "&" + $("#" + settings.oInit.formObject).serialize(),
                success: function (result) {
                    if (result.success==true) {
                        var _page = {};
                        _page.draw=result.data.draw;
                        _page.recordsTotal=result.data.page.total;
                        _page.recordsFiltered=result.data.page.total;
                        _page.data = result.data.page.records;
                        callback(_page);

                        //    将一页显示多少条，放到下面来
                        $("#dynamic-table_length").addClass("fl");
                        $("#dynamic-table_info").addClass("fl").before($("#dynamic-table_length"));

                    } else {
                        layer.msg("获取数据：" + result.message);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    layer.msg("获取数据失败，请联系管理员。");
                }
            });
        },
        select: {
            style: 'multi',
            info: false
        }
    };

    // 公共方法

    /**
     * 生成缺省选择列复选框HTML
     * @param key
     * @param row
     * @param type
     * @param set
     * @param meta
     * @returns {string}
     */
    $.fn.openDataTable.getDefaultCheckboxHtml = function (key, row, type, set, meta) {
        return "<div class=\"center\">\n" +
            "                                    <label class=\"pos-rel\">\n" +
            "                                        <input type=\"checkbox\"  value='" + row[key] + "' name='" + key + "' class=\"ace checknum\" />\n" +
            "                                        <span class=\"lbl\"></span>\n" +
            "                                    </label>\n" +
            "                                </div>";
    };

    /**
     * 生成缺省操作列按钮HTML
     * @param data
     * @param urlNamespace
     * @param noShow
     * @param noDelete
     * @param noEdit
     * @returns {string}
     */
    $.fn.openDataTable.getDefaultOperationHtml = function (data, urlNamespace, noShow, noDelete, noEdit, showMethodName, inputMethodName, saveMethodName, deleteMethodName) {
        noShow = noShow || false;
        noDelete = noDelete || false;
        noEdit = noEdit || false;
        showMethodName = showMethodName || "show";
        inputMethodName = inputMethodName || "input";
        saveMethodName = saveMethodName || "save";
        deleteMethodName = deleteMethodName || "delete";

        var html1 = " <div class=\"hidden-sm hidden-xs action-buttons\">\n";
        if (!noShow) {
            html1 +=
                "                                        <a class=\"blue\" href=\"javascript:void(0);\" onclick='info(\"查看\",\"" + urlNamespace + "/" + showMethodName + "?id="+data+"\")'>\n" +
                "                                            <i class=\"ace-icon fa fa-search-plus bigger-130\"></i>\n" +
                "                                        </a>\n";
        }
        if (!noEdit) {
            html1 +=
                "                                        <a class=\"green\" href=\"javascript:void(0);\" onclick='edit(\"修改\",\"" + urlNamespace + "/" + inputMethodName + "?id="+data+"\",\"" + urlNamespace + "/" + saveMethodName + "\")'>\n" +
                "                                            <i class=\"ace-icon fa fa-pencil bigger-130\"></i>\n" +
                "                                        </a>\n";
        }
        if (!noDelete) {
            html1 +=
                "                                        <a class=\"red\" href=\"javascript:void(0);\" onclick='del(\"" + data + "\",\"" + urlNamespace + "/" + deleteMethodName + "\",\""+data+"\")'>\n" +
                "                                            <i class=\"ace-icon fa fa-trash-o bigger-130\"></i>\n" +
                "                                        </a>\n";
        }
        html1 += "                                    </div>";

        var html2 = "<div class=\"hidden-md hidden-lg\">\n" +
            "                                        <div class=\"inline pos-rel\">\n" +
            "                                            <button class=\"btn btn-minier btn-yellow dropdown-toggle\" data-toggle=\"dropdown\" data-position=\"auto\">\n" +
            "                                                <i class=\"ace-icon fa fa-caret-down icon-only bigger-120\"></i>\n" +
            "                                            </button>\n" +
            "\n" +
            "                                            <ul class=\"dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close\">\n";
        if (!noShow) {
            html2 +=
                "                                                <li  onclick='info(\"查看\",\"" + urlNamespace + "/" + showMethodName + "?id="+data+"\")'>\n" +
                "                                                    <a href=\"javascript:void(0);\" class=\"tooltip-info\" data-rel=\"tooltip\" title=\"View\">\n" +
                "<span class=\"blue\">\n" +
                "<i class=\"ace-icon fa fa-search-plus bigger-120\"></i>\n" +
                "</span>\n" +
                "                                                    </a>\n" +
                "                                                </li>\n";
        }
        if (!noEdit) {
            html2 +=
                "                                                <li onclick='edit(\"修改\",\"" + urlNamespace + "/" + inputMethodName + "?id="+data+"\",\"" + urlNamespace + "/" + saveMethodName + "\")'>\n" +
                "                                                    <a href=\"javascript:void(0);\" class=\"tooltip-success\" data-rel=\"tooltip\" title=\"Edit\">\n" +
                "<span class=\"green\">\n" +
                "<i class=\"ace-icon fa fa-pencil-square-o bigger-120\"></i>\n" +
                "</span>\n" +
                "                                                    </a>\n" +
                "                                                </li>\n";
        }
        if (!noDelete) {
            html2 +=
                "                                                <li  onclick='del(\"" + data + "\",\"" + urlNamespace + "/" + deleteMethodName + "\")'>\n" +
                "                                                    <a href=\"javascript:void(0);\" class=\"tooltip-error\" data-rel=\"tooltip\" title=\"Delete\">\n" +
                "<span class=\"red\">\n" +
                "<i class=\"ace-icon fa fa-trash-o bigger-120\"></i>\n" +
                "</span>\n" +
                "                                                    </a>\n" +
                "                                                </li>\n";
        }
        html2 +=
            "                                            </ul>\n" +
            "                                        </div>\n" +
            "                                    </div>";

        return html1+ html2;
    };

    // 私有方法
    /**
     * checkbox 检查是否全选
     */
    function childclick(){
        if(!$(".checknum").checked){
            $("#checkAll").prop("checked",false); // 子复选框某个不选择，全选也被取消
        }
        var choicelength=$("input[type='checkbox'][class='ace checknum']").length;
        var choiceselect=$("input[type='checkbox'][class='ace checknum']:checked").length;

        if(choicelength==choiceselect){
            $("#checkAll").prop("checked",true);   // 子复选框全部部被选择，全选也被选择；1.对于HTML元素我们自己自定义的DOM属性，在处理时，使用attr方法；2.对于HTML元素本身就带有的固有属性，在处理时，使用prop方法。
        }

    }

})(window.jQuery);
