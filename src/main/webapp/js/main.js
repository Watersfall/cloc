const PATH = "/cloc";

function ajax(url, params, callback, method)
{
	url = PATH + url;
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
				console.log(this.responseText);
				updateFromJson(JSON.parse(this.responseText));
			}
		};
	}
	else
	{
		xhttp.onreadystatechange = callback;
	}
}

function updateFromJson(json)
{
	if(json.CREATE !== undefined && json.create !== null)
	{
		let create = json.CREATE;
		for (const [key, value] of Object.entries(create))
		{
			let element = document.getElementById(value.ID.toString());
			if(element !== null)
			{
				element.innerText = value.CONTENT.toString();
			}
			else
			{
				let element = document.createElement(value.TYPE.toString());
				element.id = value.ID.toString();
				element.innerHTML = value.CONTENT.toString();
				document.getElementById(key).appendChild(element);
			}
		}
	}
	for (const [key, value] of Object.entries(json))
	{
		let element = document.getElementById(key);
		if(element != null)
		{
			element.innerHTML = value.toString();
		}
	}
}

function displayResults()
{
	document.getElementById('results').style.display = "block";
	document.getElementById("MESSAGE").innerHTML = "<p>Loading...</p>";
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
	tab = document.getElementById(tab);
	let tabs = document.getElementsByClassName("special-toggle");
	for(let i = 0; i < tabs.length; i++)
	{
		if(tabs[i] !== tab)
		{
			let tab = tabs[i];
			if(tab != null)
			{
				tab.style.display = "";
			}
		}
	}
	if(tab !== null)
	{
		toggle(tab.id);
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

function register2()
{
	displayResults();
	let form = document.getElementById("register");
	let url = "/register/";
	let params = new URLSearchParams(new FormData(form).entries()).toString();
	let callback = function() {
		if(this.readyState === 4 && this.status === 200)
		{
			let json = JSON.parse(this.responseText);
			if(json.SUCCESS === false)
			{
				updateFromJson(JSON.parse(this.responseText));
			}
			else
			{
				window.location.href = PATH + "/main/";
			}
		}
	};
	ajax(url, params, callback);
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
		window.location.href = PATH + "/createtreaty/";
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
	FACTORY.src = PATH + "/images/production/factory.svg";
	FACTORY.alt = "factory";
	BLANK.src = PATH + "/images/production/blank.svg";
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
			updateFromJson(JSON.parse(this.responseText));
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
			element.replaceWith(placeholder.firstChild);
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
			updateFromJson(JSON.parse(this.responseText));
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
	let url = "/production/0";
	let params = "action=new";
	let callback = function()
	{
		if(this.readyState === 4 && this.status === 200)
		{
			if(!Number.isNaN(this.responseText))
			{
				let id = this.responseText;
				let url = "/production/" + id;
				let callback = function()
				{
					if(this.readyState === 4 && this.status === 200)
					{
						freeFactories -= 1;
						setFreeFactories(freeFactories);
						let placeholder = document.createElement("div");
						placeholder.innerHTML = this.responseText;
						document.getElementsByClassName("production_list").item(0).appendChild(placeholder.firstChild);
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
			updateFromJson(JSON.parse(this.responseText));
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

function nation(id, attribute, value)
{
	let url = "/nation/" + id;
	let params = "action=" + attribute + "&amount=" + value;
	ajax(url, params);
}

function nationEquipment(id, equipment, value)
{
	let url = "/nation/" + id;
	let params = "action=equipment&equipment=" + equipment + "&amount=" + value;
	ajax(url, params);
}

function nationMessage(id, attribute, value)
{
	let url = "/nation/" + id;
	let params = "action=" + attribute + "&message=" + value;
	ajax(url, params);
}

function mark(type)
{
	let url = "/main/";
	let params = "mark=" + type;
	ajax(url, params);
}

function createTreaty(name)
{
	displayResults();
	let url = "/createtreaty/";
	let params = "name=" + name;
	let callback = function()
	{
		if(this.readyState === 4 && this.status === 200)
		{
			document.getElementById("result").innerHTML = this.responseText;
		}
		if(this.readyState === 4 && this.status === 201)
		{
			window.location.href = PATH + "/treaty/" + this.responseText;
		}
	};
	ajax(url, params, callback);
}

function doEvent(id, response)
{
	let url = "/news/";
	let params = "event=" + id + "&event_action=" + response;
	ajax(url, params);
}

function editCityName(id)
{
	document.getElementById("city_name_" + id).style.display = "none";
	toggle("city_name_change_" + id);
}

function confirmCityName(id)
{
	displayResults();
	let name = document.getElementById("new_name_" + id).value;
	document.getElementById("city_name_" + id).firstElementChild.innerHTML = "<b>" + name + "</b>";
	let url = "/cities/" + id;
	let param = "name=" + name;
	let callback = function() {
		if(this.readyState === 4 && this.status === 200)
		{
			cancelCityName(id);
			updateFromJson(JSON.parse(this.responseText));
		}
	};
	ajax(url, param, callback);
}

function cancelCityName(id)
{
	toggle("city_name_change_" + id);
	toggle("city_name_" + id);
}

function alignmentTransaction(alignment, producible, action)
{
	let url = "/alignment/" + alignment;
	let params = "producible=" + producible + "&action=" + action;
	ajax(url, params);
}

function setAirforceSize(type, amount)
{
	let url = "/military/";
	let param = "type=" + type + "&amount=" + amount;
	ajax(url, param);
}

function army(id, action, type)
{
	let url = "/army/" + id;
	let params = "action=" + action;
	if(type !== undefined)
	{
		params += "&type=" + type;
	}
	ajax(url, params);
}

function createArmy()
{
	let url = "/army/0";
	let params = "action=create_army";
	let callback = function() {
		displayResults();
		if(this.readyState === 4 && this.status === 200)
		{
			let json = JSON.parse(this.responseText);
			updateFromJson(json);
			if(json.SUCCESS === true)
			{
				let url = "/api/army/" + json.ARMY_ID;
				let params = "";
				let callback = function() {
					if(this.readyState === 4 && this.status === 200)
					{
						let element = document.createElement("div");
						element.classList.add("subtile");
						element.id = "army_" + json.ARMY_ID;
						element.innerHTML = this.responseText;
						document.getElementById("armies").appendChild(element);
					}
				};
				ajax(url, params, callback, "GET");
			}
		}
	};
	ajax(url, params, callback);
}

function deleteArmy(id)
{
	let url = "/army/" + id;
	let params = "action=delete_army";
	let callback = function() {
		displayResults();
		if(this.readyState === 4 && this.status === 200)
		{
			let json = JSON.parse(this.responseText);
			updateFromJson(json);
			if(json.SUCCESS === true)
			{
				document.getElementById("army_" + id).remove();
			}
		}
	};
	ajax(url, params, callback);
}

function createBattalion(id, type)
{
	let url = "/army/" + id;
	let params = "action=create_battalion&type=" + type;
	let callback = function() {
		displayResults();
		if(this.readyState === 4 && this.status === 200)
		{
			console.log(this.responseText);
			let json = JSON.parse(this.responseText);
			updateFromJson(json);
			if(json.SUCCESS === true)
			{
				let element = document.createElement("div");
				element.classList.add("subtile");
				element.id = "battalion_" + json.BATTALION_ID;
				let title = document.createElement("div");
				title.classList.add("title");
				title.innerText = json.BATTALION_TYPE;
				let table = document.createElement("table");
				table.classList.add("nation");
				table.classList.add("nation_left");
				table.classList.add("left_text");
				table.classList.add("full_width");
				let t1 = document.createElement("tr");
				let t11 = document.createElement("td");
				t11.colSpan = 2;
				t11.innerText = "Size";
				let t2 = document.createElement("tr");
				let t21 = document.createElement("td");
				t21.colSpan = 2;
				t21.innerHTML = "0 / " + json.BATTALION_MAX_SIZE + " Soldiers";
				let t3 = document.createElement("tr");
				let t31 = document.createElement("td");
				t31.colSpan = 2;
				t31.innerText = "Equipment";
				let t4 = document.createElement("tr");
				let t41 = document.createElement("td");
				let deleteDiv = document.createElement("div");
				deleteDiv.classList.add("right_text");
				let deleteButton = document.createElement("button");
				deleteButton.classList.add("red");
				deleteButton.innerText = "Delete";
				deleteButton.setAttribute("onclick", "deleteBattalion(" + json.ARMY_ID + ", " + json.BATTALION_ID + ");");
				t4.appendChild(t41);
				t3.appendChild(t31);
				t2.appendChild(t21);
				t1.appendChild(t11);
				table.appendChild(t1);
				table.appendChild(t2);
				table.appendChild(t3);
				table.appendChild(t4);
				deleteDiv.appendChild(deleteButton);
				element.appendChild(title);
				element.appendChild(table);
				element.appendChild(deleteDiv);
				document.getElementById("battalions").appendChild(element);
			}
		}
	};
	ajax(url, params, callback);
}

function deleteBattalion(army, id)
{
	let url = "/army/" + army;
	let param = "action=delete_battalion&type=" + id;
	let callback = function() {
		displayResults();
		if(this.readyState === 4 && this.status === 200)
		{
			let json = JSON.parse(this.responseText);
			updateFromJson(json);
			if(json.SUCCESS === true)
			{
				document.getElementById("battalion_" + id).remove();
			}
		}
	};
	ajax(url, param, callback);
}

function getEstimatedBattlePlan(id, data)
{
	let location = data.split("_")[0];
	let locationId = data.split("_")[1];
	let url = "/estimate/" + id + "/" + location + "/" + locationId;
	let callback = function() {
		if(this.readyState === 4 && this.status === 200)
		{
			document.getElementById("enemy_battle_plan").innerHTML = this.responseText;
		}
	};
	ajax(url, "", callback, "GET");
}

function battle(id)
{
	let url = "/battle/";
	let params = "defender_id=" + id;
	let armyList = document.getElementsByName("armies");
	let targetList = document.getElementsByName("target");
	let armies = "";
	let target = "";
	let targetId = "";
	for(let i = 0; i < armyList.length; i++)
	{
		if(armyList[i].checked)
		{
			armies += armyList[i].value + ",";
		}
	}
	armies = armies.substring(0, armies.length - 1);
	for(let i = 0; i < targetList.length; i++)
	{
		if(targetList[i].checked)
		{
			target = targetList[i].value.split("_")[0];
			targetId = targetList[i].value.split("_")[1];
		}
	}
	params += "&attacker_armies=" + armies + "&location_id=" + targetId + "&battle_location=" + target;
	ajax(url, params);
}

function editArmyName(id)
{
	document.getElementById("army_name_" + id).style.display = "none";
	toggle("army_name_change_" + id);
}

function confirmArmyName(id)
{
	displayResults();
	let name = document.getElementById("new_name_" + id).value;
	let url = "/army/" + id;
	let param = "action=rename&type=" + name;
	let callback = function() {
		if(this.readyState === 4 && this.status === 200)
		{
			cancelArmyName(id);
			console.log(this.responseText);
			updateFromJson(JSON.parse(this.responseText));
		}
	};
	ajax(url, param, callback);
}

function cancelArmyName(id)
{
	toggle("army_name_change_" + id);
	toggle("army_name_" + id);
}