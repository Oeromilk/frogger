package cha;

import java.awt.*;
import java.applet.AudioClip;
import java.applet. Applet;
import java.net.URL;
import javax.swing.*;

/**
 * Write a description of class Oval here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CHClip
extends CHComponent
{
    private AudioClip clip;
    private Player loop;
    private boolean running;
    
    public CHClip() {
    }
    
    public void setFile(String path) {
//         if(path == null) {
//             System.out.println("No filename given.");
//             return;
//         }
//         
//         Component top = getTopLevelAncestor();
//         if (top == null) {
//             System.out.println("Cannot find the top level window/applet.\n"
//                 + "Did you remember to add() before setfile()?");
//             return;
//         }
//         
//         Class<? extends Component> appClass = top.getClass();
//         URL clipURL = appClass.getResource(path);

        URL clipURL = getURL(path);
        
        try {
            clip = Applet.newAudioClip(clipURL);
        } catch (Exception e) {
            System.err.println(e);
        }
            
        if (clip == null) {
            System.out.println("Couldn't find file: " + path);
            return;
        }
    }
    
    public boolean isValid() {
        return clip != null;
    }
    
    public void play() {
        if(clip == null) {
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        
        clip.play();
    }
    
    public void loop() {
        if(clip == null) {
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        
//         clip.loop();
        loop = new Player();
        loop .start();
    }
    
    public void stop() {
        if(clip == null) {
            return;
        }
        
        clip.stop();
        running = false;
    }
    
    public class Player
    extends Thread {
        public void run() {
            running = true;
            clip.loop();
            while(running) {
               try {
                   Thread.sleep(30);
                } catch (Exception e) { }
            }
        }
    }
}
