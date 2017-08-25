/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test4;

import java.util.logging.Level;
import java.util.logging.Logger;

class Class_1 {
    public static void x1()
    {
        System.out.println("Class_1::x1()");
    }
}

class Class_2 extends Class_1 {
    public static void x2()
    {
        System.out.println("Class_2::x2()");
    }
}

class Class_3 extends Class_2 {
    public static void x3()
    {
        System.out.println("Class_3::x3()");
    }
}

/**
 *
 * @author boss
 */
public class Test4 {
    private static Class_3 xxx;
    static final Logger logger = Logger.getLogger(Test4.class.getName());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        xxx.x1();
        xxx.x2();
        xxx.x3();
        ClassicSingleton.test();
        //logger.log(Level.WARNING, "main");
        logger.info("test");
        logger.warning("warning");
        logger.fine("fine");
    }
}
