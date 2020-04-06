package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.model.Updatable;
import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NationArmy extends Updatable
{
	public static final String TABLE_NAME = "cloc_army";
	private @Getter int size;
	private @Getter int training;
	private @Getter int musket;
	private @Getter int rifledMusket;
	private @Getter int singleShot;
	private @Getter int needleNose;
	private @Getter int boltActionManual;
	private @Getter int boltActionClip;
	private @Getter int straightPull;
	private @Getter int semiAuto;
	private @Getter int machineGun;
	private @Getter int artillery;
	private @Getter int tank;
	private @Getter int fortification;

	public NationArmy(int id, ResultSet results) throws SQLException
	{
		super(TABLE_NAME, id, results);
		this.size = results.getInt("size");
		this.training = results.getInt("training");
		this.musket = results.getInt("musket");
		this.rifledMusket = results.getInt("rifled_musket");
		this.singleShot = results.getInt("single_shot");
		this.needleNose = results.getInt("needle_nose");
		this.boltActionManual = results.getInt("bolt_action_manual");
		this.boltActionClip = results.getInt("bolt_action_clip");
		this.straightPull = results.getInt("straight_pull");
		this.semiAuto = results.getInt("semi_auto");
		this.machineGun = results.getInt("machine_gun");
		this.artillery = results.getInt("artillery");
		this.fortification = results.getInt("fortification");
		this.tank = results.getInt("tank");
	}

	public void setSize(int size)
	{
		if(size < 0)
			size = 0;
		this.addField("size", size);
		this.size = size;
	}

	public void setTraining(int training)
	{
		if(training < 0)
			training = 0;
		else if (training > 100)
			training = 100;
		this.addField("training", training);
		this.training = training;
	}

	public void setMusket(int musket)
	{
		if(musket < 0)
			musket = 0;
		else if(musket > 2000000000)
			musket = 2000000000;
		this.addField("musket", musket);
		this.musket = musket;
	}

	public void setRifledMusket(int rifledMusket)
	{
		if(rifledMusket < 0)
			rifledMusket = 0;
		else if(rifledMusket > 2000000000)
			rifledMusket = 2000000000;
		this.addField("rifled_musket", rifledMusket);
		this.rifledMusket = rifledMusket;
	}

	public void setSingleShot(int singleShot)
	{
		if(singleShot < 0)
			singleShot = 0;
		else if(singleShot > 2000000000)
			singleShot = 2000000000;
		this.addField("single_shot", singleShot);
		this.singleShot = singleShot;
	}

	public void setNeedleNose(int needleNose)
	{
		if(needleNose < 0)
			needleNose = 0;
		else if(needleNose > 2000000000)
			needleNose = 2000000000;
		this.addField("needle_nose", needleNose);
		this.needleNose = needleNose;
	}

	public void setBoltActionManual(int boltActionManual)
	{
		if(boltActionManual < 0)
			boltActionManual = 0;
		else if(boltActionManual > 2000000000)
			boltActionManual = 2000000000;
		this.addField("bolt_action_manual", boltActionManual);
		this.boltActionManual = boltActionManual;
	}

	public void setBoltActionClip(int boltActionClip)
	{
		if(boltActionClip < 0)
			boltActionClip = 0;
		else if(boltActionClip > 2000000000)
			boltActionClip = 2000000000;
		this.addField("bolt_action_clip", boltActionClip);
		this.boltActionClip = boltActionClip;
	}

	public void setStraightPull(int straightPull)
	{
		if(straightPull < 0)
			straightPull = 0;
		else if(straightPull > 2000000000)
			straightPull = 2000000000;
		this.addField("straight_pull", straightPull);
		this.straightPull = straightPull;
	}

	public void setSemiAuto(int semiAuto)
	{
		if(semiAuto < 0)
			semiAuto = 0;
		else if(semiAuto > 2000000000)
			semiAuto = 2000000000;
		this.addField("semi_auto", semiAuto);
		this.semiAuto = semiAuto;
	}

	public void setMachineGun(int machineGun)
	{
		if(machineGun < 0)
			machineGun = 0;
		else if(machineGun > 2000000000)
			machineGun = 2000000000;
		this.addField("machine_gun", machineGun);
		this.machineGun = machineGun;
	}

	public void setArtillery(int artillery)
	{
		if(artillery < 0)
			artillery = 0;
		else if(artillery > 2000000000)
			artillery = 2000000000;
		this.addField("artillery", artillery);
		this.artillery = artillery;
	}

	public void setTank(int tank)
	{
		if(tank < 0)
			tank = 0;
		else if(tank > 2000000000)
			tank = 2000000000;
		this.addField("tank", tank);
		this.tank = tank;
	}

	public void setFortification(int fortification)
	{
		if(fortification < 0)
			fortification = 0;
		if(fortification > 10)
			fortification = 10;
		this.addField("fortification", fortification);
		this.fortification = fortification;
	}
}
