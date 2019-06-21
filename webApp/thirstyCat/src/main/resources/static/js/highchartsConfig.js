function testScript() {
    alert("success!");
}

function createCharts(drinkData, chartId) {

    //Determine function to call
    if (chartId == 0) {
        createDrinkVsDay(drinkData)
    }

}

function createDrinkVsDay(drinkData) {

    console.log("-------------in chart creation---------")

    console.log(drinkData);

    var xAxis = drinkData[0];
    var yAxis = drinkData[1];
    for (i = 0; i < xAxis.length; i++) {
        xAxis[i] = xAxis[i].toDateString().substring(4);
    }


    console.log(xAxis);

    var myChart = Highcharts.chart('graph-container', {
        chart: {
            type: 'column',
            backgroundColor: false
        },
        title: {
            text: 'Drinks Taken Per Day',
        	style: {
                fontSize: '2rem'
            }
        },
        xAxis: {
            categories: xAxis,
            title: {
            	enabled: 'true',
            	text: 'Date',
            	style: {
                    fontSize: '1.5rem'
                }
            }
        },
        yAxis: {
            title: {
                text: 'Number of Drinks',
            	style: {
                    fontSize: '1.5rem'
                }
            }
        },
        series: [{
            showInLegend: false,
            name: 'Jane',
            data: yAxis,
            color: '#796ad1'
        }]
    });

}