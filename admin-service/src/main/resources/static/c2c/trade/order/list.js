/**
 * @author syf
 */
var User;
(function (User) {
    function UserTrade() {
        this.$status = $("#status");
        this.$type = $("#type");
        this.$payType = $("#payType");
        this.$productName = $("#productName");
        this.$mobile = $("#mobile");
        this.$blockchainStatus = $("#blockchainStatus");
        this.$select = $("#select");

        this.$orderTable = $("#orderTable");
    }

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
                    /*
                    {
                        orderable: false,
                        targets: 5,//最后1列
                        render: function (data, type, row, meta) {
                            var isEdit = [[${#authorization.expression('hasRole(''sys:dict:edit'')')}]];
                            var option = "";
                            if (isEdit) {
                                option += '<a href="#" id="update" name="update" data-id="' + row.id + '" >修改</a>'
                                    + '  <a href="#" id="delete" name="delete" data-id="' + row.id + '" >删除</a>';
                            }
                            option += ' <a href="#" id="check" name="check" data-id="' + row.id + '" >查看</a>';
                            return option;
                        }
                    }
                     */
                ],//第一列与最后一列禁止排序

                order: [ [0, null] ],//第一列排序图标改为默认
                ajax: {
                    url: urlPath + '/trade/order/dataList',
                    data: function (d) {
                        //添加额外的数据请求参数
                        d.status = curr.$status.val();
                        d.productName = curr.$productName.val();
                        d.mobile = curr.$mobile.val();
                        d.blockchainStatus = curr.$blockchainStatus.val();
                        d.payType = curr.$payType.val();
                        d.type = curr.$type.val();
                        console.log("查询参数:"+JSON.stringify(d));
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
                    {data: 'createDate', name: 'createDate'}
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