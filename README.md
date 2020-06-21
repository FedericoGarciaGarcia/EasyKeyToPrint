<img align="left" width="80" height="80" src="https://raw.githubusercontent.com/FedericoGarciaGarcia/EasyKeyToPrint/development/source/images/icon.png" alt="Resume application project app icon">

# EasyKeyToPrint

An application to print screenshots made in Java.

## How to use

* Run the application. The application will appear in the tray.
* Right click the tray icon and select your printer and other settings.
* Press the PRINT SCREEN key.
* The screen will be printed.

## Features


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