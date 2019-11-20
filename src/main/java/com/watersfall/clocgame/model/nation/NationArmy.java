package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.exception.NationNotFoundException;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NationArmy extends NationBase
{
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
	private @Getter int fortification;

	public NationArmy(int size, int training, int musket, int rifledMusket, int singleShot, int needleNose, int boltActionManual, int boltActionClip, int straightPull, int semiAuto, int machineGun, int artillery, int fortification)
	{
		this.size = size;
		this.training = training;
		this.musket = musket;
		this.rifledMusket = rifledMusket;
		this.singleShot = singleShot;
		this.needleNose = needleNose;
		this.boltActionManual = boltActionManual;
		this.boltActionClip = boltActionClip;
		this.straightPull = straightPull;
		this.semiAuto = semiAuto;
		this.machineGun = machineGun;
		this.artillery = artillery;
		this.fortification = fortification;
	}

	public NationArmy(ResultSet results, Connection connection, int id, boolean safe) throws SQLException
	{
		this.id = results.getInt("id");
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
	}

	public NationArmy(Connection connection, int id, boolean safe) throws SQLException
	{
		super(connection, id, safe);
		PreparedStatement read;
		if(safe)
		{
			read = connection.prepareStatement("SELECT size, training, musket, rifled_musket, single_shot, needle_nose, bolt_action_manual, bolt_action_clip, straight_pull, semi_auto, machine_gun, artillery, fortification, id " + "FROM cloc_army " + "WHERE id=? FOR UPDATE ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		}
		else
		{
			read = connection.prepareStatement("SELECT size, training, musket, rifled_musket, single_shot, needle_nose, bolt_action_manual, bolt_action_clip, straight_pull, semi_auto, machine_gun, artillery, fortification, id " + "FROM cloc_army " + "WHERE id=?");
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
			this.size = results.getInt(1);
			this.training = results.getInt(2);
			this.musket = results.getInt(3);
			this.rifledMusket = results.getInt(4);
			this.singleShot = results.getInt(5);
			this.needleNose = results.getInt(6);
			this.boltActionManual = results.getInt(7);
			this.boltActionClip = results.getInt(8);
			this.straightPull = results.getInt(9);
			this.semiAuto = results.getInt(10);
			this.machineGun = results.getInt(11);
			this.artillery = results.getInt(12);
			this.fortification = results.getInt(13);
		}
	}

	public void setSize(int size) throws SQLException
	{
		if(size < 0)
		{
			size = 0;
		}
		this.size = size;
		this.results.updateInt(1, size);
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
		this.training = training;
		this.results.updateInt(2, training);
	}

	public void setMusket(int musket) throws SQLException
	{
		if(musket < 0)
		{
			musket = 0;
		}
		this.musket = musket;
		this.results.updateInt(3, musket);
	}

	public void setRifledMusket(int rifledMusket) throws SQLException
	{
		if(rifledMusket < 0)
		{
			rifledMusket = 0;
		}
		this.rifledMusket = rifledMusket;
		this.results.updateInt(4, rifledMusket);
	}

	public void setSingleShot(int singleShot) throws SQLException
	{
		if(singleShot < 0)
		{
			singleShot = 0;
		}
		this.singleShot = singleShot;
		this.results.updateInt(5, singleShot);
	}

	public void setNeedleNose(int needleNose) throws SQLException
	{
		if(needleNose < 0)
		{
			needleNose = 0;
		}
		this.needleNose = needleNose;
		this.results.updateInt(6, needleNose);
	}

	public void setBoltActionManual(int boltActionManual) throws SQLException
	{
		if(boltActionManual < 0)
		{
			boltActionManual = 0;
		}
		this.boltActionManual = boltActionManual;
		this.results.updateInt(7, boltActionManual);
	}
	
	public void setBoltActionClip(int boltActionClip) throws SQLException
	{
		if(boltActionClip < 0)
		{
			boltActionClip = 0;
		}
		this.boltActionClip = boltActionClip;
		this.results.updateInt(8, boltActionClip);
	}

	public void setStraightPull(int straightPull) throws SQLException
	{
		if(straightPull < 0)
		{
			straightPull = 0;
		}
		this.straightPull = straightPull;
		this.results.updateInt(9, straightPull);
	}
	
	public void setSemiAuto(int semiAuto) throws SQLException
	{
		if(semiAuto < 0)
		{
			semiAuto = 0;
		}
		this.semiAuto = semiAuto;
		this.results.updateInt(10, semiAuto);
	}
	
	public void setMachineGun(int machineGun) throws SQLException
	{
		if(machineGun < 0)
		{
			machineGun = 0;
		}
		this.machineGun = machineGun;
		this.results.updateInt(11, machineGun);
	}

	public void setArtillery(int artillery) throws SQLException
	{
		if(artillery < 0)
		{
			artillery = 0;
		}
		this.artillery = artillery;
		this.results.updateInt(12, artillery);
	}

	public void setFortification(int fortification) throws SQLException
	{
		if(fortification < 0)
		{
			fortification = 0;
		}
		else if(fortification  > 10)
		{
			fortification = 10;
		}
		this.fortification = fortification;
		this.results.updateInt(13, fortification);
	}
}
