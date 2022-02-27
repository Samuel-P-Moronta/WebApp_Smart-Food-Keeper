

function findBranchOfficeByCompany(idCompany){
    let serverResponse = "";
    let endpoint = "findBranchOfficeByCompany"
    const Http = new XMLHttpRequest();
    const url = location.protocol + "//" + location.hostname + ':' + location.port + '/api/' + endpoint + "/" + idCompany;
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
function findAllCompanies(){
    console.log("Entering all company function")
    let serverResponse = "";
    const Http = new XMLHttpRequest();
    const url =  "http://localhost:7000/api/companyList"
    Http.open("GET", url);
    Http.send();
    Http.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            serverResponse = JSON.parse(Http.responseText);
            console.log("Respuesta servidor: ",serverResponse);
            if (serverResponse != null) {
                if (serverResponse.length > 0) {
                    loadCompanyList(serverResponse);
                }
            }
        }
    }
}
function loadCompanyList(data){
    console.log("Entering load company list function")
    var company = document.getElementById("idCompany");
    company.length = data.length + 1;
    let j = 0;
    for(let i in data){
        j = parseInt(i) + 1;
        company.options[j].value = data[i].id;
        company.options[j].text = " " + data[i].name;
    }
}
function loadBranchOfficeList(data){
    var branchOffice = document.getElementById("branchOfficeShelf");
    branchOffice.length = data.length + 1;
    let j = 0;
    for(let i in data){
        j = parseInt(i) + 1;
        branchOffice.options[j].value = data[i].id;
        branchOffice.options[j].text = " " + data[i].address.direction;
    }
}
function enableBranchOfficeSelect() {
    var idCompany = document.getElementById('idCompany').value;
    var branchOffice = document.getElementById("branchOfficeShelf");

    branchOffice.disabled = true;
    if(branchOffice != -1){
        branchOffice.disabled = false;
        findBranchOfficeByCompany(idCompany)
    }
}
function enableAddButton() {
    var idBranchOffice = document.getElementById('branchOfficeShelf').value;
    let add = document.getElementById('add');
    add.disabled = true;
    if (idBranchOffice != -1) {
        add.disabled = false;
        return;
    }
}
function shelfList(){
    let serverResponse = "";
    let endpoint = "shelfList"
    const Http = new XMLHttpRequest();
    const url = location.protocol + "//" + location.hostname + ':' + location.port + '/api/' + endpoint;
    Http.open("GET", url);
    Http.send();
    Http.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            serverResponse = JSON.parse(Http.responseText);
            console.log("Respuesta servidor: ",serverResponse);
            if (serverResponse != null) {
                if (serverResponse.length > 0) {
                    shelfListTable(serverResponse);
                }
            }
        }
    }
}
function shelfListTable(data){
    console.log("Entrando a la funcion shelfList");
    var row = ""
    for(var key in data){
        row += "<tr>"
        row += "<td>" + "SH-"+data[key].deviceId + "</td>"
        row += "<td>" + "</td>"
        row += "<td>" + "</td>"

        row += "<td>" + data[key].registerDate+"</td>"
        row += "<tr>"

        console.log("id: ", data[key].deviceId);
        console.log("Register date: ", data[key].registerDate);
        console.log("Company: ", data[key].branchOffice.city);
    }
    document.getElementById("shelfList").innerHTML = row;

}
shelfList();
findAllCompanies();
