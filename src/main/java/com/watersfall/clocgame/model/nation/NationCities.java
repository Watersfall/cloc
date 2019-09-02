package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.exception.NationNotFoundException;
import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class NationCities extends NationBase
{

	private @Getter HashMap<Integer, City> cities;

	public NationCities(Connection connection, int id, boolean safe) throws SQLException
	{
		super(connection, id, safe);
		PreparedStatement read;
		if(safe)
		{
			read = connection.prepareStatement("SELECT owner, id " + "FROM cloc_cities " + "WHERE owner=? FOR UPDATE ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		}
		else
		{
			read = connection.prepareStatement("SELECT owner, id " + "FROM cloc_cities " + "WHERE owner=?");
		}
		read.setInt(1, id);
		this.results = read.executeQuery();
		if(!results.first())
		{
			throw new NationNotFoundException("No nation with that id!");
		}
		else
		{
			results.beforeFirst();
			this.cities = new HashMap<>();
			this.connection = connection;
			this.safe = safe;
			this.id = id;
			while(results.next())
			{
				cities.put(results.getInt(2), new City(connection, results.getInt(2), safe));
			}
		}
	}

	public HashMap<String, Double> getTotalCoalProduction()
	{
		double mines = 0e0;
		double bonus = 0e0;
		double total = 0e0;
		double costs = 0e0;
		double net = 0e0;
		HashMap<String, Double> map;
		for(City city : this.getCities().values())
		{
			map = city.getCoalProduction();
			mines += map.get("mines");
			bonus += map.get("bonus");
			total += map.get("total");
			costs += map.get("costs");
			net += map.get("net");
		}
		map =  new HashMap<>();
		map.put("mines", mines);
		map.put("bonus", bonus);
		map.put("total", total);
		map.put("costs", costs);
		map.put("net", net);
		return map;
	}

	public HashMap<String, Double> getTotalIronProduction()
	{
		double mines = 0e0;
		double bonus = 0e0;
		double total = 0e0;
		double costs = 0e0;
		double net = 0e0;
		HashMap<String, Double> map;
		for(City city : this.getCities().values())
		{
			map = city.getIronProduction();
			mines += map.get("mines");
			bonus += map.get("bonus");
			total += map.get("total");
			costs += map.get("costs");
			net += map.get("net");
		}
		map =  new HashMap<>();
		map.put("mines", mines);
		map.put("bonus", bonus);
		map.put("total", total);
		map.put("costs", costs);
		map.put("net", net);
		return map;
	}

	public HashMap<String, Double> getTotalOilProduction()
	{
		double wells = 0e0;
		double bonus = 0e0;
		double total = 0e0;
		double costs = 0e0;
		double net = 0e0;
		HashMap<String, Double> map;
		for(City city : this.getCities().values())
		{
			map = city.getOilProduction();
			wells += map.get("wells");
			bonus += map.get("bonus");
			total += map.get("total");
			costs += map.get("costs");
			net += map.get("net");
		}
		map =  new HashMap<>();
		map.put("wells", wells);
		map.put("bonus", bonus);
		map.put("total", total);
		map.put("costs", costs);
		map.put("net", net);
		return map;
	}

	public HashMap<String, Double> getTotalSteelProduction()
	{
		double factories = 0e0;
		double bonus = 0e0;
		double total = 0e0;
		double costs = 0e0;
		double net = 0e0;
		HashMap<String, Double> map;
		for(City city : this.getCities().values())
		{
			map = city.getSteelProduction();
			factories += map.get("factories");
			bonus += map.get("bonus");
			total += map.get("total");
			costs += map.get("costs");
			net += map.get("net");
		}
		map =  new HashMap<>();
		map.put("factories", factories);
		map.put("bonus", bonus);
		map.put("total", total);
		map.put("costs", costs);
		map.put("net", net);
		return map;
	}

	public HashMap<String, Double> getTotalNitrogenProduction()
	{
		double factories = 0e0;
		double bonus = 0e0;
		double total = 0e0;
		double costs = 0e0;
		double net = 0e0;
		HashMap<String, Double> map;
		for(City city : this.getCities().values())
		{
			map = city.getNitrogenProduction();
			factories += map.get("factories");
			bonus += map.get("bonus");
			total += map.get("total");
			costs += map.get("costs");
			net += map.get("net");
		}
		map =  new HashMap<>();
		map.put("factories", factories);
		map.put("bonus", bonus);
		map.put("total", total);
		map.put("costs", costs);
		map.put("net", net);
		return map;
	}

	public HashMap<String, Double> getTotalWeaponsProduction()
	{
		double factories = 0e0;
		double bonus = 0e0;
		double total = 0e0;
		HashMap<String, Double> map;
		for(City city : this.getCities().values())
		{
			map = city.getWeaponsProduction();
			factories += map.get("factories");
			bonus += map.get("bonus");
			total += map.get("total");
		}
		map =  new HashMap<>();
		map.put("factories", factories);
		map.put("bonus", bonus);
		map.put("total", total);
		return map;
	}

	public HashMap<String, Double> getTotalResearchProduction()
	{
		double universities = 0e0;
		double bonus = 0e0;
		double total = 0e0;
		HashMap<String, Double> map;
		for(City city : this.getCities().values())
		{
			map = city.getResearchProduction();
			universities += map.get("universities");
			bonus += map.get("bonus");
			total += map.get("total");
		}
		map =  new HashMap<>();
		map.put("universities", universities);
		map.put("bonus", bonus);
		map.put("total", total);
		return map;
	}

	public HashMap<String, HashMap<String, Double>> getAllTotalProductions()
	{
		HashMap<String, HashMap<String, Double>> map = new HashMap<>();
		map.put("coal", this.getTotalCoalProduction());
		map.put("iron", this.getTotalIronProduction());
		map.put("oil", this.getTotalOilProduction());
		map.put("steel", this.getTotalSteelProduction());
		map.put("nitrogen", this.getTotalNitrogenProduction());
		map.put("research", this.getTotalResearchProduction());
		map.put("weapons", this.getTotalWeaponsProduction());
		return map;
	}

}
