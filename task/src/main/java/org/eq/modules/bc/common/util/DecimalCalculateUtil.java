package org.eq.modules.bc.common.util;

import java.math.BigDecimal;

public class DecimalCalculateUtil {
	
	
	private static final Integer defaultDecimal = 2;
	public static boolean isDouble4Str(String str){
		boolean flag = false;
		try{
			double d = Double.parseDouble(str);
			if(d>0){
				flag = true;
			}
		}catch(Exception e){
		}
		return flag;
	}
	
	public static boolean isLong4Str(String str){
		boolean flag = false;
		try{
			long l = Long.parseLong(str);
			if(l>0){
				flag = true;
			}
		}catch(Exception e){
		}
		return flag;
	}
	
	
	public static long amount10Pow(String str,Integer decimal){
		return (new BigDecimal(str).multiply(new BigDecimal(Math.pow(10,decimal)))).stripTrailingZeros().longValue();
	}
	/**
	 * 除以10的几次方
	 * @param amount
	 * @param decimal
	 * @return
	 */
	public static String divide10Pow(String amount,Integer decimal){
		return (new BigDecimal(amount).divide(new BigDecimal(Math.pow(10,decimal))).setScale(8, BigDecimal.ROUND_DOWN)).toPlainString();
	}
	
	public static Integer numberMultiply10Pow4Int(String str,Integer decimal){
		return new BigDecimal(str).multiply(new BigDecimal(Math.pow(10,decimal))).intValue();
	}
	
	public static Long numberMultiply10Pow4Long(String str,Integer decimal){
		return new BigDecimal(str).multiply(new BigDecimal(Math.pow(10,decimal))).longValue();
	}
	
	/**
	 * 保留两位小数
	 * @param str
	 * @param decimal
	 * @return
	 */
	public static String numberKeep2Decimals(String str){
		return new BigDecimal(str).setScale(2, BigDecimal.ROUND_DOWN).toPlainString();
	}
	
	/**
	 * 除10的几次方
	 * @param amount
	 * @param decimal 次方
	 * @return
	 */
	public static String numberDivide10Pow(Integer amount,Integer decimal){
		return (new BigDecimal(amount).divide(new BigDecimal(Math.pow(10,decimal))).setScale(decimal, BigDecimal.ROUND_DOWN)).toPlainString();
	}
	
	public static String numberMultiplNumber(String str1,String str2,Integer decimal){
		return (new BigDecimal(str1).multiply(new BigDecimal(str2)).setScale(decimal, BigDecimal.ROUND_DOWN)).toPlainString();
	}
	
	public static String numberMultiplNumberDefault(String str1,String str2){
		return numberMultiplNumber(str1,str2,defaultDecimal);
	}
	
	public static String numberMultiplNumber(String str1,String str2){
		return (new BigDecimal(str1).multiply(new BigDecimal(str2))).toPlainString();
	}
	
	
	public static String numberDivideNumber(String amount1,String amount2,Integer decimal){
		return (new BigDecimal(amount1).divide(new BigDecimal(amount2),decimal,BigDecimal.ROUND_DOWN)).toPlainString();
	}
	
	/**
	 * 去除无效的0
	 * @param s
	 * @return
	 */
	public static String rvZeroAndDot(String s){  
		s = new BigDecimal(s).toPlainString();
		if (s.isEmpty()) {
			return null;
	     }
		if(s.indexOf(".") > 0){  
			//去掉多余的0
			s = s.replaceAll("0+?$", "");  
			//如最后一位是.则去掉  
			s = s.replaceAll("[.]$", "");
		}  
		return s;  
	}
	
	public static String add(String number1,String number2){
		return (new BigDecimal(number1).add(new BigDecimal(number2))).toPlainString();
	}
	
	public static String subtract(String sourceAmount,String amount2){
		return new BigDecimal(sourceAmount).subtract(new BigDecimal(amount2)).stripTrailingZeros().toPlainString();
	}
	
	public static int compareTo(String amount1,String amount2){
		return new BigDecimal(amount1).compareTo(new BigDecimal(amount2));
	}
	
	public static void main(String[] args) {
		System.out.println(numberKeep2Decimals("0.002"));
	}
	
}
