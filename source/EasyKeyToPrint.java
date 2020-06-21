/**
 * Author: Federico Garcia Garcia
 * License: GPL-3.0
 * Created on: 21/06/2020 1:30
 */

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.concurrent.AbstractExecutorService;
import java.util.List;
import java.util.ArrayList;

import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.print.*;

import java.util.HashMap;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

public class EasyKeyToPrint implements NativeKeyListener  {
    
    // Flag for key press
    private boolean printHasBeenPressed;
    
    // Printers
    private PrintService[] printServices;
    private HashMap<String, PrintService> printersMap;
    private String currentPrinter;
    
    // Robot
    private Robot robot;
    
    // Properties
    public static int COLOR_BLACK_AND_WHITE = 0;
    public static int COLOR_COLOR           = 1;
    public static int FIT_MAINTAIN_ASPECT_RATIO = 0;
    public static int FIT_SCALE                 = 1;
    
    // Start everything
    public EasyKeyToPrint() {
        /*
         * VARIABLES
         */
        printHasBeenPressed = false;
        printersMap = new HashMap<String, PrintService>();
        robot = null;
        try {
            robot = new Robot();
        }
        catch(Exception e) {
            System.out.println("Could not create robot");
            e.printStackTrace();
            System.exit(1);
        }
    
        /*
         * INIT EVERYTHNING
         */
        initPriters();
        hookKeyboard();
        createTrayIconSystem();
    }
    
    // Get available printers
    // Create dictionary of printers
    private void initPriters() {
        printServices = PrintServiceLookup.lookupPrintServices(null, null);
        System.out.println("Number of print services: " + printServices.length);

        for (PrintService printer : printServices) {
            System.out.println("  "+printer.getName());
            printersMap.put(printer.getName(), printer);
        }
    }
        
    // Init keyboard interception
    private void hookKeyboard() {
        try {
            Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
            logger.setLevel(Level.OFF);

            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(this);
        }
        catch(Exception e) {
            System.out.println("Could not hook keyboard");
            e.printStackTrace();
            return;
        }
        
        System.out.println("Successfully hooked keyboard");
    }
    
    // Create tray icon system
    private void createTrayIconSystem() {
        // Check the if the SystemTray is supported
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        // If it is, work
        else {
            // Create popup menu
            final PopupMenu popup = new PopupMenu();
            
            // Null Image for now
            Image image = null;
            Image imageblackandwhite = null;
            ImageIcon blackandwhite = null;
            
            // Try to load the image
            try {
                image = ImageIO.read(getClass().getResource("/images/icon.png"));
                imageblackandwhite = ImageIO.read(getClass().getResource("/images/blackandwhite.png"));
                blackandwhite = new ImageIcon(imageblackandwhite);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            // Get the OS tray image size
            final SystemTray tray = SystemTray.getSystemTray();
            Dimension trayIconSize = tray.getTrayIconSize();
            
            // Rescale image to that size
            image = image.getScaledInstance(trayIconSize.width, trayIconSize.height, Image.SCALE_SMOOTH);
            
            // Create tray icon system
            final TrayIcon trayIcon = new TrayIcon(image, "EasyKeyToPrint");
            
            // Create a pop-up menu components
            Menu colorMenu     = new Menu("Color");
                CheckboxMenuItem checkboxMenuItemBlackAndWhite = new CheckboxMenuItem("Black and white");
                CheckboxMenuItem checkboxMenuItemColor         = new CheckboxMenuItem("Color");
            MenuItem titleItem = new MenuItem("Title");
            MenuItem dateItem  = new MenuItem("Date");
            Menu orientationMenu  = new Menu("Orientation");
                CheckboxMenuItem checkboxMenuItemLandscape  = new CheckboxMenuItem("Landscape");
                CheckboxMenuItem checkboxMenuItemPortrait   = new CheckboxMenuItem("Portrait");
            Menu fitMenu  = new Menu("Fit");
                CheckboxMenuItem checkboxMenuItemAspectRatio = new CheckboxMenuItem("Maintain aspect ratio");
                CheckboxMenuItem checkboxMenuItemStretch     = new CheckboxMenuItem("Stretch");
            Menu printerMenu   = new Menu("Printers");
            
            Menu languageMenu  = new Menu("Language");
                CheckboxMenuItem checkboxMenuItemEnglish  = new CheckboxMenuItem("English");
                CheckboxMenuItem checkboxMenuItemJapanese = new CheckboxMenuItem("Japanese");
            MenuItem helpItem  = new MenuItem("Help");
            MenuItem aboutItem = new MenuItem("About");
            
            MenuItem exitItem  = new MenuItem("Exit");
            
            // Create each printer menu item
            final CheckboxMenuItem [] printerCheckboxMenuItem = new CheckboxMenuItem[printServices.length];
            
            for (int i=0; i<printerCheckboxMenuItem.length; i++) {
                printerCheckboxMenuItem[i] = new CheckboxMenuItem(printServices[i].getName());
            }
            
            // Add the printer checkboxes with listener
            for (int i=0; i<printerCheckboxMenuItem.length; i++) {
                final int ii = i;
                ItemListener iListener = new ItemListener() {
                      public void itemStateChanged(ItemEvent event) {
                          // Unselect everything
                          for (int j=0; j<printerCheckboxMenuItem.length; j++) {
                              printerCheckboxMenuItem[j].setState(false);
                          }
                          
                          // Select self
                          printerCheckboxMenuItem[ii].setState(true);
                          
                          // Set the current printer name
                          currentPrinter = printServices[ii].getName();
                          
                          System.out.println("Printer changed to "+currentPrinter);
                      }
                };
                
                printerCheckboxMenuItem[i].addItemListener(iListener);
                printerMenu.add(printerCheckboxMenuItem[i]);
            }
            
            // Add color
            colorMenu.add(checkboxMenuItemBlackAndWhite);
            colorMenu.add(checkboxMenuItemColor);
            
            // Select first printer
            printerCheckboxMenuItem[0].setState(true);
            currentPrinter = printServices[0].getName();
            
            // Add orientations
            orientationMenu.add(checkboxMenuItemLandscape);
            orientationMenu.add(checkboxMenuItemPortrait);
            
            // Add fits
            fitMenu.add(checkboxMenuItemAspectRatio);
            fitMenu.add(checkboxMenuItemStretch);
            
            // Add languages
            languageMenu.add(checkboxMenuItemEnglish);
            languageMenu.add(checkboxMenuItemJapanese);
            
            // Set icons
            //checkboxMenuItemBlackAndWhite.setIcon(blackandwhite);
            
            //Add components to pop-up menu
            popup.add(colorMenu);
            popup.add(titleItem);
            popup.add(dateItem);
            popup.add(orientationMenu);
            popup.add(fitMenu);
            popup.add(printerMenu);
            popup.addSeparator();
            popup.add(languageMenu);
            popup.add(helpItem);
            popup.add(aboutItem);
            popup.addSeparator();
            popup.add(exitItem);
            
            trayIcon.setPopupMenu(popup);
            
            // Set default settings
            checkboxMenuItemBlackAndWhite.setState(true); // Color -> black and white
            checkboxMenuItemLandscape.setState(true);     // Orientation -> landscape
            checkboxMenuItemAspectRatio.setState(true);   // Fit -> aspect ratio
            checkboxMenuItemEnglish.setState(true);       // Language -> English
            printerCheckboxMenuItem[0].setState(true);    // Printer -> First printer
            currentPrinter = printServices[0].getName();
            
            // Try and add the tray system
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.out.println("TrayIcon could not be added.");
                return;
            }
        }
        
        System.out.println("Successfully created TrayIcon");
    }
    
