package com.watersfall.clocgame.util;

import com.watersfall.clocgame.model.Region;
import com.watersfall.clocgame.model.SpamAction;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.HashMap;

public class Util
{

	public static final String DIRECTORY = System.getenv("CLOC_FILE_PATH");

	public static void uploadImage(BufferedImage part, String path) throws IOException
	{
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

	public static String formatNumber(int number)
	{
		return formatNumber((long) number);
	}

	public static String formatNumber(long number)
	{
		return String.format("%,d", number);
	}

	public static String formatNumber(double number)
	{
		DecimalFormat df = new DecimalFormat("#.##");
		return df.format(number);
	}

	public static int getTotalNations(Connection conn) throws SQLException
	{
		PreparedStatement statement = conn.prepareStatement("SELECT count(id) FROM cloc_login WHERE id>0");
		ResultSet results = statement.executeQuery();
		results.first();
		return results.getInt(1);
	}

	public static int getTotalNationsInRegion(Connection conn, Region region) throws SQLException
	{
		PreparedStatement statement = conn.prepareStatement("SELECT count(cloc_login.id) FROM cloc_login, cloc_foreign WHERE cloc_login.id=cloc_foreign.id AND cloc_foreign.region=?");
		statement.setString(1, region.name());
		ResultSet results = statement.executeQuery();
		results.first();
		return results.getInt(1);
	}

	public static int getTotalTreaties(Connection conn) throws SQLException
	{
		PreparedStatement statement = conn.prepareStatement("SELECT count(id) FROM cloc_treaties WHERE id>0");
		ResultSet results = statement.executeQuery();
		results.first();
		return results.getInt(1);
	}

	public static int getTotalDeclarations(Connection conn) throws SQLException
	{
		PreparedStatement statement = conn.prepareStatement("SELECT count(id) FROM cloc_declarations WHERE id>0");
		ResultSet results = statement.executeQuery();
		results.first();
		return results.getInt(1);
	}

	public static int getTotalOngoingWars(Connection conn) throws SQLException
	{
		PreparedStatement statement = conn.prepareStatement("SELECT count(id) FROM cloc_war WHERE end=-1");
		ResultSet results = statement.executeQuery();
		results.first();
		return results.getInt(1);
	}

	public static int getTotalEndedWars(Connection conn) throws SQLException
	{
		PreparedStatement statement = conn.prepareStatement("SELECT count(id) FROM cloc_war WHERE end>0");
		ResultSet results = statement.executeQuery();
		results.first();
		return results.getInt(1);
	}

	public static int getTotalSentMessages(Connection conn, int id) throws SQLException
	{
		PreparedStatement statement = conn.prepareStatement("SELECT count(id) FROM messages WHERE sender=?");
		statement.setInt(1, id);
		ResultSet results = statement.executeQuery();
		results.first();
		return results.getInt(1);
	}

	public static int getTotalReceivedMessages(Connection conn, int id) throws SQLException
	{
		PreparedStatement statement = conn.prepareStatement("SELECT count(id) FROM messages WHERE receiver=?");
		statement.setInt(1, id);
		ResultSet results = statement.executeQuery();
		results.first();
		return results.getInt(1);
	}

	public static boolean checkSpamAndInsertIfNot(SpamAction action, int id, Connection conn) throws SQLException
	{
		PreparedStatement check = conn.prepareStatement("SELECT count(*) FROM anti_spam WHERE action=? AND user=? AND time>?-?");
		check.setString(1, action.name());
		check.setInt(2, id);
		check.setLong(3, System.currentTimeMillis());
		check.setLong(4, action.getTime());
		ResultSet results = check.executeQuery();
		results.first();
		if(results.getInt(1) >= action.getAmount())
		{
			return true;
		}
		else
		{
			PreparedStatement insertSpam = conn.prepareStatement("INSERT INTO anti_spam (user, action, time) VALUES (?,?,?)");
			insertSpam.setInt(1, id);
			insertSpam.setString(2, action.name());
			insertSpam.setLong(3, System.currentTimeMillis());
			insertSpam.execute();
			return false;
		}
	}

	public static String convertToJson(ResultSet results) throws SQLException
	{
		String string =
				"{\n" +
						"\t\"months\":[\n";
		while(results.next())
		{
			string += "\t\t{\n";
			ResultSetMetaData data = results.getMetaData();
			for(int i = 1; i <= data.getColumnCount(); i++)
			{
				string += "\t\t\t\"" + data.getColumnName(i) + "\":" + results.getLong(i);
				if(i != data.getColumnCount())
				{
					string += ", ";
				}
				string += "\n";
			}
			string += "\t\t}";
			if(!results.isLast())
			{
				string += ",";
			}
			string += "\n";
		}
		string +=
				"\t]\n" +
						"}";
		return string;
	}

	public static void setPaginationAttributes(HttpServletRequest req, int page, String url, int maxPage)
	{
		req.setAttribute("url", url);
		req.setAttribute("page", page);
		req.setAttribute("maxPage", maxPage);
	}
}
