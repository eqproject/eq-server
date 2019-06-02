/**
 * 该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.auth.service.impl;

import org.eq.basic.common.annotation.AutowiredService;
import org.eq.basic.common.base.ServiceImplExtend;
import org.eq.basic.common.util.StringLowUtils;
import org.eq.modules.auth.dao.UserMapper;
import org.eq.modules.auth.entity.User;
import org.eq.modules.auth.entity.UserExample;
import org.eq.modules.auth.service.UserService;
import org.eq.modules.auth.util.WalletUtil;
import org.eq.modules.common.entitys.ResponseData;
import org.eq.modules.common.factory.ResponseFactory;
import org.eq.modules.common.utils.AESUtils;
import org.eq.modules.common.utils.MD5Utils;
import org.eq.modules.wallet.entity.BcTxRecord;
import org.eq.modules.wallet.entity.UserWallet;
import org.eq.modules.wallet.service.BcTxRecordService;
import org.eq.modules.wallet.service.UserWalletService;
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

    private static final String MD5_KEY = "com.eq.modules";

    @Value("ase.key")
    private String aesKey;

    @Autowired
    private UserWalletService userWalletService;

    @Autowired
    private BcTxRecordService bcTxRecordService;

    @Autowired
    private RedisTemplate redisTemplate;

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
        String captchaRedis = (String) redisTemplate.opsForValue().get(mobile);
        if (!captcha.equals(captchaRedis)) {
            return ResponseFactory.paramsError("验证码错误");
        }

        // 1.添加用户
        User user = new User();
        user.setMobile(mobile);
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        insertRecordReturnId(user);
        // 2.生成钱包地址
        String address = WalletUtil.getWalletAddr();

        // 3.bc_tx_record插入一条记录
        BcTxRecord bcTxRecord = new BcTxRecord();
        bcTxRecord.setFromAddress("平台账户");
        bcTxRecord.setToAddress(address);
        bcTxRecord.setTransferAmount("0.01");
        bcTxRecord.setAssetType(2);
        bcTxRecord.setTxStatus(0);
        bcTxRecord.setBizType(1);
        bcTxRecord.setCreateTime(new Date());
        bcTxRecord.setUpdateTime(new Date());
        bcTxRecord.setOptMetadata("激活账户");

        bcTxRecordService.insertRecordReturnId(bcTxRecord);

        UserWallet wallet = new UserWallet();
        wallet.setUserId(user.getId());
        wallet.setAddress(address);
        wallet.setStatus(0);
        wallet.setTxId(bcTxRecord.getId());
        wallet.setCreateDate(new Date());
        wallet.setUpdateDate(new Date());

        // 4.插入user_wallet钱包地址
        userWalletService.insertRecordReturnId(wallet);

        return ResponseFactory.success(user);
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
            System.out.println("明文密码：" +password);
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
}