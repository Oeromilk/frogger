package cha;

import java.awt.*;
import java.awt.geom.*;

import static java.lang.Math.*;

public class CHCurve
extends CHComponent
{
    private GeneralPath path;
    private int npoints;
    private double ts, tf;
    double[] xpoints, ypoints;
    private double xmin, xmax, xspan;
    private double ymin, ymax, yspan;
    
    public CHCurve()
    {
        super();
        npoints = 101;
        setLimits(0, 2 * PI);
        super.setBounds(50, 150, 200, 100);
    }
    
    public void setBounds(double x, double y, double w, double h) {
        super.setBounds(x, y, w, h);
        path = null;
    }
    
    public void setLimits(double start, double finish) {
        ts = start;
        tf = finish;
        xpoints = ypoints = null;
    }
    
    public void setResolution(int n) {
        npoints = n;
        xpoints = ypoints = null;
    }
    
    public int getResolution() {
        return npoints;
    }
    
    /**
     *  overload this method to plot y(x)
     */
    public double y(double x) {
        return sin(x);
    }
    
    /**
     *  overload this method to produce 2D curves
     */
    public double x(double t) {
        return t;
    }
    
    public void paintComponent(Graphics g) {
        if(xpoints == null || ypoints == null) {
            calcPoints();
        }
        
        if(path == null) {
            setPath();
        }
        
        Graphics2D g2d = (Graphics2D) g;
                
        g2d.setColor(getBackground());
        g2d.fill(path);
        
        g2d.setColor(getForeground());
        g2d.draw(path);
        
        super.paintComponent(g);
    }
    
    private void calcPoints() {
        double td = (tf - ts) / (npoints - 1);
        xmin = xmax = x(ts);
        ymin = ymax = y(ts);
        xpoints = new double[npoints];
        ypoints = new double[npoints];
        for(int i = 0; i < npoints; i++) {
            double t = ts + td * i;
            xpoints[i] = x(t);
            ypoints[i] = y(t);
            xmax = max(xpoints[i], xmax);
            xmin = min(xpoints[i], xmin);
            ymax = max(ypoints[i], ymax);
            ymin = min(ypoints[i], ymin);
        }
        xspan = xmax - xmin;
        yspan = ymax - ymin;
        
        // force recalculation of path
        path = null;
    }
    
    private void setPath() {
        if(npoints == 0) {
            System.err.println("Error: no points in path.");
            return;
        }
        
        if(xpoints == null || ypoints == null)  {
            System.err.println("Error: xpoints[] and ypoints[] " 
                + "have not been calculated.");
            return;
        }
        
        if(xpoints.length != npoints || ypoints.length != npoints) {
            System.err.println("Error: point vectors xpoints["
                + xpoints.length + "] and ypoints[" + ypoints.length
                + "] do not contain the correct number of points.");
            return;
        }
        
        path = new GeneralPath();
        addPoint(xpoints[0], ypoints[0], true);
        for(int i = 1; i < npoints; i++) {
            addPoint(xpoints[i], ypoints[i]);
        }
    }
    
    private void addPoint(double x, double y)
    {
        addPoint(x, y, false);
    }
    
    private void addPoint(double xp, double yp, boolean move)
    {
        Rectangle bounds = getBounds();
        
        float x = (float) (
            (bounds.width - 1) * (xp - xmin) / xspan);
        
        float y = (float) (
            (bounds.height - 1) * (ymax -yp) / yspan);
            
        if(move) {
            path.moveTo(x, y);
        } else {
            path.lineTo(x, y);
        }
    }
    
    public PathIterator getPath() {
        if(xpoints == null || ypoints == null) {
            calcPoints();
        }
        
        if(path == null) {
            setPath();
        }
        
        Rectangle bounds = getBounds();
        AffineTransform at = new AffineTransform();
        at.translate(bounds.x, bounds.y);
        return path.getPathIterator(at);
    }
}