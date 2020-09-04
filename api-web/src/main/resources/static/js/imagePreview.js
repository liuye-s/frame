
$(function () {

    setPreview();

});

function setPreview(divId, itemId) {
    $((divId ? ("#" + divId) : "") + ".wu-example .uploader-list " + (itemId ? ("#" + itemId) : "") + ".item p.fa-eye").click(function () {
        var data = [];
        var thiz = $(this);
        var imageNo = 0;
        thiz.closest("div.uploader-list").find(".item p.fa-eye").each(function (index) {
            data.push({
                "alt": $(this).attr("filename"), //图片名
                "pid": $(this).attr("fileid"), //图片id
                "src": $(this).attr("filesrc") //原图地址
            });
            if (thiz.is($(this))) {
                imageNo = index;
            }
        });

        var json=  {
            "title": "图片预览", //相册标题
            "start": imageNo, //初始显示的图片序号，默认0
            "data": data  //相册包含的图片，数组格式
        };
        layer.photos({
            photos: json //格式见API文档手册页
            // ,anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机
        });
    });
}

