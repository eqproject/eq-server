package org.eq.modules.bc.init;

public class KeyStoreManager {
	
	public final static Integer version =2;
	
	private static String pwd = "";
	
	public static void setKeyStorePwd(String password){
		pwd = password;
	}
	
	public static String getKeyStorePwd(){
		return pwd;
	}
	
}
