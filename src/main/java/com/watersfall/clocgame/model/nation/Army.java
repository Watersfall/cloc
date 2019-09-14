package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.exception.CityNotFoundException;
import com.watersfall.clocgame.model.Region;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Army
{
	private @Getter int id;
	private @Getter int owner;
	private @Getter Region region;
	private @Getter int army;
	private @Getter int training;
	private @Getter int weapons;
	private @Getter int artillery;
	private @Getter int row;
	private @Getter ResultSet results;

	public Army(Connection connection, int id, boolean safe) throws SQLException
	{
		PreparedStatement read;
		if(safe)
		{
			read = connection.prepareStatement("SELECT owner, region, army, training, weapons, artillery, id " + "FROM cloc_armies " + "WHERE id=? FOR UPDATE ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		}
		else
		{
			read = connection.prepareStatement("SELECT owner, region, army, training, weapons, artillery, id " + "FROM cloc_armies " + "WHERE id=?");
		}
		read.setInt(1, id);
		this.results = read.executeQuery();
		if(!results.first())
		{
			throw new CityNotFoundException("No army with that id!");
		}
		this.owner = results.getInt(1);
		this.region = Region.getFromName(results.getString(2));
		this.army = results.getInt(3);
		this.training = results.getInt(4);
		this.weapons = results.getInt(5);
		this.artillery = results.getInt(6);
		this.id = results.getInt(7);
		this.row = results.getRow();
	}

	public int getTrainingCost()
	{
		return this.army * this.army / 2;
	}

	public void setRegion(Region region) throws SQLException
	{
		results.updateString(2, region.getName());
	}

	public void setArmy(int army) throws SQLException
	{
		if(army < 0)
		{
			army = 0;
		}
		results.updateInt(3, army);
	}

	public void setTraining(int training) throws SQLException
	{
		if(training < 0)
		{
			training = 0;
		}
		else if(training > 100)
		{
			training = 100;
		}
		results.updateInt(4, training);
	}

	public void setWeapons(int weapons) throws SQLException
	{
		if(weapons < 0)
		{
			weapons = 0;
		}
		results.updateInt(5, weapons);
	}

	public void setArtillery(int artillery) throws SQLException
	{
		if(artillery < 0)
		{
			artillery = 0;
		}
		results.updateInt(6, artillery);
	}

	public double getPower()
	{
		double power = army * 1000;
		if(power > weapons)
		{
			power = power * (weapons / power);
		}
		power = Math.sqrt(power / 1000);
		power *= Math.sqrt(training + 1);
		power *= Math.sqrt(artillery + 1);
		return Math.sqrt(power);
	}

	public int getAttackingCasualties(Army defender)
	{
		double attackPower = this.getPower();
		double defensePower = defender.getPower();
		if(attackPower > defensePower)
		{
			defensePower = Math.pow(defensePower, 1.95);
			double armyHp = this.army * 20.0;
			armyHp -= defensePower;
			return (int)(this.army - (armyHp / 20.0));
		}
		else
		{
			defensePower = Math.pow(defensePower, 2.0);
			double armyHp = this.army * 20.0;
			armyHp -= defensePower;
			return (int)(this.army - (armyHp / 20.0));
		}
	}

	public int getDefendingCasualties(Army attacker)
	{
		double attackPower = attacker.getPower();
		double defensePower = this.getPower();
		if(attackPower > defensePower)
		{
			attackPower = Math.pow(attackPower, 2.0);
			double armyHp = this.army * 20.0;
			armyHp -= attackPower;
			return (int)(this.army - (armyHp / 20.0));
		}
		else
		{
			attackPower = Math.pow(attackPower, 1.95);
			double armyHp = this.army * 20.0;
			armyHp -= attackPower;
			return (int)(this.army - (armyHp / 20.0));
		}
	}

	public void update() throws SQLException
	{
		results.updateRow();
	}
}
