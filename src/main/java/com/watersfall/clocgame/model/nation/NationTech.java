package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.exception.NationNotFoundException;
import com.watersfall.clocgame.model.technology.Technologies;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class NationTech extends NationBase
{
	private @Getter int chem;
	private @Getter int advancedChem;
	private @Getter int bomber;
	private @Getter int stratBombing;
	private @Getter int tank;
	private @Getter int shipOil;
	private @Getter int boltAction;
	private @Getter int semiAuto;
	private @Getter int machineGun;
	private @Getter int food;
	private @Getter HashSet<Technologies> researchedTechs;

	/**
	 * Constructs the technologies of a Nation1
	 * @param connection the SQL connection to use
	 * @param id 		 The Nation ID
	 * @param safe 		 Whether the results should be safe to write to
	 * @throws SQLException on an SQL error
	 */
	public NationTech(Connection connection, int id, boolean safe) throws SQLException
	{
		super(connection, id, safe);
		PreparedStatement read;
		if(safe)
		{
			read = connection.prepareStatement("SELECT chem_tech, advanced_chem_tech, bomber_tech, strategic_bombing_tech, tank_tech, ship_oil_tech, bolt_action_tech, semi_automatic_tech, machine_gun_tech, food_tech, id " + "FROM cloc_tech " + "WHERE id=? FOR UPDATE ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		}
		else
		{
			read = connection.prepareStatement("SELECT chem_tech, advanced_chem_tech, bomber_tech, strategic_bombing_tech, tank_tech, ship_oil_tech, bolt_action_tech, semi_automatic_tech, machine_gun_tech, food_tech, id " + "FROM cloc_tech " + "WHERE id=?");
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
			this.chem = results.getInt(1);
			this.advancedChem = results.getInt(2);
			this.bomber = results.getInt(3);
			this.stratBombing = results.getInt(4);
			this.tank = results.getInt(5);
			this.shipOil = results.getInt(6);
			this.boltAction = results.getInt(7);
			this.semiAuto = results.getInt(8);
			this.machineGun = results.getInt(9);
			this.food = results.getInt(10);
			loadTechnologies();
		}
	}

	private void loadTechnologies()
	{
		this.researchedTechs = new HashSet<>();
		if(this.chem >= Technologies.CHEMICAL_WEAPONS.getTechnology().getRequiredSuccesses())
		{
			researchedTechs.add(Technologies.CHEMICAL_WEAPONS);
		}
		if(this.advancedChem >= Technologies.ADVANCED_CHEMICAL_WEAPONS.getTechnology().getRequiredSuccesses())
		{
			researchedTechs.add(Technologies.ADVANCED_CHEMICAL_WEAPONS);
		}
		if(this.bomber >= Technologies.BOMBERS.getTechnology().getRequiredSuccesses())
		{
			researchedTechs.add(Technologies.BOMBERS);
		}
		if(this.boltAction >= Technologies.BOLT_ACTION.getTechnology().getRequiredSuccesses())
		{
			researchedTechs.add(Technologies.BOLT_ACTION);
		}
	}

	public void setChem(int chem) throws SQLException
	{
		this.results.updateInt(1, chem);
	}

	public void setAdvancedChem(int chem) throws SQLException
	{
		this.results.updateInt(2, chem);
	}

	public void setBomber(int bomber) throws SQLException
	{
		this.results.updateInt(3, bomber);
	}

	public void setStratBombing(int stratBombing) throws SQLException
	{
		this.results.updateInt(4, stratBombing);
	}

	public void setTank(int tank) throws SQLException
	{
		this.results.updateInt(5, tank);
	}

	public void setShipOil(int shipOil) throws SQLException
	{
		this.results.updateInt(6, shipOil);
	}

	public void setBoltAction(int boltAction) throws SQLException
	{
		this.results.updateInt(7, boltAction);
	}

	public void setSemiAuto(int semiAuto) throws SQLException
	{
		this.results.updateInt(8, semiAuto);
	}

	public void setMachineGun(int machineGun) throws SQLException
	{
		this.results.updateInt(9, machineGun);
	}

	public void setFood(int food) throws SQLException
	{
		this.results.updateInt(10, food);
	}

	public void setTech(Technologies tech) throws SQLException
	{
		switch(tech)
		{
			case BOLT_ACTION:
				setBoltAction(boltAction + 1);
				break;
			case BOMBERS:
				setBomber(bomber + 1);
				break;
			case CHEMICAL_WEAPONS:
				setChem(chem + 1);
				break;
			case ADVANCED_CHEMICAL_WEAPONS:
				setAdvancedChem(advancedChem + 1);
				break;
		}
	}

}
