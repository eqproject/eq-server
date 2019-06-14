package org.eq.modules.auth.service.impl;

import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.basic.common.util.StringLowUtils;
import org.eq.modules.auth.dao.UserMapper;
import org.eq.modules.auth.entity.User;
import org.eq.modules.auth.entity.UserAccountBind;
import org.eq.modules.auth.entity.UserExample;
import org.eq.modules.auth.entity.UserIdentityAuth;
import org.eq.modules.auth.service.UserAccountBindService;
import org.eq.modules.auth.service.UserIdentityAuthService;
import org.eq.modules.auth.service.UserService;
import org.eq.modules.bc.entity.BcTxRecord;
import org.eq.modules.common.entitys.ResponseData;
import org.eq.modules.common.factory.ResponseFactory;
import org.eq.modules.enums.BindStatusEnum;
import org.eq.modules.enums.DefaultReceipEnum;
import org.eq.modules.enums.StateEnum;
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
import java.util.Map;

/**
 * 用户管理ServiceImpl
 *
 * @author hobe
 * @version 2019-05-30
 */
@Service
@Transactional
@AutowiredService
public class UserServiceImpl extends ServiceImplExtend<UserMapper, User, UserExample> implements UserService {
    public UserServiceImpl(UserMapper mapper) {
        super.setMapper(mapper);
    }

    private static final String MD5_KEY = "org.eq.modules";

    @Value("${aes.key}")
    private String aesKey;

    @Autowired
    private UserWalletService userWalletService;

    @Autowired
    private BcTxRecordService bcTxRecordService;

    @Autowired
    private UserIdentityAuthService userIdentityAuthService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserAccountBindService userAccountBindService;

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

    @Override
    public ResponseData register(String mobile, String captcha) {
        if (mobile == null) {
            return ResponseFactory.paramsError("电话号码为空");
        }

        if (captcha == null) {
            return ResponseFactory.paramsError("验证码为空");
        }
        //检查验证码
        if (!checkCaptcha(mobile, captcha)) {
            return ResponseFactory.paramsError("验证码错误");
        }

        // 1.添加用户
        User user = new User();
        user.setMobile(mobile);
        //检查是否已注册
        User checkUser = checkDuplicateMobile(user);
        //已注册直接返回成功
        if (checkUser != null) {
            user.setId(checkUser.getId());
            return ResponseFactory.success(user);
        }

        Long userId = saveUser(user);

        if (userId == null) {
            return ResponseFactory.systemError("注册失败");
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

        return ResponseFactory.success(user);
    }

    /**
     * 注册或登陆成功清楚验证码
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
        bcTxRecord.setFromAddress("平台账户");
        bcTxRecord.setTransferAmount("0.01");
        bcTxRecord.setAssetType(2);
        bcTxRecord.setTxStatus(0);
        bcTxRecord.setBizType(1);
        bcTxRecord.setCreateTime(new Date());
        bcTxRecord.setUpdateTime(new Date());
        bcTxRecord.setOptMetadata("激活账户");
        bcTxRecordService.insertRecordReturnId(bcTxRecord);
        return bcTxRecord.getId();
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
        insertRecordReturnId(user);
        return user.getId();
    }


    @Override
    public ResponseData reset(String userId, String pwd) {
        try {
            User user = new User();
            user.setId(Long.parseLong(userId));
            //AES解密
            String password = AESUtils.decrypt(pwd, aesKey);

            String content = userId + password + MD5_KEY;
            user.setPassword(MD5Utils.digestAsHex(content));
            user.setUpdateDate(new Date());
            int cnt = updateByPrimaryKeySelective(user);
            if (cnt > 0) {
                return ResponseFactory.success(null);
            } else {
                return ResponseFactory.error("密码修改失败", "1");
            }
        } catch (Exception e) {
            return ResponseFactory.error("密码修改失败", "1");
        }

    }

    @Override
    public ResponseData login(String userId, String pwd) {
        try {
            User user = new User();
            user.setId(Long.parseLong(userId));
            //AES解密
            String password = AESUtils.decrypt(pwd, aesKey);
            String content = userId + password + MD5_KEY;
            user.setPassword(MD5Utils.digestAsHex(content));
            User currUser = selectByRecord(user);
            if (currUser != null) {
                return ResponseFactory.success(null);
            } else {
                return ResponseFactory.error("登陆失败", "1");
            }
        } catch (Exception e) {
            return ResponseFactory.error("登陆失败", "1");
        }
    }

    @Override
    public ResponseData verify(UserIdentityAuth userIdentityAuth) {
        //实名认证
        userIdentityAuth.setResultStatus(1);
        userIdentityAuth.setCreateDate(new Date());
        userIdentityAuth.setUpdateDate(new Date());
        int cnt = userIdentityAuthService.insertRecord(userIdentityAuth);
        if (cnt > 0) {
            return ResponseFactory.success(null);
        } else {
            return ResponseFactory.error("认证失败", "1");
        }
    }

    @Override
    public ResponseData mobileLogin(String mobile, String captcha) {
        if (!checkCaptcha(mobile, captcha)) {
            return ResponseFactory.paramsError("验证码错误");
        }
        clearCaptcha(mobile);
        return ResponseFactory.success(null);
    }

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
            return ResponseFactory.error("绑定失败", "1");
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
     * 检查手机号重复注册
     *
     * @param user
     * @return
     */
    private User checkDuplicateMobile(User user) {
        return selectByRecord(user);
    }

}