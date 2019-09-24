package com.watersfall.clocgame.model.technology;

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
	BALL_AND_POWDER(TechnologyBallAndPowder.getInstance()),
	SMOKELESS_POWDER(TechnologySmokelessPowder.getInstance()),
	PAPER_CARTRIDGE(TechnologyPaperCartridge.getInstance()),
	BRASS_CARTRIDGE(TechnologyBrassCartridge.getInstance()),
	RIFLE_CLIPS(TechnologyRifleClips.getInstance()),
	BELT(TechnologyBelt.getInstance()),
	DETACHABLE_MAGAZINES(TechnologyDetachableMagazines.getInstance()),

	/*
	** Weapons
	 */
	MUSKET(TechnologyMusket.getInstance()),
	RIFLED_MUSKET(TechnologyRifledMusket.getInstance()),
	SINGLE_SHOT_RIFLE(TechnologySingleShotRifle.getInstance()),
	NEEDLE_NOSE_RIFLE(TechnologyNeedleNoseRifle.getInstance()),
	BOLT_ACTION_MANUAL(TechnologyBoltActionManual.getInstance()),
	BOLT_ACTION_CLIP(TechnologyBoltActionClip.getInstance()),
	STRAIGHT_PULL_RIFLE(TechnologyStraightPullRifle.getInstance()),
	SEMI_AUTOMATIC(TechnologySemiAutomatic.getInstance()),
	MACHINE_GUN(TechnologyMachineGun.getInstance()),


	TANK(TechnologyTank.getInstance()),
	SHIP_OIL(TechnologyShipOil.getInstance()),
	BOMBERS(TechnologyBombers.getInstance()),
	CHEMICAL_WEAPONS(TechnologyChemicalWeapons.getInstance()),
	ADVANCED_CHEMICAL_WEAPONS(TechnologyAdvancedChemicalWeapons.getInstance()),
	STRATEGIC_BOMBING(TechnologyStrategicBombing.getInstance());

	private @Getter Technology technology;

	Technologies(Technology technology)
	{
		this.technology = technology;
	}
}
