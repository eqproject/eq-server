package org.eq.basic.modules.sys.security;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.eq.basic.common.base.BaseEntity;
import org.eq.basic.common.config.properties.SourceProperties;
import org.eq.basic.modules.sys.entity.SysArea;
import org.eq.basic.modules.sys.entity.SysMenu;
import org.eq.basic.modules.sys.entity.SysOffice;
import org.eq.basic.modules.sys.entity.SysRole;
import org.eq.basic.modules.sys.entity.SysUser;
import org.eq.basic.modules.sys.entity.SysUserInfo;

/**
 * 用户信息类
 *
 * @author JoinHan
 */
public class UserInfo {

    // 用户登录信息
    private SysUser user;

    // 用户详细信息
    private SysUserInfo sysUserInfo;

    // 角色信息 可能有多个角色
    private List<SysRole> roleList;

    // 菜单信息 因多个角色可能会菜单按钮重复 按照菜单code 排序
    private TreeSet<SysMenu> menuSet;

    // 树机构菜单
    private List<SysMenu> menuList;

    // 缓存用户去重后的菜单 用于加速查询速度
    private Map<Long, SysMenu> menuMap;

    // 机构信息
    private List<SysOffice> sysOfficeList;

    // 登录人某个机构下的下属机构
    Map<Long, List<SysOffice>> underOneOfficeMap;

    // 多个机构 下属所有机构信息
    String underOfficeIds = "";

    List<Long> underOfficeIdList;

    List<SysOffice> underOfficeList;

    // 地区信息
    private SysArea sysArea;

    // 下属所有地区信息
    String underAreaIds = "";

    List<SysArea> underAreaList;

    List<Long> underAreaIdList;

    public UserInfo() {
    }

