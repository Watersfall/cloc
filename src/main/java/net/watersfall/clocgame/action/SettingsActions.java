package net.watersfall.clocgame.action;

import net.watersfall.clocgame.model.SpamAction;
import net.watersfall.clocgame.model.json.JsonFields;
import net.watersfall.clocgame.model.nation.Nation;
import net.watersfall.clocgame.model.nation.NationCosmetic;
import net.watersfall.clocgame.text.Responses;
import net.watersfall.clocgame.util.Util;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class SettingsActions
{
	private static void uploadFlag(BufferedImage part, int user) throws IOException
	{
		String directory = Util.DIRECTORY + File.separator + "flag" + File.separator + user + ".png";
		Util.uploadImage(part, directory);
	}

	private static void uploadPortrait(BufferedImage part, int user) throws IOException
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
		else if(description.length() > 65535)
		{
			return Responses.tooLong("Description", 65535);
		}
		else
		{
			return null;
		}
	}


	public static String updateFlag(Nation nation, Part part) throws SQLException, IOException
	{
		BufferedImage image = ImageIO.read(part.getInputStream());
		NationCosmetic cosmetic = nation.getCosmetic();
		String check = checkFlag(image);
		if(Util.checkSpamAndInsertIfNot(SpamAction.UPDATE_FLAG, nation.getId(), nation.getConn()))
		{
			return Responses.noSpam();
		}
		else if(check != null)
		{
			return check;
		}
		else
		{
			uploadFlag(image, cosmetic.getId());
			cosmetic.setFlag(cosmetic.getId() + ".png?v=" + System.currentTimeMillis());
			return Responses.updated("Flag");
		}
	}

	public static String updatePortrait(Nation nation, Part part) throws SQLException, IOException
	{
		BufferedImage image = ImageIO.read(part.getInputStream());
		NationCosmetic cosmetic = nation.getCosmetic();
		String check = checkPortrait(image);
		if(Util.checkSpamAndInsertIfNot(SpamAction.UPDATE_PORTRAIT, nation.getId(), nation.getConn()))
		{
			return Responses.noSpam();
		}
		else if(check != null)
		{
			return check;
		}
		else
		{
			uploadPortrait(image, cosmetic.getId());
			cosmetic.setPortrait(cosmetic.getId() + ".png?v=" + System.currentTimeMillis());
			return Responses.updated("Portrait");
		}
	}

	public static String updateNationTitle(Nation nation, String title) throws SQLException
	{
		String check = checkNationTitle(title);
		NationCosmetic cosmetic = nation.getCosmetic();
		JSONObject object = new JSONObject();
		if(check != null)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), check);
		}
		else
		{
			cosmetic.setNationTitle(title);
			object.put(JsonFields.SUCCESS.name(), true);
			object.put(JsonFields.MESSAGE.name(), Responses.updated("Nation Title"));
		}
		return object.toString();
	}

	public static String updateLeaderTitle(Nation nation, String title) throws SQLException
	{
		String check = checkLeaderTitle(title);
		NationCosmetic cosmetic = nation.getCosmetic();
		JSONObject object = new JSONObject();
		if(check != null)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), check);
		}
		else
		{
			cosmetic.setLeaderTitle(title);
			object.put(JsonFields.SUCCESS.name(), true);
			object.put(JsonFields.MESSAGE.name(), Responses.updated("Leader Title"));
		}
		return object.toString();
	}

	public static String updateDescription(Nation nation, String description) throws SQLException
	{
		String check = checkDescription(description);
		NationCosmetic cosmetic = nation.getCosmetic();
		JSONObject object = new JSONObject();
		if(check != null)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), check);
		}
		else
		{
			cosmetic.setDescription(description);
			object.put(JsonFields.SUCCESS.name(), true);
			object.put(JsonFields.MESSAGE.name(), Responses.updated("Description"));
		}
		return object.toString();
	}

	public static String updateAll(Nation nation, String nationTitle, String leaderTitle, String description) throws SQLException
	{
		String checkNationTitle = checkNationTitle(nationTitle);
		NationCosmetic cosmetic = nation.getCosmetic();
		JSONObject object = new JSONObject();
		if(checkNationTitle != null)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), checkNationTitle);
		}
		String checkLeaderTitle = checkLeaderTitle(leaderTitle);
		if(checkLeaderTitle != null)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), checkLeaderTitle);
		}
		String checkDescription = checkDescription(description);
		if(checkDescription != null)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), checkDescription);
		}
		cosmetic.setNationTitle(nationTitle);
		cosmetic.setLeaderTitle(leaderTitle);
		cosmetic.setDescription(description);
		object.put(JsonFields.SUCCESS.name(), false);
		object.put(JsonFields.MESSAGE.name(), Responses.updated());
		return object.toString();
	}
}
