package cha;

import java.awt. BasicStroke;
import java.awt. Color;
import java.awt. Component;
import java.awt. Graphics;
import java.awt. Graphics2D;
import javax.swing. Icon;

    /**
     * The "missing icon" is a white box with a black border and a red x.
     * It's used to display something when there are issues loading an
     * icon from an external location.
     *
     * @author Collin Fagan
     */
    public class MissingIcon
    implements Icon
    {
        private double width;
        private double height;

        public MissingIcon() {
            width = 64;
            height = width;
        }

        // private BasicStroke stroke = new BasicStroke(4);

        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2d = (Graphics2D) g.create();

            int rw = (int) Math.round(width / 16);
            BasicStroke rstroke = new BasicStroke(rw);
            g2d.setStroke(rstroke);
            
            g2d.setColor(Color.BLACK);
            int ix = (int) Math.round(x);
            int iy = (int) Math.round(y);
            int iw = (int) Math.round(width - 1);
            int ih = (int) Math.round(height - 1);
            g2d.drawRect(ix, iy, iw, ih);

            g2d.setColor(Color.RED);

            int sw = (int) Math.round(width / 8);
            BasicStroke stroke = new BasicStroke(sw);
            g2d.setStroke(stroke);
            
            int left   = (int) Math.round(x + width  / 5);
            int right  = (int) Math.round(x + width  * 4 / 5);
            int top    = (int) Math.round(y + height / 5);
            int bottom = (int) Math.round(y + height * 4 / 5);
            g2d.drawLine(left, top, right, bottom);
            g2d.drawLine(left, bottom, right, top);

            g2d.dispose();
        }

        public void setSize(double w, double h) {
            width = w;
            height = h;
        }

        public int getIconWidth() {
            return (int) Math.round(width);
        }

        public int getIconHeight() {
            return (int) Math.round(height);
        }
    }

