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
            console.log("ID FROM API: ", serverResponse['deviceId']);
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
    console.log("ID CONTAINER",data['deviceId']);
    let j = 0;
    for(let i in data){
        j = parseInt(i) + 1;
        idContainer.options[j].text = data[i].deviceId;
        branchOfficeHeader.innerHTML = data[i].branchOffice.address.direction;
        containerHeader.innerHTML = data[i].deviceId;
        //idShelf.options[j].text ="SH-"+data[i].deviceId + " " + "Sucursal: "+data[i].branchOffice.address.direction;
    }
}
function enableContainerSelect(){
    var idBranchOffice = document.getElementById('idBranchOffice-S').value;
    var idContainer = document.getElementById("idContainer");
    var headerRealtime = document.getElementById("headerRealtime");

    headerRealtime.disabled = true;
    idContainer.disabled = true;
    if(idContainer != -1){
        idContainer.disabled = false;
        findContainerByBranchOffice(idBranchOffice);
    }
}