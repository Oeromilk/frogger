
import cha.*;
import cha.action.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import static java.awt.Color.*;

public class Turtles
extends CHContainer {
    
    public void init() {
      CHImage turtle1;
      turtle1 = new CHImage();
      add(turtle1);
      turtle1.setFile("turtle.png");
      turtle1.setBounds(0, 0, 50, 50);
      
      CHImage turtle2;
      turtle2 = new CHImage();
      add(turtle2);
      turtle2.setFile("turtle.png");
      turtle2.setBounds(60, 0, 50, 50);
      
      CHImage turtle3;
      turtle3 = new CHImage();
      add(turtle3);
      turtle3.setFile("turtle.png");
      turtle3.setBounds(120, 0, 50, 50);
    } // end of init - DO NOT REMOVE
    
}
