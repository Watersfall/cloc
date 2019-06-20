function sendOil(amount, id) {
    document.getElementById('resultsContainer').style.visibility = "visible";
    document.getElementById("result").innerHTML = "<p>Loading...</p>";
    var xhttp = new XMLHttpRequest();
    var params = "sendoil=" + amount + "&id=" + id;
    xhttp.open("POST", "/nationresults", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send(params);
    xhttp.onreadystatechange = function () {
        if (xhttp.readyState === 4 && xhttp.status === 200) {
            document.getElementById("result").innerHTML = xhttp.responseText;
        }
    };
}

function sendCoal(amount, id) {
    document.getElementById('resultsContainer').style.visibility = "visible";
    document.getElementById("result").innerHTML = "<p>Loading...</p>";
    var xhttp = new XMLHttpRequest();
    var params = "sendcoal=" + amount + "&id=" + id;
    xhttp.open("POST", "/nationresults", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send(params);
    xhttp.onreadystatechange = function () {
        if (xhttp.readyState === 4 && xhttp.status === 200) {
            document.getElementById("result").innerHTML = xhttp.responseText;
        }
    };
}

function sendIron(amount, id) {
    document.getElementById('resultsContainer').style.visibility = "visible";
    document.getElementById("result").innerHTML = "<p>Loading...</p>";
    var xhttp = new XMLHttpRequest();
    var params = "sendiron=" + amount + "&id=" + id;
    xhttp.open("POST", "/nationresults", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send(params);
    xhttp.onreadystatechange = function () {
        if (xhttp.readyState === 4 && xhttp.status === 200) {
            document.getElementById("result").innerHTML = xhttp.responseText;
        }
    };
}

function sendMg(amount, id) {
    document.getElementById('resultsContainer').style.visibility = "visible";
    document.getElementById("result").innerHTML = "<p>Loading...</p>";
    var xhttp = new XMLHttpRequest();
    var params = "sendmg=" + amount + "&id=" + id;
    xhttp.open("POST", "/nationresults", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send(params);
    xhttp.onreadystatechange = function () {
        if (xhttp.readyState === 4 && xhttp.status === 200) {
            document.getElementById("result").innerHTML = xhttp.responseText;
        }
    };
}

function sendCash(amount, id) {
    document.getElementById('resultsContainer').style.visibility = "visible";
    document.getElementById("result").innerHTML = "<p>Loading...</p>";
    var xhttp = new XMLHttpRequest();
    var params = "sendcash=" + amount + "&id=" + id;
    xhttp.open("POST", "/nationresults", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send(params);
    xhttp.onreadystatechange = function () {
        if (xhttp.readyState === 4 && xhttp.status === 200) {
            document.getElementById("result").innerHTML = xhttp.responseText;
        }
    };
}