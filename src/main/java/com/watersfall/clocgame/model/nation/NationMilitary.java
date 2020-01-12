package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.exception.NationNotFoundException;
import lombok.Getter;

import java.sql.*;

public class NationMilitary extends NationBase
{
	private @Getter int reconBalloons;
	private @Getter int zeppelins;
	private @Getter int reconPlanes;
	private @Getter int biplaneFighters;
	private @Getter int triplaneFighters;
	private @Getter int monoplaneFighters;
	private @Getter int bombers;
	private @Getter int submarines;
	private @Getter int destroyers;
	private @Getter int cruisers;
	private @Getter int preBattleships;
	private @Getter int battleships;
	private @Getter int transports;
	private @Getter int warProtection;

	public NationMilitary(ResultSet results, Connection connection, int id, boolean safe) throws SQLException
	{
		super(connection, id, safe);
		this.results = results;
		this.reconBalloons = results.getInt("recon_balloons");
		this.reconPlanes = results.getInt("recon_planes");
		this.triplaneFighters = results.getInt("triplane_fighters");
		this.monoplaneFighters = results.getInt("monoplane_fighters");
		this.biplaneFighters = results.getInt("biplane_fighters");
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
			read = connection.prepareStatement("SELECT id, zeppelins, bombers, submarines, destroyers, cruisers, pre_battleships, battleships, transports, war_protection, recon_balloons, recon_planes, biplane_fighters, triplane_fighters, monoplane_fighters " + "FROM cloc_military " + "WHERE id=? FOR UPDATE ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		}
		else
		{
			read = connection.prepareStatement("SELECT id, zeppelins, bombers, submarines, destroyers, cruisers, pre_battleships, battleships, transports, war_protection, recon_balloons, recon_planes, biplane_fighters, triplane_fighters, monoplane_fighters " + "FROM cloc_military " + "WHERE id=?");
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
			this.reconBalloons = results.getInt("recon_balloons");
			this.reconPlanes = results.getInt("recon_planes");
			this.triplaneFighters = results.getInt("triplane_fighters");
			this.monoplaneFighters = results.getInt("monoplane_fighters");
			this.biplaneFighters = results.getInt("biplane_fighters");
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
	}

	public <T> T getByName(String name) throws SQLException
	{
		int column = results.findColumn(name);
		T value;
		switch (results.getMetaData().getColumnType(column))
		{
			case Types.INTEGER:
			case Types.TINYINT:
			case Types.SMALLINT:
				value = (T) new Integer(results.getInt(name));
				break;
			case Types.VARCHAR:
			case Types.CHAR:
				value = (T) results.getString(name);
				break;
			case Types.BIGINT:
				value = (T) new Long(results.getLong(name));
				break;
			case Types.FLOAT:
			case Types.DECIMAL:
			case Types.DOUBLE:
				value = (T) new Double(results.getDouble(name));
			default:
				value = (T) results.getString(name);
		}
		return value;
	}

	public <T> void setByName(String name, T value) throws SQLException
	{
		int column = results.findColumn(name);
		switch (results.getMetaData().getColumnType(column))
		{
			case Types.INTEGER:
			case Types.TINYINT:
			case Types.SMALLINT:
				results.updateInt(name, (Integer)value);
				break;
			case Types.VARCHAR:
			case Types.CHAR:
				results.updateString(name, (String)value);
				break;
			case Types.BIGINT:
				results.updateLong(name, (Long)value);
				break;
			case Types.FLOAT:
			case Types.DECIMAL:
			case Types.DOUBLE:
				results.updateDouble(name, (Double)value);
			default:
				results.updateString(name, (String)value);
		}
	}

	public <T> void updateByName(String name, T value) throws SQLException
	{
		int column = results.findColumn(name);
		switch (results.getMetaData().getColumnType(column))
		{
			case Types.INTEGER:
			case Types.TINYINT:
			case Types.SMALLINT:
				results.updateInt(name, results.getInt(name) + (Integer)value);
				break;
			case Types.BIGINT:
				results.updateLong(name, results.getLong(name) + (Long)value);
				break;
			case Types.FLOAT:
			case Types.DECIMAL:
			case Types.DOUBLE:
				results.updateDouble(name, results.getDouble(name) + (Double)value);
			default:
				results.updateString(name, (String)value);
		}
	}

	public void setReconBalloons(int reconBalloons) throws SQLException
	{
		if(reconBalloons < 0)
		{
			reconBalloons = 0;
		}
		this.reconBalloons = reconBalloons;
		results.updateInt("recon_balloons", reconBalloons);
	}

	public void setZeppelins(int zeppelins) throws SQLException
	{
		if(zeppelins < 0)
		{
			zeppelins = 0;
		}
		this.zeppelins = zeppelins;
		results.updateInt("zeppelins", zeppelins);
	}

	public void setReconPlanes(int reconPlanes) throws SQLException
	{
		if(reconPlanes < 0)
		{
			reconPlanes = 0;
		}
		this.reconPlanes = reconPlanes;
		results.updateInt("recon_planes", reconPlanes);
	}
	
	public void setBiplaneFighters(int biplaneFighters) throws SQLException
	{
		if(biplaneFighters < 0)
		{
			biplaneFighters = 0;
		}
		this.biplaneFighters = biplaneFighters;
		results.updateInt("biplane_fighters", biplaneFighters);
	}

	public void setTriplaneFighters(int triplaneFighters) throws SQLException
	{
		if(triplaneFighters < 0)
		{
			triplaneFighters = 0;
		}
		this.triplaneFighters = triplaneFighters;
		results.updateInt("triplane_fighters", triplaneFighters);
	}

	public void setMonoplaneFighters(int monoplaneFighters) throws SQLException
	{
		if(monoplaneFighters < 0)
		{
			monoplaneFighters = 0;
		}
		this.monoplaneFighters = monoplaneFighters;
		results.updateInt("monoplane_fighters", monoplaneFighters);
	}

	public void setBombers(int bombers) throws SQLException
	{
		if(bombers < 0)
		{
			bombers = 0;
		}
		this.bombers = bombers;
		results.updateInt("bombers", bombers);
	}

	public void setSubmarines(int submarines) throws SQLException
	{
		if(submarines < 0)
		{
			submarines = 0;
		}
		this.submarines = submarines;
		results.updateInt("submarines", submarines);
	}

	public void setDestroyers(int destroyers) throws SQLException
	{
		if(destroyers < 0)
		{
			destroyers = 0;
		}
		this.destroyers = destroyers;
		results.updateInt("destroyers", destroyers);
	}

	public void setCruisers(int cruisers) throws SQLException
	{
		if(cruisers < 0)
		{
			cruisers = 0;
		}
		this.cruisers = cruisers;
		results.updateInt("cruisers", cruisers);
	}

	public void setPreBattleships(int preBattleships) throws SQLException
	{
		if(preBattleships < 0)
		{
			preBattleships = 0;
		}
		this.preBattleships = preBattleships;
		results.updateInt("pre_battleships", preBattleships);
	}

	public void setBattleships(int battleships) throws SQLException
	{
		if(battleships < 0)
		{
			battleships = 0;
		}
		this.battleships = battleships;
		results.updateInt("battleships", battleships);
	}

	public void setTransports(int transports) throws SQLException
	{
		if(transports < 0)
		{
			transports = 0;
		}
		this.transports = transports;
		results.updateInt("transports", transports);
	}

	public void setWarProtection(int warProtection) throws SQLException
	{
		if(warProtection < 0)
		{
			warProtection = 0;
		}
		this.warProtection = warProtection;
		results.updateInt("war_protection", warProtection);
	}

}
