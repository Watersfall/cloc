package com.watersfall.clocgame.model.military;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public enum Equipment
{
	MUSKET("Muskets", 1.5),
	RIFLED_MUSKET("Rifled Muskets", 3),
	SINGLE_SHOT_RIFLE("Single Shot Rifles", 4.5),
	NEEDLE_NOSE("Needle Nose Rifles", 6),
	BOLT_ACTION_MANUAL("Manually Loaded Bolt Action Rifles", 7.5),
	BOLT_ACTION_CLIP("Clip Loaded Bolt Action Rifles", 9),
	STRAIGHT_PULL_RIFLE("Straight Pull Rifles", 10.5),
	SEMI_AUTOMATIC("Semi Automatic Rifles", 12),
	MACHINE_GUN("Machine Guns", 13.5),
	ARTILLERY("Artillery", 25.0),
	TANK("Tanks", 50.0);

	private @Getter String name;
	private @Getter double power;
	Equipment(String name, double power)
	{
		this.name = name;
		this.power = power;
	}

	public static List<Equipment> getInfantryEquipment()
	{
		return Arrays.asList(MACHINE_GUN, SEMI_AUTOMATIC, STRAIGHT_PULL_RIFLE, BOLT_ACTION_CLIP, BOLT_ACTION_MANUAL,
				NEEDLE_NOSE, SINGLE_SHOT_RIFLE, RIFLED_MUSKET, MUSKET);
	}

	public static List<Equipment> getArtillery()
	{
		return Arrays.asList(ARTILLERY);
	}

	public static List<Equipment> getArmor()
	{
		return Arrays.asList(TANK);
	}
}
