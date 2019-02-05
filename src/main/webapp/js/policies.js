function policy(policy)
{
    document.getElementById('resultsContainer').style.visibility = "visible";
    document.getElementById("result").innerHTML = "Loading...";
    var xhttp = new XMLHttpRequest();
    var params = "policy=" + policy;
    xhttp.open("POST", "/policyresults", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send(params);
    xhttp.onreadystatechange=function()
    {
        if (xhttp.readyState===4 && xhttp.status===200)
        {
            document.getElementById("result").innerHTML = xhttp.responseText;
        }
    };
}

