var tradeStatusObj = {0:'未处理',1:'已处理'};

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
        this.$appealNo = $("#appealNo");
        this.$mobile = $("#mobile");
        this.$tradeNo = $("#tradeNo");
        this.$status = $("#status");
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
                        targets: 7,//最后1列
                        render: function (data, type, row, meta) {
                            var option = "";
                            option += ' <a href="#" id="detail" name="detail" onclick="detail(this);" data-id="' + row.id + '" >详情</a>';
                            if(row.status==0){
                                option += ' <a href="#"   name="oporder" onclick="oporder(this);" data-id="' + row.id + '" >处理</a>';
                            }
                            return option;
                        }
                    }

                ],//第一列与最后一列禁止排序

                order: [ [0, null] ],//第一列排序图标改为默认
                ajax: {
                    url: urlPath + '/orderTradeAppeal/dataList',
                    data: function (d) {
                        //添加额外的数据请求参数
                        d.tradeNo=curr.$tradeNo.val();
                        d.appealNo =curr.$appealNo.val();
                        d.mobile = curr.$mobile.val();
                        d.status=curr.$status.val();
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
                    {data: 'appealNo', name: 'appealNo'},
                    {data: 'tradeNo', name: 'tradeNo'},
                    {data: 'userNickName', name: 'userNickName'},
                    {data: 'mobile', name: 'mobile'},
                    {data: 'status', name: 'status',render:function(data, type, row){return getObjValByKey(tradeStatusObj,row.status)}},
                    {data: 'crateTime', name: 'crateTime'},
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
        url: urlPath + '/orderTradeAppeal/selectOrderTrade',
        type: 'POST',
        dataType: 'json',
        data: {orderId: $(row).attr("data-id")},
        success: function (data, status) {
            if (data.status == 'success') {
                var form = modifyModal.find(".modalForm");
                form.find("[id='modifyModalLabel']").text("详情");
                var resultData = data.object;
                form.find("input[name='appealNo']").val(resultData.appealNo);
                form.find("input[name='tradeNo']").val(resultData.tradeNo);
                form.find("input[name='userNickName']").val(resultData.userNickName);
                form.find("input[name='mobile']").val(resultData.mobile);
                form.find("input[name='status']").val(getObjValByKey(tradeStatusObj,resultData.status));
                form.find("input[name='crateTime']").val(resultData.crateTime);
                form.find("input[name='updateTime']").val(resultData.updateTime);
                form.find("textarea[name='remark']").val(resultData.remark);
                form.find("input").attr("readonly","readonly");
            } else {
                //操作失败 弹出提示信息
                base_alert_time(data.msg, 1000);
            }
        }
    });
}

function oporder(row){
    var modifyModal = $("#auditModal");
    modifyModal.modal({
        keyboard: false
    });

    //查询详情
    $.ajax({
        url: urlPath + '/orderTradeAppeal/selectOrderTrade',
        type: 'POST',
        dataType: 'json',
        data: {orderId: $(row).attr("data-id")},
        success: function (data, status) {
            if (data.status == 'success') {
                var form = modifyModal.find(".auditForm");
                var resultData = data.object;
                form.find("input[name='id']").val(resultData.id);
                form.find("input[name='appealNo']").val(resultData.appealNo);
                form.find("input[name='tradeNo']").val(resultData.tradeNo);
                form.find("input[name='userNickName']").val(resultData.userNickName);
                form.find("input[name='mobile']").val(resultData.mobile);
                form.find("textarea[name='remark']").val(resultData.remark);
            } else {
                //操作失败 弹出提示信息
                base_alert_time(data.msg, 1000);
            }
        }
    });
}

function audit(){
    var modifyModal = $("#auditModal");
    var form = modifyModal.find(".auditForm");
    var id = form.find("input[name='id']").val();
    if(id<=0){
        base_alert_time("数据有误", 1000);
        return;
    }
    var remark =  form.find("textarea[name='remark']").val();
    if(remark.length<=0){
        base_alert_time("审核意见为空", 1000);
        return;
    }

    //查询详情
    $.ajax({
        url: urlPath + '/orderTradeAppeal/audit',
        type: 'POST',
        dataType: 'json',
        data: {orderId: id,remark:remark},
        success: function (data, status) {
            base_alert_time(data.msg, 1000);
            var UserTrade = new User.UserTrade();
            UserTrade.init();
        }
    });
}
