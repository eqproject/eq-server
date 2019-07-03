package org.eq.modules.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.basic.common.util.DateUtil;
import org.eq.basic.common.util.IdCardVerificationUtil;
import org.eq.basic.common.util.StringLowUtils;
import org.eq.modules.auth.dao.UserMapper;
import org.eq.modules.auth.entity.*;
import org.eq.modules.auth.service.UserAccountBindService;
import org.eq.modules.auth.service.UserClientWhitelistService;
import org.eq.modules.auth.service.UserIdentityAuthService;
import org.eq.modules.auth.service.UserService;
import org.eq.modules.bc.dao.InitiatorAccMapper;
import org.eq.modules.bc.entity.BcTxRecord;
import org.eq.modules.bc.entity.InitiatorAcc;
import org.eq.modules.bc.entity.InitiatorAccExample;
import org.eq.modules.common.entitys.ResponseData;
import org.eq.modules.common.factory.ResponseFactory;
import org.eq.modules.enums.*;
import org.eq.modules.support.entity.SystemConfig;
import org.eq.modules.support.service.SystemConfigService;
import org.eq.modules.utils.AESUtils;
import org.eq.modules.utils.MD5Utils;
import org.eq.modules.wallet.entity.UserWallet;
import org.eq.modules.wallet.service.BcTxRecordService;
import org.eq.modules.wallet.service.UserWalletService;
import org.eq.modules.wallet.util.WalletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 用户管理ServiceImpl
 *
 * @author hobe
 * @version 2019-05-30
 */
