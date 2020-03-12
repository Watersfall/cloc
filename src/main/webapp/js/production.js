let freeFactories = 0;

function updateFreeFactories()
{
	let element = document.getElementById("factories");
	if(freeFactories === 1)
	{
		element.innerHTML = "1 Free Factory";
	}
	else
	{
		element.innerHTML = freeFactories + " Free Factories";
	}
}

function clickProduction(id, number)
{
	let elements = document.getElementsByClassName("production_table_id=" + id);
	let div = document.getElementById("production_id_" + id);
	let originalNumber = parseInt(div.className.substring(div.className.indexOf("=") + 1));
	console.log(originalNumber);
	console.log(id);
	console.log(number);
	if(originalNumber !== number)
	{
		if(number < originalNumber || ((number > originalNumber) && (originalNumber + freeFactories >= number)))
		{
			div.className = "original=" + number;
			freeFactories = freeFactories + (originalNumber - number);
			updateFreeFactories();
			for(let i = 0; i < 15; i++)
			{
				if(i < number)
				{
					elements.item(i).src = elements.item(i).src.replace("blank", "factory");
				}
				else
				{
					elements.item(i).src = elements.item(i).src.replace("factory", "blank");
				}
			}
		}
	}
}

function deleteProduction(id)
{
	displayResults();
	let div = document.getElementById("production_id_" + id);
	let originalNumber = parseInt(div.className.substring(div.className.indexOf("=") + 1));
	let url = "/production/" + id;
	let params = "action=delete";
	let callback = function()
	{
		if(this.readyState === 4 && this.status === 200)
		{
			document.getElementById("result").innerHTML = this.responseText;
			if(this.responseText.indexOf("Deleted") !== -1)
			{
				freeFactories += originalNumber;
				updateFreeFactories();
				div.parentElement.remove();
			}
		}
	};
	ajax(url, params, callback);
}

function createProduction()
{
	displayResults();
	let url = "/production/0";
	let params = "action=new";
	let callback = function()
	{
		if(this.readyState === 4 && this.status === 200)
		{
			document.getElementById("result").innerHTML = this.responseText;
			if(this.responseText.indexOf("Created") !== -1)
			{
				freeFactories -= 1;
				updateFreeFactories();
				reloadProduction();
			}
		}
	};
	ajax(url, params, callback);
}

function updateProduction(id)
{
	displayResults();
	let div = document.getElementById("production_id_" + id);
	let number = parseInt(div.className.substring(div.className.indexOf("=") + 1));
	let producing = document.getElementById("change_" + id).value;
	let url = "/production/" + id;
	let params = "action=update&factories=" + number + "&production=" + producing;
	let callback = function()
	{
		if(this.readyState === 4 && this.status === 200)
		{
			document.getElementById("result").innerHTML = this.responseText;
			if(this.responseText.indexOf("Updated") !== -1)
			{
				reloadProduction();
			}
		}
	};
	ajax(url, params, callback);
}

function reloadProduction()
{
	let url = context + "/production/all";
	let element = document.getElementById("production");
	let callback = function()
	{
		if(this.readyState === 4 && this.status === 200)
		{
			element.innerHTML = this.responseText;
		}
	};
	ajax(url, null, callback, "GET");
}