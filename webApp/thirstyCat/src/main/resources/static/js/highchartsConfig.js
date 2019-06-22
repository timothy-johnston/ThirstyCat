function testScript() {
    alert("success!");
}

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
            name: 'Jane',
            data: yAxis,
            color: '#796ad1'
        }]
    });
}

function createDrinkPerHourPerDay(drinkData, allDrinks) {

    var arrayDrinkPerHourPerDay = calculateDrinkPerHourPerDay(drinkData, allDrinks);

    console.log(arrayDrinkPerHourPerDay);

    

}

function calculateDrinkPerHourPerDay(drinkData, allDrinks) {

    var arrayDrinkPerHourPerDay = [];

    //Format data into 3 column array
    //[x,y,z] = [date, hourBucket, # of drinks]
    for (var i = 0; i < drinkData[0].length; i++) {

        var dateIteration = drinkData[0][i];
        var dateMidnight = new Date(dateIteration.getFullYear() + "-" + (dateIteration.getMonth() + 1) + "-" + dateIteration.getDate());
        var hourIncrement = 60 * 60 * 1000;
        var timeBucketWidth = 6;                      //Break day up into two hour chunks
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
            xyz[0] = dateMidnight;
            xyz[1] = bucketLowerBound;
            xyz[2] = drinkCount;

            arrayDrinkPerHourPerDay.push(xyz);

        }

    }

    return arrayDrinkPerHourPerDay;

}