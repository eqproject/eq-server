/**
 *  该类有generator 自动生成
 * Copyright &copy; 2017-2018 All rights reserved.
 */
package org.eq.modules.sms.controller;

import org.eq.basic.common.base.BaseController;
import org.eq.modules.common.entitys.ResponseData;
import org.eq.modules.sms.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 短信发送记录Controller
 * @author hobe
 * @version 2019-06-06
 */
@RestController
@RequestMapping(value = "/api/sms")
public class SmsController extends BaseController {

	@Autowired
	private SmsService smsService;

	@RequestMapping(value = "/send")
	public ResponseData send(HttpServletRequest request) {
		String mobile = request.getParameter("mobile");
		int type = Integer.parseInt(request.getParameter("type"));
		return smsService.send(mobile,type);
	}
}