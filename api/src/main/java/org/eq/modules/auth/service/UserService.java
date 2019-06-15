/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.auth.service;

import org.eq.modules.auth.entity.User;
import org.eq.modules.auth.entity.UserAccountBind;
import org.eq.modules.auth.entity.UserExample;
import org.eq.basic.common.base.ServiceExtend;
import org.eq.modules.auth.entity.UserIdentityAuth;
import org.eq.modules.common.entitys.ResponseData;

/**
 * 用户管理Service
 * @author hobe
 * @version 2019-0-05-30
 */
public interface UserService extends ServiceExtend<User, UserExample> {
    ResponseData register(String mobile, String captcha);
    ResponseData reset(String userId, String pwd);
    ResponseData login(String mobile, String pwd);
    ResponseData verify(UserIdentityAuth userIdentityAuth);
    ResponseData mobileLogin(String mobile, String captcha);
    ResponseData payBind(UserAccountBind userAccountBind);
}