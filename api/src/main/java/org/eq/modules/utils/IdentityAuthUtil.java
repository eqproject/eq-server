package org.eq.modules.utils;

import com.alibaba.fastjson.JSONObject;
import org.eq.basic.common.util.WebClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class IdentityAuthUtil {
    private static Logger logger = LoggerFactory.getLogger(IdentityAuthUtil.class);
    private final static String REASON = "成功";
    private final static int MATCH_YES = 1;

    private final static String URL = "http://op.juhe.cn/idcard/query";
    private final static String KEY = "11250ad3a668af859fc118636970b167";

    /**
     * 用户实名认证
     * @param idcard
     * @param realname
     * @return
     */
    public static Boolean userVerify(String idcard, String realname) {
        WebClientUtil webClientUtil = new WebClientUtil();

        Map<String, Object> param = new HashMap<>();
        param.put("idcard", idcard);
        param.put("realname", realname);
        param.put("key", KEY);
        try {
            // {"reason":"成功 ","result":{"realname":"xxx","idcard":"622301********809X","res":1},"error_code":0}
            String result = webClientUtil.syncPostByForm(URL, param);
            Map<String, Object> resultMap = JSONObject.parseObject(result, Map.class);
            if (!REASON.equals(((String) resultMap.get("reason")).trim())) {
                return false;
            }

            Map<String, Object> data = (Map) resultMap.get("result");
            int res = (Integer) data.get("res");
            if (MATCH_YES == res) {
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.error("实名认证接口调用失败", e);
            return false;
        }
    }
}
