package org.eq.modules.auth.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.eq.basic.common.base.BaseController;
import org.eq.modules.auth.entity.User;
import org.eq.modules.auth.entity.UserIdentityAuth;
import org.eq.modules.common.entitys.ResponseData;
import org.eq.modules.common.factory.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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


    @Autowired
    private RedisTemplate redisTemplate;

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
     * 用户重置密码接口
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/reset",method = RequestMethod.POST)
    public ResponseData reset(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        String pwd = request.getParameter("pwd");
        return userService.reset(userId,pwd);
    }

    /**
     * 用户信息维护接口
     * @param user
     * @return
     */
    @RequestMapping(value = "/modify",method = RequestMethod.POST)
    public ResponseData modify(User user) {
        user.setUpdateDate(new Date());
        int cnt = userService.updateByPrimaryKeySelective(user);
        if(cnt > 0){
            return ResponseFactory.success(null);
        }else{
            return ResponseFactory.error("更新失败","1");
        }
    }

    /**
     * 用户登录接口(密码登录方式)
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/login")
    public ResponseData login(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        String pwd = request.getParameter("pwd");
        return userService.login(userId,pwd);
    }

    /**
     * 用户登录接口(手机验证码登录)
     * @param request
     * @return
     */
    @RequestMapping(value = "/mobileLogin")
    public ResponseData mobileLogin(HttpServletRequest request){
        String mobile = request.getParameter("mobile");
        String captcha = request.getParameter("captcha");
        return userService.mobileLogin(mobile, captcha);
    }

    /**
     * 用户实名认证接口
     * @param userIdentityAuth
     * @return
     */
    @RequestMapping(value = "/identity/verify")
    public ResponseData verify(UserIdentityAuth userIdentityAuth) {
        return userService.verify(userIdentityAuth);
    }
    @RequestMapping(value = "/getCode")
    public String getCode(String mobile){
        String captcha = RandomStringUtils.random(4,false,true);
        redisTemplate.opsForValue().set(mobile,captcha);

        String captchaRedis = (String)redisTemplate.opsForValue().get(mobile);
        System.out.println("captchaRedis:"+captchaRedis);
        return captcha;
    }
}