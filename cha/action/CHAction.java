package cha.action;

import cha.CHApplet;
import java.awt. Component;
import java.awt. Container;
import java.awt. Toolkit;
import java.awt.event.ActionEvent;
import javax.swing. AbstractAction;
import javax.swing. JComponent;
import javax.swing.InputMap;
import javax.swing.ActionMap;
import javax.swing.KeyStroke;
// import static javax.swing.JComponent.WHEN_FOCUSED;
// import static javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW;
import static javax.swing.JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT;

public class CHAction
extends AbstractAction
{
    protected int limit;
    protected int delay;
    protected int counter;
    protected boolean running;
    protected Component top;
    protected ActionEvent event;
    protected boolean verbose;
    private CHAction action;

    public CHAction() {
        super();
        limit = 1;
        delay = 20;
        running = false;
        verbose = false;
        setEnabled(true);
        action = this;
    }

    public void setLimit(int lim) {
        limit = lim;
    }

    public void setDelay(int d) {
        delay = d;
    }

    public void setTop(Component t) {
        top = t;
    }

    public void setText(String s) {
        putValue(NAME, s);
    }

    public String getText() {
        return (String) getValue(NAME);
    }

    public void setVerbose(boolean v) {
        verbose = v;
    }

    /**
     * default CH button action
     */
    public void actionPerformed(ActionEvent ev) {
        // print diagnostics to terminal
        event = ev;
        Object sourceObject = ev.getSource();
        JComponent source;
        try {
            source = (JComponent) sourceObject;
            if(verbose) {
                String target = source .getClass() .getName();
                String command = ev .getActionCommand();
                System.out.println("Target: " + target
                    + ",  Command: " + command);
            }
        } catch (Exception ec) {
            System.err.println(ec);
            return;
        }

        Thread actor = new Actor();
        actor.start();
    }

    private void setTop()
    {
        if(top != null)
            return;

        Object sourceObject = event.getSource();
        try {
            JComponent source = (JComponent) sourceObject;
            top = source.getTopLevelAncestor();
        } catch (Exception ec) {
            System.err.println(ec);
        }
        if(top != null)
            return;

        JComponent source = (JComponent) sourceObject;
        Container parent = source.getParent();
        while(parent != null) {
            top = (Component) parent;
            parent = top.getParent();
        }
    }

    public class Actor
    extends Thread {
        public void run() {
            action.run();
        }
    }

    public void run() {
        //             System.out.println("Actor running.");
        running = true;
        setTop();
        if (top == null) {
            System.out.println("Cannot find the top level window/applet.");
            return;
        }

        before();
        for(counter = 0; counter < limit; counter++) {
            step();
            sleep(delay);
            if (!running
            || !top.isVisible()
            ) {
                break;
            }
        }
        after();
        if(top != null) {
            top.repaint();
        }
    }

    // before iteration
    public void before() { }

    // define each step
    public void step() { }

    // after iteration
    public void after() { }

    public void start() {
        Thread actor = new Actor();
        actor.start();
    }

    public void stop() {
        running = false;
    }

    public void sleep(int delay) {
        try { Thread.sleep(delay); }
        catch (Exception e) { }
    }

    public void sleep() {
        try { Thread.sleep(delay); }
        catch (Exception e) { }
    }

    public void beep() {
        Toolkit.getDefaultToolkit().beep();
    }

    /**
     * Map a key to this action.
     * @param pane the active pane
     * (if the window is divided into panes)
     * @param keyDesc a string with the key names, e.g. 
     * "A".."Z", "0".."9", "UP".."LEFT", "SPACE", "PERIOD", "F1".."F12", 
     * The string may also include prefixes "shift", "control", "ctrl", "meta", "alt",
     *   and "pressed", "released", or "typed"
     * @see https://docs.oracle.com/javase/7/docs/api/javax/swing/KeyStroke.html#getKeyStroke
     * @see https://docs.oracle.com/javase/7/docs/api/java/awt/event/KeyEvent.html
     */
    public void mapKey(JComponent pane, String keyDesc) {
        InputMap imap = pane.getInputMap(
                //             WHEN_FOCUSED
                //             WHEN_IN_FOCUSED_WINDOW
                WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
            );
        ActionMap amap = pane.getActionMap();

        KeyStroke stroke = KeyStroke.getKeyStroke(keyDesc);
        String key = this.toString();
        imap.put(stroke, key);
        amap.put(key, this);
    }

    public void mapKey(CHApplet app, String keyDesc) {
        mapKey(app.getRootPane(), keyDesc);
    }
    
    public void mapKey(JComponent pane, int keyCode, int modifiers) {
        InputMap imap = pane.getInputMap(
                //             WHEN_FOCUSED
                //             WHEN_IN_FOCUSED_WINDOW
                WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
            );
        ActionMap amap = pane.getActionMap();

        KeyStroke stroke = KeyStroke.getKeyStroke(keyCode, modifiers);
        String key = this.toString();
        imap.put(stroke, key);
        amap.put(key, this);
    }
    
    public void mapKey(JComponent pane, int keyCode) {
        mapKey(pane, keyCode, 0);
    }

    public void mapKey(CHApplet app, int keyCode, int modifiers) {
        mapKey(app.getRootPane(), keyCode, modifiers);
    }

    public void mapKey(CHApplet app, int keyCode) {
        mapKey(app.getRootPane(), keyCode, 0);
    }
}
