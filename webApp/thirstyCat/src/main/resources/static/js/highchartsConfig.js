function createCharts(drinkData, allDrinks, chartId) {

    //Determine function to call
    switch (chartId) {
        case 0:
            createDrinkVsDay(drinkData);
            break;
        case 1:
            createDrinkPerHourPerDay(drinkData, allDrinks);
            break;
    }

}

function createDrinkVsDay(drinkData) {

    //Create x (date) and y (drinks) axes
    var xAxis = drinkData[0];
    var yAxis = drinkData[1];
    for (i = 0; i < xAxis.length; i++) {
        xAxis[i] = xAxis[i].toDateString().substring(4);
    }

    //Make chart
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
            data: yAxis,
            color: '#796ad1'
        }]
    });
}

function createDrinkPerHourPerDay(drinkData, allDrinks) {

    var arrayDrinkPerHourPerDay = calculateDrinkPerHourPerDay(drinkData, allDrinks);
    var timeBuckets = ["Midnight - 3AM", "3AM - 6AM", "6AM - 9AM", "9AM - 12PM", "12PM - 3PM", "3PM - 6PM", "6PM - 9PM", "9PM - Midnight"];
    var dates = [];
    for (i = 0; i < drinkData[0].length; i++) {
        dates[i] = drinkData[0][i].toDateString().substring(4);
    }
    console.log(dates);

    Highcharts.chart('graph-container', {

        chart: {
          type: 'heatmap',
          marginTop: 40,
          marginBottom: 80,
          plotBorderWidth: 1,
          backgroundColor: false,
        },
      
        title: {
          text: 'Drink Timing - Heatmap',
          style: {
        	  fontSize: "2rem"
          } 
        },
      
        xAxis: {
//          categories: dates,
        	labels: {
        		enabled: false
        	},
          title: {
              text: null,
          	  style: {
                  fontSize: '1.5rem'
              }
          }
        },
      
        yAxis: {
          categories: timeBuckets,
          title: {
        	  text: 'Time Slice',
          	  style: {
                  fontSize: '1.5rem'
              }
          }
        },
        
        colorAxis: {
          min: 0,
          minColor: '#FFFFFF',
          maxColor: '#796ad1',
          reversed: true
        },
      
        legend: {
          align: 'right',
          layout: 'vertical',
          margin: 0,
          verticalAlign: 'top',
          y: 25,
          symbolHeight: 280
        },
      
        tooltip: {
          formatter: function () {
            return '<b>' + this.series.xAxis.categories[this.point.x] + '</b> sold <br><b>' +
              this.point.value + '</b> items on <br><b>' + this.series.yAxis.categories[this.point.y] + '</b>';
          }
        },
      
        series: [{
          name: 'Sales per employee',
          borderWidth: 6,
          borderColor: "FFFFFF",
          data: arrayDrinkPerHourPerDay,
          dataLabels: {
            enabled: true,
            color: '#000000'
          }
        }]
      
      });

}

function calculateDrinkPerHourPerDay(drinkData, allDrinks) {

    var arrayDrinkPerHourPerDay = [];

    //Format data into 3 column array
    //[x,y,z] = [date, hourBucket, # of drinks]
    for (var i = 0; i < drinkData[0].length; i++) {

        var dateIteration = drinkData[0][i];
        var dateMidnight = new Date(dateIteration.getFullYear() + "-" + (dateIteration.getMonth() + 1) + "-" + dateIteration.getDate());
        var hourIncrement = 60 * 60 * 1000;
        var timeBucketWidth = 3;                      //Break day up into two hour chunks
        var timeBuckets = 24 / timeBucketWidth;       //# of buckets = 24 hours in a day / chunk size

        //Loop over all hour buckets through day
        //Count drinks taking place in that slice of the day
        for (var j = 0; j < timeBuckets; j++) {

            var bucketLowerBound = new Date(dateMidnight.getTime() + hourIncrement * (j * timeBucketWidth));
            var bucketUpperBound = new Date(dateMidnight.getTime() + hourIncrement * ((j + 1) * timeBucketWidth));
            var drinkCount = 0;

            //Loop over drinks. When one falls in the current bucket, increment count
            for (var k = 0; k < allDrinks.length; k++) {

                var drinkStart = new Date(allDrinks[k].startTime);

                if (drinkStart >= bucketLowerBound && drinkStart < bucketUpperBound) {
                    drinkCount++;
                } else if (drinkStart >= bucketUpperBound) {
//                    break;
                }
            }

            var xyz = [];
            xyz[0] = i;
            xyz[1] = j;
            xyz[2] = drinkCount;

            arrayDrinkPerHourPerDay.push(xyz);

        }

    }

    return arrayDrinkPerHourPerDay;

}