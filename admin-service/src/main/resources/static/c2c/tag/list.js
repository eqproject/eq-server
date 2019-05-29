/**
 * @author kaka
 */

function getObjValByKey(obj,key) {
    for(var v in obj){
        if (v == key) {
            return obj[key];
        }
    }
    return "";
}

var Tag;
(function (Tag) {
    function TagAuth() {
        this.$name= $("#name");
        this.$status=$("#status");
        this.$select = $("#select");
        this.$orderDir =  $("#orderDir");
        this.$orderName =  $("#orderName");
        this.$tagTable = $("#tagTable");
        this.$select = $("#select");
    }
    // 交易类型
    var TagStatueArray = {0:'启用',1:'禁用'};
    TagAuth.prototype = {

        constructor: TagAuth,
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
                    targets: 6,
                    render: function (data, type, row, meta) {
                        var returnhtml = "";
                        if(row.status==0){
                            returnhtml += '<a href="javascript:void(0);" onclick="opTag(this)"   data-id="' + row.id + '">禁用</a>'
                        }else{
                            returnhtml += '<a href="javascript:void(0);" onclick="opTag(this)"    data-id="' + row.id + '">启用</a>'
                        }
                        returnhtml += '&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" onclick="tagEdit(this)"    id="update" name="update" data-id="' + row.id + '" >修改</a>'

                        return returnhtml;

                    }
                }],//第一列与最后一列禁止排序

                order: [ [0, null] ],//第一列排序图标改为默认
                ajax: {
                    url: urlPath + '/tag/dataList',
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
                    {data: 'createName', name: 'createName'},
                    {data: 'createDate', name: 'createDate'},
                    {data: 'updateDate', name: 'updateDate'},
                    {data: 'status', name: 'status',render:function(data, type, row){return getObjValByKey(TagStatueArray,row.status)}},
                    {}
                ],//表格按钮事件
                fnDrawCallback: function(){}
            };
            curr.$tagTable = initOriginalTable('tagTable', option);
            this.loadTable(this);
            this.bindBtn();

        },
        loadTable: function (event) {
            var curr = getNow(event);
            curr.$tagTable.ajax.reload();
        },
        bindBtn: function () {
            var curr = this;
            this.$select.on("click", this, this.loadTable);
        }

    };

    function getNow(event) {
        return event.data ? event.data : event;
    }

    Tag.TagAuth = TagAuth;
})(Tag || (Tag = {}));
$(function () {
    var tagAuth = new Tag.TagAuth();
    tagAuth.init();
});

//操作
function opTag(op){
    var id = $(op).attr("data-id");
    $.post(urlPath +"/tag/opstate",{"id":id},function(data){
        if(data.code!="10401"){
            alert(data.msg);
        }else{
            alert("修改成功");
            var tagAuth = new Tag.TagAuth();
            tagAuth.init();
        }
    });
}

/**
 * 修改标签
 * @param button
 */
function tagEdit(button){
    //ajax 查询后台数据 放到模态框 修改模态框的保存按钮属性
    $.ajax({
        url: urlPath+'/tag/selectTag',
        type: 'POST',
        dataType:'json',
        data:{tagId:$(button).attr("data-id")},
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

                //修改模态框的保存按钮名称,属性 type=save 新增 update 修改
                modifyModal.find("[id='save']").data("type","update");
                modifyModal.find("[id='save']").text("修改");
                modifyModal.find("[id='modifyModalLabel']").text("标签修改");

                //模态框按钮事件 保存or修改
                saveOrUpdate(modifyModal);
            }else{
                //操作失败 弹出提示信息
                base_alert_time(data.msg,1000);
            }
        }
    });
}