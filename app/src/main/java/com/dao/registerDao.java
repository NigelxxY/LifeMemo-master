package com.dao;

import android.util.Log;
import java.sql.Connection;
import java.sql.PreparedStatement;


/**
 * Created by Nigel_xxY on 2017/5/29.
 */

public class registerDao {
    private Connection connection;
    private PreparedStatement preparedStatement;
    public boolean registerInsert(String username,String password){
        connection = connectToData.getConn();
        if (connection==null){
            Log.e("res con null","!!");
            return false;
        }
        String sql = "INSERT INTO user (username,password) VALUES (?,?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            int isOk = preparedStatement.executeUpdate();
            if(isOk>0){
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            connectToData.closeall(connection,null,preparedStatement);
        }
        return false;
    }
}
