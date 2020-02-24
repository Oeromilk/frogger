package cha;

import java.awt.*;

/**
 * Write a description of class Oval here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CHRectangle
extends CHComponent
{    
    public CHRectangle() {
        super();
        setBounds(50, 50, 100, 70);
    }
    
    public void paintComponent(Graphics g) {
//         Graphics2D g2d = (Graphics2D) g.create();
        Dimension size = getSize();
        int w = size.width - 1;
        int h = size.height - 1;
        
        g.setColor(getBackground());
        g.fillRect(0, 0, w, h);
        
        g.setColor(getForeground());
//         g2d.setStroke(stroke);
        g.drawRect(0, 0, w, h);
        
        super.paintComponent(g);
    }
}
