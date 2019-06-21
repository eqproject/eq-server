package org.eq.basic.common.base;

import com.alibaba.fastjson.JSONObject;
import org.eq.modules.auth.entity.User;
import org.eq.modules.auth.service.UserService;
import org.eq.modules.enums.WalletStateEnum;
import org.eq.modules.wallet.entity.UserWallet;
import org.eq.modules.wallet.service.UserWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * 控制器支持类
 * Created by JoinHan on 2018/01/11.
 */
@Controller
public abstract class BaseController extends BaseLog {


    protected final static String SYSTEM_ERROR_MSG = "系统错误";

    @Autowired
    protected UserService userService;

    /**
     * 验证用户是否有效
     * @param userId
     * @return
     */
    public User getUserInfo(long userId){
        if(userId<=0){
            return null;
        }
        User user =null;
        try{
            user = userService.selectByPrimaryKey(userId);
            if(user!=null && user.getDelFlag() != 0){
                logger.info("用户ID {} 被封禁",user.getId());
                user = null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }


}
