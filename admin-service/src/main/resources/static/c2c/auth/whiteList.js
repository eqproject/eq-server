/**
 * @author gb
 */
var statusObj = {0:'未关联',1:'已关联'};
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
    function UserAuth() {
        this.$mobile = $("#mobile");
        this.$status = $("#status");
        this.$select = $("#select");
        this.$add = $("#add");

        this.$whitelistTable = $("#whitelistTable");
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
                        console.log("data"+data);
                        console.log("row"+row);
                        console.log("meta"+meta);
                        return meta.row+1;
                    }
                },

                {
                        orderable: false,
                        targets: 4,//最后1列
                        render: function (data, type, row, meta) {
                            var option = "";
                            option += '<a href="#" id="delete" name="delete" onclick="del(this);" data-id="' + row.id + '" >删除</a>';
                            return option;
                        }
                    }
                    ],//第一列与最后一列禁止排序

                order: [ [0, null] ],//第一列排序图标改为默认
                ajax: {
                    url: urlPath + '/user/auth/whitelist/dataList',
                    data: function (d) {
                        //添加额外的数据请求参数
                        d.mobile = curr.$mobile.val();
                    }
                },
                columns: [
                    {},
                    {data: 'mobile', name: 'mobile'},
                    {data: 'status', name: 'status',render:function(data, type, row){return getObjValByKey(statusObj,row.status)}},
                    {data: 'createDate', name: 'createDate'},
                    {}
                ],
                fnDrawCallback: function () {
                    //加载完成后 初始化绑定事件
                    //checkbox全选
                }
            };
            curr.$whitelistTable = initOriginalTable('whitelistTable', option);
            this.loadTable(this);
            this.bindBtn();

        },
        loadTable: function (event) {
            var curr = getNow(event);
            curr.$whitelistTable.ajax.reload();
        },
        add:addmobile,
        bindBtn: function () {
            var curr = this;
            this.$select.on("click", this, this.loadTable);
            this.$add.on("click", this, this.add);
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

function addmobile(){
    var addModal = $("#addModal");
    addModal.modal({
        keyboard: false
    });

    //修改
    addModal.find("[id='save']").on("click",function(){
        var mobile = addModal.find("input").val();
        //保存数据
        $.ajax({
                url: urlPath + '/user/auth/whitelist/modify',
                type: 'POST',
                dataType: 'json',
                data: {mobile:mobile,opType:"save"},
                success: function (data, status) {
                    if (data.status == 'success') {
                        base_alert_time("新增成功", 1000);
                        addModal.modal('hide');
                    } else {
                        //操作失败 弹出提示信息
                        base_alert_time(data.msg, 1000);
                    }
                }
         });
    });
}

function del(row) {
    //查询详情
    $.ajax({
        url: urlPath + '/user/auth/whitelist/delete',
        type: 'POST',
        dataType: 'json',
        data: {ids: $(row).attr("data-id"),virtual:false},
        success: function (data, status) {
            if (data.status == 'success') {
                  base_alert_time("删除成功", 1000);
            } else {
                //操作失败 弹出提示信息
                base_alert_time(data.msg, 1000);
            }
        }
    });
}