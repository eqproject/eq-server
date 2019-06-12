/**
 * @author gb
 */
var Support;
(function (Support) {
    function SupportInfo() {
        this.$grouping = $("#grouping");
        this.$type = $("#type");
        this.$content = $("#content");
        this.$value = $("#value");
        this.$state = $("#state");
        this.$select = $("#select");//绑定点击事件

    }

    var stateObj = {0:'正常',1:'删除'};
    var typeObj = {1:'服务条款',2:'法务支持',3:'求购文案',4:'出售文案',5:'实名认证'};
    function getObjValByKey(obj,key) {
        for(var v in obj){
            if (v == key) {
                return obj[key];
            }
        }
        return "";
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
                        targets: 5,
                        render: function (data, type, row, meta) {
                            option ='  <a href="javascript:void(0);" onclick="edit(this);" id="check" name="check" data-id="' + row.id + '" >编辑</a>';
                            return option;
                        }
                    }

                    ],//第一列与最后一列禁止排序

                order: [ [0, null] ],//第一列排序图标改为默认
                ajax: {
                    url: urlPath + '/support/dataList',
                    data: function (d) {
                        //添加额外的数据请求参数
                    }
                },
                columns: [
                    {},
                    {data: 'grouping', name: 'grouping'},
                    {data: 'type', name: 'type',render:function(data, type, row){return getObjValByKey(typeObj,row.type)}},
                    {data: 'value', name: 'value'},
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

function edit(row) {
    var modifyModal = $("#modifyModal");
    var E = window.wangEditor;
    var editor = new E('#editor');
    editor.create();
    modifyModal.modal({
        keyboard: false
    });

    //修改
    modifyModal.find("[id='save']").on("click",function(){
        var id = modifyModal.find("#id").val();
        var grouping = modifyModal.find("#grouping").val();
        var value = modifyModal.find("#value").val();
        var content = editor.txt.html();
        var state = modifyModal.find("#state").val();
        //保存数据
        $.ajax({
                url: urlPath + '/support/modify',
                type: 'POST',
                dataType: 'json',
                data: {id:id,grouping:grouping,value:value,content:content,state:state,opType:"update"},
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


    //查询详情
    $.ajax({
        url: urlPath + '/support/select',
        type: 'POST',
        dataType: 'json',
        data: {id: $(row).attr("data-id")},
        success: function (data, status) {
            if (data.status == 'success') {
                var form = modifyModal.find(".modalForm");
                form.find("[id='modifyModalLabel']").text("详情");
                var resultData = data.list[0];
                form.find("input[name='id']").val(resultData.id);
                form.find("input[name='grouping']").val(resultData.grouping);
                form.find("select[name='type']").find("[value = "+$(row).attr("data-id")+"]").attr("selected",true);
                form.find("input[name='value']").val(resultData.value);
                form.find("select[name='state']").find("[value = "+$(row).attr("data-id")+"]").attr("selected",true);
                editor.txt.html(resultData.content);
                form.find("input[name='id']").attr("disabled", true);
                form.find("input[name='grouping']").attr("disabled", true);
                form.find("select[name='type']").attr("disabled", true);
            } else {
                //操作失败 弹出提示信息
                base_alert_time(data.msg, 1000);
            }
        }
    });
}
