/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;

/**
 *
 * @author boss
 */
public class Test_Timer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        javax.swing.Timer timer = new javax.swing.Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("OK");
            }
        });
    }

}
