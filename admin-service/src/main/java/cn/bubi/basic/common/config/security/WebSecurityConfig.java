package cn.bubi.basic.common.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.security.web.session.SimpleRedirectSessionInformationExpiredStrategy;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

import cn.bubi.basic.modules.sys.security.CustomUserService;
import cn.bubi.basic.modules.sys.security.DefaultAccessDeniedHandler;
import cn.bubi.basic.modules.sys.security.LoginSuccessHandler;

/**
 * spring security config
 *
 * @author JoinHan
 * @version 0.0.1
 */
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // 开启springsecurity 的方法权限
                                                                          // @Secured("ROLE_TELLER")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserService customUserService;

    @Autowired
    private FindByIndexNameSessionRepository sessionRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers("/login").permitAll().anyRequest().authenticated().and().formLogin()
                .loginPage("/login").defaultSuccessUrl("/").failureUrl("/loginError?error=true").permitAll()
                .successHandler(this.loginSuccessHandler()) // 登录成功后可使用loginSuccessHandler()存储用户信息，可选。
                .and().logout().logoutSuccessUrl("/login").permitAll() // 退出登录后的默认网址是”/login”
                .deleteCookies() // 删除cookie
                .and().sessionManagement().invalidSessionUrl("/login").maximumSessions(-1).expiredUrl("/login")
                .expiredSessionStrategy(this.sessionInformationExpiredStrategy()).maxSessionsPreventsLogin(true)
                .sessionRegistry(this.sessionRegistry()).and().and().rememberMe()// 登录后记住用户，下次自动登录,数据库中必须存在名为persistent_logins的表
                .tokenValiditySeconds(1209600).and();
        // .addFilter(concurrentSessionFilter());
        // .addFilterAfter(requestFilter(), ConcurrentSessionFilter.class)
        // .exceptionHandling().accessDeniedHandler(accessDeniedHandler());
    }

    /*
     * @Bean
     * public SessionRegistry sessionRegistry(){
     * return new SessionRegistryImpl();
     * }
     */

    @Bean
    @SuppressWarnings("unchecked")
    public SpringSessionBackedSessionRegistry sessionRegistry() {

        return new SpringSessionBackedSessionRegistry(this.sessionRepository);
    }

    @Bean
    public ConcurrentSessionFilter concurrentSessionFilter() {

        SimpleRedirectSessionInformationExpiredStrategy expiredSessionStrategy = new SimpleRedirectSessionInformationExpiredStrategy(
                "/logout");
        ConcurrentSessionFilter filter = new ConcurrentSessionFilter(this.sessionRegistry(), expiredSessionStrategy);
        return filter;
    }

    /*
     * @Bean
     * public SessionFilter sessionFilter(){
     * SimpleRedirectSessionInformationExpiredStrategy expiredSessionStrategy = new
     * SimpleRedirectSessionInformationExpiredStrategy("/logout");
     * SessionFilter filter = new SessionFilter(sessionRegistry(), expiredSessionStrategy);
     * return filter;
     * }
     */

    /*
     * @Bean
     * public RequestFilter requestFilter(){
     * return new RequestFilter();
     * }
     */

    @Bean
    public static ServletListenerRegistrationBean httpSessionEventPublisher() {

        return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
    }

    @Bean
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {

        return new SimpleRedirectSessionInformationExpiredStrategy("/login");
    }

    /**
     * 密码设置
     *
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        // 指定密码加密所使用的加密器为passwordEncoder()
        auth.userDetailsService(this.customUserService).passwordEncoder(this.passwordEncoder());
        // 开启自动登录
        auth.eraseCredentials(false);
    }

    /**
     * BCrypt加密
     *
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder(4);
    }

    /**
     * 登录成功后处理
     *
     * @return
     */
    @Bean
    public LoginSuccessHandler loginSuccessHandler() {

        return new LoginSuccessHandler();
    }

    /**
     * 请求失败后处理
     *
     * @return
     */
    @Bean
    public DefaultAccessDeniedHandler accessDeniedHandler() {

        return new DefaultAccessDeniedHandler();
    }

    /**
     * 忽略的静态文件
     */
    @Override
    public void configure(WebSecurity web) throws Exception {

        web.ignoring().antMatchers("/static/**", "/webjars/**");
    }

}