/**
 * 打开导航菜单
 * @param navId 父菜单ID
 * @param elementId 当前也菜单ID
 */
function activeNavBar(navId, elementId) {
    var cas = navId + ">ul";
    $("#" + navId).addClass("open");
    $("#" + cas).css("display", "block");
    $("#" + elementId).addClass("active");
}

/**
 * 初始化操作按钮
 * @param selector 选择器
 * @param rowId 行ID
 * @param op       操作
 */
function initOpBtn(selector, rowId, op) {
    if (op && op != "") {
        var btn = "<div class='btn-group'>" +
            "<button type='button' class='btn btn-default btn-xs'>操作</button>" +
            "<button type='button' class='btn btn-default btn-xs dropdown-toggle' data-toggle='dropdown'>" +
            "<span class='caret'></span><span class='sr-only'></span></button><ul class='dropdown-menu pull-right' role='menu'>"
            + op +
            "</ul></div>";

        jQuery(selector).jqGrid('setRowData', rowId, {act: btn});
    }
}

/**
 *隐藏Modal 并刷新父页面
 * @param modalId Modal ID
 * @param tableId  表格ID
 */
function hideAndReload(modalId, tableId) {
    var id = tableId || "grid-table";
    $("#" + modalId).modal("hide");
    reloadCurrentPage(id);
}

/**
 * 重新载入当前页面
 * @param table
 */
function reloadCurrentPage(table) {
    var selector = table;
    if (table.indexOf("#") == -1) {
        selector = "#" + table;
    }
    var currentPage = $(selector).jqGrid("getGridParam", "page");
    $(selector).trigger("reloadGrid", [
        {page: currentPage}
    ]);
}


var progressOption =
{
    content: '<img src="../../res/images/progressBar_s.gif"/> 正在处理,请稍候....',
    zIndex: 1041
}

/**
 *
 * @param action    请求的action
 * @param formId    form表单ID
 * @param modalId   当前modalIDUser
 * @param confirmInfo   确认提示信息
 */
function postFormData(url, formId, modalId, confirmInfo, tableId) {
    //确认提示
    var table = tableId || "grid-table";
    var progress = dialog(progressOption);
    $.ajax({
        type: 'POST',
        datatype: 'json',
        url: url,
        contentType: "application/x-www-form-urlencoded; charset=utf-8", 
        data: $('#' + formId).serialize(),
        beforeSend: function () {
            progress.showModal()
        },
        complete: function () {
            progress.close().remove()
        },
        success: function (data) {
        	data=JSON.parse(data);
            progress.close().remove();
            if (data.statusCode == "200") {
                hideAndReload(modalId, table)
            } else if (data.statusCode == "300") {
                onException(data);
            }
        }
    });
}


/**
 * 删除
 * @param reqUrl 删除请求url
 */
function del(reqUrl, table) {
    confirm("确定删除此记录？", function () {
        $.ajax({
            type: 'get',
            url: reqUrl,
            success: function (data) {
                if (data.statusCode == "200") {
                    reloadCurrentPage(table);
                } else if (data.statusCode == "300") {
                    onException(data);
                }
            }
        });
    });
}

/**
 * 更改权限
 * @param url 更改权限url
 */
function alterPerm(url, table) {
    $.ajax({
        type: 'get',
        url: url,
        success: function (data) {
            if (data.statusCode == "200") {
                reloadCurrentPage(table);
            } else if (data.statusCode == "300") {
                onException(data);
            }
        }
    });
}

//获取行数据
function getRowData(rowId, selector) {
    var selectorId = selector || "#grid-table";
    var rowData = $(selectorId).jqGrid("getRowData", rowId);
    return rowData;
}
//获取ID
function getId(rowId, tableSelector) {
    var selector = tableSelector || "#grid-table";
    return getRowData(rowId, selector).id;
}

