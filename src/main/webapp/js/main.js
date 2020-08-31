function ajax(url, params, callback, method)
{
	let xhttp = new XMLHttpRequest();
	if(method === undefined)
	{
		method = "POST";
	}
	xhttp.open(method, url, true);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	if(params != null)
	{
		xhttp.send(params);
	}
	else
	{
		xhttp.send();
	}
	if(callback === null || callback === undefined)
	{
		displayResults();
		xhttp.onreadystatechange = function()
		{
			if(xhttp.readyState === 4 && xhttp.status === 200)
			{
				document.getElementById("results_content").innerHTML = xhttp.responseText;
				return xhttp.responseText;
			}
		};
	}
	else
	{
		xhttp.onreadystatechange = callback;
	}
}

function displayResults()
{
	document.getElementById('results').style.display = "block";
	document.getElementById("results_content").innerHTML = "<p>Loading...</p>";
}


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

function toggleUITab(tab)
{
	let tabs = ["budget_production", "food_production", "coal_production", "iron_production", "oil_production",
		"steel_production", "nitrogen_production", "research_production", "Approval", "Stability", "Land", "Population", "Growth",
		"Manpower", "Equipment", "Bombers", "Fighters", "Recon"];
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
		toggle(tab);
	}
}

function anyParentIsToggleTab(element)
{
	do
	{
		if(element.onclick == null)
		{
			element = element.parentElement;
		}
		else if(element.onclick.toString().indexOf("toggleUITab(") !== -1)
		{
			return true;
		}
		else
		{
			element = element.parentElement;
		}
	}
	while(element);
	return false;
}

function login()
{
	let user = document.getElementById("username").value;
	let pass = document.getElementById("password").value;
	let url = "/login/";
	let params = "username=" + user + "&password=" + pass;
	let callback = function()
	{
		if(this.readyState === 4 && this.status === 200)
		{
			if(this.responseText !== "")
			{
				let errorDiv = document.getElementById("login_error");
				errorDiv.style.display = "block";
				errorDiv.innerHTML = this.responseText;
			}
			else
			{
				location.reload();
			}
		}
	};
	ajax(url, params, callback);
}

function cityDecision(decision, cityId)
{
	let url = "/decision/city/" + cityId + "/" + decision;
	ajax(url, null);
}

function decision(decision)
{
	if(decision === "FORM_TREATY")
	{
		window.location.href = "/createtreaty/";
	}
	else
	{
		let url = "/decision/" + decision;
		ajax(url, null);
	}
}

function policy(policy)
{
	let url = "/policy/" + policy;
	let params = 'selection=' + document.getElementById(policy).value;
	ajax(url, params);
}

function policyDesc(policyId)
{
	let policy = document.getElementById(policyId);
	let selection = policy.options[policy.selectedIndex].value;
	for(let i = 0; i < policy.options.length; i++)
	{
		let element = document.getElementById(policy.options[i].value + "_DESC");
		element.style.display = 'none';
	}
	let element = document.getElementById(selection + "_DESC");
	element.style.display = 'block';
}

function deleteNews(id)
{
	let url = "/news/";
	let params = "delete=" + id;
	ajax(url, params);
}

function deleteMessage(id)
{
	let url = "/messages/";
	let params = "delete=" + id;
	ajax(url, params);
}

/*
 * Production
 */

const FACTORY = document.createElement("img", {"src": "/images/production/factory.svg"});
const BLANK = document.createElement("img");
let freeFactories = 0;

function setFreeFactories(num)
{
	freeFactories = num;
	document.getElementById("free_factories").innerText = freeFactories;
}

function setFactories(id, amount)
{
	FACTORY.src = "/images/production/factory.svg";
	FACTORY.alt = "factory";
	BLANK.src = "/images/production/blank.svg";
	BLANK.alt = "blank";
	let production = document.getElementById("factories_" + id);
	let currentFactoryCount = 0;
	for(let i = 0; i < 15; i++)
	{
		if(production.children.item(i).children.item(0).src.indexOf("factory") > 0)
		{
			currentFactoryCount++;
		}
		else
		{
			break;
		}
	}
	let max = freeFactories + currentFactoryCount;
	for(let i = 0; i < amount; i++)
	{
		if(i < max)
		{
			production.children.item(i).children.item(0).replaceWith(FACTORY.cloneNode());
		}
		else
		{
			amount = i;
			break;
		}
	}
	for(let i = amount; i < 15; i++)
	{
		production.children.item(i).children.item(0).replaceWith(BLANK.cloneNode());
	}
	setFreeFactories(freeFactories - (amount - currentFactoryCount));
}

