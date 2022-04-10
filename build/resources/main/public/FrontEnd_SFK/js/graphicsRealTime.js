var dataplot;
var dataplotWeight;
var maxDataPoints = 15;

function loadTempHumGraph(){
    var lineTempHumGraphData = {
        labels: [],
        datasets: [{
            data: [20,30],
            label: "Temperatura (*C)",
            borderWidth: 2,
            pointBorderWidth: 2,
            pointRadius: 4,
            pointBorderColor: "#fff",
            pointBackgroundColor: "#0162ff",
            borderColor: "#3e95cd",
            backgroundColor: 'rgba(33, 76, 229, 0.5)',
            fill: false
        },{
            data: [10,25],
            label: "Humedad (%)",
            borderWidth: 2,
            pointBorderWidth: 2,
            pointRadius: 4,
            pointBorderColor: "#fff",
            backgroundColor: 'rgba(255, 99, 71, 0.5)',
            borderColor: "#f44252",
            fill: false
        }]
    };
    var lineTempHumGraphOptions = {
        responsive: true,
        legend: {
            position: 'top',
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
                },
                scaleLabel: {
                    display: true,
                    labelString: 'Temperatura (ªC) & Humedad (%)'
                }
            }]
        },
        elements: {
            point: {
                radius: 1
            },
            line: {
                tension: 0
            }
        },
    };
    dataPlot = new Chart(document.getElementById("lineChartTempHum"), {
        type: 'line',
        data: lineTempHumGraphData,
        options: lineTempHumGraphOptions
    });
}
function loadWeightGraph(){
    var lineWeightData = {
        labels: [],
        datasets: [{
            data: [],
            label: "Peso (Kg)",
            borderColor: "#3e95cd",
            backgroundColor: 'rgba(33, 76, 229, 0.5)',
            steppedLine: 'before',
            fill: false
        }]
    };
    var lineWeightOption = {
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
                    max: 8,
                    min:0
                }
            }]
        },
    };
    var plugins = {
        afterDraw: chart => {
                var ctx = chart.chart.ctx;
                var xAxis = chart.scales['x-axis-0'];
                var yAxis = chart.scales['y-axis-0'];
                var dataset = chart.config.data.datasets[0];
                var data = dataset.data;
                var xFrom = xAxis.getPixelForTick(data.length - 1);
                var xTo = xAxis.getPixelForTick(data.length);
                var y = yAxis.getPixelForValue(data[data.length - 1]);
                ctx.save();
                ctx.strokeStyle = dataset.borderColor;
                ctx.lineWidth = dataset.lineWidth;
                ctx.setLineDash([8, 4]);
                ctx.beginPath();
                ctx.moveTo(xFrom, y);
                ctx.lineTo(xTo, y);
                ctx.stroke();
                ctx.restore();
            }
    }
    dataplotWeight = new Chart(document.getElementById("lineChartWeight"), {
        type: 'line',
        data: lineWeightData,
        options: lineWeightOption,
        plugins: plugins
    });
}
function removeData(){
    dataPlot.data.labels.shift();
    dataPlot.data.datasets[0].data.shift();
}
function removeDataWeight(){
    dataplotWeight.data.labels.shift();
    dataplotWeight.data.datasets[0].data.shift();
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
function addDataContainer(label, weight) {
    if(dataplotWeight.data.labels.length > maxDataPoints){
        removeDataWeight();
    }
    dataplotWeight.data.labels.push(label);
    dataplotWeight.data.datasets[0].data.push(weight);
    dataplotWeight.update();
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
        var doughnutChartRipenessCanvas = new Chart(doughnutChartRipenessRealTime, {
            type: 'pie',
            data: doughnutPieData,
            options: doughnutPieOptions
        });

        Chart.plugins.register({
            afterDraw: function(doughnutChartRipenessCanvas) {
                if (doughnutChartRipenessCanvas.data.datasets[0].data.every(item => item === 0)) {
                    let ctx = doughnutChartRipenessCanvas.chart.ctx;
                    let width = doughnutChartRipenessCanvas.chart.width;
                    let height = doughnutChartRipenessCanvas.chart.height;

                    doughnutChartRipenessCanvas.clear();
                    ctx.save();
                    ctx.textAlign = 'center';
                    ctx.textBaseline = 'middle';
                    ctx.fillText('No data to display', width / 2, height / 2);
                    ctx.restore();
                }
            }
        });
    }
}