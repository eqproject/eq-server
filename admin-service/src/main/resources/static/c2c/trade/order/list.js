/**
 * @author syf
 */
// 交易类型
var tradeTypeObj = {1:'出售',2:'求购'};
// 支付类型
var tradePayTypeObj = {1:'微信',2:'支付宝'};
// 订单状态
var tradeStatusObj = {1:'待支付',2:'取消交易',3:'支付中',4:'支付成功',5:'支付失败',6:'关闭交易(支付超时)',7:'放券中',8:'放款中',9:'放款失败',10:'交易成功',11:'退款中',12:'退款成功'};

// 区块链状态
var blockchainStatusObj = {1:'区块链处理中',2:'区块链处理成功',3:'区块链处理失败'};

//交易订单日志状态
var tradeLogStatus ={1:'创建',2:'待支付',3:'取消',4:'下线取消',5:'支付成功',6:'支付失败',7:'区块链处理中',8:'区块链处理成功',9:'区块链处理失败'};

//支付日志状态
var paymentLogStatus = {1:'支付成功',2:'支付失败',3:'通知放款中',4:'通知放款成功',5:'通知放款失败'};
//退款日志状态
var refundLogStatus = {1:'退款中',2:'退款成功',3:'退款失败'};

var statusObj = {"trade":tradeLogStatus,"pay":paymentLogStatus,"refund":refundLogStatus};

function getObjValByKey(obj,key) {
    for(var v in obj){
        if (v == key) {
            return obj[key];
        }
    }
    return "";
}

var User;
(function (User) {
    function UserTrade() {
        this.$adNo = $("#adNo");
        this.$tradeNo = $("#tradeNo");
        this.$productName = $("#productName");
        this.$blockchainStatus = $("#blockchainStatus");
        this.$status = $("#status");
        this.$type = $("#type");
        this.$payType = $("#payType");
        this.$beginCreateDate = $("#beginCreateDate");
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
                },

                    {
                        orderable: false,
                        targets: 13,//最后1列
                        render: function (data, type, row, meta) {
                            var option = "";
                            option += ' <a href="#" id="detail" name="detail" onclick="detail(this);" data-id="' + row.id + '" >查看详情</a>';
                            option += ' <a href="#" id="detail" name="detail" onclick="getLog(this,\'trade\');" data-id="' + row.id + '" >交易订单日志</a>';
                            option += ' <a href="#" id="detail" name="detail" onclick="getLog(this,\'pay\');" data-id="' + row.id + '" >支付日志</a>';
                            option += ' <a href="#" id="detail" name="detail" onclick="getLog(this,\'refund\');" data-id="' + row.id + '" >退款日志</a>';
                            return option;
                        }
                    }

                ],//第一列与最后一列禁止排序

                order: [ [0, null] ],//第一列排序图标改为默认
                ajax: {
                    url: urlPath + '/trade/order/dataList',
                    data: function (d) {
                        //添加额外的数据请求参数
                        d.adNo = curr.$adNo.val();
                        d.tradeNo=curr.$tradeNo.val();
                        d.productName=curr.$productName.val();
                        d.blockchainStatus =curr.$blockchainStatus.val();
                        d.status=curr.$status.val();
                        d.type=curr.$type.val();
                        d.payType=curr.$payType.val();
                        d.beginCreateDate=curr.$beginCreateDate.val();
                        d.endCreateDate=curr.$endCreateDate.val();
                    }
                },
                columns: [
                    {},
                    {data: 'adNo', name: 'adNo'},
                    {data: 'tradeNo', name: 'tradeNo'},
                    {data: 'productName', name: 'productName'},
                    {data: 'orderNum', name: 'orderNum'},
                    {data: 'amount', name: 'amount'},
                    {data: 'type', name: 'type',render:function(data, type, row){return getObjValByKey(tradeTypeObj,row.type)}},
                    {data: 'status', name: 'status',render:function(data, type, row){return getObjValByKey(tradeStatusObj,row.status)}},
                    {data: 'blockchainStatus', name: 'blockchainStatus',render:function(data, type, row){return getObjValByKey(blockchainStatusObj,row.blockchainStatus)}},
                    {data: 'payType', name: 'payType',render:function(data, type, row){return getObjValByKey(tradePayTypeObj,row.payType)}},
                    {data: 'finishTime', name: 'finishTime'},
                    {data: 'description', name: 'description'},
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


function detail(row) {
    var modifyModal = $("#modifyModal");

    modifyModal.modal({
        keyboard: false
    });

    //查询详情
    $.ajax({
        url: urlPath + '/trade/order/selectOrderTrade',
        type: 'POST',
        dataType: 'json',
        data: {id: $(row).attr("data-id")},
        success: function (data, status) {
            if (data.status == 'success') {
                var form = modifyModal.find(".modalForm");
                form.find("[id='modifyModalLabel']").text("详情");
                var resultData = data.list[0];
                form.find("input[name='adNo']").val(resultData.adNo);
                form.find("input[name='tradeNo']").val(resultData.tradeNo);
                form.find("input[name='productName']").val(resultData.productName);
                form.find("input[name='orderNum']").val(resultData.orderNum);
                form.find("input[name='amount']").val(resultData.amount);
                form.find("input[name='type']").val(getObjValByKey(tradeTypeObj,resultData.type));
                form.find("input[name='status']").val(getObjValByKey(tradeStatusObj,resultData.status));
                form.find("input[name='blockchainStatus']").val(getObjValByKey(blockchainStatusObj,resultData.blockchainStatus));
                form.find("input[name='payType']").val(getObjValByKey(tradePayTypeObj,resultData.payType));
                form.find("input[name='finishTime']").val(resultData.finishTime);
                form.find("input[name='description']").val(resultData.description);
                form.find("input[name='createDate']").val(resultData.createDate);
                form.find("input").attr("readonly","readonly");
            } else {
                //操作失败 弹出提示信息
                base_alert_time(data.msg, 1000);
            }
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
                data.forEach(v =>{
                    logListHtml += "<p class=\"text-info\">"+ v.createDate
                                +"  -  "+ (getObjValByKey(statusObj[type],v.newStatus) || "") +" "
                                + (getObjValByKey(statusObj[type],v.oldStatus) || "")
                                +" " + (v.remarks || "") +"</p>";
                });
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
                                + (getObjValByKey(statusObj[type],v.oldStatus) || "")+"</td><td>"
                                + (getObjValByKey(statusObj[type],v.newStatus) || "")+"</td><td>"
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