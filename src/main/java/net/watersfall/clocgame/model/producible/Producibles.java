package net.watersfall.clocgame.model.producible;

import lombok.Getter;
import net.watersfall.clocgame.model.producible.army.artillery.Artillery;
import net.watersfall.clocgame.model.producible.army.infantry.*;
import net.watersfall.clocgame.model.producible.army.tank.Tank;
import net.watersfall.clocgame.model.producible.military.plane.bomber.Bombers;
import net.watersfall.clocgame.model.producible.military.plane.bomber.Zeppelins;
import net.watersfall.clocgame.model.producible.military.plane.fighter.BiplaneFighters;
import net.watersfall.clocgame.model.producible.military.plane.fighter.MonoplaneFighters;
import net.watersfall.clocgame.model.producible.military.plane.fighter.TriplaneFighters;
import net.watersfall.clocgame.model.producible.military.plane.recon.ReconBalloons;
import net.watersfall.clocgame.model.producible.military.plane.recon.ReconPlanes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Producibles
{
	MUSKET(new Musket()),
	RIFLED_MUSKET(new RifledMusket()),
	SINGLE_SHOT(new SingleShot()),
	NEEDLE_NOSE(new NeedleNose()),
	BOLT_ACTION_MANUAL(new BoltActionManual()),
	BOLT_ACTION_CLIP(new BoltActionClip()),
	STRAIGHT_PULL(new StraightPull()),
	SEMI_AUTO(new SemiAuto()),
	MACHINE_GUN(new MachineGun()),
	ARTILLERY(new Artillery()),
	TANK(new Tank()),
	BIPLANE_FIGHTERS(new BiplaneFighters()),
	TRIPLANE_FIGHTERS(new TriplaneFighters()),
	MONOPLANE_FIGHTERS(new MonoplaneFighters()),
	RECON_BALLOONS(new ReconBalloons()),
	RECON_PLANES(new ReconPlanes()),
	ZEPPELINS(new Zeppelins()),
	BOMBERS(new Bombers());


	private @Getter Producible producible;
	Producibles(Producible producible)
	{
		this.producible = producible;
	}

	public static ArrayList<Producibles> getProduciblesForCategory(ProducibleCategory category)
	{
		ArrayList<Producibles> list = new ArrayList<>();
		for(Producibles producibles : Producibles.values())
		{
			if(producibles.producible.getCategory() == category)
			{
				list.add(producibles);
			}
		}
		list.sort((p1, p2) -> (int) (p2.getProducible().getProductionICCost() - p1.getProducible().getProductionICCost()));
		return list;
	}

	public static ArrayList<Producibles> getProduciblesByCategories(ProducibleCategory... category)
	{
		List<ProducibleCategory> categories = Arrays.asList(category);
		ArrayList<Producibles> list = new ArrayList<>();
		for(Producibles producibles : Producibles.values())
		{
			if(categories.contains(producibles.producible.getCategory()))
			{
				list.add(producibles);
			}
		}
		return list;
	}

	public static Producibles valueOf(Producible producible)
	{
		for(Producibles producibles : Producibles.values())
		{
			if(producibles.getProducible() == producible)
			{
				return producibles;
			}
		}
		return null;
	}
}
