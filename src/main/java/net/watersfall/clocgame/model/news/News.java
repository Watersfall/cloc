package net.watersfall.clocgame.model.news;

import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class News
{
	private @Getter int id;
	private @Getter int sender;
	private @Getter int receiver;
	private @Getter long time;
	private @Getter String content;

	public static final int ID_SEND_RESOURCE = 0;
	public static final int ID_SEND_MONEY = 6;
	public static final int ID_DECLARE_WAR = 7;
	public static final int ID_DEFENSIVE_LOST = 8;
	public static final int ID_DEFENSIVE_WON = 9;
	public static final int ID_WAR_LOST = 10;
	public static final int ID_NAVAL_BATTLE = 11;
	public static final int ID_NAVAL_BOMBARD = 12;
	public static final int ID_AIR_BATTLE = 13;
	public static final int ID_AIR_BOMBARD = 14;
	public static final int ID_DEFENSIVE_CITY_LOST = 15;
	public static final int ID_DEFENSIVE_CITY_WON = 16;
	public static final int ID_FORTIFICATION = 17;
	public static final int ID_AIR_BOMB_TROOPS = 18;
	public static final int ID_SEND_PEACE = 19;
	public static final int ID_PEACE_ACCEPTED = 20;
	public static final int ID_TREATY_INVITE = 21;

	private static String sendMessage(Object... args)
	{
		return String.format("%s has sent us %s %s!", args);
	}

	public static String createMessage(int id, Object... args)
	{
		switch(id)
		{
			case ID_SEND_RESOURCE:
				return sendMessage(args);
			case ID_SEND_MONEY:
				return String.format("%s has wired us $%sk!", args);
			case ID_DECLARE_WAR:
				return String.format("%s has declared war on us!", args);
			case ID_DEFENSIVE_LOST:
				return String.format("%s has defeated our army in battle! We lost %sk soldiers and killed %sk enemy soldiers!", args);
			case ID_DEFENSIVE_WON:
				return String.format("%s attacked our army, but has been defeated! We lost %sk soldiers and killed %sk enemy soldiers!", args);
			case ID_DEFENSIVE_CITY_LOST:
				return String.format("%s has attacked and occupied %s! We lost %sk soldiers and killed %sk enemy soldiers!", args);
			case ID_DEFENSIVE_CITY_WON:
				return String.format("%s has attacked the city of %s! We killed %sk enemy soldiers, with %sk of our soldiers dying in the defense of the city!", args);
			case ID_FORTIFICATION:
				return String.format("Our scouts report that %s's army has further fortified their position", args);
			case ID_NAVAL_BATTLE:
				return String.format("%s has attacked our navy! We have lost %s Battleships, %s Pre-Dreadnought Battleships, " +
						"%s Cruisers, %s Destroyers, and %s submarines. However, we managed to sink %s Battleships, " +
						"%s Pre-Dreadnought Battleships, %s Cruisers, %s Destroyers, and %s submarines.", args);
			case ID_NAVAL_BOMBARD:
				return String.format("%s has bombarded the city of %s with their navy!", args);
			case ID_AIR_BATTLE:
				return String.format("%s has attacked our airforce! They have shot down %s of our planes," +
						"and we managed to shoot down %s !", args);
			case ID_AIR_BOMBARD:
				return String.format("%s has bombed the city of %s with their airforce!", args);
			case ID_AIR_BOMB_TROOPS:
				return String.format("%s has bombed our army with their airforce, killing %sk of our troops!", args);
			case ID_WAR_LOST:
				return String.format("%s has defeated us in war, stealing part of our land, devastating our cities, and demanding a third of our resources in reparations. ", args);
			case ID_SEND_PEACE:
				return String.format("%s has requested white peace! Should we continue the war, or halt our advances?", args);
			case ID_PEACE_ACCEPTED:
				return String.format("%s has accepted our offer for peace! Let there be peace in our time!", args);
			case ID_TREATY_INVITE:
				return String.format("%s has invited us to the %s treaty!", args);
		}
		return "";
	}

	public News(ResultSet results) throws SQLException
	{
		this.sender = results.getInt("sender");
		this.receiver = results.getInt("receiver");
		this.content = results.getString("content");
		this.time = results.getLong("time");
		this.id = results.getInt("id");
	}
}