    /**
     * 根据查询的信息生成userInfo
     *
     * @param list1
     *            用户信息，机构信息，地域信息
     * @param list2
     *            角色信息，菜单信息
     * @param sourceProperties
     *            资源变量
     */
    public UserInfo(List<Map> list1, List<Map> list2, SourceProperties sourceProperties) {
        this.menuList = new ArrayList<>();
        this.sysOfficeList = new ArrayList<>();
        this.sysArea = new SysArea();
        for (Map m : list1) {
            if (this.user == null) {
                this.user = new SysUser();
                this.user.setId(( (Long) m.get("userId")));
                this.user.setLnm((String) m.get("loginName"));
                this.user.setStatus((Integer) m.get("loginStatus"));
                this.user.setNoId((Long) m.get("noId"));
                this.user.setLoginIp((String) m.get("loginIp"));
                this.user.setLoginDate((Date) m.get("loginDate"));
                this.user.setType((Integer) m.get("type"));// 登录类型 0 admin 用户
            }
            if (this.sysUserInfo == null) {
                this.sysUserInfo = new SysUserInfo();
                this.sysUserInfo.setSex((String) m.get("sex"));
                this.sysUserInfo.setId((Long) m.get("noId"));
                this.sysUserInfo.setStatus((Integer) m.get("status"));
                this.sysUserInfo.setName((String) m.get("name"));
                this.sysUserInfo.setTel((String) m.get("phone"));
            }
            SysOffice sysOffice = new SysOffice();
            sysOffice.setId(( (Long) m.get("officeId")));
            sysOffice.setParentId(( (Long) m.get("officeParentId")));
            sysOffice.setParentIds(( (String) m.get("officeParentIds")));
            sysOffice.setName(( (String) m.get("officeName")));
            sysOffice.setCode((String) m.get("officeCode"));
            sysOffice.setGrade((Integer) m.get("grade"));
            sysOffice.setType((String) m.get("officeType"));
            sysOffice.setAddress((String) m.get("address"));
            sysOffice.setPhone((String) m.get("officePhone"));
            sysOffice.setEmail((String) m.get("officeEmail"));
            this.sysOfficeList.add(sysOffice);

            this.sysArea.setId((Long) m.get("areaId"));
            this.sysArea.setParentId((Long) m.get("areaParentId"));
            this.sysArea.setParentIds((String) m.get("areaParentIds"));
            this.sysArea.setName((String) m.get("areaName"));
            this.sysArea.setCode((String) m.get("areaCode"));
            this.sysArea.setType((Integer) m.get("areaType"));
            this.sysArea.setAreaLevel((Integer) m.get("areaLevel"));
        }
        // 角色可以有多个 menu是分级的
        this.roleList = new ArrayList<>();
        this.menuSet = new TreeSet<>(new Comparator<SysMenu>() {

            @Override
            public int compare(SysMenu o1, SysMenu o2) {

                return o1.getCode().compareTo(o2.getCode());
            }
        });

        Map<Long, SysRole> roleTemp = new HashMap<>();
        Map<Long, SysMenu> menuTemp = new HashMap<>();
        // 为了提高生成树的速度 缓存parentid
        Map<Long, List<Long>> parentTemp = new HashMap<>();
        for (Map m : list2) {
            Long roleId = (Long) m.get("roleId");
            // 先判断角色是不是禁用
            Integer useable = (Integer) m.get("roleStatus");
            Integer value = BaseEntity.DISABLE_STATUS;
            if (value.equals(useable)) {
                continue;
            }

            if (roleTemp.get(roleId) == null) {
                SysRole role = new SysRole();
                role.setId((Long) m.get("roleId"));
                role.setName((String) m.get("roleName"));
                role.setEnname((String) m.get("roleEnname"));
                role.setType((Integer) m.get("roleType"));
                role.setStatus((Integer) m.get("roleStatus"));
                role.setDataScope((Integer) m.get("dataScope"));
                this.roleList.add(role);
                roleTemp.put(roleId, role);
            }
            SysMenu menu = new SysMenu();
            // menu
            Long menuId = (Long) m.get("menuId");
            Long parentId = Long.parseLong(m.get("menuParentId") + "");
            menu.setId(menuId);
            menu.setParentId(parentId);
            menu.setParentIds((String) m.get("menuParentIds"));
            menu.setName((String) m.get("menuName"));
            String href = (String) m.get("href");
            if (href != null) {
                menu.setHref((String) m.get("href"));
            }
            menu.setTarget((String) m.get("target"));
            menu.setCode((String) m.get("menuCode"));
            menu.setIcon((String) m.get("icon"));
            menu.setIsShow((String) m.get("isShow"));
            menu.setStatus((Integer) m.get("menuStatus"));
            menu.setPermission((String) m.get("permission"));
            menuTemp.put(menuId, menu);
            List<Long> idList = parentTemp.get(parentId);
            if (idList == null) {
                idList = new ArrayList<>();
            }
            idList.add(menuId);
            parentTemp.put(parentId, idList);
        }

        this.menuMap = menuTemp;

        // 形成菜单树结构 parentid=0 是最顶级， 0不是菜单
        this.makeTree(menuTemp, this.menuSet, parentTemp, null);
        this.SetToList(this.menuSet, this.menuList, null);

    }

    /**
     * 将父级节点的子节点挂到树上
     *
     * @param menuTemp
     *            节点对应的menu
     * @param menuSet
     *            子节点挂载树
     * @param parentTemp
     *            父子节点关系缓存
     * @param parent
     *            父级节点指针
     */
    private TreeSet<SysMenu> makeTree(Map<Long, SysMenu> menuTemp, TreeSet<SysMenu> menuSet,
            Map<Long, List<Long>> parentTemp, SysMenu parent) {

        // 子节点集合
        List<Long> idList = null;
        if (parent == null) {// 0 不是菜单 从1开始
            idList = parentTemp.get(1L);
            if (idList != null && !( idList.isEmpty())) {
                for (Long id : idList) {
                    SysMenu menu = menuTemp.get(id);
                    menu.setParentMenu(null);
                    menuSet.add(menu);
                    /*
                     * TreeSet<SysMenu> childSet = menu.getChildTree();
                     * if(childSet==null){
                     * childSet = new TreeSet<SysMenu>(new Comparator<SysMenu>() {
                     *
                     * @Override
                     * public int compare(SysMenu o1, SysMenu o2) {
                     * return o1.getSort().intValue()-o2.getSort().intValue();
                     * }
                     * });
                     * }
                     */
                    TreeSet<SysMenu> childSet = this.makeTree(menuTemp, menu.getChildMenuSet(), parentTemp, menu);
                    menu.setChildMenuSet(childSet);
                }
            }
        } else {
            idList = parentTemp.get(parent.getId());

            if (idList != null && !( idList.isEmpty())) {
                if (menuSet == null) {
                    menuSet = new TreeSet<>(new Comparator<SysMenu>() {

                        @Override
                        public int compare(SysMenu o1, SysMenu o2) {

                            return o1.getCode().compareTo(o2.getCode());
                        }
                    });
                }
                for (Long id : idList) {
                    SysMenu menu = menuTemp.get(id);
                    menu.setParentMenu(parent);
                    menuSet.add(menu);
                    TreeSet<SysMenu> childSet = this.makeTree(menuTemp, menu.getChildMenuSet(), parentTemp, menu);
                    menu.setChildMenuSet(childSet);
                }
            }

        }
        return menuSet;
    }

