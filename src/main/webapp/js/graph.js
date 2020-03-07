let height = 450;
let width = 650;
let max = 0;
let min = -1;
let heightScale = 0;
let widthScale = 0;
let data = null;
let dataPoints = 0;
let maxDataPoints = 20;
let graphs = [];

function generateGraph(id)
{
	if(data == null)
	{
		loadData('/graphs/globalstats', id);
	}
	else
	{
		toggle(id + "_div");
		drawGraph(id);
	}
}

function loadData(url, id)
{
	let xhttp = new XMLHttpRequest();
	xhttp.open("GET", url, true);
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhttp.send();
	xhttp.onreadystatechange = function()
	{
		if(xhttp.readyState === 4 && xhttp.status === 200)
		{
			data = JSON.parse(xhttp.responseText);
			data = data["weeks"];
			dataPoints = data.length;
			generateGraph(id)
		}
	};
}

function setHeightScale()
{
	heightScale = 350 / (max - min);
}

function setWidthScale()
{
	widthScale = 600 / 24;
}

function getMax(id)
{
	max = 0;
	for(let i = 0; i < dataPoints; i++)
	{
		if(data[i][id] > max)
		{
			console.log(data[i][id]);
			max = data[i][id];
		}
	}
	return max;
}

function getMin(id)
{
	min = max;
	for(let i = 0; i < dataPoints; i++)
	{
		if(data[i][id] < min)
		{
			min = data[i][id];
		}
	}
	return min;
}

function drawGraph(id)
{
	if(graphs.indexOf(id) === -1)
	{
		getMax(id);
		getMin(id);
		setHeightScale();
		setWidthScale();
		drawGrid(id);
		let temp = document.getElementById(id);
		let canvas = temp.getContext("2d");
		canvas.strokeStyle = "black";
		let y = height - 50 - ((data[0][id] - min)) * heightScale;
		let x = 100;
		canvas.moveTo(x, y);
		canvas.lineWidth = 3;
		canvas.beginPath();
		for(let i = 0; i < dataPoints; i++)
		{
			canvas.moveTo(x, y);
			x = 100 + i * widthScale;
			y = height - 50 - (parseInt(data[i][id]) - min) * heightScale;
			canvas.lineTo(x, y);
			canvas.moveTo(x, y);
			canvas.arc(x, y, 3, 0, 2 * Math.PI);
			canvas.fill();
		}
		canvas.closePath();
		canvas.stroke();
		graphs.push(id);
	}
}

function drawGrid(id)
{
	let temp = document.getElementById(id);
	let canvas = temp.getContext("2d");
	canvas.strokeStyle = "gray";
	canvas.beginPath();
	for(let x = 100; x < width; x += (widthScale * 2))
	{
		canvas.moveTo(x, 50);
		canvas.lineTo(x, height - 50);
	}
	for(let y = 50; y < height; y += 50)
	{
		canvas.moveTo(100, y);
		canvas.lineTo(width - 50, y);
	}
	canvas.closePath();
	canvas.stroke();
	drawLabels(id);
}

function drawLabels(id)
{
	let temp = document.getElementById(id);
	let canvas = temp.getContext("2d");
	canvas.textAlign = "center";
	canvas.font = "18px Trebuchet MS";
	canvas.fillText(id, width / 2, 28);
	canvas.fillText("Week", 350, height - 10);
	canvas.font = "12px Trebuchet MS";
	for(let i = 0; i < dataPoints; i += 2)
	{
		canvas.fillText(data[i]["week"], 100 + (i * widthScale), height - 35);
	}
	canvas.textAlign = "right";
	let increment = (max - min) / 7;
	for(let i = 50; i < height; i += 50)
	{
		canvas.fillText(Math.trunc(max - (increment * ((i - 50) / 50))) + "", 95, i);
	}
	canvas.font = "18px Trebuchet MS";
	canvas.textAlign = "center";
	canvas.save();
	canvas.translate(28, 200);
	canvas.rotate(-Math.PI / 2);
	canvas.fillText(id, 0, 0);
	canvas.restore()
}