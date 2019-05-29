package cn.bubi.basic.modules.sys.controller;

import cn.bubi.basic.common.config.properties.SourceProperties;
import cn.bubi.basic.common.config.sysUtil.UserUtil;
import cn.bubi.basic.modules.sys.security.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @Autowired
    private SourceProperties sourceProperties;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/")
    public String index(HttpServletRequest request, Model model) {

        UserInfo userInfo = UserUtil.getInstance().getUserInfo();
        if (userInfo == null) {
            return "index";
        }
        model.addAttribute("userInfo", userInfo);
        // 前台链接地址前缀
        model.addAttribute("contextPath", this.sourceProperties.getEnv().getProperty("server.servlet.context-path"));
        return "home";
    }

    @GetMapping("/login")
    public String loginGet(HttpServletRequest request, HttpServletResponse response, Model model) {

        response.setHeader("sessionstatus", "timeout");
        return "index";
    }

    /**
     * 错误信息处理
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @GetMapping("/loginError")
    public String loginError(HttpServletRequest request, HttpServletResponse response, Model model) {

        Object object = request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
        if (object instanceof BadCredentialsException) {
            BadCredentialsException ex = (BadCredentialsException) object;
            if (ex.getMessage().equals("Bad credentials")) {
                model.addAttribute("errorMsg", "用户名或密码错误！");
            } else {
                model.addAttribute("errorMsg", ex.getMessage());
            }
        }
        if (object instanceof InternalAuthenticationServiceException) {
            InternalAuthenticationServiceException ex = (InternalAuthenticationServiceException) object;
            if (ex.getMessage().equals("Bad credentials")) {
                model.addAttribute("errorMsg", "用户名或密码错误！");
            } else {
                model.addAttribute("errorMsg", ex.getMessage());
            }
        }
        return "index";
    }

    @GetMapping(value = "/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {

        UserUtil.getInstance().removeUser(request, response);
        return "redirect:/login";
    }

    @RequestMapping("/changePwd")
    public String changePwd(String userId, Model model) {

        model.addAttribute("userId", userId);
        return "modules/sys/changePwd";
    }

    @RequestMapping("/fileUpload")
    public String fileUpload(Model model) {

        return "modules/sys/fileUpload";
    }
}