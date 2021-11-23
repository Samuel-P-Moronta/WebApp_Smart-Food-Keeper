
//abriendo el objeto para el websocket
var webSocket;
var webSocket2;
var tiempoReconectar = 5000;

$(document).ready(function(){
    verifyContainer();
    verifyShelf()

});
/**
 *
 * @param mensaje
 */
function receiveInfoContainer(mensaje){
    console.log("Recibiendo del servidor [Container]: "+mensaje.data)
    $("#infoContainer").append(mensaje.data);
}
function receiveInfoShelf(mensaje){
    data_received = mensaje.data
    $('#infoShelf').attr('value',mensaje.data.toString())
}
function verifyContainer() {
    webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/server/container");
    //indicando los eventos:
    webSocket.onmessage = function(data){receiveInfoContainer(data);};
    webSocket.onopen  = function(e){ console.log("Conectado - status "+this.readyState); };
    webSocket.onclose = function(e){console.log("Desconectado - status "+this.readyState);};
}
function verifyShelf() {
    webSocket2 = new WebSocket("ws://" + location.hostname + ":" + location.port + "/server/shelf");
    //indicando los eventos:
    webSocket2.onmessage = function(data){receiveInfoShelf(data);};
    webSocket2.onopen  = function(e){ console.log("Conectado - status "+this.readyState); };
    webSocket2.onclose = function(e){console.log("Desconectado - status "+this.readyState);
    };
}
function verificarConexion(){
    if(!webSocket || webSocket.readyState == 3){
        verifyContainer();
    }
    if(!webSocket2 || webSocket.readyState == 3){
        verifyShelf();
    }
}
setInterval(verificarConexion, tiempoReconectar);
