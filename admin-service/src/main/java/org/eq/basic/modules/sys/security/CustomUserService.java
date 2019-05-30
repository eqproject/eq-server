package org.eq.basic.modules.sys.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eq.basic.common.base.BaseEntity;
import org.eq.basic.common.util.StringLowUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import org.eq.basic.common.config.sysUtil.UserUtil;
import org.eq.basic.modules.sys.entity.SysMenu;
import org.eq.basic.modules.sys.entity.SysUser;
import org.eq.basic.modules.sys.service.SysUserService;

/**
 * 自定义UserDetailsService
 * Created by yangyibo on 17/1/18.
 */
@Service
public class CustomUserService implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    private final String ROLE_PREFIX = "ROLE_";

    /*
     * 重写loadUserByUsername方法获得 userdetails
     */
    @Override
    public UserDetails loadUserByUsername(String username) {

        SysUser user = this.sysUserService.findUserByNmae(username);
        Integer value = BaseEntity.DISABLE_STATUS;
        if (user == null) {
            throw new BadCredentialsException("用户名不存在");
        } else if (value.equals(user.getStatus())) {
            throw new BadCredentialsException("用户已经禁用，请联系管理员！");
        }

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        Map<Long, SysMenu> menuMap = ( UserUtil.getInstance().getUserInfo(user.getLnm())).getMenuMap();
        for (Long menuKey : menuMap.keySet()) {
            SysMenu menu = menuMap.get(menuKey);
            if (StringLowUtils.isNotBlank(menu.getPermission())) {
                authorities.add(new SimpleGrantedAuthority(this.ROLE_PREFIX + menu.getPermission()));
            }
        }
        return new User(user.getLnm(), user.getPw(), authorities);
    }
}
