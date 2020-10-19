package net.watersfall.clocgame.model.military.army;

import lombok.Getter;
import net.watersfall.clocgame.model.city.City;
import net.watersfall.clocgame.model.nation.Nation;

import java.util.HashMap;

public class EstimatedBattlePlan
{
	private @Getter Nation nation;
	private @Getter ArmyLocation battleLocation;
	private @Getter Nation locationNation;
	private @Getter City locationCity;

	public EstimatedBattlePlan(Nation nation, ArmyLocation battleLocation, Nation locationNation)
	{
		this.nation = nation;
		this.battleLocation = battleLocation;
		this.locationNation = locationNation;
	}

	public EstimatedBattlePlan(Nation nation, ArmyLocation battleLocation, City locationCity)
	{
		this.nation = nation;
		this.battleLocation = battleLocation;
		this.locationCity = locationCity;
	}

	public HashMap<Army, Double> getArmies()
	{
		HashMap<Army, Double> map = new HashMap<>();
		Army army;
		if(this.battleLocation == ArmyLocation.CITY)
		{
			if(nation.getId() == locationCity.getOwner())
			{
				//TODO: Proper City Garrisons
				//map.put(locationCity.getArmy());
			}
		}
		for(int i = 0; i < nation.getArmies().size(); i++)
		{
			army = nation.getArmies().get(i);
			if(battleLocation == ArmyLocation.NATION)
			{
				if(army.getLocation() == battleLocation)
				{
					if(army.getNation().equals(this.locationNation))
					{
						map.put(army, 1D);
					}
					else
					{
						map.put(army, 0.25D);
					}
				}
				else
				{
					map.put(army, 0.5D);
				}
			}
			else
			{
				if(army.getLocation() == battleLocation)
				{
					if(army.getCity().equals(this.locationCity))
					{
						map.put(army, 1D);
					}
					else
					{
						map.put(army, 0D);
					}
				}
				else
				{
					map.put(army, 0.5D);
				}
			}
		}
		return map;
	}
}
