function toggle(id)
{
	let div = document.getElementById(id);
	if(div.style.display === "")
	{
		div.style.display = "block";
	}
	else
	{
		div.style.display = "";
	}
}

function toggleTab(tab)
{
	let tabs = ["Budget", "Food", "Coal", "Iron", "Oil", "Steel", "Nitrogen", "Research", "Approval", "Stability", "Land", "Population", "Growth", "Manpower", "Equipment"];
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
}