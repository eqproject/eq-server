/**
 * @author gb
 */
var User;
(function (User) {
    function UserInfo() {
        this.$nickname = $("#nickname");
        this.$mobile = $("#mobile");
        this.$status = $("#status");
        this.$delFlag = $("#delFlag");
        this.$select = $("#select");//绑定点击事件

    }

    var stateObj = {1:'未认证',2:'已认证'};
    var delObj = {0:'正常',1:'已删除'};
    function getObjValByKey(obj,key) {
        for(var v in obj){
            if (v == key) {
                return obj[key];
            }
        }
        return "";
    }

    UserInfo.prototype = {

        constructor: UserInfo,
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
                        targets: 7,
                        render: function (data, type, row, meta) {
                            var option = "";
                            if(row.delFlag ==1){
                                option += ' <a href="javascript:void(0);" onclick="opUserDelFlag(this,0);"id="check" name="check" data-id="' + row.id + '" >启用</a>';
                            }else{
                                option += ' <a href="javascript:void(0);" onclick="opUserDelFlag(this,1);" id="check" name="check" data-id="' + row.id + '" >禁用</a>';
                            }
                            option+='  <a href="javascript:void(0);" onclick="check(this);" id="check" name="check" data-id="' + row.id + '" >查看</a>';

                            return option;
                        }
                    }

                    ],//第一列与最后一列禁止排序

                order: [ [0, null] ],//第一列排序图标改为默认
                ajax: {
                    url: urlPath + '/user/auth/user/dataList',
                    data: function (d) {
                        //添加额外的数据请求参数
                        d.nickname = curr.$nickname.val();
                        d.mobile = curr.$mobile.val();
                        d.status = curr.$status.val();
                        d.delFlag = curr.$delFlag.val();
                    }
                },
                columns: [
                    {},
                    {data: 'nickname', name: 'nickname'},
                    {data: 'name', name: 'name'},
                    {data: 'mobile', name: 'mobile'},
                    {data: 'status', name: 'status',render:function(data, type, row){return getObjValByKey(stateObj,row.status)}},
                    {data: 'createDate', name: 'createDate'},
                    {data: 'delFlag', name: 'delFlag',render:function(data, type, row){return getObjValByKey(delObj,row.delFlag)}},
                    {}
                ],
                fnDrawCallback: function () {
                    //加载完成后 初始化绑定事件
                    //checkbox全选
                }
            };
            curr.$authTable = initOriginalTable('authTable', option);
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

    User.UserInfo = UserInfo;
})(User || (User = {}));
$(function () {
    var UserInfo = new User.UserInfo();
    UserInfo.init();
});

//操作
function opUserDelFlag(op,commond) {
    var id = $(op).attr("data-id");
    $.post(urlPath + "/user/auth/user/opDelFlag", {"id": id,"commond":commond}, function (data) {
        if (data.code != "10401") {
            alert(data.msg);
        } else {
            alert("操作成功");
            $("#select").click();
        }
    });
}

function check(row) {
    var modifyModal = $("#modifyModal");
    modifyModal.modal({
        keyboard: false
    })
    modifyModal.find("[id='save']").remove();
    //ajax 查询后台数据 放到模态框 修改模态框的保存按钮属性
    $.ajax({
        url: urlPath + '/user/auth/user/selectUser',
        type: 'POST',
        dataType: 'json',
        data: {userId: $(row).attr("data-id")},
        success: function (data, status) {
            if (data.status == 'success') {
                var form = modifyModal.find(".modalForm");
                form.find("[id='modifyModalLabel']").text("用户详情");
                var resultData = data.list[0];
                form.find("input[name='id']").val(resultData.id);
                form.find("input[name='name']").val(resultData.name);
                form.find("input[name='nickname']").val(resultData.nickname);
                form.find("input[name='sex']").eq(resultData.sex).attr("checked", 'checked');
                form.find("input[name='mobile']").val(resultData.mobile);
                form.find("input[name='idCard']").val(resultData.idCard);
                form.find("select[name='status']").find("option").each(function(){
                    if($(this).val()==resultData.status){
                        $(this).attr("th:checked","true");
                    }
                });
                form.find("input[name='birthday']").val(resultData.birthday);
                form.find("input[name='photoHead']").val(resultData.photoHead);
                form.find("input[name='intro']").val(resultData.intro);
                form.find("textarea[name='remarks']").text(resultData.remarks);
                form.find("select[name='delFlag']").val(resultData.delFlag).trigger("change");
                form.find("input").attr("disabled", true);
                form.find("select").attr("disabled", true);
                form.find("textarea").attr("disabled", true);
            } else {
                //操作失败 弹出提示信息
                base_alert_time(data.msg, 1000);
            }
        }
    });
}
