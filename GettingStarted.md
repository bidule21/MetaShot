![logo](./misc/img/logo.png)
<div>(Logo made with <a href="https://www.designevo.com/" title="Free Online Logo Maker">DesignEvo</a>)</div>

## Hardware/Software Requirements
MetaShot is written for Android 8.0 (API level 26) with the minimum support of Android 7.0 (API level 24).

In order to utilize the "automatic recording of shots" functionality in the app, a MetaWear device is necessary. When purchasing a MetaWear device, ensure it contains, at minimum, the following sensors:
* Accelerometer 
* Temperature

MetaWear devices can be purchased from https://mbientlab.com/store/.

## Installation
To download the most recent release of MetaShot on your mobile device, navigate to this page on your mobile device and click on the provided download link [MetaShot APK](https://github.com/gandersonUNO/MetaShot/raw/master/misc/app-release.apk) or you can use scan the QR code provided to instantly down load the APK. For android to install unknown apps, be sure to opt-in for installing unknown sources, more information below. 

<img src="./misc/img/QR Code.PNG" />

NOTE:
* Android protects users from inadvertent download and install of unknown apps, or apps from sources other than Google Play, which is trusted. Android blocks such installs until the user opts into allowing the installation of apps from other sources. The opt-in process depends on the version of Android running on the device:
* On devices running Android 8.0 (API level 26) and higher, you must navigate to the Install unknown apps system settings screen to enable app installations from a particular location.
* On devices running Android 7.1.1 (API level 25) and lower, users should enable the Unknown sources system setting, found in Settings > Security on the devices.
* When you attempt to install an unknown app on a device running Android 7.1.1 (API level 25) or lower, the system sometimes shows a dialog that asks whether you want to allow only one particular unknown app to be installed. In almost all cases, you should allow only one unknown app installation at a time if the option is available to you.
Note: Some network providers don’t allow users to install applications from unknown sources.


## Getting Started
In order to use MetaShot to its full potentaial, the following permissions and services are needed:
* Location permission granted
  * Permission requested in app
* Internet service enabled 
  * Either WiFi or cellular network
* GPS service enabled
* Bluetooth service enabled if using MetaWear device

### Landing Page

| <img src="./misc/img/LandingPage.png" /> | <ul><li><p align=left>This is the main landing page for the app.</p></li><li><p align=left>A user can choose to:</p></li><ul><li><p align=left>Create a new shooting record</p></li><li><p align=left>View previous shooting records</p></li><li><p align=left>View and edit their weapon inventory</p></li></ul><li><p align=left>This is the page the user is redirected to whenever <br />they press the Home button (top left corner)</p></li></ul> |
|:--:|:----------------------------------------------------:|

### Create New Shooting Record
| <img src="./misc/img/CreateShootingRecord.png" /> | <ul><li><p align=left>This page is where the user can create a new shooting <br />record. A shooting record contains user defined <br />details about the shooting environment. Shot records <br />are later associated with a shooting record.</p></li><li><p align=left>All input filed are designed to accept a string input.<br />Non-string inputs (i.e.: integer, double, etc.) are <br />converted to a string prior to being saved to the database.<br />Any blank input fields at the time "CREATE" is clicked are <br />populated with the default string "Not Specified".</p></li><li><p align=left>The GPS and weather buttons (to the left of the <br />"GPS Location" and "Weather" fields) will auto-populate <br />the user's current GPS location and weather conditions <br />(temperature, wind speed, and wind direction) for the <br />user's current location.</p></li><ul><li><p align=left>GPS requires location permission to be granted <br />by the user and for the location service to be enabled.</p></li><li><p align=left>Weather requires a network connection (WiFi or <br />cellular network) to be enabled.</p></li></ul><li><p align=left>The weapon select dropdown is populated with weapons <br />the user has added to their weapon inventory.</p></li></ul> |
|:--:|:----------------------------------------------------:|

### Create New Shot Records
| <img src="./misc/img/CreateShotRecords.png" /> | <ul><li><p align=left>This page is where a user can create shot records <br />that are associated with a shooting record. After <br />creating a new shooting record, the user is automatically <br />redirected to this page. A user can also access this <br />page by adding shot records to an existing shooting record.</p></li><li><p align=left>To automatically record shots, the user needs a MetaWear device.</p></li><li><p align=left>If the user does not have or does not wish to use a MetaWear <br />device, shots can be manually created.</p></li><li><p align=left>If the shooting record already has associated shot records, <br />the shot records will be populated in a list on this page.</p></li></ul> |
|:--:|:----------------------------------------------------:|

### Manually Create Shot Records
| <img src="./misc/img/ManualShot.png" /> | <ul><li><p align=left>This page is where the user can manually create shot records.</p></li><li><p align=left>If a bullseye is hit, a button is available for the user to quickly <br />record the shot as a bullseye and continue shooting.</p></li><li><p align=left>The vertical and horizontal distance from target center input <br />fields are designed to only accept a positive number.</p></li><ul><li><p align=left>These input fields are also designed to accept the input <br />in units of inches.</p></li><li><p align=left>On a traditional shooting target, each ring going out <br />from the center of the bullseye is approximately one inch.</p></li></ul><li><p align=left>Radio buttons are provided for the user to indicate the vertical <br />and horizontal orientation from the target center.</p></li><li><p align=left>If vertical and/or horizontal distance inputs are missing, the user <br />is notified via pop-up dialog</p></li><li><p align=left>If there is no radio button selected for the vertical and/or horizontal <br />orientation, the user is notified via pop-up dialog.</p></li><li><p align=left>For a valid manual shot record to be created (other than clicking the <br />BULLSEYE button), the vertical and horizontal input fields must <br />contain a positive number and one of the two radio buttons must be <br />selected for both the vertical and horizontal orientation.</p></li></ul> |
|:--:|:----------------------------------------------------:|

### Automatically Generate Shot Records
| <img src="./misc/img/AutoShotNotConnected.png" /> | <ul><li><p align=left>Every Metawear device has its own MAC Address. <br />To find your devices MAC address, we recommend <br />getting the MetaBase app from the play store.<br />This will give you all the information you'll need <br />regarding your Metawear device.</p></li><li><p align=left>Once your Metawear device is connected, the start <br />and stop buttons will become active to begin <br />recording your shots.</p></li></ul> |
|:---------------------:|:----------------------------------------------------:|
| <img src="./misc/img/AutoShotConnected.png" /> | <strong><ul><li><p align=left>Pressing start will begin recording shots from your <br />Metawear device.</p></li><li><p align=left>Upon a successful shot, a list will be populated <br />where you can edit by selecting a record or delete a <br />shot by pressing and holding a record.</p></li><li><p align=left>To stop recording shots, you can press the Stop button.</p></li></ul></strong> |

### View Previous Shooting Records
| <img src="./misc/img/PreviousShootingRecords.png" /> | <ul><li><p align=left>This page is where the user can view previous shooting records.</p></li><li><p align=left>The naming convention is <br /><i>User Defined Record Name</i> - <i>Date Record Was Created</i></p></li><li><p align=left>Clicking on a shooting record allows the user to view the details <br />and shot records asscoated with that single shooting record.</p></li></ul> |
|:--:|:----------------------------------------------------:|

### View Single Previous Shooting Record
| <img src="./misc/img/SingleShootingRecord.png" /> | <ul><li>Instruction 1</li><li>Instruction 2</li><li>Instruction n</li></ul> |
|:--:|:----------------------------------------------------:|

### View/Edit Weapon Inventory
| <img src="./misc/img/WeaponInventory.png" /> | <ul><li><p align=left>This page is where the user can view and edit their <br />weapon inventory.</p></li><li><p align=left>If weapons have been previously added to the <br />inventory, a list of stored weapons will be populated.</p></li><li><p align=left>Clicking on a weapon in the list will allow the user <br />to edit the weapon's details.</p></li><li><p align=left>Clicking on the "ADD WEAPON" button allows the <br />user to add a new weapon to their inventory.</p></li></ul> |
|:--:|:----------------------------------------------------:|
| <img src="./misc/img/AddWeapon.png" /> | <strong><ul><li><p align=left>This page is where the user can add a weapon to their inventory.</p></li><li><p align=left>The user can provide a nickname for the weapon. This nickname is what appears <br />in the dropdown list when creating a new shooting record.</p></li><li><p align=left>The user can also add any additional details regarding the weapon <br />(i.e.: manufacturer, scope type, caliber, ammo used, etc.)</p></li><li><p align=left>If the user is editing an existing weapon, the "ADD WEAPON" button <br />will say "UPDATE".</p></li></ul></strong> |
