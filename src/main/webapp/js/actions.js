let context = "";
let freeFactories = 0;

function setContext(newContext)
{
	context = newContext;
}

function setFreeFactories(newFreeFactories)
{
	freeFactories = newFreeFactories;
}

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
	let url = context + "/decision/" + policy;
	ajax(url, null);
}

function cityDecision(policy, cityId)
{
	displayResults();
	let url = context + "/decision/city/" + cityId + "/" + policy;
	ajax(url, null);
}

function send(action, amount, id)
{
	displayResults();
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
	displayResults();
	let url = context + "/nation/" + id;
	let params = "action=war";
	ajax(url, params);
}

function settings(name, value)
{
	displayResults();
	let url = context + "/settings/";
	let params = "action=" + name + "&" + name + "=" + value;
	ajax(url, params);
}

function settingsAll(flag, portrait, nationTitle, leaderTitle, description)
{
	displayResults();
	let url = context + "/settings/";
	let params = "action=all" + "&flag=" + flag +
		"&portrait=" + portrait + "&nationTitle=" + nationTitle +
		"&leaderTitle=" + leaderTitle + "&description=" + description;
	ajax(url, params);
}

function policy(policy)
{
	displayResults();
	let url = context + "/policy/" + policy;
	let params = 'selection=' + document.getElementById(policy).selectedIndex;
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
	xhttp.open("POST", context + "/createtreaty/", true);
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
			window.location.href = "/treaty/" + xhttp.responseText;
		}
	};
}

function login(user, pass)
{
	displayResults();
	let xhttp = new XMLHttpRequest();
	let params = "username=" + user + "&password=" + pass;
	xhttp.open("POST", context + "/login/", true);
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
	xhttp.open("POST", context + "/technology/", true);
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
	if(url.pathname.indexOf("/tree/") !== -1)
	{
		let category = url.pathname.substring(url.pathname.indexOf("/tree/") + 6);
		let xhttp = new XMLHttpRequest();
		xhttp.open("GET", context + "/techtree/" + category, true);
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
}

function updateTreaty(attribute, value)
{
	displayResults();
	let url = context + "/treaty/";
	let params = "attribute=" + attribute + "&value=" + value;
	ajax(url, params);
}

function postDeclaration()
{
	displayResults();
	let message = document.getElementById("post").value;
	let url = context + "/declarations/";
	let params = "message=" + message;
	ajax(url, params);
}

function newsDelete(id)
{
	displayResults();
	let url = context + "/news/";
	let params = "delete=" + id;
	ajax(url, params);
}

function processProduction(hover)
{
	/*let temp = freeFactories;
	let id = hover.classList.item(0).substring(3);
	let classes = document.getElementsByClassName("id=" + id);
	let index = hover.classList.item(1).substring(7);
	let old = hover.classList.item(2).substring(8);
	temp = freeFactories + (old - index);
	for(let i = 0; i < 15; i++)
	{
		if(i < index && (freeFactories + (old - i)) > 0)
		{
			classes[i].src = "/images/production/factory.svg";
		}
		else
		{
			classes[i].src = "/images/production/blank.svg";
		}
	}
	updateFreeFactories(temp);*/
}

function resetProduction(event, id, num)
{
	/*if(event.target.classList.contains("cityElements"))
	{
		let classes = document.getElementsByClassName("id=" + id);
		for(let i = 0; i < 15; i++)
		{
			if(i < classes[i].classList.item(2).substring(8))
			{
				classes[i].src = "/images/production/factory.svg";
			}
			else
			{
				classes[i].src = "/images/production/blank.svg";
			}
		}
		updateFreeFactories();
	}*/
}

function clickProduction(event, id, num)
{
	let element = document.getElementsByClassName("id=" + id);
	let old = element[0].classList.item(2).substring(8);
	let temp = freeFactories + (old - num);
	console.log(temp);
	if(temp < 0)
	{
		num += temp;
		temp = 0;
	}
	if(temp >= 0)
	{
		for(let i = 0; i < 15; i++)
		{
			element[i].classList.remove(element[i].classList.item(2));
			element[i].classList.add("default=" + num);
			if(i < element[i].classList.item(2).substring(8))
			{
				element[i].src = "/images/production/factory.svg";
			}
			else
			{
				element[i].src = "/images/production/blank.svg";
			}
		}
		freeFactories += (old - num);
	}
	updateFreeFactories();
}

function updateFreeFactories(num)
{
	if(num !== undefined)
	{
		document.getElementById("factories").innerHTML = num + " Free Factories";
	}
	else
	{
		document.getElementById("factories").innerHTML = freeFactories + " Free Factories";
	}
}

function sendProduction(id, num)
{
	displayResults();
	let production = document.getElementById("change" + id).value;
	let url = context + "/production/" + id;
	let params = "action=update&factories=" + num + "&production=" + production;
	ajax(url, params);
}

function deleteProduction(id)
{
	displayResults();
	let url = context + "/production/" + id;
	let params = "action=delete&delete=" + id;
	ajax(url, params);
	let element = document.getElementsByClassName("id=" + id);
	element[0].parentElement.parentElement.parentElement.parentElement.parentElement.parentElement.style.display = 'none';
}

function createProduction()
{
	let url = context + "/production/0";
	let params = "action=new";
	let xhttp = new XMLHttpRequest();
	xhttp.open("POST", url, true);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send(params);
	xhttp.onreadystatechange = function()
	{
		if(xhttp.readyState === 4 && xhttp.status === 200)
		{
			updateProduction();
			freeFactories--;
			updateFreeFactories();
		}
	};
}

function changeProduction(id)
{
	toggle("change" + id)
}

function updateProduction()
{
	let url = context + "/production/all";
	let xhttp = new XMLHttpRequest();
	let element = document.getElementById("production");
	xhttp.open("GET", url, true);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send();
	xhttp.onreadystatechange = function()
	{
		if(xhttp.readyState === 4 && xhttp.status === 200)
		{
			element.innerHTML = xhttp.responseText;
		}
	};
}