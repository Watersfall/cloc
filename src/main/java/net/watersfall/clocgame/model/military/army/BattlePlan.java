package net.watersfall.clocgame.model.military.army;

import lombok.Getter;
import lombok.Setter;
import net.watersfall.clocgame.model.city.City;
import net.watersfall.clocgame.model.nation.Nation;

import java.util.ArrayList;

public class BattlePlan
{
	private @Getter ArrayList<Army> armies;
	private @Getter ArmyLocation battleLocation;
	private @Getter Nation nation, locationNation;
	private @Getter City locationCity;
	private @Getter @Setter int attack, defense, breakthrough;

	public BattlePlan(Nation nation, ArrayList<Army> armies, ArmyLocation location, Nation locationNation)
	{
		this.nation = nation;
		this.armies = armies;
		this.battleLocation = location;
		this.locationNation = locationNation;
		generateValues();
	}

	public BattlePlan(Nation nation, ArrayList<Army> armies, ArmyLocation location, City locationCity)
	{
		this.nation = nation;
		this.armies = armies;
		this.battleLocation = location;
		this.locationCity = locationCity;
		generateValues();
	}

	private void generateValues()
	{
		this.attack = 0;
		this.defense = 0;
		this.breakthrough = 0;
		for(Army army : armies)
		{
			attack += army.getAttack();
			defense += army.getDefense();
			breakthrough += army.getBreakthrough();
		}
	}

	public int damage()
	{
		ArrayList<Army> armies = new ArrayList<>(this.armies);
		armies.removeIf((army -> !army.canBeDamaged()));
		if(armies.size() <= 0)
		{
			return 0;
		}
		Army army = armies.get((int)(Math.random() * armies.size()));
		ArrayList<Battalion> battalions = new ArrayList<>(army.getBattalions());
		battalions.removeIf((battalion -> !battalion.canBeDamaged()));
		if(battalions.size() <= 0)
		{
			return 0;
		}
		Battalion battalion = battalions.get((int)(Math.random() * battalions.size()));
		return battalion.damage((Math.random() + 1D) / 2D);
	}
}
