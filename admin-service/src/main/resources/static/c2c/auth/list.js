/**
 * @author gb
 */
var User;
(function (User) {
    function UserAuth() {
        this.$identityName = $("#identityName");
        this.$select = $("#select");

        this.$authTable = $("#authTable");
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
                    /*
                    {
                        orderable: false,
                        targets: 5,//最后1列
                        render: function (data, type, row, meta) {
                            var isEdit = [[${#authorization.expression('hasRole(''sys:dict:edit'')')}]];
                            var option = "";
                            if (isEdit) {
                                option += '<a href="#" id="update" name="update" data-id="' + row.id + '" >修改</a>'
                                    + '  <a href="#" id="delete" name="delete" data-id="' + row.id + '" >删除</a>';
                            }
                            option += ' <a href="#" id="check" name="check" data-id="' + row.id + '" >查看</a>';
                            return option;
                        }
                    }
                     */
                    ],//第一列与最后一列禁止排序

                order: [ [0, null] ],//第一列排序图标改为默认
                ajax: {
                    url: urlPath + '/user/auth/identity/dataList',
                    data: function (d) {
                        //添加额外的数据请求参数
                        d.identityName = curr.$identityName.val();
                    }
                },
                columns: [
                    {},
                    {data: 'identityName', name: 'identityName'},
                    {data: 'identityCard', name: 'identityCard'},
                    {data: 'authResult', name: 'authResult'},
                    {data: 'createDate', name: 'createDate'}
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

    User.UserAuth = UserAuth;
})(User || (User = {}));
$(function () {
    var UserAuth = new User.UserAuth();
    UserAuth.init();
});