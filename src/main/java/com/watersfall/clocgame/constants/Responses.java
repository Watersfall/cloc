package com.watersfall.clocgame.constants;

public class Responses
{

	/*
	 ** ERRORS
	 */

	public static String noLogin()
	{
		return "<p>You must be logged in to do this!</p>";
	}

	public static String notYourNation()
	{
		return "<p>Not your nation!</p>";
	}

	public static String notYourTreaty()
	{
		return "<p>Not your treaty!</p>";
	}

	public static String notYourCity()
	{
		return "<p>Not your city!</p>";
	}

	public static String notYourArmy()
	{
		return "<p>Not your army!</p>";
	}

	public static String genericError()
	{
		return "<p>Try again stupid</p>";
	}

	public static String genericException(Exception e)
	{
		return "<p>Error: " + e.getLocalizedMessage() + "!</p>";
	}

	public static String noNation()
	{
		return "<p>Nation does not exist!</p>";
	}

	public static String noCity()
	{
		return "<p>City does not exist!</p>";
	}

	public static String tooLong()
	{
		return "<p>Name must be less than 32 characters!</p>";
	}

	public static String negative()
	{
		return "<p>You cannot give 0 or less!</p>";
	}

	public static String missingPrerequisite()
	{
		return "<p>You cannot research this technology!</p>";
	}

	public static String noPermission()
	{
		return "<p>You do not have permission to do this!</p>";
	}

	public static String nameTaken()
	{
		return "<p>That username or nation name is already being used!</p>";
	}


	/*
	 ** MISSING REQUIREMENTS
	 */

	public static String nullFields()
	{
		return "<p>Please fill out all fields!</p>";
	}

	public static String noMoney()
	{
		return "<p>You do not have enough money!</p>";
	}

	public static String noIron()
	{
		return "<p>You do not have enough iron!<p>";
	}

	public static String noCoal()
	{
		return "<p>You do not have enough coal!</p>";
	}

	public static String noSteel()
	{
		return "<p>You do not have enough steel!</p>";
	}

	public static String noNitrogen()
	{
		return "<p>You do not have enough nitrogen!</p>";
	}

	public static String noResearch()
	{
		return "<p>You do not have enough research!</p>";
	}

	public static String noManpower()
	{
		return "<p>You do not have enough manpower!</p>";
	}

	public static String noTroopsForAttack()
	{
		return "<p>You do not have enough troops to attack!</p>";
	}

	public static String noTroops()
	{
		return "<p>You can not deconscript further!</p>";
	}

	public static String no(String field)
	{
		switch(field)
		{
			case "budget":
				return noMoney();
			case "iron":
				return noIron();
			case "coal":
				return noCoal();
			case "steel":
				return noSteel();
			case "nitrogen":
				return noNitrogen();
			case"research":
				return noResearch();
			default:
				return genericError();
		}
	}

	public static String notEnough()
	{
		return "<p>You do not have enough to send!</p>";
	}

	public static String notCoastal()
	{
		return "<p>City is not on the coast!</p>";
	}

	public static String tooLong(String field, int characters)
	{
		return "<p>" + field + " must be less than " + characters + " characters!";
	}

	public static String limit()
	{
		return "<p>You can not build any more of these!</p>";
	}

	public static String invalidLogin()
	{
		return "<p>Incorrect username or password!</p>";
	}

	public static String noCriminals()
	{
		return "<p>There are no more jaywalkers to arrest!</p>";
	}

	public static String hated()
	{
		return "<p>You are not popular enough to do this</p>";
	}

	public static String noPrisoners()
	{
		return "<p>You do not have more prisoners to free</p>";
	}

	public static String unstable()
	{
		return "<p>You are not stable enough to do this!</p>";
	}

	public static String alreadyYourAlignment()
	{
		return "<p>That is already your alignment!</p>";
	}

	public static String noneLeft()
	{
		return "<p>You do not have any left to remove!</p>";
	}

	public static String fullTrained()
	{
		return "<p>Your army is already fully trained!</p>";
	}

	public static String noWar()
	{
		return "<p>You are not at war with this nation!</p>";
	}

	public static String alreadyAttacked()
	{
		return "<p>Your army is still preparing!</p>";
	}

	public static String noLand()
	{
		return "<p>You do not have enough land!</p>";
	}

	public static String noChange()
	{
		return "<p>You can not change this yet!</p>";
	}

	public static String policySame()
	{
		return "<p>You already have this policy set!</p>";
	}

	public static String policyNoWar()
	{
		return "<p>You must be at war to select this!</p>";
	}

	public static String policyNoPeace()
	{
		return "<p>You can not select this when at war!</p>";
	}

	public static String alreadyHaveTech()
	{
		return "<p>You already have this technology!</p>";
	}

	public static String alreadyInvited()
	{
		return "<p>You have already invited this nation!</p>";
	}

	public static String noInvite()
	{
		return "<p>You do not have an invite!</p>";
	}

	public static String propagandaMaxApproval()
	{
		return "<p>You're people already love you! There is no need to convince them further!</p>";
	}

	public static String propagandaNoWar()
	{
		return "<p>You cannot do war propaganda while not at war!<p>";
	}

	/*
	 ** RESPONSES
	 */

	public static String loggedIn()
	{
		return "<p>Logged in!</p>";
	}

	public static String registered()
	{
		return "<p>Registered!</p>";
	}

	public static String sent()
	{
		return "<p>Sent!</p>";
	}

