package net.watersfall.clocgame.action;

import net.watersfall.clocgame.dao.InviteDao;
import net.watersfall.clocgame.dao.NationDao;
import net.watersfall.clocgame.dao.NewsDao;
import net.watersfall.clocgame.exception.NationNotFoundException;
import net.watersfall.clocgame.model.SpamAction;
import net.watersfall.clocgame.model.json.JsonFields;
import net.watersfall.clocgame.model.nation.Nation;
import net.watersfall.clocgame.model.news.News;
import net.watersfall.clocgame.text.Responses;
import net.watersfall.clocgame.util.Util;
import org.json.JSONObject;

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
		else if(nation2.getTreatyPermissions().isFounder())
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
		JSONObject object = new JSONObject();
		object.put(JsonFields.MESSAGE.name(), nation.joinTreaty(treaty));
		return object.toString();
	}

	public static String rejectInvite(Nation nation, int treaty) throws SQLException
	{
		JSONObject object = new JSONObject();
		if(nation.getInvites().contains(treaty))
		{
			new InviteDao(nation.getConn(), true).deleteInvite(nation.getId(), treaty);
			object.put(JsonFields.SUCCESS.name(), true);
			object.put(JsonFields.MESSAGE.name(), Responses.deleted());
		}
		else
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.noInvite());
		}
		return object.toString();
	}

	public static String resign(Nation nation) throws SQLException
	{
		JSONObject object = new JSONObject();
		object.put(JsonFields.MESSAGE.name(), nation.leaveTreaty());
		return object.toString();
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
		JSONObject object = new JSONObject();
		String check = check(member, name, 32);
		if(check != null)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), check);
		}
		else
		{
			member.getTreaty().setName(name);
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.updated("Name"));
		}
		return object.toString();
	}

	public static String updateDescription(Nation member, String description) throws SQLException
	{
		JSONObject object = new JSONObject();
		String check = check(member, description, 65535);
		if(check != null)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), check);
		}
		else
		{
			member.getTreaty().setDescription(description);
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.updated("Description"));
		}
		return object.toString();
	}

	public static String invite(Nation member, String name) throws SQLException
	{
		JSONObject object = new JSONObject();
		Nation nation;
		try
		{
			if(Util.checkSpamAndInsertIfNot(SpamAction.SEND_INVITE, member.getId(), member.getConn()))
			{
				object.put(JsonFields.SUCCESS.name(), false);
				object.put(JsonFields.MESSAGE.name(), Responses.noSpam());
			}
			else if(member.getTreatyPermissions().isInvite() || member.getTreatyPermissions().isManage()
					|| member.getTreatyPermissions().isFounder())
			{
				NationDao dao = new NationDao(member.getConn(), true);
				InviteDao inviteDao = new InviteDao(member.getConn(), true);
				nation = dao.getNationByName(name);
				if(nation == null)
				{
					object.put(JsonFields.SUCCESS.name(), false);
					object.put(JsonFields.MESSAGE.name(), Responses.noNation());
				}
				else if(nation.getInvites().contains(member.getTreaty().getId()))
				{
					object.put(JsonFields.SUCCESS.name(), false);
					object.put(JsonFields.MESSAGE.name(), Responses.alreadyInvited());
				}
				else
				{
					inviteDao.createInvite(nation.getId(), member.getTreaty().getId());
					NewsDao newsDao = new NewsDao(member.getConn(), true);
					String message = News.createMessage(News.ID_TREATY_INVITE, member.getNationUrl(),
							member.getTreaty().getTreatyUrl());
					newsDao.createNews(member.getId(), nation.getId(), message);
					object.put(JsonFields.SUCCESS.name(), false);
					object.put(JsonFields.MESSAGE.name(), Responses.invited());
				}
			}
			else
			{
				object.put(JsonFields.SUCCESS.name(), false);
				object.put(JsonFields.MESSAGE.name(), Responses.noPermission());
			}
		}
		catch(NationNotFoundException e)
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.noNation());
		}
		return object.toString();
	}

	public static String kick(Nation member, Nation personToKick) throws SQLException
	{
		JSONObject object = new JSONObject();
		if(member.getTreaty().getId() != personToKick.getTreaty().getId())
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.notYourTreaty());
		}
		else if(!(member.getTreatyPermissions().isKick() || member.getTreatyPermissions().isManage()
				|| member.getTreatyPermissions().isFounder()))
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.noPermission());
		}
		else if(!canInteractWith(member, personToKick))
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.noPermission());
		}
		else
		{
			personToKick.leaveTreaty();
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.kicked());
		}
		return object.toString();
	}

	public static String toggleEdit(Nation member, int id) throws SQLException
	{
		JSONObject object = new JSONObject();
		if(!member.getTreatyPermissions().isFounder())
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.noPermission());
		}
		else
		{
			NationDao dao = new NationDao(member.getConn(), true);
			Nation personToToggle = dao.getCosmeticNationById(id);
			personToToggle.getTreatyPermissions().setEdit(!personToToggle.getTreatyPermissions().isEdit());
			dao.saveNation(personToToggle);
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.updated());
		}
		return object.toString();
	}

	public static String toggleInvite(Nation member, int id) throws SQLException
	{
		JSONObject object = new JSONObject();
		if(!member.getTreatyPermissions().isFounder())
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.noPermission());
		}
		else
		{
			NationDao dao = new NationDao(member.getConn(), true);
			Nation personToToggle = dao.getCosmeticNationById(id);
			personToToggle.getTreatyPermissions().setInvite(!personToToggle.getTreatyPermissions().isInvite());
			dao.saveNation(personToToggle);
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.updated());
		}
		return object.toString();
	}

	public static String toggleKick(Nation member, int id) throws SQLException
	{
		JSONObject object = new JSONObject();
		if(!member.getTreatyPermissions().isFounder())
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.noPermission());
		}
		else
		{
			NationDao dao = new NationDao(member.getConn(), true);
			Nation personToToggle = dao.getCosmeticNationById(id);
			personToToggle.getTreatyPermissions().setKick(!personToToggle.getTreatyPermissions().isKick());
			dao.saveNation(personToToggle);
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.updated());
		}
		return object.toString();
	}

	public static String toggleManage(Nation member, int id) throws SQLException
	{
		JSONObject object = new JSONObject();
		if(!member.getTreatyPermissions().isFounder())
		{
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.noPermission());
		}
		else
		{
			NationDao dao = new NationDao(member.getConn(), true);
			Nation personToToggle = dao.getCosmeticNationById(id);
			personToToggle.getTreatyPermissions().setManage(!personToToggle.getTreatyPermissions().isManage());
			dao.saveNation(personToToggle);
			object.put(JsonFields.SUCCESS.name(), false);
			object.put(JsonFields.MESSAGE.name(), Responses.updated());
		}
		return object.toString();
	}
}