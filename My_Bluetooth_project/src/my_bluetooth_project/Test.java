/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my_bluetooth_project;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author boss
 */
public class Test extends Thread{
    
    public void run()
    {
        int cnt = 0;
        boolean flag = true;
        while(flag)
        {
            cnt++;
            if(cnt > 10) flag = false;
            System.out.println("Test");
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
