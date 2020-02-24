package cha;

import java.awt.*;

/**
 * Write a description of class Oval here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CHArc
extends CHComponent
{
    double start, sweep;
    
    public CHArc()
    {
        super();
        start = 0;
        sweep = 300;
        setBounds(350, 50, 100, 70);
    }
    
    public void setArc(double start, double sweep) {
        this.start = start;
        this.sweep = sweep;
    }
    
    public void paintComponent(Graphics g) {
        Dimension size = getSize();
        int w = size.width;
        int h = size.height;
        int iStart = (int) Math.round(start);
        int iSweep = (int) Math.round(sweep);
        
        g.setColor(getBackground());
        g.fillArc(0, 0, w-1, h-1, iStart, iSweep);
        
        g.setColor(getForeground());
        g.drawArc(0, 0, w-1, h-1, iStart, iSweep);
        
        super.paintComponent(g);
    }
}