//获取状态
function getStatus(rowId, tableSelector) {
    var selector = tableSelector || "#grid-table";
    return getRowData(rowId, selector).status;
}
//获取代码
function getCode(rowId, tableSelector) {
    var selector = tableSelector || "#grid-table";
    return getRowData(rowId, selector).code;
}

//更新页脚图标
function updatePagerIcons(table) {
    var replacement =
    {
        'ui-icon-seek-first': 'icon-double-angle-left bigger-140',
        'ui-icon-seek-prev': 'icon-angle-left bigger-140',
        'ui-icon-seek-next': 'icon-angle-right bigger-140',
        'ui-icon-seek-end': 'icon-double-angle-right bigger-140'
    };
    $('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function () {
        var icon = $(this);
        var $class = $.trim(icon.attr('class').replace('ui-icon', ''));

        if ($class in replacement) icon.attr('class', 'ui-icon ' + replacement[$class]);
    })
}

Date.prototype.format = function (format) {
    var date = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S+": this.getMilliseconds()
    };
    if (/(y+)/i.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
    }
    for (var k in date) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1
                ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
        }
    }
    return format;
}

function timeFormatter(cellvalue, options, rowObject) {
    var time = new Date(parseFloat(cellvalue));
    return time.format("yyyy-MM-dd hh:mm:ss");
}


function dateTimeFormatter(cellvalue, options, rowObject) {
    var time = new Date(parseFloat(cellvalue));
    return time.format("MM-dd hh:mm:ss");
}

function subFormatter(cellvalue, options, rowObject) {
    return cellvalue.substr(0,60)+"...";
}




function dateFormatter(cellvalue, options, rowObject) {
    var time = new Date(parseFloat(cellvalue));
    return time.format("yyyy-MM-dd");
}
function sizeFormatter(cellvalue, options, rowObject) {
    var size ="";
    switch (cellvalue)
    {
        case 0:
            size="1k以下";
            break;
        case 1:
            size="1k到5k";
            break;
        case 2:
            size="5k到10k";
            break;
        case 3:
            size="10k到100k";
            break;
        case 4:
            size="100k到500k";
            break;
        case 5:
            size="500k以上";
            break;
    }
    return size;
}

/**
 * 枚举格式化
 * @param cellvalue 单元格值
 * @param options 选项
 * @param rowObject 行记录
 * @returns {*}
 */
function enumFormatter(cellvalue, options, rowObject) {
    var value = options.colModel.enums[cellvalue];
    if (value == null) {
        value = "";
    }
    return value;
}

/**
 * 归档枚举格式化
 * @param cellvalue 单元格值
 * @param options 选项
 * @param rowObject 行记录
 * @returns {*}
 */
function archiveEnumFormatter(cellvalue, options, rowObject) {
    var value = options.colModel.enums[cellvalue];
    if (value == null) {
        value = "";
    } else if (value == "开启") {
        value= "<span class='label label-success'>已开启</span>";
    } else {
        value = "<span class='label label-warning'>已关闭</span>";
    }
    return value;
}
function topicTypeEnumFormatter(cellvalue, options, rowObject) {
    var value = options.colModel.enums[cellvalue];
    if (value == null) {
        value = "";
    }
    return value;
}

function importanceEnumFormatter(cellvalue, options, rowObject){
    var value = options.colModel.enums[cellvalue];
    if (value == null) {
        value = "一般";
    }else if (value == "非常重要") {
        value= "<span class='label label-error'>非常重要</span>";
    } else if(value == "重要"){
        value = "<span class='label label-warning'>重要</span>";
    }else{
        value = "<span class='label label-success'>一般</span>";
    }
    return value;
}

/**
 * 密码格式化
 * @param cellvalue 单元格值
 * @param options 选项
 * @param rowObject 行记录
 * @returns {string}
 */
function passwordFormatter(cellvalue, options, rowObject) {
    if (!rowObject.password) {
        return cellvalue;
    }
    return "******";
}

/**
 * 布尔值格式化
 * @param cellvalue 单元格
 * @param options 选项
 * @param rowObject 行记录
 * @returns {*}
 */
