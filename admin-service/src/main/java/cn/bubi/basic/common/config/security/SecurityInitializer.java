package cn.bubi.basic.common.config.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Created by JoinHan on 2017/8/7.
 */
public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {

    public SecurityInitializer() {
        super(WebSecurityConfig.class, SessionConfig.class);
    }

    @Override
    protected boolean enableHttpSessionEventPublisher() {

        return true;
    }
}
