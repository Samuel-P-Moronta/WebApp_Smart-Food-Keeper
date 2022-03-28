function loadStats(idBranchOffice){
    let date = document.getElementById("date-input").value;
    let currentStats = "";
    let statsByHour = "";
    const Http = new XMLHttpRequest();
    let endpoint = "getStatsByBranchOffice"
    const url = location.protocol + "//" + location.hostname + ':' + location.port + '/api/' + endpoint + "/" + idBranchOffice + "/" + date;
    Http.open("GET", url);
    Http.send();
    Http.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            currentStats = JSON.parse(Http.responseText);
            console.log("Estadistica recibidas",currentStats.wasteFruits.byHour);
            console.log("Otra",statsByHour);
            loadStatsByHour(currentStats.wasteFruits.byHour);
            //loadIncidents(currentStats.wasteFruits.byHour);
            prueba();
        }
    }
}
function changeBranchOffice(){
    let idBranchOffice = document.getElementById("idBranchOffice").value;
    loadStats(idBranchOffice)
}
function updateCharts(){
    let idBranchOffice = document.getElementById("idBranchOffice").value;
    loadStats(idBranchOffice)
}
function findAllBranchOffice(){
    console.log("Entering all company function")
    let serverResponse = "";
    const Http = new XMLHttpRequest();
    const endpoint = "branchOfficeList";

    const url = location.protocol + "//" + location.hostname + ':' + location.port + '/api/' + endpoint;
    Http.open("GET", url);
    Http.send();
    Http.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            serverResponse = JSON.parse(Http.responseText);
            console.log("Respuesta servidor: ",serverResponse);
            if (serverResponse != null) {
                if (serverResponse.length > 0) {
                    loadBranchOfficeList(serverResponse);
                }
            }
        }
    }
}
function loadBranchOfficeList(data){
    var idBranchOffice = document.getElementById("idBranchOffice");

    idBranchOffice.length = data.length + 1;
    let j = 0;
    for(let i in data){
        j = parseInt(i) + 1;
        idBranchOffice.options[j].value = data[i].id;
        idBranchOffice.options[j].text = " " + data[i].address.city + " " +"("+data[i].address.direction+")";
    }
}
function enableShelfSelect(){
    var idBranchOffice = document.getElementById('idBranchOffice').value;
    var idShelf = document.getElementById("idShelf");
    var time = document.getElementById("measure_shelf_date");

    time.disabled = true;
    idShelf.disabled = true;
    console.log("ID SELECTED: ",idShelf.value)
    if(idShelf != -1){
        idShelf.disabled = false;
        time.disabled = false;
        findShelfByBranchOffice(idBranchOffice);
    }

}
function enableContainerSelect(){
    console.log("Estoy en la funcion enable container select")
    var idBranchOffice = document.getElementById('idBranchOffice').value;
    var idContainer = document.getElementById("idContainer");

    idContainer.disabled = true;
    if(idContainer != -1){
        idContainer.disabled = false;
        findContainerByBranchOffice(idBranchOffice);
    }
}
findAllBranchOffice();