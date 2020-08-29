package com.watersfall.clocgame.dao;

import com.watersfall.clocgame.model.message.Message;
import com.watersfall.clocgame.model.nation.Nation;

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

	public ArrayList<Message> getUnreadMessages(int receiver, int lastId) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement(GET_UNREAD_MESSAGES);
		statement.setInt(1, receiver);
		statement.setInt(2, lastId);
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
}
