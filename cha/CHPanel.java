package cha;
import java.awt.GridBagLayout;
import javax.swing.JPanel;

public class CHPanel
extends JPanel
{
    public CHPanel () {
        super(new GridBagLayout());
    }
    
    public void add(CHButton b) {
        add(b, b.getConstraints());
    }
    
    public void add(CHField b) {
        add(b, b.getConstraints());
    }
}
