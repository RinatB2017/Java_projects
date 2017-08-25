
package test_proto;

public class Test_Proto {

    public static void main(String[] args) {
        Proto proto = new Proto();
        proto.set_address((short)0x55);
        proto.set_command((byte)2);
        char data[] = new char[3];
        data[0] = 4;
        data[1] = 5;
        data[2] = 6;
        proto.set_data(data);
        String temp = proto.get_data();
        System.out.println(temp);
    }
    
}
