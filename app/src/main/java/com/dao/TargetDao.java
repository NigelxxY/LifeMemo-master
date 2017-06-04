package com.dao;

import android.util.Log;

import com.models.target;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Nigel_xxY on 2017/5/29.
 */

public class TargetDao {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public target getTargetList(int userid,String date){
        target data = new target();
        connection = connectToData.getConn();
        if (connection==null)
            return null;
        String sql = "select * from target where userid = ? and targetdate = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,userid);
            preparedStatement.setString(2,date);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                data.setTargetid(resultSet.getInt(1));
                data.setUserid(resultSet.getInt(2));
                data.setTargetnum(resultSet.getInt(3));
                List<String> targetList = new ArrayList<>();
                for(int i=0;i<3;i++)
                    targetList.add(resultSet.getString(4+i));
                data.setTarget(targetList);
                data.setDate(resultSet.getString(7));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            connectToData.closeall(connection,resultSet,preparedStatement);
        }
        return data;
    }
    public int inserttarget(int userid,String target1,String target2,String target3,String date){
        int num = 0;
        connection = connectToData.getConn();
        String sql = "INSERT INTO target (userid,targetnum,target1,target2,target3,targetdate) VALUES (?,3,?,?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,userid);
            preparedStatement.setString(2,target1);
            preparedStatement.setString(3,target2);
            preparedStatement.setString(4,target3);
            preparedStatement.setString(5,date);
            num = preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            connectToData.closeall(connection,resultSet,preparedStatement);
        }
        return num;
    }
    public int changetarget(int userid,String target1,String target2,String target3,String date){
        int num = 0;
        connection = connectToData.getConn();
        String sql = "update target set target1 = ? , target2 = ? , target3 = ? where userid = ? and targetdate = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,target1);
            preparedStatement.setString(2,target2);
            preparedStatement.setString(3,target3);
            preparedStatement.setInt(4,userid);
            preparedStatement.setString(5,date);
            num = preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            connectToData.closeall(connection,null,preparedStatement);
        }
        return num;
    }
}