    /**
     * 将set转出list 便于前端遍历
     *
     * @param menuSet
     * @param menuList
     * @param parent
     */
    private List<SysMenu> SetToList(TreeSet<SysMenu> menuSet, List<SysMenu> menuList, SysMenu parent) {

        if (menuList == null) {
            menuList = new ArrayList<>();
        }
        Iterator<SysMenu> it = menuSet.iterator();
        boolean hasChildMenu = false;
        while (it.hasNext()) {
            SysMenu menu = it.next();
            if ("1".equals(menu.getIsShow())) {
                hasChildMenu = true;
            }
            if (menu.getChildMenuSet() != null) {
                List<SysMenu> childList = this.SetToList(menu.getChildMenuSet(), menu.getChildMenuList(), menu);
                menu.setChildMenuList(childList);
            }
            menuList.add(menu);
        }
        if (parent != null) {
            parent.setHasChildMenu(hasChildMenu);
        }
        return menuList;
    }

    public SysUser getUser() {

        return this.user;
    }

    public void setUser(SysUser user) {

        this.user = user;
    }

    public List<SysRole> getRoleList() {

        return this.roleList;
    }

    public void setRoleList(List<SysRole> roleList) {

        this.roleList = roleList;
    }

    public List<SysMenu> getMenuList() {

        return this.menuList;
    }

    public void setMenuList(List<SysMenu> menuList) {

        this.menuList = menuList;
    }

    public Map<Long, SysMenu> getMenuMap() {

        return this.menuMap;
    }

    public SysArea getSysArea() {

        return this.sysArea;
    }

    public void setSysArea(SysArea sysArea) {

        this.sysArea = sysArea;
    }

    public String getUnderOfficeIds() {

        return this.underOfficeIds;
    }

    public void setUnderOfficeIds(String underOfficeIds) {

        this.underOfficeIds = underOfficeIds;
    }

    public List<SysOffice> getUnderOfficeList() {

        return this.underOfficeList;
    }

    public void setUnderOfficeList(List<SysOffice> underOfficeList) {

        this.underOfficeList = underOfficeList;
    }

    public List<Long> getUnderOfficeIdList() {

        return this.underOfficeIdList;
    }

    public void setUnderOfficeIdList(List<Long> underOfficeIdList) {

        this.underOfficeIdList = underOfficeIdList;
    }

    public String getUnderAreaIds() {

        return this.underAreaIds;
    }

    public void setUnderAreaIds(String underAreaIds) {

        this.underAreaIds = underAreaIds;
    }

    public List<Long> getUnderAreaIdList() {

        return this.underAreaIdList;
    }

    public void setUnderAreaIdList(List<Long> underAreaIdList) {

        this.underAreaIdList = underAreaIdList;
    }

    public List<SysArea> getUnderAreaList() {

        return this.underAreaList;
    }

    public void setUnderAreaList(List<SysArea> underAreaList) {

        this.underAreaList = underAreaList;
    }

    public SysUserInfo getSysUserInfo() {

        return this.sysUserInfo;
    }

    public void setSysUserInfo(SysUserInfo sysUserInfo) {

        this.sysUserInfo = sysUserInfo;
    }

    public List<SysOffice> getSysOfficeList() {

        return this.sysOfficeList;
    }

    public void setSysOfficeList(List<SysOffice> sysOfficeList) {

        this.sysOfficeList = sysOfficeList;
    }

    public Map<Long, List<SysOffice>> getUnderOneOfficeMap() {

        return this.underOneOfficeMap;
    }

    public void setUnderOneOfficeMap(Map<Long, List<SysOffice>> underOneOfficeMap) {

        this.underOneOfficeMap = underOneOfficeMap;
    }
}
