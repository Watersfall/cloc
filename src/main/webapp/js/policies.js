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

function armyPolicy(policy, armyId)
{
	document.getElementById('resultsContainer').style.visibility = "visible";
	document.getElementById("result").innerHTML = "<p>Loading...</p>";
	var xhttp = new XMLHttpRequest();
	xhttp.open("POST", "/policy/army/" + policy + "?army=" + cityId, true);
	xhttp.send();
	xhttp.onreadystatechange = function()
	{
		if(xhttp.readyState === 4 && xhttp.status === 200)
		{
			document.getElementById("result").innerHTML = xhttp.responseText;
		}
	};
}

