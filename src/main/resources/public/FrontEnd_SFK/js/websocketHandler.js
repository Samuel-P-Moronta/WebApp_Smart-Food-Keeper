var webSocket;
var dataPlot;
var maxDataPoints = 15;

function connectWebSocket() {
    webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/server/shelf");
    webSocket.onmessage = function(event) {
        showWebsocketData(event);
    };
    loadTempHumGraph();
}
function showWebsocketData(evt) {
    console.log("Estoy en la funcion para recibir datos del websocket")
    var data = JSON.parse(evt.data);
    console.log("DEVICE ID: ", data['deviceId'])

    if($('#idShelf option:selected').text() == data['deviceId']){
        var data_json_fruit_type = data['fruitType'];
        var fruit_cant = data['fruitCant'];
        var cant_overripe = data['cantOverripe'];
        var cant_ripe = data['cantRipe'];
        var cant_unripe = data['cantUnripe']
        var temperature = data['temperature'];
        var humidity = data['humidity'];
        var today = new Date();
        var t = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();

        addData(t,temperature,humidity)


        if(data_json_fruit_type == "pineapple"){
            data_json_fruit_type = "Pina"
        }else{
            if(data_json_fruit_type == "papaya"){
                data_json_fruit_type = "Lechosa"
            }
        }
        document.getElementById('fruitType').innerHTML = data_json_fruit_type;
        console.log(data['fruitType']);

        if((data['fruitType']) == 0){
            document.getElementById('fruitType').innerHTML = "No hay frutas";
        }else{
            document.getElementById('cantFruit').innerHTML = fruit_cant;
            document.getElementById('overripe').innerHTML = cant_overripe;
            document.getElementById('ripe').innerHTML = cant_ripe;
            document.getElementById('unripe').innerHTML = cant_unripe;
        }
        if(fruit_cant != 0){
            var percentage_ripe = (cant_ripe/fruit_cant)*100;
            var percentage_unripe = (cant_unripe/fruit_cant)*100;
            var percentage_overipe = (cant_overripe/fruit_cant)*100;

            loadRipenessGraphicsRealTime(percentage_unripe,percentage_ripe,percentage_overipe);
        }else{
            document.getElementById('cantFruit').innerHTML = "0";
            document.getElementById('overripe').innerHTML = "0";
            document.getElementById('ripe').innerHTML = "0";
            document.getElementById('unripe').innerHTML = "0";
            loadRipenessGraphicsRealTime(0,0,0);
        }

        var measure_date = new Date();
        var date = measure_date.getFullYear()+'-'+(measure_date.getMonth()+1)+'-'+measure_date.getDate();
        var time = measure_date.getHours() + ":" + measure_date.getMinutes() + ":" + measure_date.getSeconds();
        document.getElementById('measure_shelf_date').innerHTML = date+" "+time;
    }
}
window.onload = function(e)   {
    connectWebSocket();
}