package test;
//package com.boss.moonflower;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import java.util.zip.CRC32;
import java.util.zip.Checksum;

//---------------------------------------------------------------------------------------------

/**
 * Created by tux on 31.03.16.
 */
//---------------------------------------------------------------------------------------------
public class ModBus {
    int address;
    int command;
    int cnt_data;
    ByteArrayOutputStream packet;
    ByteArrayOutputStream data;

    //---------------------------------------------------------------------------------------------
    public ModBus() {
        this.address = 0;
        this.command = 0;
        this.cnt_data = 0;
        packet = new ByteArrayOutputStream();
        data = new ByteArrayOutputStream();
    }

    //---------------------------------------------------------------------------------------------
    public boolean set_address(int address) {
        if (address < 0) return false;
        if (address > 0xFF) return false;
        this.address = address;
        return true;
    }

    //---------------------------------------------------------------------------------------------
    public boolean set_command(int cmd) {
        if (cmd < 0) return false;
        if (cmd > 0xFF) return false;
        this.command = cmd;
        return true;
    }
    
    //---------------------------------------------------------------------------------------------
    public boolean set_data(ByteArrayOutputStream i_data) {
        this.data.write(i_data.toByteArray(), 0, i_data.toByteArray().length);
        return true;
    }

    //---------------------------------------------------------------------------------------------
    public String get_data_2(int value) {
        return String.format("%02X", value);
    }
    
    //---------------------------------------------------------------------------------------------
    public String get_data_4(int value) {
        return String.format("%04X", value);
    }
    
    //---------------------------------------------------------------------------------------------
    public long get_CRC32(byte bytes[]) {
        Checksum checksum = new CRC32();
         
        // update the current checksum with the specified array of bytes
        checksum.update(bytes, 0, bytes.length);
          
        // get the current checksum value
        long checksumValue = checksum.getValue(); 
        
        return checksumValue;
    }
    
    //---------------------------------------------------------------------------------------------
    private boolean add_address(int address) {
        if (address < 0)    return false;
        if (address > 0xFF) return false;
        
        packet.write(address);

        return true;
    }
    
    //---------------------------------------------------------------------------------------------
    private boolean add_command(int cmd) {
        if (cmd < 0)    return false;
        if (cmd > 0xFF) return false;
        
        packet.write(cmd);

        return true;
    }
    
    //---------------------------------------------------------------------------------------------
    private boolean add_data() {
        packet.write(data.toByteArray(), 0, data.toByteArray().length);
        return true;
    }
    
    
    //---------------------------------------------------------------------------------------------
    private byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(x);
        return buffer.array();
    }
    
    //---------------------------------------------------------------------------------------------
    private boolean add_cnt_data(int cnt_data) {
        if (cnt_data < 0)    return false;
        if (cnt_data > 0xFF) return false;
        
        packet.write(cnt_data);

        return true;
    }
    
    //---------------------------------------------------------------------------------------------
    private boolean add_CRC32() throws IOException {
        long crc32 = get_CRC32(packet.toByteArray());
        packet.write(longToBytes(crc32), 4, 4);
        return true;
    }
    
    //---------------------------------------------------------------------------------------------
    public void clear_packet() {
        packet.reset();
    }
    
    //---------------------------------------------------------------------------------------------
    public void clear_data() {
        data.reset();
    }
    
    //---------------------------------------------------------------------------------------------
    String get_string() throws IOException {
        StringBuilder str = new StringBuilder();

        clear_packet();
        
        add_address(address);
            System.err.println(packet.size());
        add_command(command);
            System.err.println(packet.size());
        add_cnt_data(data.toByteArray().length);
            System.err.println(packet.size());
        add_data();
            System.err.println(packet.size());
        add_CRC32();
            System.err.println(packet.size());
        
        str.append(':');
        for (int n = 0; n < packet.size(); n++) {
            str.append(String.format("%02X", packet.toByteArray()[n]));
        }
        str.append('\n');
        
        return str.toString();
    }
    //---------------------------------------------------------------------------------------------
}
