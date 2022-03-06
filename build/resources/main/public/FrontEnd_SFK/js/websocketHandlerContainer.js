var webSocket2;

function connectWebSocketContainer() {
    console.log("Estoy en websocketContainer");
    webSocket2 = new WebSocket("ws://" + location.hostname + ":" + location.port + "/server/container");
    webSocket2.onmessage = function(event) {
        showWebsocketDataContainer(event);
    };
    loadWeightGraph();
}
function showWebsocketDataContainer(evt)
{
    console.log("Estoy en showWebsocketDataContainer");
    var data = JSON.parse(evt.data);

    var weight = data['weight'];
    console.log("Peso: ",weight);

    var today = new Date();
    var t = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();

    document.getElementById("weight").innerHTML = weight + " " +"Kg";
    var idContainer = document.getElementById('idContainer').value;

    if (idContainer != -1) {
        if($('#idContainer option:selected').text() == data['containerId']){
            addDataContainer(t,weight);
        }
    }
}
window.onload = function(e)   {
    connectWebSocketContainer();

}