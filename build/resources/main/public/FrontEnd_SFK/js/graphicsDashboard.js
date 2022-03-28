function loadStatsByHour(weightByHour){
    var ctx = document.getElementById("weightTodayChart");
    let weightByHourAux = [];
    let counter = 0;
    for(let i = 7; i <= 23; i++){
        weightByHourAux[counter] = weightByHour[i];
        counter = counter+1;
    }
    console.log("WeightByHour on loadStatsByHour: ", weightByHourAux);
    if (window.weightByHour) {
        window.weightByHour.clear();
        window.weightByHour.destroy();
    }
    window.weightByHour = new Chart(ctx, {
        type: 'line',
        data: {
            labels: ["7:00", "8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"],
            datasets: [{
                label: "Desperdicios (KG) ",
                lineTension: 0.3,
                backgroundColor: "#99FF99",
                borderColor: "#28a745",
                pointRadius: 5,
                pointBackgroundColor: "#28a745",
                pointBorderColor: "rgba(255,255,255,0.8)",
                pointHoverRadius: 5,
                pointHoverBackgroundColor: "#28a745",
                pointHitRadius: 50,
                pointBorderWidth: 2,
                data: weightByHourAux,
            }],
        },
        options: {
            scales: {
                xAxes: [{
                    time: {
                        unit: 'date'
                    },
                    gridLines: {
                        display: false
                    },
                    ticks: {
                        maxTicksLimit: 7
                    }
                }],
                yAxes: [{
                    ticks: {
                        min: 0,
                        max: 30,
                        maxTicksLimit: 8
                    },
                    gridLines: {
                        color: "rgba(0, 0, 0, .125)",
                    }
                }],
            },
            legend: {
                display: true
            }
        }
    });

}
function loadIncidents(incidents){
    var ctx = document.getElementById("incidentsTodayChart");
    let incidentsAux = [];
    let counter = 0;
    for(let i = 7; i <= 23; i++){
        incidentsAux[counter] = incidents[i];
        counter = counter+1;
    }
    console.log("WeightByHour on loadStatsByHour: ", incidentsAux);
    if (window.incidents) {
        window.incidents.clear();
        window.incidents.destroy();
    }
    window.incidents = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: ["Limite de temperatura permitido, Limite de humedad permitido, Frutas muy maduras, Suministro de frutas"],
            datasets: [{
                label: "Desperdicios (KG) ",
                data: [10,5],
            }],
        },
        options: {
            elements: {
                bar: {
                    borderWidth: 2,
                }
            },
            responsive: true,
            plugins: {
                legend: {
                    position: 'right',
                },
                title: {
                    display: true,
                    text: 'Chart.js Horizontal Bar Chart'
                },
                yAxes: [{
                    stacked:true,
                    ticks: {
                        min: 0,
                        beginAtZero: true,
                        max: 30,
                        maxTicksLimit: 8
                    },
                    gridLines: {
                        color: "rgba(0, 0, 0, .125)",
                    }
                }],
                xAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }],
            }
        },

    });

}
function prueba(){
    var MeSeContext = document.getElementById("incidentsTodayChart").getContext("2d");
    var MeSeData = {
        labels: [
            "Limite de temperatura excedido ",
            "Limite de humedad excedido",
            "Frutas en estado muy maduras",
            "Suministro de frutas"
        ],
        datasets: [{
            label: "Test",
            data: [10, 5,4,6],
            backgroundColor: ["#FF0000","#669911", "#119966","#0062ff"],
            hoverBackgroundColor: ["#66A2EB", "#FCCE56",]
        }]
    };

    var MeSeChart = new Chart(MeSeContext, {
        type: 'horizontalBar',
        data: MeSeData,
        options: {
            scales: {
                xAxes: [{
                    ticks: {
                        beginAtZero: true
                    }
                }],
                yAxes: [{
                    stacked: true
                }]
            }

        }
    });
}