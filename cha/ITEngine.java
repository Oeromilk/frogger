package cha;

import cha.action.*;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.Date;
import java.util.Arrays;
import java.lang.reflect.Field;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.JColorChooser;

import static java.awt.Color.*;
import static java.awt.event.KeyEvent.*;
import static javax.swing.Action.*;

/**
 * The ImageTester Engine
 */
public class ITEngine
extends CHApplet
{
    String filename;
    String folder;
    Container welcome;
    Container pathWarning;
    CHImage image;
    CHImage error;
    ITEngine applet;

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
        appmenu .setText("ImageTester");

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

        JMenuItem of = new JMenuItem();
        of .setAction(open);
        filemenu .add(of);

        // color menu
        JMenu bgmenu = new JMenu();
        bgmenu .setText("Background");
        menuBar.add(bgmenu);

        for(String name : new String[] { "black", "blue", "cyan",
            "darkGray", "gray", "green", "lightGray", "magenta",
            "orange", "pink", "red", "white", "yellow"
        }) {
            Color c = getColor(name);
            ChangeColor cc = new ChangeColor(c);
            add(cc);
            cc .setText(name);
            cc .setVerbose(false);

            JMenuItem bc = new JMenuItem();
            bc.setBackground(c);
            bc.setForeground(getContrast(c));
            bc .setAction(cc);
            bgmenu .add(bc);
        }

        PickColor pc = new PickColor();
        add(pc);
        pc.setText("Other...");

        JMenuItem pci = new JMenuItem();
        pci.setBackground(black);
        pci.setForeground(white);
        pci.setAction(pc);
        bgmenu .add(pci);

        int shortcut = (int) appName.charAt(0);
        //         appmenu.setMnemonic(VK_I);
        //         filemenu .setMnemonic(VK_F);
        //         reset .putValue(MNEMONIC_KEY, VK_R);
        //         quit .putValue(MNEMONIC_KEY, VK_Q);
        //         open .putValue(MNEMONIC_KEY, VK_O);

        String messages[ ] = {
                "Copy a .gif, .png or .jpg file into this folder.",
                "Choose \"Open...\" from the \"File\" menu,",
                "then choose the image.",
                "Check the Terminal Window for error messages,",
                "and to see how to use the image in your applet.",
                "",
                "If you wish,",
                "choose a color from the \"Background\" menu to see",
                "how the image looks against different backgrounds.",
            };

        welcome = getContainer(messages);

    } // end of init

    private void center(Component c) {
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

    public class ChangeColor
    extends CHAction
    {
        Color color;
        public ChangeColor(Color c) {
            color = c;
        }

        public void step() {
            setBackground(color);
        }
    }

    public class PickColor
    extends CHAction
    {
        public void step() {
            Color bg = getBackground();
            Color newColor = JColorChooser.showDialog(
                    applet, "Choose Background Color", bg);

            if(newColor == null)
                return;

            if(newColor.equals(bg))
                return;

            int r = newColor.getRed();
            int g = newColor.getGreen();
            int b = newColor.getBlue();
            String name = "r" + r + "g" + g + "b" + b;

            String[] cm = new String[] {
                    "// the background color",
                    "// replace \"" + name + "\" with a name",
                    "// that's easier to remember",
                    "Color " + name + ";",
                    name + " = new Color("
                    + r + ", " + g + ", " + b + ");"
                };
            printMessage(cm, 8);
            setBackground(newColor);
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
        //         String slash = "/";
        //         String slash = java.io.File.separator;
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
        clear();

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
        image = new CHImage();
        add(image);
        image .setFile(".." + slash + filename);
        //         image .setFile(folder + filename);
        int w = image .getWidth();
        int h = image .getHeight();
        int x = (getWidth() - w) / 2;
        int y = (getHeight() - h) / 2;
        image .setLocation(x, y);

        // is the image valid?
        if(!image.isValid()) {
            return;
        }

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

            error = new CHImage();
            applet.add(error, 0);
            center(error);
            return;
        }

        // we have a good image, so explain how to use it
        String vName = variableName(safe);
        String[] ok = new String[] {
                "// Add this code to your applet",
                "CHImage " + vName + ";",
                vName + " = new CHImage();",
                "add(" + vName + ");",
                vName + ".setFile(\"" + safe + "\");",
                vName + ".setBounds(" + x + ", " + y
                + ", " + w + ", " + h + ");",
            };
        printMessage(ok, 8);

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
        //     private static Container getContainer(String messages[ ]) {
        CHContainer c = new MessageContainer();
        //         Container c = new Container();
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

    private static Color getContrast(Color c) {
        if(c.equals(gray))
            return black;

        int rgb = c.getRGB();
        int bgr = rgb ^ 0x0FFFFFF;
        Color d = new Color(bgr);
        return d;
    }

    private static Color getColor(String colorName) {
        try {
            // Find the field and value of colorName
            Field field = Class.forName("java.awt.Color").getField(colorName);
            return (Color)field.get(null);
        } catch (Exception e) {
            return null;
        }
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
