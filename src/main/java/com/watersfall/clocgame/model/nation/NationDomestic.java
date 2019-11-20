package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.exception.NationNotFoundException;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NationDomestic extends NationBase
{
	private @Getter int land;
	private @Getter int government;
	private @Getter int approval;
	private @Getter int stability;
	private @Getter long population;
	private @Getter long manpowerLost;
	private @Getter int rebels;

	public NationDomestic(int land, int government, int approval, int stability, long population, long manpowerLost, int rebels)
	{
		this.land = land;
		this.government = government;
		this.approval = approval;
		this.stability = stability;
		this.population = population;
		this.manpowerLost = manpowerLost;
		this.rebels = rebels;
	}

	public NationDomestic(ResultSet results, Connection connection, int id, boolean safe) throws SQLException
	{
		super(connection, id, safe);
		this.results = results;
		this.land = results.getInt("land");
		this.government = results.getInt("government");
		this.approval = results.getInt("approval");
		this.stability = results.getInt("stability");
		this.population = results.getLong("population");
		this.manpowerLost = results.getLong("lost_manpower");
		this.rebels = results.getInt("rebels");
	}

	public NationDomestic(Connection connection, int id, boolean safe) throws SQLException
	{
		super(connection, id, safe);
		PreparedStatement read;
		if(safe)
		{
			read = connection.prepareStatement("SELECT land, government, approval, stability, population, rebels, lost_manpower, id " + "FROM cloc_domestic " + "WHERE id=? FOR UPDATE ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		}
		else
		{
			read = connection.prepareStatement("SELECT land, government, approval, stability, population, rebels, lost_manpower, id " + "FROM cloc_domestic " + "WHERE id=?");
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
			this.land = results.getInt(1);
			this.government = results.getInt(2);
			this.approval = results.getInt(3);
			this.stability = results.getInt(4);
			this.population = results.getLong(5);
			this.rebels = results.getInt(6);
			this.manpowerLost = results.getInt(7);
		}
	}

	public void setLand(int land) throws SQLException
	{
		if(land < 0)
		{
			land = 0;
		}
		results.updateInt(1, land);
	}

	public void setGovernment(int government) throws SQLException
	{
		if(government < 0)
		{
			government = 0;
		}
		else if(government > 100)
		{
			government = 100;
		}
		this.government = government;
		results.updateInt(2, government);
	}

	public void setApproval(int approval) throws SQLException
	{
		if(approval < 0)
		{
			approval = 0;
		}
		else if(approval > 100)
		{
			approval = 100;
		}
		this.approval = approval;
		results.updateInt(3, approval);
	}

	public void setStability(int stability) throws SQLException
	{
		if(stability < 0)
		{
			stability = 0;
		}
		else if(stability > 100)
		{
			stability = 100;
		}
		this.stability = stability;
		results.updateInt(4, stability);
	}

	public void setPopulation(long population) throws SQLException
	{
		if(population < 1)
		{
			population = 1;
		}
		this.population = population;
		results.updateDouble(5, population);
	}

	public void setRebels(int rebels) throws SQLException
	{
		if(rebels < 0)
		{
			rebels = 0;
		}
		else if(rebels > 100)
		{
			rebels = 100;
		}
		this.rebels = rebels;
		results.updateInt(6, rebels);
	}

	public void setManpowerLost(long lostManpower) throws SQLException
	{
		if(lostManpower < 0)
		{
			lostManpower = 0;
		}
		this.manpowerLost = lostManpower;
		results.updateLong(7, lostManpower);
	}
}
