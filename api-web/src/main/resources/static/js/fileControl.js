
//预览附件
function seeFile(attachments) {

    var data = [];
    attachments.forEach(function (item) {
        data.push({
            "alt": item.fileName, //图片名
            "pid": item.fileId, //图片id
            "src": item.showUrl //原图地址
        });
    });
    var json=  {
        "title": "附件预览", //相册标题
        "start": 0, //初始显示的图片序号，默认0
        "data": data  //相册包含的图片，数组格式
    };
    layer.photos({
        photos: json //格式见API文档手册页
        ,anim: 0 //0-6的选择，指定弹出图片动画类型，默认随机
        , closeBtn: 1, //不显示关闭按钮
    });
}


//下载
function downFile2(swfModel,swfName,dataSource){
    $("#swfModel").val(swfModel);
    $("#swfName").val(swfName);
    $("#dataSource").val(dataSource);
    $('#modelDown').submit();

}