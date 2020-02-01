package com.watersfall.clocgame.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class Security
{
	public static String hash(String password)
	{
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

	public static boolean checkPassword(String password, String hash)
	{
		return BCrypt.checkpw(password, hash);
	}
}
