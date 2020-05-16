package com.watersfall.clocgame.action;

import com.watersfall.clocgame.exception.NationNotFoundException;
import com.watersfall.clocgame.model.SpamAction;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.treaty.TreatyMember;
import com.watersfall.clocgame.text.Responses;
import com.watersfall.clocgame.util.Util;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class TreatyActions
{
	private static void uploadFlag(HttpServletRequest req, BufferedImage part, int user) throws IOException
	{
		String directory = Util.DIRECTORY + File.separator + "treaty" + File.separator + user + ".png";
		Util.uploadImage(part, directory);
	}

	private static String check(TreatyMember member, String string, int length)
	{
		if(string == null)
		{
			return Responses.nullFields();
		}
		else if(string.length() > length)
		{
			return Responses.tooLong();
		}
		else if(!(member.isFounder() || member.isEdit() || member.isManage()))
		{
			return Responses.noPermission();
		}
		else
		{
			return null;
		}
	}

	public static String updateFlag(HttpServletRequest req, TreatyMember member, Part flag) throws SQLException, IOException
	{
		if(Util.checkSpamAndInsertIfNot(SpamAction.UPDATE_ALLIANCE_FLAG, member.getIdTreaty(), member.getConn()))
		{
			return Responses.noSpam();
		}
		else if(!(member.isFounder() || member.isEdit() || member.isManage()))
		{
			return Responses.noPermission();
		}
		BufferedImage image = ImageIO.read(flag.getInputStream());
		if(image.getWidth() > 2048)
		{
			return Responses.tooLong("Flag width", 2048);
		}
		else if(image.getHeight() > 768 * 2)
		{
			return Responses.tooLong("Flag height", 768 * 2);
		}
		else
		{
			uploadFlag(req, image, member.getTreaty().getId());
			member.getTreaty().setFlag(member.getTreaty().getId() + ".png");
			member.getTreaty().update(member.getConn());
			return Responses.updated("Flag");
		}
	}

	public static String updateName(TreatyMember member, String name) throws SQLException
	{
		String check = check(member, name, 32);
		if(check != null)
		{
			return check;
		}
		else
		{
			member.getTreaty().setName(name);
			member.getTreaty().update(member.getConn());
			return Responses.updated("Name");
		}
	}

	public static String updateDescription(TreatyMember member, String description) throws SQLException
	{
		String check = check(member, description, 65535);
		if(check != null)
		{
			return check;
		}
		else
		{
			member.getTreaty().setDescription(description);
			member.getTreaty().update(member.getConn());
			return Responses.updated("Description");
		}
	}

	public static String invite(TreatyMember member, String name) throws SQLException
	{
		Nation nation;
		try
		{
			if(Util.checkSpamAndInsertIfNot(SpamAction.SEND_INVITE, member.getId(), member.getConn()))
			{
				return Responses.noSpam();
			}
			else if(member.isInvite() || member.isManage() || member.isFounder())
			{
				nation = Nation.getNationByName(member.getConn(), name, member.isSafe());
				return nation.inviteToTreaty(member.getTreaty().getId(), member);
			}
			else
			{
				return Responses.noPermission();
			}
		}
		catch(NationNotFoundException e)
		{
			return Responses.noNation();
		}

	}

	public static String kick(TreatyMember member, String name) throws SQLException
	{
		TreatyMember nation = new TreatyMember(member.getConn(), Nation.getNationByName(member.getConn(), name, member.isSafe()).getId(), member.isSafe());
		return member.kick(nation);
	}
}