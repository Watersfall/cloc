function toggle(id)
{
	let div = document.getElementById(id);
	if(div.style.display === "none")
	{
		div.style.display = "block";
	}
	else
	{
		div.style.display = "none";
	}
}

function toggleTab(tab)
{
	let tabs = ["budget", "food", "coal", "iron", "oil", "steel", "nitrogen", "research", "approval", "stability", "land", "population", "growth", "manpower"];
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
			let tab = document.getElementById(tabs[i]);
			if(tab != null)
			{
				tab.style.display = "";
			}
		}
	}
}