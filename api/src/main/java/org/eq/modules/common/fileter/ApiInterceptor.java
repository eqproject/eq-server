package org.eq.modules.common.fileter;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.MimeHeaders;
import org.eq.basic.common.exception.BizException;
import org.eq.basic.common.util.SpringContextUtil;
import org.eq.modules.auth.entity.User;
import org.eq.modules.auth.service.UserService;
import org.eq.modules.common.enums.ResponseStateEnum;
import org.eq.modules.enums.WalletStateEnum;
import org.eq.modules.wallet.entity.UserWallet;
import org.eq.modules.wallet.service.UserWalletService;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.*;

/**
 * api 拦截器
 * @author  kaka
 *
 */

public class ApiInterceptor  implements HandlerInterceptor {
    private final static String KEY = "1A9585B3C9F0854AD1B73C4ED80F7D31";
    private final static String USER_ID = "userId";
    private final static String SIGN = "sign";
    public  final static String REQUEST_ID="REQUEST_ID";

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private static Set<String> noSignUrl = new HashSet<>();
    static {
        noSignUrl.add("api/support/terms");
        noSignUrl.add("api/support/legal");
        noSignUrl.add("api/support/buydoc");
        noSignUrl.add("api/support/getConfig");
        noSignUrl.add("api/test/up");
        noSignUrl.add("api/test/loan");
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o)
            throws Exception {

        String url = httpServletRequest.getRequestURL().toString();
        url = url.substring(url.indexOf("api"));

        Map<String, String[]> map = httpServletRequest.getParameterMap();
        if(noSignUrl.contains(url)){
            return true;
        }

        if(!map.containsKey(SIGN)){
            throw new BizException(ResponseStateEnum.SIGN_NULL);
        }

        String reqSign = map.get(SIGN)[0];

        if(map.containsKey(USER_ID)){
            String userId = map.get(USER_ID)[0];
            checkUser(userId,httpServletRequest);
        }

        List<String> params = new ArrayList<>(map.size());
        map.forEach((k,v)->{
            if (!k.equals(SIGN)) {
                params.add(k + "=" + v[0]);
            }
        });

        Collections.sort(params);
        params.add(KEY);

        String sorted = StringUtils.join(params,"&");

        String sign = Hashing.sha256().newHasher()
                .putString(sorted, Charsets.UTF_8)
                .hash().toString();
        System.out.println(sign);
        if(!reqSign.equalsIgnoreCase(sign)){
            throw new BizException(ResponseStateEnum.SIGN_INVALID);
        }
        String requestId= "-1" ;
        String number = String.valueOf((Math.random()*9+1)*100000);
        if(number.contains(".")){
            number=number.substring(0,number.indexOf("."));
        }
        requestId = System.currentTimeMillis()+number;
        httpServletResponse.setHeader(REQUEST_ID,requestId);
        logger.info("requestId:{}, url:{} , sign params:{}",requestId,url,sorted);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {

        // System.out.println(">>>MyInterceptor>>>>>>>在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）");
    }

    private void checkUser(String userIdStr,
                           HttpServletRequest httpServletRequest)throws Exception{
        UserService userService = SpringContextUtil.getBean(UserService.class);
        UserWalletService userWalletService =  SpringContextUtil.getBean(UserWalletService.class);

        long userId = Long.parseLong(userIdStr);
        User user = userService.selectByPrimaryKey(userId);

        if(user==null){
            throw new BizException(ResponseStateEnum.USER_NULL);
        }

        //用户API不验证钱包地址
        if(httpServletRequest.getRequestURI().startsWith("/api/user")){
            return;
        }

        /*
        暂时不验证钱包是否激活
        UserWallet userWallet = userWalletService.selectByPrimaryKey(userId);
        if(userWallet==null || userWallet.getStatus()== WalletStateEnum.NO_ACTIVE.getState()){
            throw new BizException(ResponseStateEnum.USER_WALLET_INACTIVE);
        }*/
    }
}
