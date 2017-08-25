package test;

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
    }
}