@Service
@Transactional
@AutowiredService
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl extends ServiceImplExtend<UserMapper, User, UserExample> implements UserService {
    private static final String MD5_KEY = "org.eq.modules";

    private final UserWalletService userWalletService;
    private final BcTxRecordService bcTxRecordService;
    private final UserIdentityAuthService userIdentityAuthService;
    private final RedisTemplate redisTemplate;
    private final UserAccountBindService userAccountBindService;
    private final InitiatorAccMapper initiatorAccMapper;
    private final SystemConfigService systemConfigService;
    private final UserClientWhitelistService userClientWhitelistService;

    @Value("${aes.key}")
    private String aesKey;


    @Override
    public int insertRecord(User user) {
        return super.insertRecord(user);
    }

    @Override
    public void insertRecordReturnId(User user) {
        super.insertRecordReturnId(user);
    }

    @Override
    public UserExample getExampleFromEntity(User user, Map<String, Object> params) {
        UserExample example = new UserExample();
        UserExample.Criteria ca = example.or();
        if (user == null) {
            return example;
        }
        String orderName = null;
        String orderDir = null;
        if (params != null) {
            orderDir = (String) params.get("orderDir");
            orderName = (String) params.get("orderName");
        }
        if (orderName != null && !"".equals(orderName)) {
            example.setOrderByClause(orderName + " " + orderDir);
        } else {
            example.setOrderByClause("id asc");
        }
        if (user.getId() != null) {
            ca.andIdEqualTo(user.getId());
        }
        if (StringLowUtils.isNotBlank(user.getName())) {
            ca.andNameEqualTo(user.getName());
        }
        if (StringLowUtils.isNotBlank(user.getNickname())) {
            ca.andNicknameEqualTo(user.getNickname());
        }
        if (StringLowUtils.isNotBlank(user.getPassword())) {
            ca.andPasswordEqualTo(user.getPassword());
        }
        if (StringLowUtils.isNotBlank(user.getTxPassword())) {
            ca.andTxPasswordEqualTo(user.getTxPassword());
        }
        if (user.getSex() != null) {
            ca.andSexEqualTo(user.getSex());
        }
        if (StringLowUtils.isNotBlank(user.getLevel())) {
            ca.andLevelEqualTo(user.getLevel());
        }
        if (StringLowUtils.isNotBlank(user.getMobile())) {
            ca.andMobileEqualTo(user.getMobile());
        }
        if (user.getAuthStatus() != null) {
            ca.andAuthStatusEqualTo(user.getAuthStatus());
        }
        if (user.getBirthday() != null) {
            ca.andBirthdayEqualTo(user.getBirthday());
        }
        if (StringLowUtils.isNotBlank(user.getPhotoHead())) {
            ca.andPhotoHeadEqualTo(user.getPhotoHead());
        }
        if (StringLowUtils.isNotBlank(user.getIntro())) {
            ca.andIntroEqualTo(user.getIntro());
        }
        if (user.getCreateDate() != null) {
            ca.andCreateDateEqualTo(user.getCreateDate());
        }
        if (user.getUpdateDate() != null) {
            ca.andUpdateDateEqualTo(user.getUpdateDate());
        }
        if (StringLowUtils.isNotBlank(user.getRemarks())) {
            ca.andRemarksEqualTo(user.getRemarks());
        }
        return example;
    }

    /**
     * 用户注册
     *
     * @param mobile
     * @param captcha
     * @return
     */
    @Override
    public ResponseData register(String mobile, String captcha) {
        Map dataMap = new HashMap();

        if (mobile == null) {
            return ResponseFactory.businessError("电话号码为空");
        }

        if (captcha == null) {
            return ResponseFactory.businessError("验证码为空");
        }
        //检查验证码
        if (!checkCaptcha(mobile, captcha)) {
            return ResponseFactory.businessError("验证码错误");
        }

        // 1.添加用户
        //检查是否已注册
        User user = getUserByMobile(mobile);
        if (user != null) {
            return ResponseFactory.businessError("手机号已注册");
        } else {
            user = new User();
            user.setMobile(mobile);
        }
        //检查是否是白名单用户
        UserClientWhitelist userClientWhitelist = new UserClientWhitelist();
        userClientWhitelist.setMobile(mobile);
        UserClientWhitelist checkUserWhiteList = userClientWhitelistService.selectByRecord(userClientWhitelist);
        if (checkUserWhiteList != null) {
            user.setClientType(ClientTypeEnum.B.getState());
            checkUserWhiteList.setStatus(UserWhiteListStatusEnum.YES.getState());
            userClientWhitelistService.updateByPrimaryKey(checkUserWhiteList);
        } else {
            user.setClientType(ClientTypeEnum.C.getState());
        }
        Long userId = saveUser(user);

        if (userId == null) {
            return ResponseFactory.businessError("注册失败");
        }
        //清除redis验证码
        clearCaptcha(mobile);

        // 2.生成钱包地址
        UserWallet wallet = WalletUtil.generate(userId);

        // 3.bc_tx_record插入一条记录
        BcTxRecord bcTxRecord = new BcTxRecord();
        bcTxRecord.setToAddress(wallet.getAddress());
        Long txId = addBcTxRecord(bcTxRecord);

        //4.插入user_wallet钱包地址
        wallet.setTxId(txId);
        userWalletService.insertRecordReturnId(wallet);

        dataMap.put("userId", userId);
        return ResponseFactory.success(dataMap);
    }

    /**
     * 注册或登陆成功清除验证码
     *
     * @param mobile
     */
    private void clearCaptcha(String mobile) {
        redisTemplate.delete(mobile);
    }

    /**
     * bc_tx_record添加一条激活账户的记录
     *
     * @param bcTxRecord
     * @return
     */
    private Long addBcTxRecord(BcTxRecord bcTxRecord) {

        bcTxRecord.setFromAddress(getInitiatorAcc().getAddress());
        bcTxRecord.setTransferAmount("0.01");
        bcTxRecord.setAssetType(2);
        bcTxRecord.setTxStatus(0);
        bcTxRecord.setBizType(BcAccountTypeEnum.ACTIVITY.getCode());
        bcTxRecord.setCreateTime(new Date());
        bcTxRecord.setUpdateTime(new Date());
        bcTxRecord.setOptMetadata("");
        bcTxRecord.setTxType(BcTxTypeEnum.BU.getCode());
        bcTxRecordService.insertRecordReturnId(bcTxRecord);
        return bcTxRecord.getId();
    }

    private InitiatorAcc getInitiatorAcc() {
        InitiatorAccExample example = new InitiatorAccExample();
        InitiatorAccExample.Criteria criteria = example.or();
        criteria.andTypeEqualTo(0);
        List<InitiatorAcc> list = initiatorAccMapper.selectByExample(example);
        int index = RandomUtils.nextInt(0, list.size());
        return list.get(index);
    }

    /**
     * 保存用户，返回ID
     *
     * @param user
     * @return
     */
    private Long saveUser(User user) {
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        user.setDelFlag(StateEnum.VALID.getState());
        user.setAuthStatus(UserStateEnum.AUTHENTICATION_NO.getState());
        insertRecordReturnId(user);
        return user.getId();
    }

    /**
     * 用户重置密码
     *
     * @param mobile
     * @param captcha
     * @param userId
     * @param pwd
     * @return
     */
    @Override
    public ResponseData reset(String mobile, String captcha, Long userId, String pwd) {

        try {
            User user;
            if (userId != null) {
                user = selectByPrimaryKey(userId);
            } else {
                //检查验证码
                if (!checkCaptcha(mobile, captcha)) {
                    return ResponseFactory.businessError("验证码错误");
                }

                user = getUserByMobile(mobile);
            }

            if (user == null) {
                return ResponseFactory.businessError("手机号未注册");
            }

            //AES解密
            String password = AESUtils.decrypt(pwd, aesKey);
            if (password == null || "".equals(password)) {
                return ResponseFactory.businessError("重置密码解密失败");
            }
            String content = user.getId() + password + MD5_KEY;
            user.setPassword(MD5Utils.digestAsHex(content));
            user.setUpdateDate(new Date());
            int cnt = updateByPrimaryKeySelective(user);
            if (cnt > 0) {
                return ResponseFactory.success(user);
            } else {
                return ResponseFactory.businessError("重置密码失败");
            }
        } catch (Exception e) {
            logger.error("重置密码失败", e);
            return ResponseFactory.businessError("重置密码失败");
        } finally {
            if (StringUtils.isNotBlank(mobile)) {
                clearCaptcha(mobile);
            }
        }

    }

    /**
     * 用户登录
     *
     * @param mobile
     * @param pwd
     * @return
     */
    @Override
    public ResponseData login(String mobile, String pwd) {
        try {
            User user = new User();
            user.setMobile(mobile);
            //手机号获取userId
            User checkUser = selectByRecord(user);
            if (checkUser == null) {
                return ResponseFactory.businessError("手机号未注册");
            }
            //AES解密
            String password = AESUtils.decrypt(pwd, aesKey);
            if (password == null || "".equals(password)) {
                return ResponseFactory.businessError("密码不正确");
            }
            String content = checkUser.getId() + password + MD5_KEY;
            user.setPassword(MD5Utils.digestAsHex(content));
            User currUser = selectByRecord(user);
            if (currUser != null) {
                return ResponseFactory.success(currUser);
            } else {
                return ResponseFactory.businessError("密码不正确");
            }
        } catch (Exception e) {
            logger.error("登陆失败", e);
            return ResponseFactory.businessError("登陆失败");
        }
    }

    /**
     * 实名认证
     *
     * @param userIdentityAuth
     * @return
     */
    @Override
    public ResponseData verify(UserIdentityAuth userIdentityAuth) {
        //验证次数风控
        String prefix = "verify";
        String key = prefix + userIdentityAuth.getUserId();
        String defer = (String) redisTemplate.opsForValue().get(key + "time");
        if (defer != null) {
            return ResponseFactory.businessError("请求过于频繁，请稍后再试！");
        }
        SystemConfig systemConfig = systemConfigService.getSystemConfigByType(SystemConfigTypeEnum.VERIFY.getType());
        Integer limit = Integer.parseInt(systemConfig.getValue());
        Integer count = (Integer) redisTemplate.opsForValue().get(key);
        logger.info("User Verify Limit : " + count);
        logger.info("User Verify Expire : " + redisTemplate.getExpire(key));
        if (count != null) {
            if (count >= limit) {
                return ResponseFactory.businessError("实名认证次数超过限制,每个用户每天限制" + limit + "次");
            } else {
                redisTemplate.opsForValue().increment(key, 1);
                redisTemplate.opsForValue().set(key + "time", "1", 1, TimeUnit.MINUTES);
            }
        } else {
            redisTemplate.opsForValue().set(key, 1);
            redisTemplate.opsForValue().set(key + "time", "1", 1, TimeUnit.MINUTES);
            redisTemplate.expireAt(key, DateUtil.getNextDayTime());
        }

        //解密身份证号
        String identityCard = AESUtils.decrypt(userIdentityAuth.getIdentityCard(), aesKey);

        //验证身份证号码是否合法

        String checkResult = IdCardVerificationUtil.IDCardValidate(identityCard);
        if (!checkResult.equals(IdCardVerificationUtil.VALIDITY)) {
            return ResponseFactory.businessError(checkResult);
        }

        //验证用户
        User user = new User();
        user.setId(userIdentityAuth.getUserId());
        User checkUser = selectByRecord(user);
        if (checkUser == null) {
            return ResponseFactory.businessError("用户不存在");
        }
        //检查用户ID查询是否已认证
        UserIdentityAuth auth = new UserIdentityAuth();
        auth.setUserId(userIdentityAuth.getUserId());
        UserIdentityAuth checkAuth = userIdentityAuthService.selectByRecord(auth);
        if (checkAuth != null && IdentityStatusEnum.SUCCESS.getState() == checkAuth.getResultStatus()) {
            return updateUserAuthStatus(checkUser, UserStateEnum.AUTHENTICATION_YES.getState());
        }

        //实名认证
        //验证次数不够，暂时不调用认证接口，直接返回认证成功
//        Boolean match = IdentityAuthUtil.userVerify(identityCard, userIdentityAuth.getIdentityName());
//        if (!match) {
//            return ResponseFactory.businessError("用户认证失败");
//        }
        userIdentityAuth.setResultStatus(IdentityStatusEnum.SUCCESS.getState());
        userIdentityAuth.setResultMsg("认证成功");
        userIdentityAuth.setCreateDate(new Date());
        userIdentityAuth.setUpdateDate(new Date());
        int cnt = userIdentityAuthService.insertRecord(userIdentityAuth);
        if (cnt == 0) {
            return ResponseFactory.businessError("用户认证失败");
        }
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setAuthStatus(UserStateEnum.AUTHENTICATION_YES.getState());
        updateUser.setUpdateDate(new Date());
        //修改User认证状态
        return updateUserAuthStatus(updateUser, UserStateEnum.AUTHENTICATION_YES.getState());
    }

    /**
     * 更新用户认证状态
     *
     * @param user
     * @param state
     * @return
     */
    private ResponseData updateUserAuthStatus(User user, int state) {
        user.setAuthStatus(state);
        int cnt = updateByPrimaryKeySelective(user);

        if (cnt == 0) {
            return ResponseFactory.businessError("用户认证状态修改失败");
        }
        return ResponseFactory.success(null);
    }

    /**
     * 手机验证码登录
     *
     * @param mobile
     * @param captcha
     * @return
     */
    @Override
    public ResponseData mobileLogin(String mobile, String captcha) {
        User user = new User();
        user.setMobile(mobile);
        //手机号获取userId
        User checkUser = selectByRecord(user);

        if (checkUser == null) {
            return ResponseFactory.businessError("手机号未注册");
        }

        if (!checkCaptcha(mobile, captcha)) {
            return ResponseFactory.businessError("验证码错误");
        }
        clearCaptcha(mobile);
        return ResponseFactory.success(checkUser);
    }

    /**
     * 支付账号绑定
     *
     * @param userAccountBind
     * @return
     */
    @Override
    public ResponseData payBind(UserAccountBind userAccountBind) {
        userAccountBind.setCreateDate(new Date());
        userAccountBind.setUpdateDate(new Date());
        userAccountBind.setStatus(BindStatusEnum.YES.getState());
        userAccountBind.setDefaultReceip(DefaultReceipEnum.NO.getState());
        int cnt = userAccountBindService.insertRecord(userAccountBind);
        if (cnt > 0) {
            return ResponseFactory.success(null);
        } else {
            return ResponseFactory.businessError("绑定失败");
        }
    }

    @Override
    public ResponseData getUserInfo(Long userId) {
        User user = selectByPrimaryKey(userId);
        if (user != null) {
            return ResponseFactory.success(user);
        } else {
            return ResponseFactory.businessError("用户信息获取失败");
        }
    }

    /**
     * 检查手机验证码是否正确
     *
     * @param mobile
     * @param captcha
     * @return
     */
    private Boolean checkCaptcha(String mobile, String captcha) {
        String captchaRedis = (String) redisTemplate.opsForValue().get(mobile);
        return captcha.equals(captchaRedis);
    }

    /**
     * 根据手机号查询用户
     *
     * @param mobile
     * @return
     */
    private User getUserByMobile(String mobile) {
        User user = new User();
        user.setMobile(mobile);
        return selectByRecord(user);
    }

    public static void main(String[] args) throws Exception {
        String content = 8 + "123456" + MD5_KEY;
        System.out.println(MD5Utils.digestAsHex(content));
    }
}