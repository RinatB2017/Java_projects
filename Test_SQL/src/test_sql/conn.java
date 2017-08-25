/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test_sql;

//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;

import java.sql.*;

/**
 *
 * @author boss
 */
class conn {
    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;

    // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
    static void Conn() throws ClassNotFoundException, SQLException {
        conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch(ClassNotFoundException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        try {
            //conn = DriverManager.getConnection("jdbc:sqlite:test.s3db");
            conn = DriverManager.getConnection("jdbc:sqlite:test.db");
        } catch(SQLException e) {
            System.err.println("Conn: "+ e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        
        System.out.println("База Подключена!");
    }

    // --------Создание таблицы--------
    static void CreateDB() throws SQLException {
        try {
            statmt = conn.createStatement();
            statmt.execute("CREATE TABLE if not exists 'users' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' text, 'phone' INT);");
        } catch(SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        System.out.println("Таблица создана или уже существует.");
    }

    // --------Заполнение таблицы--------
    static void WriteDB() throws SQLException {
        try {
            statmt.execute("INSERT INTO 'users' ('name', 'phone') VALUES ('Petya', 125453); ");
            statmt.execute("INSERT INTO 'users' ('name', 'phone') VALUES ('Vasya', 321789); ");
            statmt.execute("INSERT INTO 'users' ('name', 'phone') VALUES ('Masha', 456123); ");
        } catch(SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }

        System.out.println("Таблица заполнена");
    }

    // -------- Вывод таблицы--------
    static void ReadDB() throws SQLException {
        resSet = statmt.executeQuery("SELECT * FROM users");

        while(resSet.next())
        {
            int id = resSet.getInt("id");
            String  name = resSet.getString("name");
            String  phone = resSet.getString("phone");
            System.out.println( "ID = " + id );
            System.out.println( "name = " + name );
            System.out.println( "phone = " + phone );
            System.out.println();
        }	

        System.out.println("Таблица выведена");
    }

    // --------Закрытие--------
    static void CloseDB() throws SQLException {
        conn.close();
        statmt.close();
        resSet.close();

        System.out.println("Соединения закрыты");
    }
    
}
