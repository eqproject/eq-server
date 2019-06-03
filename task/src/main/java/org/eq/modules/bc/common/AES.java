package org.eq.modules.bc.common;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class AES {
public static boolean initialized = false;  
	
	/**
	 * AES解密
	 */
	public static byte[] decrypt(byte[] content, byte[] keyByte, byte[] ivByte) throws InvalidAlgorithmParameterException {
		initialize();
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
			Key sKeySpec = new SecretKeySpec(keyByte, "AES");
			
			cipher.init(Cipher.DECRYPT_MODE, sKeySpec, generateIV(ivByte));// 初始化 
			byte[] result = cipher.doFinal(content);
			return result;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();  
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();  
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}  
	
	public static void initialize(){  
        if (initialized) return;  
        Security.addProvider(new BouncyCastleProvider());  
        initialized = true;  
    }
    public static AlgorithmParameters generateIV(byte[] iv) throws Exception{  
        AlgorithmParameters params = AlgorithmParameters.getInstance("AES");  
        params.init(new IvParameterSpec(iv));  
        return params;  
    }  
    
    public static void main(String[] args) throws InvalidAlgorithmParameterException {
    	 String encryptedDataStr = "gBUxkxk9sG0WKK2nn2hwVfRWID6xBt1P+NRFXpcDALAu4FnhrRLuXG3WfmDg82NXVcMu0PnI3KigqNqV4M4uJsoTibY5nw7uuIxIeN/nzkNmd856LsV1oyFPA3x3+TN2m3FgWpXhNU0/1/EB2UUnWKpB91CwX/7P1AwJcFVUyGntWr7uuLHVYKk53D58nsuGtQpFUDrI0dp+3t+IK8fFJ5okE85MFEsvpkvviFvV8veGJWc0W2L4P5Vhcvzre6Wf5okoiSkXGnjxZ0LMmq15k4PL8fccgDeRaZTI89uhi5TX5WFK+6Nv3FdnBGmwOb/YF4xFxwTsgwA3VRPOeJpki4ecPkZIMzO+G5xKt+phwlP/w5eDFoZjx2Z+2RjFZKu+lFH6WM/V7Y8kyzUfK0+B3I4rSt1AWUFlEbpPtXKPEhC1LREpWaGKf2+0tFYuG8jk8agLLvrsrexLo9AT9bZEwg==";
         
         String key1 = "QS6vkoEeiCoSRcc6VS0Ujw==";
         
         String iv1Str = "6u02UWMciYDFwR5SmElPYQ==";
         //进行解密  
         byte[] data1 = decrypt(Base64.decodeBase64(encryptedDataStr), Base64.decodeBase64(key1),Base64.decodeBase64(iv1Str));  
         System.out.println("解密得到的数据 : " + new String(data1));  
	}
}
