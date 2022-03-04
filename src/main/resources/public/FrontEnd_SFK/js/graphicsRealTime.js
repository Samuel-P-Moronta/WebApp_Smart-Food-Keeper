function loadRipenessGraphicsRealTime(unripe,ripe,overripe){
    console.log("Entrando a la funcion para cargar el grafico real time")
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
    if ($("#lineChartTempHum").length) {
        var lineChartTempHum = $("#lineChartTempHum").get(0).getContext("2d");
        var lineChartTempHum = new Chart(lineChartTempHum, {type: 'line', data: areaData, options: areaOptions});

        if(lineChartTempHum.data.labels.length !=15){
            lineChartTempHum.data.labels.push(data)
        }
    }
}