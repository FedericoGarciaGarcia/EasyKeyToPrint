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
import java.io.*;
import java.lang.StringBuilder;
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
    private HashMap<String, Integer> printerNamesMap;
    private HashMap<String, Integer> languageNamesMap;
    
    // Robot
    private Robot robot;
    
    // Config
    class Config {
        public int color;
        public int orientation;
        public int fit;
		public int taskbar;
        public String printer;
        public String language;
    }
    private Config config;
	
	// Trayicon
	private TrayIcon trayIcon;
	
	// Menus
	private Menu colorMenu;
	private 	CheckboxMenuItem checkboxMenuItemBlackAndWhite;
	private 	CheckboxMenuItem checkboxMenuItemColor;
	private 	CheckboxMenuItem [] colorMenuCheckboxMenuItems;
	private MenuItem titleItem;
	private MenuItem dateItem;
	private Menu orientationMenu;
	private 	CheckboxMenuItem checkboxMenuItemLandscape;
	private 	CheckboxMenuItem checkboxMenuItemPortrait;
	private 	CheckboxMenuItem [] orientationMenuCheckboxMenuItems;
	private Menu fitMenu;
	private 	CheckboxMenuItem checkboxMenuItemAspectRatio;
	private 	CheckboxMenuItem checkboxMenuItemStretch;
	private 	CheckboxMenuItem [] fitMenuCheckboxMenuItems;
	private Menu taskbarMenu;
	private 	CheckboxMenuItem checkboxMenuItemShowTaskbar;
	private 	CheckboxMenuItem checkboxMenuItemHideTaskbar;
	private 	CheckboxMenuItem [] taskbarMenuCheckboxMenuItems;
	private Menu paperSizeMenu;
	private 	CheckboxMenuItem checkboxMenuItemA3;
	private 	CheckboxMenuItem checkboxMenuItemA4;
	private 	CheckboxMenuItem checkboxMenuItemB5;
	private 	CheckboxMenuItem [] paperSizeMenuCheckboxMenuItems;
	private Menu printerMenu;
	private     CheckboxMenuItem [] printerMenuCheckboxMenuItems;
	private Menu languageMenu;
	private 	CheckboxMenuItem checkboxMenuItemEnglish;
	private 	CheckboxMenuItem checkboxMenuItemJapanese;
	private     CheckboxMenuItem [] languageMenuCheckboxMenuItems;
	private MenuItem helpItem;
	private MenuItem aboutItem;
	
	private MenuItem exitItem;
    
    // Start everything
    public EasyKeyToPrint() {
        /*
         * VARIABLES
         */
        printHasBeenPressed = false;
        config = new Config();
        printersMap      = new HashMap<String, PrintService>();
		printerNamesMap  = new HashMap<String, Integer>();
		languageNamesMap = new HashMap<String, Integer>();
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
		
        // Check the if the SystemTray is supported
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        // If it is, work
        else {
            // Create tray system
			createTray();
			
			// Set default config
			defaultConfig();
            
            // Load config.
            loadConfig();
			
			// Set initial state
			initialState();
            
            // Save it again, in case the file was missing some configs
            saveConfig();
        }
        
        System.out.println("Successfully created TrayIcon");
    }
    
    // Get available printers
    // Create dictionary of printers
    private void initPriters() {
        printServices = PrintServiceLookup.lookupPrintServices(null, null);
        System.out.println("Number of print services: " + printServices.length);

        for (int i=0; i<printServices.length; i++) {
            System.out.println("  "+printServices[i].getName());
            printersMap.put(printServices[i].getName(), printServices[i]);
			printerNamesMap.put(printServices[i].getName(), i);
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
	
	// Create Tray System and popup menu with actions
	private void createTray() {
		// Create popup menu
		final PopupMenu popup = new PopupMenu();
		
		// Null Image for now
		Image image = null;
		
		// Try to load the image
		try {
			image = ImageIO.read(getClass().getResource("/images/icon.png"));
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
		trayIcon = new TrayIcon(image, "EasyKeyToPrint");
		
		// Create a pop-up menu components
		colorMenu     = new Menu("Color");
			checkboxMenuItemBlackAndWhite = new CheckboxMenuItem("Black and white");
			checkboxMenuItemColor = new CheckboxMenuItem("Color");
			
			colorMenuCheckboxMenuItems = new CheckboxMenuItem[]{checkboxMenuItemBlackAndWhite, checkboxMenuItemColor};
			
			for(int i=0; i<colorMenuCheckboxMenuItems.length; i++) {
				final int ii = i;
				colorMenuCheckboxMenuItems[i].addItemListener(new ItemListener() {
					  public void itemStateChanged(ItemEvent event) {
						  config.color = ii;
						  unselectAll(colorMenuCheckboxMenuItems, colorMenuCheckboxMenuItems[ii]);
					  }
				});
			}
		titleItem = new MenuItem("Title");
		dateItem  = new MenuItem("Date");
		orientationMenu  = new Menu("Orientation");
			checkboxMenuItemLandscape  = new CheckboxMenuItem("Landscape");
			checkboxMenuItemPortrait   = new CheckboxMenuItem("Portrait");
			
			orientationMenuCheckboxMenuItems = new CheckboxMenuItem[]{checkboxMenuItemLandscape, checkboxMenuItemPortrait};
			
			for(int i=0; i<orientationMenuCheckboxMenuItems.length; i++) {
				final int ii = i;
				orientationMenuCheckboxMenuItems[i].addItemListener(new ItemListener() {
					  public void itemStateChanged(ItemEvent event) {
						  config.orientation = ii;
						  unselectAll(orientationMenuCheckboxMenuItems, orientationMenuCheckboxMenuItems[ii]);
					  }
				});
			}
		fitMenu  = new Menu("Fit");
			checkboxMenuItemAspectRatio = new CheckboxMenuItem("Maintain aspect ratio");
			checkboxMenuItemStretch     = new CheckboxMenuItem("Stretch");
			fitMenuCheckboxMenuItems = new CheckboxMenuItem[]{checkboxMenuItemAspectRatio, checkboxMenuItemStretch};
			
			for(int i=0; i<fitMenuCheckboxMenuItems.length; i++) {
				final int ii = i;
				fitMenuCheckboxMenuItems[i].addItemListener(new ItemListener() {
					  public void itemStateChanged(ItemEvent event) {
						  config.fit = ii;
						  unselectAll(fitMenuCheckboxMenuItems, fitMenuCheckboxMenuItems[ii]);
					  }
				});
			}
		taskbarMenu  = new Menu("Taskbar");
			checkboxMenuItemShowTaskbar = new CheckboxMenuItem("Show");
			checkboxMenuItemHideTaskbar = new CheckboxMenuItem("Hide");
			taskbarMenuCheckboxMenuItems = new CheckboxMenuItem[]{checkboxMenuItemShowTaskbar, checkboxMenuItemHideTaskbar};
			
			for(int i=0; i<taskbarMenuCheckboxMenuItems.length; i++) {
				final int ii = i;
				taskbarMenuCheckboxMenuItems[i].addItemListener(new ItemListener() {
					  public void itemStateChanged(ItemEvent event) {
						  config.taskbar = ii;
						  unselectAll(taskbarMenuCheckboxMenuItems, taskbarMenuCheckboxMenuItems[ii]);
					  }
				});
			}
		printerMenu   = new Menu("Printers");
		
		languageMenu  = new Menu("Language");
			checkboxMenuItemEnglish  = new CheckboxMenuItem("English");
			checkboxMenuItemJapanese = new CheckboxMenuItem("Japanese");
			
			languageMenuCheckboxMenuItems = new CheckboxMenuItem[]{checkboxMenuItemEnglish, checkboxMenuItemJapanese};
			
			checkboxMenuItemEnglish.addItemListener(new ItemListener() {
				  public void itemStateChanged(ItemEvent event) {
					  unselectAll(languageMenuCheckboxMenuItems, checkboxMenuItemEnglish);
				  }
			});
			checkboxMenuItemJapanese.addItemListener(new ItemListener() {
				  public void itemStateChanged(ItemEvent event) {
					  unselectAll(languageMenuCheckboxMenuItems, checkboxMenuItemJapanese);
				  }
			});
		helpItem  = new MenuItem("Help");
		aboutItem = new MenuItem("About");
		
		exitItem  = new MenuItem("Exit");
		exitItem.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent e) {
				  exit();
			  }
		});
		// Create each printer menu item and its action
		printerMenuCheckboxMenuItems = new CheckboxMenuItem[printServices.length];
		
		for (int i=0; i<printerMenuCheckboxMenuItems.length; i++) {
			printerMenuCheckboxMenuItems[i] = new CheckboxMenuItem(printServices[i].getName());
		}
		
		// Add the printer checkboxes with listener
		for (int i=0; i<printerMenuCheckboxMenuItems.length; i++) {
			final int ii = i;

			printerMenuCheckboxMenuItems[i].addItemListener(new ItemListener() {
				  public void itemStateChanged(ItemEvent event) {
					  config.printer = printerMenuCheckboxMenuItems[ii].getLabel();
					  unselectAll(printerMenuCheckboxMenuItems, printerMenuCheckboxMenuItems[ii]);
				  }
			});
			printerMenu.add(printerMenuCheckboxMenuItems[i]);
		}
		
		// Add color
		colorMenu.add(checkboxMenuItemBlackAndWhite);
		colorMenu.add(checkboxMenuItemColor);
		
		// Add orientations
		orientationMenu.add(checkboxMenuItemLandscape);
		orientationMenu.add(checkboxMenuItemPortrait);
		
		// Add fits
		fitMenu.add(checkboxMenuItemAspectRatio);
		fitMenu.add(checkboxMenuItemStretch);
		
		// Add taskbar
		taskbarMenu.add(checkboxMenuItemShowTaskbar);
		taskbarMenu.add(checkboxMenuItemHideTaskbar);
		
		// Add languages
		languageMenu.add(checkboxMenuItemEnglish);
		languageMenu.add(checkboxMenuItemJapanese);
		
		// Set icons
		//checkboxMenuItemBlackAndWhite.setIcon(blackandwhite);
		
		//Add components to pop-up menu
		popup.add(colorMenu);
		//popup.add(titleItem); TODO
		//popup.add(dateItem);  TODO
		popup.add(orientationMenu);
		popup.add(fitMenu);
		popup.add(taskbarMenu);
		popup.add(printerMenu);
		popup.addSeparator();
		popup.add(languageMenu);
		popup.add(helpItem);
		popup.add(aboutItem);
		popup.addSeparator();
		popup.add(exitItem);
		
		trayIcon.setPopupMenu(popup);
			
		// Try and add the tray system
		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			System.out.println("TrayIcon could not be added.");
			return;
		}
	}
	
	// Set the initial state of the UI, with checked boxes and all that
	private void initialState() {
		colorMenuCheckboxMenuItems      [config.color].setState(true);
		orientationMenuCheckboxMenuItems[config.orientation].setState(true);
		fitMenuCheckboxMenuItems        [config.fit].setState(true);
		taskbarMenuCheckboxMenuItems        [config.taskbar].setState(true);
		printerMenuCheckboxMenuItems    [printerNamesMap.get(config.printer)].setState(true);
		// TODO
		languageMenuCheckboxMenuItems   [0].setState(true);
	}
	
	private void defaultConfig() {
        config.color       = 0;
        config.orientation = 0;
        config.fit         = 0;
		config.printer  = printerMenuCheckboxMenuItems[0].getLabel();
		config.language = languageMenuCheckboxMenuItems[0].getLabel();
	}
    
    // Try and load configuration
    private void loadConfig() {
		// Save config file
		try {
			// Read line by line
			BufferedReader reader = new BufferedReader(new FileReader("config.ini"));
			ArrayList<String> strings = new ArrayList<String>();
			String line = null;
			
			while ((line = reader.readLine()) != null) {
				// Do not add empty lines
				if(line.length() > 0)
				strings.add(line);
			}
			reader.close();
			
			// Set config
			for(int i=0; i<strings.size(); i++) {
				if(strings.get(i).equals("[Color]"))
					config.color = Integer.parseInt(strings.get(i+1));
				
				if(strings.get(i).equals("[Orientation]"))
					config.orientation = Integer.parseInt(strings.get(i+1));
				
				if(strings.get(i).equals("[Fit]"))
					config.fit = Integer.parseInt(strings.get(i+1));
				
				if(strings.get(i).equals("[Taskbar]"))
					config.taskbar = Integer.parseInt(strings.get(i+1));
				
				if(strings.get(i).equals("[Printer]"))
					config.printer = strings.get(i+1);
			}
		}
		catch(IOException e) {
			System.out.println("Could not save config.ini");
			e.printStackTrace();
		}
    }
    
    // Save configuration
    private void saveConfig() {
		System.out.println("Settings: ");
		
		// Save config file
		try (PrintWriter out = new PrintWriter("config.ini")) {
			out.println("[Color]");
			out.println(config.color);
			
			out.println("[Orientation]");
			out.println(config.orientation);
			
			out.println("[Fit]");
			out.println(config.fit);
			
			out.println("[Taskbar]");
			out.println(config.taskbar);
			
			out.println("[Printer]");
			out.println(config.printer);
			
			out.println("[Language]");
			out.println(config.language);
			
			out.close();
			
			System.out.println("  color: "      +config.color);
			System.out.println("  orientation: "+config.orientation);
			System.out.println("  fit: "        +config.fit);
			System.out.println("  taskbar: "    +config.taskbar);
			System.out.println("  printer: "    +config.printer);
			System.out.println("  language: "   +config.language);
			System.out.println("  config.ini saved");
		}
		catch(IOException e) {
			System.out.println("Could not save config.ini");
			e.printStackTrace();
		}
    }
    
    // Unselect everything
    private void unselectAll(CheckboxMenuItem [] array, CheckboxMenuItem self) {
        for (int j=0; j<array.length; j++) {
            array[j].setState(false);
        }
        
        // Select this one
        self.setState(true);
        
        // Save configuration
        saveConfig();
    }
    
    // Take a screenshot and print
    private void print() {
        // Take screenshot
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        // Remove taskbar if requested
		if(config.taskbar == 1) {
			Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
			int taskBarHeight = screenSize.height - winSize.height;

			screenSize.height -= taskBarHeight;
        }
		
        // Take screenshot
        Rectangle screenRect = new Rectangle(screenSize);
        BufferedImage imageColor = robot.createScreenCapture(screenRect);
        
        // Try and find a horizontal line, that is not white, all of the same color in the first 20% of the image height.
        // That would mean that's the application's menubar
        /*int y=0;
        
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
		*/
        
		BufferedImage image;
		
        // If option of black and white is selected, make the image grayscale, just in case
		if(config.color == 0) {
			BufferedImage imageGray = new BufferedImage(imageColor.getWidth(), imageColor.getHeight(), BufferedImage.TYPE_BYTE_GRAY);  
			Graphics g = imageGray.getGraphics();  
			g.drawImage(imageColor, 0, 0, null);  
			g.dispose();
			image = imageGray;
        }
		else {
			image = imageColor;
		}
        // Also, change the opacicity of the printer //TODO
        //attrib.add(Chromaticity.MONOCHROME);
        
        // Create printer and start thread
		trayIcon.displayMessage("EasyKeyToPrint", "Printing document...", TrayIcon.MessageType.INFO);
        Printer printer = new Printer(image, printersMap.get(config.printer), config.fit, config.orientation);
        printer.run();
    }
	
	private void exit() {
		try {
			GlobalScreen.unregisterNativeHook();
		} catch (Exception ex) {
			System.out.println("Could not unhook keyboard");
			ex.printStackTrace();
		}
		
		System.out.println("Exit");
		System.exit(0);
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
