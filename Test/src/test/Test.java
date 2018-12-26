package test;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import javax.xml.bind.DatatypeConverter;

/*
:000124FFFF000000000000FFFFFFFFFFFF0000FFFFFF000000000000000000000000FF00000000
uint16_t MainBox::get_value(NewMoonLightPacket *packet, uint16_t address)
{
    union UINT16 {
        uint16_t value;
        struct {
            uint8_t c:4;
            uint8_t d:4;
            uint8_t a:4;
            uint8_t b:4;
        } bytes;
    };

    union UINT16 temp;
    temp.value = address;

    return packet->body.data[temp.bytes.a][temp.bytes.b] << 8 | packet->body.data[temp.bytes.c][temp.bytes.d];
}
*/

public class Test
{
    static String hex_str;
    static byte[] data;
    
    static void print(String text) {
        System.out.println(text);
    }
    
    static int get_value(int address) {
        if(address > hex_str.length()) {
            return -1;
        }
        String address_str = hex_str.substring(address, address+2);
        int value = Integer.parseInt(address_str, 16);
        return value;
    }
    
    private static boolean test(String message) throws UnsupportedEncodingException {
        print(message);
        hex_str = message.substring(1, message.length() - 1);
        print(hex_str);
        print("hex_str.length " + hex_str.length());
        
        int addr = 0;
        print("address " + get_value(addr));

        addr+=2;
        print("cmd " + get_value(addr));

        addr+=2;
        print("len " + get_value(addr));

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        addr = 6;
        for(int n=addr; n<hex_str.length(); n+=2) {
            String str = hex_str.substring(n, n+2);
            int value = Integer.parseInt(str, 16);
            
            output.write((byte)value);
        }
        data = output.toByteArray();
       
        return true;
    }
  
    public static void main(String[] args) throws UnsupportedEncodingException {
        print("Test");
        
        String temp_str = ":000124FFFF000000000000FFFFFFFFFFFF0000FFFFFF000000000000000000000000FF00000000\n";
        boolean res = test(temp_str);
        print("temp return " + res);
        
        print("data len " + data.length);
        if(res) {
            for(int n=0; n<data.length; n++) {
                System.out.print(String.format("%x ", data[n]));
            }
        }
        print("");
        print("The end!");
    }
}