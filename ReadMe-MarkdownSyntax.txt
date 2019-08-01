# ThirstyCat
##### A Raspberry Pi & Python powered cat detector + a full-stack Java and Spring Boot web app.
###### Featuring my wonderful cat, Shasta. Because, well, I take the term *pet* project very seriously.

<br>
### About
------------
**ThirstyCat is a two part project, incorporating both hardware and software.**

**The hardware component** is a pressure-sensitive platform built with a Raspberry Pi, force sensing resistor, and PiCam and placed in front of Shasta's water bowl. A Python script continuously reads the sensor's pressure data waiting for the values to exceed some threshold; when the cat steps on the platform to take a drink, the threshold is exceeded, and the PiCam is triggered to snap a photo. The script then uses the Twitter API to tweet the photo from the @TheThirstyCat Twitter and persists it to a database (see the software description below).

**The software component** is a full-stack Java and Spring boot web application deployed on AWS with Elastic Beanstalk. The application's back end is a REST API secured with JWT Bearer Authentication allowing for the persistence and retrieval of the photos captured by the PiCam. The front end is a fairly simple HTML, CSS, and JavaScript UI secured with Basic Authentication allowing users to create an account, view and save the automatically captured photos, and view CatStats (CatStats:  Pretty graphs detailing Shasta's drinking habits!).

Now the fun stuff
Build the pi
https://github.com/timothy-johnston/timothy-johnston.github.io/blob/f6b6b4b173a30449ee70589b18a67e4837ba8eab/Old%20Jekyll%20Page/_posts/2018-07-17-how-i-built-a-cat-detecting-twitter-bot.markdown
