package org.eq.modules.bc.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import io.bumo.mall.talent.common.util.StringRandom;
import io.bumo.mall.talent.enums.IdTypeEnum;

/**
 * create id generator 
 */
public class IdGenerator {
	
	/**服务器ID*/
//	private static final String  THIS_MACHINE_ID = "100";
	
	private static final Integer DEFAULT_UUID_LENGHT = 18;
	
	/**
	 * 根据时间生成唯一id （3位标识符+14位时间戳+6位随机数）
	 * @param userIdEnum
	 * @return
	 */
	public static String createNumberId(IdTypeEnum userIdEnum) {
		String dataStr = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		return userIdEnum.getCode() + dataStr + StringRandom.getStringRandomNumber(6);
	}
	
	/**
	 * 随机字符串生成唯一id （3位标识符+18位随机串）
	 * @param userIdEnum
	 * @return
	 */
	public static String createUUIDId(IdTypeEnum userIdEnum) {
		return createUUIDId(userIdEnum, DEFAULT_UUID_LENGHT);
	}
	
	/**
	 * 随机字符串生成唯一id （3位标识符+自定义长度的随机串）
	 * @param userIdEnum
	 * @param idLength
	 * @return
	 */
	public static String createUUIDId(IdTypeEnum userIdEnum, Integer idLength) {
		String uuid = getUUID();
		return userIdEnum.getCode() + uuid.substring(0, idLength);
	}
	
	private static String getRandomStr(Integer lengh) {
		return getUUID().substring(0, lengh);
	}
	
	 /** 
     * 获得一个UUID 
     * @return String UUID 
     */ 
	private static String getUUID() {
		String s = UUID.randomUUID().toString();
		// 去掉“-”符号
		return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
	} 
	
	
	
	


	public static void main(String[] args) {
		for(int i=0;i<1;i++) {
//			System.out.println(createNumberId(IdTypeEnum.GOODS_ORDER_ID));
			System.out.println(createUUIDId(IdTypeEnum.GOODS_ID));
//			System.out.println( new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()).length());
		}
	}

}
