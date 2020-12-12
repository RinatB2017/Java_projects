package com.home.calculator;


import java.util.*;

public class Calculator {
        String[] subStr;
    
    public boolean check_text(String text) {
        String delimeter = " "; // Разделитель
        subStr = text.split(delimeter);
        
        int cnt = subStr.length;
        return (cnt == 3);
    }
    
    public static void main(String args[]) {
        Scanner sc= new Scanner(System.in);
        System.out.print("Enter a string: ");  
        String str = sc.nextLine();
        System.out.println("You have entered: " + str);
        
        if(check_text(str) == false)
        {
            System.err.println("Need 3 substr");
            return;
        } 
        
        System.out.println("Len: " + cnt);
    }
}

/*
        try (Scanner in = new Scanner(System.in)) {
            System.out.print("Input a number: ");
            int num = in.nextInt();
            
            System.out.printf("Your number: %d \n", num);
        } catch(java.util.InputMismatchException e) {
            System.err.println("ERROR");
        }

*/