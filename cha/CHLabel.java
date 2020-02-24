package cha;

import java.awt.*;
import java.awt.font.*;
import javax.swing.*;

import static java.lang.Math.round;

/**
 * Write a description of class Oval here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CHLabel
extends JLabel
{    
    public CHLabel() {
        super();
        super.setText("Empty Label");
        setBounds(50, 250, 500, 20);
        setBackground(new Color(0, 0, 0, 0));
    }

    public void setBounds(double x, double y, double w, double h) {
        int xi = (int) round(x);
        int yi = (int) round(y);
        int wi = (int) round(w);
        int hi = (int) round(h);
        super.setBounds(xi, yi, wi, hi);
    }

    public void setLocation(double x, double y) {
        super.setLocation(
            (int) Math.round(x), 
            (int) Math.round(y));
    }

    /**
     * set the width and height to match the current label
     */
    public void resizeToFit() {
        resizeToFit(getText());
    }

    /**
     * set the width and height to fit text
     * @param s the sample text
     */
    public void resizeToFit(String s) {
        Rectangle bounds = getBounds();
        int x = bounds.x;
        int y = bounds.y;

        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension screen = t.getScreenSize();

        JFrame jfrm = new JFrame();
        jfrm.setLayout(new FlowLayout());
        jfrm.setBounds(screen.width, screen.height, 1, 1);
        JLabel j = new JLabel(s);
        jfrm.add(j);
        jfrm.setVisible(true);
        Rectangle b = j.getBounds();
        jfrm.setVisible(false);

        int w = b.width;
        int h = b.height;

        setBounds(x, y, w, h);

        Component top = getTopLevelAncestor();
        if(!(top instanceof CHApplet)) {
            return;
        }
        if(((CHApplet) top).debugging()) {
            System.out.println(".setBounds(" + x + ", " + y + ", "
                + w + ", " + h + ");");
        }
    }
}
