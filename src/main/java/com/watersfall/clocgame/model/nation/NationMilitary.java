package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.exception.NationNotFoundException;
import com.watersfall.clocgame.exception.ValueException;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NationMilitary extends NationBase
{
	private @Getter int id;
	private @Getter int fighters;
	private @Getter int zeppelins;
	private @Getter int bombers;
	private @Getter int submarines;
	private @Getter int destroyers;
	private @Getter int cruisers;
	private @Getter int preBattleships;
	private @Getter int battleships;
	private @Getter int transports;
	private @Getter int stockpileArtillery;
	private @Getter int stockpileWeapons;
	private @Getter int warProtection;

	public NationMilitary(Connection connection, int id, boolean safe) throws SQLException
	{
		super(connection, id, safe);
		PreparedStatement read;
		if(safe)
		{
			read = connection.prepareStatement("SELECT fighters, zeppelins, bombers, submarines, destroyers, cruisers, pre_battleships, battleships, transports, artillery_stockpile, weapon_stockpile, war_protection, id " + "FROM cloc_military " + "WHERE id=? FOR UPDATE ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		}
		else
		{
			read = connection.prepareStatement("SELECT fighters, zeppelins, bombers, submarines, destroyers, cruisers, pre_battleships, battleships, transports, artillery_stockpile, weapon_stockpile, war_protection, id " + "FROM cloc_military " + "WHERE id=?");
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
			this.stockpileArtillery = results.getInt(10);
			this.stockpileWeapons = results.getInt(11);
			this.warProtection = results.getInt(12);
		}
	}

	public void setFighters(int fighters) throws SQLException
	{
		if(fighters < 0)
		{
			throw new ValueException("Can not be negative!");
		}
		else
		{
			results.updateInt(fighters, 1);
		}
	}

	public void setZeppelins(int zeppelins) throws SQLException
	{
		if(zeppelins < 0)
		{
			throw new ValueException("Can not be negative!");
		}
		else
		{
			results.updateInt(zeppelins, 2);
		}
	}

	public void setBombers(int bombers) throws SQLException
	{
		if(bombers < 0)
		{
			throw new ValueException("Can not be negative!");
		}
		else
		{
			results.updateInt(bombers, 3);
		}
	}

	public void setSubmarines(int submarines) throws SQLException
	{
		if(submarines < 0)
		{
			throw new ValueException("Can not be negative!");
		}
		else
		{
			results.updateInt(submarines, 4);
		}
	}

	public void setDestroyers(int destroyers) throws SQLException
	{
		if(destroyers < 0)
		{
			throw new ValueException("Can not be negative!");
		}
		else
		{
			results.updateInt(destroyers, 5);
		}
	}

	public void setCruisers(int cruisers) throws SQLException
	{
		if(cruisers < 0)
		{
			throw new ValueException("Can not be negative!");
		}
		else
		{
			results.updateInt(cruisers, 6);
		}
	}

	public void setPreBattleships(int preBattleships) throws SQLException
	{
		if(preBattleships < 0)
		{
			throw new ValueException("Can not be negative!");
		}
		else
		{
			results.updateInt(preBattleships, 7);
		}
	}

	public void setBattleships(int battleships) throws SQLException
	{
		if(battleships < 0)
		{
			throw new ValueException("Can not be negative!");
		}
		else
		{
			results.updateInt(battleships, 8);
		}
	}

	public void setTransports(int transports) throws SQLException
	{
		if(transports < 0)
		{
			throw new ValueException("Can not be negative!");
		}
		else
		{
			results.updateInt(transports, 9);
		}
	}

	public void setStockpileArtillery(int stockpileArtillery) throws SQLException
	{
		if(stockpileArtillery < 0)
		{
			throw new ValueException("Can not be negative!");
		}
		else
		{
			results.updateInt(stockpileArtillery, 10);
		}
	}

	public void setStockpileWeapons(int stockpileWeapons) throws SQLException
	{
		if(stockpileWeapons < 0)
		{
			throw new ValueException("Can not be negative!");
		}
		else
		{
			results.updateInt(stockpileWeapons, 11);
		}
	}

	public void setWarProtection(int warProtection) throws SQLException
	{
		if(warProtection < 0)
		{
			throw new ValueException("Can not be negative!");
		}
		else
		{
			results.updateInt(warProtection, 12);
		}
	}

}
