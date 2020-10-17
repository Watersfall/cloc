package com.watersfall.clocgame.model.nation;

import com.watersfall.clocgame.dao.*;
import com.watersfall.clocgame.model.Region;
import com.watersfall.clocgame.model.TextKey;
import com.watersfall.clocgame.model.alignment.AlignmentTransaction;
import com.watersfall.clocgame.model.alignment.Alignments;
import com.watersfall.clocgame.model.city.City;
import com.watersfall.clocgame.model.decisions.Decision;
import com.watersfall.clocgame.model.event.Event;
import com.watersfall.clocgame.model.message.Message;
import com.watersfall.clocgame.model.military.army.Army;
import com.watersfall.clocgame.model.modifier.Modifier;
import com.watersfall.clocgame.model.news.News;
import com.watersfall.clocgame.model.policies.Policy;
import com.watersfall.clocgame.model.producible.*;
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

public class Nation
{
	private @Getter int id;
	private @Getter @Setter NationCosmetic cosmetic;
	private @Getter @Setter NationStats stats;
	private @Getter @Setter NationProducibles producibles;
	private @Getter @Setter TreatyPermissions treatyPermissions;
	private @Getter @Setter HashMap<Long, City> cities;
	private @Getter @Setter ArrayList<Army> armies;
	private @Getter @Setter NationPolicy policy;
	private @Getter @Setter NationTech tech;
	private @Getter @Setter ArrayList<Integer> invites;
	private @Getter @Setter int newsCount;
	private @Getter @Setter int eventCount;
	private @Getter @Setter ArrayList<Event> events;
	private @Getter @Setter War defensive;
	private @Getter @Setter War offensive;
	private @Getter @Setter Treaty treaty;
	private @Getter @Setter Connection conn;
	private @Getter @Setter boolean allowWriteAccess;
	private @Getter @Setter long freeFactories;
	private @Getter @Setter long lastSeen;
	private @Getter @Setter ArrayList<Modifier> modifiers;
	private @Getter @Setter EnumMap<Alignments, ArrayList<AlignmentTransaction>> alignmentTransactions;
	private @Getter @Setter LinkedHashMap<Integer, Production> production;
	private @Getter @Setter ArrayList<Message> unreadMessages = null;
	private @Setter long lastMessage;

	private long landUsage = -1;
	private LinkedHashMap<String, LinkedHashMap<TextKey, Double>> allProductions = null;
	private HashMap<String, Double> totalProductionCosts = null;
	private LinkedHashMap<TextKey, Double> coalProduction = null;
	private LinkedHashMap<TextKey, Double> ironProduction = null;
	private LinkedHashMap<TextKey, Double> oilProduction = null;
	private LinkedHashMap<TextKey, Double> steelProduction = null;
	private LinkedHashMap<TextKey, Double> nitrogenProduction = null;
	private LinkedHashMap<TextKey, Double> researchProduction = null;

	public Nation(int id)
	{
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
	public String joinTreaty(int id, boolean founder) throws SQLException
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
		else if(!Region.borders(nation.getStats().getRegion(), this.getStats().getRegion()))
		{
			return Responses.cannotWar("noBorder");
		}
		else if(nation.getStats().getWarProtection() > 0)
		{
			if(nation.getStats().getAlignment() == Alignments.NEUTRAL)
			{
				return Responses.cannotWar("neutralProtection");
			}
			else
			{
				return (nation.getStats().getAlignment() == Alignments.CENTRAL_POWERS) ? Responses.cannotWar("germanProtection") : Responses.cannotWar("frenchProtection");
			}
		}
		else if(this.stats.getWarProtection() > 0)
		{
			if(this.getStats().getAlignment() == Alignments.NEUTRAL)
			{
				return Responses.cannotWar("youNeutralProtection");
			}
			else
			{
				return (this.getStats().getAlignment() == Alignments.CENTRAL_POWERS) ? Responses.cannotWar("youGermanProtection") : Responses.cannotWar("youFrenchProtection");
			}
		}
		else
		{
			return null;
		}
	}

