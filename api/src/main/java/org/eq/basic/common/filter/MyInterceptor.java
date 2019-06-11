package org.eq.basic.common.filter;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import org.apache.commons.lang3.StringUtils;
import org.eq.basic.common.util.SpringContextUtil;
import org.eq.modules.auth.entity.User;
import org.eq.modules.auth.service.UserService;
import org.eq.modules.enums.WalletStateEnum;
import org.eq.modules.wallet.entity.UserWallet;
import org.eq.modules.wallet.service.UserWalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Author: JoinHan
 * @Date: Created in 13:46 2018/3/5
 * @Modified By：
 */
public class MyInterceptor implements HandlerInterceptor {
    private final static String KEY = "1A9585B3C9F0854AD1B73C4ED80F7D31";

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o)
            throws Exception {
        JSONObject json = new JSONObject();
        json.put("status", 1);

        Map<String, String[]> map = httpServletRequest.getParameterMap();
        if(!map.containsKey("sign")){
            json.put("errMsg","签名为空");
            write(httpServletResponse,json.toJSONString());
            return false;
        }
        String reqSign = map.get("sign")[0];

        if(map.containsKey("userId")){
            String userId = map.get("userId")[0];
            boolean result = checkUser(userId,httpServletResponse);
            if(!result){
                return false;
            }
        }

        List<String> params = new ArrayList<>(map.size());
        map.forEach((k,v)->params.add(k+"="+v[0]));

        Collections.sort(params);
        params.add(KEY);

        String sorted = StringUtils.join(params,"&");

        logger.info("sing params:{}",sorted);

        String sign = Hashing.sha256().newHasher()
                .putString(sorted, Charsets.UTF_8)
                .hash().toString();

        if(!reqSign.equalsIgnoreCase(sign)){
            json.put("errMsg","签名不正确");
            write(httpServletResponse,json.toJSONString());
            return false;
        }

        // System.out.println(">>>MyInterceptor>>>>>>>在请求处理之前进行调用（Controller方法调用之前）");
        return true;// 只有返回true才会继续向下执行，返回false取消当前请求
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
            ModelAndView modelAndView) throws Exception {

        // System.out.println(">>>MyInterceptor>>>>>>>请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            Object o, Exception e) throws Exception {

        // System.out.println(">>>MyInterceptor>>>>>>>在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）");
    }

    private boolean checkUser(String userIdStr,HttpServletResponse httpServletResponse)throws Exception{
        UserService userService = SpringContextUtil.getBean(UserService.class);
        UserWalletService userWalletService =  SpringContextUtil.getBean(UserWalletService.class);

        long userId = Long.parseLong(userIdStr);
        User user = userService.selectByPrimaryKey(userId);

        JSONObject json = new JSONObject();
        json.put("status", 1);
        if(user==null){
            json.put("errMsg","用户不存在");
            return false;
        }

        UserWallet userWallet = userWalletService.selectByPrimaryKey(userId);
        if(userWallet==null || userWallet.getStatus()== WalletStateEnum.NO_ACTIVE.getState()){
            json.put("errMsg","用户钱包未激活不存在");
            return false;
        }
        write(httpServletResponse,json.toJSONString());
        return true;
    }

    private void write(HttpServletResponse httpServletResponse,String result)throws Exception{
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setHeader("Content-Type", "application/json; charset=UTF-8");
        httpServletResponse.getWriter().write(result);
    }
}
