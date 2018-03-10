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
As a user, I want to view the accelerometer data of my rifle before and after a shot has been fired, so I can focus on steadying my hand.
* A graph is displayed for 10 seconds before and after my shot has been fired.
* Multiple graphs are displayed if I fire multiple times.

As a user, I want to associate each shot with its accuracy on a target, so that I can determine if my scope needs adjusting.
* a input field is available for each entry/shot made to take negative and positive values

As a user, I want to store the weather & GPS location for the current day, so that I know if the weather or location effects my aim.
* Input fields exist to allow user to manually enter weather information
* Input fields exist to allow user to manually enter GPS information
* Input fields exist to allow user to enter a location description
* A button exists to manually input weather & GPS information


As a **user/role**, I want to **goal** so I can **rationale**.
**Acceptance Criteria:**
* Insert criteria 1 here
* Insert criteria 2 here
* etc.

## Misuser stories
As a **misuser/misuser-role**, I want to **misuse goal** so I can **bad rationale**.
**Mitigations:**
* Mitigation technique 1 to be used goes here
* Mitigation technique 2 to be used goes here
* etc.

## High Level Design
![Tooltip for visually disabled](./path-to-image-file.imgextension)

## Component List
### Component 1 Name here
Component description here

#### Sub-component 1.1 name here
Sub component description here

#### Sub-component 1.2 name here
Sub component description here

### Component 2 Name here
Component 2 description here

#### Sub-component 2.1 name here
Sub component description here

#### Sub-component 2.2 name here
Sub component description here

## Security analysis
Text describing high level diagram with red or other callouts identifying problem points or attacks.
![Tooltip for visually disabled](./path-to-image-file.imgextension)

| Component name | Category of vulnerability | Issue Description | Mitigation |
|----------------|---------------------------|-------------------|------------|
| Component 1 name | Privilege Escalation | This component exposes an interface | Sandboxing techniques|
