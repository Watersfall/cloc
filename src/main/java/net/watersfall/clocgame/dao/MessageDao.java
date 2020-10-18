package net.watersfall.clocgame.dao;

import net.watersfall.clocgame.model.message.Message;
import net.watersfall.clocgame.model.nation.Nation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class MessageDao extends Dao
{
	private static final String GET_RECEIVED_MESSAGE_PAGE = "SELECT * FROM messages WHERE receiver=? LIMIT 50 OFFSET ?";
	private static final String GET_SENT_MESSAGE_PAGE = "SELECT * FROM messages WHERE sender=? LIMIT 50 OFFSET ?";
	private static final String GET_UNREAD_MESSAGES = "SELECT * FROM messages WHERE receiver=? AND id>? LIMIT 10";
	private static final String GET_MESSAGE_BY_ID = "SELECT * FROM messages WHERE id=? ";
	private static final String DELETE_MESSAGE_BY_ID = "DELETE FROM messages WHERE id=? ";
	private static final String MARK_AS_READ = "UPDATE nation_stats SET last_message=? WHERE id=? ";
	private static final String GET_MAX_MESSAGE_ID = "SELECT MAX(id) FROM messages WHERE receiver=? ";
	private static final String CREATE_MESSAGE = "INSERT INTO messages (sender, receiver, alliance_message, admin_message, system_message, content) VALUES (?,?,?,?,?,?)";

	public MessageDao(Connection connection, boolean allowWriteAccess)
	{
		super(connection, allowWriteAccess);
	}

	public ArrayList<Message> getReceivedMessagePage(int receiver, int page) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement(GET_RECEIVED_MESSAGE_PAGE);
		statement.setInt(1, receiver);
		statement.setInt(2, (page - 1) * 50);
		ResultSet results = statement.executeQuery();
		ArrayList<Message> list = new ArrayList<>();
		HashMap<Integer, Nation> nations = new HashMap<>();
		NationDao dao = new NationDao(connection, allowWriteAccess);
		while(results.next())
		{
			Message message = new Message(results);
			if(nations.get(message.getSenderId()) != null)
			{
				message.setSenderNation(nations.get(message.getSenderId()));
			}
			else
			{
				Nation nation = dao.getCosmeticNationById(message.getSenderId());
				message.setSenderNation(nation);
				nations.put(nation.getId(), nation);
			}
			list.add(message);
		}
		return list;
	}

	public ArrayList<Message> getSentMessagePage(int sender, int page) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement(GET_SENT_MESSAGE_PAGE);
		statement.setInt(1, sender);
		statement.setInt(2, (page - 1) * 50);
		ResultSet results = statement.executeQuery();
		ArrayList<Message> list = new ArrayList<>();
		HashMap<Integer, Nation> nations = new HashMap<>();
		NationDao dao = new NationDao(connection, allowWriteAccess);
		while(results.next())
		{
			Message message = new Message(results);
			if(nations.get(message.getReceiverId()) != null)
			{
				message.setSenderNation(nations.get(message.getReceiverId()));
			}
			else
			{
				Nation nation = dao.getCosmeticNationById(message.getReceiverId());
				message.setReceiverNation(nation);
				nations.put(nation.getId(), nation);
			}
			list.add(message);
		}
		return list;
	}

	public ArrayList<Message> getUnreadMessages(int receiver, long lastId) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement(GET_UNREAD_MESSAGES);
		statement.setInt(1, receiver);
		statement.setLong(2, lastId);
		ResultSet results = statement.executeQuery();
		ArrayList<Message> list = new ArrayList<>();
		HashMap<Integer, Nation> nations = new HashMap<>();
		NationDao dao = new NationDao(connection, allowWriteAccess);
		while(results.next())
		{
			Message message = new Message(results);
			if(nations.get(message.getSenderId()) != null)
			{
				message.setSenderNation(nations.get(message.getSenderId()));
			}
			else
			{
				Nation nation = dao.getCosmeticNationById(message.getSenderId());
				message.setSenderNation(nation);
				nations.put(nation.getId(), nation);
			}
			list.add(message);
		}
		return list;
	}

	public Message getMessageById(long id) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement(GET_MESSAGE_BY_ID);
		statement.setLong(1, id);
		ResultSet results = statement.executeQuery();
		results.first();
		return new Message(results);
	}

	public void deleteMessageById(long id) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement(DELETE_MESSAGE_BY_ID);
		statement.setLong(1, id);
		statement.execute();
	}

	public void markMessagesAtRead(int nation) throws SQLException
	{
		PreparedStatement id = connection.prepareStatement(GET_MAX_MESSAGE_ID);
		id.setInt(1, nation);
		ResultSet results = id.executeQuery();
		results.first();
		PreparedStatement read = connection.prepareStatement(MARK_AS_READ);
		read.setLong(1, results.getLong(1));
		read.setInt(2, nation);
		read.execute();
	}

	public void createMessage(int sender, int receiver, String content) throws SQLException
	{
		PreparedStatement create = connection.prepareStatement(CREATE_MESSAGE);
		create.setInt(1, sender);
		create.setInt(2, receiver);
		create.setBoolean(3, false);
		create.setBoolean(4, false);
		create.setBoolean(5, false);
		create.setString(6, content);
		create.execute();
	}
}
