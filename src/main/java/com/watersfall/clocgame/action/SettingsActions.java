package com.watersfall.clocgame.action;

import com.watersfall.clocgame.constants.Responses;
import com.watersfall.clocgame.model.nation.NationCosmetic;
import com.watersfall.clocgame.util.Util;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class SettingsActions
{
	private static void uploadFlag(HttpServletRequest req, BufferedImage part, int user) throws IOException
	{
		String directory = Util.DIRECTORY + File.separator + "flag" + File.separator + user + ".png";
		Util.uploadImage(part, directory);
	}

	private static void uploadPortrait(HttpServletRequest req, BufferedImage part, int user) throws IOException
	{
		String directory = Util.DIRECTORY + File.separator + "portrait" + File.separator + user + ".png";
		Util.uploadImage(part, directory);
	}

	private static String checkFlag(BufferedImage flag)
	{
		if(flag == null)
		{
			return Responses.nullFields();
		}
		else if(flag.getWidth() > 1024)
		{
			return Responses.tooLong("Flag width", 1024);
		}
		else if(flag.getHeight() > 768)
		{
			return Responses.tooLong("Flag height", 768);
		}
		else
		{
			return null;
		}
	}

	private static String checkPortrait(BufferedImage portrait)
	{
		if(portrait == null)
		{
			return Responses.nullFields();
		}
		else if(portrait.getWidth() > 768)
		{
			return Responses.tooLong("Portrait width", 768);
		}
		else if(portrait.getHeight() > 1024)
		{
			return Responses.tooLong("Portrait height", 1024);
		}
		else
		{
			return null;
		}
	}

	private static String checkNationTitle(String title)
	{
		if(title == null)
		{
			return Responses.nullFields();
		}
		else if(title.length() > 128)
		{
			return Responses.tooLong("Nation Title", 128);
		}
		else
		{
			return null;
		}
	}

	private static String checkLeaderTitle(String title)
	{
		if(title == null)
		{
			return Responses.nullFields();
		}
		else if(title.length() > 128)
		{
			return Responses.tooLong("Leader Title", 128);
		}
		else
		{
			return null;
		}
	}

	private static String checkDescription(String description)
	{
		if(description == null)
		{
			return Responses.nullFields();
		}
		else if(description.length() > 65536)
		{
			return Responses.tooLong("Description", 65536);
		}
		else
		{
			return null;
		}
	}


	public static String updateFlag(HttpServletRequest req, NationCosmetic cosmetic, Part flag) throws SQLException, IOException
	{
		BufferedImage image = ImageIO.read(flag.getInputStream());
		String check = checkFlag(image);
		if(check != null)
		{
			return check;
		}
		else
		{
			uploadFlag(req, image, cosmetic.getId());
			cosmetic.setFlag(cosmetic.getId() + ".png");
			cosmetic.update();
			return Responses.updated("Flag");
		}
	}

	public static String updatePortrait(HttpServletRequest req, NationCosmetic cosmetic, Part portrait) throws SQLException, IOException
	{
		BufferedImage image = ImageIO.read(portrait.getInputStream());
		String check = checkPortrait(image);
		if(check != null)
		{
			return check;
		}
		else
		{
			uploadPortrait(req, image, cosmetic.getId());
			cosmetic.setPortrait(cosmetic.getId() + ".png");
			cosmetic.update();
			return Responses.updated("Portrait");
		}
	}

	public static String updateNationTitle(NationCosmetic cosmetic, String title) throws SQLException
	{
		String check = checkNationTitle(title);
		if(check != null)
		{
			return check;
		}
		else
		{
			cosmetic.setNationTitle(title);
			cosmetic.update();
			return Responses.updated("Nation Title");
		}
	}

	public static String updateLeaderTitle(NationCosmetic cosmetic, String title) throws SQLException
	{
		String check = checkLeaderTitle(title);
		if(check != null)
		{
			return check;
		}
		else
		{
			cosmetic.setLeaderTitle(title);
			cosmetic.update();
			return Responses.updated("Leader Title");
		}
	}

	public static String updateDescription(NationCosmetic cosmetic, String description) throws SQLException
	{
		String check = checkDescription(description);
		if(check != null)
		{
			return check;
		}
		else
		{
			cosmetic.setDescription(description);
			cosmetic.update();
			return Responses.updated("Description");
		}
	}

	public static String updateAll(NationCosmetic cosmetic, String nationTitle, String leaderTitle, String description) throws SQLException
	{
		String checkNationTitle = checkNationTitle(nationTitle);
		if(checkNationTitle != null)
		{
			return checkNationTitle;
		}
		String checkLeaderTitle = checkLeaderTitle(leaderTitle);
		if(checkLeaderTitle != null)
		{
			return checkLeaderTitle;
		}
		String checkDescription = checkDescription(description);
		if(checkDescription != null)
		{
			return checkDescription;
		}
		cosmetic.setNationTitle(nationTitle);
		cosmetic.setLeaderTitle(leaderTitle);
		cosmetic.setDescription(description);
		cosmetic.update();
		return Responses.updated();
	}
}
