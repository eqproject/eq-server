/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.basic.modules.sys.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eq.basic.common.base.BaseEntity;
import org.eq.basic.common.base.BaseTableData;
import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.basic.common.util.ParseUtil;
import org.eq.basic.common.util.StringLowUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.config.properties.SourceProperties;
import org.eq.basic.common.config.sysUtil.SysCacheUtil;
import org.eq.basic.common.config.sysUtil.UserUtil;
import org.eq.basic.modules.sys.dao.SqlMapper;
import org.eq.basic.modules.sys.dao.SysOfficeMapper;
import org.eq.basic.modules.sys.dao.SysUserInfoMapper;
import org.eq.basic.modules.sys.dao.SysUserMapper;
import org.eq.basic.modules.sys.dao.SysUserOfficeMapper;
import org.eq.basic.modules.sys.dao.SysUserRoleMapper;
import org.eq.basic.modules.sys.entity.SysOffice;
import org.eq.basic.modules.sys.entity.SysOfficeExample;
import org.eq.basic.modules.sys.entity.SysUser;
import org.eq.basic.modules.sys.entity.SysUserExample;
import org.eq.basic.modules.sys.entity.SysUserOffice;
import org.eq.basic.modules.sys.entity.SysUserOfficeExample;
import org.eq.basic.modules.sys.entity.SysUserRole;
import org.eq.basic.modules.sys.entity.SysUserRoleExample;
import org.eq.basic.modules.sys.security.UserInfo;
import org.eq.basic.modules.sys.service.SysUserService;

/**
 * 用户表ServiceImpl
 *
 * @author JoinHan
 * @version 0.0.1
 */
