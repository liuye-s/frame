String.prototype.replaceAll = function(s1,s2) { 
    return this.replace(new RegExp(s1,"gm"),s2); 
}
//去空格
function trimkeyup(e) {
     lucene_objInput = $(this);
   if (e.keyCode != 38 && e.keyCode != 40 && e.keyCode != 13) {
        var im = $.trim(lucene_objInput.val());
       lucene_objInput.val(im); 
     }
}       
/*$.fn.datebox.defaults.formatter = function(date) {
	var y = date.getFullYear();
	var m = date.getMonth() + 1;
	var d = date.getDate();
	return y + '-' + (m < 10 ? '0' + m : m) + '-' + (d < 10 ? '0' + d : d);
	};
	 
	$.fn.datebox.defaults.parser = function(s) {
	 
	if (s) {
	var a = s.split('-');
	var y = parseInt(a[0]);
	var m = parseInt(a[1]) - 1;
	var d = parseInt(a[2]); 
	var str = y + '-' + (m < 10 ? '0' + m : m) + '-' + (d < 10 ? '0' + d : d);
	return new Date(str)
	} else {
	return new Date();
	}

	};*/
	
jQuery.fn.limit=function(){
	var self = $("[limit]");
	self.each(function(){
	var objString = $.trim($(this).text()); 
	var objLength = $.trim($(this).text()).length; 
	var num = $(this).attr("limit"); 
	if(objLength > num){
	  $(this).attr("title",objString); 
	  $(this).text(objString.substring(0,num)+ "...");
	} 
	});
}

var dataStatus = [
        		{
					value: '',
					text: '全部'
				},
				{
					value: '0',
					text: '正常',
					selected:'true'
				},
				{
					value: '1',
					text: '删除'
				}
			] 
