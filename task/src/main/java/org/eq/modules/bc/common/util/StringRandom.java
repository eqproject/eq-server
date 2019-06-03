package org.eq.modules.bc.common.util;

import java.util.Random;

public class StringRandom {  
	private static String[] letter = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","P","Q","R","S","T","U","V","W","X","Y","Z",
			"a","b","c","d","e","f","g","h","i","j","k","l","m","n","p","q","r","s","t","u","v","w","x","y","z"};
	private static String[] numberArr = new String[]{"1","2","3","4","5","6","7","8","9"};
	
    //生成随机数字和字母,  
    public static String getStringRandom(int length) {  

        String val = "";  
        Random random = new Random();        
        //length为几位长度
        for(int i = 0; i < length; i++) {          
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";  
            //输出字母还是数字  
            if( "char".equalsIgnoreCase(charOrNum) ) {  
                int randomIndex = random.nextInt(49);
                val += letter[randomIndex];
            } else if("num".equalsIgnoreCase(charOrNum) ) {  
                val += numberArr[random.nextInt(9)]; 
            }  
        }  
        return val;  
    }  
    
    //生成随机数字
    public static String getStringRandomNumber(int length) {  
        String val = "";  
        Random random = new Random();        
        //length为几位长度
        for(int i = 0; i < length; i++) {          
           val += numberArr[random.nextInt(9)]; 
        }  
        return val;  
    }
} 