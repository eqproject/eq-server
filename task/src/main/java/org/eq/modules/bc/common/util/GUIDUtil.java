package org.eq.modules.bc.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;


public class GUIDUtil {
	private static String seedingString = "";
	private static String rawGUID = "";
	private static boolean bSecure ;
	private static Random myRand;
	private static SecureRandom mySecureRand;
	private static String s_id;
	public static final int BeforeMD5 = 1;
	public static final int AfterMD5 = 2;
	public static final int FormatString = 3;
	static {
		mySecureRand = new SecureRandom();
		long secureInitializer = mySecureRand.nextLong();
		myRand = new Random(secureInitializer);
		try {
			s_id = InetAddress.getLocalHost().toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Default constructor. With no specification of security option, this
	 * constructor defaults to lower security, high performance.
	 */
	public GUIDUtil() {
	}

	/*
	 * Constructor with security option. Setting secure true enables each random
	 * number generated to be cryptographically strong. Secure false defaults to
	 * the standard Random function seeded with a single cryptographically
	 * strong random number.
	 */
	public GUIDUtil(boolean secure) {
		bSecure = secure;
	}

	/*
	 * Method to generate the random GUID
	 */
	private static void getRandomGUID(boolean secure) {
		MessageDigest md5 = null;
		StringBuffer sbValueBeforeMD5 = new StringBuffer();
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Error:" + e);
		}
		try {
			long time = System.currentTimeMillis();
			long rand = 0;
			if (secure) {
				rand = mySecureRand.nextLong();
			} else {
				rand = myRand.nextLong();
			}
			sbValueBeforeMD5.append(s_id);
			sbValueBeforeMD5.append(":");
			sbValueBeforeMD5.append(Long.toString(time));
			sbValueBeforeMD5.append(":");
			sbValueBeforeMD5.append(Long.toString(rand));
			seedingString = sbValueBeforeMD5.toString();
			md5.update(seedingString.getBytes());
			byte[] array = md5.digest();
			StringBuffer sb = new StringBuffer();
			for (int j = 0; j < array.length; ++j) {
				int b = array[j] & 0xFF;
				if (b < 0x10)
					sb.append('0');
				sb.append(Integer.toHexString(b));
			}
			rawGUID = sb.toString();
		} catch (Exception e) {
			System.out.println("Error:" + e);
		}
	}

	public static String createNewGuid(int nFormatType, boolean secure) {
		getRandomGUID(secure);
		String sGuid = "";
		if (BeforeMD5 == nFormatType) {
			sGuid = GUIDUtil.seedingString;
		} else if (AfterMD5 == nFormatType) {
			sGuid = GUIDUtil.rawGUID;
		} else {
//			sGuid = GUIDUtil.toString();
		}
		return sGuid;
	}

	public static String createNewGuid(int nFormatType) {
		return GUIDUtil.createNewGuid(nFormatType, bSecure);
	}

	public static String createToken128(){
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(createNewGuid(GUIDUtil.AfterMD5)).append(createNewGuid(GUIDUtil.AfterMD5)).append(createNewGuid(GUIDUtil.AfterMD5)).append(createNewGuid(GUIDUtil.AfterMD5));
		return stringBuffer.toString();
	}
	
}
