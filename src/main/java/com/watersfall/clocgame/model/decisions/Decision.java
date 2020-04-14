package com.watersfall.clocgame.model.decisions;

import lombok.Getter;

import java.util.ArrayList;

public enum Decision
{
	FREE_MONEY_COMMUNIST("Free Money",
			"Acquire some free money, the socialist way",
			"Get Rich",
			DecisionCategory.ECONOMY),
	FREE_MONEY_CAPITALIST("Free Money",
			"Acquire some free money, the free market war",
			"Get Rich",
			DecisionCategory.ECONOMY),

	INCREASE_ARREST_QUOTAS("Increase Arrest Quotas",
			"Tell your police force they need to arrest more criminals! Increases stability, but lowers approval and makes your government more authoritarian.",
			"Crackdown",
			DecisionCategory.DOMESTIC),
	PARDON_CRIMINALS("Parden Petty Criminals",
			"Release the jaywalkers from their cells! Decreases stability, but increases approval and makes your government less authoritarian.",
			"Free",
			DecisionCategory.DOMESTIC),
	PROPAGANDA("Propaganda Campaign",
			"Put up posters saying how great you are! Increases approval. Cost is based on current GDP and approval.",
			"Propaganda",
			DecisionCategory.DOMESTIC),
	WAR_PROPAGANDA("War Propaganda",
			"Rallying war speeches will make anyone love you, right? Increases approval, but only available when at war. Cost is based on GDP and approval.",
			"Rally",
			DecisionCategory.DOMESTIC),
	LAND_CLEARANCE("Land Clearance",
			"Slash and burn some useless jungle to make room for our expanding economy.",
			"Burn",
			DecisionCategory.DOMESTIC),

	ALIGN_ENTENTE("Align with the Entente",
			"Praise France's Democracy, hoping to make them like you. Officially aligns us with the Entente.",
			"Priase",
			DecisionCategory.FOREIGN),
	ALIGN_CENTRAL_POWERS("Align with the Central Powers",
			"Admire the German <i>Stahlhelm</i>, hoping to protect yourself from shrapnel. Officially aligns us with the Central Powers.",
			"Admire",
			DecisionCategory.FOREIGN),
	ALIGN_NEUTRAL("Declare Neutrality",
			"Go out on stage and celebrate your people's strength!",
			"Celebrate",
			DecisionCategory.FOREIGN),
	FORM_TREATY("Form Treaty",
			"Create an international alliance to keep yourself safe.",
			"Create",
			DecisionCategory.FOREIGN),

	CONSCRIPT("Conscript",
			"Increase the size of your army by 2,000",
			"Draft",
			DecisionCategory.MILITARY),
	DECONSCRIPT("Deconscript",
			"Decrease the size of your army by 2,000",
			"Fire",
			DecisionCategory.MILITARY),
	TRAIN("Train",
			"Train your army",
			"Train",
			DecisionCategory.MILITARY),
	FORTIFY("Fortify",
			"Increases fortification level by 10% of your max possible fortification, cost is based on max fortification level",
			"Fortify",
			DecisionCategory.MILITARY);


	private @Getter String name;
	private @Getter String description;
	private @Getter String buttonText;
	private @Getter DecisionCategory category;
	Decision(String name, String description, String buttonText, DecisionCategory category)
	{
		this.name = name;
		this.description = description;
		this.buttonText = buttonText;
		this.category = category;
	}

	public static ArrayList<Decision> getDecisionsByCategory(DecisionCategory category)
	{
		ArrayList<Decision> list = new ArrayList<>();
		for(Decision decision : Decision.values())
		{
			if(decision.getCategory() == category || category == DecisionCategory.ALL)
			{
				list.add(decision);
			}
		}
		return list;
	}
}
