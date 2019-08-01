# ThirstyCat
##### A Raspberry Pi & Python powered cat detector + a full-stack Java and Spring Boot web app.
###### Featuring my wonderful cat, Shasta. Because, well, I take the term *pet* project very seriously.


## About

**ThirstyCat is a two part project, incorporating both hardware and software.**

**The hardware component** is a pressure-sensitive platform built with a Raspberry Pi, force sensing resistor, and PiCam and placed in front of Shasta's water bowl. A Python script continuously reads the sensor's pressure data waiting for the values to exceed some threshold; when the cat steps on the platform to take a drink, the threshold is exceeded, and the PiCam is triggered to snap a photo. The script then uses the Twitter API to tweet the photo from the @TheThirstyCat Twitter and persists it to a database (see the software description below).

**The software component** is a full-stack Java and Spring boot web application deployed on AWS with Elastic Beanstalk. The application's back end is a REST API secured with JWT Bearer Authentication allowing for the persistence and retrieval of the photos captured by the PiCam. The front end is a fairly simple HTML, CSS, and JavaScript UI secured with Basic Authentication allowing users to create an account, view and save the automatically captured photos, and view CatStats (CatStats:  Pretty graphs detailing Shasta's drinking habits!).

## Setup and Installation
### Hardware
I've previously put together a guide on how to set up the Raspberry Pi, you can check it out and follow along here:
https://github.com/timothy-johnston/timothy-johnston.github.io/blob/f6b6b4b173a30449ee70589b18a67e4837ba8eab/Old%20Jekyll%20Page/_posts/2018-07-17-how-i-built-a-cat-detecting-twitter-bot.markdown

### Software
Setup for this project is pretty simple and straightforward. You'll want to have installed Java, MySQL, and your IDE/editor of choice.
#### Database
All we need to do is create the database; Hibernate will handle the rest, including table creation. From the terminal, cd into /ThirstyCat/webApp/thirstyCat/database and run the following command:
```bash
./create-database.sh
```
Alternatively, you yourself can fire up a MySQL connection and manually create the database with
```sql
CREATE DATABASE thirstycat;
```
#### Java Source Code
Nothing too tricky here, just a couple environment variables to set up:

| Key  | Value Description |
| :------------: |:---------------:|
| JWT_CRED_1      | An admin JWT username |
| JWT_CRED_1      | An admin JWT password         |
| JWT_SECRET | Secret key to sign JWT        |

This app runs on Spring Boot's embedded Tomcat server, so no need to configure or set up a web server.  Additionally, no need to do anything special to connect the front & back ends, as that is handled by the Thymeleaf templating engine.
<br>
## THE END
That's it! Thanks for checking the project out. Feel free to reach out to me with any questions about the project; you can reach my on [My Twitter](https://twitter.com/TimJohnston_11 "Twitter") or [My LinkedIn](https://www.linkedin.com/in/timothyejohnston/ "My LinkedIn") at any time.


Now, go on and make an account at [https://www.thethirstycat.net](https://www.thethirstycat.net "https://www.thethirstycat.net") and start building your cat pic collection!

\- Tim (and Shasta the Cat)


