package com.watersfall.clocgame.model.technology.technologies.single.weapons;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.technology.SingleTechnology;
import com.watersfall.clocgame.model.technology.Technologies;

import java.util.ArrayList;
import java.util.HashMap;

public class TechnologyMachineGun extends SingleTechnology
{
	private static TechnologyMachineGun technologyMachineGun;
	public static final String NAME = "Machine Guns";
	public static final String DESC = "Machine Guns";
	public static final String COLUMN_NAME = "machine_gun_tech";
	public static final String FIELD_NAME = "MachineGun";

	private TechnologyMachineGun()
	{
		super(NAME, DESC, COLUMN_NAME, FIELD_NAME, 1);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		prerequisites.add(Technologies.BELT);
		costs.put("research", 100);
		costs.put("steel", 100);
	}

	public static TechnologyMachineGun getInstance()
	{
		if(technologyMachineGun == null)
		{
			technologyMachineGun = new TechnologyMachineGun();
		}
		return technologyMachineGun;
	}

	@Override
	public int getSuccessChance(Nation nation)
	{
		return 100;
	}

	@Override
	public Technologies getTechnology()
	{
		return Technologies.MACHINE_GUN;
	}
}