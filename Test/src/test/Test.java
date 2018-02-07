package test;

import java.util.regex.Pattern;

class C
{
    C()
    {
        System.out.println("class C");
    }
    void ccc() {
        System.out.println("ccc");
    }
}

class B extends C
{
    B() 
    {
        System.out.println("class B");
    }
    void bbb() {
        System.out.println("bbb");
    }
}

class A extends B
{
    A()
    {
        System.out.println("class A");
    }    
    void aaa() {
        System.out.println("aaa");
    }
}

public class Test extends A
{
    Test()
    {
        System.out.println("class Test");
    }    

    public static void main(String[] args) {
        /*
        System.out.println("Test");
        A a = new A();
        a.aaa();
        a.bbb();
        a.ccc();
        
        System.out.println(Integer.MIN_VALUE);
        
        int x = 5;
        int y = 5;
        boolean temp = x != y;
        System.out.println(temp);
        */
        String str = "|-2|666|f4|e";
        //String[] ls = str.split(Pattern.quote("|"));
        String[] ls = str.split("\\|");
        int cnt = ls.length;
        if(cnt == 5) 
        {
            String str_temperature = ls[0];
            if(str_temperature.isEmpty())   str_temperature = "0";
            Float temperature = Float.parseFloat(str_temperature);
            
            System.out.println(Float.toString(temperature));
            System.out.println(ls[0]);
            System.out.println(ls[1]);
            System.out.println(ls[2]);
            System.out.println(ls[3]);
            System.out.println(ls[4]);
        }
    }
}