var utils = {
		
		/**
		 * 公共的查询数据字典
		 * dicCode ：数据字典名称
		 * selectId ：数据字典输出<select>的id属性
		 * defaultVal ：数据字典默认值（修改时，还原数据字典选择值）
		 */
		  querySubList : function(dicCode,selectId,defaultVal,isAll){ 
				$.post("../dictionary/getSubListByParent.wag",{'dicCode':dicCode},function(data){ 
				    var datad = []; 
				    if(isAll){
				      datad.push({ "text": "全部", "value": ""});
				    }
					 $.each(data.listDic, function(i, obj){  
 
					     datad.push({ "text": obj.dicSubName, "value": obj.dicSubCode});
					 }); 
				    $("#"+selectId).combobox("loadData", datad);
				    if(null != defaultVal && defaultVal != ""){
					    $("#"+selectId).combobox("setValue",defaultVal);  
				    }
				},"json");
				
			},
		 queryThrList : function(dicCode,selectId,defaultVal,isAll){ 
					$.post("../dictionary/getThrListByParent.wag",{'dicSubCode':dicCode},function(data){ 
					    var datad = []; 
					    if(isAll){
					      datad.push({ "text": "全部", "value": ""});
					    }
						 $.each(data.listDic, function(i, obj){  
	 
						     datad.push({ "text": obj.dicThrName, "value": obj.dicThrCode});
						 }); 
					    $("#"+selectId).combobox("loadData", datad);
					    if(null != defaultVal && defaultVal != ""){
						    $("#"+selectId).combobox("setValue",defaultVal);  
					    }
					},"json");
					
				},
			//由于此方法的原来的数据库表删除，修改为从enterprise_archives数据库数据字典表中取值  	update by zhangyy in 2018-12-27
			queryPortalManageSysDictionary : function(dicCode,selectId,defaultVal,isAll){ 
				$.post("../dictionary/getSubListByParent.wag",{'dicCode':dicCode},function(data){ 
				    var datad = []; 
				    if(isAll){
				      datad.push({ "text": "全部", "value": "-1"});
				    }
					$.each(data.listDic, function(i, obj){  
 
					     datad.push({ "text":  obj.dicSubName, "value": obj.dicSubCode});
					}); 
				    $("#"+selectId).combobox("loadData", datad);
				    if(null != defaultVal && defaultVal != ""){
					    $("#"+selectId).combobox("setValue",defaultVal);  
				    }
				},"json");
				
			},
			//增加二级字典编码查询所属三级字典的查询方法  	add by zhangyy in 2018-12-28     
			queryThreeList : function(dicCode,selectId,defaultVal,isAll){ 
				$.post("../dictionary/getThrListBySub.wag?dicSubCode="+dicCode,{},function(data){ 
				    var datad = []; 
				    if(isAll){
				      datad.push({ "text": "全部", "value": "-1"});
				    }
					 $.each(data.listDic, function(i, obj){  
					     datad.push({ "text": obj.dicThrName, "value": obj.dicThrCode});
					 }); 
				    $("#"+selectId).combobox("loadData", datad);
				    if(null != defaultVal && defaultVal != ""){
					    $("#"+selectId).combobox("setValue",defaultVal);  
				    }
				},"json");
			}, 
			setDictionaryVal : function(dicCode,selectId,defaultVal){ 
				$.post("../dictionary/getSubListByParent.wag",{'dicCode':dicCode},function(data){ 
				    var datad = []; 
				    
					 $.each(data.listDic, function(i, obj){  
                         if(obj.dicSubCode == defaultVal){ 
                        	 $("#"+selectId).html(obj.dicSubName);
                         } 
					 });  
				},"json");
				
			},
			/**
			 * 公共的状态<select>
			 * id：<select>id 属性
			 * defaultVal：选中值
			 */
			queryStaticStatus:function(id,defaultVal){ 
				  //初始化选中值 
				  $("#"+id).combobox("loadData",dataStatus);
			 
				  //if(null !=defaultVal && defaultVal != ""){ 
					   $("#"+id).combobox("setValue",defaultVal);  
				  //}
			}, 
			queryIndexSubList : function(dicCode,selectId,defaultVal,isAll){ 
				debugger
				$.post("getSubListByParent.htm",{'dicCode':dicCode},function(data){ 
				    var datad = []; 
				    if(isAll){
				      datad.push({ "text": "全部", "value": ""});
				    }
					 $.each(data.listDic, function(i, obj){  
 
					     datad.push({ "text": obj.dicSubName, "value": obj.dicSubCode});
					 }); 
				    $("#"+selectId).combobox("loadData", datad);
				    if(null != defaultVal && defaultVal != ""){
					    $("#"+selectId).combobox("setValue",defaultVal);  
				    }
				},"json");
				
			},
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
    						buttonIcon = 'icon add';
    						buttonOnClick = 'new' + type + '()';
    						buttonName = '增加';
    						break;
    					case 'c':
    						buttonIcon = 'icon edit';
    						buttonOnClick = 'edit' + type + '()';
    						buttonName = '编辑';
    						break;
    					case 'd':
    						buttonIcon = 'icon dele';
    						buttonOnClick = 'remove' + type + '()';
    						buttonName = '删除';
    						break;
    					}
    					var buttonHtml = '<li><a href="javascript:void(0)" class="' 
    						+ buttonIcon
    						+'" hideFocus="false" onclick="'
    						+ buttonOnClick
    						+'">'
    						+ buttonName
    						+'</a></li>';
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
    },
    
    /**
     * 获取当前登录用户信息
     * @param callback	回调函数
     */
    getUserInfo : function(callback) {
    	$.ajax({
    		type : 'GET',
    		url : config.baseURL + 'user/getCurrentUserInfo',
    		dataType : 'json',
    		async : false,//取消异步请求
    		success : function(data) {
    			if (data.success) {
    				callback(data);
    			} else {
    				$.messager.alert('系统提示', '获取用户信息失败', 'error');
    			}
    		},
    		error : function() {
    			$.messager.alert('系统提示', '获取用户信息失败', 'error');
    		}
    	});
    },
    
    /********************************** artDialog相关方法封装 START **********************************/
    /**
     * 页面窗口
     * @param title	标题
     * @param content	页面内容
     * @param width	窗口宽
     * @param height	窗口高
     */
    win : function(title, content, width, height) {
    	art.dialog({
    	    content : content,
    	    lock : true,
    	    title : title,
    	    width : width,
    		height : height
    	});
    },
    
    /**
     * 有回调函数的提示框
     * @param title
     * @param content
     * @param fun
     */
    blockingAlert : function(title, content, fun) {
    	art.dialog({
		    content: content,
		    title:title,
		    ok: function () {
		    	fun();
		    },
		    cancel: false,
		    lock:true
		});
    },
    /**
     * 提示窗口
     * @param title
     * @param html
     */
    alert : function(title, content) {
    	art.dialog({
			lock:true,
		    content: content,
		    title:title
		});
    },
    /**
     * 有回调函数的确认消息窗口
     * @param title
     * @param content
     * @param yesFun
     */
    confirm : function(title, content, yesFun) {
    	art.dialog({
		    content: content,
		    title:title,
		    icon:'question',
		    ok: function () {
				yesFun();
		    },
		    cancel: true,
		    lock:true
		});
    },
    /**
     * 关闭指定对象里的所有artDialog窗口
     * @param owner
     */
    closeAllDialog : function(owner) {
    	var list = owner ? owner.art.dialog.list : art.dialog.list;
    	for (var i in list) {
    	    list[i].close();
    	}
    },
    /********************************** artDialog相关方法封装 END **********************************/
    
    
    /********************************** 翻页相关方法 START **********************************/
    /**
     * 生成动态翻页
     * @param pageNum 当前页数
     * @param total 总页数
     * @param func 回调函数（带当前页码参数）
     */
    createPagination : function(pageNum, total) {
    	if(null == pageNum)
    		pageNum = 1; 
    	$('#currentPage').val(pageNum);
    	var _html; 
    	if (pageNum <= 1  ) {
    		_html = '<li class="prev disabled"><a href="javascript:void(0)" aria-label="Previous"><span aria-hidden="true"><img src="../Themes_2.0/default/images/prevpage.png"/></span></a></li>';
    	} else{
    		_html = '<li class="prev" onclick="utils.prev()"><a href="javascript:void(0)" aria-label="Previous"><span aria-hidden="true"><img src="../Themes_2.0/default/images/prevpage.png"/></span></a></li>';
    	}
    	var difference;
		if ((pageNum - 2) < 1 || total <= 5) {
			difference = 0;
		} else if ((pageNum - (-2)) > total) {
			difference = total - 5;
		} else {
			difference = pageNum - 3;
		}
    	for (var i=1; i<=(total<5 ? total : 5); i++) {
    		var j;
    		j = i + difference;
    		if (j == pageNum) {
    			_html += '<li class="active" onclick="utils.createPagination(';
    			_html += j;
    			_html += ',';
        		_html += total;
    			_html += ')"><a href="javascript:void(0)" onclick="utils.goToPage(';
    			_html += j;
    			_html += ')">';
    			_html += j;
    			_html += '</a></li>';
    		} else {
    			_html += '<li onclick="utils.createPagination(';
    			_html += j;
    			_html += ',';
        		_html += total;
    			_html += ')"><a href="javascript:void(0)"onclick="utils.goToPage(';
    			_html += j;
    			_html += ')">';
    			_html += j;
    			_html += '</a></li>';
    		}
    	}
    	
    	if (pageNum == total) {
    		_html += '<li class="next disabled"><a href="javascript:void(0)" aria-label="Next"><span aria-hidden="true"><img src="../Themes_2.0/default/images/nextpage.png" /></span></a></li>';
    	} else {
    		_html += '<li class="next" onclick="utils.next()"><a href="javascript:void(0)" aria-label="Next"><span aria-hidden="true"><img src="../Themes_2.0/default/images/nextpage.png" /></span></a></li>';
    	}
    	$('ul.pagination').html(_html);
    },
    
    /**
     * 上一页
     */
    prev : function() {
    	$("#currentPage").val($('#currentPage').val()-1); 
    	listForm.submit();
    },
    
    /**
     * 上一页
     */
    next : function() {
    	$("#currentPage").val($('#currentPage').val()-(-1)); 
        listForm.submit();
    },
    /**
     * 跳转
     */
    goToPage:function(currentPage){
    	$("#currentPage").val(currentPage); 
    	 listForm.submit();
    },
    /**
     * 设置页数
     */
    selVal:function(pageSize){ 
		$(".dropdown-toggle").find("span:eq(0)").text(pageSize);
		$("#pageSize").val(pageSize);
		$("#currentPage").val(1);
		listForm.submit();
    }
    /********************************** 翻页相关方法 END **********************************/
};