function booleanFormatter(cellvalue, options, rowObject) {
    var enums = {"true": "是", "false": "否"};
    return enums[cellvalue];
}

//broker权限格式化
function permissionFormatter(cellvalue, options, rowObject) {
    if (cellvalue == 'FULL') {
        return "<span class='label label-success'>读写</span>";
    } else if (cellvalue == 'WRITE') {
        return "<span class='label label-warning'>只写</span>";
    } else if (cellvalue == 'READ') {
        return "<span class='label label-warning'>只读</span>";
    } else {
        return "<span class='label label-error'>无权限</span>";
    }
}

//broker复制方式格式化
function syncModeFormatter(cellvalue, options, rowObject) {
    if (cellvalue == 'ASYNCHRONOUS') {
        return "<span class='label label-warning'>异步</span>";
    } else {
        return "<span class='label label-success'>同步</span>";
    }
}

//broker启用禁用状态格式化
function brokerStatusFormatter(cellvalue, options, rowObject) {
    if (cellvalue == '1') {
        return "<span class='label label-success'>已启用</span>";
    } else {
        return "<span class='label label-warning'>已禁用</span>";
    }
}

//broker角色格式化
function roleFormatter(cellvalue, options, rowObject) {
    if (cellvalue == 'MASTER') {
        return "主";
    } else if (cellvalue == 'SLAVE') {
        return "从";
    } else if (cellvalue == 'BACKUP') {
        return "备份";
    }
}

//broker运行时角色格式化
function runtimeRoleFormatter(cellvalue, options, rowObject) {
    if (cellvalue == 'MASTER') {
        return "<span class='label label-success'>主节点</span>";
    } else if (cellvalue == 'SLAVE') {
        return "<span class='label label-warning'>从节点</span>";
    } else if (cellvalue == 'BACKUP') {
        return "<span class='label label-warning'>备份节点</span>";
    } else if (cellvalue == 'NONE') {
        return "<span class='label label-danger'>无</span>";
    } else if (cellvalue == '') {
        return "";
    } else {
        return '<img src="../../res/images/progressBar_s.gif"/>';
    }
}

/**
 * 按条件查询
 * @param cnd 条件
 * @param table   表格
 */
function findByCnd(cnd, table) {
    var postData = $("#" + table).jqGrid("getGridParam", "postData");
    $.extend(postData, cnd);

    $("#" + table).jqGrid("setGridParam", {search: true}).trigger("reloadGrid", [
        {page: 1}
    ]);
}


/**
 * 关键字查询
 * @param btn 查询按钮ID
 */
function initSearchByKeyWord(btn, table) {
    $("#" + btn).bind("click", function () {
        var keyWord = $("#keyWord").val();
        if (keyWord) {
            keyWord = keyWord.trim();
        }
        var cnd = {
            "keyWord": keyWord,
            "pause": false
        };

        //条件查询 jmq.js
        findByCnd(cnd, table);
    });
}
/**
 * 关键字查询
 * @param btn 查询按钮ID
 */
function initAppPausedByKeyWord(btn, table) {
    $("#" + btn).bind("click", function () {
        var cnd = {
            "pause": true
        };

        //条件查询 jmq.js
        findByCnd(cnd, table);
    });
}

/**
 * 初始化远程加载
 * @param id
 */
function initRemoteLoad(id) {
    $("#" + id).on('click', function (e) {
        e.preventDefault();
        var url = $(this).attr("remote-url");
        var modal = $(this).attr("remote-target");
        var $modal = $(modal);
        $modal.html(" ");
        $modal.load(url);
    });
}

//任务状态格式化
function taskItemStatusFormatter(cellvalue, options, rowObject) {
    if (cellvalue == 0) {
        return "新增";
    }  else if (cellvalue == 2) {
        return "执行中";
    } else if (cellvalue == 3) {
        return "执行成功";
    } else if (cellvalue == 4) {
        return "失败不重试";
    } else if (cellvalue == 5) {
        return "待审核";
    }
}

