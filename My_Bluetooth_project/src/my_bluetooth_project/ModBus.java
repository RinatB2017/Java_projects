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
public class ModBus 
{
    int address;
    int command;
    ByteArrayBuffer ba;

    public ModBus() 
    {
        this.address = 0;
        this.command = 0;
        ba = new ByteArrayBuffer();
    }
    
    public boolean set_address(int address)
    {
        if(address < 0) return false;
        if(address > 0xFF) return false;
        this.address = address;
        return true;
    }

    public boolean set_command(int cmd)
    {
        if(cmd < 0) return false;
        if(cmd > 0xFF) return false;
        this.command = cmd;
        return true;
    }

    public boolean set_data(ByteArrayBuffer data)
    {
        if(data.size() > 100) return false;
        ba = data;
        return true;
    }
    
    String get_string() 
    {
        StringBuilder str = new StringBuilder();
        
        str.append(':');
        str.append(String.format("%02X", address));
        str.append(String.format("%02X", command));
        for(int n=0; n<ba.size(); n++)
        {
            str.append(String.format("%02X", ba.getRawData()[n]));
        }
        str.append('\n');
        
        return str.toString();
    }
}
