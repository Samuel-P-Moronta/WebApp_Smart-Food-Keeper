function validateIdentificationCard(){
    console.log("estoy en validate identification card")
    let serverResponse = "";
    let endpoint = "validIdentificationCard"
    var identificationCard = document.getElementById("identificationCard").value;
    console.log("Cedula recibida: ",identificationCard);
    const Http = new XMLHttpRequest();
    const url = location.protocol + "//" + location.hostname + ':' + location.port + '/api/' + endpoint + "/" + identificationCard;
    Http.open("GET", url);
    Http.send();
    Http.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            serverResponse = JSON.parse(Http.responseText);
            console.log("Respuesta servidor: ",serverResponse);
            if (serverResponse != null) {
                if (serverResponse.length > 0) {
                }
            }
        }
    }
}
//validateIdentificationCard();