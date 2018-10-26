package test;

class Bot {
    public final static int LV_FREE = 0; 
    public final static int LV_ORGANIC_HOLD = 1;
    public final static int LV_ORGANIC_SINK = 2;
    public final static int LV_ALIVE = 3;
}

public class Test
{
    static void print(String text) {
        System.out.println(text);
    }
    
    static void test(int value) {
        switch(value) {
            case Bot.LV_FREE:
                break;
                
            case Bot.LV_ORGANIC_HOLD:
                break;
                
            default:
                break;
        }
    }
    
    public static void main(String[] args) {
        //print("Test");
        print("value " + String.valueOf(Bot.LV_ALIVE));
        
        test(0);        
    }
}