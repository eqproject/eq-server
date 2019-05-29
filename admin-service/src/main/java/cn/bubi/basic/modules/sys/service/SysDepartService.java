/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package cn.bubi.basic.modules.sys.service;

import java.util.Map;

import cn.bubi.basic.common.base.BaseTableData;
import cn.bubi.basic.common.base.ServiceExtend;
import cn.bubi.basic.modules.sys.entity.SysDepart;
import cn.bubi.basic.modules.sys.entity.SysDepartExample;
import cn.bubi.basic.modules.sys.security.UserInfo;

/**
 * 部门表Service
 *
 * @author JoinHan
 * @version 2018-03-30
 */
public interface SysDepartService extends ServiceExtend<SysDepart, SysDepartExample> {

    /**
     * 账户权限下的部门
     *
     * @param userInfo
     * @param sysDepart
     * @param params
     * @return
     */
    BaseTableData findDataTableByRecordPermission(UserInfo userInfo, SysDepart sysDepart, Map<String, Object> params);
}