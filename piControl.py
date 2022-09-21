
import config
import os
import time
import datetime
import Adafruit_MCP3008
import tweepy
import Tkinter
import urllib2
import requests
import json



urlRoot = 'http://thirstycat.us-east-1.elasticbeanstalk.com/api/'
urlAddDrink = 'drink/addDrink'
urlAddImage = 'image/addImage'
urlJWT = 'authenticateJWT'
dataFileName = "log1.txt"

# If data exists, read it in
if os.path.isfile("/home/pi/projects_2018/shastacam/data/" + dataFileName):
    dataFileExists = True   

    #Open the file
    file = open("/home/pi/projects_2018/shastacam/data/" + dataFileName, "a+")
    
    #Store each line in the contentsList array
    contentsList = file.readlines()
    
    #Parse the latest entry into the mostRecentRecord variable
    mostRecentRecord = contentsList[-1]
    print "HELLO"
    
    #Parse information that is needed for later calculations / file writing
    timeStampStartLast = mostRecentRecord.split(",")[0]
    yearMonthDayLast = timeStampStartLast.split(" ")[0]
    # monthStartLast = yearMonthDayLast.split("-")[1]
    usesThisDay = int(mostRecentRecord.split(",")[2])
    # usesThisMonth = int(mostRecentRecord.split(",")[4])
    # totalUses = int(mostRecentRecord.split(",")[5])
    
    #To determine how many days of data have been collected this month, iterate over the list of records
    # for record in contentsList:
        #Get the timestamp, month, and day of the current record
        # fullTimestampCurrentRecord = record.split(",")[0]
        # yearMonthDayCurrentRecord = fullTimestampCurrentRecord.split(" ")[0]
        # monthCurrentRecord = yearMonthDayCurrentRecord.split("-")[1]
	
	#Check if the month of the current record matches the current month
	#If so, save the day as firstDayWithDataThisMonth. This will be used for averaging later (total days = thisDay - firstDay)
	#If no records with this month exist, the firstDayWithDataThisMonth will be the current day
        # fullTimestampToday = datetime.datetime.now().isoformat(' ')
        # yearMonthDayToday = fullTimestampToday.split(" ")[0]
	# monthToday = yearMonthDayToday.split("-")[1]
	# dayToday = yearMonthDayToday.split("-")[2]
	# firstDayWithDataThisMonth = dayToday
	# totalDays = 0
	# if monthCurrentRecord == monthToday:
	    # firstDayWithDataThisMonth = yearMonthDayCurrentRecord.split("-")[2]
	    # totalDays = int(dayToday) - int(firstDayWithDataThisMonth) + 1
#	    avgUsesPerDayThisMonth = totalUses / totalDays
	    # break
    #Close the file
    file.close()
#If the data file doesn't exist yet (first time running the program), create it
else:
    dateStartLast = datetime.datetime.now().isoformat(' ')
    yearMonthDayLast = dateStartLast.split(" ")[0]
    # monthStartLast = dateStartLast.split("-")[1]
    usesThisDay = 0
    # usesThisMonth = 0
    # totalUses = 0
    # totalDays = 0
    # avgUsesPerDayThisMonth = 0


# Allow write permissions for log file
os.system("sudo chmod -R 777 /home/pi/projects_2018/shastacam/data")

# Configure Twitter api
consumer_key = config.consumer_key
consumer_secret = config.consumer_secret
access_token = config.access_token
access_token_secret = config.access_token_secret

auth = tweepy.OAuthHandler(consumer_key, consumer_secret)
auth.set_access_token(access_token, access_token_secret)
api = tweepy.API(auth)

# Configure FSR parameters
CLK  = 18
MISO = 23
MOSI = 24
CS   = 25
mcp = Adafruit_MCP3008.MCP3008(clk=CLK, cs=CS, miso=MISO, mosi=MOSI)
channel = 0
threshold = 415

delayTime = 15    #Wait when going above/below threshold
incTime = 1
firstTimeInLoop = True

value = mcp.read_adc(channel)

# Program runs infinitely
while True:

    # Control whether data should be written at end of loop
    catWasHere = False
    
    # Read FSR value
    lastValue = value
    value = mcp.read_adc(channel)   

    # If FSR goes above threshold, take a photo and check again
    if value >= threshold:
	os.system("raspistill -w 1024 -h 768 -rot 0 -n -q 10 -o catWasHere.jpg")       
	timeStampStartDatetime = datetime.datetime.now()
	fullTimeStampStart = datetime.datetime.now().isoformat(' ')
	yearMonthDay = fullTimeStampStart.split(" ")[0]
	# monthStart = yearMonthDay.split("-")[1]
        # dayStart = yearMonthDay.split("-")[2]
	#time.sleep(delayTime)
        value = mcp.read_adc(channel)
       
	 # If FSR still above threshold, assume cat is on platform
        if value >= threshold:
	#if value - lastValue > 15:
	    catWasHere = True
            # totalUses = totalUses + 1

            if yearMonthDayLast == yearMonthDay:
                usesThisDay = usesThisDay + 1
            else:
                usesThisDay = 1
		# totalDays = totalDays + 1
