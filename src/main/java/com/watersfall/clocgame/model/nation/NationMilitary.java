package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.exception.NationNotFoundException;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NationMilitary extends NationBase
{
	private @Getter int fighters;
	private @Getter int zeppelins;
	private @Getter int bombers;
	private @Getter int submarines;
	private @Getter int destroyers;
	private @Getter int cruisers;
	private @Getter int preBattleships;
	private @Getter int battleships;
	private @Getter int transports;
	private @Getter int warProtection;

	public NationMilitary(int fighters, int zeppelins, int bombers, int submarines, int destroyers, int cruisers, int preBattleships, int battleships, int transports, int warProtection)
	{
		this.fighters = fighters;
		this.zeppelins = zeppelins;
		this.bombers = bombers;
		this.submarines = submarines;
		this.destroyers = destroyers;
		this.cruisers = cruisers;
		this.preBattleships = preBattleships;
		this.battleships = battleships;
		this.transports = transports;
		this.warProtection = warProtection;
	}

	public NationMilitary(ResultSet results, Connection connection, int id, boolean safe) throws SQLException
	{
		super(connection, id, safe);
		this.results = results;
		this.fighters = results.getInt("fighters");
		this.zeppelins = results.getInt("zeppelins");
		this.bombers = results.getInt("bombers");
		this.submarines = results.getInt("submarines");
		this.destroyers = results.getInt("destroyers");
		this.cruisers = results.getInt("cruisers");
		this.preBattleships = results.getInt("pre_battleships");
		this.battleships = results.getInt("battleships");
		this.transports = results.getInt("transports");
		this.warProtection = results.getInt("war_protection");
	}

	public NationMilitary(Connection connection, int id, boolean safe) throws SQLException
	{
		super(connection, id, safe);
		PreparedStatement read;
		if(safe)
		{
			read = connection.prepareStatement("SELECT fighters, zeppelins, bombers, submarines, destroyers, cruisers, pre_battleships, battleships, transports, war_protection, id " + "FROM cloc_military " + "WHERE id=? FOR UPDATE ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		}
		else
		{
			read = connection.prepareStatement("SELECT fighters, zeppelins, bombers, submarines, destroyers, cruisers, pre_battleships, battleships, transports, war_protection, id " + "FROM cloc_military " + "WHERE id=?");
		}
		read.setInt(1, id);
		this.results = read.executeQuery();
		if(!results.first())
		{
			throw new NationNotFoundException("No nation with that id!");
		}
		else
		{
			this.connection = connection;
			this.id = id;
			this.safe = safe;
			this.fighters = results.getInt(1);
			this.zeppelins = results.getInt(2);
			this.bombers = results.getInt(3);
			this.submarines = results.getInt(4);
			this.destroyers = results.getInt(5);
			this.cruisers = results.getInt(6);
			this.preBattleships = results.getInt(7);
			this.battleships = results.getInt(8);
			this.transports = results.getInt(9);
			this.warProtection = results.getInt(10);
		}
	}

	public void setFighters(int fighters) throws SQLException
	{
		if(fighters < 0)
		{
			fighters = 0;
		}
		this.fighters = fighters;
		results.updateInt(1, fighters);
	}

	public void setZeppelins(int zeppelins) throws SQLException
	{
		if(zeppelins < 0)
		{
			zeppelins = 0;
		}
		this.zeppelins = zeppelins;
		results.updateInt(2, zeppelins);
	}

	public void setBombers(int bombers) throws SQLException
	{
		if(bombers < 0)
		{
			bombers = 0;
		}
		this.bombers = bombers;
		results.updateInt(3, bombers);
	}

	public void setSubmarines(int submarines) throws SQLException
	{
		if(submarines < 0)
		{
			submarines = 0;
		}
		this.submarines = submarines;
		results.updateInt(4, submarines);
	}

	public void setDestroyers(int destroyers) throws SQLException
	{
		if(destroyers < 0)
		{
			destroyers = 0;
		}
		this.destroyers = destroyers;
		results.updateInt(5, destroyers);
	}

	public void setCruisers(int cruisers) throws SQLException
	{
		if(cruisers < 0)
		{
			cruisers = 0;
		}
		this.cruisers = cruisers;
		results.updateInt(6, cruisers);
	}

	public void setPreBattleships(int preBattleships) throws SQLException
	{
		if(preBattleships < 0)
		{
			preBattleships = 0;
		}
		this.preBattleships = preBattleships;
		results.updateInt(7, preBattleships);
	}

	public void setBattleships(int battleships) throws SQLException
	{
		if(battleships < 0)
		{
			battleships = 0;
		}
		this.battleships = battleships;
		results.updateInt(8, battleships);
	}

	public void setTransports(int transports) throws SQLException
	{
		if(transports < 0)
		{
			transports = 0;
		}
		this.transports = transports;
		results.updateInt(9, transports);
	}

	public void setWarProtection(int warProtection) throws SQLException
	{
		if(warProtection < 0)
		{
			warProtection = 0;
		}
		this.warProtection = warProtection;
		results.updateInt(10, warProtection);
	}

}
