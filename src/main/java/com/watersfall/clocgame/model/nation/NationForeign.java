package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.model.Region;
import com.watersfall.clocgame.model.Updatable;
import com.watersfall.clocgame.model.alignment.Alignments;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NationForeign extends Updatable
{
	public static final String TABLE_NAME = "cloc_foreign";
	private @Getter Region region;
	private @Getter Alignments alignment;
	private @Getter int ententeReputation;
	private @Getter int centralPowersReputation;

	public NationForeign(int id, ResultSet results) throws SQLException
	{
		super(TABLE_NAME, id);
		this.region = Region.valueOf(results.getString("region"));
		this.alignment = Alignments.valueOf(results.getString("alignment"));
		this.ententeReputation = results.getInt("entente_reputation");
		this.centralPowersReputation = results.getInt("central_powers_reputation");
	}

	public void setRegion(Region region)
	{
		this.setField("region", region);
		this.region = region;
	}

	public void setAlignment(Alignments alignment)
	{
		this.setField("alignment", alignment);
		this.alignment = alignment;
	}
	
	public void setEntenteReputation(int ententeReputation)
	{
		this.ententeReputation = ententeReputation;
		this.setField("entente_reputation", ententeReputation);
	}

	public void setCentralPowersReputation(int centralPowersReputation)
	{
		this.centralPowersReputation = centralPowersReputation;
		this.setField("central_powers_reputation", centralPowersReputation);
	}

	public int getReputation(Alignments alignment)
	{
		if(alignment == Alignments.CENTRAL_POWERS)
			return this.centralPowersReputation;
		else if(alignment == Alignments.ENTENTE)
			return this.ententeReputation;
		else
			return 0;
	}

	public void setReputation(Alignments alignment, int reputation)
	{
		if(alignment == Alignments.CENTRAL_POWERS)
			setCentralPowersReputation(reputation);
		else if(alignment == Alignments.ENTENTE)
			setEntenteReputation(reputation);
	}
}
