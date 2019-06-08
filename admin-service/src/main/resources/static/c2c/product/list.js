
/**
 * @author gb
 */
var User;
(function (User) {
    function UserAuth() {
        this.$name= $("#name");
        this.$status=$("#status");
        this.$orderDir =  $("#orderDir");
        this.$orderName =  $("#orderName");
        this.$productTable = $("#productTable");
        this.$select = $("#select");
        this.$select = $("#select");
    }
    // 交易类型
    var productStatue = {0:'待上架',1:'上架中',2:'已过期',3:"已下架"};
    function getObjValByKey(obj,key) {
        for(var v in obj){
            if (v == key) {
                return obj[key];
            }
        }
        return "";
    }


    UserAuth.prototype = {

        constructor: UserAuth,
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
                }, {
                    orderable: false,
                    targets: 8,
                    render: function (data, type, row, meta) {
                        var returnhtml = "";
                        if(row.status==0){
                            returnhtml += '<a href="javascript:void(0);" onclick="opproduct(this,1)"  name="oppro" data-id="' + row.id + '">上架</a>'
                            returnhtml += '<a href="javascript:void(0);" onclick="opproduct(this,2)"  name="oppro" data-id="' + row.id + '">下架</a>'
                        }else if(row.status==1){
                            returnhtml += '<a href="javascript:void(0);" onclick="opproduct(this,2)"  name="oppro" data-id="' + row.id + '">下架</a>'
                        }
                        returnhtml += '&nbsp;&nbsp;<a href="javascript:void(0);" onclick="opmove(this,1);"  name="movepro" data-id="' + row.id + '"  data-sort="'+row.sort+'">上移</a>';
                        returnhtml += '&nbsp;&nbsp;<a href="javascript:void(0);" onclick="opmove(this,-1);"  name="movepro" data-id="' + row.id + '" data-sort="\'+row.sort+\'">下移</a>';
                        returnhtml += '&nbsp;&nbsp;<a href="javascript:void(0);" onclick="look(this);"  name="movepro" data-id="' + row.id + '" data-sort="\'+row.sort+\'">查看</a>';
                        returnhtml += '&nbsp;&nbsp;<a href="javascript:void(0);" onclick="editTag(this);"  name="movepro" data-id="' + row.id + '" data-tag="'+row.tagIds+'" data-sort="\'+row.sort+\'">修改标签</a>';
                        return returnhtml;

                    }
                }],//第一列与最后一列禁止排序

                order: [ [0, null] ],//第一列排序图标改为默认
                ajax: {
                    url: urlPath + '/product/dataList',
                    data: function (d) {
                        //添加额外的数据请求参数
                        d.name = curr.$name.val();
                        d.status = curr.$status.val();
                        d.orderDir =  curr.$orderDir.val();
                        d.orderName =  curr.$orderName.val();
                    }
                },
                columns: [
                    {},
                    {data: 'name', name: 'name'},
                    {data: 'unitPrice', name: 'unitPrice'},
                    {data: 'tagNames', name: 'tagNames'},
                    {data: 'expirationEnd', name: 'expirationEnd'},
                    {data: 'updateDate', name: 'updateDate'},
                    {data: 'sort', name: 'sort'},
                    {data: 'status', name: 'status',render:function(data, type, row){return getObjValByKey(productStatue,row.status)}},
                    {}
                ],
                fnDrawCallback: function (){}

            };
            curr.$productTable = initOriginalTable('productTable', option);
            this.loadTable(this);
            this.bindBtn();

        },
        loadTable: function (event) {
            var curr = getNow(event);
            curr.$productTable.ajax.reload();
        },
        bindBtn: function () {
            var curr = this;
            this.$select.on("click", this, this.loadTable);
        }

    };

    function getNow(event) {
        return event.data ? event.data : event;
    }

    User.UserAuth = UserAuth;

})(User || (User = {}));
$(function () {
    var UserAuth = new User.UserAuth();
    UserAuth.init();
});

