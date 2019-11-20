package com.watersfall.clocgame.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Util
{
	public static int turn = 0;

	public static final String DIRECTORY = System.getenv("CLOC_FILE_PATH");

	public static void uploadImage(BufferedImage part, String path) throws IOException
	{
		System.out.println(System.getenv("CLOC_FILE_PATH"));
		ImageIO.write(part, "png", new File(path));
	}

	/**
	 * Converts a URL with path parameters into a HashMap with variable-value pairs<br>
	 * URL parameters should be wrapped in {brackets}, so a url parameter string would look like
	 * <code>thing/{id}/{thing}</code>
	 * With an actual URL of
	 * <code>thing/5/number</code>
	 * Would return a HashMap with keys "id" and "thing" with values "5" and "number", respectively
	 * @param varString The parameter URL
	 * @param url The actual URL
	 * @return A map with key-value pairs matching the URLs
	 */
	public static HashMap<String, String> urlConvert(String varString, String url)
	{
		HashMap<String, String> map = new HashMap<>();
		if(url == null || varString == null)
		{
			return map;
		}
		String[] varArray = varString.split("/");
		String[] urlArray = url.split("/");
		for(int i = 0; i < varArray.length; i++)
		{
			if(varArray[i].startsWith("{") && varArray[i].endsWith("}"))
			{
				String string = varArray[i];
				string = string.substring(1, string.length() - 1);
				if(urlArray.length > i)
				{
					map.put(string, urlArray[i]);
				}
				else
				{
					map.put(string, null);
				}
			}
		}
		return map;
	}
}
