package com.dao;

import android.util.Log;

import com.models.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Nigel_xxY on 2017/5/29.
 */

public class loginDao {
    private Connection connection ;
    private PreparedStatement preparedStatement ;
    private ResultSet resultSet ;
    public int loginin(String username, String password){
        int userid = 0;
        connection = connectToData.getConn();
        if (connection==null)
            Log.e("tttt","tttt");
        String sql = "SELECT * FROM user";
        try {
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String usr = resultSet.getString(2);
                if (usr.equals(username)){
                    String psd = resultSet.getString(3);
                    if (psd.equals(password)){
                        userid = resultSet.getInt(1);
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            connectToData.closeall(connection,resultSet,preparedStatement);
        }
        return userid;
    }
}
