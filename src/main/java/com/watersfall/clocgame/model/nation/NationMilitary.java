package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.model.Updatable;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NationMilitary extends Updatable
{
	public static final String TABLE_NAME = "cloc_military";
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

	public NationMilitary(int id, ResultSet results) throws SQLException
	{
		super(TABLE_NAME, id, results);
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

	public void setReconBalloons(int reconBalloons)
	{
		if(reconBalloons < 0)
			reconBalloons = 0;
		else if(reconBalloons > 2000000000)
			reconBalloons = 2000000000;
		this.addField("recon_balloons", reconBalloons);
		this.reconBalloons = reconBalloons;
	}

	public void setZeppelins(int zeppelins)
	{
		if(zeppelins < 0)
			zeppelins = 0;
		else if(zeppelins > 2000000000)
			zeppelins = 2000000000;
		this.addField("zeppelins", zeppelins);
		this.zeppelins = zeppelins;
	}

	public void setReconPlanes(int reconPlanes)
	{
		if(reconPlanes < 0)
			reconPlanes = 0;
		else if(reconPlanes > 2000000000)
			reconPlanes = 2000000000;
		this.addField("recon_planes", reconPlanes);
		this.reconPlanes = reconPlanes;
	}

	public void setBiplaneFighters(int biplaneFighters)
	{
		if(biplaneFighters < 0)
			biplaneFighters = 0;
		else if(biplaneFighters > 2000000000)
			biplaneFighters = 2000000000;
		this.addField("biplane_fighters", biplaneFighters);
		this.biplaneFighters = biplaneFighters;
	}

	public void setTriplaneFighters(int triplaneFighters)
	{
		if(triplaneFighters < 0)
			triplaneFighters = 0;
		else if(triplaneFighters > 2000000000)
			triplaneFighters = 2000000000;
		this.addField("triplane_fighters", triplaneFighters);
		this.triplaneFighters = triplaneFighters;
	}

	public void setMonoplaneFighters(int monoplaneFighters)
	{
		if(monoplaneFighters < 0)
			monoplaneFighters = 0;
		else if(monoplaneFighters > 2000000000)
			monoplaneFighters = 2000000000;
		this.addField("monoplane_fighters", monoplaneFighters);
		this.monoplaneFighters = monoplaneFighters;
	}

	public void setBombers(int bombers)
	{
		if(bombers < 0)
			bombers = 0;
		else if(bombers > 2000000000)
			bombers = 2000000000;
		this.addField("bombers", bombers);
		this.bombers = bombers;
	}

	public void setSubmarines(int submarines)
	{
		if(submarines < 0)
			submarines = 0;
		else if(submarines > 2000000000)
			submarines = 2000000000;
		this.addField("submarines", submarines);
		this.submarines = submarines;
	}

	public void setDestroyers(int destroyers)
	{
		if(destroyers < 0)
			destroyers = 0;
		else if(destroyers > 2000000000)
			destroyers = 2000000000;
		this.addField("destroyers", destroyers);
		this.destroyers = destroyers;
	}

	public void setCruisers(int cruisers)
	{
		if(cruisers < 0)
			cruisers = 0;
		else if(cruisers > 2000000000)
			cruisers = 2000000000;
		this.addField("cruisers", cruisers);
		this.cruisers = cruisers;
	}

	public void setPreBattleships(int preBattleships)
	{
		if(preBattleships < 0)
			preBattleships = 0;
		else if(preBattleships > 2000000000)
			preBattleships = 2000000000;
		this.addField("pre_battleships", preBattleships);
		this.preBattleships = preBattleships;
	}

	public void setBattleships(int battleships)
	{
		if(battleships < 0)
			battleships = 0;
		else if(battleships > 2000000000)
			battleships = 2000000000;
		this.addField("battleships", battleships);
		this.battleships = battleships;
	}

	public void setTransports(int transports)
	{
		if(transports < 0)
			transports = 0;
		else if(transports > 2000000000)
			transports = 2000000000;
		this.addField("transports", transports);
		this.transports = transports;
	}

	public void setWarProtection(int warProtection)
	{
		this.addField("war_protection", warProtection);
		this.warProtection = warProtection;
	}
}
