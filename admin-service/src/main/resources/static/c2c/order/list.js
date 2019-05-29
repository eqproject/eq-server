/**
 * @author syf
 */
var User;
(function (User) {
    function UserTrade() {
        this.$orderNo = $("#orderNo");
        this.$productCode = $("#productCode");
        this.$productName = $("#productName");
        this.$status = $("#status");
        this.$type = $("#type");

        this.$beginCreateDate = $("#beginCreateDate");
        this.$endCreateDate = $("#endCreateDate");
        this.$select = $("#select");
        this.$orderTable = $("#orderTable");
    }

    // 交易类型
    var orderTypeObj = {1:'出售',2:'求购'};
    // 订单状态
    var orderStatusObj = {1:'创建',2:'取消',3:'交易中',4:'已完成'};

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
                },{
                        orderable: false,
                        targets: 10,//最后1列
                        render: function (data, type, row, meta) {
                            var option = "";
                            if(row.status ==1){
                                option +=   '  <a href="javascript:void(0);" onclick="down(this);" id="delete" name="delete" data-id="' + row.id + '" >下架</a>';
                            }

                            //option += ' <a href="#" id="check" name="check" data-id="' + row.id + '" >查看</a>';
                            return option;
                        }
                    }

                ],//第一列与最后一列禁止排序

                order: [ [0, null] ],//第一列排序图标改为默认
                ajax: {
                    url: urlPath + '/order/userOrder/dataList',
                    data: function (d) {
                        //添加额外的数据请求参数
                        d.orderNo = curr.$orderNo.val();
                        d.status = curr.$status.val();
                        d.productName = curr.$productName.val();
                        d.productCode = curr.$productCode.val();
                        if(curr.$beginCreateDate.val()!=""){
                            d.beginCreateDate = curr.$beginCreateDate.val();
                        }
                        if(curr.$endCreateDate.val()!=""){
                            d.endCreateDate = curr.$endCreateDate.val();
                        }


                        d.type = curr.$type.val();
                    }
                },
                columns: [
                    {},
                    {data: 'orderNo', name: 'orderNo'},
                    {data: 'productName', name: 'productName'},
                    {data: 'productCode', name: 'productCode'},
                    {data: 'productNum', name: 'productNum'},
                    {data: 'amount', name: 'amount'},
                    {data: 'type', name: 'type',render:function(data, type, row){return getObjValByKey(orderTypeObj,row.type)}},
                    {data: 'status', name: 'status',render:function(data, type, row){return getObjValByKey(orderStatusObj,row.status)}},


                    {data: 'createByName', name: 'createByName'},
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

//操作
function down(op){
    var id = $(op).attr("data-id");

    $.post(urlPath +"/order/userOrder/downOrder",{"id":id},function(data){
        if(data.code!="10401"){
            alert(data.msg);
        }else{
            alert("下架成功!!");
            $("#select").click();
        }
    });
}