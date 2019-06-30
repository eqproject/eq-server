package org.eq.modules.sms.service.impl;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.RandomStringUtils;
import org.eq.basic.common.util.DateUtil;
import org.eq.modules.common.entitys.ResponseData;
import org.eq.modules.common.enums.SmsStatusEnum;
import org.eq.modules.common.enums.SmsTypeEnum;
import org.eq.modules.common.factory.ResponseFactory;
import org.eq.modules.enums.SystemConfigTypeEnum;
import org.eq.modules.sms.dao.SmsLogMapper;
import org.eq.modules.sms.dao.SmsTemplateMapper;
import org.eq.modules.sms.entity.SmsLog;
import org.eq.modules.sms.entity.SmsTemplate;
import org.eq.modules.sms.service.SmsService;
import org.eq.modules.sms.util.SmsUtils;
import org.eq.modules.support.entity.SystemConfig;
import org.eq.modules.support.service.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    SmsLogMapper smsLogMapper;
    @Autowired
    SmsTemplateMapper smsTemplateMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    SystemConfigService systemConfigService;

    @Override
    public ResponseData send(String mobile, int type) {
        String prefix = "Smstemplate";
        String suffix = "check";
        //获取配置参数
        SystemConfig config = systemConfigService.getSystemConfigByType(SystemConfigTypeEnum.RISK_SMS.getType());
        String defer = (String) redisTemplate.opsForValue().get(mobile + type + suffix);
        if (defer != null) {
            return ResponseFactory.businessError("请求过于频繁，请稍后重试！");
        }
        //获取模板
        SmsTemplate template = smsTemplateMapper.selectByType(type);
        String content = template.getContent();

        //短信平台风控，模板每天限制次数
        Integer limitDay = template.getLimitDay();
        String platKey = prefix + type;
        Integer platCount = (Integer) redisTemplate.opsForValue().get(platKey);
        if (platCount != null) {
            if (platCount >= limitDay) {
                return ResponseFactory.businessError("短信发送次数超过平台限制！");
            } else {
                redisTemplate.opsForValue().increment(platKey, 1);
            }
        } else {
            redisTemplate.opsForValue().set(platKey, 1);
            redisTemplate.expireAt(platKey, DateUtil.getNextDayTime());
        }


        String json = config.getValue();
        Map configMap = JSON.parseObject(json, Map.class);
        Integer userLimit = (Integer) configMap.get("user");

        String userKey = prefix + type + mobile;
        Integer userCount = (Integer) redisTemplate.opsForValue().get(userKey);
        if (userCount != null) {
            if (userCount >= userLimit) {
                return ResponseFactory.businessError("短信发送次数超过用户限制！");
            } else {
                redisTemplate.opsForValue().increment(userKey, 1);
                redisTemplate.opsForValue().set(mobile + type + suffix, "ok", 1, TimeUnit.MINUTES);
            }
        } else {
            redisTemplate.opsForValue().set(userKey, 1);
            redisTemplate.expireAt(userKey, DateUtil.getNextDayTime());
            redisTemplate.opsForValue().set(mobile + type + suffix, "ok", 1, TimeUnit.MINUTES);
        }
        if (SmsTypeEnum.REGISTER.getState() == type) {
            //注册登录短信验证码
            //生成验证码
            String code = RandomStringUtils.random(4, false, true);
            redisTemplate.opsForValue().set(mobile, code, 5, TimeUnit.MINUTES);
            content = content.replace("{#code}", code);
            SmsUtils.send(mobile, content);
        } else {
            SmsUtils.send(mobile, content);
        }
        //保存发送短信记录
        int row = insertSmsLog(mobile, content, template);
        if (row > 0) {
            return ResponseFactory.success(null);
        } else {
            return ResponseFactory.systemError("写入短信发送记录异常");
        }

    }

    /**
     * 写入发送短信记录
     *
     * @param mobile
     * @param content
     * @param template
     * @return
     */
    private int insertSmsLog(String mobile, String content, SmsTemplate template) {
        SmsLog smsLog = new SmsLog();
        smsLog.setUserId(0L);
        smsLog.setSmsTemplateId(template.getId());
        smsLog.setSmsTemplateName(template.getName());
        smsLog.setMobile(mobile);
        smsLog.setStatus(SmsStatusEnum.SENDING.getState());
        smsLog.setContent(content);
        smsLog.setCreateDate(new Date());
        smsLog.setUpdateDate(new Date());
        return smsLogMapper.insert(smsLog);
    }


}
