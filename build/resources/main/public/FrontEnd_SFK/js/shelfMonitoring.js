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
function findShelfByDeviceId(deviceId){
    let serverResponse = "";
    let endpoint = "findShelfByDeviceId"
    const Http = new XMLHttpRequest();
    const url = location.protocol + "//" + location.hostname + ':' + location.port + '/api/' + endpoint + "/" + deviceId;
    Http.open("GET", url);
    Http.send();
    Http.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            serverResponse = JSON.parse(Http.responseText);
            console.log("ID FROM API: ", serverResponse['deviceId']);
            console.log("Respuesta servidor: ",serverResponse);
            if (serverResponse != null) {
                if (serverResponse.length > 0) {
                    loadUniqueShelf(serverResponse);
                }
            }

        }
    }
}
function loadShelfList(data){
    var idShelf = document.getElementById("idShelf");
    var shelfHeader = document.getElementById("shelfHeader");
    var branchOfficeHeader = document.getElementById("branchOfficeHeader");


    idShelf.length = data.length + 1;
    console.log("ID",data['deviceId']);
    let j = 0;
    for(let i in data){
        j = parseInt(i) + 1;
        idShelf.options[j].text = data[i].deviceId;
        branchOfficeHeader.innerHTML = data[i].branchOffice.address.direction;
        shelfHeader.innerHTML = data[i].deviceId;
        //idShelf.options[j].text ="SH-"+data[i].deviceId + " " + "Sucursal: "+data[i].branchOffice.address.direction;
    }
}
function loadUniqueShelf(data){
    var idShelf = document.getElementById("idShelf");
    idShelf.length = data.length + 1;
    let j = 0;
    for(let i in data){
        j = parseInt(i) + 1;
        idShelf.options[j].text = data[i].deviceId;
    }
}
function enableButtonShowShelf(){
    var idShelf = document.getElementById('idShelf').value;
    let showShelfButton = document.getElementById('showShelfButton');
    var headerRealtime = document.getElementById("headerRealtime");

    headerRealtime.disabled = true;
    showShelfButton.disabled = true;
    if (idShelf != -1) {
        headerRealtime.disabled = false;
        showShelfButton.disabled = false;
        return;
    }
}
findAllBranchOffice();