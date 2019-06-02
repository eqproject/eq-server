package org.eq.basic.common.base;

import com.alibaba.fastjson.JSONObject;
import org.eq.modules.auth.entity.User;

/**
 * 控制器支持类
 * Created by JoinHan on 2018/01/11.
 */
public abstract class BaseController extends BaseLog {



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
        //TODO 增加用户有效性 变更用户实体
        User user = new User();
        user.setId(1L);
        user.setTxPassword("KAKA");
        return user;
    }


}
