package net.watersfall.clocgame.model.message;

import lombok.Getter;
import net.watersfall.clocgame.model.nation.Nation;

public class Declaration
{
	private @Getter int id;
	private @Getter Nation sender;
	private @Getter long sent;
	private @Getter String content;

	public Declaration(int id, Nation sender, long sent, String content)
	{
		this.id = id;
		this.sender = sender;
		this.sent = sent;
		this.content = content;
	}
}
