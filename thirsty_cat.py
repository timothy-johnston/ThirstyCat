import config
import os
import time
import datetime
import Adafruit_MCP3008
import tweepy
import Tkinter


# If data exists, read it in. Otherwise, create new file
if os.path.isfile("/home/pi/projects_2018/shastacam/data/thirstyCatDataLog.txt"):
    file = open("/home/pi/projects_2018/shastacam/data/thirstyCatDataLog.txt", "a+")
    contentsList = file.readlines()
    mostRecentRecord = contentsList[-1]
    print "HELLO"
    # Error here. How to convert string to datetime?
    timeStampStartLast = mostRecentRecord.split(",")[0]
    dateStartLast = timeStampStartLast.split(" ")[0]
    drinksThisDay = int(mostRecentRecord.split(",")[3])
    totalDrinks = int(mostRecentRecord.split(",")[4])
    file.close()
else:
    file = open("/home/pi/projects_2018/shastacam/data/thirstyCatDataLog.txt","a+")
    file.write("timeStart, timeEnd, duration, drinksThisDay, totalDrinks\n")
    file.write("0,0,0,0,0\n")
    timeStampStartLast = datetime.datetime.now()
    dateStartLast = datetime.datetime.now().isoformat(' ')
    dateStartLast = dateStartLast.split(" ")[0]
    drinksThisDay = 0
    totalDrinks = 0
    file.close()

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
threshold = 450
delayTime = 15
incTime = 1

# Program runs infinitely
while True:

    # Control whether data should be written at end of loop
    catWasDrinking = False
    
    # Read FSR value
    value = mcp.read_adc(channel)

    # If FSR goes above threshold, delay several seconds and check again
    if value >= threshold:
        timeStampStart = datetime.datetime.now()
	dateStart = datetime.datetime.now().isoformat(' ')
	dateStart = dateStart.split(" ")[0]
        time.sleep(delayTime)
        value = mcp.read_adc(channel)
        # If FSR still above threshold, assume cat is drinking
        if value >= threshold:
            catWasDrinking = True
            os.system("raspistill -n -rot -90 -q 10 -o catWasHere.jpg")
            totalDrinks = totalDrinks + 1

            if dateStartLast == dateStart:
                drinksThisDay = drinksThisDay +1
            else:
                 drinksThisDay = 1
	
            dateStartLast = dateStart

            # Wait until cat finishes drinking to move on
            while value >= threshold:
                value = mcp.read_adc(channel)
                time.sleep(incTime)

    # If cat was drinking, write data
    if catWasDrinking:
        catWasDrinking = False
        timeStampEnd = datetime.datetime.now()
	duration = timeStampEnd - timeStampStart
        lineToWrite = str(timeStampStart) + "," + str(timeStampEnd) + "," + str(duration) + "," +  str(drinksThisDay) + "," + str(totalDrinks) + "\n"
        twitterMessage = "Shasta just took a drink!\nNumber of times she's taken a drink today: " + str(drinksThisDay)
	file = open("/home/pi/projects_2018/shastacam/data/thirstyCatDataLog.txt", "a+")
        file.write(lineToWrite)
        file.close()
        print "MOST RECENT DRINK:"
        print lineToWrite

	# Share to twitter and follow any new followers
        for follower in tweepy.Cursor(api.followers).items():
	    follower.follow()
        photoPath = "/home/pi/projects_2018/shastacam/catWasHere.jpg"
	api.update_with_media(photoPath, twitterMessage)

    # Pause
    print "Finished loop at " + str(datetime.datetime.now())
    time.sleep(incTime)
        
        









    
