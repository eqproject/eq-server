package org.eq.modules.bc.common.util;

import java.math.BigDecimal;

public class AmountUtil {
	
	/**
	 * Get rid of the excess zeros.
	 * @param String
	 * @return String
	 */
	public static String rvZeroAndDot(String s){  
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
	
	/**
	 * 除以10的8次方
	 * @param amount
	 * @return
	 */
	public static String amountDivision10For8(long amount){
		
		return (new BigDecimal(amount).divide(new BigDecimal(100000000)).setScale(8, BigDecimal.ROUND_HALF_UP)).toPlainString();
		
		//
	}
	/**
	 * c乘以100
	 * @param amount
	 * @return
	 */
	public static long amountMultiply100(String amount){
		return (new BigDecimal(amount).multiply(new BigDecimal(100))).longValue();
	}
	
	public static long amountDivision100Long(long amount){
		return (new BigDecimal(amount).divide(new BigDecimal(100))).longValue();
		
	}
	
	public static long amountMultiply10ForAmountLong(String amount1,Integer amount2){
		return (new BigDecimal(amount1).multiply(new BigDecimal(Math.pow(10,amount2)))).stripTrailingZeros().longValue();
	}
	
	public static long amountMultiply10For8(String amount){
		return (new BigDecimal(amount).multiply(new BigDecimal(100000000))).longValue();
	}
	
	public static String amount1SubtractAmount2(String amount1, String amount2){
		return (new BigDecimal(amount1).subtract(new BigDecimal(amount2))).stripTrailingZeros().toPlainString();
	}
	
	public static String amount1AddAmount2(String amount1, String amount2){
		return (new BigDecimal(amount1).add(new BigDecimal(amount2))).stripTrailingZeros().toPlainString();
	}
	
	public static String amount1MultiplyAmount2(String amount1, String amount2){
		return (new BigDecimal(amount1).multiply(new BigDecimal(amount2))).stripTrailingZeros().toPlainString();
	}
	
	public static void main(String[] args) {
		System.out.println(amount1AddAmount2("0.0212312","0.01"));
	}
}
