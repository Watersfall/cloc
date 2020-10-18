package net.watersfall.clocgame.model.message;

import lombok.Getter;
import lombok.Setter;
import net.watersfall.clocgame.model.nation.Nation;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Message
{
	private @Getter long id;
	private @Getter @Setter Nation senderNation, receiverNation;
	private @Getter int senderId, receiverId;
	private @Getter boolean allianceMessage, adminMessage, systemMessage;
	private @Getter String content;

	public Message(ResultSet results) throws SQLException
	{
		this.id = results.getLong("id");
		this.receiverId = results.getInt("receiver");
		this.senderId = results.getInt("sender");
		this.allianceMessage = results.getBoolean("alliance_message");
		this.adminMessage = results.getBoolean("admin_message");
		this.systemMessage = results.getBoolean("system_message");
		this.content = results.getString("content");
	}
}
