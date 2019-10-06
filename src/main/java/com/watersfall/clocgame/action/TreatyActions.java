package com.watersfall.clocgame.action;

import com.watersfall.clocgame.constants.Responses;
import com.watersfall.clocgame.exception.NationNotFoundException;
import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.treaty.TreatyMember;

import java.sql.SQLException;

public class TreatyActions
{
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

	public static String updateFlag(TreatyMember member, String flag) throws SQLException
	{
		String check = check(member, flag, 32);
		if(check != null)
		{
			return check;
		}
		else
		{
			member.getTreaty().setFlag(flag);
			member.getTreaty().update();
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
			member.getTreaty().update();
			return Responses.updated("Name");
		}
	}

	public static String updateDescription(TreatyMember member, String description) throws SQLException
	{
		String check = check(member, description, (1 << 15) - 1);
		if(check != null)
		{
			return check;
		}
		else
		{
			member.getTreaty().setDescription(description);
			member.getTreaty().update();
			return Responses.updated("Description");
		}
	}

	public static String invite(TreatyMember member, String name) throws SQLException
	{
		Nation nation;
		try
		{
			if(member.isInvite() || member.isManage() || member.isFounder())
			{
				nation = Nation.getNationByName(member.getConnection(), name, member.isSafe());
				return nation.getInvites().invite(nation, member);
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
		TreatyMember nation = new TreatyMember(member.getConnection(), Nation.getNationByName(member.getConnection(), name, member.isSafe()).getId(), member.isSafe());
		return member.kick(nation);
	}
}