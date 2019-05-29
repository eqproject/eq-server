/** dataTable公用操作*/
$.fn.dataTable.ext.errMode = 'none';//抛出错误异常
var dataTableOption = {
    serverSide: true,
    //"dom": '<"top"f >rt<"bottom"ilp><"clear">',//dom定位
    dom: '<"pull-left"l>rtip',//自定义显示项
    order: [ [0, null] ],//第一列排序图标改为默认
    oLanguage: {//国际语言转化
        oAria: {
            sSortAscending: ' - click/return to sort ascending',
            sSortDescending: ' - click/return to sort descending'
        },
        sLengthMenu: '显示 _MENU_ 记录',
        sZeroRecords: '对不起，查询不到任何相关数据',
        sEmptyTable: '未有相关数据',
        sLoadingRecords: '正在加载数据-请等待...',
        sInfo: '当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录。',
        sInfoEmpty: '当前显示0到0条，共0条记录',
        sInfoFiltered: '（数据库中共为 _MAX_ 条记录）',
        sProcessing: '<img src="../resources/user_share/row_details/select2-spinner.gif"/> 正在加载数据...',
        sSearch: '模糊查询：',
        sUrl: '',
        //多语言配置文件，可将oLanguage的设置放在一个txt文件中，例：Javascript/datatable/dtCH.txt
        oPaginate: {
            sFirst: '首页',
            sPrevious: ' 上一页 ',
            sNext: ' 下一页 ',
            sLast: ' 尾页 '
        }
    }
};

function initOriginalTable(tableName,option){
    var newOption = $.extend({},dataTableOption,option);
    table = $("#"+tableName)
        .on( 'error.dt', function ( e, settings, techNote, message ) {
            //throw e;
        }).DataTable(newOption);
    return table;
}

/** modal 公共处理 */
//解决模态框背景色越来越深的问题
function setModalsAndBackdropsOrder() {
    var modalZIndex = 1040;
    var last = null;
    $('.modal.in').each(function(index) {
        last = index;
        var $modal = $(this);
        modalZIndex++;
        $modal.css('zIndex', modalZIndex);
        $('.modal-backdrop.in').eq(index).removeClass('in');
    });
    $('.modal-backdrop').eq(last).addClass('in');
}

//TODO 是否支持拖拽
//div 区域里面modal 处理 loadLocation 处理位置 call 回调函数
function modalRest(div,call,loadLocation){
    var location = "mainDiv";
    if(loadLocation!=undefined){
        location = loadLocation;
    }

    div.find(".modal").each(function (i,one) {
        //把div中的modal 移动到div 的末尾 如果有则替换掉
        var modalId = $(one).attr("id");
        if($('#'+location).find("div[id='"+modalId+"']").length>0){
            $('#'+location).find("div[id='"+modalId+"']").remove();
        }
        $(one).appendTo($('#'+location));

        var originalModal = $(one).clone();
        $(one).on('hidden.bs.modal',function(event) {
                var myClone = originalModal.clone(true);
                myClone.replaceAll($(this));
                modalRest($(myClone),call,location);
                if(typeof call == 'function'){
                    call();
                }
                setModalsAndBackdropsOrder();
            }
        ).on('shown.bs.modal', function(event) {
            setModalsAndBackdropsOrder();
        })
    })
    if(div.hasClass('modal')){
        var originalModal = div.clone();
        div.on('hidden.bs.modal',function(event) {
                var myClone = originalModal.clone(true);
                myClone.replaceAll($(this));
                modalRest($(myClone),call,location);
                if(typeof call == 'function'){
                    call();
                }
                setModalsAndBackdropsOrder();
            }
        ).on('shown.bs.modal', function(event) {
            setModalsAndBackdropsOrder();
        })
    }
}

