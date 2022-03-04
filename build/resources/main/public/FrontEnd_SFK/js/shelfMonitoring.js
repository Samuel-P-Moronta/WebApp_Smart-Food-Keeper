function findShelfByBranchOffice(idBranchOffice){
    let serverResponse = "";
    let endpoint = "findShelfByBranchOffice"
    var message = document.getElementById("idShelf");
    const Http = new XMLHttpRequest();
    const url = location.protocol + "//" + location.hostname + ':' + location.port + '/api/' + endpoint + "/" + idBranchOffice;
    Http.open("GET", url);
    Http.send();
    Http.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            serverResponse = JSON.parse(Http.responseText);
            console.log("ID FROM API: ", serverResponse['deviceId']);
            console.log("Respuesta servidor: ",serverResponse);
            if (serverResponse != null) {
                if (serverResponse.length > 0) {
                    loadShelfList(serverResponse);
                }
            }
            else{
                message.innerHTML = "No hay estantes para esta sucursal";
            }
        }
    }
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
    var idBranchOffice = document.getElementById("idBranchOffice-S");

    idBranchOffice.length = data.length + 1;
    let j = 0;
    for(let i in data){
        j = parseInt(i) + 1;
        idBranchOffice.options[j].value = data[i].id;
        idBranchOffice.options[j].text = " " + data[i].address.city + " " +"("+data[i].address.direction+")";
    }
}
function loadShelfList(data){
    var idShelf = document.getElementById("idShelf");
    idShelf.length = data.length + 1;
    console.log("ID",data['deviceId']);
    let j = 0;
    for(let i in data){
        j = parseInt(i) + 1;
        idShelf.options[j].text ="SH-"+data[i].deviceId + " " + "Sucursal: "+data[i].branchOffice.address.direction;
    }
}
function enableButtonShowShelf(){
    var idShelf = document.getElementById('idShelf').value;
    let showShelfButton = document.getElementById('showShelfButton');
    showShelfButton.disabled = true;
    if (idShelf != -1) {
        showShelfButton.disabled = false;
        return;
    }
}
function enableShelfSelect(){
    var idBranchOffice = document.getElementById('idBranchOffice-S').value;
    var idShelf = document.getElementById("idShelf");

    idShelf.disabled = true;
    if(idShelf != -1){
        idShelf.disabled = false;
        findShelfByBranchOffice(idBranchOffice);
    }
}
findAllBranchOffice();