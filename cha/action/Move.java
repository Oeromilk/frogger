package cha.action;

import cha.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Write a description of class FreeMovement here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Move
extends CHAction
{
    Double x, y;
    Double xv, yv;
    Double xa, ya;
    private CHComponent thing;
    
    public void connect(CHComponent t) {
        thing = t;
    }
    
    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public void setVelocity(double xv, double yv) {
        this.xv = xv;
        this.yv = yv;
    }
    
    public void setAcceleration(double xa, double ya) {
        this.xa = xa;
        this.ya = ya;
    }
    
    public void step() {
        if(x != null || y != null) {
            thing.setLocation(x, y);
        }
        if(xv != null || yv != null) {
            thing.setVelocity(xv, yv);
        }
        if(xa != null || ya != null) {
            thing.setAcceleration(xa, ya);
        }
        thing.moveOneStep();
    }
}
