package org.eq.modules.sms.service.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.eq.basic.common.annotation.AutowiredService;
import org.eq.modules.common.entitys.ResponseData;
import org.eq.modules.common.enums.SmsStatusEnum;
import org.eq.modules.common.enums.SmsTypeEnum;
import org.eq.modules.common.factory.ResponseFactory;
import org.eq.modules.sms.dao.SmsLogMapper;
import org.eq.modules.sms.dao.SmsTemplateMapper;
import org.eq.modules.sms.entity.SmsLog;
import org.eq.modules.sms.entity.SmsTemplate;
import org.eq.modules.sms.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

@Service
@AutowiredService
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
        SmsTypeEnum smsTypeEnum = SmsTypeEnum.getEnumByState(type);
        String content = "";
        //发送短信
        switch (smsTypeEnum) {
            case REGISTER:
                //生成验证码
                String code = RandomStringUtils.random(4, false, true);
                content = template.getContent().replace("{#code}", code);
                System.out.println(content);
                //发送短信
                //判断是否超出一天的限制
                int limitDay = template.getLimitDay();
                HashMap<String, Integer> templateMap = (HashMap<String, Integer>) redisTemplate.opsForValue().get("SmsTemplate");
                int cnt = 0;
                if (templateMap != null) {
                    cnt = templateMap.get(String.valueOf(type));
                } else {
                    templateMap = new HashMap<>();
                }
                if (cnt < limitDay) {
                    //SmsUtils.send(mobile, content);
                    System.out.println("发送成功,发送次数：" + cnt);
                    templateMap.put(String.valueOf(type), cnt + 1);
                    redisTemplate.opsForValue().set("SmsTemplate", templateMap);
                }
                break;
            case BUY_AD:
                break;
            case SALE_AD:
                break;
            case BUY_SUCESS:
                break;
            case SUCESS:
                break;
            case PAY:
                break;
            default:
                break;
        }

        //保存发送短信记录
        SmsLog smsLog = new SmsLog();
        smsLog.setUserId(0L);
        smsLog.setSmsTemplateId(template.getId());
        smsLog.setSmsTemplateName(template.getName());
        smsLog.setMobile(mobile);
        smsLog.setStatus(SmsStatusEnum.SENDING.getState());
        smsLog.setContent(content);
        smsLog.setCreateDate(new Date());
        smsLog.setUpdateDate(new Date());
        smsLogMapper.insert(smsLog);


        return ResponseFactory.success(null);
    }


}
