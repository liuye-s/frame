/**
 * 传入度分秒获取经纬度
 * @param {} du
 * @param {} fen
 * @param {} miao
 * @return {}
 */
function jingduFocus(du,fen,miao){
    var jwd=0;
    if(du==""){du=0;}  if(du>180||du<0){$("#du").val("0");du=0;alert("度值仅限于0-180之间");}
    if(fen==""){fen=0;} if(fen>60||fen<0){$("#fen").val("0");fen=0;alert("分值仅限于0-60之间");}
    if(miao==""){miao=0;}  if(miao>60||miao<0){$("#miao").val("0");miao=0;alert("秒值仅限于0-60之间");}
    var num = (parseFloat(miao/60)+parseFloat(fen))/60+parseFloat(du);
    var str = num.toString();
    if(str.indexOf('.')<0){
        jwd=num;
    }else{
        jwd=str.substring(0,str.indexOf('.')+7);
    }
    return   jwd;
}

/**
 * 传入经度或者纬度获取度分秒
 * @param {} num  经度或者纬度
 * @param {} numType  返回的类型（du,fen,miao）
 * @return {}
 */
function getdfm(num, numType) {
    var du = 0;
    var fen = 0;
    var miao = 0

    var len = num.indexOf('.');
    if (len == "-1") {
        du = num;
        fen = "0";
        miao = "0";
    } else {
        var du = num.substring(0, len);
        var f = 0 + num.substring(len, num.length);
        var fen = parseFloat(f) * 60;
        var m = fen.toString();
        len = m.indexOf('.');
        if (len == "-1") {
            du = du;
            fen = m;
            miao = "0";
        } else {
            var mi = 0 + m.substring(len, m.length);
            var miao = parseFloat(mi) * 60 + 0.49;
            fen = du;
            fen = m.substring(0, len);
            var s = miao.toString();
            len = s.indexOf('.');
            if (len == "-1")
                miao = s;
            else
                miao = s.substring(0, len);
        }
    }
    if (numType =='du') {
        return du;
    } else if (numType == 'fen') {
        return fen;
    } else if (numType == 'miao') {
        return miao;
    }

}

String.prototype.replaceAll = function(s1,s2) {
    return this.replace(new RegExp(s1,"gm"),s2);
}

