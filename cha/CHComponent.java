package cha;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Component;
import java.awt.geom.PathIterator;
import java.awt.geom.AffineTransform;
import java.net.URL;
import javax.swing.JComponent;

import static java.awt.geom.PathIterator.*;
import static java.awt.Color.*;
import static java.lang.Math.abs;
import static java.lang.Math.round;

/**
 * Write a description of class CHComponent here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CHComponent
extends JComponent
{
    protected Double x, y;
    protected double xv, yv;
    protected double xa, ya;

    public CHComponent() {
        super();
        xv = yv = 1;
        xa = ya = 0;

        setBackground(new Color(0, 0, 0, 0));
        setForeground(black);
    }

    public void setBounds(double x, double y, double w, double h) {
        int xi = (int) round(x);
        int yi = (int) round(y);
        int wi = (int) round(w);
        int hi = (int) round(h);
        super.setBounds(xi, yi, wi, hi);
    }

    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
        super.setLocation(
            (int) Math.round(x), 
            (int) Math.round(y));
    }

    public void setVelocity(double xv, double yv) {
        this.xv = xv;
        this.yv = yv;
    }

    public void setAcceleration(double xa, double ya) {
        this.xa = xa;
        this.ya = ya;
    }

    public void moveOneStep() {
        Point l = getLocation();
        if(x == null || y == null 
        || abs(x - l.x) > 1 || abs(y - l.y) > 1) {
            x = (double) l.x;
            y = (double) l.y;
        }
        xv += xa;
        yv += ya;
        x += xv;
        y += yv;
        setLocation(x, y);
    }

    public PathIterator getPath() {
        AffineTransform at = new AffineTransform();
        Rectangle bounds = getBounds();
        return bounds.getPathIterator(at);
    }

    public boolean contains(double x, double y) {
        Rectangle bounds = this.getBounds();
        return bounds.contains(x, y);
    }

    /**
     * This is an incomplete implementation of a containment 
     * test.
     * @param c another CHComponent.
     * @return true iff the rectangular boundary of c fits 
     * inside the rectangular boundary of this object.
     */
    public boolean contains(CHComponent c) {
        Rectangle bounds = this.getBounds();
        Rectangle cBounds = c.getBounds();
        return bounds.contains(cBounds);
        
        // this code would be necessary if all the CHComponents 
        // actually implemented Shapes
        
        //         PathIterator p = c.getPath();
        //         boolean result = true;
        //         
        //         while(!p.isDone()) {
        //             double[] coords = new double[6];
        //             int segmentType = p.currentSegment(coords);
        //             switch(segmentType) {
        //                 case SEG_CUBICTO:
        //                 int x2 = (int) Math.round(coords[4]);
        //                 int y2 = (int) Math.round(coords[5]);
        //                 if(!bounds.contains(x2, y2)) {
        //                     // return false;
        //                     result = false;
        //                 }
        //                     
        //                 case SEG_QUADTO:
        //                 int x1 = (int) Math.round(coords[2]);
        //                 int y1 = (int) Math.round(coords[3]);
        //                 if(!bounds.contains(x1, y1)) {
        //                     // return false;
        //                     result = false;
        //                 }
        //                     
        //                 case SEG_MOVETO:
        //                 case SEG_LINETO:
        //                 int x0 = (int) Math.round(coords[0]);
        //                 int y0 = (int) Math.round(coords[1]);
        //                 if(!bounds.contains(x0, y0)) {
        //                     // return false;
        //                     result = false;
        //                 }
        // 
        //                 case SEG_CLOSE:
        //             }
        //             p.next();
        //         }
        //         // return true;
        //         return result;
    }

    public void centerAbout(double xc, double yc) {
        Rectangle bounds = getBounds();
        int w = bounds.width;
        int h = bounds.height;
        double x = xc - w / 2.0;
        double y = yc - h / 2.0;

        setBounds(x, y, w, h);
    }

    public void start() {
        init();
    }

    public void init() {
    }

    public URL getURL(String path) {
        if(path == null) {
            System.out.println("No filename given.");
            return null;
        }

        Component top = getTopLevelAncestor();
        if (top == null) {
            System.out.println("Cannot find the top level window/applet.\n"
                + "Did you remember to add() before setfile()?");
            return null;
        }

        Class<? extends Component> appClass = top.getClass();
        URL url = appClass.getResource(path);

        if (url == null) {
            System.out.println("Couldn't find path to file: " + path);
        }

        return url;
    }
}
