package org.eq.modules.common.fileter;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import org.apache.commons.lang3.StringUtils;
import org.eq.basic.common.exception.BizException;
import org.eq.basic.common.exception.BizExcetionMsg;
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
 * api 拦截器
 * @author  kaka
 *
 */

public class ApiInterceptor  implements HandlerInterceptor {
    private final static String KEY = "1A9585B3C9F0854AD1B73C4ED80F7D31";
    private final static String USER_ID = "userId";
    private final static String SIGN = "sign";

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o)
            throws Exception {

        Map<String, String[]> map = httpServletRequest.getParameterMap();
        if(!map.containsKey(SIGN)){
            throw new BizException(BizExcetionMsg.SIGN_NULL);
        }

        String reqSign = map.get(SIGN)[0];

        if(map.containsKey(USER_ID)){
            String userId = map.get(USER_ID)[0];
            checkUser(userId,httpServletResponse);
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

        logger.info("sing params:{}",sorted);

        String sign = Hashing.sha256().newHasher()
                .putString(sorted, Charsets.UTF_8)
                .hash().toString();

        if(!reqSign.equalsIgnoreCase(sign)){
            throw new BizException(BizExcetionMsg.SIGN_INVALID);
        }
        return true;
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

    private void checkUser(String userIdStr,HttpServletResponse httpServletResponse)throws Exception{
        UserService userService = SpringContextUtil.getBean(UserService.class);
        UserWalletService userWalletService =  SpringContextUtil.getBean(UserWalletService.class);

        long userId = Long.parseLong(userIdStr);
        User user = userService.selectByPrimaryKey(userId);

        if(user==null){
            throw new BizException(BizExcetionMsg.USER_NULL);
        }

        UserWallet userWallet = userWalletService.selectByPrimaryKey(userId);
        if(userWallet==null || userWallet.getStatus()== WalletStateEnum.NO_ACTIVE.getState()){
            throw new BizException(BizExcetionMsg.USER_WALLET_INACTIVE);
        }
    }
}