	public static String cannotWar(String cause)
	{
		switch(cause)
		{
			case "germanProtection":
				return "<p>This nation is currently under the protection of The German Empire! To attack them would be suicide!</p>";
			case "frenchProtection":
				return "<p>This nation is currently under the protection of the French! To attack would be suicide!</p>";
			case "neutralProtection":
				return "<p>Generic war protection message!</p>";
			case "youGermanProtection":
				return "<p>You are currently under the protection of The German Empire! They refuse to let you attack!</p>";
			case "youFrenchProtection":
				return "<p>You are currently under the protection of The French! They refuse to let you attack!</p>";
			case "youNeutralProtection":
				return "<p>Generic you are under war protection message!</p>";
			case "noBorder":
				return "<p>Not in your region or a bordering region!</p>";
			case "alreadyAtWar":
				return "<p>You are already at war with another nation!</p>";
			case "alreadyAtWar2":
				return "<p>They are already in a defensive war!</p>";
			default:
				return null;
		}
	}

	public static String war()
	{
		return "<p>You have declared war!</p>";
	}

	public static String freeMoneyCapitalist()
	{
		return "<p>You cut the pay and benefits for government employees to fund your newest projects!</p>";
	}

	public static String freeMoneyCommunist()
	{
		return "<p>You raise taxes by 1% to fund your newest projects!</p>";
	}

	public static String coalMine()
	{
		return "<p>You dig a new coal mine!</p>";
	}

	public static String ironMine()
	{
		return "<p>You dig a new iron mine!</p>";
	}

	public static String drill()
	{
		return "<p>You drill a new oil well!</p>";
	}

	public static String industrialize()
	{
		return "<p>Your farmers flock to the city for a new life!</p>";
	}

	public static String militarize()
	{
		return "<p>Your farmers flock to the city for a new life!</p>";
	}

	public static String nitrogenPlant()
	{
		return "<p>Your farmers flock to the city for a new life!</p>";
	}

	public static String university()
	{
		return "<p>You build a new University!</p>";
	}

	public static String railway()
	{
		return "<p>You build a new railway!</p>";
	}

	public static String port()
	{
		return "<p>You build a new port!</p>";
	}

	public static String barrack()
	{
		return "<p>You build the army some shiny new barracks!<p>";
	}

	public static String artillery()
	{
		return "<p>You build some new artillery guns!</p>";
	}

	public static String weapons()
	{
		return "<p>You manufacture some new uniforms and rifles for your conscripts!</p>";
	}

	public static String arrest()
	{
		return "<p>Your police force arrests every petty criminal they could find!</p>";
	}

	public static String free()
	{
		return "<p>Your convicts enjoy their freedom!</p>";
	}

	public static String landClearance(int amount)
	{
		return "<p>You peacefully annexed " + amount + "km<sup>2</sup> of land from the natives!</p>";
	}

	public static String align(int align)
	{
		switch(align)
		{
			case 1:
				return "<p>You align yourself with the Entente!</p>";
			case 0:
				return "<p>Your people cheer as you declare your neutrality!</p>";
			case -1:
				return "<p>You align yourself with the Central Powers!</p>";
			default:
				return "<p>What did you do?</p>";
		}
	}

	public static String close(String type)
	{
		return "<p>You close down a " + type + " and thousands of workers lose their jobs!</p>";
	}

	public static String closeN(String type)
	{
		return "<p>You close down an " + type + " and thousands of workers lose their jobs!</p>";
	}

	public static String closePort()
	{
		return "<p>You close down a port and thousands of boats lose their homes!</p>";
	}

	public static String closeBarrack()
	{
		return "<p>You close down a barrack and thousands of soldiers lose their homes!</p>";
	}

	public static String closeRailroad()
	{
		return "<p>You tear up a railroad and thousands of railroad cars lose their homes!</p>";
	}

	public static String conscript()
	{
		return "<p>You conscript 2000 soldiers!</p>";
	}

	public static String deconscript()
	{
		return "<p>You fire off thousands of soldiers!</p>";
	}

	public static String train()
	{
		return "<p>You train your army!</p>";
	}

	public static String offensiveVictory(int attacker, int defender)
	{
		return "<p>Victory! You have killed " + defender + "k enemy soldiers while suffering " + attacker+ "k casualties!</p>";
	}

	public static String offensiveDefeat(int attacker, int defender)
	{
		return "<p>Defeat! You have lost " + attacker+ "k soldiers while only killing " + defender + "k enemy troops!</p>";
	}

	public static String warWon()
	{
		return "<p>You have won the war, winning absolutely nothing because I haven't written that part yet!</p>";
	}

	public static String updated(String field)
	{
		return "<p>" + field + " updated!</p>";
	}

	public static String updated()
	{
		return "<p>Updated!</p>";
	}

	public static String policyUpdated()
	{
		return "<p>Policy updated!</p>";
	}

	public static String researchSucceeded()
	{
		return "<p>Researched!</p>";
	}

	public static String researchFailed()
	{
		return "<p>You have failed to make progress</p>";
	}

	public static String invited()
	{
		return "<p>Invite sent!</p>";
	}

	public static String kicked()
	{
		return "<p>Member kicked!</p>";
	}

	public static String resigned()
	{
		return "<p>Resigned!</p>";
	}

	public static String inviteAccepted()
	{
		return "<p>Accepted!</p>";
	}

	public static String inviteRejected()
	{
		return "<p>Rejected!</p>";
	}

	public static String propaganda()
	{
		return "<p>You run a successful propaganda campaign demonizing a neighboring country, and idolizing your leader!</p>";
	}

	public static String declaration()
	{
		return "<p>Declaration posted!</p>";
	}
}