@Service
@Transactional
@AutowiredService
public class SysUserServiceImpl extends ServiceImplExtend<SysUserMapper, SysUser, SysUserExample>
        implements SysUserService {

    @Autowired
    private SqlMapper sqlMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysOfficeMapper sysOfficeMapper;

    @Autowired
    private SysUserInfoMapper sysUserInfoMapper;

    @Autowired
    private SysUserOfficeMapper sysUserOfficeMapper;

    @Autowired
    private SourceProperties sourceProperties;

    @Override
    public SysUserExample getExampleFromEntity(SysUser sysUser, Map<String, Object> params) {

        SysUserExample example = new SysUserExample();
        SysUserExample.Criteria ca = example.or();
        if (sysUser == null) {
            return example;
        }
        String orderName = null;
        String orderDir = null;
        if (params != null) {
            orderDir = (String) params.get("orderDir");
            orderName = (String) params.get("orderName");
        }
        if (orderName != null && !"".equals(orderName)) {
            example.setOrderByClause(orderName + " " + orderDir);
        } else {
            example.setOrderByClause("id asc");
        }
        if (sysUser.getId() != null) {
            ca.andIdEqualTo(sysUser.getId());
        }
        if (sysUser.getAreaId() != null) {
            ca.andAreaIdEqualTo(sysUser.getAreaId());
        }
        if (sysUser.getOfficeId() != null) {
            ca.andOfficeIdEqualTo(sysUser.getOfficeId());
        }
        if (sysUser.getDepartId() != null) {
            ca.andDepartIdEqualTo(sysUser.getDepartId());
        }
        if (StringLowUtils.isNotBlank(sysUser.getLnm())) {
            ca.andLnmLike("%" + sysUser.getLnm() + "%");
        }
        if (StringLowUtils.isNotBlank(sysUser.getPw())) {
            ca.andPwEqualTo(sysUser.getPw());
        }
        if (StringLowUtils.isNotBlank(sysUser.getNm())) {
            ca.andNmLike("%" + sysUser.getNm() + "%");
        }
        if (StringLowUtils.isNotBlank(sysUser.getSex())) {
            ca.andSexEqualTo(sysUser.getSex());
        }
        if (sysUser.getStatus() != null) {
            ca.andStatusEqualTo(sysUser.getStatus());
        }
        if (StringLowUtils.isNotBlank(sysUser.getTel())) {
            ca.andTelEqualTo(sysUser.getTel());
        }
        if (sysUser.getNoId() != null) {
            ca.andNoIdEqualTo(sysUser.getNoId());
        }
        if (sysUser.getType() != null) {
            ca.andTypeEqualTo(sysUser.getType());
        }
        if (StringLowUtils.isNotBlank(sysUser.getPhotoHead())) {
            ca.andPhotoHeadEqualTo(sysUser.getPhotoHead());
        }
        if (StringLowUtils.isNotBlank(sysUser.getLoginIp())) {
            ca.andLoginIpEqualTo(sysUser.getLoginIp());
        }
        if (sysUser.getLoginDate() != null) {
            ca.andLoginDateEqualTo(sysUser.getLoginDate());
        }
        if (StringLowUtils.isNotBlank(sysUser.getJob())) {
            ca.andJobEqualTo(sysUser.getJob());
        }
        if (sysUser.getCreateBy() != null) {
            ca.andCreateByEqualTo(sysUser.getCreateBy());
        }
        if (sysUser.getCreateDate() != null) {
            ca.andCreateDateEqualTo(sysUser.getCreateDate());
        }
        if (sysUser.getUpdateBy() != null) {
            ca.andUpdateByEqualTo(sysUser.getUpdateBy());
        }
        if (sysUser.getUpdateDate() != null) {
            ca.andUpdateDateEqualTo(sysUser.getUpdateDate());
        }
        if (StringLowUtils.isNotBlank(sysUser.getRemarks())) {
            ca.andRemarksEqualTo(sysUser.getRemarks());
        }
        if (StringLowUtils.isNotBlank(sysUser.getDelFlag())) {
            ca.andDelFlagEqualTo(sysUser.getDelFlag());
        }
        return example;
    }

    @Override
    public SysUser findUserByNmae(String username) {

        SysUserExample example = new SysUserExample();
        example.or().andLnmEqualTo(username).andDelFlagEqualTo(BaseEntity.DEL_FLAG_NORMAL);
        List<SysUser> userList = this.getMapper().selectByExample(example);
        if (userList != null && !( userList.isEmpty())) {
            return userList.get(0);
        }
        return null;
    }

    @Override
    public UserInfo findUserInfo(String username) {

        // 执行两条sql 1.查出用户信息 机构（部门）信息 地域信息 2.查出角色信息和菜单信息
        /*
         * 用户登陆信息包括
         * userId登录编号 loginName登录名 loginStatus登录用户状态
         * noId 员工编号 loginIp最后登陆IP loginDate 最后登陆日期 type登录类型
         * 用户详细信息
         * sex 用户性别 status 用户状态 name姓名 phone电话 photo头像
         * 机构信息包括
         * officeId机构编号 officeParentId父级编号 officeParentIds所有父级编号 officeName名称
         * officeCode机构代码 officeType机构类型 grade机构等级 address联系地址 officePhone机构电话 officeEmail 机构邮箱
         * 地域信息
         * areaId地域编号 areaParentId父级编号 areaParentIds所有父级编号 areaName名称 areaCode区域编码
         * areaType区域类型 areaLevel地区级别
         */
        String sql1 = "" + "  select " + "		su.id userId,su.lnm loginName,su.status loginStatus,su.type type,"
                + "     su.no_id noId,su.login_ip loginIp,su.login_date loginDate,"
                + "    sui.sex sex,sui.status status,sui.name name,sui.tel phone,sui.photo_head photo,"
                + "		so.id officeId,so.parent_id officeParentId,so.parent_ids officeParentIds,so.name officeName,"
                + "		so.code officeCode,so.type officeType,so.grade grade,so.address address,so.phone officePhone,so.email officeEmail,"
                + "		sa.id areaId,sa.parent_id areaParentId,sa.parent_ids areaParentIds,sa.`name` areaName,sa.`code` areaCode,"
                + "     sa.type areaType,sa.area_level areaLevel  " + "	from " + "		sys_user su "
                + "	inner join sys_user_info sui on sui.id = su.no_id"
                + "	inner join sys_user_office suo on suo.user_id = su.id "
                + "	inner join sys_office so on suo.office_id=so.id " + "	inner join sys_area sa on so.area_id=sa.id "
                + "	where su.lnm = '" + username + "' " + "		and su.del_flag = '" + BaseEntity.DEL_FLAG_NORMAL + "' "
                + "		and so.del_flag = '" + BaseEntity.DEL_FLAG_NORMAL + "' " + "		and sa.del_flag = '"
                + BaseEntity.DEL_FLAG_NORMAL + "' ";
        this.logger.debug(sql1);
        List<Map> list1 = this.sqlMapper.superManagerSelect(sql1);

        /*
         * 角色信息包括
         * roleId角色编号 roleName角色名称 roleEnname英文名称roleType角色类型
         * roleStatus角色状态 dataScope数据范围
         * 菜单按钮信息包括
         * menuId菜单编号 menuParentId父级编号 menuParentIds所有父级编号 menuName名称
         * href访问路径 target目标(打开方式) icon图标 isShow是否在菜单中显示 menuCode编码
         * status menuStatus菜单状态 permission权限标识
         */
        String sql2 = "" + "	select " + "		sr.id roleId,sr.name roleName,sr.enname roleEnname,sr.type roleType,"
                + "     sr.status roleStatus, sr.data_scope dataScope, "
                + "		sm.id menuId,sm.parent_id menuParentId,sm.parent_ids menuParentIds,sm.name menuName,"
                + "     sm.href href,sm.target target,sm.icon icon,sm.is_show isShow,sm.code menuCode,"
                + "     sm.status menuStatus,sm.permission permission " + "	from " + "		sys_user su "
                + "	inner join sys_user_role sur on su.id = sur.user_id "
                + "	inner join sys_role sr on sur.role_id = sr.id "
                + "	inner join sys_role_menu srm on sr.id = srm.role_id "
                + "	inner join sys_menu sm on srm.menu_id = sm.id " + "	where su.lnm = '" + username + "' "
                + "		and sr.del_flag = '" + BaseEntity.DEL_FLAG_NORMAL + "' " + "  and sr.status = "
                + BaseEntity.START_STATUS + "		and sm.del_flag = '" + BaseEntity.DEL_FLAG_NORMAL + "' "
                + "  and sm.status = " + BaseEntity.START_STATUS + "		and srm.del_flag = '"
                + BaseEntity.DEL_FLAG_NORMAL + "' " + "		and sur.del_flag = '" + BaseEntity.DEL_FLAG_NORMAL + "' ";
        this.logger.debug(sql2);
        List<Map> list2 = this.sqlMapper.superManagerSelect(sql2);
        return new UserInfo(list1, list2, this.sourceProperties);
    }

    @Override
    public boolean insertUser(SysUser sysUser) {

        // 保存用户信息，保存登录信息，保存用户角色信息，保存用户机构信息
        if (this.sysUserInfoMapper.insert(sysUser.getSysUserInfo()) > 0) {
            sysUser.setNoId(sysUser.getSysUserInfo().getId());
            if (this.getMapper().insert(sysUser) > 0) {
                for (SysUserRole sysUserRole : sysUser.getSysUserRoleList()) {
                    sysUserRole.setUserId(sysUser.getId());
                    this.sysUserRoleMapper.insert(sysUserRole);
                }
                for (SysUserOffice sysUserOffice : sysUser.getSysUserOfficeList()) {
                    sysUserOffice.setUserId(sysUser.getId());
                    this.sysUserOfficeMapper.insert(sysUserOffice);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateUser(SysUser sysUser) {

        if (this.sysUserInfoMapper.updateByPrimaryKeySelective(sysUser.getSysUserInfo()) > 0) {
            if (this.getMapper().updateByPrimaryKeySelective(sysUser) > 0) {
                // 删除之前的sysRole
                SysUserRoleExample example = new SysUserRoleExample();
                example.or().andUserIdEqualTo(sysUser.getId());
                this.sysUserRoleMapper.deleteByExample(example);
                for (SysUserRole sysUserRole : sysUser.getSysUserRoleList()) {
                    sysUserRole.setUserId(sysUser.getId());
                    this.sysUserRoleMapper.insert(sysUserRole);
                }
                // 删除之前的sysOffice
                SysUserOfficeExample sysUserOfficeExample = new SysUserOfficeExample();
                sysUserOfficeExample.or().andUserIdEqualTo(sysUser.getId());
                this.sysUserOfficeMapper.deleteByExample(sysUserOfficeExample);
                for (SysUserOffice sysUserOffice : sysUser.getSysUserOfficeList()) {
                    sysUserOffice.setUserId(sysUser.getId());
                    this.sysUserOfficeMapper.insert(sysUserOffice);
                }
                return true;
            }
        }

        return false;
    }

    @Override
    public int deleteUser(String ids, String virtual) {

        int delNum = 0;
        List<Long> idLongs = new ArrayList<>();
        for (String str : ids.split(",")) {
            idLongs.add(ParseUtil.getLong(str));
        }
        // 删除用户角色， 删除用户机构，删除用户信息， 删除登录信息 中间表均为直接删除
        SysUserRoleExample example = new SysUserRoleExample();
        example.or().andUserIdIn(idLongs);
        this.sysUserRoleMapper.deleteByExample(example);
        SysUserOfficeExample sysUserOfficeExample = new SysUserOfficeExample();
        sysUserOfficeExample.or().andUserIdIn(idLongs);
        this.sysUserOfficeMapper.deleteByExample(sysUserOfficeExample);

        if (virtual.equals("false")) {
            delNum = this.deleteByPrimaryKeys(ids);
        } else {
            delNum = this.deleteVirtualByPrimaryKeys(ids);
        }
        return delNum;
    }

    @Override
    public BaseTableData findDataTableByRecordForPagePermission(SysUser sysUser, Map<String, Object> params) {

        Object associationObj = params.get("association");
        StringBuilder sb = new StringBuilder();
        String officesStr = "";
        if (associationObj != null) {
            // 当前机构下的所有机构id
            SysOffice sysOffice = SysCacheUtil.getInstance().getOfficeIdMapCache().get(associationObj);
            SysOfficeExample sysOfficeExample = new SysOfficeExample();
            sysOfficeExample.or().andParentIdsLike(sysOffice.getParentIds() + sysOffice.getId() + ",%")
                    .andDelFlagEqualTo(BaseEntity.DEL_FLAG_NORMAL);
            sysOfficeExample.or().andIdEqualTo(sysOffice.getId()).andDelFlagEqualTo(BaseEntity.DEL_FLAG_NORMAL);
            List<SysOffice> officeList = this.sysOfficeMapper.selectByExample(sysOfficeExample);
            if (officeList != null && !( officeList.isEmpty())) {
                for (SysOffice office : officeList) {
                    sb.append(office.getId() + ",");
                }
            }
            officesStr = sb.substring(0, sb.length() - 1);
        } else {
            if (UserUtil.getInstance().isAdmin(null)) {
                officesStr = null;
            } else {
                officesStr = UserUtil.getInstance().getUserInfo().getUnderOfficeIds();
            }
        }
        sysUser.setOfficeidsStr(officesStr);

        Integer pageNum = (Integer) params.get("start");
        Integer pageSize = (Integer) params.get("pageSize");
        String orderDir = (String) params.get("orderDir");
        String orderName = (String) params.get("orderName");
        if (orderName != null && !"".equals(orderName)) {
            sysUser.setOrderByClause(orderName + " " + orderDir);
        } else {
            sysUser.setOrderByClause("id asc");
        }

        BaseTableData<SysUser> baseTableData = new BaseTableData();
        PageHelper.startPage(pageNum, pageSize, true);

        sysUser.setDelFlag(BaseEntity.DEL_FLAG_NORMAL);
        List<SysUser> sysUserList = this.getMapper().findUserList(sysUser);

        PageInfo pageInfo = new PageInfo(sysUserList);
        baseTableData.setData(pageInfo.getList());
        baseTableData.setRecordsFiltered(pageInfo.getTotal());
        // 查询总条数
        baseTableData.setRecordsTotal(pageInfo.getTotal());
        return baseTableData;
    }

    @Override
    public List<SysUser> selectUser(SysUser sysUser) {

        return this.getMapper().findUserList(sysUser);
    }
}