package cha;

import java.awt.*;
import java.awt.geom.*;

/**
 * Write a description of class Oval here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CHPolygon
extends CHComponent
{
    private Polygon poly;
    private boolean first;
    
    public CHPolygon() {
        super();
        poly = new Polygon();
        super.setBounds(0, 0, 350, 50);
        first = true;
    }
    
    public void addPoint(double x, double y) {
//         System.out.println("addPoint(" + x + ", " + y + ")"); 
        if(first) {
            super.setBounds(x, y, 1, 1);
            poly.addPoint(0, 0);
        } else {
            // enlarge the bounds to include the new point.
            Rectangle bounds = getBounds();
            Rectangle nB = getBounds();
            nB.add(x, y);
            nB.add(x+1, y+1);
            super.setBounds(nB.x, nB.y, nB.width, nB.height);
            
            // if needed, move the existing line to fit within the new bounds.
            int tx = bounds.x - nB.x;
            int ty = bounds.y - nB.y;
            poly.translate(tx, ty);
            
            // add the point relative to the current bounds
            int nx = (int) Math.round(x - nB.x);
            int ny = (int) Math.round(y - nB.y);
            poly.addPoint(nx, ny);
        }
        first = false;
    }
    
    public void setBounds(double x, double y, double w, double h) {
        Rectangle bounds = getBounds();
        double rx = (w - 0) / bounds.getWidth();
        double ry = (h - 0) / bounds.getHeight();
        for(int i = 0; i < poly.npoints; i++) {
            poly.xpoints[i] = (int) Math.round(poly.xpoints[i] * rx);
            poly.ypoints[i] = (int) Math.round(poly.ypoints[i] * ry);
        }
        super.setBounds(x, y, w, h);
    }
    
    public void paintComponent(Graphics g) {
        if(first) {
            g.setColor(getForeground());
            g.drawString("Empty CHPolygon", 200, 40);
            return;
        } else {
            g.setColor(getBackground());
            g.fillPolygon(poly);
            
            g.setColor(getForeground());
            g.drawPolygon(poly);
        }
        
        super.paintComponent(g);
    }
    
    public PathIterator getPath() {
        Rectangle bounds = getBounds();
        AffineTransform at = new AffineTransform();
        at.translate(bounds.x, bounds.y);
        return poly.getPathIterator(at);
    }
    
    public boolean contains(double x, double y) {
        Rectangle bounds = getBounds();
        double xc = x - bounds.x;
        double yc = y - bounds.y;
        
        return poly .contains(xc, yc);
    }
    
    public boolean contains(double x, double y, double w, double h) {
        Rectangle bounds = getBounds();
        x = x - bounds.x;
        y = y - bounds.y;
        
        return poly .contains(x, y, h, w);
    }
    
    public boolean contains(Rectangle r) {
        Rectangle bounds = getBounds();
        int x = r.x - bounds.x;
        int y = r.y - bounds.y;
        
        return poly .contains(x, y, r.width, r.height);
    }
}
