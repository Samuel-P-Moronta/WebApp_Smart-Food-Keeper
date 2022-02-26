var webSocket;
var webSocket2;

function connect1()
{
    webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/server/shelf");
    webSocket.onmessage = function(event) { getDataFromServerShelf(event); };


}
function connect2()
{
    webSocket2 = new WebSocket("ws://" + location.hostname + ":" + location.port + "/server/container");
    webSocket2.onmessage = function(event) { getDataFromServerContainer(event); };
}

function loadRipenessGraphicsRealTime(unripe,ripe,overripe){

    var doughnutPieData = {
        datasets: [{
            data: [unripe, ripe, overripe],
            backgroundColor: [
                'rgba(255, 99, 132, 0.5)',
                'rgba(54, 162, 235, 0.5)',
                'rgba(255, 206, 86, 0.5)',
                'rgba(75, 192, 192, 0.5)',
                'rgba(153, 102, 255, 0.5)',
                'rgba(255, 159, 64, 0.5)'
            ],
            borderColor: [
                'rgba(255,99,132,1)',
                'rgba(54, 162, 235, 1)',
                'rgba(255, 206, 86, 1)',
                'rgba(75, 192, 192, 1)',
                'rgba(153, 102, 255, 1)',
                'rgba(255, 159, 64, 1)'
            ],
        }],

        // These labels appear in the legend and in the tooltips when hovering different arcs
        labels: [
            'Verde',
            'Maduro',
            'Muy Maduro',
        ]
    };
    var doughnutPieOptions = {
        responsive: true,
        animation: {
            animateScale: true,
            animateRotate: true
        }
    };
    if ($("#doughnutChartRipenessRealTime").length) {
        var doughnutChartRipenessRealTime = $("#doughnutChartRipenessRealTime").get(0).getContext("2d");
        var doughnutChartRipenessRealTime = new Chart(doughnutChartRipenessRealTime, {
            type: 'doughnut',
            data: doughnutPieData,
            options: doughnutPieOptions
        });
    }

}
function loadTemperature(temperature){

    var measure_date = new Date();
    var date = measure_date.getFullYear()+'-'+(measure_date.getMonth()+1)+'-'+measure_date.getDate();
    var time = measure_date.getHours() + ":" + measure_date.getMinutes() + ":" + measure_date.getSeconds();

    var areaData = {
        labels: [date+" "+time],
        datasets: [{
            label: 'Temperatura',
            data: [temperature],
            backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)',
                'rgba(255, 206, 86, 0.2)',
                'rgba(75, 192, 192, 0.2)',
                'rgba(153, 102, 255, 0.2)',
                'rgba(255, 159, 64, 0.2)'
            ],
            borderColor: [
                'rgba(255,99,132,1)',
                'rgba(54, 162, 235, 1)',
                'rgba(255, 206, 86, 1)',
                'rgba(75, 192, 192, 1)',
                'rgba(153, 102, 255, 1)',
                'rgba(255, 159, 64, 1)'
            ],
            borderWidth: 1,
            fill: true, // 3: no fill
        }]
    };

    var areaOptions = {
        plugins: {
            filler: {
                propagate: true
            }
        }
    }

    if ($("#areaCharttempertureRealtime").length) {
        var areaCharttempertureRealtime = $("#areaCharttempertureRealtime").get(0).getContext("2d");
        var areaCharttempertureRealtime = new Chart(areaCharttempertureRealtime, {
            type: 'line',
            data: areaData,
            options: areaOptions
        });
    }
}

function getDataFromServerShelf(evt)
{
    console.log("Estoy dentro de la funcion getDataFromServerShelf")
    var data = JSON.parse(evt.data);

    var cant_unripe = data['cantUnripe']
    var cant_ripe = data['cantRipe'];
    var cant_overripe = data['cantOverripe'];
    var temperature = data['temperature'];

    console.log("Overripe: ", cant_overripe)
    console.log("Temperature: ", temperature)


    loadTemperature(temperature);
    loadRipenessGraphicsRealTime(cant_unripe,cant_ripe,cant_overripe)

}
function getDataFromServerContainer(evt)
{
    var data = JSON.parse(evt.data);
    //  document.getElementById('container_id').innerHTML = data['container']['id'];
    document.getElementById('weight').innerHTML = data['weight'];
    // MINIMO = 1 MEDIO = 2 LLENO = 3 COMPLETAMENTE VACIO = 0
    switch (data['statusCode']) {
        case 0:
            document.getElementById('statuscode').innerHTML = "Vacio";
            break;
        case 1:
            document.getElementById('statuscode').innerHTML = "Normal";
            break;
        case 2:
            document.getElementById('statuscode').innerHTML = "Medio";
            break;
        case 3:
            document.getElementById('statuscode').innerHTML = "Lleno";
            break;
        default:
    }
    var measure_date = new Date();
    var date = measure_date.getMonth()+"/"+
        measure_date.getDay()+"/"+
        measure_date.getFullYear()+":"+
        measure_date.getHours()+":"+
        measure_date.getMinutes();

    document.getElementById('measure_container_date').innerHTML = date;
}

window.onload = function(e)   {
    connect1();
    connect2();

}
