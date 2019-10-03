package com.watersfall.clocgame.model.technology;

import com.watersfall.clocgame.model.technology.technologies.Category;
import com.watersfall.clocgame.model.technology.technologies.single.ammoandfeed.*;
import com.watersfall.clocgame.model.technology.technologies.single.vehicles.TechnologyBombers;
import com.watersfall.clocgame.model.technology.technologies.single.vehicles.TechnologyShipOil;
import com.watersfall.clocgame.model.technology.technologies.single.vehicles.TechnologyStrategicBombing;
import com.watersfall.clocgame.model.technology.technologies.single.vehicles.TechnologyTank;
import com.watersfall.clocgame.model.technology.technologies.single.weapons.*;
import com.watersfall.clocgame.model.technology.technologies.single.wmd.TechnologyAdvancedChemicalWeapons;
import com.watersfall.clocgame.model.technology.technologies.single.wmd.TechnologyChemicalWeapons;
import lombok.Getter;

public enum Technologies
{
	/*
	** Ammo and Feed
	 */
	BALL_AND_POWDER(TechnologyBallAndPowder.getInstance(), Category.AMMO_AND_FEED, 2, 1, ""),
	SMOKELESS_POWDER(TechnologySmokelessPowder.getInstance(), Category.AMMO_AND_FEED, 2, 2, "vertical"),
	PAPER_CARTRIDGE(TechnologyPaperCartridge.getInstance(), Category.AMMO_AND_FEED, 2, 3, "vertical"),
	BRASS_CARTRIDGE(TechnologyBrassCartridge.getInstance(), Category.AMMO_AND_FEED, 2, 4, "vertical"),
	RIFLE_CLIPS(TechnologyRifleClips.getInstance(), Category.AMMO_AND_FEED, 2, 5, "vertical"),
	BELT(TechnologyBelt.getInstance(), Category.AMMO_AND_FEED, 1, 6, "cornerBottomRight"),
	DETACHABLE_MAGAZINES(TechnologyDetachableMagazines.getInstance(), Category.AMMO_AND_FEED, 3, 6, "cornerBottomLeft"),

	/*
	** Weapons
	 */
	MUSKET(TechnologyMusket.getInstance(), Category.WEAPONS, 1, 1, ""),
	RIFLED_MUSKET(TechnologyRifledMusket.getInstance(), Category.WEAPONS, 1, 2, "vertical"),
	SINGLE_SHOT_RIFLE(TechnologySingleShotRifle.getInstance(), Category.WEAPONS, 1, 3, "vertical"),
	NEEDLE_NOSE_RIFLE(TechnologyNeedleNoseRifle.getInstance(), Category.WEAPONS, 2, 3, "horizontalLeft"),
	BOLT_ACTION_MANUAL(TechnologyBoltActionManual.getInstance(), Category.WEAPONS, 1, 4, "vertical"),
	BOLT_ACTION_CLIP(TechnologyBoltActionClip.getInstance(), Category.WEAPONS, 2, 4, "horizontalLeft"),
	STRAIGHT_PULL_RIFLE(TechnologyStraightPullRifle.getInstance(), Category.WEAPONS, 3, 4, "horizontalLeft"),
	SEMI_AUTOMATIC(TechnologySemiAutomatic.getInstance(), Category.WEAPONS, 3, 5, "vertical"),
	MACHINE_GUN(TechnologyMachineGun.getInstance(), Category.WEAPONS, 2, 5, "vertical"),


	TANK(TechnologyTank.getInstance(), Category.LAND, 1, 1, ""),
	SHIP_OIL(TechnologyShipOil.getInstance(), Category.SEA, 1, 1, ""),
	BOMBERS(TechnologyBombers.getInstance(), Category.AIR, 1, 1, ""),
	CHEMICAL_WEAPONS(TechnologyChemicalWeapons.getInstance(), Category.WMD, 1, 1, ""),
	ADVANCED_CHEMICAL_WEAPONS(TechnologyAdvancedChemicalWeapons.getInstance(), Category.WMD, 1, 2, "vertical"),
	STRATEGIC_BOMBING(TechnologyStrategicBombing.getInstance(), Category.AIR, 1, 2, "vertical");

	private @Getter Technology technology;
	private @Getter Category category;
	private @Getter int x;
	private @Getter int y;
	private @Getter String cssClass;

	Technologies(Technology technology, Category category, int x, int y, String cssClass)
	{
		this.technology = technology;
		this.category = category;
		this.x = x;
		this.y = y;
		this.cssClass = cssClass;
	}
}
