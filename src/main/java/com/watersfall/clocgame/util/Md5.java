package com.watersfall.clocgame.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5
{
	public static String md5(String password)
	{
		try
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(password.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			return number.toString(16);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