//上下架函数
function opproduct(op,value){
    var id = $(op).attr("data-id");
    if(value==2){//下架
        var isok = window.confirm("确定要下架商品吗？");
        if(!isok){
            return ;
        }
        $.post(urlPath +"/order/countOrder",{"productId":id},function(data){
            if(data.code!="10201"){
                alert(data.msg);
            }else{
                var num  = data.object;
                isok = true;
                if(num>0){
                    isok = window.confirm("下线商品可能会同时下线"+num+"个广告订单,确认下线吗？");
                }
                if(isok){
                    $.post(urlPath +"/product/opRacks",{"productId":id,"command":"down"},function(data){
                        if(data.code!="10401"){
                            alert(data.msg);
                        }else{
                            alert("下架成功");
                            var UserAuth = new User.UserAuth();
                            UserAuth.init();
                        }
                    });
                }
            }
        });
    }
    if(value==1){
        $.post(urlPath +"/product/opRacks",{"productId":id,"command":"up"},function(data){
            if(data.code!="10401"){
                alert(data.msg);
            }else{
                alert("上架成功");
                var UserAuth = new User.UserAuth();
                UserAuth.init();
            }
        });
    }

}
//操作
function opmove(op,command){
    var id = $(op).attr("data-id");
    $.post(urlPath +"/product/updataSort",{"id":id,"command":command},function(data){
        if(data.code!="10401"){
            alert(data.msg);
        }else{
            alert("调整成功");
            var UserAuth = new User.UserAuth();
            UserAuth.init();
        }
    });
}

function loadProduct() {
    $.post(urlPath+'/product/loadProduct',function(data){
        if(data.code!="10201"){
            alert(data.msg);
        }else{
            var num = data.object;
            alert("成功加载"+num+"个商品信息");
            var UserAuth = new User.UserAuth();
            UserAuth.init();
        }
    });

}

/**
 * 查询
 * @param button
 */
function look(button){
    $.ajax({
        url: urlPath+'/product/selectProduct',
        type: 'POST',
        dataType:'json',
        data:{id:$(button).attr("data-id")},
        success: function (data) {
            if(data.status=='success'){
                var modifyModal = $("#lookModal");
                modifyModal.modal({
                    keyboard: false
                })
                var form = modifyModal.find(".modalForm");
                var resultData = data.object;
                form.find("input[name='id']").val(resultData.id);
                form.find("input[name='name']").val(resultData.name);
                form.find("input[name='productImg']").val(resultData.productImg);
                form.find("input[name='unitPrice']").val(resultData.unitPrice);
                form.find("input[name='createDate']").val(resultData.createDate);
                form.find("input[name='expirationStart']").val(resultData.expirationStart);
                form.find("input[name='expirationEnd']").val(resultData.expirationEnd);
                form.find("input[name='tagNames']").val(resultData.tagNames);
                var extend = jQuery.parseJSON(resultData.extendInfo);
                form.find("textarea[name='receive']").text(extend.receive);
                form.find("textarea[name='ticketDesc']").text(extend.ticketDesc);
                form.find("textarea[name='remarks']").text(resultData.remarks);
                form.find("textarea[name='issuerName']").text(resultData.issuerName);
                form.find("textarea[name='issuerIcon']").text(resultData.issuerIcon);
                form.find("textarea[name='issuerAddress']").text(resultData.issuerAddress);
                form.find("textarea[name='issuerIntro']").text(resultData.issuerIntro);
                form.find("textarea[name='acceptName']").text(resultData.acceptName);
                form.find("textarea[name='acceptIcon']").text(resultData.acceptIcon);
                form.find("textarea[name='acceptAddress']").text(resultData.acceptAddress);
                form.find("textarea[name='acceptIntro']").text(resultData.acceptIntro);
                form.find("textarea[name='acceptMobile']").text(resultData.acceptMobile);
                form.find("textarea[name='assetCode']").text(resultData.assetCode);
                form.find("textarea[name='assetIssuer']").text(resultData.assetIssuer);
                form.find("textarea[name='contractAddress']").text(resultData.contractAddress);

            }else{
                //操作失败 弹出提示信息
                base_alert_time(data.msg,1000);
            }
        }
    });
}


function editTag(op){
    var ids = $(op).attr("data-tag");
    var arr = ids.split(",");
    $.post(urlPath+'/tag/listTag',function(data){
        if(data.code!="10401"){
            alert(data.msg);
        }else{
            var tagData = [];
            var tagArray = data.list;
            $.each(tagArray, function(i, n){
                tagData.push({id:n.id, text:n.name});
            });
            $("#product-id-change").select2({
                data: tagData,
                placeholder: '请选择'
            });
            $('#product-id-change').val(arr).trigger('change');
        }
    });
    var modifyModal = $("#modelTag");
    modifyModal.modal({
        keyboard: false
    });
    $("#modelTagForm input[name='id']").val($(op).attr("data-id"));

}
function subTag(){
    var value = $("#modelTagForm input[name='id']").val();
    var ids ="";
    var array = $("#product-id-change").select2("data");
    $.each(array,function(i,n){
        ids = ids + n.id;
        if(i!=array.length-1){
            ids = ids + ",";
        }
    });
    $.post(urlPath+'/product/updataTag',{"id":value,"tagIds":ids},function(data) {
        if(data.code!="10401"){
            alert(data.msg);
        }else{
           alert("更新成功");
            var UserAuth = new User.UserAuth();
            UserAuth.init();
        }
    });
}

