package com.watersfall.clocgame.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class Md5
{
	/**
	 * Converts passwords into their md5 hash so that there's at least some form of
	 * password security
	 * @param password The password to hash
	 * @return The md5 hash of the password
	 */
	public static String md5(String password, String salt)
	{
		try
		{
			String temp = password + salt;
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] messageDigest = md.digest(temp.getBytes());
			BigInteger number = new BigInteger(1, messageDigest);
			return number.toString(16);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static String generateSalt()
	{
		SecureRandom random = new SecureRandom();
		byte[] bytes = new byte[32];
		random.nextBytes(bytes);
		Base64.Encoder encoder = Base64.getEncoder();
		return encoder.encodeToString(bytes);
	}
}
