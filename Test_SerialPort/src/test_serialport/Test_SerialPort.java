/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_serialport;

import jssc.SerialPortList;

/**
 *
 * @author boss
 */
public class Test_SerialPort 
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        //Метод getPortNames() возвращает массив строк. Элементы массива уже отсортированы.
        String[] portNames = SerialPortList.getPortNames();
        for (String portName : portNames) 
        {
            System.out.println(portName);
        }
    }
    
}
