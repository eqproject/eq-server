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
        this.$mobile = $("#mobile");
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
                            option += ' <a href="#" id="detail" name="detail" onclick="detail(this);" data-id="' + row.id + '" >查看</a>';
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
                        d.mobile=curr.$mobile.val();
                        d.status=curr.$status.val();
                        d.type=curr.$type.val();
                        d.payType=curr.$payType.val();
                        d.beginCreateDate=curr.$beginCreateDate.val();
                        d.endCreateDate=curr.$endCreateDate.val();
                        console.log("参数："+d);
                    }
                },
                columns: [
                    {},
                    {data: 'adNo', name: 'adNo'},
                    {data: 'tradeNo', name: 'tradeNo'},
                    {data: 'productId', name: 'productId'},
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
                form.find("input[name='productId']").val(resultData.productId);
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