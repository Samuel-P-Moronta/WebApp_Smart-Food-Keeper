function loadTempHumGraph(){
    var lineTempHumGraphData = {
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
    };
    var lineTempHumGraphOptions = {
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
    };
    dataPlot = new Chart(document.getElementById("lineChartTempHum"), {
        type: 'line',
        data: lineTempHumGraphData,
        options: lineTempHumGraphOptions
    });
}
function removeData(){
    dataPlot.data.labels.shift();
    dataPlot.data.datasets[0].data.shift();
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

function loadRipenessGraphicsRealTime(unripe,ripe,overripe){
    console.log("Entrando a la funcion para cargar el grafico real time")
    var doughnutPieData = {
        datasets: [{
            data: [unripe, ripe, overripe],
            backgroundColor: [
                'rgba(24, 172, 145, 0.8)',
                'rgba(255, 206, 145, 0.8)',
                'rgba(231, 37, 37, 0.67)'
            ],
            borderColor: [
                'rgba(22,103,33,0.67)',
                'rgba(168, 166, 18, 0.67)',
                'rgba(157, 18, 18, 0.67)'
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
            type: 'pie',
            data: doughnutPieData,
            options: doughnutPieOptions
        });
    }
}