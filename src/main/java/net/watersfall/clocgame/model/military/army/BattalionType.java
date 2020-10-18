package net.watersfall.clocgame.model.military.army;

import lombok.Getter;
import net.watersfall.clocgame.model.producible.ProducibleCategory;

import java.util.HashMap;

public enum BattalionType
{
	INFANTRY(1000, 10, 10, 2, 0.25, ProducibleCategory.INFANTRY_EQUIPMENT, 1000),
	ARTILLERY(300, 20, 5, 5, 1.0, ProducibleCategory.INFANTRY_EQUIPMENT, 150, ProducibleCategory.ARTILLERY, 24),
	ARMORED(500, 50, 5, 25, 2.5, ProducibleCategory.INFANTRY_EQUIPMENT, 150, ProducibleCategory.TANK, 48);

	private @Getter int size, attack, defense, breakthrough;
	private @Getter double weight;
	private @Getter HashMap<ProducibleCategory, Integer> equipment;
	BattalionType(int size, int attack, int defense, int breakthrough, double weight, Object... objects)
	{
		this.size = size;
		this.attack = attack;
		this.defense = defense;
		this.breakthrough = breakthrough;
		this.weight = weight;
		this.equipment = new HashMap<>();
		for(int i = 0; i < objects.length; i += 2)
		{
			equipment.put((ProducibleCategory)objects[i], (Integer)objects[i + 1]);
		}
	}
}
