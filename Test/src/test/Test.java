package test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class Test
{
    public static void prepare_packet() throws IOException {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        data.write(0x01);
        data.write(0x02);
        data.write(0x03);
        data.write(0x04);
        data.write(0x05);
        data.write(0x06);
        data.write(0x07);
        
        ModBus mb = new ModBus();
        mb.set_address(0x01);
        mb.set_command(0x00);
        mb.set_data(data);
        
        System.out.println(mb.get_string());
    }

    public static void prepare_zero_packet() throws IOException {
        
        ModBus mb = new ModBus();
        mb.set_address(0x01);
        mb.set_command(0x01);
        
        System.out.println(mb.get_string());
    }
    
    public static long convertToLong(byte[] array) 
    {
        ByteBuffer buffer = ByteBuffer.wrap(array);
        return buffer.getLong();
    }
    
    public static boolean check_packet(ByteArrayOutputStream i_data) {
        Checksum checksum = new CRC32();
        checksum.update(i_data.toByteArray(), 0, i_data.size() - 4);
        long checksumValue = checksum.getValue();
        
        byte[] array = new byte[8];
        int len = i_data.toByteArray().length;
        array[0] = 0;
        array[1] = 0;
        array[2] = 0;
        array[3] = 0;
        array[4] = i_data.toByteArray()[len - 4];
        array[5] = i_data.toByteArray()[len - 3];
        array[6] = i_data.toByteArray()[len - 2];
        array[7] = i_data.toByteArray()[len - 1];
        long packet_crc32 = convertToLong(array);
        
        //System.err.println("checksumValue " + Long.toHexString(checksumValue));
        //System.err.println("packet_crc32  " + Long.toHexString(packet_crc32));
        
        if(checksumValue != packet_crc32)   
            return false;
        
        return true;
    }
    
    public static void main(String[] args) throws IOException 
    {
        //prepare_packet();
        prepare_zero_packet();
        
        ByteArrayOutputStream packet = new ByteArrayOutputStream();
        packet.write(0x01);
        packet.write(0x01);
        packet.write(0x00);
        packet.write(0xE7);
        packet.write(0x98);
        packet.write(0x82);
        packet.write(0x64);
        
        boolean ok = check_packet(packet);
        System.err.println(ok ? "OK" : "FAIL");
    }
}