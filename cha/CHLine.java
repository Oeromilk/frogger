package cha;

import java.awt.*;
import java.awt.geom.*;

public class CHLine
extends CHComponent
{
    private GeneralPath path;
    private boolean first;
    
    public CHLine()
    {
        super();
        path = new GeneralPath();
        first = true;
        super.setBounds(0, 0, 200, 50);
    }
    
    public void addPoint(double x, double y)
    {
        if(first) {
            super.setBounds(x, y, 1, 1);
            path.moveTo(0, 0);
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
            AffineTransform at = AffineTransform.getTranslateInstance(tx, ty);
            path.transform(at);
            
            // add the point relative to the current bounds
            int nx = (int) Math.round(x - nB.x);
            int ny = (int) Math.round(y - nB.y);
            path.lineTo(nx, ny);
        }
        first = false;
    }
    
    public void setBounds(double x, double y, double w, double h) {
        Rectangle bounds = getBounds();
        double rx = (w - 1) / bounds.getWidth();
        double ry = (h - 1) / bounds.getHeight();
        AffineTransform at = new AffineTransform();
        PathIterator pi = path.getPathIterator(at);
        GeneralPath newpath = new GeneralPath();
        boolean first = true;
        while(!pi.isDone()) {
            float[] coords = new float[6];
            int type = pi.currentSegment(coords);
            int nx = (int) Math.round(coords[0] * rx);
            int ny = (int) Math.round(coords[1] * ry);
            if(first) {
                newpath.moveTo(nx, ny);
            } else {
                newpath.lineTo(nx, ny);
            }
            first = false;
            pi.next();
        }
        path = newpath;
        super.setBounds(x, y, w, h);
    }
    
    public void paintComponent(Graphics g) {
        if(first) {
            g.setColor(getForeground());
            g.drawString("Empty CHLine", 50, 40);
        } else {        
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(getBackground());
            g2d.fill(path);
            
            g2d.setColor(getForeground());
            g2d.draw(path);
        }
        
        super.paintComponent(g);
    }
    
    public PathIterator getPath() {
        Rectangle bounds = getBounds();
        AffineTransform at = new AffineTransform();
        at.translate(bounds.x, bounds.y);
        return path.getPathIterator(at);
    }
}
