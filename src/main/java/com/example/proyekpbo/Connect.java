package com.example.proyekpbo;

import java.sql.*;
public class Connect {
    private static Connection conn;
    private static Connection Connect(){
        String driver = "com.mysql.cj.jdbc.Driver";
        String ur = "jdbc:mysql://localhost:3306/proyekpbo?&serverTimezone=UTC"; // menganti koneksi di sini yang lain tidak perlu
        String user = "root";
        String password = "";
        try {
            Class.forName(driver);
            Connection con = DriverManager.getConnection(ur, user, password);

            return con;
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
    public static Connection GetConnection(){
        if(conn != null){
            return conn;
        } else{
            conn = Connect();
            return  conn;
        }
    }
}

