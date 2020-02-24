package cha.action;

import cha.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;

import static java.awt.geom.PathIterator.*;

/**
 * Write a description of class FreeMovement here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Follow
extends CHAction
{
    Double x, y;
    Double xv, yv;
    Double xa, ya;
    private CHComponent thing;
    private CHComponent pathComponent;
    private PathIterator path;
    private int position;
    private boolean wrap;
    
    public Follow() {
        super();
        setPath(new CHCurve());
        position = 0;
        wrap = true;
    }
    
    public void connect(CHComponent t) {
        thing = t;
    }
    
    public void setPath(CHComponent c) {
        pathComponent = c;
        path = c.getPath();
    }
    
    public void setWrap(boolean w) {
        wrap = w;
    }
    
    public void step() {
        if(path.isDone()) {
            if(!wrap) {
                System.err.println("End of path: " + pathComponent);
                return;
            } else {
                path = pathComponent.getPath();
            }
        }
        
        float[] coords = new float[6];
        int type = path.currentSegment(coords);
        path.next();
        
        switch(type) {
            case SEG_MOVETO:
            case SEG_LINETO:
            case SEG_QUADTO:
            case SEG_CUBICTO:
                thing.setLocation(coords[0], coords[1]);
                break;
            default:
        }
    }
}
