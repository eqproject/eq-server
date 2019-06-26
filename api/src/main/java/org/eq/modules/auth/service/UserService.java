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
    /**
     * 用户注册
     * @param mobile
     * @param captcha
     * @return
     */
    ResponseData register(String mobile, String captcha);

    /**
     * 用户重置密码
     * @param mobile
     * @param captcha
     * @param userId
     * @param pwd
     * @return
     */
    ResponseData reset(String mobile, String captcha,Long userId, String pwd);

    /**
     * 用户登录
     * @param mobile
     * @param pwd
     * @return
     */
    ResponseData login(String mobile, String pwd);

    /**
     * 用户实名认证
     * @param userIdentityAuth
     * @return
     */
    ResponseData verify(UserIdentityAuth userIdentityAuth);

    /**
     * 用户手机号验证码登录
     * @param mobile
     * @param captcha
     * @return
     */
    ResponseData mobileLogin(String mobile, String captcha);

    /**
     * 支付账号绑定
     * @param userAccountBind
     * @return
     */
    ResponseData payBind(UserAccountBind userAccountBind);

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    ResponseData getUserInfo(Long userId);
}