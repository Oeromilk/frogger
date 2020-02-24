
import cha.*;
import cha.action.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import static java.awt.Color.*;

public class SafeZone
extends CHContainer {
    
    public void init() {
        CHRectangle safeZone;
        CHOval [] redDots;
        Random gen = new Random();
        Color purple = new Color(121, 40, 209); 
        
        safeZone = new CHRectangle();
        add(safeZone);
        safeZone.setBounds(0, 0, 720, 55);
        safeZone.setBackground(purple);
        
        redDots = new CHOval[500];
        for(int i = 0; i < redDots.length; i++){
            int x = gen.nextInt(720);
            int y = gen.nextInt(45);
            int w = gen.nextInt(13) - 3;
            int h = gen.nextInt(13) - 3;
            redDots[i] = new CHOval();
            add(redDots[i], 0);
            redDots[i].setBounds(x, y, w, h);
            redDots[i].setBackground(red);
            redDots[i].setForeground(blue);
        }
        
    } // end of init - DO NOT REMOVE
    
}
