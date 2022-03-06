function findContainerByBranchOffice(idBranchOffice){
    let serverResponse = "";
    let endpoint = "findContainerByBranchOffice"
    const Http = new XMLHttpRequest();
    const url = location.protocol + "//" + location.hostname + ':' + location.port + '/api/' + endpoint + "/" + idBranchOffice;
    Http.open("GET", url);
    Http.send();
    Http.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            serverResponse = JSON.parse(Http.responseText);
            console.log("ID FROM API: ", serverResponse['containerId']);
            console.log("Respuesta servidor: ",serverResponse);
            if (serverResponse != null) {
                if (serverResponse.length > 0) {
                    loadContainerList(serverResponse);
                }
            }
        }
    }
}
function loadContainerList(data){
    var idContainer = document.getElementById("idContainer");
    var containerHeader = document.getElementById("containerHeader");
    var branchOfficeHeader = document.getElementById("branchOfficeHeader");


    idContainer.length = data.length + 1;
    console.log("ID",data['deviceId']);
    let j = 0;
    for(let i in data){
        j = parseInt(i) + 1;
        idContainer.options[j].text = data[i].containerId;
        branchOfficeHeader.innerHTML = data[i].branchOffice.address.direction;
        containerHeader.innerHTML = data[i].containerId;
        //idShelf.options[j].text ="SH-"+data[i].deviceId + " " + "Sucursal: "+data[i].branchOffice.address.direction;
    }
}
function enableButtonShowContainer(){
    var idContainer = document.getElementById('idContainer').value;
    let showGraphContainer = document.getElementById('showGraphContainer');
    var headerRealtime = document.getElementById("headerRealtime");

    headerRealtime.disabled = true;
    showGraphContainer.disabled = true;
    if (idContainer != -1) {
        headerRealtime.disabled = false;
        showGraphContainer.disabled = false;
        return;
    }
}
function showGraphContainer(){
    let canvasGraph = document.getElementById("lineChartWeight");
    let showGraphContainer = document.getElementById('showGraphContainer');
    canvasGraph.disabled = true;
    if(showGraphContainer.disabled === false){
        canvasGraph.disabled = false;
    }
}
findAllBranchOffice();