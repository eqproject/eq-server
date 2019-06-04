package org.eq.modules.bc.common.util;

import org.eq.modules.bc.common.log.Logger;
import org.eq.modules.bc.common.log.LoggerFactory;

import javax.servlet.http.HttpServletRequest;


public class NetUtil {
	private static Logger logger =  LoggerFactory.getLogger(NetUtil.class);
//	/***
//	 * 获取客户端IP地址;这里通过了Nginx获取;X-Real-IP,
//	 * @param request
//	 * @return
//	 */
//	public static String getClientIp(HttpServletRequest request) {
//		String fromSource = "X-Real-IP";
//		String ip = request.getHeader("X-Real-IP");
//		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getHeader("X-Forwarded-For");
//			fromSource = "X-Forwarded-For";
//		}
//		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getHeader("Proxy-Client-IP");
//			fromSource = "Proxy-Client-IP";
//		}
//		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getHeader("WL-Proxy-Client-IP");
//			fromSource = "WL-Proxy-Client-IP";
//		}
//		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getRemoteAddr();
//			fromSource = "request.getRemoteAddr";
//		}
//		logger.debug("App Client IP: "+ip+", fromSource: "+fromSource);
//		return ip;
//	}
	public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for"); 
        logger.info("x-forwarded-for ip: " + ip);
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {  
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if( ip.indexOf(",")!=-1 ){
                ip = ip.split(",")[0];
            }
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
            logger.info("Proxy-Client-IP ip: " + ip);
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
            logger.info("WL-Proxy-Client-IP ip: " + ip);
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
            logger.info("HTTP_CLIENT_IP ip: " + ip);
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
            logger.info("HTTP_X_FORWARDED_FOR ip: " + ip);
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("X-Real-IP");  
            logger.info("X-Real-IP ip: " + ip);
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
            logger.info("getRemoteAddr ip: " + ip);
        } 
        return ip;  
    }	
}
