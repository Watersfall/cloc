function ajax(url, params)
{
	let xhttp = new XMLHttpRequest();
	xhttp.open("POST", url, true);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	if(params != null)
	{
		xhttp.send(params);
	}
	else
	{
		xhttp.send();
	}
	xhttp.onreadystatechange = function()
	{
		if(xhttp.readyState === 4 && xhttp.status === 200)
		{
			document.getElementById("result").innerHTML = xhttp.responseText;
			return xhttp.responseText;
		}
	};
}

function displayResults()
{
	document.getElementById('resultsContainer').style.display = "block";
	document.getElementById("result").innerHTML = "<p>Loading...</p>";
}

function decision(policy)
{
	displayResults();
	let url = "/decision/" + policy;
	ajax(url, null);
}

function cityDecision(policy, cityId)
{
	displayResults();
	let url = "/decision/city/" + policy;
	let params = "city=" + cityId;
	ajax(url, params);
}

function send(action, amount, id)
{
	displayResults();
	let url = "nation.do";
	let params;
	if(action === null)
	{
		params = "action=" + action + "&id=" + id;
	}
	else
	{
		params = "action=" + action + "&amount=" + amount + "&id=" + id;
	}
	ajax(url, params);
}

function declareWar(id)
{
	displayResults();
	let url = "nation.do";
	let params = "action=war&id=" + id;
	ajax(url, params);
}

function settings(name, value)
{
	displayResults();
	let url = "settings.do";
	let params = "action=" + name + "&" + name + "=" + value;
	ajax(url, params);
}

function settingsAll(flag, portrait, nationTitle, leaderTitle, description)
{
	displayResults();
	let url = "settings.do";
	let params = "action=all" + "&flag=" + flag +
		"&portrait=" + portrait + "&nationTitle=" + nationTitle +
		"&leaderTitle=" + leaderTitle + "&description=" + description;
	ajax(url, params);
}

function policy(policy)
{
	displayResults();
	let url = "policy.do";
	let params = 'selection=' + document.getElementById(policy).selectedIndex + '&policy=' + policy;
	ajax(url, params);
}

function updateDesc(name)
{
	let elements = document.getElementsByClassName("optionDesc");
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
	let xhttp = new XMLHttpRequest();
	let params = "name=" + name;
	xhttp.open("POST", "createtreaty.do", true);
	xhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	xhttp.send(params);
	xhttp.onreadystatechange = function()
	{
		if(xhttp.readyState === 4 && xhttp.status === 200)
		{
			document.getElementById("result").innerHTML = xhttp.responseText;
		}
		if(xhttp.readyState === 4 && xhttp.status === 201)
		{
			window.location.href = "treaty.jsp?id=" + xhttp.responseText;
		}
	};
}

function login(user, pass)
{
	displayResults();
	let xhttp = new XMLHttpRequest();
	let params = "username=" + user + "&password=" + pass;
	xhttp.open("POST", "login.do", true);
	xhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	xhttp.send(params);
	xhttp.onreadystatechange = function()
	{
		if(xhttp.readyState === 4 && xhttp.status === 200)
		{
			if(xhttp.responseText != "")
			{
				document.getElementById("result").innerHTML = xhttp.responseText;
			}
			else
			{
				location.reload();
			}
		}
	};
}

function research(tech)
{
	displayResults();
	let xhttp = new XMLHttpRequest();
	let params = "tech=" + tech;
	xhttp.open("POST", "technology.do", true);
	xhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	xhttp.send(params);
	xhttp.onreadystatechange = function()
	{
		if(xhttp.readyState === 4 && xhttp.status === 200)
		{
			document.getElementById("result").innerHTML = xhttp.responseText;
			updateTechs();
		}
	};
}

function updateTechs()
{
	let url = new URL(window.location.href);
	let params = "";
	if(url.searchParams.get("category") !== null)
	{
		params = "category=" + url.searchParams.get("category");
	}
	let xhttp = new XMLHttpRequest();
	xhttp.open("GET", "techtree.jsp?" + params, true);
	xhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	xhttp.send();
	xhttp.onreadystatechange = function()
	{
		if(xhttp.readyState === 4 && xhttp.status === 200)
		{
			document.getElementById("techTree").innerHTML = xhttp.responseText;
		}
	};
}

function updateTreaty(attribute, value)
{
	displayResults();
	let url = "treaty.do";
	let params = "attribute=" + attribute + "&value=" + value;
	ajax(url, params);
}

function postDeclaration()
{
	displayResults();
	let message = document.getElementById("post").value;
	let url = "declarations.do";
	let params = "message=" + message;
	ajax(url, params);
}

function newsDelete(id)
{
	displayResults();
	let url = "news.do";
	let params = "delete=" + id;
	ajax(url, params);
}