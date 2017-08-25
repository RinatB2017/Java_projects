/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_sql;

import java.io.File;
import java.sql.SQLException;

/**
 *
 * @author boss
 */
public class Test_SQL {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        System.out.println("Test SQL");
        System.out.println(new File(".").getAbsolutePath());
        System.out.println(System.getProperty("user.dir"));
        
        System.out.println("Conn");
        conn.Conn();
        
        System.out.println("CreateDB");
	conn.CreateDB();
        
        System.out.println("WriteDB");
	conn.WriteDB();
        
        System.out.println("ReadDB");
	conn.ReadDB();
        
        System.out.println("CloseDB");
	conn.CloseDB();
    }
    
}
