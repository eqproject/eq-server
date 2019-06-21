package org.eq.modules.bc.init;

public class KeyStoreManager {
	
	public final static Integer version =2;
	
	private static String pwd = "123456";
	
	public static void setKeyStorePwd(String password){
		pwd = password;
	}
	
	public static String getKeyStorePwd(){
		return pwd;
	}
	
}
