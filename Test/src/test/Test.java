package test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Test
{
    public static void main(String[] args) throws IOException 
    {
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
        
        //System.out.println(mb.get_data_2(0x01020304));
        //System.out.println(mb.get_data_4(0x11223344));
    }
}