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

    var idContainer = document.getElementById('idContainer').value;
    var headerRealtime = document.getElementById("headerRealtime");
    var showWeight =  document.getElementById("weight").innerHTML = weight + " " +"Kg";

    var measure_date = new Date();
    var date = measure_date.getFullYear()+'-'+(measure_date.getMonth()+1)+'-'+measure_date.getDate();
    var time = measure_date.getHours() + ":" + measure_date.getMinutes() + ":" + measure_date.getSeconds();
    document.getElementById('measure_container_date_date').innerHTML = date+" "+time;

    if (idContainer != -1) {
        if($('#idContainer option:selected').text() == data['containerId']){
            headerRealtime.style.display = "block";
            showWeight.style.display = "block";
            addDataContainer(t,weight);
        }
    }
}
window.onload = function(e)   {
    connectWebSocketContainer();
}