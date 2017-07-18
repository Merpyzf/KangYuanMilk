package com.merpyzf.kangyuanmilk.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * MD5加密算法
 * @author wangke
 *
 */
public class Md5Helper {
	
	public static String GetMd5Str(String psd){
		
		return Loop(psd);
		
	}
	private static String Loop(String str)
	{
		String s = str;
		for(int i=0; i<15; i++)
		{
			s = Md5(s);
		}
		return s;
	}
	
	private static String Md5(String str)
	{
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			byte[] bs = digest.digest(str.getBytes());
			StringBuffer stringBuffer = new StringBuffer();
		
			for(byte b:bs){
				
				int i = b & 0xff;
				
				String hexString = Integer.toHexString(i);
				
				if(hexString.length()<2){
				
					hexString = "0"+hexString;
					
				}
				
				stringBuffer.append(hexString);
			}
			
			return stringBuffer.toString();
	
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
		}
		return null;
	}

}
