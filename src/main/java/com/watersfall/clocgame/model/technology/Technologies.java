package com.watersfall.clocgame.model.technology;

import com.watersfall.clocgame.model.technology.technologies.Category;
import com.watersfall.clocgame.model.technology.technologies.single.artillery.TechnologyArtillery;
import com.watersfall.clocgame.model.technology.technologies.single.doctrine.*;
import com.watersfall.clocgame.model.technology.technologies.single.economy.*;
import com.watersfall.clocgame.model.technology.technologies.single.vehicles.TechnologyShipOil;
import com.watersfall.clocgame.model.technology.technologies.single.vehicles.TechnologyTank;
import com.watersfall.clocgame.model.technology.technologies.single.vehicles.air.*;
import com.watersfall.clocgame.model.technology.technologies.single.weapons.*;
import com.watersfall.clocgame.model.technology.technologies.single.wmd.TechnologyAdvancedChemicalWeapons;
import com.watersfall.clocgame.model.technology.technologies.single.wmd.TechnologyChemicalWeapons;
import lombok.Getter;

import java.util.Map;

public enum Technologies
{
	/*
	** Weapons
	 */
	MUSKET(TechnologyMusket.getInstance(), Category.WEAPONS, 1, 1, new String[]{""}),
	RIFLED_MUSKET(TechnologyRifledMusket.getInstance(), Category.WEAPONS, 1, 2, new String[]{"vertical"}),
	SINGLE_SHOT_RIFLE(TechnologySingleShotRifle.getInstance(), Category.WEAPONS, 1, 3, new String[]{"vertical"}),
	NEEDLE_NOSE_RIFLE(TechnologyNeedleNoseRifle.getInstance(), Category.WEAPONS, 2, 3, new String[]{"horizontalLeft"}),
	BOLT_ACTION_MANUAL(TechnologyBoltActionManual.getInstance(), Category.WEAPONS, 1, 4, new String[]{"vertical"}),
	BOLT_ACTION_CLIP(TechnologyBoltActionClip.getInstance(), Category.WEAPONS, 2, 4, new String[]{"horizontalLeft"}),
	STRAIGHT_PULL_RIFLE(TechnologyStraightPullRifle.getInstance(), Category.WEAPONS, 3, 4, new String[]{"horizontalLeft"}),
	SEMI_AUTOMATIC(TechnologySemiAutomatic.getInstance(), Category.WEAPONS, 3, 5, new String[]{"vertical"}),
	MACHINE_GUN(TechnologyMachineGun.getInstance(), Category.WEAPONS, 1, 5, new String[]{"vertical"}),

	/*
	** ARTILLERY
	 */
	ARTILLERY(TechnologyArtillery.getInstance(), Category.ARTILLERY, 2, 1, new String[]{""}),

	/*
	** Air
	 */
	RECON_BALLOONS(TechnologyReconBalloon.getInstance(), Category.AIR, 1, 1, new String[]{"horizontalRight"}),
	RECON_BIPLANE(TechnologyReconPlane.getInstance(), Category.AIR, 2, 1, new String[]{}),
	ZEPPELIN_BOMBERS(TechnologyZeppelinBombers.getInstance(), Category.AIR, 1, 2, new String[]{"vertical"}),
	BIPLANE_FIGHTERS(TechnologyBiplaneFighter.getInstance(), Category.AIR, 2, 2, new String[]{"vertical", "horizontalRight"}),
	TRIPLANE_FIGHTERS(TechnologyTriplaneFighter.getInstance(), Category.AIR, 3, 2, new String[]{}),
	MONOPLANE_FIGHTERS(TechnologyMonoplaneFighter.getInstance(), Category.AIR, 3, 3, new String[]{"cornerBottomLeftHalf"}),
	BOMBERS(TechnologyBombers.getInstance(), Category.AIR, 2, 3, new String[]{"vertical", "cornerBottomLeft"}),
	STRATEGIC_BOMBERS(TechnologyStrategicBombers.getInstance(), Category.AIR, 2, 4, new String[]{"vertical"}),

