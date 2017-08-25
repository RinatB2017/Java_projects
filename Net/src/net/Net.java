/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author boss
 */
public class Net {

    /**
     * @param args the command line arguments
     * @throws java.net.UnknownHostException
     */
    public static void main(String[] args) throws UnknownHostException {
        InetAddress Address = InetAddress.getLocalHost();
        System.out.println("Address="+Address);
        
        InetAddress SW[] = InetAddress.getAllByName("www.nba.com");
        for (InetAddress SW1 : SW) {
            System.out.println(SW1);
        }
    }
    
}
