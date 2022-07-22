Fragment 1 - 4
Class fragment 1:
Displays SMOKE SENSOR panel
In that panel we have a slider with a range of the sensor.
a switch button and the sensor name.
Slider and switch button are static and don't work dynamicaly.
We select the range for the measurement with edit text and a button,
in the java class file of the specific sensor we find the sensor measurement 
with random function and if the value is below the asked range, then 
value passed is 0.

Fragments 2,3,4 are following the template of fragmnet 1

The drop down menu is displaed in the top right corner above the fragments of the sensors
and expands to the following options: Settings,Send Position, Add Sensor and Logout

Settings:
Opens up a new activity for setting up the IP and Port.
Also is used for connecting and publish data to a topic and then to Server.
We pass all the measurement to the MQTT server. The message to the MQTT contains,
measurement from the sensors, battery level and the cordinates X,Y of the device.

Send Position:
Send Position also expands to two sub-options: Automatic and Manual
Automatic: With the click of GET LOCATION button we get the curent location of the device.
We have the code for this part on the internet.
Manual: With option we select one of the four fixed locations from a spiner widget and we 
pass the value to option 1

Add Sensor:
Is temporarily out of order.
We select the sensor type and seting up the range and we add the sensor to a new fragment
with buton. This activity is static.

Logout:
This option is used for terminating the app. When we press Logout a pop up window 
apears and ask us if we want to close the app.
