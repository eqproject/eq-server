package cn.bubi.basic.common.base;

import com.alibaba.fastjson.JSONObject;

/**
 * 控制器支持类
 * Created by JoinHan on 2018/01/11.
 */
public abstract class BaseController {

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
}
