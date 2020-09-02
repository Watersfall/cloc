package com.watersfall.clocgame.model.technology.technologies.single.weapons;

import com.watersfall.clocgame.model.nation.Nation;
import com.watersfall.clocgame.model.producible.Producible;
import com.watersfall.clocgame.model.producible.Producibles;
import com.watersfall.clocgame.model.technology.SingleTechnology;
import com.watersfall.clocgame.model.technology.Technologies;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;

public class TechnologyMachineGun extends SingleTechnology
{
	private static TechnologyMachineGun technologyMachineGun;
	public static final String NAME = "Machine Guns";
	public static final String DESC = "Machine Guns";
	public static final String COLUMN_NAME = "machine_gun_tech";
	public static final String FIELD_NAME = "MachineGun";
	private @Getter String productionName = "machine_gun";

	private TechnologyMachineGun()
	{
		super(NAME, DESC, COLUMN_NAME, FIELD_NAME, 1);
		this.prerequisites = new ArrayList<>();
		this.costs = new HashMap<>();
		this.requirements = new HashMap<>();
		costs.put("research", 100);
		costs.put("steel", 100);
		effects.add("Unlocks production: Machine Guns");
		prerequisites.add(Technologies.BOLT_ACTION_MANUAL);
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
	public boolean isProducible()
	{
		return true;
	}

	@Override public Producible getProducibleItem()
	{
		return Producibles.MACHINE_GUN.getProducible();
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