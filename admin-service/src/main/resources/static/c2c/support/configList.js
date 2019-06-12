
var stateObj = {0:'正常',1:'删除'};
var typeObj = {1:'交易支付时长(H)',2:'服务费比例',3:'短信风控',4:'广告关闭时长(天)'};
function getObjValByKey(obj,key) {
    for(var v in obj){
        if (v == key) {
            return obj[key];
        }
    }
    return "";
}
/**
 * @author gb
 */
var Support;
(function (Support) {
    function SupportInfo() {
        this.$type = $("#type");
        this.$state = $("#state");
        this.$select = $("#select");//绑定点击事件
    }

   SupportInfo.prototype = {

        constructor:SupportInfo,
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
                        targets: 4,
                        render: function (data, type, row, meta) {
                            option ='  <a href="javascript:void(0);" onclick="look(this);" id="look" name="look" data-id="' + row.id + '" >查看</a>';
                            return option;
                        }
                    }

                    ],//第一列与最后一列禁止排序

                order: [ [0, null] ],//第一列排序图标改为默认
                ajax: {
                    url: urlPath + '/support/config/dataList',
                    data: function (d) {
                        d.type = curr.$type.val();
                        d.state = curr.$state.val();
                    }
                },
                columns: [
                    {},
                    {data: 'type', name: 'type',render:function(data, type, row){return getObjValByKey(typeObj,row.type)}},
                    {data: 'remark', name: 'remark'},
                    {data: 'state', name: 'state',render:function(data, type, row){return getObjValByKey(stateObj,row.state)}},
                    {}
                ],
                fnDrawCallback: function () {
                    //加载完成后 初始化绑定事件
                    //checkbox全选
                }
            };
            curr.$supportTable = initOriginalTable('supportTable', option);
            this.loadTable(this);
            this.bindBtn();

        },
        loadTable: function (event) {
            var curr = getNow(event);
            curr.$supportTable.ajax.reload();
        },
        bindBtn: function () {
            var curr = this;
            this.$select.on("click", this, this.loadTable);
        }

    };

    function getNow(event) {
        return event.data ? event.data : event;
    }

   Support.SupportInfo =SupportInfo;
})(Support || (Support = {}));
$(function () {
    var SupportInfo = new Support.SupportInfo();
   SupportInfo.init();

});
function look(op){
    var id = $(op).attr("data-id");
    $.ajax({
        url: urlPath+'/support/config/getSystemConfig',
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
                form.find("input[name='remark']").val(resultData.remark);
                form.find("textarea[name='value']").text(resultData.value);
                var typeValue = getObjValByKey(typeObj,resultData.type);
                var stateValue = getObjValByKey(stateObj,resultData.state);
                form.find("input[name='type']").val(typeValue );
                form.find("input[name='status']").val(stateValue);
            }else{
                //操作失败 弹出提示信息
                base_alert_time(data.msg,1000);
            }
        }
    });
}