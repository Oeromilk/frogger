package cha;

import cha.action.CHAction;
import java.awt.Component;
import java.awt.Container;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.util.Date;

import static java.awt.Color.*;

/**
 * Write a description of class CHApplet here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CHApplet
extends JApplet
implements MouseListener, MouseMotionListener
{
    protected Container contents;
    protected String title;
    protected boolean debugging;

    public CHApplet() {
        super();
        contents = getContentPane();
        contents .setLayout(null);
        contents .setBackground(white);

        // for some bizarre reason, 
        // the applet won't listen to keyboard shortcut events 
        // unless you add at least one button
        contents.add(new JButton());
        title = "";
        debugging = false;

        // Include these 2 lines in your init()
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    /**
     * pass Background changes to the Content Pane
     * @see JApplet
     */
    public void setBackground(Color c) {
        if(contents != null)
            contents .setBackground(c);
    }

    /**
     * get Background color from the Content Pane
     * @see JApplet
     */
    public Color getBackground() {
        if(contents != null)
            return contents .getBackground();

        return super .getBackground();
    }

    /**
     * pass Foreground changes to the Content Pane
     * @see JApplet
     */
    public void setForeground(Color c) {
        if(contents != null)
            contents .setForeground(c);
    }

    /**
     * get Foreground color from the Content Pane
     * @see JApplet
     */
    public Color getForeground() {
        if(contents != null)
            return contents .getForeground();

        return super .getForeground();
    }

    public void add(CHComponent c, Object l, int p) {
        super.add(c, l, p);
        if(c instanceof CHComponent) {
            CHComponent ch = (CHComponent) c;
            ch.start();
        }
    }

    public CHComponent add(CHComponent c, int p) {
        add(c, null, p);
        return c;
    }

    public CHComponent add(CHComponent c) {
        return add(c, -1);
    }

    public void add(CHAction c) {
        c.setTop(this);
    }

    public void addTitle(String title) {
        this.title = " - " + title;
    }

    public void debug(double width, double height) {
        debugging = true;
        run(width, height); 
    }

    public void run(double width, double height) {
        run(0, 0, width, height);
    }

    public void run(double x, double y, double width, double height) {
        int ix = (int) Math.round(x);
        int iy = (int) Math.round(y);
        int iWidth = (int) Math.round(width);
        int iHeight = (int) Math.round(height);
        CHApplet applet = this;

        String windowTitle = applet.getClass().getName() + title;
        if(debugging)
            System.out.println(windowTitle + " created " + new Date());
        JFrame frame = new JFrame(windowTitle);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(ix, iy);
        frame.setSize(iWidth, iHeight + 20);
        applet.setSize(iWidth, iHeight);
        frame.add(applet);
        applet.init();       // simulate browser call(1)
        applet.start();      // simulate browser call(2)
        frame.setVisible(true);
    }

    public void setDebugging(boolean newValue) {
        debugging = newValue;
    }

    public boolean debugging() {
        return debugging;
    }

    /****************************************************************************
     * Mouse Functions
     */
    public void mousePressed(MouseEvent e) {}

    // Dragging the mouse while holding down the mouse button.
    public void mouseDragged(MouseEvent e) {}

    // Handles the event of a user releasing the mouse button.
    public void mouseReleased(MouseEvent e) {}
    
    // This method required by MouseListener.
    public void mouseMoved(MouseEvent e) {}

    // These methods are required by MouseMotionListener.
    public void mouseClicked(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}
}
