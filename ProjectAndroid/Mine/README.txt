ANDROID DEVICE

-----------------------------------
java Package
-----------------------------------
Most of the Activitis and Fragments in this Package check in all the necessary points whether the 
device has access to the internet. If not, the user is prompted to the software settings in order
to establish an internet connection.
==========================
ui.main Package
==========================
In this package there are the classes that manage the Fragments that are being used as the user
swipes between the 2 tabs. 
Tab 1: "Automatic Location" (here the application gathers the device's
location elements <Longtitude,Latitude> automatically through the GPS sensor).
Tab 2: "Manual Location" (here the application collects the location elements from the 2 CSV files
given for the purposes of the Project.


=======================
MainActivity is the Activity that synchronizes all features and activities in this application.
----------------------------------------------------------------------------------------------

sett (provides functionality to the layout: settings.xml)
---------------------------------------------------------
Here the user has two fields to fill,each with a different type of information.
One field for the IP address of the MQTT server , and another one for the Communication Port of the same server.
Once this info is provided, the user can push the Button Connect to, connect to the server that matches
this info. A pop out message is given to the user whether the attempt was successful or not.
Also on the Bottom of the Layout ,there is a Logout Button in order to Exit the App.
A Pop out message is also given here to confirm the wish of the user to exit.
If "Yes" is clicked, then the device disconnects from the server, and it exits the app.



stime (provides functionality to the layout : s_time.xml)
---------------------------------------------------------
Here the user has one field to fill. That of the time of the duration of the Measurement ,in case of
the Manual Location. Input must be integer.



Frag1 (provides functionality to the layout : frag1.xml)
---------------------------------------------------------

Here functionality is provided to Tab 1 for the automatic location of the device in question.
The user may push a given Button in order to gain his location details (Longtitude, Latitude), 
and followingly publish this info to the MQTT server ,that the device is connected to.

Frag2 (provides functionality to the layout : frag2.xml)
---------------------------------------------------------
Here functionality is provided to Tab 1 for the manual location of the device.c
The user pushes a button and then the application randomly selects one of the two given CSV files
to gain as many doubles of info (Longtitude, Latitude) as the user wishes, and has given in the Sample
Time Setting Area. For Example if the user has given the integer 10, then the application publishes to
the MQTT server 10 <Latitude, Longtitude> vectors taken from the CSV file selected. Default value (if the user
hasn't provided one) is that the device send all the data of the file to the server.

MyCallBack
--------------------

Basic Functionalities for the communication with the MQTT Server.



=================================================
Resources Package
=================================================
Drawable--------------------> some icons for the image of the app
Layout----------------------> The layouts which provide touch to the functions of the app.
Menu------------------------> Contains the pop-up menu ,with the options of "Settings" and "Sample Time"
Raw-------------------------> Contains the two CSV files given with the project