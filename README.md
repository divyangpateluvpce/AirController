AirController
-------------
![Application Logo](Source%20Code/AirController/app/src/main/res/drawable/ic_new.png)
Wireless Keyboard and Mouse using Android Phone. Presentation Remote to use for Presentation from Laptop/ Computer. 

* Laptop and PC will connect with Android mobile using Wi-Fi socket connection.
* The Android application has a two-tab One-tab work as a Mouse and another Tab work as a keyboard.
* There is a Presentation Tab in the application it will work as a Presentation remote.

* On computer side we have to run a Java file it will accept the connection and command.
* It will perform and operation according to the command given by the Android application.

Requirements
------------
In order to Run Application and Server you need:
* jdk 1.8 or More (On Laptop/PC) 
* Android Version Device More than 4.0.3(Ice cream Sandwich).
* Accelerometer Sensor (On Mobile device).


Start The Server.
-----------------------
First, Download directory: 

Open CMD and Goto directory.

Command to run the Jar file.

    java -jar AirControllerServer.jar
	
Use Host and Port address to connect with Mobile Application.

Stop The Server.
-----------------------

Open New CMD window 

    jsp  
    
It will retuen list of java file running. Then find PID of running Process "*AirControllerServer.jar*".

    taskkill -f /PID /*PID of AirControllerServer.jar*/

![Command ](Documentation/image1.png)


On Mobile Device Installation
------------------------------
First, Download APK from Application Folder.

Save to your android device and Install in to Android Phone.


**Connect With server using Host and Port Address.** 

Enjoy AirController!
