package net.watersfall.clocgame.model.nation;

import lombok.Getter;
import net.watersfall.clocgame.model.Region;
import net.watersfall.clocgame.model.UpdatableIntId;
import net.watersfall.clocgame.model.alignment.Alignments;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NationStats extends UpdatableIntId
{
	private @Getter long lastMessage, lastNews, lastLogin, lostManpower, casualties;
	private @Getter int recentConscription, recentDeconscription, land, monthsInFamine, fortification,
						ententeReputation, centralPowersReputation, rebels, economic, government, approval, stability,
						warProtection, maxFighters, maxBombers, maxRecon, currentFighters, currentBombers, currentRecon;
	private @Getter double gdp, growth, budget, iron, coal, oil, food, steel, nitrogen, research;
	private @Getter Region region;
	private @Getter Alignments alignment;

	public NationStats(ResultSet results) throws SQLException
	{
		super("nation_stats", results.getInt("login.id"));
		this.lastMessage = results.getLong("last_message");
		this.lastNews = results.getLong("last_news");
		this.lastLogin = results.getLong("last_login");
		this.land = results.getInt("land");
		this.government = results.getInt("government");
		this.approval = results.getInt("approval");
		this.stability = results.getInt("stability");
		this.lostManpower = results.getLong("lost_manpower");
		this.rebels = results.getInt("rebels");
		this.monthsInFamine = results.getInt("months_in_famine");
		this.economic = results.getInt("economic");
		this.gdp = results.getDouble("gdp");
		this.growth = results.getDouble("growth");
		this.budget = results.getDouble("budget");
		this.iron = results.getDouble("iron");
		this.coal = results.getDouble("coal");
		this.oil = results.getDouble("oil");
		this.food = results.getDouble("food");
		this.steel = results.getDouble("steel");
		this.nitrogen= results.getDouble("nitrogen");
		this.research = results.getDouble("research");
		this.recentConscription = results.getInt("recent_conscription");
		this.recentDeconscription = results.getInt("recent_deconscription");
		this.region = Region.valueOf(results.getString("region"));
		this.alignment = Alignments.valueOf(results.getString("alignment"));
		this.ententeReputation = results.getInt("entente_reputation");
		this.centralPowersReputation = results.getInt("central_powers_reputation");
		this.casualties = results.getLong("casualties");
		this.warProtection = results.getInt("war_protection");
		this.maxFighters = results.getInt("max_fighters");
		this.maxBombers = results.getInt("max_bombers");
		this.maxRecon = results.getInt("max_recon");
		this.currentFighters = results.getInt("current_fighters");
		this.currentBombers = results.getInt("current_bombers");
		this.currentRecon = results.getInt("current_recon");
	}

	public void setEconomic(int economic)
	{
		if(economic < 0)
			economic = 0;
		else if(economic > 100)
			economic = 100;
		this.setField("economic", economic);
		this.economic = economic;
	}

	public void setGdp(double gdp)
	{
		if(gdp < 100)
			gdp = 100;
		else if(gdp >= 999999999999.00)
			gdp = 999999999999.00;
		this.setField("gdp", gdp);
		this.gdp = gdp;
	}

	public void setGrowth(double growth)
	{
		if(growth >= 999999999999.00)
			growth = 999999999999.00;
		this.setField("growth", growth);
		this.growth = growth;
	}

	public void setBudget(double budget)
	{
		if(budget >= 999999999999.00)
			budget = 999999999999.00;
		this.setField("budget", budget);
		this.budget = budget;
	}

	public void setIron(double iron)
	{
		if(iron < 0)
			iron = 0;
		else if(iron >= 999999999999.00)
			iron = 999999999999.00;
		this.setField("iron", iron);
		this.iron = iron;
	}

	public void setCoal(double coal)
	{
		if(coal < 0)
			coal = 0;
		else if(coal >= 999999999999.00)
			coal = 999999999999.00;
		this.setField("coal", coal);
		this.coal = coal;
	}

	public void setOil(double oil)
	{
		if(oil < 0)
			oil = 0;
		else if(oil >= 999999999999.00)
			oil = 999999999999.00;
		this.setField("oil", oil);
		this.oil = oil;
	}

	public void setFood(double food)
	{
		if(food < 0)
			food = 0;
		else if(food >= 999999999999.00)
			food = 999999999999.00;
		this.setField("food", food);
		this.food = food;
	}

	public void setSteel(double steel)
	{
		if(steel < 0)
			steel = 0;
		else if(steel >= 999999999999.00)
			steel = 999999999999.00;
		this.setField("steel", steel);
		this.steel = steel;
	}

	public void setNitrogen(double nitrogen)
	{
		if(nitrogen < 0)
			nitrogen = 0;
		else if(nitrogen >= 999999999999.00)
			nitrogen = 999999999999.00;
		this.setField("nitrogen", nitrogen);
		this.nitrogen = nitrogen;
	}

	public void setResearch(double research)
	{
		if(research < 0)
			research = 0;
		if(research >= 999999999999.00)
			research = 999999999999.00;
		this.setField("research", research);
		this.research = research;
	}

	public void setRecentConscription(int conscription)
	{
		if(conscription < 0)
			conscription = 0;
		else if(conscription > 2000000000)
			conscription = 2000000000;
		this.setField("recent_conscription", conscription);
		this.recentConscription = conscription;
	}

	public void setRecentDeconscription(int conscription)
	{
		if(conscription < 0)
			conscription = 0;
		else if(conscription > 2000000000)
			conscription = 2000000000;
		this.setField("recent_deconscription", conscription);
		this.recentDeconscription = conscription;
	}

	public void setLand(int land)
	{
		if(land < 0)
			land = 0;
		else if(land > 2000000000)
			land = 2000000000;
		this.setField("land", land);
		this.land = land;
	}

	public void setGovernment(int government)
	{
		if(government < 0)
			government = 0;
		else if(government > 100)
			government = 100;
		this.setField("government", government);
		this.government = government;
	}

	public void setApproval(int approval)
	{
		if(approval < 0)
			approval = 0;
		else if(approval > 100)
			approval = 100;
		this.setField("approval", approval);
		this.approval = approval;
	}

	public void setStability(int stability)
	{
		if(stability < 0)
			stability = 0;
		else if(stability > 100)
			stability = 100;
		this.setField("stability", stability);
		this.stability = stability;
	}

	public void setLostManpower(long manpowerLost)
	{
		if(manpowerLost < 0)
			manpowerLost = 0;
		else if(manpowerLost > 1000000000000000L)
			manpowerLost = 1000000000000000L;
		this.setField("lost_manpower", manpowerLost);
		this.lostManpower = manpowerLost;
	}

	public void setRebels(int rebels)
	{
		if(rebels < 0)
			rebels = 0;
		else if(rebels > 100)
			rebels = 100;
		this.setField("rebels", rebels);
		this.rebels = rebels;
	}

	public void setMonthsInFamine(int monthsInFamine)
	{
		if(monthsInFamine < 0)
			monthsInFamine = 0;
		this.setField("months_in_famine", monthsInFamine);
		this.monthsInFamine = monthsInFamine;
	}

	public void setRegion(Region region)
	{
		this.setField("region", region);
		this.region = region;
	}

	public void setAlignment(Alignments alignment)
	{
		this.setField("alignment", alignment);
		this.alignment = alignment;
	}

	public void setEntenteReputation(int ententeReputation)
	{
		this.ententeReputation = ententeReputation;
		this.setField("entente_reputation", ententeReputation);
	}

	public void setCentralPowersReputation(int centralPowersReputation)
	{
		this.centralPowersReputation = centralPowersReputation;
		this.setField("central_powers_reputation", centralPowersReputation);
	}

	public int getReputation(Alignments alignment)
	{
		if(alignment == Alignments.CENTRAL_POWERS)
			return this.centralPowersReputation;
		else if(alignment == Alignments.ENTENTE)
			return this.ententeReputation;
		else
			return 0;
	}

	public void setReputation(Alignments alignment, int reputation)
	{
		if(alignment == Alignments.CENTRAL_POWERS)
			setCentralPowersReputation(reputation);
		else if(alignment == Alignments.ENTENTE)
			setEntenteReputation(reputation);
	}

	public void setFortification(int fortification)
	{
		if(fortification < 0)
			fortification = 0;
		if(fortification > 10000)
			fortification = 10000;
		this.setField("fortification", fortification);
		this.fortification = fortification;
	}

	public void setCasualties(long casualties)
	{
		if(casualties < 0)
			casualties = 0;
		if(casualties > 100000000000000L)
			casualties = 100000000000000L;
		this.setField("casualties", casualties);
		this.casualties = casualties;
	}

	public void setWarProtection(int warProtection)
	{
		if(warProtection < 0)
			warProtection = 0;
		else if(warProtection > 127)
			warProtection = 127;
		this.setField("war_protection", warProtection);
		this.warProtection = warProtection;
	}
	
	public void setMaxFighters(int maxFighters)
	{
		if(maxFighters > 2000000000)
			maxFighters = 2000000000;
		else if(maxFighters < -1)
			maxFighters = -1;
		this.maxFighters = maxFighters;
		this.setField("max_fighters", maxFighters);
	}

	public void setMaxBombers(int maxBombers)
	{
		if(maxBombers > 2000000000)
			maxBombers = 2000000000;
		else if(maxBombers < -1)
			maxBombers = -1;
		this.maxBombers = maxBombers;
		this.setField("max_bombers", maxBombers);
	}

	public void setMaxRecon(int maxRecon)
	{
		if(maxRecon > 2000000000)
			maxRecon = 2000000000;
		else if(maxRecon < -1)
			maxRecon = -1;
		this.maxRecon = maxRecon;
		this.setField("max_recon", maxRecon);
	}

	public void setCurrentFighters(int currentFighters)
	{
		if(currentFighters > 2000000000)
			currentFighters = 2000000000;
		else if(currentFighters < 0)
			currentFighters = 0;
		this.currentFighters = currentFighters;
		this.setField("current_fighters", currentFighters);
	}

	public void setCurrentBombers(int currentBombers)
	{
		if(currentBombers > 2000000000)
			currentBombers = 2000000000;
		else if(currentBombers < 0)
			currentBombers = 0;
		this.currentBombers = currentBombers;
		this.setField("current_bombers", currentBombers);
	}

	public void setCurrentRecon(int currentRecon)
	{
		if(currentRecon > 2000000000)
			currentRecon = 2000000000;
		else if(currentRecon < 0)
			currentRecon = 0;
		this.currentRecon = currentRecon;
		this.setField("current_recon", currentRecon);
	}
}