var utils = {
    /**
     * 获取url参数
     *
     * @param key
     */
    getUrlParam : function(key){
        if(key){
            var url = location.href;
            var paramsStr = url.substring(url.lastIndexOf("?")+1);
            var paramArray = paramsStr.split("&");
            for(var i=0;i<paramArray.length;i++){
                var p = paramArray[i].split("=");
                if(p[0]==key){
                    return p[1];
                }
            }
        }
        return;
    },

    /**
     * 根据文件名获取文件类型
     *
     * @param fileName
     * @returns 文件后缀的大写
     */
    getFileExtention : function(fileName){
        if(fileName){
            return fileName.split('.')[fileName.split('.').length-1].toUpperCase();
        }
        return;
    },

    /**
     * 日期转换
     * yyyy splitStr mm splitStr dd hh:mm:ss
     * @param {} date  splitStr 分隔字符
     * @return {}
     */
    dateFormatSec : function(date,splitStr){
        if (date) {
            if(!splitStr){
                splitStr = "/";
            }
            var month = parseInt(date.getMonth())<9?"0"+(date.getMonth()+1):date.getMonth()+1;
            var day = parseInt(date.getDate())<10?"0"+date.getDate():date.getDate();
            var hours = parseInt(date.getHours())<10?"0"+date.getHours():date.getHours();
            var minutes = parseInt(date.getMinutes())<10?"0"+date.getMinutes():date.getMinutes();
            var seconds = parseInt(date.getSeconds())<10?"0"+date.getSeconds():date.getSeconds();
            return date.getFullYear() + splitStr + month + splitStr + day + ' ' + hours + ':' + minutes + ':' + seconds;
        } else {
            return "未知";
        }
    },

    loadJS : function(url,callback,charset) {
        var script = document.createElement('script');
        script.onload = script.onreadystatechange = function ()
        {
            if (script && script.readyState && /^(?!(?:loaded|complete)$)/.test(script.readyState)) return;
            script.onload = script.onreadystatechange = null;
            script.src = '';
            script.parentNode.removeChild(script);
            script = null;
            if(callback)callback();
        };
        script.charset=charset || document.charset || document.characterSet;
        script.src = url;
        try {document.getElementsByTagName("head")[0].appendChild(script);} catch (e) {}
    },

    /**
     * 文件上传formId：form表单的ID cb回调函数
     */
    uploadFile : function(fromId,fileSource,cb){
        var options = {
            url : config.eduCloudStorageURL+"upload/1_"+fileSource,
            type : 'POST',
            cache:true,
            mitType : "uplaodFile",
            dataType : "json",
            success : function(data) {
                //
                if(data && data.retCode == 0){
                    if(cb)
                        cb(data);
                }else if(data.retCode==2){
                    $.msgBox.alert({
                        title : "",
                        cls:"eduCloudTip",
                        width:270,
                        msg : "文件大小超过了指定大小!",
                        icon:"warning"
                    });
                }else{
                    $.msgBox.alert({
                        title : "",
                        cls:"eduCloudTip",
                        width:270,
                        msg : "文件上传失败，请重新上传!",
                        icon:"warning"
                    });
                }
            },
            error : function(data){
            }
        };
        $('#'+fromId).ajaxSubmit(options);
        $.msgBox.alert({
            title : "文件上传",
            cls : "eduCloudTip",
            width : 370,
            html : "上传进度:<div id='progress' style='background: #728820; height: 10px; width: 0'></div><font id='uploadStatus'>0</font>%</br></br>已经上传:" +
                "<font id='uploadSize'>0</font>&nbsp;&nbsp;&nbsp;&nbsp;," +
                "总大小:<font id='uploadTotalSize'></font>"+'<div class="btn mt10"><a class="btnSure" href="javascript:void(0)" onclick="$.msgBox.close();" ><span>完成</span></a></div>'
        });
        utils.uploadProcess(1);
    },
    uploadProcess : function(i){
        var inter;
        $.ajax({
            url: config.eduCloudStorageURL + "uploadStatus/1?c="+i,
            type : "GET",
            dataType: "jsonp",
            jsonp:"jsonpcallback",
            success: function(data){
                if(data.percent < 100){
                    //$("#progress").css((data.percent+'%'),300);
                    $("#progress").css("width",data.percent+"%");

                    $('#uploadStatus').html('');
                    $('#uploadStatus').html(data.percent);

                    $('#uploadSize').html('');

                    $('#uploadSize').html(data.comTotal);

                    $('#uploadTotalSize').html('');
                    $('#uploadTotalSize').html(data.allTotal);

                    inter=setTimeout("utils.uploadProcess("+(i+1)+")",200);
                } else if (data.limitTime==0){
                    $("#progress").css("width",data.percent+"%");

                    $('#uploadStatus').html('');
                    $('#uploadStatus').html(data.percent);

                    $('#uploadSize').html('');
                    $('#uploadSize').html(data.comTotal);

                    $('#uploadTotalSize').html('');
                    $('#uploadTotalSize').html(data.allTotal);
                }
            }
        });
    },

    /**
     * 加载按钮
     * @param menuId
     * 						菜单Id
     * @param buttonPlace
     * 						按钮加载位置
     */
    addButton: function(menuId, buttonPlace) {
        $.ajax({
            type : "POST",
            url : config.baseURL + 'button_role/find.json',
            dataType : "json",
            data : {
                "menuId" : menuId
            },
            success : function(data) {
                $.each(data.list, function(index, value) {
                    var buttonHtml = $('<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="' + value.buttonIcon +'" plain="true" onclick="'+ value.buttonOnclick +'">'+ value.buttonName +'</a>');
                    buttonHtml.linkbutton();
                    $("#" + buttonPlace).append(buttonHtml);
                });
            },
            error : function() {
                $.messager.alert('系统提示', '加载按钮失败', 'error');
            }
        });
    },

    /**
     * 动态加载菜单操作权限
     * @param	menuId	菜单ID
     * @param	type	功能类型（与增删改操作函数名相关）
     * @param	buttonPlace	按钮区域DIV的id
     */
    loadActionButton: function(menuId, type, buttonPlace) {
        $.ajax({
            type : 'GET',
            url : config.baseURL + 'menu/getMenuAction.json',
            dataType : 'json',
            data : {
                'menuId' : menuId
            },
            success : function(data) {
                if (data.success) {
                    var buttonIcon;
                    var buttonOnClick;
                    var buttonName;
                    $.each(data.list, function(index, value) {
                        switch (value) {
                            case 'b':
                                buttonIcon = 'icon-add';
                                buttonOnClick = 'new' + type + '()';
                                buttonName = '增加';
                                break;
                            case 'c':
                                buttonIcon = 'icon-edit';
                                buttonOnClick = 'edit' + type + '()';
                                buttonName = '编辑';
                                break;
                            case 'd':
                                buttonIcon = 'icon-remove';
                                buttonOnClick = 'remove' + type + '()';
                                buttonName = '删除';
                                break;
                        }
                        var buttonHtml = $('<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="'
                            + buttonIcon
                            +'" plain="true" onclick="'
                            + buttonOnClick
                            +'">'
                            + buttonName +'</a>');
                        buttonHtml.linkbutton();
                        $('#' + buttonPlace).append(buttonHtml);
                    });
                } else {
                    $.messager.alert('系统提示', '加载按钮失败', 'error');
                }
            },
            error : function() {
                $.messager.alert('系统提示', '加载按钮失败', 'error');
            }
        });
    },

    /**
     * 生成iFrame代码块
     * @param url
     */
    createFrame : function(url) {
        var s = '<iframe scrolling="auto" frameborder="0" width="100%" height="100%" allowTransparency src="' + url + '"></iframe>';
        return s;
    },

    /**
     * 动态加载CSS
     * @param path
     */
    loadCSS : function(path) {
        $('head').append('<link>');
        var a = $('head').children(':last');
        a.attr({
            rel: 'stylesheet',
            type: 'text/css',
            href: config.sourceURL + path
        });
    }
};

$(function() {

});

$(window).resize(function() {
    var d = $('table[type="dataGrid"]');
    var l = $('div[type="layout"]');
    var p = $('div[type="panel"]');
    var w = $('div[type="window"]');
    if (d && d.length != 0) {
        d.datagrid('resize');
    }
    if (l && l.length != 0) {
        l.layout('resize');
    }
    if (p && p.length != 0) {
        p.panel('resize');
    }
    if (w && w.length != 0) {
        w.window('resize');
    }
});
