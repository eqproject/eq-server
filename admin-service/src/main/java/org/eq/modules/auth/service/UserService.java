package org.eq.modules.auth.service;

import org.eq.basic.common.base.ServiceExtend;
import org.eq.modules.auth.entity.User;
import org.eq.modules.auth.entity.UserExample;


public interface UserService extends ServiceExtend<User, UserExample> {


    /**
     * 通过条件更新订单状态
     * @param userId
     * @param newDelFlag
     * @param oldDelFlag
     * @return
     */
    int updateUserDelFlagById(long userId,Integer newDelFlag,Integer oldDelFlag);
}
