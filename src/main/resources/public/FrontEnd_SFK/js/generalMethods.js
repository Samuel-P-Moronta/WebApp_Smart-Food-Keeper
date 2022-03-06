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
    var headerRealtime = document.getElementById("headerRealtime");

    headerRealtime.disabled = true;
    idShelf.disabled = true;
    if(idShelf != -1){
        idShelf.disabled = false;
        findShelfByBranchOffice(idBranchOffice);
    }
}
function enableContainerSelect(){
    console.log("Estoy en la funcion enable container select")
    var idBranchOffice = document.getElementById('idBranchOffice').value;
    var idContainer = document.getElementById("idContainer");
    var headerRealtime = document.getElementById("headerRealtime");

    headerRealtime.disabled = true;
    idContainer.disabled = true;
    if(idContainer != -1){
        idContainer.disabled = false;
        findContainerByBranchOffice(idBranchOffice);
    }
}
findAllBranchOffice();