function updateProduction(id)
{
	let production = document.getElementById("factories_" + id);
	let factories = 0;
	for(let i = 0; i < 15; i++)
	{
		if(production.children.item(i).children.item(0).src.indexOf("factory") > 0)
		{
			factories++;
		}
		else
		{
			break;
		}
	}
	let select = document.getElementById("select_" + id);
	let name = select.options[select.selectedIndex].value;
	let url = "/production/" + id;
	let params = "action=update&factories=" + factories + "&production=" + name;
	let callback = function()
	{
		displayResults();
		if(this.readyState === 4 && this.status === 200)
		{
			document.getElementById("results_content").innerHTML = this.responseText;
			if(this.responseText.indexOf("Updated") !== -1)
			{
				reloadProduction(id);
			}
		}
	};
	ajax(url, params, callback);
}

function reloadProduction(id)
{
	let url = "/production/" + id;
	let element = document.getElementById("production_" + id);
	let callback = function()
	{
		if(this.readyState === 4 && this.status === 200)
		{
			let placeholder = document.createElement("div");
			placeholder.innerHTML = this.responseText;
			element.replaceWith(placeholder);
		}
	};
	ajax(url, null, callback, "GET");
}

function deleteProduction(id)
{
	displayResults();
	let div = document.getElementById("production_" + id);
	let factoryDiv = document.getElementById("factories_" + id);
	let factories = 0;
	for(let i = 0; i < 15; i++)
	{
		if(factoryDiv.children.item(i).children.item(0).src.indexOf("factory") > 0)
		{
			factories++;
		}
		else
		{
			break;
		}
	}
	let url = "/production/" + id;
	let params = "action=delete";
	let callback = function()
	{
		if(this.readyState === 4 && this.status === 200)
		{
			document.getElementById("results_content").innerHTML = this.responseText;
			if(this.responseText.indexOf("Deleted") !== -1)
			{
				freeFactories += factories;
				setFreeFactories(freeFactories);
				div.remove();
			}
		}
	};
	ajax(url, params, callback);
}

function createNewProduction()
{
	displayResults();
	let url = "/production/0";
	let params = "action=new";
	let callback = function()
	{
		if(this.readyState === 4 && this.status === 200)
		{
			document.getElementById("results_content").innerHTML = this.responseText;
			if(!Number.isNaN(this.responseText))
			{
				let id = this.responseText;
				freeFactories -= 1;
				setFreeFactories(freeFactories);
				let url = "/production/" + this.responseText;
				let callback = function()
				{
					if(this.readyState === 4 && this.status === 200)
					{
						let placeholder = document.createElement("div");
						placeholder.innerHTML = this.responseText;
						document.getElementsByClassName("column").item(id % 2).appendChild(placeholder);
					}
				};
				ajax(url, null, callback, "GET");
			}
		}
	};
	ajax(url, params, callback);
}

function loadTech(tech)
{
	let url = "/tech/" + tech;
	let callback = function() {
		if(this.readyState === 4 && this.status === 200)
		{
			document.getElementById("tech").style.display = "";
			document.getElementById("tech").innerHTML = this.responseText;
		}
	};
	ajax(url, null, callback, "GET");
}

function research(tech, category)
{
	displayResults();
	let url = "/technology/";
	let params = "tech=" + tech;
	let callback = function() {
		if(this.readyState === 4 && this.status === 200)
		{
			document.getElementById("results_content").innerHTML = this.responseText;
			if(this.responseText.indexOf("Researched") >= 0)
			{
				let url = "/techtree/" + category;
				let callback = function ()
				{
					if(this.readyState === 4 && this.status === 200)
					{
						let element = document.getElementById("tech_tree");
						let placeholder = document.createElement("div");
						placeholder.className = "tech_tree";
						placeholder.id = "tech_tree";
						placeholder.innerHTML = this.responseText;
						element.replaceWith(placeholder);
					}
				};
				ajax(url, null, callback, "GET");
			}
		}
	};
	ajax(url, params, callback);
}

function postDeclaration(content)
{
	let url = "/declarations/";
	let params = "message=" + content;
	ajax(url, params);
}

function updateTreaty(attribute, value)
{
	let url = "/treaty/";
	let params = "attribute=" + attribute + "&value=" + value;
	ajax(url, params);
}

function settings(attribute, value)
{
	let url = "/settings/";
	let params = "action=" + attribute + "&value=" + value;
	ajax(url, params);
}