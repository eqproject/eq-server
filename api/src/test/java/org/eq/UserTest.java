package org.eq;

import org.eq.modules.auth.entity.UserIdentityAuth;
import org.eq.modules.auth.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author : gb 2019/7/1
 */
public class UserTest extends BaseTest{
    @Autowired
    private UserService userService;

    @Test
    public void test(){
        UserIdentityAuth auth = new UserIdentityAuth();
        auth.setUserId(17L);
        auth.setIdentityCard("fac155087c9f58bfed75cbf4c853fc00f6b02449a1ba31da478532c694a20f5c");
        auth.setIdentityName("test");
        userService.verify(auth);
    }
}
