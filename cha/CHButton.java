package cha;

import cha.action.*;
import java.awt. GridBagConstraints;
import java.awt. Rectangle;
import java.awt. Toolkit;
import java.awt. Dimension;
import java.awt. FlowLayout;
import java.awt. Component;
import javax.swing. JButton;
import javax.swing. JFrame;

public class CHButton
extends JButton
{
    private GridBagConstraints constraints;
        
    public CHButton() {
        super();
        constraints = new GridBagConstraints();
        setBounds(20, 300, 60, 30);
        setAction(new Blink());
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
        JButton j = new JButton(s);
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
    
    public GridBagConstraints getConstraints() {
        return constraints;
    }
    
    public void setGridLocation(int row, int col) {
        constraints. gridx = col;
        constraints. gridy = row;
    }
}