    // Take a screenshot and print
    private void print() {
        System.out.println("Printing with "+currentPrinter);
        
        // Take screenshot
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        // Remove taskbar
        Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        int taskBarHeight = screenSize.height - winSize.height;

        screenSize.height -= taskBarHeight;
        
        // Take screenshot
        Rectangle screenRect = new Rectangle(screenSize);
        BufferedImage imageColor = robot.createScreenCapture(screenRect);
        
        
        // Try and find a horizontal line, that is not white, all of the same color in the first 20% of the image height.
        // That would mean that's the application's menubar
        int y=0;
        
        int heightToAnalyze = (int)(0.2f*(float)imageColor.getHeight());
        int white = Color.white.getRGB();
        
        for(int i=heightToAnalyze-1; i>=0; i--) {
        
            int currentColor = imageColor.getRGB(0, i);
            boolean found = true;
            
            if(currentColor != white) {
                for(int j=1; j<imageColor.getWidth() && found; j++) {
                    int color = imageColor.getRGB(j, i);
                    
                    if(color != currentColor) {
                        found = false;
                    }
                }
            }
            
            // We found it!
            if(found) {
                y = i;
                break;
            }
        }
        
        System.out.println(heightToAnalyze);
        System.out.println(y);
        
        // Get subimage
        imageColor = imageColor.getSubimage(0, y, imageColor.getWidth(), imageColor.getHeight()-y);
        
        // If option of black and white is selected, make the image grayscale, just in case
        BufferedImage imageGray = new BufferedImage(imageColor.getWidth(), imageColor.getHeight(), BufferedImage.TYPE_BYTE_GRAY);  
        Graphics g = imageGray.getGraphics();  
        g.drawImage(imageColor, 0, 0, null);  
        g.dispose();
        
        BufferedImage image = imageGray;
        
        // Also, change the opacicity of the printer //TODO
        //attrib.add(Chromaticity.MONOCHROME);
        
        // Create printer and start thread
        System.out.println(printersMap.get(currentPrinter));
        Printer printer = new Printer(image, printersMap.get(currentPrinter));
        printer.run();
    }
    
    // Cehck key pressed
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_PRINTSCREEN && e.getRawCode() == 44) {// e.getRawCode() == 44 Solves the problem of mistaking with asterisk
            // Only print once. We prevent from people holding the print button too long
            // and printing multiple times by accident
            if(!printHasBeenPressed) {
                print();
            }
            
            printHasBeenPressed = true;
        }
        
        // If close
        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (Exception ex) {
                System.out.println("Could not unhook keyboard");
                ex.printStackTrace();
            }
            
            System.out.println("Exit");
            System.exit(0);
        }
    }
    
    // Check key released
    public void nativeKeyReleased(NativeKeyEvent e) {
        if (e.getKeyCode() == NativeKeyEvent.VC_PRINTSCREEN ) {
            printHasBeenPressed = false; // We can print again
        }
    }
    
    // Check typed key - UNUSED
    public void nativeKeyTyped(NativeKeyEvent e) {
    }
    
    public static void main(String [] args) {
        new EasyKeyToPrint();
    }
}
