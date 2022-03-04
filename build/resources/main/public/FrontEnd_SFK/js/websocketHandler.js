var webSocket;
var dataPlot;
var maxDataPoints = 15;

function connectWebSocket() {
    webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/server/shelf");
    webSocket.onmessage = function(event) { getDataFromServerShelf(event); };

    dataPlot = new Chart(document.getElementById("lineChartTempHum"), {
        type: 'line',
        data: {
            labels: [],
            datasets: [{
                data: [],
                label: "Temperatura (*C)",
                borderColor: "#3e95cd",
                backgroundColor: 'rgba(33, 76, 229, 0.5)',
                fill: true
            },{
                data: [],
                label: "Humedad (*C)",
                backgroundColor: 'rgba(255, 99, 71, 0.5)',
                borderColor: "#f44252",
                fill: true
            }]
        },
        options: {
            responsive: true,
            legend: {
                position: 'bottom',
            },
            hover: {
                mode: 'label'
            },
            scales: {
                yAxes: [{
                    display: true,
                    ticks: {
                        beginAtZero: true,
                        steps: 10,
                        stepValue: 5,
                        max: 80,
                        min:10
                    }
                }]
            },
        }
    });
}

function removeData(){
    dataPlot.data.labels.shift();
    dataPlot.data.datasets[0].data.shift();
    dataPlot.data.datasets[1].data.shift();

}
function addData(label, temp,hum) {
    if(dataPlot.data.labels.length > maxDataPoints){
        removeData();
    }
    dataPlot.data.labels.push(label);
    dataPlot.data.datasets[0].data.push(temp);
    dataPlot.data.datasets[1].data.push(hum);
    dataPlot.update();
}
function getDataFromServerShelf(evt) {
    console.log("Estoy en la funcion para recibir datos del websocket")
    var data = JSON.parse(evt.data);

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
    }

    var measure_date = new Date();
    var date = measure_date.getFullYear()+'-'+(measure_date.getMonth()+1)+'-'+measure_date.getDate();
    var time = measure_date.getHours() + ":" + measure_date.getMinutes() + ":" + measure_date.getSeconds();
    document.getElementById('measure_shelf_date').innerHTML = date+" "+time;
}
window.onload = function(e)   {
    connectWebSocket();
}