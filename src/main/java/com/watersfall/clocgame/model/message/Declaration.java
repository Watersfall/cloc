package com.watersfall.clocgame.model.message;

import com.watersfall.clocgame.model.nation.Nation;
import lombok.Getter;

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