	public void damagePopulation(long amount)
	{
		for(Long integer : cities.keySet())
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
	public void declareWar(Nation nation, String name) throws SQLException
	{
		new WarDao(conn, true).createWar(this, nation, name);
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
		return this.stats.getLand() - this.getTotalLandUsage();
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

	public int getArmySize()
	{
		int total = 0;
		for(Army army : this.armies)
		{
			total += army.getSize();
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
		long lostManpower = stats.getLostManpower();
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
		long airforce = stats.getCurrentBombers() + stats.getCurrentFighters() + stats.getCurrentRecon();
		airforce *= 50;
		long army = this.getArmySize();
		map.put(TextKey.Manpower.AIRFORCE, -airforce);
		map.put(TextKey.Manpower.ARMY, -army);
		map.put(TextKey.Manpower.NET, -airforce + -army);
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
		map.put("Budget", this.stats.getBudget());
		map.put("Food", this.stats.getFood());
		map.put("Coal", this.stats.getCoal());
		map.put("Iron", this.stats.getIron());
		map.put("Oil", this.stats.getOil());
		map.put("Steel", this.stats.getSteel());
		map.put("Nitrogen", this.stats.getNitrogen());
		map.put("Research", this.stats.getResearch());
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
		double stabilityEffect = this.getStats().getStability() - 50;
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
				double amount = -this.stats.getLand() / TechnologyFarmingMachines.LAND_PER_STEEL;
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
				double amount = -this.stats.getLand() / TechnologyArtificialFertilizer.LAND_PER_NITROGEN;
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
		if(this.hasTech(Technologies.ADVANCED_ARTIFICIAL_FERTILIZER) && this.getTotalNitrogenProduction().get(TextKey.Resource.NET) + this.getStats().getNitrogen() >= 0)
		{
			tech = farming * TechnologyAdvancedArtificialFertilizer.FOOD_GAIN;
		}
		else if(this.hasTech(Technologies.ARTIFICIAL_FERTILIZER) && this.getTotalNitrogenProduction().get(TextKey.Resource.NET) + this.getStats().getNitrogen() >= 0)
		{
			tech = farming * TechnologyArtificialFertilizer.FOOD_GAIN;
		}
		else if(this.hasTech(Technologies.BASIC_ARTIFICIAL_FERTILIZER))
		{
			tech = farming * TechnologyBasicArtificialFertilizer.FOOD_GAIN;
		}
		if(this.hasTech(Technologies.ADVANCED_FARMING_MACHINES) && this.getTotalSteelProduction().get(TextKey.Resource.NET) + this.getStats().getSteel() > 0)
		{
			tech += farming * TechnologyAdvancedFarmingMachines.FOOD_GAIN;
		}
		else if(this.hasTech(Technologies.FARMING_MACHINES) && this.getTotalSteelProduction().get(TextKey.Resource.NET) + this.getStats().getSteel() > 0)
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

	public long getTotalProduciblesByCategory(ProducibleCategory category)
	{
		long total = 0;
		for(Producibles producibles : Producibles.getProduciblesForCategory(category))
		{
			total += this.producibles.getProducible(producibles);
		}
		return total;
	}

	public long getTotalProduciblesByCategories(ProducibleCategory... category)
	{
		long total = 0;
		for(Producibles producibles : Producibles.getProduciblesByCategories(category))
		{
			total += this.producibles.getProducible(producibles);
		}
		return total;
	}

	public long getProduciblesProductionByCategory(ProducibleCategory category)
	{
		double total = 0;
		for(Production production : this.production.values())
		{
			if(production.getProductionAsTechnology().getTechnology().getProducibleItem().getCategory() == category)
			{
				total += production.getMonthlyProduction(this);
			}
		}
		return (long)total;
	}

	public long getProduciblesProduction(Producibles producible)
	{
		double total = 0;
		for(Production production : this.production.values())
		{
			if(production.getProductionAsTechnology().getTechnology().getProducibleItem() == producible.getProducible())
			{
				total += production.getMonthlyProduction(this);
			}
		}
		return (long)total;
	}
	
	public LinkedHashMap<Producibles, Integer> getActivePlanes(ProducibleCategory category, int allowed)
	{
		LinkedHashMap<Producibles, Integer> map = new LinkedHashMap<>();
		ArrayList<Producibles> list = Producibles.getProduciblesForCategory(category);
		for(Producibles producible : list)
		{
			if(allowed > 0)
			{
				int count = this.getProducibles().getProducible(producible);
				if(count > 0)
				{
					if(count > allowed)
					{
						map.put(producible, allowed);
						break;
					}
					else
					{
						map.put(producible, count);
						allowed -= count;
					}
				}
			}
			else
			{
				break;
			}
		}
		return map;
	}

	public LinkedHashMap<Producibles, Integer> getActiveFighters()
	{
		int allowed = stats.getCurrentFighters();
		return getActivePlanes(ProducibleCategory.FIGHTER_PLANE, allowed);
	}

	public LinkedHashMap<Producibles, Integer> getActiveBombers()
	{
		int allowed = stats.getCurrentBombers();
		return getActivePlanes(ProducibleCategory.BOMBER_PLANE, allowed);
	}

	public LinkedHashMap<Producibles, Integer> getActiveRecon()
	{
		int allowed = stats.getCurrentRecon();
		return getActivePlanes(ProducibleCategory.RECON_PLANE, allowed);
	}

	/**
	 * Calculates the power of an army based on it's getArmySize(), technology level, getArmyTraining(), and artillery
	 * @return The army's power
	 */
	/*public double getPower()
	{
		double power = 0e0;
		long requiredEquipment = (long)getArmySize() * 1000L;
		for(Producibles producibles : Producibles.getProduciblesForCategory(ProducibleCategory.INFANTRY_EQUIPMENT))
		{
			if(requiredEquipment > 0)
			{
				if(this.producibles.getProducible(producibles) > 0)
				{
					if(this.producibles.getProducible(producibles) > requiredEquipment)
					{
						power += requiredEquipment * ((IArmyPower)producibles.getProducible()).getArmyPower();
						requiredEquipment = 0;
					}
					else
					{
						power += this.producibles.getProducible(producibles) * ((IArmyPower)producibles.getProducible()).getArmyPower();
						requiredEquipment -= this.producibles.getProducible(producibles);
					}
				}
			}
		}
		//Sticks and stones are better than nothing
		power += requiredEquipment * 0.5;
		power *= java.lang.Math.sqrt(getArmyTraining() + 1);
		return Math.sqrt(power);
	}*/

	/**
	 * Calculates the ability of this nations army to break through defenses
	 * and generally attack better
	 * <br>
	 * Represented as a percentage, at 0% ignores no defender bonuses, at 100% ignores all defender bonuses
	 * @return The army's breakthrough
	 */
	public double getBreakthrough()
	{
		int max = this.getArmySize() * 5;
		long currentTanks = this.getTotalProduciblesByCategory(ProducibleCategory.TANK);
		if(currentTanks > max)
		{
			currentTanks = max;
		}
		double ratio = (double)currentTanks / (double)max;
		long currentArtillery = this.getTotalProduciblesByCategory(ProducibleCategory.ARTILLERY);
		if(currentArtillery > max)
		{
			currentArtillery = max;
		}
		double artillery = ((double)currentArtillery / (double)max);
		artillery /= 2;
		double recon = 1;
		int maxRecon = max / 5;
		long currentRecon = this.getTotalProduciblesByCategory(ProducibleCategory.RECON_PLANE);
		if(currentRecon > maxRecon)
		{
			currentRecon = maxRecon;
		}
		List<Producibles> list = Producibles.getProduciblesForCategory(ProducibleCategory.RECON_PLANE);
		Collections.reverse(list);
		for(Producibles plane : list)
		{
			if(this.producibles.getProducible(plane) > currentRecon)
			{
				recon += (currentRecon * ((IReconPower)plane.getProducible()).getReconPower());
				currentRecon = 0;
			}
			else
			{
				recon += (this.producibles.getProducible(plane) * ((IReconPower)plane.getProducible()).getReconPower());
				currentRecon -= this.producibles.getProducible(plane);
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
		double ratio = this.stats.getFortification() / 10000.0;
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
		for(Producibles producibles : Producibles.getProduciblesForCategory(ProducibleCategory.FIGHTER_PLANE))
		{
			power += (this.producibles.getProducible(producibles) * ((IFighterPower)producibles.getProducible()).getFighterPower());
		}
		if(attackingOtherAirforce)
		{
			for(Producibles producibles : Producibles.getProduciblesForCategory(ProducibleCategory.BOMBER_PLANE))
			{
				power += (this.producibles.getProducible(producibles) * ((IBomberPower)producibles.getProducible()).getBomberPower() / 2);
			}
		}
		for(Producibles producibles : Producibles.getProduciblesForCategory(ProducibleCategory.FIGHTER_PLANE))
		{
			power += (this.producibles.getProducible(producibles) * ((IFighterPower)producibles.getProducible()).getFighterPower());
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
		for(Producibles producibles : Producibles.getProduciblesForCategory(ProducibleCategory.BOMBER_PLANE))
		{
			power += (this.producibles.getProducible(producibles) * ((IBomberPower)producibles.getProducible()).getBomberPower());
		}
		return power / 10;
	}

	public long getTotalShipCount()
	{
		return 0;
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
		double armyHp = this.getArmySize() * 100.0;
		armyHp -= (powerDiff * theirPower);
		return (int)(this.getArmySize() - (armyHp / 100.0));
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
				return (long)(this.stats.getGdp() / 2L * (this.stats.getApproval() / 100.0));
			case WAR_PROPAGANDA:
				return getDecisionCost(Decision.PROPAGANDA) / 2L;
			case LAND_CLEARANCE:
				return (long)(this.stats.getGdp() * 2L);
			case ALIGN_CENTRAL_POWERS:
			case ALIGN_ENTENTE:
			case ALIGN_NEUTRAL:
			case PARDON_CRIMINALS:
			case INCREASE_ARREST_QUOTAS:
				return 100;
			case FORM_TREATY:
				return 500;
			case FORTIFY:
				return (long)(this.getMaximumFortificationLevel() / 100.0 * ((double)this.stats.getFortification() / (double)this.getMaximumFortificationLevel()));
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
		return this.stats.getGdp() / 7;
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
				this.stats.getFortification() + (0.1 * ((double)this.getMaximumFortificationLevel() / (this.stats.getFortification() / 100.0))),
				this.getMaximumFortificationLevel()
		);
		base -= this.stats.getFortification();
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
		int approval = this.stats.getApproval() / 20 - 2;
		int famine = (int)this.getFamineLevel();
		int growth = 0;
		if(this.getStats().getGrowth() < 0)
		{
			growth = -1;
		}
		if(this.getStats().getGrowth() < -5)
		{
			growth = -2;
		}
		if(this.getStats().getGrowth() < -10)
		{
			growth = -4;
		}
		if(this.getStats().getGrowth() < -20)
		{
			growth = -6;
		}
		if(this.getStats().getGrowth() < -50)
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
		double food = this.stats.getFood() + this.getFoodProduction().get(TextKey.Resource.NET);
		if(food > 0)
		{
			return 0;
		}
		else
		{
			return -Math.sqrt(Math.abs(food)) * (Math.min(1, (this.getStats().getMonthsInFamine() + 1.0) / 10.0));
		}
	}

	public LinkedHashMap<TextKey, Long> getGrowthChange()
	{
		LinkedHashMap<TextKey, Long> map = new LinkedHashMap<>();
		long factories = this.getTotalFactories();
		long military = this.getUsedManpower().get(TextKey.Manpower.NET) / 20000;
		long overMaxManpower = 0;
		if(this.getFreeManpower() < 0)
		{
			overMaxManpower = this.getFreeManpower() / 1000;
		}
		long conscription = stats.getRecentDeconscription() - stats.getRecentConscription();
		long fortification = -this.stats.getFortification() / 500;
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
		int airforce = this.getFighterChange() + this.getBomberChange() + this.getReconChange();
		if(airforce > 0)
		{
			map.put(TextKey.Growth.AIR_INCREASE, (long)(airforce / 50.0));
		}
		long cityGarrison = 0;
		for(City city : cities.values())
		{
			cityGarrison += city.getGarrisonSize().get(TextKey.Garrison.NET);
		}
		cityGarrison /= -20000;
		map.put(TextKey.Growth.FACTORIES, factories);
		map.put(TextKey.Growth.MILITARY, military);
		map.put(TextKey.Growth.OVER_MAX_MANPOWER, overMaxManpower);
		map.put(TextKey.Growth.FORTIFICATION, fortification);
		map.put(TextKey.Growth.CITY_GARRISON, cityGarrison);
		map.put(TextKey.Growth.NET, factories + military + conscription + fortification + overMaxManpower + airforce + cityGarrison);
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
		long cityId = 0;
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

	public int getMaxReputation(Alignments alignment)
	{
		if(alignment == Alignments.NEUTRAL)
		{
			return 0;
		}
		else
		{
			HashMap<TextKey, Integer> map;
			if(alignment == Alignments.CENTRAL_POWERS)
			{
				map = getCentralPowersReputation();
			}
			else
			{
				map = getEntenteReputation();
			}
			int sum = 0;
			for(Integer num : map.values())
			{
				sum += num;
			}
			return sum;
		}

	}

	public HashMap<TextKey, Integer> getEntenteReputation()
	{
		HashMap<TextKey, Integer> map = new HashMap<>();
		int positiveTrade = 0, negativeTrade = 0;
		if(this.alignmentTransactions.containsKey(Alignments.ENTENTE))
		{
			for(AlignmentTransaction transaction : this.alignmentTransactions.get(Alignments.ENTENTE))
			{
				positiveTrade += 25;
			}
		}
		if(this.alignmentTransactions.containsKey(Alignments.CENTRAL_POWERS))
		{
			for(AlignmentTransaction transaction : this.alignmentTransactions.get(Alignments.CENTRAL_POWERS))
			{
				negativeTrade -= 50;
			}
		}
		map.put(TextKey.Alignment.EQUIPMENT_SALES, positiveTrade);
		map.put(TextKey.Alignment.EQUIPMENT_SALES_NEGATIVE, negativeTrade);
		if(this.stats.getAlignment() == Alignments.ENTENTE)
		{
			map.put(TextKey.Alignment.OFFICIAL_ALIGNMENT, (int)(positiveTrade * 0.5));
		}
		else if(this.stats.getAlignment() == Alignments.CENTRAL_POWERS)
		{
			map.put(TextKey.Alignment.OFFICIAL_ALIGNMENT, -100000);
		}
		return map;
	}

	public HashMap<TextKey, Integer> getCentralPowersReputation()
	{
		HashMap<TextKey, Integer> map = new HashMap<>();
		int positiveTrade = 0, negativeTrade = 0;
		if(this.alignmentTransactions.containsKey(Alignments.CENTRAL_POWERS))
		{
			for(AlignmentTransaction transaction : this.alignmentTransactions.get(Alignments.CENTRAL_POWERS))
			{
				positiveTrade += 25;
			}
		}
		if(this.alignmentTransactions.containsKey(Alignments.ENTENTE))
		{
			for(AlignmentTransaction transaction : this.alignmentTransactions.get(Alignments.ENTENTE))
			{
				negativeTrade -= 50;
			}
		}
		map.put(TextKey.Alignment.EQUIPMENT_SALES, positiveTrade);
		map.put(TextKey.Alignment.EQUIPMENT_SALES_NEGATIVE, negativeTrade);
		if(this.stats.getAlignment() == Alignments.ENTENTE)
		{
			map.put(TextKey.Alignment.OFFICIAL_ALIGNMENT, -100000);
		}
		else if(this.stats.getAlignment() == Alignments.CENTRAL_POWERS)
		{
			map.put(TextKey.Alignment.OFFICIAL_ALIGNMENT, (int)(positiveTrade * 0.5));
		}
		return map;
	}

	public int getFighterChange()
	{
		int max = this.stats.getMaxFighters();
		int fighterCount = (int)Math.min(this.getTotalProduciblesByCategory(ProducibleCategory.FIGHTER_PLANE), this.stats.getCurrentFighters());
		if(max <= -1)
		{
			max = (int)this.getTotalProduciblesByCategory(ProducibleCategory.FIGHTER_PLANE);
		}
		if(fighterCount >= max)
		{
			return max - fighterCount;
		}
		else
		{
			int increase = (int)(max / 10.0);
			if(increase + fighterCount > max)
			{
				increase = max - fighterCount;
			}
			return increase;
		}
	}

	public int getBomberChange()
	{
		int max = this.stats.getMaxBombers();
		int bomberCount = (int)this.getProduciblesProductionByCategory(ProducibleCategory.BOMBER_PLANE);
		if(max <= -1)
		{
			max = (int)this.getTotalProduciblesByCategory(ProducibleCategory.BOMBER_PLANE);
		}
		if(bomberCount >= max)
		{
			return max - bomberCount;
		}
		else
		{
			int increase = (int)(max / 10.0);
			if(increase + bomberCount > max)
			{
				increase = max - bomberCount;
			}
			return increase;
		}
	}

	public int getReconChange()
	{
		int max = this.stats.getMaxRecon();
		int reconCount = (int)this.getProduciblesProductionByCategory(ProducibleCategory.RECON_PLANE);
		if(max <= -1)
		{
			max = (int)this.getTotalProduciblesByCategory(ProducibleCategory.RECON_PLANE);
		}
		if(reconCount >= max)
		{
			return max - reconCount;
		}
		else
		{
			int increase = (int)(max / 10.0);
			if(increase + reconCount > max)
			{
				increase = max - reconCount;
			}
			return increase;
		}
	}

	public boolean hasUnreadNews()
	{
		return this.lastMessage > stats.getLastNews();
	}

	public void fixCurrentPlanes()
	{
		int fighters = (int)this.getTotalProduciblesByCategory(ProducibleCategory.FIGHTER_PLANE);
		int bombers = (int)this.getTotalProduciblesByCategory(ProducibleCategory.BOMBER_PLANE);
		int recon = (int)this.getTotalProduciblesByCategory(ProducibleCategory.RECON_PLANE);
		if(fighters < this.getStats().getCurrentFighters())
		{
			this.getStats().setCurrentFighters(fighters);
		}
		if(bombers < this.getStats().getCurrentBombers())
		{
			this.getStats().setCurrentBombers(bombers);
		}
		if(recon < this.getStats().getCurrentRecon())
		{
			this.getStats().setCurrentRecon(recon);
		}
	}

	public HashMap<Army, HashMap<ProducibleCategory, Integer>> getArmyEquipmentChange()
	{
		HashMap<Army, HashMap<ProducibleCategory, Integer>> map = new HashMap<>();
		int max = getEquipmentReinforcementCapacity().get(TextKey.Reinforcement.NET);
		for(ProducibleCategory category : ProducibleCategory.values())
		{
			for(int i = 0; i < armies.size() && max > 0; i++)
			{
				Army army = armies.get(i);
				map.putIfAbsent(army, new HashMap<>());
				if(army.getNeededEquipment().getOrDefault(category, 0) > 0)
				{
					int totalPossibleGain = Math.min(army.getNeededEquipment().get(category), (int)(this.getTotalProduciblesByCategory(category) + this.getProduciblesProductionByCategory(category)));
					if(totalPossibleGain > 0)
					{
						if(totalPossibleGain > max)
						{
							totalPossibleGain = max;
							max = 0;
						}
						max -= totalPossibleGain;
						map.get(army).put(category, totalPossibleGain);
					}
				}
			}
		}
		return map;
	}

	public int getTotalArmyEquipmentChange(Army army)
	{
		int total = 0;
		if(getArmyEquipmentChange().get(army) == null)
		{
			return total;
		}
		for(Integer integer : getArmyEquipmentChange().get(army).values())
		{
			total += integer;
		}
		return total;
	}

	public HashMap<Army, Integer> getArmyManpowerChange()
	{
		HashMap<Army, Integer> map = new HashMap<>();
		int max = getManpowerReinforcementCapacity().get(TextKey.Reinforcement.NET);
		for(int i = 0; i < armies.size() && max > 0; i++)
		{
			int required = armies.get(i).getNeededManpower();
			if(required > 0)
			{
				if(required > max)
				{
					required = max;
				}
				max -= required;
				map.put(armies.get(i), required);
			}
		}
		return map;
	}

	public int getMaxArmies()
	{
		return 3 + (this.getTotalBarracks() / 3);
	}

	public boolean canCreateNewArmy()
	{
		return getMaxArmies() > armies.size();
	}

	public LinkedHashMap<TextKey, Integer> getEquipmentReinforcementCapacity()
	{
		LinkedHashMap<TextKey, Integer> map = new LinkedHashMap<>();
		int base = 1000;
		int infra = this.getTotalInfrastructure() * 250;
		map.put(TextKey.Reinforcement.BASE, base);
		map.put(TextKey.Reinforcement.INFRASTRUCTURE, infra);
		map.put(TextKey.Reinforcement.NET, base + infra);
		return map;
	}

	public LinkedHashMap<TextKey, Integer> getManpowerReinforcementCapacity()
	{
		LinkedHashMap<TextKey, Integer> map = new LinkedHashMap<>();
		int base = 2500;
		int barracks = this.getTotalBarracks() * 250;
		int conscription = 0;
		switch(this.policy.getManpower())
		{
			case SCRAPING_THE_BARREL_MANPOWER:
				conscription = 2500;
				break;
			case MANDATORY_MANPOWER:
				conscription = 1500;
				break;
			case RECRUITMENT_MANPOWER:
				conscription = 500;
				break;
			case DISARMED_MANPOWER:
				conscription = -250;
				break;
		}
		int net = base + barracks + conscription;
		map.put(TextKey.Reinforcement.BASE, base);
		map.put(TextKey.Reinforcement.BARRACKS, barracks);
		map.put(TextKey.Reinforcement.CONSCRIPTION_LAW, conscription);
		map.put(TextKey.Reinforcement.NET, net);
		return map;
	}

	public int getTotalInfrastructure()
	{
		int total = 0;
		for(City city : cities.values())
		{
			total += city.getRailroads();
		}
		return total;
	}

	public int getTotalBarracks()
	{
		int total = 0;
		for(City city : cities.values())
		{
			total += city.getBarracks();
		}
		return total;
	}

	public int getTotalPorts()
	{
		int total = 0;
		for(City city : cities.values())
		{
			total += city.getPorts();
		}
		return total;
	}

	public Army getArmy(long id)
	{
		for(Army army : armies)
		{
			if(army.getId() == id)
			{
				return army;
			}
		}
		return null;
	}

	public void update(Connection conn) throws SQLException
	{
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
		if(stats != null)
		{
			stats.update(conn);
		}
		if(producibles != null)
		{
			producibles.update(conn);
		}
		if(cosmetic != null)
		{
			cosmetic.update(conn);
		}
		if(treatyPermissions != null)
		{
			treatyPermissions.update(conn);
		}
		if(tech != null)
		{
			tech.update(conn);
		}
		if(policy != null)
		{
			policy.update(conn);
		}
		if(armies != null)
		{
			for(Army army : armies)
			{
				army.update(conn);
			}
		}
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