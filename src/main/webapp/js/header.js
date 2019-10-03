function showHidePolicies()
{
	let div = document.getElementById("policies");
	if(div.style.display === "none")
	{
		div.style.display = "block";
	}
	else
	{
		div.style.display = "none";
	}
}

function showHideWorld()
{
	let div = document.getElementById("world");
	if(div.style.display === "none")
	{
		div.style.display = "block";
	}
	else
	{
		div.style.display = "none";
	}
}

function showHideUser()
{
	let div = document.getElementById("user");
	if(div.style.display === "none")
	{
		div.style.display = "block";
	}
	else
	{
		div.style.display = "none";
	}
}

function showHideCities()
{
	let div = document.getElementById("cities");
	if(div.style.display === "none")
	{
		div.style.display = "block";
	}
	else
	{
		div.style.display = "none";
	}
}

function showHideTab(tab)
{
	let tabs = ["budget", "food", "coal", "iron", "oil", "steel", "nitrogen", "research", "approval", "stability", "land", "population", "growth"];
	if(tab !== null)
	{
		let div = document.getElementById(tab);
		if(div.style.display === "")
		{
			div.style.display = "block";
		}
		else
		{
			div.style.display = "";
		}
	}
	for(let i = 0; i < tabs.length; i++)
	{
		if(tabs[i] !== tab)
		{
			document.getElementById(tabs[i]).style.display = "";
		}
	}
}