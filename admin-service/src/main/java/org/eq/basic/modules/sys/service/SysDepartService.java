/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.basic.modules.sys.service;

import java.util.Map;

import org.eq.basic.common.base.BaseTableData;
import org.eq.basic.common.base.ServiceExtend;
import org.eq.basic.modules.sys.entity.SysDepart;
import org.eq.basic.modules.sys.entity.SysDepartExample;
import org.eq.basic.modules.sys.security.UserInfo;

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