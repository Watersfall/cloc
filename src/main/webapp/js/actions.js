function policy(policy)
{
	document.getElementById('resultsContainer').style.visibility = "visible";
	document.getElementById("result").innerHTML = "<p>Loading...</p>";
	var xhttp = new XMLHttpRequest();
	xhttp.open("POST", "/policy/" + policy, true);
	xhttp.send();
	xhttp.onreadystatechange = function()
	{
		if(xhttp.readyState === 4 && xhttp.status === 200)
		{
			document.getElementById("result").innerHTML = xhttp.responseText;
		}
	};
}

function cityPolicy(policy, cityId)
{
	document.getElementById('resultsContainer').style.visibility = "visible";
	document.getElementById("result").innerHTML = "<p>Loading...</p>";
	var xhttp = new XMLHttpRequest();
	xhttp.open("POST", "/policy/city/" + policy + "?city=" + cityId, true);
	xhttp.send();
	xhttp.onreadystatechange = function()
	{
		if(xhttp.readyState === 4 && xhttp.status === 200)
		{
			document.getElementById("result").innerHTML = xhttp.responseText;
		}
	};
}

function send(action, amount, id)
{
	document.getElementById('resultsContainer').style.visibility = "visible";
	document.getElementById("result").innerHTML = "<p>Loading...</p>";
	var xhttp = new XMLHttpRequest();
	var params;
	if(action === null)
	{
		params = "action=" + action + "&id=" + id;
	}
	else
	{
		params = "action=" + action + "&amount=" + amount + "&id=" + id;
	}
	xhttp.open("POST", "/nation.do", true);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send(params);
	xhttp.onreadystatechange = function()
	{
		if(xhttp.readyState === 4 && xhttp.status === 200)
		{
			document.getElementById("result").innerHTML = xhttp.responseText;
		}
	};
}

function declareWar(id)
{
	document.getElementById('resultsContainer').style.visibility = "visible";
	document.getElementById("result").innerHTML = "<p>Loading...</p>";
	var xhttp = new XMLHttpRequest();
	var params = "action=war&id=" + id;
	xhttp.open("POST", "/nation.do", true);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send(params);
	xhttp.onreadystatechange = function()
	{
		if(xhttp.readyState === 4 && xhttp.status === 200)
		{
			document.getElementById("result").innerHTML = xhttp.responseText;
			if(!(xhttp.responseText.indexOf("can not") > 0))
			{
				document.getElementById("decc").style.display = "none";
				document.getElementById("land").style.display = "inline";
			}
		}
	};
}

function settings(name, value)
{
	document.getElementById('resultsContainer').style.visibility = "visible";
	document.getElementById("result").innerHTML = "<p>Loading...</p>";
	var xhttp = new XMLHttpRequest();
	var params = "action=" + name + "&" + name + "=" + value;
	xhttp.open("POST", "/settings.do", true);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send(params);
	xhttp.onreadystatechange = function()
	{
		if(xhttp.readyState === 4 && xhttp.status === 200)
		{
			document.getElementById("result").innerHTML = xhttp.responseText;
		}
	};
}

function settingsAll(flag, portrait, nationTitle, leaderTitle, description)
{
	document.getElementById('resultsContainer').style.visibility = "visible";
	document.getElementById("result").innerHTML = "<p>Loading...</p>";
	var xhttp = new XMLHttpRequest();
	var params = "action=all" + "&flag=" + flag +
		"&portrait=" + portrait + "&nationTitle=" + nationTitle +
		"&leaderTitle=" + leaderTitle + "&description=" + description;
	xhttp.open("POST", "/settings.do", true);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send(params);
	xhttp.onreadystatechange = function()
	{
		if(xhttp.readyState === 4 && xhttp.status === 200)
		{
			document.getElementById("result").innerHTML = xhttp.responseText;
		}
	};
}

function decision(policy)
{
	document.getElementById('resultsContainer').style.visibility = "visible";
	document.getElementById("result").innerHTML = "<p>Loading...<p>";
	let xhttp = new XMLHttpRequest();
	let params = 'selection=' + document.getElementById(policy).selectedIndex + '&decision=' + policy;
	xhttp.open('POST', '/decisions.do', true);
	xhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	xhttp.send(params);
	xhttp.onreadystatechange = function()
	{
		if(xhttp.readyState === 4 && xhttp.status === 200)
		{
			document.getElementById("result").innerHTML = xhttp.responseText;
		}
	};
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
	document.getElementById('resultsContainer').style.visibility = "visible";
	document.getElementById("result").innerHTML = "<p>Loading...</p>";
	var xhttp = new XMLHttpRequest();
	var params = "name=" + name;
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
	document.getElementById('resultsContainer').style.visibility = "visible";
	document.getElementById("result").innerHTML = "<p>Loading...</p>";
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
	document.getElementById('resultsContainer').style.visibility = "visible";
	document.getElementById("result").innerHTML = "<p>Loading...</p>";
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
	document.getElementById('resultsContainer').style.visibility = "visible";
	document.getElementById("result").innerHTML = "<p>Loading...</p>";
	let xhttp = new XMLHttpRequest();
	let params = "attribute=" + attribute + "&value=" + value;
	xhttp.open("POST", "treaty.do", true);
	xhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	xhttp.send(params);
	xhttp.onreadystatechange = function()
	{
		if(xhttp.readyState === 4 && xhttp.status === 200)
		{
			document.getElementById("result").innerHTML = xhttp.responseText;
		}
	};
}