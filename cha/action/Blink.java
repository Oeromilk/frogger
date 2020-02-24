package cha.action;

import java.awt.*;
import javax.swing.*;

import static java.awt.Color.*;

/**
 * Write a description of class DefaultAction here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Blink
extends CHAction
{
    public Blink() {
        super();
        setText("??");
    }
    
    public void step() {
        System.out.println(this.getClass().getName() + " running.");
        if (top == null) {
            System.err.println("Cannot find the top level window/applet.");
            return;
        }
        
        // find the current color and its inverse
        Color bg = top.getBackground();
        int bits = bg.getRGB() ^ -1;
        Color invert = new Color(bits);
        
        // flash the screen
        top.setBackground(invert);
        top.repaint();
             
        beep();

        try { Thread.sleep(100); }
        catch (Exception f) { }
        top.setBackground(bg);
        top.repaint();
    }
}
