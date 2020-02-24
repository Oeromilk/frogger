package cha;

import java.awt.Image;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;

/**
 * Write a description of class Oval here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CHImage
extends CHComponent
{
    MissingIcon icon;
    Image image;
    BufferedImage bufferedImage;

    public CHImage() {
        super();
        icon = new MissingIcon();
        int w = icon.getIconWidth();
        int h = icon.getIconHeight();
        setBounds(300, 150, w, h);
    }

    public void setFile(String path) {
        URL imgURL = getURL(path);
        if (imgURL == null) {
            System.out.println("Couldn't open image at: " + path);
            return;
        }

        ImageIcon icon = new ImageIcon(imgURL);
        if (icon == null || icon.getIconWidth() < 0
        || icon.getIconHeight() < 0) {
            System.out.println("Couldn't extract image from: " + path);
            return;
        }
        image = icon.getImage(); 
        int x = getX();
        int y = getY();
        int w = icon.getIconWidth();
        int h = icon.getIconHeight();
        setBounds(x, y, w, h);  

        //         try {
        //             bufferedImage = ImageIO.read(new File(path));
        //             int x = getX();
        //             int y = getY();
        //             int w = bufferedImage.getWidth();
        //             int h = bufferedImage.getHeight();
        //             setBounds(x, y, w, h);
        //         } catch (IOException e) {
        //             e.printStackTrace();
        //         }
    }

    public boolean isValid() {
        return image != null;
    }

    private void makeBuffer() {
        int width = getWidth();
        int height = getHeight();
        if(bufferedImage != null
        && bufferedImage.getWidth() == width
        && bufferedImage.getHeight() == height) {
            return;
        }

        bufferedImage = new BufferedImage(
            width, height,
            BufferedImage.TYPE_INT_RGB);

        Graphics g = bufferedImage.createGraphics();
        paintComponent(g);
        g.dispose();
    }

    /**
     * Common code for getPixel and setPixel.
     * @param rx x relative to the left edge of the image.
     * @param ry y relative to the top edge of the image.
     * @return true iff x and y are in bounds.
     * As a side effect, create a buffered image 
     * for setting and getting pixels.
     */
    private boolean checkPixel(double rx, double ry) {
        int width = getWidth();
        int height = getHeight();
        if(x < 0 || width <= x) {
            System.out.println("x (" + x 
                + ") is out of range [0 .. " + width + "].");
            return false;
        }

        if(y < 0 || height <= y) {
            System.out.println("y (" + y 
                + ") is out of range [0 .. " + height + "].");
            return false;
        }

        makeBuffer();
        return true;
    }
    
    /**
     * @param rx x relative to the left edge of the image.
     * @param ry y relative to the top edge of the image.
     * @return the color of the pixel at (x,y).
     */
    public Color getPixel(double rx, double ry) {
        if(!checkPixel(rx, ry)) {
            System.out.println("getPixel failed.");
            return null;
        }
        
        int x = (int) rx;
        int y = (int) ry;
        int rgb = bufferedImage.getRGB(x, y);
        return new Color(rgb);
    }

    /**
     * @param rx x relative to the left edge of the image.
     * @param ry y relative to the top edge of the image.
     * @param c the Color to set.
     */
    public void setPixel(double rx, double ry, Color c) {
        if(!checkPixel(rx, ry)) {
            System.out.println("getPixel failed.");
            return;
        }
        
        int x = (int) rx;
        int y = (int) ry;
        int rgb = c.getRGB();
        bufferedImage.setRGB(x, y, rgb);
    }

    public void paintComponent(Graphics g) {
        Component top = getTopLevelAncestor();
        Dimension size = getSize();
        int w = size.width;
        int h = size.height;

        if(bufferedImage != null) {
            g.drawImage(bufferedImage, 0, 0, w, h, top);
        } 
        else if(image != null) {
            g.drawImage(image, 0, 0, w, h, top);
        } 
        else {        
            icon.setSize(w, h);
            icon.paintIcon(this, g, 0, 0);
        }
        super.paintComponent(g);
    }
}
