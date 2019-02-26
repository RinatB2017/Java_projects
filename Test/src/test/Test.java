package test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

    public static void prepare_zero_packet(int address, int cmd) throws IOException {
        
        ModBus mb = new ModBus();
        mb.set_address(address);
        mb.set_command(cmd);
        
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
        
        return (checksumValue == packet_crc32);   
    }
    
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
    
    public static void test_0() throws IOException {
        prepare_zero_packet(0, 0);
        prepare_zero_packet(0, 1);
        prepare_zero_packet(0, 2);
        prepare_zero_packet(0, 3);
        prepare_zero_packet(0, 4);
    }
    
    public static void test_1() {
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

    public static void test_2() {
        byte[] array = hexStringToByteArray("000000FF41D912");
        for(int n=0; n<array.length; n++)
        {
            System.err.println(String.format("%02X", array[n]));
        }
        System.err.println("");
    }
    
    public static void test_3() throws UnsupportedEncodingException {
        int ascii_len = 6;
        byte[] buffer = new byte[ascii_len];
        buffer[0] = 'A';
        buffer[1] = 'B';
        buffer[2] = 'C';
        buffer[3] = 'D';
        buffer[4] = 'E';
        buffer[5] = 'F';
        
        System.err.println(new String(buffer, "UTF-8").substring(1, ascii_len-1));
    }
    
    public static void test_4(int num) throws Exception {
        switch(num) {
            case 0:
                throw new Exception("num_9");
            case 1:
                throw new Exception("num_1");
            case 2:
                throw new Exception("num_2");
                
            default:
                break;
        }
        System.out.println(num);
    }

    public static void test_5() {
        for(int n=0; n<10; n++) {
            try {
                test_4(n);
            } catch(Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public static void test_6() {
        Gen<Integer> iOb;

        // Создаём объект Gen<Integer>
        iOb = new Gen<Integer>(77);

        // Показать тип данных, используемый iOb
        iOb.showType();

        // Получить значение iOb
        int value = iOb.getob();
        System.out.println("Значение " + value);        
    }
    
    static class Gen<T> {
        T ob; // объявление объекта типа T

        // Передать конструктору ссылку на объект типа T
        Gen(T o) {
            ob = o;
        }

        // Вернуть ob
        T getob() {
            return ob;
        }

        // Показать тип T
        void showType() {
            System.out.println("Тип T: " + ob.getClass().getName());
        }
    }
    
    public static int fromByteArray(byte[] bytes) {
         return ByteBuffer.wrap(bytes).getInt();
    }
    
    public static void main(String[] args) throws IOException 
    {
        //test_0();
        //test_1();
        //test_2();
        //test_3();
        //test_5();
        //test_6();
        
        /*
        byte[] array = new byte[4];
        array[0] = 1;
        array[1] = 0;
        array[2] = 0;
        array[3] = 0;
        
        System.err.println(fromByteArray(array));
        */
        
        //prepare_zero_packet(0, 0);
        
        /*
        ByteArrayOutputStream packet = new ByteArrayOutputStream();
        for(int n=0; n<10; n++) 
        {
            packet.write(n);
        }
        System.out.println(packet.size());
        packet.reset();
        System.out.println(packet.size());
        */
        
        byte[] array = new byte[5];
        array[0] = 'H';
        array[1] = 'e';
        array[2] = 'l';
        array[3] = 'l';
        array[4] = 'o';
        
        System.out.println(new String(array, "UTF-8"));
    }
}