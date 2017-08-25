/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my_bluetooth_project;

import com.sun.xml.internal.ws.util.ByteArrayBuffer;

/**
 *
 * @author tux
 */
public class My_Bluetooth_project 
{
    public void test_1()
    {
        ByteArrayBuffer ba = new ByteArrayBuffer();
        for(int n=0; n<25; n++)
        {
            ba.write(n);
        }
        
        System.out.println("Begin");
        ModBus m = new ModBus();
        
        m.set_address(0x12);
        m.set_command(3);
        m.set_data(ba);
        
        System.out.println(m.get_string());
        System.out.println("End");
        
        Test test = new Test();
        test.start();
        System.out.println("The end!");
    }
    
    public static void main(String[] args) 
    {
        String str = "25.5";
        try {
            //System.out.println(Integer.decode(str));
            System.out.println(Float.parseFloat(str));
        } catch(NumberFormatException e) {
            System.err.println("Error: "+e.getMessage());
        }
    }
    
}
