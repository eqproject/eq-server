/**
 * @author gb
 */
var User;
(function (User) {
    function UserAuth() {
        this.$name= $("#name");
        this.$code= $("#code");
        this.$brand=$("#brand");
        this.$status=$("#status");
        this.$orderDir =  $("#orderDir");
        this.$orderName =  $("#orderName");
        this.$productTable = $("#productTable");
        this.$select = $("#select");
        this.$select = $("#select");
    }
    // 交易类型
    var productStatue = {0:'待上线',1:'上线中',2:'已下线'};
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
                    targets: 9,
                    render: function (data, type, row, meta) {
                        var returnhtml = "";
                        if(row.status==0){
                            returnhtml += '<a href="javascript:void(0);" onclick="opproduct(this,1)"  name="oppro" data-id="' + row.id + '">上线</a>'
                            returnhtml += '<a href="javascript:void(0);" onclick="opproduct(this,2)"  name="oppro" data-id="' + row.id + '">下线</a>'
                        }else if(row.status==1){
                            returnhtml += '<a href="javascript:void(0);" onclick="opproduct(this,2)"  name="oppro" data-id="' + row.id + '">下线</a>'
                        }
                        returnhtml += '&nbsp;&nbsp;<a href="javascript:void(0);" onclick="opmove(this,1);"  name="movepro" data-id="' + row.id + '"  data-sort="'+row.sort+'">上移</a>';
                        returnhtml += '&nbsp;&nbsp;<a href="javascript:void(0);" onclick="opmove(this,-1);"  name="movepro" data-id="' + row.id + '" data-sort="\'+row.sort+\'">下移</a>';
                        returnhtml += '&nbsp;&nbsp;<a href="javascript:void(0);" onclick="tagEdit(this);"  name="movepro" data-id="' + row.id + '" data-sort="\'+row.sort+\'">编辑</a>';
                        return returnhtml;

                    }
                }],//第一列与最后一列禁止排序

                order: [ [0, null] ],//第一列排序图标改为默认
                ajax: {
                    url: urlPath + '/product/dataList',
                    data: function (d) {
                        //添加额外的数据请求参数
                        d.name = curr.$name.val();
                        d.code = curr.$code.val();
                        d.brand=curr.$brand.val();
                        d.status = curr.$status.val();
                        d.orderDir =  curr.$orderDir.val();
                        d.orderName =  curr.$orderName.val();
                    }
                },
                columns: [
                    {},
                    {data: 'name', name: 'name'},
                    {data: 'code', name: 'code'},
                    {data: 'tagNames', name: 'tagNames'},
                    {data: 'brand', name: 'brand'},
                    {data: 'createUserName', name: 'createUserName'},
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
    loadTag();
});

//自定义函数
function opproduct(op,value){
    var id = $(op).attr("data-id");
    if(value==2){
        alert("确定要下线吗？");
    }
    alert(id+"==="+value);
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

/**
 * 查询
 * @param button
 */
function tagEdit(button){
    $.ajax({
        url: urlPath+'/product/selectProduct',
        type: 'POST',
        dataType:'json',
        data:{id:$(button).attr("data-id")},
        success: function (data, status) {
            if(data.status=='success'){
                var modifyModal = $("#modifyModal");
                modifyModal.modal({
                    keyboard: false
                })
                var form = modifyModal.find(".modalForm");
                var resultData = data.object;
                form.find("input[name='id']").val(resultData.id);
                form.find("input[name='name']").val(resultData.name);
                form.find("input[name='code']").val(resultData.code);
                form.find("input[name='productImg']").val(resultData.productImg);
                form.find("input[name='brand']").val(resultData.brand);
                form.find("input[name='brandImg']").val(resultData.brandImg);
                form.find("textarea[name='brandDescription']").val(resultData.brandDescription);
                form.find("input[name='brandTele']").val(resultData.brandTele);
                form.find("input[name='unitPrice']").val(resultData.unitPrice);
                form.find("textarea[name='description']").text(resultData.description);
                form.find("textarea[name='receive']").text(resultData.receive);
                form.find("select[name='status']").val(resultData.status).trigger("change");
                //form.find("input[name='status']").val(resultData.status);
                form.find("input[name='expirationStart']").val(resultData.expirationStart);
                form.find("input[name='expirationEnd']").val(resultData.expirationEnd);
                form.find("input[name='tagNames']").val(resultData.tagNames);
                form.find("input[name='tagIds']").val(resultData.tagIds);
                $("#tagNames").combobox('setValues', [resultData.tagIds]);
                form.find("input[name='remarks']").val(resultData.remarks);

                //修改模态框的保存按钮名称,属性 type=save 新增 update 修改
                modifyModal.find("[id='save']").data("type","update");
                modifyModal.find("[id='save']").text("修改&查看");
                modifyModal.find("[id='modifyModalLabel']").text("商品修改");

                //模态框按钮事件 保存or修改
                saveOrUpdate(modifyModal);
            }else{
                //操作失败 弹出提示信息
                base_alert_time(data.msg,1000);
            }
        }
    });
}

