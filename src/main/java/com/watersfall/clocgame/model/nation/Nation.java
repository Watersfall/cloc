package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.exception.TreatyNotFoundException;
import com.watersfall.clocgame.exception.WarNotFoundException;
import com.watersfall.clocgame.model.treaty.Treaty;
import com.watersfall.clocgame.model.war.War;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Nation
{
	private @Getter
	int id;
	private @Getter NationCosmetic cosmetic;
	private @Getter NationDomestic domestic;
	private @Getter NationEconomy economy;
	private @Getter NationForeign foreign;
	private @Getter NationMilitary military;
	private @Getter NationCities cities;
	private @Getter NationArmies armies;
	private @Getter NationPolicy policy;
	private @Getter War defensive;
	private @Getter War offensive;
	private @Getter Treaty treaty;
	private @Getter Connection connection;
	private @Getter boolean safe;

	/**
	 * @param connection The connection object to use
	 * @param id         The id of the nation being loaded
	 * @param safe       Whether the contents of the returned Nation object should be editable
	 * @throws SQLException if something SQL related goes wrong
	 */
	public Nation(Connection connection, int id, boolean safe) throws SQLException
	{
		cosmetic = new NationCosmetic(connection, id, safe);
		domestic = new NationDomestic(connection, id, safe);
		economy = new NationEconomy(connection, id, safe);
		foreign = new NationForeign(connection, id, safe);
		military = new NationMilitary(connection, id, safe);
		cities = new NationCities(connection, id, safe);
		armies = new NationArmies(connection, id, safe);
		policy = new NationPolicy(connection, id, safe);
		try{defensive = new War(connection, id, safe, false);}catch(WarNotFoundException e){defensive=null;}
		try{offensive = new War(connection, id, safe, true);}catch(WarNotFoundException e){offensive=null;}
		try{treaty = new Treaty(connection, id, safe);}catch(TreatyNotFoundException e){treaty=null;}
		this.id = id;
		this.connection = connection;
		this.safe = safe;
		if(!safe)
		{
			connection.close();
		}
	}

	/**
	 * Commits all changes made to this object
	 * Put in the main Nation class instead of in the subclasses since they all share the same connection
	 *
	 * @throws SQLException if something TQL related goes wrong
	 */
	public void commit() throws SQLException
	{
		connection.commit();
	}

	public void joinTreaty(Treaty treaty, boolean founder) throws SQLException
	{
		PreparedStatement join =  connection.prepareStatement("INSERT INTO cloc_treaties_members (alliance_id, nation_id, founder) VALUES (?,?,?)");
		join.setInt(1, treaty.getId());
		join.setInt(2, this.id);
		join.setBoolean(3, founder);
		join.execute();
	}

	public void leaveTreaty() throws SQLException
	{
		PreparedStatement leave = connection.prepareStatement("DELETE FROM cloc_treaties_members WHERE nation_id=?");
		leave.setInt(1, this.id);
	}

	public boolean canDeclareWar(Nation nation)
	{
		return this.offensive == null && nation.getDefensive() == null;
	}

	public War declareWar(Nation nation) throws SQLException
	{
		if(canDeclareWar(nation))
		{
			return new War(this.connection, this, nation);
		}
		else
		{
			return null;
		}
	}


}
