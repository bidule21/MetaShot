Feature Review (what do we want it to do)
===========
Provide the ability for a user to view shooting records

Provide the ability for a user to view the shot records of any shooting record

Provide the ability for a user to create new shooting records

Provide the user the ability to manually enter GPS, Weather, Description and shots

* When manually entering shots, the only thing they can populate is the target property

Provide the user the ability to automatically populate the GPS and Weather

Provide the user the ability to start the metawear recording of shots

Provide the ability to create a shot record when the metawear identifies that a shot has been fired

Provide the ability for a user to delete a shot record

Provide the ability for a user to delete a shooting record

A record contains
* Date/time
* GPS
* Weather
* Description
* Type of Gun/Nickname?
* Shot Records

A Shot record contains
* Shoot number
* Graph of movement 3 seconds before and after shot. This could be an array of x,y,z values
* Temperature of the barrel???
* Target (x & y values)

Technical Review
=======
Native android or Hybrid?

* Hybrid would use ember, might be easier
* Android would use Java, might give us better control, lab exercise to work off of.

Do we want to support ads?

Do we want to mine data?
* Would require a Django API service

Where are we going to store user data?
* Do we store on device?
* - Not sure how difficult
* - Probably use sqllite, tutorial found
* Store data on web
* - Would require a Django API service 
* - Would use ajax call, similar to the ad server attack lab. 
* - Would make mining data easy
* - Would tie user to be connected to the internet.
* - Require account information

What Android API’s will we use
* GPS
* Weather API
* BlueTooth

What third party tool will we be using
* Chart view

What Meta Wear features will we use
* Accelerometer + Gyroscope
* Pressure (Barometer) ???
* Temperature??? Barrel temperature

Future Features If we have time
========
Facebook integration

Twitter integration

Instagram integration

Ability to take a picture of the target and save it to the shooting record

Based on ML data, suggest if bad aim was due to shaky movement of a shot

Hunting mode
* Allows for users to GPS location shot and hit
* Provide hunting related information such as sun rise and set times

Web Page interface (might be possible if we store all data, and do a hybrid app)

Allow users to view there shooting/shot records online.
* Would require us to store all data and credential management.

