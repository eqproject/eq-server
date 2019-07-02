/**
 * @author syf
 */
var User;


function getObjValByKey(obj,key) {
    for(var v in obj){
        if (v == key) {
            return obj[key];
        }
    }
    return "";
}
// 订单状态
var orderStatusObj = {1:'等待确认',2:'转让成功',3:'取消转让',4:'转让失败'};


(function (User) {
    function UserTrade() {
        this.$transferNo = $("#transferNo");
        this.$status = $("#status");
        this.$beginCreateDate = $("#beginCreavalteDate");
        this.$endCreateDate = $("#endCreateDate");
        this.$select = $("#select");
        this.$orderTable = $("#orderTable");
    }





    UserTrade.prototype = {

        constructor: UserTrade,
        init: function () {
            var curr = this;

            //定义table显示字段
            var option = {
                ordering:false,
                columnDefs: [{
                    orderable: false,
                    targets: 0,//第1列
                    render: function (data, type, row, meta) {
                        return meta.row+1;
                    }
                },{
                        orderable: false,
                        targets: 5,//最后1列
                        render: function (data, type, row, meta) {
                            var option = "";
                            option +='<a href="javascript:void(0);" onclick="look(this);"   name="delete" data-id="' + row.id + '" >查看</a>';
                            option +='&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" onclick="getLog(this,\'transfer\');"   name="delete" data-id="' + row.id + '" >查看日志</a>'
                            return option;
                        }
                    }

                ],//第一列与最后一列禁止排序

                order: [ [0, null] ],//第一列排序图标改为默认
                ajax: {
                    url: urlPath + '/orderTransfer/dataList',
                    data: function (d) {
                        //添加额外的数据请求参数
                        d.transferNo = curr.$transferNo.val();
                        d.status = curr.$status.val();
                        if(curr.$beginCreateDate.val()!=""){
                            d.beginCreateDate = curr.$beginCreateDate.val();
                        }
                        if(curr.$endCreateDate.val()!=""){
                            d.endCreateDate = curr.$endCreateDate.val();
                        }
                    }
                },
                columns: [
                    {},
                    {data: 'transferNo', name: 'transferNo'},
                    {data: 'productNum', name: 'productNum'},
                    {data: 'status', name: 'status',render:function(data, type, row){return getObjValByKey(orderStatusObj,row.status)}},
                    {data: 'createDate', name: 'createDate'},
                    {}
                ],
                fnDrawCallback: function () {
                    //加载完成后 初始化绑定事件
                    //checkbox全选
                }
            };
            curr.$orderTable = initOriginalTable('orderTable', option);
            this.loadTable(this);
            this.bindBtn();

        },
        loadTable: function (event) {
            var curr = getNow(event);
            curr.$orderTable.ajax.reload();
        },
        bindBtn: function () {
            var curr = this;
            this.$select.on("click", this, this.loadTable);
        }

    };

    function getNow(event) {
        return event.data ? event.data : event;
    }

    User.UserTrade = UserTrade;
})(User || (User = {}));
$(function () {
    var UserTrade = new User.UserTrade();
    UserTrade.init();
});


function look(op){
    var id = $(op).attr("data-id");
    $.ajax({
        url: urlPath+'/orderTransfer/selectOrder',
        type: 'POST',
        dataType:'json',
        data:{id:id},
        success: function (data) {
            if(data.status=='success'){
                var modifyModal = $("#lookModal");
                modifyModal.modal({
                    keyboard: false
                })
                var form = modifyModal.find(".modalForm");
                var resultData = data.object;
                form.find("input[name='id']").val(resultData.id);
                form.find("input[name='transferNo']").val(resultData.transferNo);
                form.find("input[name='productName']").val(resultData.productName);
                form.find("input[name='userNickName']").val(resultData.userNickName);
                form.find("input[name='productNum']").val(resultData.productNum);
                var stateValue = getObjValByKey(orderStatusObj,resultData.status);
                form.find("input[name='orderStatus']").val(stateValue);
                form.find("input[name='toAddress']").val(resultData.toAddress);
                form.find("input[name='createDate']").val(resultData.createDate);
                form.find("textarea[name='remarks']").text(resultData.remarks);
            }else{
                //操作失败 弹出提示信息
                base_alert_time(data.msg,1000);
            }
        }
    });
}
function closeOrder(op){
    var id = $(op).attr("data-id");
    $.post(urlPath +"/order/close",{"orderId":id},function(data){
        if(data.code!="10401"){
            alert(data.msg);
        }else{
            alert("关闭成功!!");
            var UserTrade = new User.UserTrade();
            UserTrade.init();
        }
    });

}

function audit(data,op){
    var id = $(data).attr("data-id");
    $.post(urlPath +"/order/audit",{"orderId":id,"op":op},function(data){
        if(data.code!="10401"){
            alert(data.msg);
        }else{
            alert("审核成功!!");
            var UserTrade = new User.UserTrade();
            UserTrade.init();
        }
    });
}

//获取日志
function getLog(row,type) {
    var logModal = $("#logModal");

    logModal.modal({
        keyboard: false
    });

    //查询详情
    $.ajax({
        url: urlPath + '/log/' + type,
        type: 'POST',
        dataType: 'json',
        data: {id: $(row).attr("data-id")},
        success: function (data, status) {
            if (status == 'success') {
                var resultData = data[0];
                var logListHtml = "";
                logListHtml += "<table class=\"table\">";
                logListHtml += "<thead><tr><th>日期</th><th>旧状态</th><th>新状态</th><th>内容</th></tr></thead>";
                logListHtml += "<tbody>";
                data.forEach(v =>{
                    logListHtml += "<tr><td>"+v.createDate+"</td><td>"
                                + (getObjValByKey(orderStatusObj,v.newStatus) || "")+"</td><td>"
                                + (getObjValByKey(orderStatusObj,v.oldStatus) || "")+"</td><td>"
                                + (v.remarks || "") +"</td></tr>"
                });
                logListHtml += "</tbody>";
                logListHtml += "</table>";
                if(logListHtml == ""){
                    logListHtml = "暂无信息";
                }
                logModal.find("#logList").html(logListHtml);
            } else {
                //操作失败 弹出提示信息
                base_alert_time(data.msg, 1000);
            }
        }
    });
}