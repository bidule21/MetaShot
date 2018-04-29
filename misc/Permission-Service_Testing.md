# Permission/Service Test Plan

### "Dangerous" Permissions Needed:
"Dangerous" permissions require the user to explicity grant/deny permission. User also has ability to later revoke/grant the permission.
* ACCESS_FINE_LOCATION

### "Normal" Permissions Needed:
"Normal" permissions are automatically granted by the system at install time. User is not prompted to grant permission, and permission cannot be denied/revoked.
* ACCESS_NETWORK_STATE
* INTERNET

### Services Needed:
* Location
* Internet
* Bluetooth

### Expected Test Plan Behavior
* Overall expected behavior is for the method to execute properly or to fail gracefully.
* If permission is granted and service is enaled, method is expected to execute properly.
* If permission is granted and service is disabled, dialog asking user to enable service is expected to appear.
    * Upon accepting request, user is redirected to appropriate settings page to enable service.
* If permission is not granted, a dialog requesting user permission is expected to appear.
    * If user grants permission, method is expected to execute properly.
    * If user denys permission, dialog informing user that method cannot complete is expected to appear.
    
### Test Cases
| Activity | Method | Permission Needed? | Permission Granted/Not Granted? | Service Needed? | Service Enabled/Disabled? | Testing Result |
| -------- | ------- | ------------------ | ------------------------------- | --------------- | ------------------------ | -------------- |
| NewShootingRecord | getGPSLocation | ACCESS_FINE_LOCATION | Granted | Location | Enabled | PASS |
| NewShootingRecord | getGPSLocation | ACCESS_FINE_LOCATION | Granted | Location | Disabled | PASS |
| NewShootingRecord | getGPSLocation | ACCESS_FINE_LOCATION | Not Granted | Location | Enabled | PASS |
| NewShootingRecord | getGPSLocation | ACCESS_FINE_LOCATION | Not Granted | Location | Disabled | PASS |

| Activity | Method | Permission Needed? | Permission Granted/Not Granted? | Service Needed? | Service Enabled/Disabled? | Testing Result |
| -------- | ------- | ------------------ | ------------------------------- | --------------- | ------------------------ | -------------- |
| NewShootingRecord | getWeatherDetails | ACCESS_FINE_LOCATION<br /><br />INTERNET | Granted<br /><br />Granted (default) | Location<br /><br />Internt | Enabled<br /><br />Enabled | PASS |
| NewShootingRecord | getWeatherDetails | ACCESS_FINE_LOCATION<br /><br />INTERNET | Granted<br /><br />Granted (default) | Location<br /><br />Internt | Disabled<br /><br />Enabled | PASS |
| NewShootingRecord | getWeatherDetails | ACCESS_FINE_LOCATION<br /><br />INTERNET | Granted<br /><br />Granted (default) | Location<br /><br />Internt | Enabled<br /><br />Disabled | PASS |
| NewShootingRecord | getWeatherDetails | ACCESS_FINE_LOCATION<br /><br />INTERNET | Granted<br /><br />Granted (default) | Location<br /><br />Internt | Disabled<br /><br />Disabled | PASS |
| NewShootingRecord | getWeatherDetails | ACCESS_FINE_LOCATION<br /><br />INTERNET | Not Granted<br /><br />Granted (default) | Location<br /><br />Internt | Enabled<br /><br />Enabled | PASS |
| NewShootingRecord | getWeatherDetails | ACCESS_FINE_LOCATION<br /><br />INTERNET | Not Granted<br /><br />Granted (default) | Location<br /><br />Internt | Disabled<br /><br />Enabled | PASS |
| NewShootingRecord | getWeatherDetails | ACCESS_FINE_LOCATION<br /><br />INTERNET | Not Granted<br /><br />Granted (default) | Location<br /><br />Internt | Enabled<br /><br />Disabled | PASS |
| NewShootingRecord | getWeatherDetails | ACCESS_FINE_LOCATION<br /><br />INTERNET | Not Granted<br /><br />Granted (default) | Location<br /><br />Internt | Disabled<br /><br />Disabled | PASS |

| Activity | Method | Permission Needed? | Permission Granted/Not Granted? | Service Needed? | Service Enabled/Disabled? | Testing Result |
| -------- | ------- | ------------------ | ------------------------------- | --------------- | ------------------------ | -------------- |
| NewShotRecord_AutoRecord | onClickConnect<br />retrieveBoard | N/A | N/A | Bluetooth | Enabled | PASS |
| NewShotRecord_AutoRecord | onClickConnect<br />retrieveBoard | N/A | N/A | Bluetooth | Disabled | PASS |
