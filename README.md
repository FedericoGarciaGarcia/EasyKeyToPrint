<img align="left" width="80" height="80" src="https://raw.githubusercontent.com/FedericoGarciaGarcia/EasyKeyToPrint/development/source/images/icon.png" alt="Resume application project app icon">

# Easy Key To Print

An application to print screenshots made in Java.

## Features

* *Color*: grayscale or full color.
* *Paper orientation*: landscape or portrait.
* *Fit*: maintain aspect ratio or stretch to fit.
* *Taskbar*: show or hide the operating system's traskbar in the screenshot.
* *Printers*: select the printer to use. PDF printers are also valid.
* *Language*: English, Spanish or Japanese. More languages can be added by the user.
* *Save settings:* settings are saved every time.

More features on the way:

* *Do no print screenshot*: Hold the SHIFT key and press the PRINT SCREEN key to take a screenshot without printing.
* *Title*: place a title on the document.
* *Date*: place the current date on the document.
* *Paper size*: available sizes are A4, A3 and B5. Custom sizes are also accepted.
* *Notification*: show a notification when a screenshot is taken.

## How to use

* Run the application. It will appear in the tray.
* Right click the tray icon and select your printer and other settings.
* Press the PRINT SCREEN key.
* The screen will be printed.

### Windows

Download the *.exe* file in the *release* folder, place in your desired location and run.

### Linux and MAC

Download the *.jar* file in the *release* folder, place in your desired location and run.

### Source code

You can also download the source code and compile it yourself.

Place yourself in the *source* directory:

```
cd source
```

Compile with:

```
javac -cp ".;libs/jnativehook-2.1.0.jar" *.java

```

Run with:

```
java -cp ".;libs/jnativehook-2.1.0.jar" EasyKeyToPrint

```


# Author

Federico Garcia Garcia

# Acknowledgments

PageFormatFactory class made by:
* Object Refinery Ltd, Pentaho Corporation and Contributors