/** 弹框公用操作 */
//弹出普通关闭，content:弹出内容
function base_alert(content){
    $.alert({
        title: false, // hides the title.
        animation: 'zoom',
        closeAnimation: 'scale',
        content: content,
        buttons: {
            "关闭": {
                btnClass: 'btn-blue'
            }
        }
    });
}
//弹出普通时间限制的关闭框，content:弹出内容,time 时间单位秒
function base_alert_time(content,time){
    $.alert({
        title: false, // hides the title.
        animation: 'zoom',
        closeAnimation: 'scale',
        autoClose: '关闭|'+time,
        content: content,
        buttons: {
            "关闭": {
                btnClass: 'btn-blue',
            }
        }
    });
}
//弹出带有回调函数的关闭框，content:弹出内容,call回调函数
function base_alert_success(content,call){
    $.alert({
        title: false, // hides the title.
        animation: 'zoom',
        closeAnimation: 'scale',
        autoClose: '关闭',
        content: content,
        buttons: {
            "关闭": {
                btnClass: 'btn-blue',
                action: function(){
                    call();
                }
            }
        }
    });
}
//弹出带有回调函数和时间限制关闭框，content:弹出内容,call回调函数
function base_alert_success_time(content,time,call){
    $.alert({
        title: false, // hides the title.
        animation: 'zoom',
        closeAnimation: 'scale',
        autoClose: '关闭|'+time,
        content: content,
        buttons: {
            "关闭": {
                btnClass: 'btn-blue',
                action: function(){
                    call();
                }
            }
        }
    });
}

function base_confirm(content,call,cancle) {
    $.confirm({
        title: false,
        content: content,
        buttons: {
            '取消': {
                btnClass: 'btn-blue',
                action:	function () {
                    if(cancle !=undefined && typeof call == 'function'){
                        cancle();
                    }
                }
            },
            '确定': {
                btnClass: 'btn-blue',
                action:	function () {
                    call();
                }
            }
        }
    });
}

/** 公用加载页面方法 在某区域 加载url 返回的页面 */
function loadPage(url,div){
    div.load(url,function(response,status,xhr){
        if(status=="success"){
            var sessionstatus = xhr.getResponseHeader("sessionstatus"); // 通过XMLHttpRequest取得响应头，sessionstatus
            if (sessionstatus == "timeout") {
                // 如果超时就处理 ，指定要跳转的页面
                window.location.replace(urlPath+"/login");
            }
            //页面加载完之后 进行一些默认处理
            modalRest(div,null);
        }
    });
}

/** 页面缓存数据*/
var urlPath = "";//请求路径
dictionary = {};
function refreshDictionary() {
    //加载数据字典
    $.ajax({
        url: urlPath+'/sys/sysDict/getDictionary',
        type: 'POST',
        success: function (data, status) {
            dictionary = data;
        }
    });
}

//TODO from 弹层的自动提交

/* ajax 处理 */
//TODO 全局遮罩层处理
var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
$(document).ajaxSend(function(e, xhr, options) {
    xhr.setRequestHeader(header, token);
});
$(document).ajaxStart( function(event, jqXHR, options){
    /*index = layer.load(2, {
        shade: [0.2,'#ddd'] //0.1透明度的白色背景
    });*/
});
$(document).ajaxComplete( function(event, jqXHR, options){
    /*layer.close(index);*/
});
$.ajaxSetup({
    contentType:"application/x-www-form-urlencoded;charset=utf-8",
    error:function (xhr,e) {
        if(xhr.status == 403){
            window.location.replace(urlPath+"/login");
        }
    },
    complete:function(xhr,options){
        //通过XMLHttpRequest取得响应头，sessionstatus，
        var sessionstatus = xhr.getResponseHeader("sessionstatus");
        if ("timeout" === sessionstatus) {
            window.location.replace(urlPath+"/login");
        }
    }
});


/** 对象比较函数
 *  sort 排序方式
 */
function compareFun(name,sort){
    return function(o, p){
        var a, b;
        if (typeof o === "object" && typeof p === "object" && o && p) {
            a = o[name];
            b = p[name];
            if (a === b) {
                return 0;
            }
            if (typeof a === typeof b) {
                if(sort == 'asc'){
                    return a < b ? -1 : 1;
                }else if(sort == 'desc'){
                    return a < b ? 1 : -1;
                }
            }
            return typeof a < typeof b ? -1 : 1;
        }else {
            throw ("error");
        }
    }
}

/**
 * 文件上传控件
 */
