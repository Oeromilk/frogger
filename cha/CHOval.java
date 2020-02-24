package cha;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Write a description of class Oval here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CHOval
extends CHComponent
{
    public CHOval() {
        super();
        setBounds(200, 50, 100, 70);
    }
    
    public void paintComponent(Graphics g) {
        Dimension size = getSize();
        int x = 0;
        int y = 0;
        int w = size.width - 1;
        int h = size.height - 1;
        
        g.setColor(getBackground());
        g.fillOval(x, y, w, h);
        
        g.setColor(getForeground());
        g.drawOval(x, y, w, h);
        
        super.paintComponent(g);
    }
}
