package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.dao.*;
import com.watersfall.clocgame.model.Region;
import com.watersfall.clocgame.model.TextKey;
import com.watersfall.clocgame.model.Updatable;
import com.watersfall.clocgame.model.decisions.Decision;
import com.watersfall.clocgame.model.military.Bomber;
import com.watersfall.clocgame.model.military.Equipment;
import com.watersfall.clocgame.model.military.Fighter;
import com.watersfall.clocgame.model.military.ReconPlane;
import com.watersfall.clocgame.model.policies.Policy;
import com.watersfall.clocgame.model.technology.Technologies;
import com.watersfall.clocgame.model.technology.Technology;
import com.watersfall.clocgame.model.technology.technologies.single.doctrine.*;
import com.watersfall.clocgame.model.technology.technologies.single.economy.*;
import com.watersfall.clocgame.model.treaty.Treaty;
import com.watersfall.clocgame.model.war.War;
import com.watersfall.clocgame.text.Responses;
import com.watersfall.clocgame.util.Util;
import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class Nation extends Updatable
{
	private @Getter int id;
	private @Getter @Setter NationCosmetic cosmetic;
	private @Getter @Setter NationDomestic domestic;
	private @Getter @Setter NationEconomy economy;
	private @Getter @Setter NationForeign foreign;
	private @Getter @Setter NationMilitary military;
	private @Getter @Setter TreatyPermissions treatyPermissions;
	private @Getter @Setter HashMap<Integer, City> cities;
	private @Getter @Setter NationArmy army;
	private @Getter @Setter NationPolicy policy;
	private @Getter @Setter NationTech tech;
	private @Getter @Setter ArrayList<Integer> invites;
	private @Getter @Setter int newsCount;
	private @Getter @Setter boolean anyUnreadNews;
	private @Getter @Setter int eventCount;
	private @Getter @Setter ArrayList<Events> events;
	private @Getter @Setter War defensive;
	private @Getter @Setter War offensive;
	private @Getter @Setter Treaty treaty;
	private @Getter @Setter Connection conn;
	private @Getter @Setter boolean allowWriteAccess;
	private @Getter @Setter long freeFactories;
	private @Getter @Setter long lastSeen;
	private @Getter @Setter ArrayList<Modifier> modifiers;
	private LinkedHashMap<TextKey, Double> coalProduction = null;
	private LinkedHashMap<TextKey, Double> ironProduction = null;
	private LinkedHashMap<TextKey, Double> oilProduction = null;
	private LinkedHashMap<TextKey, Double> steelProduction = null;
	private LinkedHashMap<TextKey, Double> nitrogenProduction = null;
	private LinkedHashMap<TextKey, Double> researchProduction = null;
	private @Getter @Setter LinkedHashMap<Integer, Production> production;
	private LinkedHashMap<String, LinkedHashMap<TextKey, Double>> allProductions = null;
	private long landUsage = -1;
	private HashMap<String, Double> totalProductionCosts = null;

	public Nation(int id)
	{
		super("cloc_login", id);
		this.id = id;
	}

	/**
	 * Attempts to join a treaty in the standard way:
	 * by checking if this nation has an invite and letting them in if they do
	 *
	 * @param id      The id of the Treaty to join
	 * @throws SQLException If a database error occurs
	 */
	public String joinTreaty(Integer id) throws SQLException
	{
		return joinTreaty(id, false);
	}

	/**
	 * Attempts to join a treaty in the standard way:
	 * by checking if this nation has an invite and letting them in if they do
	 *
	 * @param id      The id of the Treaty to join
	 * @param founder Whether this Nation should be roled as founder of the treaty or not
	 * @throws SQLException If a database error occurs
	 */
	public String joinTreaty(Integer id, boolean founder) throws SQLException
	{
		if(this.treaty != null)
		{
			this.leaveTreaty();
		}
		if(founder || this.invites.contains(id))
		{
			new InviteDao(conn, true).deleteInvite(this.id, id);
			new TreatyDao(conn, true).joinTreaty(this.id, id, founder);
			return Responses.inviteAccepted();
		}
		else
		{
			return Responses.noInvite();
		}
	}

	/**
	 * Leaves the current treaty
	 *
	 * @throws SQLException If a database error occurs
	 */
	public String leaveTreaty() throws SQLException
	{
		if(this.treaty != null)
		{
			new TreatyDao(conn, true).leaveTreaty(this.id, this.treaty.getId());
			return Responses.resigned();
		}
		else
		{
			return Responses.notYourTreaty();
		}
	}

	/**
	 * Checks whether this nation can declare war on the nation passed in, returning null if it can
	 * If it cannot, returns the reason why
	 *
	 * @param nation The Nation to check against
	 * @return null if the nation can declare war on the other, the reason why it cannot otherwise
	 */
	public String canDeclareWar(Nation nation)
	{
		if(this.offensive != null)
		{
			return Responses.cannotWar("alreadyAtWar");
		}
		else if(nation.defensive != null)
		{
			return Responses.cannotWar("alreadyAtWar2");
		}
		else if(this.isAtWarWith(nation))
		{
			return Responses.cannotWar("alreadyAtWar3");
		}
		else if(!Region.borders(nation.getForeign().getRegion(), this.getForeign().getRegion()))
		{
			return Responses.cannotWar("noBorder");
		}
		else if(nation.getMilitary().getWarProtection() > 0)
		{
			if(nation.getForeign().getAlignment() == 0)
			{
				return Responses.cannotWar("neutralProtection");
			}
			else
			{
				return (nation.getForeign().getAlignment() == -1) ? Responses.cannotWar("germanProtection") : Responses.cannotWar("frenchProtection");
			}
		}
		else if(this.military.getWarProtection() > 0)
		{
			if(this.getForeign().getAlignment() == 0)
			{
				return Responses.cannotWar("youNeutralProtection");
			}
			else
			{
				return (this.getForeign().getAlignment() == -1) ? Responses.cannotWar("youGermanProtection") : Responses.cannotWar("youFrenchProtection");
			}
		}
		else
		{
			return null;
		}
	}

	public void damagePopulation(long amount)
	{
		for(Integer integer : cities.keySet())
		{
			City city = cities.get(integer);
			city.setPopulation(city.getPopulation() - (long)((double)city.getPopulation() / (double)this.getTotalPopulation()) * amount);
		}
	}

	/**
	 * Checks if this nation is at war with another nation
	 *
	 * @param nation The nation to check
	 * @return True if they are at war, false if they are not
	 */
	public boolean isAtWarWith(Nation nation)
	{
		return (this.defensive != null && this.defensive.getAttacker().getId() == nation.getId())
				|| (this.offensive != null && this.offensive.getDefender().getId() == nation.getId());
	}

	/**
	 * Checks if this nation is in any war
	 *
	 * @return True if the nation is at war, false otherwise
	 */
	public boolean isAtWar()
	{
		return defensive != null || offensive != null;
	}

	/**
	 * Declares war on the specified Nation
	 *
	 * @param nation The Nation to declare war on
	 * @throws SQLException If a database error occurs
	 */
	public void declareWar(Nation nation) throws SQLException
	{
		new WarDao(conn, true).createWar(this, nation);
	}

	public String sendPeace(Nation receiver) throws SQLException
	{
		if(this.isAtWarWith(receiver))
		{
			WarDao dao = new WarDao(this.conn, true);
			NewsDao newsDao = new NewsDao(this.conn, true);
			if(this.offensive != null && this.offensive.getDefender().getId() == receiver.getId())
			{
				if(this.offensive.getPeace() == this.getId())
				{
					return Responses.peaceAlreadySent();
				}
				else if(this.offensive.getPeace() == receiver.getId())
				{
					String message = News.createMessage(News.ID_PEACE_ACCEPTED, this.getNationUrl());
					newsDao.createNews(this.getId(), receiver.getId(), message);
					dao.endWar(this.offensive);
					return Responses.peaceAccepted();
				}
				else
				{
					String message = News.createMessage(News.ID_SEND_PEACE, this.getNationUrl());
					newsDao.createNews(this.getId(), receiver.getId(), message);
					dao.offerPeace(this.id, this, receiver);
					return Responses.peaceSent();
				}
			}
			else
			{
				if(this.defensive.getPeace() == this.getId())
				{
					return Responses.peaceAlreadySent();
				}
				else if(this.defensive.getPeace() == receiver.getId())
				{
					String message = News.createMessage(News.ID_PEACE_ACCEPTED, this.getNationUrl());
					newsDao.createNews(this.getId(), receiver.getId(), message);
					dao.endWar(this.defensive);
					return Responses.peaceAccepted();
				}
				else
				{
					String message = News.createMessage(News.ID_SEND_PEACE, this.getNationUrl());
					newsDao.createNews(this.getId(), receiver.getId(), message);
					dao.offerPeace(this.id, receiver, this);
					return Responses.peaceSent();
				}
			}
		}
		else
		{
			return Responses.noWar();
		}
	}

	/**
	 * Calculates the free land available in this Nation
	 *
	 * @return The free land available
	 */
	public long getFreeLand()
	{
		return this.domestic.getLand() - this.getTotalLandUsage();
	}

	/**
	 * Calculates the total land usage of this Nation
	 *
	 * @return The total land usage
	 */
	public long getTotalLandUsage()
	{
		if(landUsage == -1)
		{
			long total = 0;
			for(City city : cities.values())
			{
				for(Long longboi : city.getLandUsage().values())
				{
					total += longboi;
				}
			}
			this.landUsage = total;
		}
		return landUsage;
	}

	public HashMap<String, Double> getTotalProductionCosts()
	{
		if(totalProductionCosts == null)
		{
			HashMap<String, Double> map = new HashMap<>();
			for(Production production1 : production.values())
			{
				for(HashMap.Entry<String, Double> entry : production1.getRequiredResources().entrySet())
				{
					if(map.containsKey(entry.getKey()))
					{
						map.compute(entry.getKey(), (key2, value) -> value = value + entry.getValue());
					}
					else
					{
						map.put(entry.getKey(), entry.getValue());
					}
				}
			}
			map.forEach((key, value) -> map.compute(key, (k, v) -> v = v * -1.0));
			totalProductionCosts = map;
		}
		return totalProductionCosts;
	}

	/**
	 * Gets the total amount of military factories in all cities in this nation
	 *
	 * @return The military factory count
	 */
	public long getTotalMilitaryFactories()
	{
		long total = 0;
		for(City city : cities.values())
		{
			total += city.getIndustryMilitary();
		}
		return total;
	}

	/**
	 * Gets the total amount of factories in all cities in this nation
	 *
	 * @return The factory count
	 */
	public long getTotalFactories()
	{
		long total = 0;
		for(City city : cities.values())
		{
			total += city.getIndustryMilitary() + city.getIndustryNitrogen() + city.getIndustryCivilian();
		}
		return total;
	}

	public long getTotalPopulation()
	{
		long total = 0;
		for(City city : cities.values())
		{
			total += city.getPopulation();
		}
		return total;
	}

	/**
	 * Calculates the total manpower a nation has
	 *
	 * @return The total manpower of the nation
	 */
	public long getTotalManpower()
	{
		long lostManpower = domestic.getManpowerLost();
		long manpower = this.getTotalPopulation();
		switch(policy.getManpower())
		{
			case DISARMED_MANPOWER:
				manpower *= 0.05;
				break;
			case VOLUNTEER_MANPOWER:
				manpower *= 0.10;
				break;
			case RECRUITMENT_MANPOWER:
				manpower *= 0.20;
				break;
			case MANDATORY_MANPOWER:
				manpower *= 0.30;
				break;
			case SCRAPING_THE_BARREL_MANPOWER:
				manpower *= 0.45;
				break;
		}
		return manpower - lostManpower;
	}

	/**
	 * Calculates out the total used manpower buy this nation, stores in a HashMap with keys:
	 * <ul>
	 *     <li>Navy</li>
	 *     <li>Airforce</li>
	 *     <li>Army</li>
	 * </ul>
	 *
	 * @return A HashMap containing manpower usage
	 */
	public LinkedHashMap<TextKey, Long> getUsedManpower()
	{
		LinkedHashMap<TextKey, Long> map = new LinkedHashMap<>();
		long navy = this.military.getBattleships() +
				this.military.getCruisers() +
				this.military.getPreBattleships() +
				this.military.getCruisers() +
				this.military.getDestroyers() +
				this.military.getSubmarines();
		navy *= 500;
		long airforce = this.military.getBiplaneFighters() +
				this.military.getReconPlanes() +
				this.military.getReconBalloons() +
				this.military.getTriplaneFighters() +
				this.military.getMonoplaneFighters() +
				this.military.getBombers() +
				this.military.getZeppelins();
		airforce *= 50;
		long army = this.army.getSize();
		army *= 1000;
		map.put(TextKey.Manpower.NAVY, -navy);
		map.put(TextKey.Manpower.AIRFORCE, -airforce);
		map.put(TextKey.Manpower.ARMY, -army);
		map.put(TextKey.Manpower.NET, navy + airforce + army);
		return map;
	}

	/**
	 * Calculates the amount of free manpower this nation has available
	 *
	 * @return The amount of free manpower
	 */
	public long getFreeManpower()
	{
		return this.getTotalManpower() - this.getUsedManpower().get(TextKey.Manpower.NET);
	}

	public LinkedHashMap<String, Double> getAllResources()
	{
		LinkedHashMap<String, Double> map = new LinkedHashMap<>();
		map.put("Budget", this.economy.getBudget());
		map.put("Food", this.economy.getFood());
		map.put("Coal", this.economy.getCoal());
		map.put("Iron", this.economy.getIron());
		map.put("Oil", this.economy.getOil());
		map.put("Steel", this.economy.getSteel());
		map.put("Nitrogen", this.economy.getNitrogen());
		map.put("Research", this.economy.getResearch());
		return map;
	}

	private static void extractionEconBoosts(LinkedHashMap<TextKey, Double> map, Policy policy)
	{
		double bonus;
		if(policy == Policy.WAR_ECONOMY || policy == Policy.CIVILIAN_ECONOMY)
		{
			bonus = -0.1;
		}
		else if(policy == Policy.AGRARIAN_ECONOMY)
		{
			bonus = -0.05;
		}
		else if(policy == Policy.INDUSTRY_ECONOMY)
		{
			bonus = -0.15;
		}
		else
		{
			bonus = 0.1;
		}
		computeBonus(map, TextKey.Resource.ECONOMY_TYPE, bonus);
	}

	private static void factoryEconBoosts(LinkedHashMap<TextKey, Double> map, Policy policy)
	{
		double bonus;
		if(policy == Policy.WAR_ECONOMY || policy == Policy.CIVILIAN_ECONOMY || policy == Policy.AGRARIAN_ECONOMY)
		{
			bonus = -0.1;
		}
		else if(policy == Policy.INDUSTRY_ECONOMY)
		{
			bonus = 0.1;
		}
		else
		{
			bonus = -0.15;
		}
		computeBonus(map, TextKey.Resource.ECONOMY_TYPE, bonus);
	}

	public void doStabilityResourceEffect(LinkedHashMap<TextKey, Double> map)
	{
		double stabilityEffect = this.getDomestic().getStability() - 50;
		if(stabilityEffect < 0)
		{
			stabilityEffect = stabilityEffect / (3.0 + 1.0/3.0);
		}
		else
		{
			stabilityEffect = stabilityEffect / 5.0;
		}
		final double stabilityIncrease = (stabilityEffect / 100.0);
		computeBonus(map, TextKey.Resource.STABILITY, stabilityIncrease);
	}

	private static void computeBonus(LinkedHashMap<TextKey, Double> map, TextKey key, double bonus)
	{
		map.put(key, map.get(TextKey.Resource.TOTAL_GAIN) * bonus);
		map.compute(TextKey.Resource.NET, (k, v) -> v += (map.get(TextKey.Resource.TOTAL_GAIN) * bonus));
		if(bonus > 0)
		{
			map.compute(TextKey.Resource.TOTAL_GAIN, (k, v) -> v += v * bonus);
		}
	}


	/**
	 * Calculates the total coal production of all cities
	 * Return value cached to save performance on multiple calls
	 * LinkedHashMap contains the following keys:
	 * <ul>
	 *     <li>total (used exclusively for turn change)</li>
	 *     <li>mines</li>
	 *     <li>infrastructure</li>
	 *     <li>civilian factory demands</li>
	 *     <li>military factory demands</li>
	 *     <li>nitrogen plant demands</li>
	 *     <li>net</li>
	 * </ul>
	 *
	 * @return A LinkedHashMap containing the total coal production of this Nation
	 */
	public LinkedHashMap<TextKey, Double> getTotalCoalProduction()
	{
		if(coalProduction == null)
		{
			LinkedHashMap<TextKey, Double> map = new LinkedHashMap<>();
			for(City city : cities.values())
			{
				LinkedHashMap<TextKey, Double> cityMap = city.getCoalProduction();
				for(HashMap.Entry<TextKey, Double> entry : cityMap.entrySet())
				{
					map.putIfAbsent(entry.getKey(), 0e0);
					map.compute(entry.getKey(), (k, v) -> v += entry.getValue());
				}
			}
			extractionEconBoosts(map, this.getPolicy().getEconomy());
			doStabilityResourceEffect(map);
			coalProduction = map;
		}
		return coalProduction;
	}

	/**
	 * Calculates the total iron production of all cities
	 * Return value cached to save performance on multiple calls
	 * HashMap contains the following keys:
	 * <ul>
	 *     <li>total (used exclusively for turn change)</li>
	 *     <li>mines</li>
	 *     <li>infrastructure</li>
	 *     <li>civilian factory demands</li>
	 *     <li>military factory demands</li>
	 *     <li>nitrogen plant demands</li>
	 *     <li>net</li>
	 * </ul>
	 *
	 * @return A HashMap containing the total iron production of this Nation
	 */
	public LinkedHashMap<TextKey, Double> getTotalIronProduction()
	{
		if(ironProduction == null)
		{
			LinkedHashMap<TextKey, Double> map = new LinkedHashMap<>();
			for(City city : cities.values())
			{
				LinkedHashMap<TextKey, Double> cityMap = city.getIronProduction();
				for(HashMap.Entry<TextKey, Double> entry : cityMap.entrySet())
				{
					map.putIfAbsent(entry.getKey(), 0e0);
					map.compute(entry.getKey(), (k, v) -> v += entry.getValue());
				}
			}
			extractionEconBoosts(map, this.getPolicy().getEconomy());
			doStabilityResourceEffect(map);
			ironProduction = map;
		}
		return ironProduction;
	}

	/**
	 * Calculates the total oil production of all cities
	 * Return value cached to save performance on multiple calls
	 * HashMap contains the following keys:
	 * <ul>
	 *     <li>total (used exclusively for turn change)</li>
	 *     <li>wells</li>
	 *     <li>infrastructure</li>
	 *     <li>civilian factory demands</li>
	 *     <li>military factory demands</li>
	 *     <li>nitrogen plant demands</li>
	 *     <li>net</li>
	 * </ul>
	 *
	 * @return A LinkedHashMap containing the total iron production of this Nation
	 */
	public LinkedHashMap<TextKey, Double> getTotalOilProduction()
	{
		if(oilProduction == null)
		{
			LinkedHashMap<TextKey, Double> map = new LinkedHashMap<>();
			for(City city : cities.values())
			{
				LinkedHashMap<TextKey, Double> cityMap = city.getOilProduction();
				for(HashMap.Entry<TextKey, Double> entry : cityMap.entrySet())
				{
					map.putIfAbsent(entry.getKey(), 0e0);
					map.compute(entry.getKey(), (k, v) -> v += entry.getValue());
				}
			}
			extractionEconBoosts(map, this.getPolicy().getEconomy());
			doStabilityResourceEffect(map);
			if(getTotalProductionCosts().get("oil") != null)
			{
				map.put(TextKey.Resource.MILITARY_FACTORY_UPKEEP, getTotalProductionCosts().get("oil"));
				map.compute(TextKey.Resource.NET, (k, v) -> v = v + getTotalProductionCosts().get("oil"));
			}
			oilProduction = map;
		}
		return oilProduction;
	}

	/**
	 * Calculates the total steel production of all cities
	 * Return value cached to save performance on multiple calls
	 * LinkedHashMap contains the following keys:
	 * <ul>
	 *     <li>total (used exclusively for turn change)</li>
	 *     <li>factories</li>
	 *     <li>infrastructure</li>
	 *     <li>net</li>
	 * </ul>
	 *
	 * @return A LinkedHashMap containing the total steel production of this Nation
	 */
	public LinkedHashMap<TextKey, Double> getTotalSteelProduction()
	{
		if(steelProduction == null)
		{
			LinkedHashMap<TextKey, Double> map = new LinkedHashMap<>();
			for(City city : cities.values())
			{
				LinkedHashMap<TextKey, Double> cityMap = city.getSteelProduction();
				for(HashMap.Entry<TextKey, Double> entry : cityMap.entrySet())
				{
					map.putIfAbsent(entry.getKey(), 0e0);
					map.compute(entry.getKey(), (k, v) -> v += entry.getValue());
				}
			}
			factoryEconBoosts(map, this.getPolicy().getEconomy());
			doStabilityResourceEffect(map);
			if(getTotalProductionCosts().get("steel") != null)
			{
				map.put(TextKey.Resource.MILITARY_FACTORY_UPKEEP, getTotalProductionCosts().get("steel"));
				map.compute(TextKey.Resource.NET, (k, v) -> v = v + getTotalProductionCosts().get("steel"));
			}
			if(this.hasTech(Technologies.FARMING_MACHINES) || this.hasTech(Technologies.ADVANCED_FARMING_MACHINES))
			{
				double amount = -this.domestic.getLand() / TechnologyFarmingMachines.LAND_PER_STEEL;
				if(this.getPolicy().getFarmingSubsidies() == Policy.NO_SUBSIDIES_FARMING)
				{
					amount *= 0.25;
				}
				else if(this.getPolicy().getFarmingSubsidies() == Policy.REDUCED_SUBSIDIES_FARMING)
				{
					amount *= 0.75;
				}
				else if(this.getPolicy().getFarmingSubsidies() == Policy.SUBSTANTIAL_SUBSIDIES_FARMING)
				{
					amount *= 1.5;
				}
				map.put(TextKey.Resource.FARMING_DEMANDS, amount);
				double finalAmount = amount;
				map.compute(TextKey.Resource.NET, (k, v) -> v = v + finalAmount);
			}
			steelProduction = map;
		}
		return steelProduction;
	}

	/**
	 * Calculates the total nitrogen production of all cities
	 * Return value cached to save performance on multiple calls
	 * LinkedHashMap contains the following keys:
	 * <ul>
	 *     <li>total (used exclusively for turn change)</li>
	 *     <li>factories</li>
	 *     <li>infrastructure</li>
	 *     <li>net</li>
	 * </ul>
	 *
	 * @return A LinkedHashMap containing the total nitrogen production of this Nation
	 */
	public LinkedHashMap<TextKey, Double> getTotalNitrogenProduction()
	{
		if(nitrogenProduction == null)
		{
			LinkedHashMap<TextKey, Double> map = new LinkedHashMap<>();
			for(City city : cities.values())
			{
				LinkedHashMap<TextKey, Double> cityMap = city.getNitrogenProduction();
				for(HashMap.Entry<TextKey, Double> entry : cityMap.entrySet())
				{
					map.putIfAbsent(entry.getKey(), 0e0);
					map.compute(entry.getKey(), (k, v) -> v += entry.getValue());
				}
			}
			factoryEconBoosts(map, this.getPolicy().getEconomy());
			doStabilityResourceEffect(map);
			if(getTotalProductionCosts().get("nitrogen") != null)
			{
				map.put(TextKey.Resource.MILITARY_FACTORY_UPKEEP, getTotalProductionCosts().get("nitrogen"));
				map.compute(TextKey.Resource.NET, (k, v) -> v = v + getTotalProductionCosts().get("nitrogen"));
			}
			if(this.hasTech(Technologies.ARTIFICIAL_FERTILIZER) || this.hasTech(Technologies.ADVANCED_ARTIFICIAL_FERTILIZER))
			{
				double amount = -this.domestic.getLand() / TechnologyArtificialFertilizer.LAND_PER_NITROGEN;
				if(this.getPolicy().getFarmingSubsidies() == Policy.NO_SUBSIDIES_FARMING)
				{
					amount *= 0.25;
				}
				else if(this.getPolicy().getFarmingSubsidies() == Policy.REDUCED_SUBSIDIES_FARMING)
				{
					amount *= 0.75;
				}
				else if(this.getPolicy().getFarmingSubsidies() == Policy.SUBSTANTIAL_SUBSIDIES_FARMING)
				{
					amount *= 1.5;
				}
				map.put(TextKey.Resource.FARMING_DEMANDS, amount);
				double finalAmount = amount;
				map.compute(TextKey.Resource.NET, (k, v) -> v = v + finalAmount);
			}
			nitrogenProduction = map;
		}
		return nitrogenProduction;
	}

	/**
	 * Calculates the total research production of all cities
	 * Return value cached to save performance on multiple calls
	 * LinkedHashMap contains the following keys:
	 * <ul>
	 *     <li>total (used exclusively for turn change)</li>
	 *     <li>factories</li>
	 *     <li>infrastructure</li>
	 *     <li>net</li>
	 * </ul>
	 *
	 * @return A LinkedHashMap containing the total research production of this Nation
	 */
	public LinkedHashMap<TextKey, Double> getTotalResearchProduction()
	{
		if(researchProduction == null)
		{
			LinkedHashMap<TextKey, Double> map = new LinkedHashMap<>();
			for(City city : cities.values())
			{
				LinkedHashMap<TextKey, Double> cityMap = city.getResearchProduction();
				for(HashMap.Entry<TextKey, Double> entry : cityMap.entrySet())
				{
					map.putIfAbsent(entry.getKey(), 0e0);
					map.compute(entry.getKey(), (k, v) -> v += entry.getValue());
				}
			}
			doStabilityResourceEffect(map);
			researchProduction = map;
		}
		return researchProduction;
	}

	/**
	 * Calculates out the food production of this Nation, stored in a HashMap with keys:
	 * <ul>
	 *     <li>farming</li>
	 *     <li>costs</li>
	 *     <li>net</li>
	 * </ul>
	 *
	 * @return A HashMap containing food production
	 */
	public LinkedHashMap<TextKey, Double> getFoodProduction()
	{
		LinkedHashMap<TextKey, Double> map = new LinkedHashMap<>();
		double farming = (this.getFreeLand() / 250.0) * this.getBaseFoodProduction().get(TextKey.Farming.NET);
		if(farming < 0)
			farming = 0;
		double tech = 0;
		if(this.hasTech(Technologies.ADVANCED_ARTIFICIAL_FERTILIZER) && this.getTotalNitrogenProduction().get(TextKey.Resource.NET) + this.getEconomy().getNitrogen() >= 0)
		{
			tech = farming * TechnologyAdvancedArtificialFertilizer.FOOD_GAIN;
		}
		else if(this.hasTech(Technologies.ARTIFICIAL_FERTILIZER) && this.getTotalNitrogenProduction().get(TextKey.Resource.NET) + this.getEconomy().getNitrogen() >= 0)
		{
			tech = farming * TechnologyArtificialFertilizer.FOOD_GAIN;
		}
		else if(this.hasTech(Technologies.BASIC_ARTIFICIAL_FERTILIZER))
		{
			tech = farming * TechnologyBasicArtificialFertilizer.FOOD_GAIN;
		}
		if(this.hasTech(Technologies.ADVANCED_FARMING_MACHINES) && this.getTotalSteelProduction().get(TextKey.Resource.NET) + this.getEconomy().getSteel() > 0)
		{
			tech += farming * TechnologyAdvancedFarmingMachines.FOOD_GAIN;
		}
		else if(this.hasTech(Technologies.FARMING_MACHINES) && this.getTotalSteelProduction().get(TextKey.Resource.NET) + this.getEconomy().getSteel() > 0)
		{
			tech += farming * TechnologyFarmingMachines.FOOD_GAIN;
		}
		if(this.getPolicy().getFarmingSubsidies() == Policy.NO_SUBSIDIES_FARMING)
		{
			tech *= 0.5;
		}
		else if(this.getPolicy().getFarmingSubsidies() == Policy.REDUCED_SUBSIDIES_FARMING)
		{
			tech *= 0.85;
		}
		else if(this.getPolicy().getFarmingSubsidies() == Policy.SUBSTANTIAL_SUBSIDIES_FARMING)
		{
			tech *= 1.5;
		}
		double consumption = -this.getTotalPopulation() / 2000.0;
		double economy = 0.0;
		double food = 0.0;
		double total = farming;
		double net = farming + consumption;
		if(this.policy.getEconomy() == Policy.AGRARIAN_ECONOMY)
		{
			economy = farming * 0.15;
			total += economy;
		}
		else if(this.policy.getEconomy() == Policy.WAR_ECONOMY)
		{
			economy = -farming * 0.1;
		}
		if(this.policy.getFood() == Policy.FREE_FOOD)
		{
			food = consumption * 0.35;
		}
		else if(this.policy.getFood() == Policy.RATIONING_FOOD)
		{
			food = -consumption * 0.35;
		}
		net += food + economy + tech;
		map.put(TextKey.Resource.FARMING, farming);
		map.put(TextKey.Resource.TECHNOLOGY, tech);
		map.put(TextKey.Resource.CONSUMPTION, consumption);
		map.put(TextKey.Resource.ECONOMY_TYPE, economy);
		map.put(TextKey.Resource.FOOD_POLICY, food);
		map.put(TextKey.Resource.NET, net);
		map.put(TextKey.Resource.TOTAL_GAIN, total);
		doStabilityResourceEffect(map);
		return map;
	}

	/**
	 * Calculates the total production of all types for this nation
	 * The production is returned as a HashMap of HashMaps, with the outer HashMap containing the keys:
	 * <ul>
	 *     <li>budget</li>
	 *     <li>food</li>
	 *     <li>coal</li>
	 *     <li>iron</li>
	 *     <li>oil</li>
	 *     <li>steel</li>
	 *     <li>nitrogen</li>
	 *     <li>research</li>
	 * </ul>
	 * Each of the inner HashMaps has the same keys as their standard getter,
	 * typically containing:
	 * <ul>
	 *     <li>total</li>
	 *     <li>type (i.e. mine, factory)</li>
	 *     <li>infrastructure</li>
	 *     <li>net</li>
	 * </ul>
	 * And if it's a type with upkeep costs, it will additionally have the various costs and upkeeps
	 * And if it's a type with upkeep costs, it will additionally have the various costs and upkeeps
	 * <ul>
	 *     <li>costs</li>
	 *     <li>upkeep</li>
	 * </ul>
	 *
	 * @return A HashMap containing the total weapons production of this Nation
	 */
	public LinkedHashMap<String, LinkedHashMap<TextKey, Double>> getAllTotalProductions()
	{
		if(allProductions == null)
		{
			LinkedHashMap<String, LinkedHashMap<TextKey, Double>> map = new LinkedHashMap<>();
			LinkedHashMap<TextKey, Double> budget = new LinkedHashMap<>();
			budget.put(TextKey.Resource.GDP, this.getBudgetChange());
			map.put("Budget", budget);
			map.put("Food", this.getFoodProduction());
			map.put("Coal", this.getTotalCoalProduction());
			map.put("Iron", this.getTotalIronProduction());
			map.put("Oil", this.getTotalOilProduction());
			map.put("Steel", this.getTotalSteelProduction());
			map.put("Nitrogen", this.getTotalNitrogenProduction());
			map.put("Research", this.getTotalResearchProduction());
			allProductions = map;
		}
		return allProductions;
	}

	public LinkedHashMap<TextKey, Double> getBaseFoodProduction()
	{
		LinkedHashMap<TextKey, Double> map = new LinkedHashMap<>();
		double base = 1.0;
		map.put(TextKey.Farming.BASE, base);
		map.put(TextKey.Resource.TOTAL_GAIN, base);
		map.put(TextKey.Farming.NET, base);
		return map;
	}

	/**
	 * Gets the total equipment of the army
	 * @return The total equipment
	 */
	public long getTotalInfantryEquipment()
	{
		long total = 0;
		for(Equipment equipment : Equipment.getInfantryEquipment())
		{
			total += this.getEquipment(equipment);
		}
		return total;
	}

	public long getTotalArmor()
	{
		long total = 0;
		for(Equipment equipment : Equipment.getArmor())
		{
			total += this.getEquipment(equipment);
		}
		return total;
	}

	public long getTotalArtillery()
	{
		long total = 0;
		for(Equipment equipment : Equipment.getArtillery())
		{
			total += this.getEquipment(equipment);
		}
		return total;
	}

	public LinkedHashMap<String, Integer> getEquipment()
	{
		LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
		map.put("Muskets", this.army.getMusket());
		map.put("Rifled Muskets", this.army.getRifledMusket());
		map.put("Single Shot Rifles", this.army.getSingleShot());
		map.put("Needle Nose Rifles", this.army.getNeedleNose());
		map.put("Manually Loaded Bolt Action Rifles", this.army.getBoltActionManual());
		map.put("Clip Loaded Bolt Action Rifles", this.army.getBoltActionClip());
		map.put("Straight Pull Rifles", this.army.getStraightPull());
		map.put("Semi-Automatic Rifles", this.army.getSemiAuto());
		map.put("Machine Guns", this.army.getMachineGun());
		return map;
	}

	public int getFighterCount()
	{
		int total = 0;
		for(Fighter fighter : Fighter.values())
		{
			total += this.getFighter(fighter);
		}
		return total;
	}

	public int getBomberCount()
	{
		int total = 0;
		for(Bomber bomber : Bomber.values())
		{
			total += this.getBomber(bomber);
		}
		return total;
	}

	public int getReconCount()
	{
		int total = 0;
		for(ReconPlane plane : ReconPlane.values())
		{
			total += this.getReconPlane(plane);
		}
		return total;
	}

	public int getEquipment(Equipment equipment)
	{
		switch(equipment)
		{
			case MUSKET:
				return this.getArmy().getMusket();
			case RIFLED_MUSKET:
				return this.getArmy().getRifledMusket();
			case SINGLE_SHOT_RIFLE:
				return this.getArmy().getSingleShot();
			case NEEDLE_NOSE:
				return this.getArmy().getNeedleNose();
			case BOLT_ACTION_MANUAL:
				return this.getArmy().getBoltActionManual();
			case BOLT_ACTION_CLIP:
				return this.getArmy().getBoltActionClip();
			case STRAIGHT_PULL_RIFLE:
				return this.getArmy().getStraightPull();
			case SEMI_AUTOMATIC:
				return this.getArmy().getSemiAuto();
			case MACHINE_GUN:
				return this.getArmy().getMachineGun();
			case ARTILLERY:
				return this.getArmy().getArtillery();
			case TANK:
				return this.getArmy().getTank();
			default:
				return 0;

		}
	}

	public int getFighter(Fighter fighter)
	{
		switch(fighter)
		{
			case BIPLANE_FIGHTER:
				return this.getMilitary().getBiplaneFighters();
			case TRIPLANE_FIGHTER:
				return this.getMilitary().getTriplaneFighters();
			case MONOPLANE_FIGHTER:
				return this.getMilitary().getMonoplaneFighters();
			default:
				return 0;
		}
	}

	public void setFighter(Fighter fighter, int value)
	{
		switch(fighter)
		{
			case BIPLANE_FIGHTER:
				this.getMilitary().setBiplaneFighters(value);
				break;
			case TRIPLANE_FIGHTER:
				this.getMilitary().setTriplaneFighters(value);
				break;
			case MONOPLANE_FIGHTER:
				this.getMilitary().setMonoplaneFighters(value);
				break;
		}
	}

	public int getBomber(Bomber bomber)
	{
		switch(bomber)
		{
			case ZEPPELIN_BOMBER:
				return this.getMilitary().getZeppelins();
			case BOMBER:
				return this.getMilitary().getBombers();
			default:
				return 0;
		}
	}

	public void setBomber(Bomber bomber, int value)
	{
		switch(bomber)
		{
			case ZEPPELIN_BOMBER:
				this.getMilitary().setZeppelins(value);
				break;
			case BOMBER:
				this.getMilitary().setBombers(value);
				break;
		}
	}

	public int getReconPlane(ReconPlane plane)
	{
		switch(plane)
		{
			case RECON_BALLOON:
				return this.getMilitary().getReconBalloons();
			case RECON_PLANE:
				return this.getMilitary().getReconPlanes();
			default:
				return 0;
		}
	}

	/**
	 * Calculates the power of an army based on it's army.getSize(), technology level, army.getTraining(), and artillery
	 * @return The army's power
	 */
	public double getPower()
	{
		double power = 0e0;
		long requiredEquipment = (long)army.getSize() * 1000L;
		for(Equipment equipment : Equipment.getInfantryEquipment())
		{
			if(requiredEquipment > 0)
			{
				if(this.getEquipment(equipment) > 0)
				{
					if(this.getEquipment(equipment) > requiredEquipment)
					{
						power += requiredEquipment * equipment.getPower();
						requiredEquipment = 0;
					}
					else
					{
						power += this.getEquipment(equipment) * equipment.getPower();
						requiredEquipment -= this.getEquipment(equipment);
					}
				}
			}
		}
		//Sticks and stones are better than nothing
		power += requiredEquipment * 0.5;
		power *= java.lang.Math.sqrt(army.getTraining() + 1);
		return Math.sqrt(power);
	}

	/**
	 * Calculates the ability of this nations army to break through defenses
	 * and generally attack better
	 * <br>
	 * Represented as a percentage, at 0% ignores no defender bonuses, at 100% ignores all defender bonuses
	 * @return The army's breakthrough
	 */
	public double getBreakthrough()
	{
		int max = this.getArmy().getSize() * 5;
		long currentTanks = this.getTotalArmor();
		if(currentTanks > max)
		{
			currentTanks = max;
		}
		double ratio = (double)currentTanks / (double)max;
		long currentArtillery = this.getTotalArtillery();
		if(currentArtillery > max)
		{
			currentArtillery = max;
		}
		double artillery = ((double)currentArtillery / (double)max);
		artillery /= 2;
		double recon = 1;
		int maxRecon = max / 5;
		int currentRecon = this.getReconCount();
		if(currentRecon > maxRecon)
		{
			currentRecon = maxRecon;
		}
		List<ReconPlane> list = Arrays.asList(ReconPlane.values());
		Collections.reverse(list);
		for(ReconPlane plane : list)
		{
			if(this.getReconPlane(plane) > currentRecon)
			{
				recon += (currentRecon * plane.getPower());
				currentRecon = 0;
			}
			else
			{
				recon += (this.getReconPlane(plane) * plane.getPower());
				currentRecon -= this.getReconPlane(plane);
			}
		}
		recon = 1 + recon / maxRecon;
		artillery *= recon;
		ratio += artillery;
		return ratio;
	}

	/**
	 * Calculated the ability of this nations army to resist attacks
	 * and generally better defense
	 * <br>
 *     Represented as a percentage, 0% adds no defense bonus, 100% doubles defender strength
	 * @return The army's defense
	 */
	public double getDefense()
	{
		double ratio = this.getArmy().getFortification() / 10000.0;
		return 1 + ratio;
	}

	public int getMinimumFortificationLevel()
	{
		return 0;
	}

	public int getMaximumFortificationLevel()
	{
		return getMaximumFortificationLevelMap().get(TextKey.Fortification.NET);
	}

	public LinkedHashMap<TextKey, Integer> getMaximumFortificationLevelMap()
	{
		LinkedHashMap<TextKey, Integer> map = new LinkedHashMap<>();
		int tech = 2000;
		if(this.hasTech(Technologies.BASIC_TRENCHES))
			tech += TechnologyTrenches.BONUS * 100;
		if(this.hasTech(Technologies.BASIC_FORTIFICATIONS))
			tech += TechnologyFortifications.BONUS * 100;
		if(this.hasTech(Technologies.ADVANCED_TRENCHES))
			tech += TechnologyAdvancedTrenches.BONUS * 100;
		if(this.hasTech(Technologies.ADVANCED_FORTIFICATIONS))
			tech += TechnologyReinforcedConcrete.BONUS * 100;
		if(this.hasTech(Technologies.MOBILE_DEFENSE))
			tech += TechnologyMobileDefense.BONUS * 100;
		int policy = tech;
		switch(this.policy.getFortification())
		{
			case UNOCCUPIED_FORTIFICATION:
				policy *= 0.15;
				break;
			case MINIMAL_FUNDING_FORTIFICATION:
				policy *= 0.4;
				break;
			case PARTIAL_FUNDING_FORTIFICATION:
				policy *= 0.75;
				break;
		}
		policy = tech - policy;
		policy = -policy;
		int net = policy + tech;
		map.put(TextKey.Fortification.TECHNOLOGY, tech);
		map.put(TextKey.Fortification.POLICY, policy);
		map.put(TextKey.Fortification.NET, net);
		return map;
	}

	/**
	 * Calculates the ability of this nation to shoot down other planes
	 * @param attackingOtherAirforce True if calculating the power of this airforce to
	 *                               fight other airforces specifically
	 * @return The fighter power
	 */
	public double getFighterPower(boolean attackingOtherAirforce)
	{
		double power = 0;
		for(Fighter fighter : Fighter.values())
		{
			power += (this.getFighter(fighter) * fighter.getPower());
		}
		if(attackingOtherAirforce)
		{
			for(Bomber bomber : Bomber.values())
			{
				power += (this.getBomber(bomber) * bomber.getBombingPower() / 2);
			}
		}
		for(Bomber bomber : Bomber.values())
		{
			power += (this.getBomber(bomber) * bomber.getDefense());
		}
		return power / 10;
	}

	/**
	 * Calculates the ability of this nation when bombing targets
	 * @return The bomber power
	 */
	public double getBomberPower()
	{
		double power = 0;
		for(Bomber bomber : Bomber.values())
		{
			power += (this.getBomber(bomber) * bomber.getBombingPower());
		}
		return power / 10;
	}

	public long getTotalShipCount()
	{
		long power = 0;
		power += this.military.getSubmarines();
		power += this.military.getDestroyers();
		power += this.military.getCruisers();
		power += this.military.getPreBattleships();
		power += this.military.getBattleships();
		return power;
	}

	/**
	 * Calculates the general naval power of this country
	 * @return The naval power
	 */
	public double getNavalPower()
	{
		double power = getTotalShipCount();
		power /= 10;
		return power;
	}

	/**
	 * Calculates the amount of casualties this army takes attacking a specified defender
	 * @param ourPower the power of our army
	 * @param theirPower the power of the other army
	 * @return The casualties taken
	 */
	public int getCasualties(double ourPower, double theirPower)
	{
		double powerDiff = theirPower / ourPower;
		double armyHp = this.army.getSize() * 100.0;
		armyHp -= (powerDiff * theirPower);
		return (int)(this.army.getSize() - (armyHp / 100.0));
	}

	/**
	 * Returns the cash-cost of a decision<br>
	 * This contains most policy costs that would show up under decisions->category
	 * but does not include any that cost another resource/other resources
	 * @param decision The ID of the decision
	 * @return The cost of the policy
	 */
	public long getDecisionCost(Decision decision)
	{
		switch(decision)
		{
			case PROPAGANDA:
				return (long)(this.economy.getGdp() / 2L * (this.domestic.getApproval() / 100.0));
			case WAR_PROPAGANDA:
				return getDecisionCost(Decision.PROPAGANDA) / 2L;
			case LAND_CLEARANCE:
				return (long)(this.economy.getGdp() * 2L);
			case ALIGN_CENTRAL_POWERS:
			case ALIGN_ENTENTE:
			case ALIGN_NEUTRAL:
			case PARDON_CRIMINALS:
			case INCREASE_ARREST_QUOTAS:
				return 100;
			case TRAIN:
				return (long)this.army.getSize() * (long)this.army.getSize() * (long)this.army.getTraining() / 200L;
			case FORM_TREATY:
				return 500;
			case FORTIFY:
				return (long)(this.getMaximumFortificationLevel() / 100.0 * ((double)this.getArmy().getFortification() / (double)this.getMaximumFortificationLevel()));
			default:
				return 0;
		}
	}

	public String getDecisionCostDisplayString(Decision decision)
	{
		long cost = getDecisionCost(decision);
		if(cost == 0)
		{
			return "Free";
		}
		else
		{
			switch(decision)
			{
				case FORTIFY:
					return Util.formatNumber(cost) + " Steel";
				default:
					return "$" + Util.formatNumber(cost);
			}
		}
	}

	/**
	 * Checks whether the nation has researched a tech
	 * @param tech The String name of the tech
	 * @return True if it's researched, false otherwise
	 */
	public boolean hasTech(String tech)
	{
		return this.tech.getResearchedTechs().contains(Technologies.valueOf(tech));
	}

	/**
	 * Checks whether the nation has researched a tech
	 * @param tech The Technology
	 * @return True if it's researched, false otherwise
	 */
	public boolean hasTech(Technology tech)
	{
		return this.tech.getResearchedTechs().contains(tech.getTechnology());
	}

	/**
	 * Checks whether the nation has researched a tech
	 * @param tech The Technology
	 * @return True if it's researched, false otherwise
	 */
	public boolean hasTech(Technologies tech)
	{
		return this.tech.getResearchedTechs().contains(tech);
	}

	/**
	 * Returns the cost of posting declarations
	 * @return Declaration posting cost
	 */
	public int getDeclarationCost()
	{
		return 100;
	}

	/**
	 * Calculates how much this nation's budget increases by for the daily turn change (or 7 times a standard turn)
	 * @return The budget increase
	 */
	public double getBudgetChange()
	{
		return this.economy.getGdp() / 7;
	}

	/**
	 * Gets one of the nations productions by id
	 * @param id the id of the production
	 * @return The production
	 */
	public Production getProductionById(int id)
	{
		return production.get(id);
	}

	public String getNationUrl()
	{
		return "<a href=\"/nation/" + id + "\"><b>" + this.cosmetic.getNationName() + "</b></a>";
	}

	/**
	 * Runs the daily production tick
	 * @throws SQLException if a database error occurs
	 */
	public void processProduction() throws SQLException
	{
		new NationDao(conn, true).doNationProduction(this);
	}

	public LinkedHashMap<TextKey, Double> getFortificationChange()
	{
		LinkedHashMap<TextKey, Double> map = new LinkedHashMap<>();
		double base = Math.min(
				this.army.getFortification() + (0.1 * ((double)this.getMaximumFortificationLevel() / (this.army.getFortification() / 100.0))),
				this.getMaximumFortificationLevel()
		);
		base -= this.getArmy().getFortification();
		if(base > 50)
		{
			base = 50;
		}
		if(base < -100)
		{
			base = -100;
		}
		double bonus = 0;
		if(base > 0)
		{
			switch(this.policy.getFortification())
			{
				case UNOCCUPIED_FORTIFICATION:
					bonus = -base * 1.25;
					break;
				case MINIMAL_FUNDING_FORTIFICATION:
					bonus = -base * 0.5;
					break;
				case PARTIAL_FUNDING_FORTIFICATION:
					break;
				case FULL_FUNDING_FORTIFICATION:
					bonus = base * 0.25;
					break;
				case MAX_FORTIFICATION:
					bonus = base * 0.5;
			}
			map.put(TextKey.Fortification.BELOW_MAX, base);
		}
		else if(base < 0)
		{
			switch(this.policy.getFortification())
			{
				case UNOCCUPIED_FORTIFICATION:
					bonus = base;
					break;
				case MINIMAL_FUNDING_FORTIFICATION:
					bonus = base * 0.5;
					break;
				case PARTIAL_FUNDING_FORTIFICATION:
					break;
				case FULL_FUNDING_FORTIFICATION:
					bonus = -base * 0.25;
					break;
				case MAX_FORTIFICATION:
					bonus = -base * 0.5;
			}
			map.put(TextKey.Fortification.ABOVE_MAX, base);
		}
		double net = base + bonus;
		map.put(TextKey.Fortification.BONUS, bonus);
		map.put(TextKey.Fortification.NET, net);
		return map;
	}

	public LinkedHashMap<TextKey, Integer> getApprovalChange()
	{
		LinkedHashMap<TextKey, Integer> map = new LinkedHashMap<>();
		int famine = (int)this.getFamineLevel();
		if(famine < 0)
		{
			map.put(TextKey.Approval.FAMINE, famine);
		}
		map.put(TextKey.Approval.NET, famine);
		return map;
	}

	public LinkedHashMap<TextKey, Integer> getStabilityChange()
	{
		LinkedHashMap<TextKey, Integer> map = new LinkedHashMap<>();
		int approval = this.domestic.getApproval() / 20 - 2;
		int famine = (int)this.getFamineLevel();
		int growth = 0;
		if(this.getEconomy().getGrowth() < 0)
		{
			growth = -1;
		}
		if(this.getEconomy().getGrowth() < -5)
		{
			growth = -2;
		}
		if(this.getEconomy().getGrowth() < -10)
		{
			growth = -4;
		}
		if(this.getEconomy().getGrowth() < -20)
		{
			growth = -6;
		}
		if(this.getEconomy().getGrowth() < -50)
		{
			growth = -10;
		}
		if(approval < 0)
		{
			map.put(TextKey.Stability.LOW_APPROVAL, approval);
		}
		else
		{
			map.put(TextKey.Stability.HIGH_APPROVAL, approval);
		}
		if(this.getFreeManpower() < 0)
		{
			map.put(TextKey.Stability.MANPOWER, (int)((double)this.getFreeManpower() / (double)this.getTotalManpower() * Math.sqrt(Math.sqrt(this.getTotalManpower()))));
		}
		if(famine < 0)
		{
			map.put(TextKey.Stability.FAMINE, famine);
		}
		map.put(TextKey.Stability.GROWTH, growth);
		map.put(TextKey.Stability.NET, approval + famine + growth);
		return map;
	}

	public double getFamineLevel()
	{
		double food = this.economy.getFood() + this.getFoodProduction().get(TextKey.Resource.NET);
		if(food > 0)
		{
			return 0;
		}
		else
		{
			return -Math.sqrt(Math.abs(food)) * (Math.min(1, (this.getDomestic().getMonthsInFamine() + 1.0) / 10.0));
		}
	}

	public LinkedHashMap<TextKey, Long> getGrowthChange()
	{
		LinkedHashMap<TextKey, Long> map = new LinkedHashMap<>();
		long factories = this.getTotalFactories();
		long military = -1 * this.getUsedManpower().get(TextKey.Manpower.NET) / 20000;
		long overMaxManpower = 0;
		if(this.getFreeManpower() < 0)
		{
			overMaxManpower = this.getFreeManpower() / 1000;
		}
		long conscription = economy.getRecentDeconscription() - economy.getRecentConscription();
		long fortification = -this.getArmy().getFortification() / 500;
		if(conscription > 0)
		{
			conscription = (long)((conscription + 1) * 0.75);
			map.put(TextKey.Growth.DECONSCRIPTION, conscription);
		}
		else
		{
			conscription = (long)(conscription * 1.15);
			map.put(TextKey.Growth.CONSCRIPTION, conscription);
		}
		switch(this.policy.getFortification())
		{
			case UNOCCUPIED_FORTIFICATION:
				fortification = 0;
				break;
			case MINIMAL_FUNDING_FORTIFICATION:
				fortification *= 0.5;
				break;
			case FULL_FUNDING_FORTIFICATION:
				fortification *= 1.25;
				break;
			case MAX_FORTIFICATION:
				fortification *= 2;
		}
		map.put(TextKey.Growth.FACTORIES, factories);
		map.put(TextKey.Growth.MILITARY, military);
		map.put(TextKey.Growth.OVER_MAX_MANPOWER, overMaxManpower);
		map.put(TextKey.Growth.FORTIFICATION, fortification);
		map.put(TextKey.Growth.NET, factories + military + conscription + fortification + overMaxManpower);
		return map;
	}

	public LinkedHashMap<TextKey, Double> getPopulationGrowth()
	{
		LinkedHashMap<TextKey, Double> map = new LinkedHashMap<>();
		double base = 2;
		double foodPolicy = 0;
		double manpowerPolicy = 0;
		double economyPolicy = 0;
		if(this.policy.getFood() == Policy.FREE_FOOD)
		{
			foodPolicy = 1.5;
		}
		else if(this.policy.getFood() == Policy.RATIONING_FOOD)
		{
			foodPolicy = -2.5;
		}
		if(this.policy.getManpower() == Policy.DISARMED_MANPOWER)
		{
			manpowerPolicy = 2.5;
		}
		else if(this.policy.getManpower() == Policy.VOLUNTEER_MANPOWER)
		{
			manpowerPolicy = 1;
		}
		else if(this.policy.getManpower() == Policy.MANDATORY_MANPOWER)
		{
			manpowerPolicy = -1;
		}
		else if(this.policy.getManpower() == Policy.SCRAPING_THE_BARREL_MANPOWER)
		{
			manpowerPolicy = -2.5;
		}
		if(this.policy.getEconomy() == Policy.CIVILIAN_ECONOMY)
		{
			economyPolicy = 1.5;
		}
		else if(this.policy.getEconomy() == Policy.WAR_ECONOMY)
		{
			economyPolicy = -1.5;
		}
		map.put(TextKey.Population.BASE, base);
		map.put(TextKey.Population.FOOD_POLICY, foodPolicy);
		map.put(TextKey.Population.MANPOWER_POLICY, manpowerPolicy);
		map.put(TextKey.Population.ECONOMY_POLICY, economyPolicy);
		map.put(TextKey.Population.NET, base + foodPolicy + manpowerPolicy + economyPolicy);
		return map;
	}

	public long getRequiredSizeForNextCity()
	{
		return 500000L * (long)Math.pow(2, cities.size() - 1);
	}

	public boolean canMakeNewCity()
	{
		return this.getTotalPopulation() >= getRequiredSizeForNextCity();
	}

	public City getLargestCity()
	{
		long max = 0;
		int cityId = 0;
		for(City city : cities.values())
		{
			if(city.getPopulation() > max)
			{
				max = city.getPopulation();
				cityId = city.getId();
			}
		}
		return cities.get(cityId);
	}

	public LinkedHashMap<String, LinkedHashMap<TextKey, Long>> getLandUsage()
	{
		LinkedHashMap<String, LinkedHashMap<TextKey, Long>> map = new LinkedHashMap<>();
		for(City city : cities.values())
		{
			map.put(city.getName(), city.getLandUsage());
		}
		return map;
	}

	public String getDisplayString(TextKey key)
	{
		return key.getText();
	}

	@Override
	public void update(Connection conn) throws SQLException
	{
		super.update(conn);
		if(this.cities != null)
		{
			for(City city : this.cities.values())
			{
				city.update(conn);
			}
		}
		if(this.production != null)
		{
			for(Production production : this.production.values())
			{
				production.update(conn);
			}
		}
		economy.update(conn);
		domestic.update(conn);
		foreign.update(conn);
		army.update(conn);
		military.update(conn);
		cosmetic.update(conn);
		if(treatyPermissions != null)
		{
			treatyPermissions.update(conn);
		}
		tech.update(conn);
		policy.update(conn);
	}

	@Override
	public boolean equals(Object object)
	{
		if(object instanceof Nation)
		{
			Nation nation = (Nation)object;
			return nation.getId() == this.getId();
		}
		else
		{
			return false;
		}
	}
}