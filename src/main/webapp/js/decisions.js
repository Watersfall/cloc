function decision(policy) {
    document.getElementById('resultsContainer').style.visibility = "visible";
    document.getElementById("result").innerHTML = "<p>Loading...<p>";
    let xhttp = new XMLHttpRequest();
    let params = 'selection=' + document.getElementById(policy).selectedIndex + '&decision=' + policy;
    xhttp.open('POST', '/decisions.do', true);
    xhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    xhttp.send(params);
    xhttp.onreadystatechange = function () {
        if (xhttp.readyState === 4 && xhttp.status === 200) {
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

