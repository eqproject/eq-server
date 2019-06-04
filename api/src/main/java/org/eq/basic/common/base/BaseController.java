package org.eq.basic.common.base;

import com.alibaba.fastjson.JSONObject;
import org.eq.modules.auth.entity.User;
import org.eq.modules.auth.service.UserService;
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

    @Autowired
    private UserWalletService userWalletService;

    public JSONObject success() {
        JSONObject json = new JSONObject();
        json.put("status", 0);
        return json;
    }

    public JSONObject error(String errMsg) {
        JSONObject json = new JSONObject();
        json.put("status", 1);
        return json;
    }

    /**
     * 验证用户是否有效
     * @param userId
     * @return
     */
    public User getUserInfo(long userId){
        User user =null;
        try{
            user = userService.selectByPrimaryKey(userId);
            UserWallet userWallet = userWalletService.selectByPrimaryKey(userId);
            if(userWallet!=null && userWallet.getStatus()==1){
                user.setAddress(userWallet.getAddress());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }


}
