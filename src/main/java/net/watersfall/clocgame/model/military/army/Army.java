package net.watersfall.clocgame.model.military.army;

import lombok.Getter;
import lombok.Setter;
import net.watersfall.clocgame.model.UpdatableLongId;
import net.watersfall.clocgame.model.city.City;
import net.watersfall.clocgame.model.nation.Nation;
import net.watersfall.clocgame.model.producible.Producible;
import net.watersfall.clocgame.model.producible.ProducibleCategory;
import net.watersfall.clocgame.model.producible.Producibles;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Army extends UpdatableLongId
{
	private @Getter long id;
	private @Getter String name;
	private @Getter int owner, training, experience, specializationAmount;
	private @Getter ArmySpecialization specialization;
	private @Getter ArmyLocation location;
	private @Getter @Setter Nation nation;
	private @Getter @Setter City city;
	private @Getter Priority priority;
	private @Getter ArrayList<Battalion> battalions;

	public Army(ResultSet results) throws SQLException
	{
		super("armies", results.getLong("id"));
		this.id = results.getLong("id");
		this.owner = results.getInt("owner");
		this.specialization = ArmySpecialization.valueOf(results.getString("specialization_type"));
		this.specializationAmount = results.getInt("specialization_amount");
		this.training = results.getInt("training");
		this.experience = results.getInt("experience");
		this.location = ArmyLocation.valueOf(results.getString("location"));
		this.name = results.getString("name");
		this.nation = null;
		this.city = null;
		this.priority = Priority.valueOf(results.getString("priority"));
		this.battalions = new ArrayList<>();
	}

	public boolean canBeDamaged()
	{
		for(Battalion battalion : battalions)
		{
			if(battalion.canBeDamaged())
			{
				return true;
			}
		}
		return false;
	}

	public int getAttack()
	{
		int total = 0;
		for(Battalion battalion : battalions)
		{
			total += battalion.getAttack();
		}
		return total;
	}

	public int getDefense()
	{
		int total = 0;
		for(Battalion battalion : battalions)
		{
			total += battalion.getDefense();
		}
		return total;
	}

	public int getBreakthrough()
	{
		int total = 0;
		for(Battalion battalion : battalions)
		{
			total += battalion.getBreakthrough();
		}
		return total;
	}

	public double getWeight()
	{
		return 0D;
	}

	public HashMap<ProducibleCategory, Integer> getEquipment()
	{
		HashMap<ProducibleCategory, Integer> map = new HashMap<>();
		for(Battalion battalion : this.battalions)
		{
			for(ArmyEquipment equipment : battalion.getEquipment())
			{
				if(!map.containsKey(equipment.getEquipment().getProducible().getCategory()))
				{
					map.put(equipment.getEquipment().getProducible().getCategory(), equipment.getAmount());
				}
				else
				{
					map.compute(equipment.getEquipment().getProducible().getCategory(), (k, v) -> v = v + equipment.getAmount());
				}
			}
		}
		return map;
	}

	public HashMap<ProducibleCategory, Integer> getNeededEquipment()
	{
		HashMap<ProducibleCategory, Integer> map = new HashMap<>();
		for(Battalion battalion : this.battalions)
		{
			for(Map.Entry<ProducibleCategory, Integer> entry : battalion.getRequiredEquipment().entrySet())
			{
				if(!map.containsKey(entry.getKey()))
				{
					map.put(entry.getKey(), entry.getValue());
				}
				else
				{
					map.compute(entry.getKey(), (k, v) -> v = v + entry.getValue());
				}
			}
		}
		return map;
	}

	public HashMap<ProducibleCategory, Integer> getMaxEquipment()
	{
		HashMap<ProducibleCategory, Integer> map = new HashMap<>();
		for(Battalion battalion : this.battalions)
		{
			for(Map.Entry<ProducibleCategory, Integer> entry : battalion.getType().getEquipment().entrySet())
			{
				if(!map.containsKey(entry.getKey()))
				{
					map.put(entry.getKey(), entry.getValue());
				}
				else
				{
					map.compute(entry.getKey(), (k, v) -> v = v + entry.getValue());
				}
			}
		}
		return map;
	}

	public HashMap<ProducibleCategory, HashMap<Producibles, Integer>> getEquipmentTypes()
	{
		HashMap<ProducibleCategory, HashMap<Producibles, Integer>> map = new HashMap<>();
		for(Battalion battalion : battalions)
		{
			for(ArmyEquipment equipment : battalion.getEquipment())
			{
				if(!map.containsKey(equipment.getEquipment().getProducible().getCategory()))
				{
					HashMap<Producibles, Integer> toAdd = new HashMap<>();
					toAdd.put(equipment.getEquipment(), equipment.getAmount());
					map.put(equipment.getEquipment().getProducible().getCategory(), toAdd);
				}
				else
				{
					HashMap<Producibles, Integer> toAdd = map.get(equipment.getEquipment().getProducible().getCategory());
					if(!toAdd.containsKey(equipment.getEquipment()))
					{
						toAdd.put(equipment.getEquipment(), equipment.getAmount());
					}
					else
					{
						toAdd.compute(equipment.getEquipment(), (k, v) -> v = v + equipment.getAmount());
					}
				}
			}
		}
		return map;
	}

	public boolean isValidUpgrade(Producible producible)
	{
		for(Battalion battalion : this.battalions)
		{
			if(battalion.isValidUpgrade(producible))
			{
				return true;
			}
		}
		return false;
	}

	public int getNeededManpower()
	{
		return this.getMaxSize() - this.getSize();
	}

	public int getSize()
	{
		int total = 0;
		for(Battalion battalion : this.battalions)
		{
			total += battalion.getSize();
		}
		return total;
	}

	public int getMaxSize()
	{
		int total = 0;
		for(Battalion battalion : battalions)
		{
			total += battalion.getMaxSize();
		}
		return total;
	}

	public boolean hasBattalionType(BattalionType type)
	{
		for(Battalion battalion : battalions)
		{
			if(battalion.getType() == type)
			{
				return true;
			}
		}
		return false;
	}

	public Battalion getBattalionById(long id)
	{
		for(int i = battalions.size() - 1; i >= 0; i--)
		{
			if(battalions.get(i).getId() == id)
			{
				return battalions.get(i);
			}
		}
		return null;
	}

	@Override
	public void update(Connection conn) throws SQLException
	{
		super.update(conn);
		for(Battalion battalion : battalions)
		{
			battalion.update(conn);
		}
	}
}
