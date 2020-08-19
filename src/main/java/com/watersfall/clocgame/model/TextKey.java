package com.watersfall.clocgame.model;

import com.watersfall.clocgame.model.nation.City;
import lombok.Getter;

public interface TextKey
{
	public String getText();

	enum Population implements TextKey
	{
		BASE("% from base growth"),
		FOOD_POLICY("% from food policy"),
		MANPOWER_POLICY("% from conscription policy"),
		ECONOMY_POLICY("% from economic policy"),
		SIZE("% from city size"),
		NET("% total population growth"),
		FAMINE("% from famine");

		private @Getter String text;
		Population(String text)
		{
			this.text = text;
		}
	}

	enum Growth implements TextKey
	{
		FACTORIES(" from factories"),
		MILITARY(" from military upkeep"),
		DECONSCRIPTION(" from recent deconscription"),
		CONSCRIPTION(" from recent conscription"),
		OVER_MAX_MANPOWER(" from being over manpower limit"),
		FORTIFICATION(" from fortification construction"),
		NET(" net growth");

		private @Getter String text;
		Growth(String text)
		{
			this.text = text;
		}
	}

	enum Stability implements TextKey
	{
		MANPOWER("% from being over max manpower"),
		LOW_APPROVAL("% from low approval"),
		HIGH_APPROVAL("% from high approval"),
		GROWTH("% from low growth"),
		FAMINE("% from famine"),
		NET("% net change");

		private @Getter String text;
		Stability(String text)
		{
			this.text = text;
		}
	}

	enum Approval implements TextKey
	{
		FAMINE("% from famine"),
		POLICIES("% from policies"),
		NET("% net change");

		private @Getter String text;
		Approval(String text)
		{
			this.text = text;
		}
	}

	enum Manpower implements TextKey
	{
		ARMY(" in the army"),
		NAVY(" in the navy"),
		AIRFORCE(" in the airforce"),
		NET(" total deployed");

		private @Getter String text;
		Manpower(String text)
		{
			this.text = text;
		}
	}

	enum Land implements TextKey
	{
		MINES(" from mines"),
		FACTORIES(" from factories"),
		UNIVERSITIES(" from universities");

		private @Getter String text;
		Land(String text)
		{
			this.text = text;
		}
	}

	enum Resource implements TextKey
	{
		GDP(" from GDP"),
		FACTORY_UPKEEP(" from factory demands"),
		FACTORY_OUTPUT(" from factory output"),
		MINES(" from mines"),
		WELLS(" from wells"),
		INFRASTRUCTURE(" from infrastructure"),
		FARMING(" from farming"),
		CONSUMPTION(" from consumption"),
		NET(" net production"),
		TOTAL_GAIN(" total produced"),
		TOTAL_LOSS(" total consumed"),
		DEVASTATION(" from devastation"),
		DEFAULT(" default gain"),
		ECONOMY_TYPE(" from economic focus"),
		FOOD_POLICY(" from food policy"),
		MILITARY_FACTORY_UPKEEP(" from military factory demands"),
		TECHNOLOGY(" from technology"),
		FARMING_DEMANDS(" from farming demands"),
		STABILITY(" from stability"),
		STRIKE(" from strikes"),
		UNIVERSITIES(" from universities");

		private @Getter String text;
		Resource(String text)
		{
			this.text = text;
		}
	}

	enum Farming implements TextKey
	{
		BASE(" default gain"),
		NET(" net gain"),
		REGULATION(" from regulations"),
		DEREGULATION(" from deregulation"),
		SUBSIDIES(" from subsidies"),
		COLLECTIVIZATION(" from collective farms"),
		TECHNOLOGY(" from technology");

		private @Getter String text;
		Farming(String text)
		{
			this.text = text;
		}
	}

	enum Fortification implements TextKey
	{
		BELOW_MAX("% from being below max fortification"),
		ABOVE_MAX("% from being above max fortification"),
		POLICY("% from fortification policies"),
		TECHNOLOGY("% from technology"),
		BONUS("% from fortification policies"),
		NET("% net change");

		private @Getter String text;
		Fortification(String text)
		{
			this.text = text;
		}
	}

	enum Modifiers implements TextKey
	{
		RESOURCE_PRODUCTION("% to resource production", false),
		COAL_PRODUCTION("% to coal production", false),
		IRON_PRODUCTION("% to iron production", false),
		OIL_PRODUCTION("% to oil production", false),
		MINE_PRODUCTION("% to all mining output", false),
		CIVILIAN_INDUSTRY_PRODUCTION("% to steel production", false),
		MILITARY_INDUSTRY_PRODUCTION("% to military factory output", false),
		FACTORY_PRODUCTION("% to all factory output", false),
		STABILITY_PER_MONTH("% stability per month", true),
		APPROVAL_PER_MONTH("% approval per month", true),
		POPULATION_GROWTH("% population growth", false);

		private @Getter String text;
		private @Getter boolean global;
		Modifiers(String text, boolean global)
		{
			this.text = text;
			this.global = global;
		}

		public String getText(City city)
		{
			return this.text + " in " + city.getUrl();
		}
	}
}
