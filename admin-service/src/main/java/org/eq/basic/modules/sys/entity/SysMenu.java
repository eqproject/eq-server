/**
 * 该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.basic.modules.sys.entity;

import org.eq.basic.common.base.BaseEntity;

import java.util.Date;
import java.util.List;
import java.util.TreeSet;

/**
 * 菜单表Entity
 *
 * @author JoinHan
 * @version 0.0.1
 */
public class SysMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long id; //

    private Long parentId; // 父级id

    private String parentIds; // 上级ids

    private String name; // 菜单名称

    private String href; // 菜单访问路径

    private String target; // 菜单打开方式

    private String icon; // 图标

    private Integer sort; // 排序

    private String code; // 菜单编码

    private Integer status; // 菜单状态

    private String isShow; // 是否显示

    private String permission; // 菜单权限

    private SysMenu parentMenu;// 父级

    private boolean hasChildMenu;// 是否有子菜单

    private TreeSet<SysMenu> childMenuSet;// 子菜单Set

    private List<SysMenu> childMenuList;// 子菜单List

    public SysMenu() {
        super();
    }

    public Long getId() {

        return this.id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public Long getParentId() {

        return this.parentId;
    }

    public void setParentId(Long parentId) {

        this.parentId = parentId;
    }

    public String getParentIds() {

        return this.parentIds;
    }

    public void setParentIds(String parentIds) {

        this.parentIds = parentIds;
    }

    public String getName() {

        return this.name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getHref() {

        return this.href;
    }

    public void setHref(String href) {

        this.href = href;
    }

    public String getTarget() {

        return this.target;
    }

    public void setTarget(String target) {

        this.target = target;
    }

    public String getIcon() {

        return this.icon;
    }

    public void setIcon(String icon) {

        this.icon = icon;
    }

    public Integer getSort() {

        return this.sort;
    }

    public void setSort(Integer sort) {

        this.sort = sort;
    }

    public String getCode() {

        return this.code;
    }

    public void setCode(String code) {

        this.code = code;
    }

    public Integer getStatus() {

        return this.status;
    }

    public void setStatus(Integer status) {

        this.status = status;
    }

    public String getIsShow() {

        return this.isShow;
    }

    public void setIsShow(String isShow) {

        this.isShow = isShow;
    }

    public String getPermission() {

        return this.permission;
    }

    public void setPermission(String permission) {

        this.permission = permission;
    }

    @Override
    public Long getCreateBy() {

        return this.createBy;
    }

    @Override
    public void setCreateBy(Long createBy) {

        this.createBy = createBy;
    }

    @Override
    public Date getCreateDate() {

        return this.createDate;
    }

    @Override
    public void setCreateDate(Date createDate) {

        this.createDate = createDate;
    }

    @Override
    public Long getUpdateBy() {

        return this.updateBy;
    }

    @Override
    public void setUpdateBy(Long updateBy) {

        this.updateBy = updateBy;
    }

    @Override
    public Date getUpdateDate() {

        return this.updateDate;
    }

    @Override
    public void setUpdateDate(Date updateDate) {

        this.updateDate = updateDate;
    }

    @Override
    public String getRemarks() {

        return this.remarks;
    }

    @Override
    public void setRemarks(String remarks) {

        this.remarks = remarks;
    }

    @Override
    public String getDelFlag() {

        return this.delFlag;
    }

    @Override
    public void setDelFlag(String delFlag) {

        this.delFlag = delFlag;
    }

    public SysMenu getParentMenu() {

        return this.parentMenu;
    }

    public void setParentMenu(SysMenu parentMenu) {

        this.parentMenu = parentMenu;
    }

    public TreeSet<SysMenu> getChildMenuSet() {

        return this.childMenuSet;
    }

    public void setChildMenuSet(TreeSet<SysMenu> childMenuSet) {

        this.childMenuSet = childMenuSet;
    }

    public List<SysMenu> getChildMenuList() {

        return this.childMenuList;
    }

    public void setChildMenuList(List<SysMenu> childMenuList) {

        this.childMenuList = childMenuList;
    }

    public boolean isHasChildMenu() {

        return this.hasChildMenu;
    }

    public void setHasChildMenu(boolean hasChildMenu) {

        this.hasChildMenu = hasChildMenu;
    }
}