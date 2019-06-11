package org.eq.modules.sms.service.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.eq.basic.common.util.DateUtil;
import org.eq.modules.common.entitys.ResponseData;
import org.eq.modules.common.enums.SmsStatusEnum;
import org.eq.modules.common.enums.SmsTypeEnum;
import org.eq.modules.common.factory.ResponseFactory;
import org.eq.modules.sms.dao.SmsLogMapper;
import org.eq.modules.sms.dao.SmsTemplateMapper;
import org.eq.modules.sms.entity.SmsLog;
import org.eq.modules.sms.entity.SmsTemplate;
import org.eq.modules.sms.service.SmsService;
import org.eq.modules.sms.util.SmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    SmsLogMapper smsLogMapper;
    @Autowired
    SmsTemplateMapper smsTemplateMapper;
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public ResponseData send(String mobile, int type) {
        //获取模板
        SmsTemplate template = smsTemplateMapper.selectByType(type);
        String content = template.getContent();

        //发送短信
        //判断是否超出一天的限制
        int limitDay = template.getLimitDay();
        String cacheDateStr = (String) redisTemplate.opsForValue().get("SmsTemplateCacheDate");
        String currDateStr = DateUtil.getNowTimeShortStr();
        HashMap<String, Integer> templateMap = null;
        if (cacheDateStr != null && cacheDateStr.equals(currDateStr)) {
            //同一天
            templateMap = (HashMap<String, Integer>) redisTemplate.opsForValue().get("SmsTemplate");
        } else {
            //初始化缓存
            redisTemplate.opsForValue().set("SmsTemplateCacheDate", DateUtil.getNowTimeShortStr());
        }
        int cnt = 0;
        if (templateMap != null) {
            if (templateMap.containsKey(String.valueOf(type))) {
                cnt = templateMap.get(String.valueOf(type));
            }
        } else {
            templateMap = new HashMap<>();
        }
        if (cnt < limitDay) {
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
            templateMap.put(String.valueOf(type), cnt + 1);
            redisTemplate.opsForValue().set("SmsTemplate", templateMap);
        }else{
            return ResponseFactory.error("超过当日短信发送次数限制","302");
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
