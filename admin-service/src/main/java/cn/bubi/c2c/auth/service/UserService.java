package cn.bubi.c2c.auth.service;

import cn.bubi.basic.common.base.ServiceExtend;
import cn.bubi.c2c.auth.entity.User;
import cn.bubi.c2c.auth.entity.UserExample;


public interface UserService extends ServiceExtend<User, UserExample> {


    /**
     * 通过条件更新订单状态
     * @param user
     * @param oldDelFlag
     * @return
     */
    int updateUserDelFlagById(User user, Integer oldDelFlag);
}
