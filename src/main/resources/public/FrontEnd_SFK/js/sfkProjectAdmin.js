

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
        }
    }
}

function enableBranchOfficeSelect() {
    var idCompany = document.getElementById('companyShelf').value;
    findBranchOfficeByCompany(idCompany)
}
function enableAddButton() {
    var ind = document.getElementById('branchOfficeShelf').value;
    let add = document.getElementById('add');
    add.disabled = true;
    if (ind != -1) {
        add.disabled = false;
    }
}
