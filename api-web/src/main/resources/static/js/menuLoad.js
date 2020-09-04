
$(function() {// 初始化加载一级菜单

    loadMenu(menuTree);

    $(".ths-top-menu-a").css("cssText","margin-top:0 !important;height:"+  $(".topMenuItem").eq(0).height() +"px !important");

    $("#sidebarTopList>li:first").addClass("active");

    $("#sidebarTopList>li").each(function(index,el){

        $("#sidebar").removeClass("sidebar").hide();
        var len =  $("#sidebarTopList>li").length;

        if(index!=len-1){
          $(el).on("click",function(e){

              $("#sidebarTopList>li").removeClass("active").eq(index).addClass("active");
                $("#sidebar .nav.nav-list").empty();
                var children = $.data(this, "children");

                if(children != null && children != "" ){

                    /**
                     * 如果有多级菜单，请将“open"的classname加上，表示展开
                     * $("#sidebar").find(".nav-list li:first").addClass("active open");
                     */
                    // $("#sidebar").find(".nav-list li:first").addClass("active");

                    // $.acetab.init();


                    // #20190803 modify by guoyong for IE兼容性
                    // $("#sidebar .nav.nav-list").append(factorialLoadLeftMenu(children,index));
                    // $("#sidebar").removeClass("sidebar").hide();
                    // $("#sidebar").addClass("sidebar").fadeIn().attr("data-topMenuIndex",index);
                    //
                    // $(".menuItem[data-id='5311']").addClass("changgui");
                    // $(".menuItem[data-id='5336']").addClass("chaxun");

                    var promise = new Promise(function(resolve,reject){
                            $("#sidebar .nav.nav-list").append(factorialLoadLeftMenu(children,index));
                            $("#sidebar").removeClass("sidebar").hide();
                            $("#sidebar").addClass("sidebar").fadeIn().attr("data-topMenuIndex",index);

                            $(".menuItem[data-id='5311']").addClass("changgui");
                            $(".menuItem[data-id='5336']").addClass("chaxun");
                            $("#sidebar").find(".nav-list li:first").attr("data-active","yes");
                            resolve();
                    });

                    promise.then(function(){
                        $.acetab.init();
                    });


                }else {
                    $("#sidebar").removeClass("sidebar").hide();
                    $.acetab.addTab(e);
                }

            });
        }


    });


});

function loadMenu(menuTree) {

    menuTree.forEach(function(item,index){
        var _icon = "";
        switch (index) {
            case 0:
                _icon = 'index1';
                break;
            case 1:
                _icon = 'company';
                break;
            case 2:
                _icon = 'search';
                break;
            case 3:
                _icon = 'exception';
                break;
            case 4:
                _icon = 'data';
                break;
            case 5:
                _icon = 'shebeiguanli1';
                break;
            case 6:
                _icon = 'set';
                break;
            case 7:
                _icon = 'tongjifenxi';
                break;
            case 8:
                _icon = 'yonghuguanli';
                break;
            default:
                _icon = 'set';

        }
        var htmlLi =" <li class=\"hover  topMenuItem \" id=\""+item.name+"\" data-id=\""+item.id+"\" data-topMenuIndex=\""+index+"\"  href=\""+item.url+"\" menuname=\""+item.name+"\" menuhaschild=\""+item.hasChild+"\" >\n" +
            "             <a href=\"javascript:\" class=\"ths-top-menu-a\" data-level=\"top\">\n" +
            // "                 <img src='/menu/getImg?id="+item.id+"' class='top-menu-img' alt=\"\">  <br/> \n"+
            "                 <i class='icon iconfont icon-"+ _icon +"'></i>  <br/> \n"+
            "                <span class=\"menu-tex\">"+item.name+"</span>\n"+
            "             </a>\n"+
            "             <b class=\"arrow\"></b>\n"+
            "         </li>";
        $("#userIcon").before(htmlLi);

        $("#"+item.name).data( "children", item.children);
    });

    $("#navbar li:last-child,#navbar li:last-child .border").height($("#navbar").height());

    // $('.topMenuItem[menuhaschild="0"]').on('click', $.acetab.addTab);
}

