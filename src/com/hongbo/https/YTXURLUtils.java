/**
 * 
 */
package com.hongbo.https;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;

/**
 * @author HB
 *
 *         2017Äê6ÔÂ26ÈÕ
 */
public class YTXURLUtils {

	public static String MD5(String str) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] a = md5.digest(str.getBytes("utf-8"));
			str = byte2hex(a);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	private static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
			if (n < b.length - 1) {
				hs = hs + "";
			}
		}
		return hs.toUpperCase();
	}
	
	public static String convertDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(date);
	}

	public static String base64(String str) {
		try {
//			byte[] b = Base64.encodeBase64(str.getBytes("utf-8"));
			str = Base64.encodeBase64String(str.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	public static void main(String[] args) {
		String s = base64("123");
		System.out.println(s);
	}
}
