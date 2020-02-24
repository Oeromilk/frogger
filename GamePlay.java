
import cha.*;
import cha.action.*;

import java.awt.*;
import javax.swing.*;
import java.util.*;

import static java.awt.Color.*;
import static java.awt.event.KeyEvent.*;
import static javax.swing.Action.*;

public class GamePlay
extends CHApplet {
    CHRectangle frogCondition;
    CHRectangle endzone;
    CHLabel message;
    Integer menuHeight;
    SafeZone safeZone1;
    SafeZone safeZone2;
    CHImage frog;
    CHImage purplecar;
    CHImage racecar;
    CHImage redcar;
    CHImage whitecar;
    Turtles turtles1;
    Turtles turtles2;
    CHImage log1;
    CHImage log2;
    Leap upm, dnm, lfm, rtm;
    Color dBlue;
    CHRectangle [] endBlock;
    CHClip frogJumpSound;
    Start startAction;
    CHRectangle water;
    MovePCLeft movePCLeft;
    MoveWCLeft moveWCLeft;
    MoveT1Left moveT1Left;
    MoveT2Left moveT2Left;
    MoveRCRight moveRCRight;
    MoveRaceCRight moveRaceCRight;
    MoveLog1Right moveLog1Right;
    MoveLog2Right moveLog2Right;
    
    private class Leap
    extends Move {
        public void step() {
            super.step();
            frogJumpSound.play();
            int frogX = frog.getX() + 30;
            int frogY = frog.getY() + 35;
            Rectangle frogCBounds = frogCondition.getBounds();
            frogCondition.setLocation(frogX, frogY);
            Rectangle waterBounds = water.getBounds();
            Rectangle frogBounds = frog.getBounds();
            Rectangle log1Bounds = log1.getBounds();
            Rectangle log2Bounds = log2.getBounds();
            Rectangle turtles1Bounds = turtles1.getBounds();
            Rectangle turtles2Bounds = turtles2.getBounds();
            Rectangle eZone = endzone.getBounds();
            Rectangle winZone1 = endBlock[0].getBounds();
            Rectangle winZone2 = endBlock[1].getBounds();
            Rectangle winZone3 = endBlock[2].getBounds();
            Rectangle winZone4 = endBlock[3].getBounds();
            Rectangle purpleBounds = purplecar.getBounds();
            Rectangle whiteBounds = whitecar.getBounds();
            Rectangle redBounds = redcar.getBounds();
            Rectangle raceBounds = racecar.getBounds();
            
            if(purpleBounds.contains(frogCBounds)) {
                frog.setVisible(false);
            }
            
            if(whiteBounds.contains(frogCBounds)) {
                frog.setVisible(false);
            }
            
            if(redBounds.contains(frogCBounds)) {
                frog.setVisible(false);
            }
            
            if(raceBounds.contains(frogCBounds)) {
                frog.setVisible(false);
            }
            
            if(waterBounds.contains(frogCBounds)) {
                frog.setVisible(false);
            }
            
            if(waterBounds.contains(frogCBounds) && log1Bounds.contains(frogCBounds) || log2Bounds.contains(frogCBounds)) {
                frog.setVisible(true);
            }
            
            if(waterBounds.contains(frogCBounds) && turtles1Bounds.contains(frogCBounds) || turtles2Bounds.contains(frogCBounds)) {
                frog.setVisible(true);
            }
            
            if(
            winZone1.contains(frogCBounds) || winZone2.contains(frogCBounds)
            || winZone3.contains(frogCBounds) || winZone4.contains(frogCBounds)){
                message.setText("You Win!");
                frog.setVisible(true);
            } else {
                message.setText("");
            }
        }
    }
    
    public class MovePCLeft
    extends CHAction {
        public void step() {
            sleep(1);
            purplecar.moveOneStep();
            sleep(1);
            if(purplecar.getX() < -50) {
                purplecar.setLocation(720, 550);
            }
        }
    }
    
    public class MoveWCLeft
    extends CHAction {
        public void step() {
            sleep(1);
            whitecar.moveOneStep();
            sleep(1);
            if(whitecar.getX() < -50) {
                whitecar.setLocation(720, 390);
            }
        }
    }
    
    public class MoveT1Left
    extends CHAction {
        public void step() {
            sleep(1);
            turtles1.moveOneStep();
            sleep(1);
            if(turtles1.getX() < -150) {
                turtles1.setLocation(720, 265);
            }
        }
    }
    
    public class MoveT2Left
    extends CHAction {
        public void step() {
            sleep(1);
            turtles2.moveOneStep();
            sleep(1);
            if(turtles2.getX() < -150) {
                turtles2.setLocation(720, 155);
            }
        }
    }
    
    public class MoveRCRight
    extends CHAction {
        public void step() {
            sleep(1);
            redcar.moveOneStep();
            sleep(1);
            if(redcar.getX() > 770) {
                redcar.setLocation(-50, 450);
            }
        }
    }
    
    public class MoveRaceCRight
    extends CHAction {
        public void step() {
            sleep(1);
            racecar.moveOneStep();
            sleep(1);
            if(racecar.getX() > 770) {
                racecar.setLocation(-50, 510);
            }
        }
    }
    
    public class MoveLog1Right
    extends CHAction {
        public void step() {
            sleep(1);
            log1.moveOneStep();
            sleep(1);
            if(log1.getX() > 750) {
                log1.setLocation(-150, 200);
            }
        }
    }
    
    public class MoveLog2Right
    extends CHAction {
        public void step() {
            sleep(1);
            log2.moveOneStep();
            sleep(1);
            if(log2.getX() > 750) {
                log2.setLocation(-150, 90);
            }
        }
    }
    
    public void init() {
        setBackground(black);
        
        frogCondition = new CHRectangle();
        
        message = new CHLabel();
        add(message, 0);
        message.setBounds(300, 350, 150, 100);
        message.setText(" ");
        message.setForeground(green);
        
        frogJumpSound = new CHClip();
        add(frogJumpSound);
        frogJumpSound.setFile("frog-jump-sound.wav");
        
        movePCLeft = new MovePCLeft();
        add(movePCLeft);
        movePCLeft.setLimit(720);
        
        moveWCLeft = new MoveWCLeft();
        add(moveWCLeft);
        moveWCLeft.setLimit(720);
        
        moveT1Left = new MoveT1Left();
        add(moveT1Left);
        moveT1Left.setLimit(720);
        
        moveT2Left = new MoveT2Left();
        add(moveT2Left);
        moveT2Left.setLimit(720);
        
        moveRCRight = new MoveRCRight();
        add(moveRCRight);
        moveRCRight.setLimit(720);
        
        moveRaceCRight = new MoveRaceCRight();
        add(moveRCRight);
        moveRCRight.setLimit(720);
        
        moveLog1Right = new MoveLog1Right();
        add(moveLog1Right);
        moveLog1Right.setLimit(720);
        
        moveLog2Right = new MoveLog2Right();
        add(moveLog2Right);
        moveLog2Right.setLimit(720);
        
        upm = new Leap();
        add(upm);
        upm.setText("↑");
        upm.setVelocity(0, -56);
        upm.mapKey(this, VK_UP);
        
        CHButton up = new CHButton();
        add(up);
        up.setLocation(600, 650);
        up.setAction(upm);
        
        dnm = new Leap();
        dnm.setText("↓");
        dnm.setVelocity(0, 56);
        dnm.mapKey(this, VK_DOWN);

        CHButton dn = new CHButton();
        dn.setLocation(600, 690);
        add(dn);
        dn.setAction(dnm);
        
        lfm = new Leap();
        lfm.setText("←");
        lfm.setVelocity(-56, 0);
        lfm.mapKey(this, VK_LEFT);

        CHButton lf = new CHButton();
        lf.setLocation(550, 670);
        add(lf);
        lf.setAction(lfm);

        rtm = new Leap();
        rtm.setText("→");
        rtm.setVelocity(56, 0);
        rtm.mapKey(this, VK_RIGHT);

        CHButton rt = new CHButton();
        rt.setLocation(650, 670);
        add(rt);
        rt.setAction(rtm);
        
        Color endgreen;
        endgreen = new Color(103, 217, 65);
        
        
        endzone = new CHRectangle();
        add(endzone);
        endzone.setBounds(0, 0, 725, 100);
        endzone.setBackground(endgreen);
        
        endBlock = new CHRectangle[4];
        for(int i = 0; i < endBlock.length; i++) {
            dBlue = new Color(0, 3, 63);
            endBlock[i] = new CHRectangle();
            add(endBlock[i], 0);
            int x = 30 + i * 180;
            endBlock[i].setBounds(x, 20, 120, 80);
            endBlock[i].setBackground(dBlue);
            endBlock[i].setForeground(dBlue);
        }
        
        safeZone1 = new SafeZone();
        add(safeZone1);
        safeZone1.setLocation(0, 600);
        
        frog = new CHImage();
        purplecar = new CHImage();
        racecar = new CHImage();
        redcar = new CHImage();
        whitecar = new CHImage();
        turtles1 = new Turtles();
        log1 = new CHImage();
        turtles2 = new Turtles();
        log2 = new CHImage();
        
        //MJ created 2nd safezone and water with color
        safeZone2 = new SafeZone();
        add(safeZone2);
        safeZone2.setLocation(0, 320);
        
        dBlue = new Color(0, 3, 63);
        
        water = new CHRectangle();
        add(water);
        water.setBounds(0, 0, 725, 325);
        water.setBackground(dBlue);
        water.setForeground(dBlue);
        
        setup();
        buildMenus();
    } 
    
    public void setup() {
        add(purplecar);
        purplecar.setFile("purplecar.png");
        purplecar.setBounds(650, 550, 50, 50);
        purplecar.setVelocity(0, 0);
        purplecar.setAcceleration(0, 0);
        
        add(racecar);
        racecar.setFile("racecar.png");
        racecar.setBounds(20, 510, 60, 40);
        racecar.setVelocity(0, 0);
        racecar.setAcceleration(0, 0);
        
        add(redcar);
        redcar.setFile("redcar.png");
        redcar.setBounds(20, 450, 60, 40);
        redcar.setVelocity(0, 0);
        redcar.setAcceleration(0, 0);
        
        add(whitecar);
        whitecar.setFile("whitecar.png");
        whitecar.setBounds(650, 390, 55, 50);
        whitecar.setVelocity(0, 0);
        whitecar.setAcceleration(0, 0);
        
        add(turtles1, 0);
        turtles1.setLocation(540, 265);
        turtles1.setVelocity(0, 0);
        turtles1.setAcceleration(0, 0);
        
        add(turtles2, 0);
        turtles2.setLocation(540, 155);
        turtles2.setVelocity(0, 0);
        turtles2.setAcceleration(0, 0);
        
        add(log1, 0);
        log1.setFile("log.png");
        log1.setBounds(20, 200, 150, 60);
        log1.setVelocity(0, 0);
        log1.setAcceleration(0, 0);
        
        add(log2, 0);
        log2.setFile("log.png");
        log2.setBounds(20, 90, 150, 60);
        log2.setVelocity(0, 0);
        log2.setAcceleration(0, 0);
        
        add(frog, 0);
        frog.setFile("frog.png");
        frog.setBounds(300, 600, 50, 50);
        frog.setVelocity(0,0);
        frog.setAcceleration(0,0);
        frog.setVisible(true);
        
        add(frogCondition);
        frogCondition.setBounds(330, 630, 5, 5);
        
        message.setText(" ");
        upm.connect(frog);
        dnm.connect(frog);
        lfm.connect(frog);
        rtm.connect(frog);
    }
    
    public class Start
    extends CHAction {
        public void step() {
            Rectangle frogBounds = frog.getBounds();
            Rectangle winZone1 = endBlock[0].getBounds();
            Rectangle winZone2 = endBlock[1].getBounds();
            Rectangle winZone3 = endBlock[2].getBounds();
            Rectangle winZone4 = endBlock[3].getBounds();
            while(!(winZone1.contains(frogBounds) || winZone1.contains(frogBounds)
            || winZone3.contains(frogBounds) || winZone4.contains(frogBounds))) {
                movePCLeft.step();
                moveWCLeft.step();
                moveT1Left.step();
                moveT2Left.step();
                moveRCRight.step();
                moveRaceCRight.step();
                moveLog1Right.step();
                moveLog2Right.step();
            }
        }
    }
    
    public class Reset
    extends CHAction {
        public void step() {
            setup();
        }
    }
    
    public class Slow
    extends CHAction {
        public void step() {
            // Negative values purple white turtles1 turtles2
            // Positive values red race log1 log2
            purplecar.setVelocity(-2, 0);
            whitecar.setVelocity(-1, 0);
            racecar.setVelocity(5, 0);
            redcar.setVelocity(3, 0);
            turtles1.setVelocity(-3, 0);
            turtles2.setVelocity(-1, 0);
            log1.setVelocity(1, 0);
            log2.setVelocity(2, 0);
        }
    }
    
    public class Medium
    extends CHAction {
        public void step() {
            // Negative values purple white turtles1 turtles2
            // Positive values red race log1 log2
            purplecar.setVelocity(-4, 0);
            whitecar.setVelocity(-2, 0);
            racecar.setVelocity(10, 0);
            redcar.setVelocity(6, 0);
            turtles1.setVelocity(-6, 0);
            turtles2.setVelocity(-2, 0);
            log1.setVelocity(2, 0);
            log2.setVelocity(4, 0);
        }
    }
    
    public class Fast
    extends CHAction {
        public void step() {
            // Negative values purple white turtles1 turtles2
            // Positive values red race log1 log2
            purplecar.setVelocity(-8, 0);
            whitecar.setVelocity(-4, 0);
            racecar.setVelocity(20, 0);
            redcar.setVelocity(12, 0);
            turtles1.setVelocity(-8, 0);
            turtles2.setVelocity(-4, 0);
            log1.setVelocity(4, 0);
            log2.setVelocity(7, 0);
        }
    }
    
    public void buildMenus() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu appmenu = new JMenu();
        appmenu.setText("Frogger");
        menuBar.add(appmenu);
        appmenu.setMnemonic(VK_F);
        
        JMenu difficultymenu = new JMenu();
        difficultymenu.setText("Difficulty");
        menuBar.add(difficultymenu);
        difficultymenu.setMnemonic(VK_D);
        
        Start start = new Start();
        start.setText("Start");
        start.putValue(MNEMONIC_KEY, VK_S);
        
        Reset reset = new Reset();
        reset.setText("Reset");
        reset.putValue(MNEMONIC_KEY, VK_R);
        
        Slow slow = new Slow();
        slow.setText("Slow");
        
        Medium medium = new Medium();
        medium.setText("Medium");
        
        Fast fast = new Fast();
        fast.setText("Fast");
        
        JMenuItem s = new JMenuItem();
        appmenu.add(s);
        s.setAction(start);
        
        JMenuItem r = new JMenuItem();
        appmenu.add(r);
        r.setAction(reset);
        
        JMenuItem slowItem = new JMenuItem();
        difficultymenu.add(slowItem);
        slowItem.setAction(slow);
        
        JMenuItem mediumItem = new JMenuItem();
        difficultymenu.add(mediumItem);
        mediumItem.setAction(medium);
        
        JMenuItem fastItem = new JMenuItem();
        difficultymenu.add(fastItem);
        fastItem.setAction(fast);
    }
    
    public void setMenuHeight() {
        int windowHeight = this.getHeight();
        int paneHeight = this.getContentPane().getSize().height;
        menuHeight = windowHeight - paneHeight;
    }
    
    /***********************************************************
     * DO NOT Change the code below this line.
     ***********************************************************/
    public static void run() {
        int width = 720;
        int height = 740;
        CHApplet applet = new GamePlay(); 
        applet.run(width, height);
    }
    
    private GamePlay(){}
}
