All specifications of this project have been implemented fully.
Supplementary comments for complex parts of the code are included
in the code.

------------
app package
------------

- In app.java are all the fields and methods needed for the app to run. It keeps the connection 
to the mqtt, the database and the gui, as well as the latest values of the IOT devices and the 
Android.
Data about the IOT devices is stored dynamically in Arraylist type.
Data about android devices is stored statically as a class object of Devices class.
Number of IOT devices that are to be created is configured from the settings file.

---------------
devices package
---------------

- In the Android.java are all the fields needed for the Android device. More specifically 
its topic, its battery and its x and y coordinates.

- In the IOT.java are all the fields needed for the IoT devices. More specifically its topic, 
the values it received from the gas, smoke, temperature and UV sensors, its x and y coordinates, 
its battery and if it has received values at dangerous levels.

----------------
database package
----------------

- In the SQLConnection.java happens the connection with and disconnection from the 
database. If no specific url, user and password is given, then the default values 
are "jdbc:mysql://127.0.0.1:3306/dangerdb?useSSL=false", "project" and "12341234" 
respectively.

- In the DatabaseManager.java can either happen the connection/disconnection 
(SQLConnection.java) or the execution of a query (SQLQueries.java). For the first, the 
methods connect and disconnect are used. For the latter, the methods that are used are:
1) initialize: A new table is created with name "dangerdb", if is does not already exist.
2) update: A new value is inserted to the table.
3) date_search: The values between two dates are printed.
4) danger_search: The values with a specific danger are printed.
5) delete: The rows with a specific value from one of the four types (smoke_value, gas_value, 
UV_value, temp_value) are deleted.

- In the SQLQueries.java happens the execution of a query.

------------
math package
------------

- The GeoDistance.java has a method for calculating the distance between two points, using the 
code from https://www.geodatasource.com/developers/java

------------
mqtt package
------------

- In the StringData.java, the message sent by the Iot or Android device is split in 
getValuesIoT or getValuesAndroid, respectively. In getValuesIoT, after checking the 
values to see if there is danger, the database is updated and a message is sent to 
the Android if needed.

- The highest value sent from each sensor is kept. If a value of a sensor surpasses the 
limits for it, then it is considered invalid and does not get taken into account. If a 
sensor does not sent any values or all of them are invalid, then its current value is 
the lower it can receive (for example, the smoke would have the value 0).

-----------
gui package
-----------

- In Gui.java, starts the graphic interface. It gets the updated values for the IOT devices 
and the Android, and displays them on the map with the right form.
BorderPane is used with one side having the WebView to load the google maps api and the other
is a box which contains buttons, which trigger specific methods of the GUI and the js scripts.
IOTs button starts a periodic task, which checks the main memory's data which are updated the main thread
Of the program.
ANDROID button acts similarly to the IOTs button
Stop Timelines stops the 2 previously mentioned tasks.
Lord CREATE the circle creates a circle. It is an Easter egg button. DO NOT USE IT.
Destroy polygon destroys the polygon created because of high danger. Used for debugging reasons. Polygons are
Destroyed during the timeline periodic tasks.
Mutexes are used to synchronise the 2 threads, when they access the shared data resources.

- The functions to change what the graphic interface shows are called with the executeScript, 
and are located in index.js and devices.js.

- In every update, the circles and rectangles, if existing, are deleted and then recreated, and 
the icons of the IOT devices are always updating.

----------------
settings package
----------------
- In Settings.java are stored some final static variables. The number of IOT devices, the ip and port for the mqtt broker to use,
as well the api key for the google maps api.






