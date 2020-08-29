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