package cha;

import java.awt. GridBagConstraints;
import java.awt. Rectangle;
import java.awt. Toolkit;
import java.awt. Dimension;
import java.awt. FlowLayout;
import java.awt. Component;
import javax.swing. JTextField;
import javax.swing. JFrame;

/**
 * Write a description of class CHButton here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CHField
extends JTextField {
    private GridBagConstraints constraints;
        
    public CHField() {
        this("Empty Field");
    }
    
    public CHField(String label) {
        super(label);
        constraints = new GridBagConstraints();
        setBounds(100, 300, 300, 20);
    }
    
    public GridBagConstraints getConstraints() {
        return constraints;
    }
    
    public void setInt(int x) {
        setText("" + x);
    }
    
    public int getInt() {
        String s = getText();
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            System.err.println(e);
        }
        return Integer.MIN_VALUE;
    }
    
    public void setDouble(double x) {
        setText("" + x);
    }
    
    public double getDouble() {
        String s = getText();
        try {
            return Double .parseDouble(s);
        } catch (Exception e) {
            System.err.println(e);
        }
        return Double.NaN;
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
        JTextField j = new JTextField(s);
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
    
    public void setGridLocation(int row, int col) {
        constraints. gridx = col;
        constraints. gridy = row;
    }
}
