package com.watersfall.clocgame.action;

import com.watersfall.clocgame.constants.Responses;
import com.watersfall.clocgame.model.nation.NationCosmetic;

import java.sql.SQLException;

public class SettingsActions
{
	private static String checkFlag(String flag)
	{
		if(flag == null)
		{
			return Responses.nullFields();
		}
		else if(flag.length() > 128)
		{
			return Responses.tooLong("Flag", 128);
		}
		else
		{
			return null;
		}
	}

	private static String checkPortrait(String portrait)
	{
		if(portrait == null)
		{
			return Responses.nullFields();
		}
		else if(portrait.length() > 128)
		{
			return Responses.tooLong("Portrait", 128);
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


	public static String updateFlag(NationCosmetic cosmetic, String flag) throws SQLException
	{
		String check = checkFlag(flag);
		if(check != null)
		{
			return check;
		}
		else
		{
			cosmetic.setFlag(flag);
			cosmetic.update();
			return Responses.updated("Flag");
		}
	}

	public static String updatePortrait(NationCosmetic cosmetic, String portrait) throws SQLException
	{
		String check = checkPortrait(portrait);
		if(check != null)
		{
			return check;
		}
		else
		{
			cosmetic.setPortrait(portrait);
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

	public static String updateAll(NationCosmetic cosmetic, String flag, String portrait, String nationTitle, String leaderTitle, String description) throws SQLException
	{
		String checkFlag = checkFlag(flag);
		if(checkFlag != null)
		{
			return checkFlag;
		}
		String checkPortrait = checkPortrait(portrait);
		if(checkPortrait != null)
		{
			return checkPortrait;
		}
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
		cosmetic.setFlag(flag);
		cosmetic.setPortrait(portrait);
		cosmetic.setNationTitle(nationTitle);
		cosmetic.setLeaderTitle(leaderTitle);
		cosmetic.setDescription(description);
		cosmetic.update();
		return Responses.updated();
	}
}
