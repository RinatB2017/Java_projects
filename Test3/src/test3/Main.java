/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test3;

import com.sun.java.accessibility.util.AWTEventMonitor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author boss
 */
public class Main extends JPanel{
    Ball ball = new Ball(this);

    private void move() {
        ball.move();
    }
    
    /**
     *
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
        ball.paint(g2d);
    }
    
    public static void main(String[] args) throws InterruptedException {
        KeyListener listener = new MyKeyListener();
        AWTEventMonitor.addKeyListener(listener);
        //setFocusable(true);
        
        JFrame frame = new JFrame("Test");
        Main main = new Main();
        frame.add(main);
        frame.setSize(300, 300);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        while(true) {
            main.move();
            main.repaint();
            Thread.sleep(10);
        }
    }
    
}
