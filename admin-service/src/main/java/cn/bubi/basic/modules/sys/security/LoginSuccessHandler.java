package cn.bubi.basic.modules.sys.security;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.stereotype.Component;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private FindByIndexNameSessionRepository sessionRegistry;

    @Override
    @SuppressWarnings("unchecked")
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        // 获得授权后可得到用户信息 可使用SUserService进行数据库操作
        Object principal = authentication.getPrincipal();
        User securityUser = (User) principal;
        if (securityUser != null && securityUser.getUsername() != null) {
            // 输出登录提示信息
            String userName = securityUser.getUsername();
            this.logger.info("sysLog:用户 " + userName + " 登录");

            // 把相同用户的session踢下线
            Map<String, Session> sessionIdToSession = this.sessionRegistry.findByIndexNameAndIndexValue(
                    FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, securityUser.getUsername());
            if (sessionIdToSession.size() > 0) {
                for (String sessionId : sessionIdToSession.keySet()) {
                    this.sessionRegistry.deleteById(sessionId);
                }
            }
        }
        this.logger.info("sysLog:IP " + this.getIpAddress(request));
        super.onAuthenticationSuccess(request, response, authentication);
    }

    private String getIpAddress(HttpServletRequest request) {

        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
