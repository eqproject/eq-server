/**
 *  该类由generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.sms.dao;

import org.eq.basic.common.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.eq.modules.sms.entity.SmsTemplate;
import org.eq.modules.sms.entity.SmsTemplateExample;

/**
 * 短信Mapper接口
 * @author hobe
 * @version 2019-06-08
 */
@Mapper
public interface SmsTemplateMapper extends BaseMapper<SmsTemplate,SmsTemplateExample> {

    SmsTemplate selectByType(int type);
}