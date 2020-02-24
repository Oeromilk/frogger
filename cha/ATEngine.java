package cha;

import cha.action.*;

import java.awt. Toolkit;
import java.awt. Container;
import java.awt. Frame;
import java.awt. FileDialog;
import java.awt. Color;
import java.awt. Dimension;
import java.awt.event. KeyEvent;
import java.net.URL;
import java.util.Date;
import java.util.Arrays;
import java.lang.reflect.Field;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import static java.awt.Color.*;
import static java.awt.event.KeyEvent.*;
import static javax.swing.Action.*;

/**
 * The AudioTester Engine
 */
public class ATEngine
extends CHApplet
{
    String filename;
    String folder;
    CHContainer welcome;
    CHContainer pathWarning;
    CHClip clip;
    ATEngine applet;
    
    public void init() {
        applet = this;
        int mask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
        
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu appmenu = new JMenu();
        menuBar .add(appmenu);
        String longname = getClass().getName();
        String appName = longname.split("\\$")[0];
//         appmenu .setText(appName);
        appmenu .setText("ClipTester");
        
        Reset reset = new Reset();
        add(reset);
        reset.setText("Reset");
        KeyStroke resetKey = KeyStroke.getKeyStroke(KeyEvent.VK_R, mask);
        reset .putValue(ACCELERATOR_KEY, resetKey);
    
        JMenuItem ar = new JMenuItem();
        ar.setAction(reset);
        appmenu .add(ar);
        
        Quit quit = new Quit();
        add(quit);
        quit.setText("Quit");
        KeyStroke quitKey = KeyStroke.getKeyStroke(KeyEvent.VK_Q, mask);
        quit .putValue(ACCELERATOR_KEY, quitKey);
    
        JMenuItem aq = new JMenuItem();
        aq.setAction(quit);
        appmenu .add(aq);
        
        JMenu filemenu = new JMenu();
        filemenu .setText("File");
        menuBar.add(filemenu);
        
        Open open = new Open();
        add(open);
        open .setText("Open...");
        KeyStroke openKey = KeyStroke.getKeyStroke(KeyEvent.VK_O, mask);
        open .putValue(ACCELERATOR_KEY, openKey);
        open.setTop(this);
    
        JMenuItem of = new JMenuItem();
        of .setAction(open);
        filemenu .add(of);
        
        int shortcut = (int) appName.charAt(0);
    
        String messages[ ] = {
            "Copy a .wav, .au or .midi file into this folder.",
            "Choose \"Open...\" from the \"File\" menu,",
            "then choose the sound file.",
            "Listen for a sound through headphones.",
            "If the sound plays, check the Terminal Window",
            "to see how to use the image in your applet.",
        };
        
        welcome = getContainer(messages);
        
    } // end of init
    
    private void center(CHComponent c) {
        int w = c.getWidth();
        int h = c.getHeight();
        int x = (applet.getWidth() - w) / 2;
        int y = (applet.getHeight() - h) / 2;
        c.setBounds(x, y, w, h);
    }
    
    public void start() {
        Container contents = getContentPane();
        contents .setBackground(gray);
        center(welcome);
        add(welcome);
    }
    
    public void clear() {
        contents.removeAll();
        repaint();
    }
    
    public class Reset
    extends CHAction
    {
        public Reset() {
            super();
            setVerbose(false);
        }
        public void step() {
            clear();
            start();
        }
    }
    
    public class Quit
    extends CHAction
    {
        public Quit() {
            super();
            setVerbose(false);
        }
        public void step() {
            System.exit(0);
        }
    }
    
    public class Open
    extends CHAction
    {
        public Open() {
            super();
            setVerbose(false);
        }
        public void step() {
            loadFile();
        }
    }
    
    private void loadFile() {
        String userDir = System.getProperty("user.dir");
        String slash = System.getProperty("file.separator");
        
        Frame frame = new Frame(); 
        FileDialog fd = new FileDialog(frame);
        fd.setDirectory(userDir);
        fd.setVisible(true); 
        filename = fd.getFile();
        folder = fd.getDirectory();
        
        // if the user cancelled, return
        if(filename == null) {
            return;
        }
        
        // check that the image is in the correct folder
        String thisFolder = userDir + slash;
        if(!folder.equals(thisFolder)) {
            Toolkit.getDefaultToolkit().beep();
            String[] pw = new String[] {
                "Your image is in the wrong folder!",
                "Copy your image from: ",
                "    " + folder,
                "to: ", 
                "    " + thisFolder,
                "and try again.",
            };
            printMessage(pw);
            return;
        }
        
        // go ahead and try to load the image
        clip = new CHClip();
        add(clip);
        clip .setFile(".." + slash + filename);
        clip . play();
        
        // check that the filename is web safe
        String safe = safeName(filename);
        if(!filename.equals(safe)) {
            Toolkit.getDefaultToolkit().beep();
            String[] nw = new String[] {
                "The file name is badly formed.",
                "Rename: " + filename,
                "    to: " + safe,
                "and try again.",
            };
            printMessage(nw);
            return;
        }
        
        // we have a good image, so explain how to use it
        String vName = variableName(safe);
        String[] ok1 = new String[] {
            "// If you can hear the sound, add this code",
            "// at the beginning of your applet, after the line",
            "// \"extends CHApplet {\"",
            "CHClip " + vName + ";",
        };
        printMessage(ok1, 4);
        
        String[] ok3 = new String[] {
            "// then add this code to the CHAction",
            "// after step() {",
            vName + ".play();",
        };
        printMessage(ok3, 12);
        
        String[] ok2 = new String[] {
            "// then add this code after init() { ",
            vName + " = new CHClip();",
            "add(" + vName + ");",
            vName + ".setFile(\"" + safe + "\");",
        };
        printMessage(ok2, 8);
    }
    
    private String safeName(String filename) {
        String safe = filename.toLowerCase()
            .replaceAll("[_ ]", "-")
            .replaceAll("[^-a-z0-9.]", "");
        return safe;
    }
    
    private String variableName(String safeName) {
        String[] parts = safeName.split("[.]");
        if(parts. length == 0)
            return "";
            
        String base = parts[0];
        for(int i = 1; i < parts. length - 1; i++) {
            String w = parts[i];
            String c = w.substring(0, 1).toUpperCase();
            String d = w.substring(1, w.length());
            base += c + d;
        }
        
        String[] words = base. split("-");
        if(words.length == 0)
            return "";
            
        String v = words[0];
        for(int i = 1; i < words. length; i++) {
            String w = words[i];
            String c = w.substring(0, 1).toUpperCase();
            String d = w.substring(1, w.length());
            v += c + d;
        }
        return v;
    }
    
    public static class MessageContainer 
    extends CHContainer {
        public void init() {}
    }
    
    /**
     * Create a container for multi-line messages.
     * @param messages an array of Strings that make up the message.
     * Each String in messages will be laid out on a separate line.
     * @return a CHContainer with the height and width set to hold 
     * all the lines in the message.
     */
    private static CHContainer getContainer(String messages[ ]) {
        CHContainer c = new MessageContainer();
        c.setBackground(yellow);
//         CHRectangle origin = new CHRectangle();
//         origin.setBounds(0, 0, 1, 1);
//         origin.setForeground(new Color(0, 0, 0, 0));
//         c.add(origin);
        
        int margin = 5;
        int ht = 0;
        for(int i = 0; i < messages.length; i++) {
            CHLabel line = new CHLabel();
            line.setText(messages[i]);
            Dimension size = line.getPreferredSize();
            line.setBounds(margin, ht, size.width + margin, 
                size.height + margin);
            ht += size.height;
            c.add(line);
        }
        
        c.initBounds();
        return c;
    }    
    
    private static void printMessage(String[] m) {
        printMessage(m, 0);
    }
    
    private static void printMessage(String[] m, int size) {
        char[] spaces = new char[size];
        Arrays.fill(spaces, ' ');
        String indent = new String(spaces);
        
        System.out.println();
        for(String s : m) {
            System.out.println(indent + s);
        }
    }

}
