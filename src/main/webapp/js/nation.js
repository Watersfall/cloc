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