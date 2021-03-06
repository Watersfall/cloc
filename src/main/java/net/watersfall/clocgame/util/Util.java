package net.watersfall.clocgame.util;

import net.watersfall.clocgame.model.Region;
import net.watersfall.clocgame.model.SpamAction;
import net.watersfall.clocgame.model.TextKey;

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
		DecimalFormat df = new DecimalFormat("#,###.##");
		return df.format(number);
	}

	public static int getTotalNations(Connection conn) throws SQLException
	{
		PreparedStatement statement = conn.prepareStatement("SELECT count(id) FROM login WHERE id>0");
		ResultSet results = statement.executeQuery();
		results.first();
		return results.getInt(1);
	}

	public static int getTotalNationsInRegion(Connection conn, Region region) throws SQLException
	{
		PreparedStatement statement = conn.prepareStatement("SELECT count(login.id) FROM login, nation_stats WHERE login.id=nation_stats.id AND nation_stats.region=?");
		statement.setString(1, region.name());
		ResultSet results = statement.executeQuery();
		results.first();
		return results.getInt(1);
	}

	public static int getTotalTreaties(Connection conn) throws SQLException
	{
		PreparedStatement statement = conn.prepareStatement("SELECT count(id) FROM treaties WHERE id>0");
		ResultSet results = statement.executeQuery();
		results.first();
		return results.getInt(1);
	}

	public static int getTotalDeclarations(Connection conn) throws SQLException
	{
		PreparedStatement statement = conn.prepareStatement("SELECT count(id) FROM declarations WHERE id>0");
		ResultSet results = statement.executeQuery();
		results.first();
		return results.getInt(1);
	}

	public static int getTotalOngoingWars(Connection conn) throws SQLException
	{
		PreparedStatement statement = conn.prepareStatement("SELECT count(id) FROM wars WHERE end=-1");
		ResultSet results = statement.executeQuery();
		results.first();
		return results.getInt(1);
	}

	public static int getTotalEndedWars(Connection conn) throws SQLException
	{
		PreparedStatement statement = conn.prepareStatement("SELECT count(id) FROM wars WHERE end>0");
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

	public static String convertUnderscoreToCamel(String underscore)
	{
		StringBuilder camel = new StringBuilder(underscore.length());
		underscore = underscore.toLowerCase();
		char[] array = underscore.toCharArray();
		camel.append(Character.toUpperCase(array[0]));
		for(int i = 1; i < array.length; i++)
		{
			if(array[i] != '_')
			{
				camel.append(array[i]);
			}
			else
			{
				i++;
				camel.append(Character.toUpperCase(array[i]));
			}
		}
		return camel.toString();
	}

	public static String convertCamelToUnderscore(String camel)
	{
		StringBuilder underscore = new StringBuilder(camel.length() + 3);
		char[] array = camel.toCharArray();
		for(char c : array)
		{
			if(Character.isUpperCase(c))
			{
				underscore.append("_").append(Character.toLowerCase(c));
			}
			else
			{
				underscore.append(c);
			}
		}
		return underscore.toString();
	}

	public static String formatDisplayNumber(double number)
	{
		String formatted = Long.toString((long)number);
		if(formatted.length() > 15)
		{
			formatted = ">999T";
		}
		else if(formatted.length() > 12)
		{
			formatted = formatNumber(number / 1e12) + "T";
		}
		else if(formatted.length() > 9)
		{
			formatted = formatNumber(number / 1e9) + "B";
		}
		else if(formatted.length() > 6)
		{
			formatted = formatNumber(number / 1e6) + "M";
		}
		else if(formatted.length() > 3)
		{
			formatted = formatNumber(number / 1e3) + "K";
		}
		return formatted;
	}

	public static <K extends TextKey, V> HashMap<? extends TextKey, V> removeNetAndTotal(HashMap<? extends TextKey, V> map)
	{
		HashMap<TextKey, V> newMap = new HashMap<TextKey, V>(map);
		newMap.remove(TextKey.Fortification.NET);
		newMap.remove(TextKey.Stability.NET);
		newMap.remove(TextKey.Approval.NET);
		newMap.remove(TextKey.Resource.NET);
		newMap.remove(TextKey.Population.NET);
		newMap.remove(TextKey.Manpower.NET);
		newMap.remove(TextKey.Growth.NET);
		newMap.remove(TextKey.Farming.NET);
		newMap.remove(TextKey.Resource.TOTAL_GAIN);
		newMap.remove(TextKey.Resource.TOTAL_LOSS);
		newMap.remove(TextKey.Garrison.NET);
		newMap.remove(TextKey.Reinforcement.NET);
		newMap.remove(TextKey.Garrison.NET);
		return newMap;
	}
}
