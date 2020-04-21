let context = "";

function setContext(newContext)
{
	context = newContext;
}

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
				document.getElementById("result").innerHTML = xhttp.responseText;
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
	document.getElementById('resultsContainer').style.display = "block";
	document.getElementById("result").innerHTML = "<p>Loading...</p>";
}

function decision(policy)
{
	if(policy === "FORM_TREATY")
	{
		window.location.href = "/createtreaty/";
	}
	else
	{
		let url = context + "/decision/" + policy;
		ajax(url, null);
	}
}

function cityDecision(policy, cityId)
{
	let url = context + "/decision/city/" + cityId + "/" + policy;
	ajax(url, null);
}

function farm(action, increase)
{
	let url = context + "/countryside/" + action;
	let params = "increase=" + increase;
	ajax(url, params);
}

function send(action, amount, id)
{
	let url = context + "/nation/" + id;
	let params;
	if(amount === null)
	{
		params = "action=" + action;
	}
	else
	{
		params = "action=" + action + "&amount=" + amount;
	}
	ajax(url, params);
}

function declareWar(id)
{
	let url = context + "/nation/" + id;
	let params = "action=war";
	ajax(url, params);
}

function settings(name, value)
{
	let url = context + "/settings/";
	let params = "action=" + name + "&" + name + "=" + value;
	ajax(url, params);
}

function settingsAll(nationTitle, leaderTitle, description)
{
	let url = context + "/settings/";
	let params = "action=all" + "&nationTitle=" + nationTitle +
		"&leaderTitle=" + leaderTitle + "&description=" + description;
	ajax(url, params);
}

function policy(policy)
{
	let url = context + "/policy/" + policy;
	let params = 'selection=' + document.getElementById("policy_" + policy).value;
	ajax(url, params);
}

function updateDesc(name)
{
	let elements = document.getElementsByClassName("desc");
	let selected = document.getElementById(name);
	let updateId = selected.options[selected.selectedIndex].id + "_desc";
	for(let i = 0; i < elements.length; i++)
	{
		if(elements[i].id.startsWith(name))
		{
			elements[i].style.display = 'none';

			if(elements[i].id === updateId)
			{
				elements[i].style.display = 'block';
			}
		}
	}
}

function createTreaty(name)
{
	displayResults();
	let url = context + "/createtreaty/";
	let params = "name=" + name;
	let callback = function()
	{
		if(this.readyState === 4 && this.status === 200)
		{
			document.getElementById("result").innerHTML = this.responseText;
		}
		if(this.readyState === 4 && this.status === 201)
		{
			window.location.href = "/treaty/" + this.responseText;
		}
	};
	ajax(url, params, callback);
}

function login()
{
	let user = document.getElementById("username").value;
	let pass = document.getElementById("password").value;
	let url = context + "/login/";
	let params = "username=" + user + "&password=" + pass;
	let callback = function()
	{
		if(this.readyState === 4 && this.status === 200)
		{
			if(this.responseText != "")
			{
				toggle("login_error_div");
				document.getElementById("login_error").innerHTML = this.responseText;
			}
			else
			{
				location.reload();
			}
		}
	};
	ajax(url, params, callback);
}

function research(tech)
{
	displayResults();
	let params = "tech=" + tech;
	let url = context + "/technology/";
	let callback = function()
	{
		if(this.readyState === 4 && this.status === 200)
		{
			document.getElementById("result").innerHTML = this.responseText;
			updateTechs();
		}
	};
	ajax(url, params, callback);
}

function updateTechs()
{
	let url = new URL(window.location.href);
	if(url.pathname.indexOf("/tree/") !== -1)
	{
		let category = url.pathname.substring(url.pathname.indexOf("/tree/") + 6);
		let url = context + "/techtree/" + category;
		let callback = function()
		{
			if(this.readyState === 4 && this.status === 200)
			{
				document.getElementById("techTree").innerHTML = this.responseText;
			}
		};
		ajax(url, null, callback());
	}
}

function updateTreaty(attribute, value)
{
	let url = context + "/treaty/";
	let params = "attribute=" + attribute + "&value=" + value;
	ajax(url, params);
}

function postDeclaration()
{
	let message = document.getElementById("post").value;
	let url = context + "/declarations/";
	let params = "message=" + message;
	ajax(url, params);
}

function createCity()
{
	let url = context + "/cities/new";
	let callback = function()
	{
		displayResults();
		this.onreadystatechange = function()
		{
			if(this.readyState === 4 && this.status === 200)
			{
				if(isNaN(this.responseText))
				{
					document.getElementById("result").innerHTML = this.responseText;
				}
				else
				{
					window.location.replace("/cities/" + this.responseText);
				}
			}
		};
	};
	ajax(url, null, callback);
}

function newsDelete(id)
{
	displayResults();
	let url = context + "/news/";
	let params = "delete=" + id;
	let callback = function()
	{
		if(this.readyState === 4 && this.status === 200)
		{
			document.getElementById("result").innerHTML = this.responseText;
			if(this.responseText.indexOf("Deleted") !== -1)
			{
				document.getElementById("news_" + id).remove();
			}
		}
	};
	ajax(url, params, callback);
}

function loadCity(id)
{
	let url = new URL(window.location.href);
	if(url.pathname.indexOf(id) === -1)
	{
		document.getElementById("city").innerHTML = "<p>Loading...</p>";
		let url = "/city/" + id;
		let callback = function()
		{
			if(this.readyState === 4 && this.status === 200)
			{
				document.getElementById("city").innerHTML = xhttp.responseText;
				window.history.replaceState("id: " + id, "", window.location.href.substring(0, window.location.href.indexOf("cities/") + 7) + id + "/");
			}
		};
		ajax(url, null, callback);
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
		else if(element.onclick.toString().indexOf("toggleTab(") !== -1)
		{
			console.log("onclick found");
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

function display()
{
	let panel = document.getElementById("admin");
	let button = document.getElementById("adminButton");
	if(panel.style.display === "")
	{
		panel.style.display = "block";
		button.innerHTML = "Hide Admin Actions";
	}
	else
	{
		panel.style.display = "";
		button.innerHTML = "Show Admin Actions";
	}
}

function stats()
{
	let panel = document.getElementById("stats");
	let button = document.getElementById("statsButton");
	if(panel.style.display === "")
	{
		panel.style.display = "block";
		button.innerHTML = "Hide Stats";
	}
	else
	{
		panel.style.display = "";
		button.innerHTML = "Show Stats";
	}
}

function tech(tech)
{
	displayResults();
	document.getElementById('resultsContainer').style.visibility = "visible";
	document.getElementById("result").innerHTML = "<p>Loading...</p>";
	let attempt = document.getElementById(tech);
	if(attempt == null)
	{
		document.getElementById("result").innerHTML = "<p>Don't do that!</p>";
	}
	else if(attempt.classList.contains("unavailable"))
	{
		document.getElementById("result").innerHTML = "<p>You can not research this technology!</p>";
	}
	else if(attempt.classList.contains("researched"))
	{
		document.getElementById("result").innerHTML = "<p>You already have this technology!</p>";
	}
	else
	{
		research(tech);
	}
}

function loadTech(tech)
{
	document.getElementById("tech").style.display = "block";
	let url = "/tech/" + tech;
	let callback = function()
	{
		if(this.readyState === 4 && this.status === 200)
		{
			document.getElementById("tech").innerHTML = this.responseText;
		}
	};
	ajax(url, null, callback);
}

function runEvent(id, response)
{
	let url = "/news/";
	let params = "event=" + id + "&event_action=" + response;
	ajax(url, params);
}