function  factorialLoadLeftMenu(item,index) {

    if (item[0].children == null) {
        if (item.length == 1) {
            return " <li class=\"\">\n" +
                "      <a href='" + item[0].url + "' data-id='" + item[0].id + "'  onclick='getItem(\"" + item[0].url + "\",\"" + item[0].id + "\")' target=\"main\" class=\"menuItem\" data-topMenuIndex = '" + index + "'>\n" +
                // "        <img src='/menu/getImg?id="+item[0].id+"' alt=\"\" onerror=nofind();>\n" +
                "                 <i class='icon iconfont icon-level1'></i>  \n" +
                "        <span class='menu-text'>" + item[0].name + "</span>\n" +
                "      </a>\n" +
                "\n" +
                "    <b class=\"arrow\"></b>\n" +
                "   </li>";
        } else {
            return " <li class=\"\">\n" +
                "      <a href='" + item[0].url + "' data-id='" + item[0].id + "' onclick='getItem(\"" + item[0].url + "\",\"" + item[0].id + "\")'  target=\"main\" class=\"menuItem\" data-topMenuIndex = '" + index + "'>\n" +
                // "      <img src='/menu/getImg?id="+item[0].id+"' alt=\"\" onerror=nofind();>\n" +
                "                 <i class='icon iconfont icon-level1'></i>   \n" +
                "        <span class='menu-text'>" + item[0].name + "</span>\n" +
                "      </a>\n" +

                "\n" +
                "    <b class=\"arrow\"></b>\n" +
                "   </li>" + factorialLoadLeftMenu(item.slice(1), index);
        }

    } else {
        if (item.length == 1) {
            return " <li class=\"\">\n" +
                "    <a href='" + item[0].url + "' data-id='" + item[0].id + "' onclick='getItem(\"" + item[0].url + "\",\"" + item[0].id + "\")' class=\"dropdown-toggle\" data-topMenuIndex = '" + index + "'>\n" +
                // "      <img src='/menu/getImg?id="+item[0].id+"' alt=\"\" onerror=nofind();>\n" +
                "                 <i class='icon iconfont icon-level1'></i>  \n" +
                "      <span class=\"menu-text\">" + item[0].name + "</span>\n" +
                "      <b class=\"arrow fa fa-angle-down\"></b>\n" +
                "    </a>\n" +
                "    <ul class=\"submenu\">" +
                factorialLoadLeftMenu(item[0].children, index) +
                "\n" +
                "\n" +
                "                    </ul>\n" +
                "                </li>";
        } else {
            return " <li class=\"\">\n" +
                "    <a href='" + item[0].url + "' data-id='" + item[0].id + "' onclick='getItem(\"" + item[0].url + "\",\"" + item[0].id + "\")' class=\"dropdown-toggle\" data-topMenuIndex = '" + index + "'>\n" +
                // "      <img src='/menu/getImg?id="+item[0].id+"' alt=\"\" onerror=nofind();>\n" +
                "                 <i class='icon iconfont icon-level1'></i>  \n" +
                "      <span class=\"menu-text\">" + item[0].name + "</span>\n" +
                "      <b class=\"arrow fa fa-angle-down\"></b>\n" +
                "    </a>\n" +
                "    <ul class=\"submenu\">" +
                factorialLoadLeftMenu(item[0].children, index) +
                "\n" +
                "\n" +
                "                    </ul>\n" +
                "                </li>" + factorialLoadLeftMenu(item.slice(1), index);
        }
    }


}

//点击左侧菜单 iframe 切换
function getItem(url,id) {
    // layer.msg(id);
    $('.mainContent .ace_iframe').each(function () {
        if ($(this).data('id') == id) {
            $(this).show().siblings('.ace_iframe').hide();
            return false;
        }
    });
}

function nofind(){
    var img=event.srcElement;
    img.src="/images/moren.png";
    img.onerror=null; //控制不要一直跳动
}

function changePassword() {
    var index = layer.open({
        type: 2,
        title: "修改登录密码",
        closeBtn: 1,
        anim: 0,
        // maxmin: true, //开启最大化最小化按钮
        shadeClose: true,
        area: ['400px', '275px'],
        shade: 0.3,
        moveType: 0, //拖拽风格，0是默认，1是传统拖动
        content: ["/passWordReset"] //iframe的url，no代表不显示滚动条
    });
}