/**
 * 构造远程数据加载按钮
 * @param id 按钮ID
 * @param modal 页面显示模态框
 * @param url url
 * @param style 按钮样式
 * @param lable 按钮名称
 * @returns {string}
 */
function createRemoteModalUrl(id, modal, url, style, lable) {
    return "<li><a id='" + id + "' href='#" + modal + "' role='button' class='remoteModal' data-toggle='modal' " +
        "remote-url='" + url + "' remote-target='#" + modal + "'>" + "<i class='" + style + "'></i><span " +
        "class='menu-span'>" + lable + "</span></a></li>";
}

function confirm(title, okCallback) {
    dialog({
        title: '提示',
        content: title,
        okValue: '确定',
        ok: okCallback,
        cancelValue: '取消',
        cancel: function () {
        },
        zIndex: 1041  //大于bootstrap的1040
    }).width(320).showModal();
}

function errorAlert(error) {
    dialog({
        title: '错误提示',
        content: error,
        okValue: '确定',
        ok: function () {
        },
        zIndex: 1041     //大于bootstrap的1040
    }).width(320).show();
}

function msgAlert(info) {
    dialog({
        title: '提示',
        content: info,
        okValue: '确定',
        ok: function () {
        },
        zIndex: 1041     //大于bootstrap的1040
    }).width(320).show();
}


/**
 * 表单校验
 * @param form
 * @returns {*|jQuery}
 */
function isValid(form) {
    var selector = "#" + form;
    $(selector).validator({
        ignore: 'hidden'
    });
    $(selector).trigger("validate");
    return $(selector).isValid()
}

/**
 * 异常输出
 * @param data 后台返回的数据
 */
function onException(data) {
    var info = "操作失败，请稍候再试!";
    if (data.error) {
        info = data.error;
    } else if (data.message) {
        info = data.message;
    }
    errorAlert(info);
}

/**
 * 搜索框响应回车事件
 * @param event 事件
 * @param buttonId button的ID
 */
function onEnterClick(event,buttonId){
    if(event.keyCode==13){buttonId.click()}
}

/**
 * 设置未来(全局)的AJAX请求默认选项
 * 主要设置了AJAX请求遇到Session过期的情况
 */
$.ajaxSetup({
    type: 'POST',
    complete: function(xhr,status) {
        var sessionStatus = xhr.getResponseHeader('sessionstatus');
        if(sessionStatus == 'timeout') {
            dialog({
                title: '提示',
                content: '由于您未登陆或者长时间没有操作, session已过期, 请重新登录.',
                okValue: '确定',
                cancelValue: '确定',
                cancel: function () {
                	var top = getTopWinow();	
                	top.location.href = '../../index.jsp';           
                },
                zIndex: 1041  //大于bootstrap的1040
            }).width(320).showModal();
        }
    }
});

/**
 * 在页面中任何嵌套层次的窗口中获取顶层窗口
 * @return 当前页面的顶层窗口对象
 */
function getTopWinow(){
    var p = window;
    while(p != p.parent){
        p = p.parent;
    }
    return p;
}
//jQuery(function($){
//    // 备份jquery的ajax方法  
//    var _ajax=$.ajax;
//    // 重写ajax方法，先判断登录在执行success函数 
//    $.ajax=function(opt){
//    	var _success = opt && opt.success || function(a, b){};
//        var _opt = $.extend(opt, {
//        	success:function(data, textStatus){
//        		alert(1);
//        		// 如果后台将请求重定向到了登录页，则data里面存放的就是登录页的源码，这里需要找到data是登录页的证据(标记)
//        		if(data.indexOf('NotLogin') != -1) {
//        			 var top = getTopWinow();
//        			 top.location.href = '../../index.jsp'; 
//        			return;
//        		}
//        		_success(data, textStatus);  
//            }  
//        });
//        _ajax(_opt);
//    };
//});