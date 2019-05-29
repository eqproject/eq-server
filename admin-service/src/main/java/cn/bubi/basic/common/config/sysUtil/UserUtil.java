package cn.bubi.basic.common.config.sysUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.bubi.basic.common.base.BaseEntity;
import cn.bubi.basic.common.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import cn.bubi.basic.common.status.CacheKey;
import cn.bubi.basic.modules.sys.entity.SysArea;
import cn.bubi.basic.modules.sys.entity.SysAreaExample;
import cn.bubi.basic.modules.sys.entity.SysOffice;
import cn.bubi.basic.modules.sys.entity.SysOfficeExample;
import cn.bubi.basic.modules.sys.entity.SysUser;
import cn.bubi.basic.modules.sys.security.UserInfo;
import cn.bubi.basic.modules.sys.service.SysAreaService;
import cn.bubi.basic.modules.sys.service.SysOfficeService;
import cn.bubi.basic.modules.sys.service.SysUserService;

/**
 * 用户信息的统一操作类 及 频繁使用的数据缓存操作
 *
 * @author JoinHan on 2017/04/27
 *
 */
@Component
public class UserUtil {

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysOfficeService sysOfficeService;

    @Autowired
    private SysAreaService sysAreaService;

    @Autowired
    private EHcacheUtil cacheUtil;

    private static UserUtil userUtil;

    private Logger logger = LoggerFactory.getLogger(UserUtil.class);

    public synchronized static UserUtil getInstance() {

        if (userUtil == null) {
            userUtil = (UserUtil) SpringContextUtil.getBean("userUtil");
        }
        return userUtil;
    }

    /**
     * 查询登录用户信息
     *
     * @return
     */
    public UserInfo getUserInfo() {

        UserInfo userInfo = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            try {
                userInfo = (UserInfo) this.cacheUtil.get(CacheKey.USER_CACHE, userDetails.getUsername());
            } catch(Exception e) {
                this.logger.info("系统缓存没有数据，从数据库查询数据");
            }
            if (userInfo == null) {
                userInfo = this.userService.findUserInfo(userDetails.getUsername());
                this.initUserInfo(userInfo);
                this.putUserInfoInCache(userInfo, userDetails.getUsername());
            }
        }
        return userInfo;
    }

    private void initUserInfo(UserInfo userInfo) {

        // 查询出用户下属的所有机构,部门
        List<Long> offidLongs = new ArrayList<>();
        Map<Long, List<SysOffice>> underOneOfficeMap = new HashMap<>();
        StringBuilder sbOffice = null;
        Map<Long, SysOffice> temOffice = new HashMap<>();
        for (SysOffice sysOffice : userInfo.getSysOfficeList()) {
            SysOfficeExample sysOfficeExample = new SysOfficeExample();
            sysOfficeExample.or().andParentIdsLike(sysOffice.getParentIds() + sysOffice.getId() + ",%")
                    .andDelFlagEqualTo(BaseEntity.DEL_FLAG_NORMAL);
            sysOfficeExample.or().andIdEqualTo(sysOffice.getId()).andDelFlagEqualTo(BaseEntity.DEL_FLAG_NORMAL);
            List<SysOffice> officeList = this.sysOfficeService.findListByExample(sysOfficeExample);
            underOneOfficeMap.put(sysOffice.getId(), officeList);
            for (SysOffice so : officeList) {
                temOffice.put(so.getId(), so);
            }
        }
        // 去重后
        List<SysOffice> allOffice = new ArrayList<>();
        for (Long id : temOffice.keySet()) {
            SysOffice so = temOffice.get(id);
            offidLongs.add(so.getId());
            if (sbOffice == null) {
                sbOffice = new StringBuilder("");
            }
            sbOffice.append(so.getId() + ",");
            allOffice.add(so);
        }
        if (sbOffice != null) {
            userInfo.setUnderOfficeIds(sbOffice.substring(0, sbOffice.length() - 1));
        }
        userInfo.setUnderOfficeIdList(offidLongs);
        userInfo.setUnderOneOfficeMap(underOneOfficeMap);
        userInfo.setUnderOfficeList(allOffice);

        // 查询出用户下属的所有地区
        SysAreaExample sysAreaExample = new SysAreaExample();
        sysAreaExample.or()
                .andParentIdsLike(userInfo.getSysArea().getParentIds() + userInfo.getSysArea().getId() + ",%")
                .andDelFlagEqualTo(BaseEntity.DEL_FLAG_NORMAL);
        sysAreaExample.or().andIdEqualTo(userInfo.getSysArea().getId()).andDelFlagEqualTo(BaseEntity.DEL_FLAG_NORMAL);
        List<SysArea> areaList = this.sysAreaService.findListByExample(sysAreaExample);
        List<Long> areaidLongs = new ArrayList<>();
        StringBuilder sbArea = new StringBuilder("");
        if (areaList != null && !( areaList.isEmpty())) {
            for (SysArea sysArea : areaList) {
                areaidLongs.add(sysArea.getId());
                sbArea.append(sysArea.getId() + ",");
            }
        }
        userInfo.setUnderAreaIds(sbArea.substring(0, sbArea.length() - 1));
        userInfo.setUnderAreaList(areaList);
        userInfo.setUnderAreaIdList(areaidLongs);
    }

    /**
     * 根据用户名 查询登录用户信息
     *
     * @return
     */
    public UserInfo getUserInfo(String userName) {

        UserInfo userInfo = null;
        try {
            userInfo = (UserInfo) this.cacheUtil.get(CacheKey.USER_CACHE, userName);
        } catch(Exception e) {
            this.logger.info("系统缓存没有数据，从数据库查询数据");
        }
        if (userInfo == null) {
            userInfo = this.userService.findUserInfo(userName);
            this.putUserInfoInCache(userInfo, userName);
            this.initUserInfo(userInfo);
        }

        return userInfo;
    }

    /**
     * 根据用户名 查询用户信息
     *
     * @param username
     * @return
     */
    public SysUser getUser(String username) {

        UserInfo userInfo = null;
        try {
            userInfo = (UserInfo) this.cacheUtil.get(CacheKey.USER_CACHE, username);
        } catch(Exception e) {
            this.logger.info("系统缓存没有数据，从数据库查询数据");
        }
        if (userInfo == null) {
            userInfo = this.userService.findUserInfo(username);
            this.putUserInfoInCache(userInfo, username);
            this.initUserInfo(userInfo);
        }
        return userInfo.getUser();
    }

    /**
     * 登录人
     *
     * @return
     */
    public SysUser getUser() {

        return this.getUserInfo().getUser();
    }

    public void putUserInfoInCache(UserInfo userInfo, String key) {

        this.cacheUtil.put(CacheKey.USER_CACHE, key, userInfo);
    }

    /**
     * 清除登录者userInfo缓存
     *
     * @return
     */
    public boolean removeUser(HttpServletRequest request, HttpServletResponse response) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            this.cacheUtil.remove(CacheKey.USER_CACHE, userDetails.getUsername());
            new SecurityContextLogoutHandler().logout(request, response, auth);
            return true;
        }
        return false;
    }

    /**
     * 判断用户是否是最高权限账户 参数为空 判断当前登录者
     *
     * @return
     */
    public boolean isAdmin(SysUser sysUser) {

        if (sysUser == null) {
            sysUser = this.getUserInfo().getUser();
        }
        if (sysUser.getType() != null && BaseEntity.USER_ADMIN == sysUser.getType()) {
            return true;
        } else {
            return false;
        }
    }
}
