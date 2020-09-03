package com.watersfall.clocgame.action;

import com.watersfall.clocgame.dao.InviteDao;
import com.watersfall.clocgame.dao.NationDao;
import com.watersfall.clocgame.dao.NewsDao;
import com.watersfall.clocgame.exception.NationNotFoundException;
import com.watersfall.clocgame.model.SpamAction;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.nation.News;
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
	private static boolean canInteractWith(Nation nation1, Nation nation2)
	{
		if(nation1.equals(nation2))
		{
			return false;
		}
		else if(nation1.getTreatyPermissions().isFounder() || nation2.getTreatyPermissions().getRoles().isEmpty())
		{
			return true;
		}
		else
		{
			return (nation1.getTreatyPermissions().isManage() && !nation2.getTreatyPermissions().isManage());
		}
	}

	private static void uploadFlag(HttpServletRequest req, BufferedImage part, int user) throws IOException
	{
		String directory = Util.DIRECTORY + File.separator + "treaty" + File.separator + user + ".png";
		Util.uploadImage(part, directory);
	}

	public static String acceptInvite(Nation nation, int treaty) throws SQLException
	{
		return nation.joinTreaty(treaty);
	}

	public static String rejectInvite(Nation nation, int treaty) throws SQLException
	{
		if(nation.getInvites().contains(treaty))
		{
			new InviteDao(nation.getConn(), true).deleteInvite(nation.getId(), treaty);
			return Responses.inviteRejected();
		}
		else
		{
			return Responses.noInvite();
		}
	}

	public static String resign(Nation nation) throws SQLException
	{
		return nation.leaveTreaty();
	}

	private static String check(Nation member, String string, int length)
	{
		if(string == null)
		{
			return Responses.nullFields();
		}
		else if(string.length() > length)
		{
			return Responses.tooLong();
		}
		else if(!(member.getTreatyPermissions().isFounder() || member.getTreatyPermissions().isEdit()
				|| member.getTreatyPermissions().isManage()))
		{
			return Responses.noPermission();
		}
		else
		{
			return null;
		}
	}

	public static String updateFlag(HttpServletRequest req, Nation member, Part flag) throws SQLException, IOException
	{
		if(Util.checkSpamAndInsertIfNot(SpamAction.UPDATE_ALLIANCE_FLAG, member.getId(), member.getConn()))
		{
			return Responses.noSpam();
		}
		else if(!(member.getTreatyPermissions().isFounder() || member.getTreatyPermissions().isEdit()
				|| member.getTreatyPermissions().isManage()))
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
			member.getTreaty().setFlag(member.getTreaty().getId() + ".png?v=" + System.currentTimeMillis());
			return Responses.updated("Flag");
		}
	}

	public static String updateName(Nation member, String name) throws SQLException
	{
		String check = check(member, name, 32);
		if(check != null)
		{
			return check;
		}
		else
		{
			member.getTreaty().setName(name);
			return Responses.updated("Name");
		}
	}

	public static String updateDescription(Nation member, String description) throws SQLException
	{
		String check = check(member, description, 65535);
		if(check != null)
		{
			return check;
		}
		else
		{
			member.getTreaty().setDescription(description);
			return Responses.updated("Description");
		}
	}

	public static String invite(Nation member, String name) throws SQLException
	{
		Nation nation;
		try
		{
			if(Util.checkSpamAndInsertIfNot(SpamAction.SEND_INVITE, member.getId(), member.getConn()))
			{
				return Responses.noSpam();
			}
			else if(member.getTreatyPermissions().isInvite() || member.getTreatyPermissions().isManage()
					|| member.getTreatyPermissions().isFounder())
			{
				NationDao dao = new NationDao(member.getConn(), true);
				InviteDao inviteDao = new InviteDao(member.getConn(), true);
				nation = dao.getNationByName(name);
				if(nation == null)
				{
					return Responses.noNation();
				}
				else if(nation.getInvites().contains(member.getTreaty().getId()))
				{
					return Responses.alreadyInvited();
				}
				else
				{
					inviteDao.createInvite(nation.getId(), member.getTreaty().getId());
					NewsDao newsDao = new NewsDao(member.getConn(), true);
					String message = News.createMessage(News.ID_TREATY_INVITE, member.getNationUrl(),
							member.getTreaty().getTreatyUrl());
					newsDao.createNews(member.getId(), nation.getId(), message);
					return Responses.invited();
				}
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

	public static String kick(Nation member, Nation personToKick) throws SQLException
	{
		if(member.getTreaty().getId() != personToKick.getTreaty().getId())
		{
			return Responses.notYourTreaty();
		}
		else if(!(member.getTreatyPermissions().isKick() || member.getTreatyPermissions().isManage()
				|| member.getTreatyPermissions().isFounder()))
		{
			return Responses.noPermission();
		}
		else if(!canInteractWith(member, personToKick))
		{
			return Responses.noPermission();
		}
		else
		{
			personToKick.leaveTreaty();
			return Responses.kicked();
		}
	}

	public static String toggleEdit(Nation member, int id) throws SQLException
	{
		if(!member.getTreatyPermissions().isFounder())
		{
			return Responses.noPermission();
		}
		else
		{
			NationDao dao = new NationDao(member.getConn(), true);
			Nation personToToggle = dao.getCosmeticNationById(id);
			personToToggle.getTreatyPermissions().setEdit(!personToToggle.getTreatyPermissions().isEdit());
			dao.saveNation(personToToggle);
			return Responses.updated();
		}
	}

	public static String toggleInvite(Nation member, int id) throws SQLException
	{
		if(!member.getTreatyPermissions().isFounder())
		{
			return Responses.noPermission();
		}
		else
		{
			NationDao dao = new NationDao(member.getConn(), true);
			Nation personToToggle = dao.getCosmeticNationById(id);
			personToToggle.getTreatyPermissions().setInvite(!personToToggle.getTreatyPermissions().isInvite());
			dao.saveNation(personToToggle);
			return Responses.updated();
		}
	}

	public static String toggleKick(Nation member, int id) throws SQLException
	{
		if(!member.getTreatyPermissions().isFounder())
		{
			return Responses.noPermission();
		}
		else
		{
			NationDao dao = new NationDao(member.getConn(), true);
			Nation personToToggle = dao.getCosmeticNationById(id);
			personToToggle.getTreatyPermissions().setKick(!personToToggle.getTreatyPermissions().isKick());
			dao.saveNation(personToToggle);
			return Responses.updated();
		}
	}

	public static String toggleManage(Nation member, int id) throws SQLException
	{
		if(!member.getTreatyPermissions().isFounder())
		{
			return Responses.noPermission();
		}
		else
		{
			NationDao dao = new NationDao(member.getConn(), true);
			Nation personToToggle = dao.getCosmeticNationById(id);
			personToToggle.getTreatyPermissions().setManage(!personToToggle.getTreatyPermissions().isManage());
			dao.saveNation(personToToggle);
			return Responses.updated();
		}
	}
}