package com.dao;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Nigel_xxY on 2017/5/29.
 */

public class connectToData {
    final static private String driver = "com.mysql.jdbc.Driver";
    final static private String url = "jdbc:mysql://115.28.74.142:3306/lifesql";
    final static private String username = "root";
    final static private String password = "12345678";
    public static Connection getConn(){
        Connection connection = null;
        try{
            Class.forName(driver);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(url,username,password);
        }catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }
    public static void closeall(Connection connection, ResultSet resultSet, PreparedStatement preparedStatement){
        if (connection!=null){
            try {
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
            connection = null;
        }
        if (preparedStatement!=null){
            try {
                preparedStatement.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
            preparedStatement = null;
        }
        if (resultSet!=null){
            try {
                resultSet.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
            resultSet = null;
        }
    }
}
