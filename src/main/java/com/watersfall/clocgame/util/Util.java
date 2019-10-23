package com.watersfall.clocgame.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Util
{
	public static int turn = 0;

	public static final String DIRECTORY = System.getenv("CLOC_FILE_PATH");

	public static void uploadImage(BufferedImage part, String path) throws IOException
	{
		System.out.println(System.getenv("CLOC_FILE_PATH"));
		ImageIO.write(part, "png", new File(path));
	}
}