var filePlugOptionSingle = {
    language: 'zh', //设置语言
    uploadAsync: false, //默认异步上传
    showUpload:false, //是否显示上传按钮
    showRemove :true, //显示移除按钮
    showBrowse :true, //显示游览按钮
    showPreview :false, //是否显示预览
    showCaption:true,//是否显示标题
    browseClass:"btn btn-primary", //按钮样式
    dropZoneEnabled: false,//是否显示拖拽区域
    autoReplace:true,//自动替换
    msgPlaceholder:"请选择",
    previewFileIconSettings: {
        'docx': '<i class="fa fa-file-word-o text-primary" ></i>',
        'xlsx': '<i class="fa fa-file-excel-o text-success"></i>',
        'pptx': '<i class="fa fa-file-powerpoint-o text-danger"></i>',
        'jpg': '<i class="fa fa-file-photo-o text-warning"></i>',
        'pdf': '<i class="fa fa-file-pdf-o text-danger"></i>',
        'zip': '<i class="fa fa-file-archive-o text-muted"></i>',
        'doc': '<i class="fa fa-file-word-o text-primary"></i>',
        'xls': '<i class="fa fa-file-excel-o text-success"></i>',
        'ppt': '<i class="fa fa-file-powerpoint-o text-danger"></i>',
        'pdf': '<i class="fa fa-file-pdf-o text-danger"></i>',
        'zip': '<i class="fa fa-file-archive-o text-muted"></i>',
        'htm': '<i class="fa fa-file-code-o text-info"></i>',
        'txt': '<i class="fa fa-file-text-o text-info"></i>',
        'mov': '<i class="fa fa-file-movie-o text-warning"></i>',
        'mp3': '<i class="fa fa-file-audio-o text-warning"></i>',
        'jpg': '<i class="fa fa-file-photo-o text-danger"></i>',
        'gif': '<i class="fa fa-file-photo-o text-muted"></i>',
        'png': '<i class="fa fa-file-photo-o text-primary"></i>'
    },
    //minImageWidth: 50, //图片的最小宽度
    //minImageHeight: 50,//图片的最小高度
    //maxImageWidth: 1000,//图片的最大宽度
    //maxImageHeight: 1000,//图片的最大高度
    //maxFileSize:0,//单位为kb，如果为0表示不限制文件大小
    //minFileCount: 0,
    maxFileCount:1, //表示允许同时上传的最大文件个数
    enctype:'multipart/form-data',
    validateInitialCount:true,
    previewFileIcon: "<iclass='glyphicon glyphicon-king'></i>",
    msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
    msgFileSecured:"安全限制禁止读取文件{name}",
    msgFileNotReadable:"文件{name}不可读",
    msgInvalidFileName:"文件名{name}中的字符无效或不受支持",
    msgInvalidFileType:"文件{name}的类型无效。只支持{types}文件",
    msgInvalidFileExtension:"文件{name}的扩展名无效。只支持{extensions}文件",
    msgUploadError:"上传出错",
    msgValidationError:"文件验证失败",
    msgAjaxError:"上传出错",
    msgFileNotFound:"上传出错",
};

function initFilePlugSingle(inputName,option){
    var newOption = $.extend({},filePlugOptionSingle,option);
    filePlug = $("#"+inputName).fileinput(newOption);
    return filePlug;
}

function fileSizeUnit(bytes) {
    var i = Math.floor(Math.log(bytes) / Math.log(1024)),
        sizes = ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];
    return (bytes / Math.pow(1024, i)).toFixed(2) * 1 + ' ' + sizes[i];
}

function fileSizeLong(size,unit) {
    var sizes = ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];
    if (size != null || size != ''){
        for (var i = 0; i <= sizes.length; i++) {
            if (unit == sizes[i]) {
                return size;
            } else {
                size = size * 1024;
            }
        }
    }
    return size;
}

function tranformName(str){
    var strArr=str.split('_');
    for(var i=1;i<strArr.length;i++){
        strArr[i]=strArr[i].charAt(0).toUpperCase()+strArr[i].substring(1);
    }
    return strArr.join('');
}
// modal 与select 不能输入问题
$.fn.modal.Constructor.prototype.enforceFocus = function () { };
/*
 * 禁用 form 回车提交事件
 */
document.onkeypress = function(){
    if(event.keyCode == 13) {
        return false;
    }
}
$(function() {
    $("input").keypress(function (e) {
        var keyCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
        if (keyCode == 13) {
            for (var i = 0; i < this.form.elements.length; i++) {
                if (this == this.form.elements[i]) break;
            }
            i = (i + 1) % this.form.elements.length;
            this.form.elements[i].focus();
            return false;
        } else {
            return true;
        }
    })
})