$(function() {
	 $("[limit]").limit();
	 $(".comTbody .table-tbody tr").find("td").click(function(){
         if($(this).parent().find(":checkbox").attr("checked")){  
             $(this).parent().find("td").end().find(":checkbox").attr("checked",false);   
             $(this).parent().find("td").each(function(){
  				 $(this).attr("style","");
             });
         }else{  
             $(this).parent().find("td").end().find(":checkbox").attr("checked",true);   
             $(this).parent().find("td").each(function(){
  				 $(this).attr("style","background:#eaf2fb");
  				 ////2.0--》#C7DFE6 
             });
         }  
     });
	 
	 //表单折叠效果

	    $(".iset .hd").attr("style","cursor:pointer");
	    $(".iset .hd").attr("title","可以折叠");
	    $(".iset .hd").click(function(){ 
			  var disP = $(this).attr("disP");
			  if(typeof(disP) == "undefined" || disP == ""){ 
//				  $(".iset .hd").each(function(){ 
//					  $(this).parent().find(".bd").attr("style","display:block");
//					  $(this).find(".t").attr("style","");
//					  $(this).attr("disP","");
//				  });
				  $(this).find(".t").attr("style","background:#FFF url(../Themes/default/images/page_dropdown_caret.gif) no-repeat left;");
				  $(this).parent().find(".bd").attr("style","display:none");
				  $(this).attr("disP","true");
			  }else{
//				  $(".iset .hd").each(function(){ 
//					  $(this).find(".t").attr("style","background:#FFF url(../Themes/default/images/page_dropdown_caret.gif) no-repeat left;");
//					  $(this).parent().find(".bd").attr("style","display:none");
//					  $(this).attr("disP","true");
//				  });
				  $(this).parent().find(".bd").attr("style","display:block");
				  $(this).find(".t").attr("style","");
				  $(this).attr("disP","");
			  }
		 });
/*	    if($.support.opacity == false){
	   	 $('tbody tr:nth-child(2n-1)').css({
	   			background:'#ddd' 
	   		 });
	   	 $('tbody tr:nth-child(2n-1) td:nth-child(2n)').css({
	   		background:'#eee' 
	   	 });

	   	 $('tbody tr:nth-child(2n) td:nth-child(2n-1)').css({
	   		background:'#f4f4f4' 
	   	 });
	    }*/
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


//上传
function btnUplad(btnId,organizationCode,zhujianId,model){
	   art.dialog.open("../attach/addInterface.wag?btnId="+btnId+"&fileType="+organizationCode+"&zhujianId="+zhujianId+"&model="+model,{height:400,width:650,title:'上传附件',lock:true,drag:true});
}
//上传
function btnUploadBathNoChange(btnId,organizationCode,zhujianId,model){
	   art.dialog.open("../attach/addBathNoChange.wag?btnId="+btnId+"&fileType="+organizationCode+"&zhujianId="+zhujianId+"&model="+model,{height:400,width:650,title:'上传附件',lock:true,drag:true});
}
//阅览
function showFile(swfModel,swfName,fileName,dataSource){  
	var index1=fileName.lastIndexOf("."); 
	var index2=fileName.length; 
	var postf=fileName.substring(index1+1,index2);
 
	//if(postf=="png" ||postf=="PNG"){
	//	window.open("../../onePlants/"+swfModel+"/"+swfName, "附件阅览", null, null);
	//}else{
		  window.open("../pubUpload/showFile.wag?swfModel="+swfModel+"&swfName="+swfName+"&fileName="+encodeURI(encodeURI(fileName))+"&dataSource="+dataSource, "附件阅览", null, null);
	//}
	 
}
//下载
function downFile(swfModel,swfName,dataSource){
	     $('#modelDown').form({
		 		url : "../pubUpload/resourceDownload.wag?swfModel="+swfModel+"&swfName="+swfName+"&dataSource="+dataSource
		  });
		  $('#modelDown').submit();
}

//下载
function downFile2(swfModel,swfName,dataSource){
          $("#swfModel").val(swfModel);
          $("#swfName").val(swfName);
          $("#dataSource").val(dataSource);
		  $('#modelDown').submit();
		  
}
//删除
function deleteFile(swfModel,id,tId){
	 art.dialog.confirm('你确定要删除该附件信息吗？', function () { 
		 $.ajax({type:'post',
					dataType:'json',
					async: false,
					url:'../attach/delete.wag',
		     		data:{fileId:id},
		   			success: function(data) {
					     if(data.success = true){ 
					    	 //location.href="../notice/updateInterface.wag?id="+tId;
					    	 $("#upl"+id).remove();
					    	 art.dialog.alert('删除成功！');
					     }else{
					    	 art.dialog.alert('删除失败，请联系管理员！');
					     }
			        }
		 }); 
	 },function(){});
}
function logout() {
	utils.confirm('系统提示', '确定退出当前系统？', function() {
		$.ajax({
			type : 'POST',
			async : false,
			url : '../user/logout',
			dataType : 'json',
			success : function(data) {
				location.href = '/qymh';
			},
			failure : function(data) {
				art.dialog('退出失败！');
			}
		})
	});
}
/**
 * 传入度分秒获取经纬度
 * @param {} du
 * @param {} fen
 * @param {} miao
 * @return {}
 */
 function jingduFocus(du,fen,miao){
              var jwd=0;
               var isFlase = false;
                if(du==""){du=0;}  if(du>180||du<0){$("#du").val("0");du=0;alert("度值仅限于0-180之间"); isFlase = true;}
                if(fen==""){fen=0;} if(fen>60||fen<0){$("#fen").val("0");fen=0;alert("分值仅限于0-60之间");isFlase = true;}
                if(miao==""){miao=0;}  if(miao>60||miao<0){$("#miao").val("0");miao=0;alert("秒值仅限于0-60之间");isFlase = true;} 
                var num = (parseFloat(miao/60)+parseFloat(fen))/60+parseFloat(du);
                var str = num.toString();
                if(str.indexOf('.')<0){
                   jwd=num;
                }else{
                  jwd=str.substring(0,str.indexOf('.')+7);   
                }
                if(isFlase){
                	jwd = -1;
                }
              return jwd;
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