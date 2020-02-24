package cha;

import java.awt.*;
import javax.swing.*;
// import static java.lang.Math.max;

public class CHContainer
extends CHComponent {
    Rectangle lastFit;
    boolean calledOnce;
    boolean visible;

    public CHContainer() {
        super();
        setBackground(new Color(0, 0, 0, 0));
        lastFit = null;
        calledOnce = false;
        visible = true;
    }

    /**
     * all instances of CHContainer should override this method
     */
    public void init() {
        System.out.println(
            "Empty CHContainer.  "
            + "Did you remember to define init()?");
    }

    public void add(CHComponent c, Object l, int p) {
        //         System.out.println(this + ".add("
        //             + c + ", " + l + ", " + p + ")");
        super.add(c, l, p);
        if(c instanceof CHContainer) {
            CHContainer ch = (CHContainer) c;
            ch.start();
        }
    }

    public CHComponent add(CHComponent c, int p) {
        add(c, null, p);
        return c;
    }

    public CHComponent add(CHComponent c) {
        return add(c, -1);
    }

    public void start() {
        init();
        initBounds();

        for (Component c : getComponents()) {
            if(c instanceof CHContainer) {
                ((CHContainer) c).start();
            }
        }
    }

    public void setVisible(boolean v) {
        visible = v;
        try {
            Container top = getTopLevelAncestor();
            top.repaint();
        } catch (Exception e) { }
    }

    public boolean isVisible()
    {
        return visible;
    }

    /**
     * find the bounding rectangle for all components
     */
    public Rectangle getCBounds() {
        Rectangle cBounds = new Rectangle(0, 0, 0, 0);

        for(Component c : getComponents()) {
            Rectangle b = c.getBounds();
            cBounds .add(b);
        }

        // add 1 pixel boundary to prevent clipping
        int w = cBounds .width + 1;
        int h = cBounds .height + 1;
        cBounds .add(w, h);

        return cBounds;
    }

    /**
     * resize the container to ensure that 
     * it will display all components
     */
    public void fitToBounds() {
        Rectangle b = getBounds();

        // prevent event-triggered recursion
        if(b.equals(lastFit)) {
            return;
        }
        lastFit = b;

        Rectangle c = getCBounds();
        if(b.width != c.width || b.height != c.height) {
            scaleTo(b.width, b.height);
        }
    }

    public void initBounds() {
        if(calledOnce)
            return;

        calledOnce = true;
        Rectangle b = getBounds();
        Rectangle c = getCBounds();
        setBounds(b.x, b.y, c.width, c.height);
    }

    /**
     * set the dimension for the collection, 
     * and adjust the bounds of any contained components
     * to fit the new bounds
     * @param w the new width
     * @param h the new height
     */
    private void scaleTo(double w, double h) {
        Rectangle bounds = getBounds();
        int x = bounds.x;
        int y = bounds.y;
        Rectangle cbounds = getCBounds();
        double rx = (w - 0) / cbounds.getWidth();
        double ry = (h - 0) / cbounds.getHeight();
        for(Component p : getComponents()) {
            Rectangle pB = p.getBounds();
            double px = pB.x * rx;
            double py = pB.y * ry;
            double pw = pB.width * rx;
            double ph = pB.height * ry;
            // String pLabel = p.toString().split("\\[")[0];
            // System.out.printf("%s (%.1f, %.1f, %.1f, %.1f)", pLabel, px, py, pw, ph);
            if(p instanceof CHComponent) {
                // System.out.println(" is a CHComponent");
                ((CHComponent) p).setBounds(px, py, pw, ph);
            } else {
                // System.out.println(" is NOT a CHComponent");
                int ix = (int) Math.round(px);
                int iy = (int) Math.round(py);
                int iw = (int) Math.round(pw);
                int ih = (int) Math.round(ph);
                p.setBounds(ix, iy, iw, ih);
            }
        }
        super.setBounds(x, y, w, h);
    }

    public void paintComponent(Graphics g) {
        if(!visible) {
            return;
        }

        fitToBounds();

        Dimension size = getSize();
        int w = size.width;
        int h = size.height;

        g.setColor(getBackground());
        g.fillRect(0, 0, w, h);

        super.paintComponent(g);
    }
}