	/*
	** Doctrine
	 */
	BASIC_TRENCHES(TechnologyTrenches.getInstance(), Category.DOCTRINE, 1, 1, new String[]{}),
	BASIC_FORTIFICATIONS(TechnologyFortifications.getInstance(), Category.DOCTRINE, 1, 2, new String[]{"vertical"}),
	ADVANCED_TRENCHES(TechnologyAdvancedTrenches.getInstance(), Category.DOCTRINE, 1, 3, new String[]{"vertical"}),
	ADVANCED_FORTIFICATIONS(TechnologyReinforcedConcrete.getInstance(), Category.DOCTRINE, 1, 4, new String[]{"vertical"}),
	MOBILE_DEFENSE(TechnologyMobileDefense.getInstance(), Category.DOCTRINE, 1, 5, new String[]{"vertical"}),

	/*
	** Economy
	 */
	BASIC_ARTIFICIAL_FERTILIZER(TechnologyBasicArtificialFertilizer.getInstance(), Category.ECONOMY, 1, 1, new String[]{}),
	ARTIFICIAL_FERTILIZER(TechnologyArtificialFertilizer.getInstance(), Category.ECONOMY, 2, 1, new String[]{"horizontalLeft"}),
	ADVANCED_ARTIFICIAL_FERTILIZER(TechnologyAdvancedArtificialFertilizer.getInstance(), Category.ECONOMY, 3, 1, new String[]{"horizontalLeft"}),
	FARMING_MACHINES(TechnologyFarmingMachines.getInstance(), Category.ECONOMY, 2, 2, new String[]{"vertical"}),
	ADVANCED_FARMING_MACHINES(TechnologyAdvancedFarmingMachines.getInstance(), Category.ECONOMY, 3, 2, new String[]{"horizontalLeft"}),


	/*
	** Other
	 */
	TANK(TechnologyTank.getInstance(), Category.LAND, 1, 1, new String[]{""}),
	SHIP_OIL(TechnologyShipOil.getInstance(), Category.SEA, 1, 1, new String[]{""}),
	CHEMICAL_WEAPONS(TechnologyChemicalWeapons.getInstance(), Category.WMD, 1, 1, new String[]{""}),
	ADVANCED_CHEMICAL_WEAPONS(TechnologyAdvancedChemicalWeapons.getInstance(), Category.WMD, 1, 2, new String[]{"vertical"});

	private @Getter Technology technology;
	private @Getter Category category;

	/**
	 * Display parameters
	 */
	private @Getter int x;
	private @Getter int y;
	private @Getter String[] cssClass;

	Technologies(Technology technology, Category category, int x, int y, String[] cssClass)
	{
		this.technology = technology;
		this.category = category;
		this.x = x;
		this.y = y;
		this.cssClass = cssClass;
	}

	public String toString()
	{
		String value = "";
		value += "<h2 style=\"text-align: center;\">" + technology.getName() + "</h2>";
		value += "<p class=\"textLeft\">" + technology.getDesc() + "</p>";
		value += "<p class=\"textLeft\">Effects: </p>";
		value += "<ul class=\"bulletList\">";
		if(this.technology.getEffects().isEmpty())
		{
			value += "<li class=\"textLeft\">None</li>";
		}
		else
		{
			for(String effect : this.technology.getEffects())
			{
				value += "<li class=\"textLeft\">" + effect + "</li>";
			}
		}
		value += "</ul><br>";
		value += "<p class=\"textLeft\">Prerequisites: </p>";
		value += "<ul class=\"bulletList\">";
		if(this.technology.getPrerequisites().isEmpty())
		{
			value += "<li class=\"textLeft\">None</li>";
		}
		else
		{
			for(Technologies tech : this.technology.getPrerequisites())
			{
				value += "<li class=\"textLeft\">" + tech.getTechnology().getName() + "</ll>";
			}
		}
		value += "</ul><br>";
		value += "<p class=\"textLeft\">Costs: </p>";
		value += "<ul class=\"bulletList\">";
		if(this.technology.getCosts().isEmpty())
		{
			value += "<li class=\"textLeft\">None</li>";
		}
		else
		{
			for(Map.Entry<String, Integer> cost: this.technology.getCosts().entrySet())
			{
				value += "<li class=\"textLeft\">" + cost.getValue() + " " + cost.getKey() + "</li>";
			}
		}
		value += "</ul>";
		value += "<button onclick=tech('" + this.name() + "');>Research</button>";
		return value;
	}
}