#		avgUsesPerDayThisMonth = totalUses / totalDays

	    # if monthStartLast == monthStart:
		# usesThisMonth = usesThisMonth + 1
	    # else:
		# usesThisMonth = 1
		# totalDays = 1
	
	    # Assign the current date and month to dateStartLast and monthStartLast for comparison to the next time the cat is here
            yearMonthDayLast = yearMonthDay
	    # monthStartLast = monthStart

            # Wait until cat finishes drinking to move on
            #while value - lastValue > 15:
	    while value >= threshold:
                value = mcp.read_adc(channel)
                time.sleep(delayTime)

    # If cat was on platform, write data
    if catWasHere:
        catWasHere = False
        timeStampEndDatetime = datetime.datetime.now()
	# duration = timeStampEndDatetime - timeStampStartDatetime
	
	#Calculate average uses per month
        lineToWrite = str(timeStampStartDatetime) + "," + str(timeStampEndDatetime) + "," + str(usesThisDay) + "\n"
	
	#Open and write to file
	file = open("/home/pi/projects_2018/shastacam/data/" + dataFileName,"a+")
        file.write(lineToWrite)
        file.close()
        print "MOST RECENT USE:"
        print lineToWrite
	
    	#Send data to REST API to add it to database

	#Get JWT Token
	jwtPath = urlRoot + urlJWT
	tokenRequestJSON = {'username':'TC_ADMIN_B', 'password':config.pi_pass}
	tokenResponse = requests.post(jwtPath, json=tokenRequestJSON)
	tokenResponseJSON = json.loads(tokenResponse.text)
	bearerToken = 'Bearer ' + tokenResponseJSON['token']

	print bearerToken


	#Drink data
	requestStartTime = str(timeStampStartDatetime).replace(" ", "T")
	requestEndTime = str(timeStampEndDatetime).replace(" ", "T")
	drinkInfo = {
		'startTime': requestStartTime,
		'endTime': requestEndTime,
		'createdBy': 'pi'
			}
	headers = {'Content-Type':'application/json', 'Authorization':'Bearer ' + tokenResponseJSON['token']}

	print drinkInfo
	print headers

	requestResponseDrink = requests.post(urlRoot + urlAddDrink, json=drinkInfo, headers=headers)
	
	print requestResponseDrink
	print requestResponseDrink.text

	requestResponseDrinkJSON = json.loads(requestResponseDrink.text)
	drinkId = requestResponseDrinkJSON['id']

	#Image data
	photoPath = "/home/pi/projects_2018/shastacam/catWasHere.jpg"	
	photo = open(photoPath, 'rb')
	photoBytes = photo.read()
	multipartKeyValue = {'image' : photoBytes}
	imageRequestBody = {'createdBy':'pi', 'drinkId':drinkId}
	headers = {'Authorization':bearerToken}
	requestResponse = requests.post(urlRoot + urlAddImage, files=multipartKeyValue, data=imageRequestBody, headers=headers)

	#Drink data
#	requestStartTime = str(timeStampStartDatetime).replace(" ", "T")
#	requestEndTime = str(timeStampEndDatetime).replace(" ", "T")
#	drinkInfo = {
#		'startTime': requestStartTime,
#		'endTime': requestEndTime,
#		'createdBy': 'pi'
 #      		 }
#	headers = {'Content-Type':'application/json', 'Authorization':bearerToken}
#	requestResponseDrink = requests.post(urlRoot + urlAddDrink, json=drinkInfo, headers=headers)

	#Format output
        tableOutput = "Time: " + str(fullTimeStampStart) + "\nNumber of drinks today:  " + str(usesThisDay) + "\n"
	print tableOutput

	# Share to twitter and follow any new followers
	twitterMessage = "Alert, cat detected! Shasta just took a drink.\n" + tableOutput
#        for follower in tweepy.Cursor(api.followers).items():             #Follow logic commented out temporarily - some issue with Twitter capping number of follows
#	    follower.follow()
	try:
	    api.update_with_media(photoPath, twitterMessage)
	except Exception as e:
	    print "EXCEPTION. TWITTER FAIL."
	    print e

    # Pause
    print "Finished loop at " + str(datetime.datetime.now()) +", FSR reading was: " + str(value) + ", threshold is: " + str(threshold)
    time.sleep(incTime)
    #threshold = lastValue + 100
