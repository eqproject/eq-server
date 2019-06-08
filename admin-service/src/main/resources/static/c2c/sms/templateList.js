/**
 * @author gb
 */
var Sms;
(function (Sms) {
    function SmsInfo() {
        this.$code = $("#code");
        this.$name = $("#name");
        this.$limitDay = $("#limitDay");
        this.$type = $("#type");
        this.$select = $("#select");//绑定点击事件

    }

    var stateObj = {1:'未认证',2:'已认证'};
    var delObj = {0:'正常',1:'已删除'};
    var typeObj = {1:'注册验证码',2:'发布求购广告成功通知',3:'发布出售广告成功通知',4:'购买成功收货通知',5:'转出成功通知',6:'通知买家付款'};
    function getObjValByKey(obj,key) {
        for(var v in obj){
            if (v == key) {
                return obj[key];
            }
        }
        return "";
    }

    SmsInfo.prototype = {

        constructor: SmsInfo,
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
                            option ='  <a href="javascript:void(0);" onclick="check(this);" id="check" name="check" data-id="' + row.id + '" >编辑</a>';
                            return option;
                        }
                    }

                    ],//第一列与最后一列禁止排序

                order: [ [0, null] ],//第一列排序图标改为默认
                ajax: {
                    url: urlPath + '/sms/template/dataList',
                    data: function (d) {
                        //添加额外的数据请求参数
                    }
                },
                columns: [
                    {},
                    {data: 'code', name: 'code'},
                    {data: 'name', name: 'name'},
                    {data: 'limitDay', name: 'limitDay'},
                    {data: 'type', name: 'type',render:function(data, type, row){return getObjValByKey(typeObj,row.type)}},
                    {data: 'content', name: 'content'},
                    {}
                ],
                fnDrawCallback: function () {
                    //加载完成后 初始化绑定事件
                    //checkbox全选
                }
            };
            curr.$templateTable = initOriginalTable('templateTable', option);
            this.loadTable(this);
            this.bindBtn();

        },
        loadTable: function (event) {
            var curr = getNow(event);
            curr.$authTable.ajax.reload();
        },
        bindBtn: function () {
            var curr = this;
            this.$select.on("click", this, this.loadTable);
        }

    };

    function getNow(event) {
        return event.data ? event.data : event;
    }

    Sms.SmsInfo = SmsInfo;
})(Sms || (Sms = {}));
$(function () {
    var SmsInfo = new Sms.SmsInfo();
    SmsInfo.init();
});

function check(row) {
    var modifyModal = $("#modifyModal");
    modifyModal.modal({
        keyboard: false
    });

    modifyModal.find("[id='save']").on("click",function(){
        var id = modifyModal.find("#id").val();
        var limitDay = modifyModal.find("#limitDay").val();
        var name = modifyModal.find("#name").val();
        var content = modifyModal.find("#content").val();
        //保存数据
        $.ajax({
                url: urlPath + '/sms/template/modify',
                type: 'POST',
                dataType: 'json',
                data: {id:id,limitDay:limitDay,opType:"update"},
                success: function (data, status) {
                    if (data.status == 'success') {
                        base_alert_time("修改成功", 1000);
                        modifyModal.modal('hide');
                    } else {
                        //操作失败 弹出提示信息
                        base_alert_time(data.msg, 1000);
                    }
                }
         });
    });


    //modifyModal.find("[id='save']").remove();
    //ajax 查询后台数据 放到模态框 修改模态框的保存按钮属性
    $.ajax({
        url: urlPath + '/sms/template/select',
        type: 'POST',
        dataType: 'json',
        data: {id: $(row).attr("data-id")},
        success: function (data, status) {
            if (data.status == 'success') {
                var form = modifyModal.find(".modalForm");
                form.find("[id='modifyModalLabel']").text("模板详情");
                var resultData = data.list[0];
                form.find("input[name='id']").val(resultData.id);
                form.find("input[name='code']").val(resultData.code);
                form.find("input[name='name']").val(resultData.name);
                form.find("input[name='limitDay']").val(resultData.limitDay);
                form.find("select[name='type']").find("[value = "+$(row).attr("data-id")+"]").attr("selected",true);
                form.find("input[name='content']").val(resultData.content);
                form.find("input[name='id']").attr("disabled", true);
                form.find("input[name='code']").attr("disabled", true);
                form.find("select[name='type']").attr("disabled", true);
            } else {
                //操作失败 弹出提示信息
                base_alert_time(data.msg, 1000);
            }
        }
    });
}
