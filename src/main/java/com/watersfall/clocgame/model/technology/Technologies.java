package com.watersfall.clocgame.model.technology;

import com.watersfall.clocgame.model.technology.technologies.Category;
import com.watersfall.clocgame.model.technology.technologies.single.ammoandfeed.*;
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
	** Ammo and Feed
	 */
	BALL_AND_POWDER(TechnologyBallAndPowder.getInstance(), Category.AMMO_AND_FEED, 2, 1, new String[]{}),
	SMOKELESS_POWDER(TechnologySmokelessPowder.getInstance(), Category.AMMO_AND_FEED, 2, 2, new String[]{"vertical"}),
	PAPER_CARTRIDGE(TechnologyPaperCartridge.getInstance(), Category.AMMO_AND_FEED, 2, 3, new String[]{"vertical"}),
	BRASS_CARTRIDGE(TechnologyBrassCartridge.getInstance(), Category.AMMO_AND_FEED, 2, 4, new String[]{"vertical"}),
	RIFLE_CLIPS(TechnologyRifleClips.getInstance(), Category.AMMO_AND_FEED, 2, 5, new String[]{"vertical"}),
	BELT(TechnologyBelt.getInstance(), Category.AMMO_AND_FEED, 1, 6, new String[]{"cornerBottomRight"}),
	DETACHABLE_MAGAZINES(TechnologyDetachableMagazines.getInstance(), Category.AMMO_AND_FEED, 3, 6, new String[]{"cornerBottomLeft"}),

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
		value += "</ul>";
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
