package org.eq.modules.auth.controller;

import org.eq.basic.common.base.BaseController;
import org.eq.modules.auth.entity.User;
import org.eq.modules.auth.entity.UserAccountBind;
import org.eq.modules.auth.entity.UserIdentityAuth;
import org.eq.modules.common.entitys.ResponseData;
import org.eq.modules.common.factory.ResponseFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 用户Controller
 *
 * @author hobe
 * @version 2019-05-30
 */
@RestController
@RequestMapping(value = "/api/user")
public class UserController extends BaseController {
    /**
     * 用户注册接口
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/register")
    public ResponseData register(HttpServletRequest request) {
        String mobile = request.getParameter("mobile");
        String captcha = request.getParameter("captcha");
        return userService.register(mobile, captcha);
    }

    /**
     * 获取用户信息
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/getInfo")
    public ResponseData getUserInfo(Long userId) {
        return userService.getUserInfo(userId);
    }

    /**
     * 用户重置密码接口
     *
     * @param mobile
     * @param pwd
     * @return
     */
    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public ResponseData reset(String mobile, String captcha,Long userId,String pwd) {
        return userService.reset(mobile,captcha,userId, pwd);
    }

    /**
     * 用户信息维护接口
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public ResponseData modify(User user) {
        user.setUpdateDate(new Date());
        int cnt = userService.updateByPrimaryKeySelective(user);
        if (cnt > 0) {
            return ResponseFactory.success(null);
        } else {
            return ResponseFactory.error("更新失败", "1");
        }
    }

    /**
     * 用户登录接口(密码登录方式)
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseData login(HttpServletRequest request) {
        String mobile = request.getParameter("mobile");
        String pwd = request.getParameter("pwd");
        return userService.login(mobile, pwd);
    }

    /**
     * 用户登录接口(手机验证码登录)
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/mobileLogin", method = RequestMethod.POST)
    public ResponseData mobileLogin(HttpServletRequest request) {
        String mobile = request.getParameter("mobile");
        String captcha = request.getParameter("captcha");
        return userService.mobileLogin(mobile, captcha);
    }

    /**
     * 用户实名认证接口
     *
     * @param userIdentityAuth
     * @return
     */
    @RequestMapping(value = "/identity/verify", method = RequestMethod.POST)
    public ResponseData verify(UserIdentityAuth userIdentityAuth) {
        return userService.verify(userIdentityAuth);
    }

    @RequestMapping(value = "/pay/bind", method = RequestMethod.POST)
    public ResponseData payBind(UserAccountBind userAccountBind) {
        return userService.payBind(userAccountBind);
    }

}