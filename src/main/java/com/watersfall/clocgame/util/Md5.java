package com.watersfall.clocgame.util;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Md5
{
	/**
	 * Converts passwords into their md5 hash so that there's at least some form of
	 * password security
	 * @param password The password to hash
	 * @return The md5 hash of the password
	 */
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
