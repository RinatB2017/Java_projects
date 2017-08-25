/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test3;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author boss
 */
public class MyKeyListener implements KeyListener{

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        System.out.println("keyPressed="+KeyEvent.getKeyText(ke.getKeyCode()));
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        System.out.println("keyReleased="+KeyEvent.getKeyText(ke.getKeyCode()));
   }
    
}
