![logo](./misc/img/logo.png)
<div>(Logo made with <a href="https://www.designevo.com/" title="Free Online Logo Maker">DesignEvo</a>)</div>

## Executive Summary
It's opening day of rifle season for deer hunting. A novice hunter sees a trophy buck 100 yards out. The hunter spent months tweaking his/her scope settings to sight in their rifle at 100 yards. The hunter takes a deep breath and pulls the trigger. The shot misses, and the buck runs off. How did the shot miss when the rifle was accurately sighted?

A likely reason is that special equipment is commonly used to sight in a rifle. A bench rest is a piece of equipment that a rifle sets into for sighting purposes. The advantage of using a bench rest is the ability to eliminate human error as much as possible. This allows for tweaking a scope and sighting the rifle for perfect accuracy at a predetermined distance. The disadvantage of using a bench rest is diminishing human interaction with the rifle. When in the field, it is unlikely a hunter will use a bench rest. Therefore, no matter how accurately the rifle is sighted, human error can result in missing the perfect shot.

<img src="./misc/img/zeroing-in_1_rest.jpg">(Photo of a rifle bench rest used for sighting)

MetaShot is designed to enhance the traditional approach to sighting a rifle. A bench rest is still recommended as a first step to ensure the scope is accurately aligned. Then, MetaShot is attached. MetaShot leverages human error factors (such as pulling the rifle in one direction prior to firing) to further fine tune the sighting process and account for real-world situations. MetaShot also accounts for environmental variables (temperature, wind, etc.) and provides recommendations for scope adjustment. If the novice hunter was using MetaShot, he/she could have a trophy mount hanging above their fireplace.

## Project Goals
* Create a harness for the MetaShot device to attach to a rifle scope
* Develop native android app for recording, storing, and displaying data
* Provide reliable recommendations for scope adjustment based on environmental factors
  - Recommendations based on expert/industry-standard research
* Provide user ability to view past usage records

## User stories


As a user, I want to view previous shots, so I can view differences in days.
* A graph of accelerometer data for a span of three seconds before and after the shot is viewable
* The environmental data is viewable.
* The barrel temperature is viewable.

As a user, I want to associate each shot with its accuracy on a target, so that I can determine if my scope needs adjusting.
* An input field is available for each entry/shot made to take negative and positive values.

As a user, I want to store the environmental variables for the current day, so that I can adjust my scope to accommodate for the variables.
* Input fields exist to allow user to manually enter environmental variables.
* A button exists to automatically input environmental variables.

## Misuser stories
As an attacker, I want to inject a script through an ad server, so that I can steal user data.
* We will sanitize all incoming ads to mitigate Cross-Site Scripting.

As an environmental activist, I want to deploy a rogue Bluetooth access point, so that the app thinks it is connecting to the MetaShot device.
* We will ensure mobile device is only connecting to MetaShot approved device.
* We will not execute any functions that are returned from the MetaShot device.

## High Level Design
![Tooltip for visually disabled](https://www.lucidchart.com/publicSegments/view/0a675a0f-6ce4-43f5-98fc-0a1f10ebfb77/image.png)

## Component List
### MetaWear
* MetaMotionR+ is a motion tracking board that comes with Bluetooth, and ARM core, and various sensors.
* This component records sensor data and sends the data over Bluetooth to another component.

#### Accelerometer Sensor
* This sensor measures acceleration (i.e.: the rate of change in velocity).
* This subcomponent records rifle pull prior to firing a shot.

#### Gyroscope Sensor
* This sensor measures orientation.
* This subcomponent records rifle kickback.

#### Temperature Sensor
* This sensor measures ambient temperature.
* This subcomponent records rifle barrel temperature.

#### Barometer Sensor
* This sensor measures atmospheric pressure and altitude.
* This subcomponent records atmospheric pressure and altitude of the shooter's current location.

---

### Android Mobile Device
* Samsung Galaxy S7
  - Model Number: SM-G930P
  - Android Version: Nougat
* This component receives data from the MetaWear, Ad Server, and Weather Server components.
* The device displays and stores the data.

#### Bluetooth LE
* This subcomponent provides a means of communication between the MetaWear and Android Mobile Device components.

#### GPS API
* This subcomponent is the android native GPS API.
* This subcomponent is used for geotagging shooting records.

#### Local Storage Medium
* This subcomponent uses SQLite to create and store records in internal storage.
* This subcomponent reads from and writes to an external SD card.

---

### Ad Server
* This component is a third-party server that will retrieve and display ads.

---

### Weather Server
* This component is a third-party server that retrieves weather data for a given location.
  - The given location can be manually input or transmitted via GPS location.
* This component records data to shooting records and provides live weather conditions for recommended scope adjustments.

---

## Security analysis
![Tooltip for visually disabled](https://www.lucidchart.com/publicSegments/view/d4a249f9-782f-4444-b5ef-816b8e9e79dd/image.png)

| Diagram Mapping | Component Name | Category of Vulnerability | Issue Description | Mitigation |
|--------|----------------|---------------------------------|-------------------|------------|
| 1 |  MetaWear | Rogue Access Point | A rogue access point could impersonate this component. | Functions sent by any device identified as MetaWear will not be executed. |
| 2 | Android Mobile Device | Improper Data Validation | Unauthorized modification of locally stored data | I/O validation |
| 3 | Ad Server | Cross-Site Scripting | Ad server sends JavaScript functions in payload. | Sanitize input from ad server. |
| 4 | Weather Server | Cross-Site Scripting | Weather server send JavaScript functions in payload. | Sanitize input from weather server. |
