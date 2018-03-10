# MetaShot

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
* A input field is available for each entry/shot made to take negative and positive values.

As a user, I want to store the enviremental variables for the current day, so that I can adjust my scope to accommodate the variables.
* Input fields exist to allow user to manually enter enviremental variables.
* A button exists to automatically input enviremental variables information.

## Misuser stories
As an attacker, I want to Inject script through an ad server, so that i can steal user data.
* Sanitize all incoming ads for Cross-site Scripting.

As an enviremental activist, I deploy a rogue bluetooth access point, so that app thinks that its connecting to the metashot.
* Ensure that we are connecting to metashot approved device.
* We will not execute any functions that are returned from the metashot device.

## High Level Design
![Tooltip for visually disabled]<img src="https://www.lucidchart.com/publicSegments/view/f299fef4-df7a-4128-8ee8-1a863f3d3661/image.png">

## Component List
### Metawear
Component description here

#### Accelerometer Sensor
Sub component description here

#### Gyroscope Sensor
Sub component description here

#### Temperature Sensor
Sub component description here

#### Barometer Sensor
Sub component description here

---

### Android Mobile Device
Component 2 description here

#### Bluetooth LE
Sub component description here

#### GPS API
Sub component description here

#### Local Storage Medium
Sub component description here

---

### Ad Server
Component 3 description here

---

### Weather Server
Component 4 description here

---

## Security analysis
<img "src=https://www.lucidchart.com/publicSegments/view/9ef6d7c9-9bbc-4ee9-ae6e-42d66eacbda0/image.png">
![Tooltip for visually disabled](./path-to-image-file.imgextension)

| Diagram Mapping | Component Name | Category of Vulnerability | Issue Description | Mitigation |
|--------|----------------|---------------------------------|-------------------|------------|
| 1 |  Metawear | Rogue Access Point | A rogue access point could impersonate this component. | Functions sent by any device identified as Metawear will not be executed. |
| 2 | Android Mobile Device | Improper Data Validation | Unauthorized modification of locally stored data | I/O validation |
| 3 | Ad Server | Cross-Site Scripting | Ad server sends JavaScript functions in payload. | Sanitize input from ad server. |
| 4 | Weather Server | Cross-Site Scripting | Weather server send JavaScript functions in payload. | Sanitize input from weather server. |
