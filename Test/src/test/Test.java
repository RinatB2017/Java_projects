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
    static byte[][] data_arr;
    
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
            
            print(Integer.toHexString(value) + " ");
            
            output.write((byte)value);
        }
        data = output.toByteArray();
       
        return true;
    }
    
    /*
        union UINT16 {
        uint16_t value;
        struct {
            uint8_t c:4;
            uint8_t d:4;
            uint8_t a:4;
            uint8_t b:4;
        } bytes;
    };
    */
    private static int get_led_value(int address)
    {
        int b = (address >> 12) & 0xF;
        int a = (address >> 8) & 0xF;
        int d = (address >> 4) & 0xF;
        int c = address & 0xF;
        
        return data_arr[a][b] << 8 | data_arr[c][d];
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
        
        int index = 0;
        data_arr = new byte[6][6];
        for(int y=0; y<6; y++) {
            for(int x=0; x<6; x++) {
                data_arr[y][x] = data[index];
                System.out.print(String.format("%x ", data_arr[x][y]));
                index++;
            }
        }
        print("");
        
        int led_15 = get_led_value(0x2112);
        int led_16 = get_led_value(0x4151);
        int led_17 = get_led_value(0x3102);

        int led_12 = get_led_value(0x2255);
        int led_13 = get_led_value(0x3245);
        int led_14 = get_led_value(0x2535);

        int led_09 = get_led_value(0x4223);
        int led_10 = get_led_value(0x5213);
        int led_11 = get_led_value(0x0333);

        int led_06 = get_led_value(0x4314);
        int led_07 = get_led_value(0x5334);
        int led_08 = get_led_value(0x0424);

        int led_03 = get_led_value(0x5415);
        int led_04 = get_led_value(0x0510);
        int led_05 = get_led_value(0x4400);

        int led_00 = get_led_value(0x2001);
        int led_01 = get_led_value(0x4011);
        int led_02 = get_led_value(0x3050);
        
        print("led_15 " + (led_15 & 0xFFFF));
        print("led_16 " + (led_16 & 0xFFFF));
        print("led_17 " + (led_17 & 0xFFFF));

        print("led_12 " + (led_12 & 0xFFFF));
        print("led_13 " + (led_13 & 0xFFFF));
        print("led_14 " + (led_14 & 0xFFFF));

        print("led_09 " + (led_09 & 0xFFFF));
        print("led_10 " + (led_10 & 0xFFFF));
        print("led_11 " + (led_11 & 0xFFFF));

        print("led_06 " + (led_06 & 0xFFFF));
        print("led_07 " + (led_07 & 0xFFFF));
        print("led_08 " + (led_08 & 0xFFFF));

        print("led_03 " + (led_03 & 0xFFFF));
        print("led_04 " + (led_04 & 0xFFFF));
        print("led_05 " + (led_05 & 0xFFFF));
        
        print("led_00 " + (led_00 & 0xFFFF));
        print("led_01 " + (led_01 & 0xFFFF));
        print("led_02 " + (led_02 & 0xFFFF));
        
        int address = 0x1234;
        int b = (address >> 12) & 0xF;
        int a = (address >> 8) & 0xF;
        int d = (address >> 4) & 0xF;
        int c = address & 0xF;
        print("a " + a);
        print("b " + b);
        print("c " + c);
        print("d " + d);
        
        print("The end!");
    }
}