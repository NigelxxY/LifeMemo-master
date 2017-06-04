package com.dao;

import android.util.Log;

import com.models.event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nigel_xxY on 2017/5/29.
 */

public class EventDao {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public List<event> getEventList(int userid,String date){
        List<event> data = new ArrayList<>();
        connection=connectToData.getConn();
        if (connection==null){
            return null;
        }
        String sql = "select * from event where userid = ? and eventdate = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,userid);
            preparedStatement.setString(2,date);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                event tmp = new event();
                tmp.setEventid(resultSet.getInt(1));
                tmp.setUserid(resultSet.getInt(2));
                tmp.setEventclass(resultSet.getString(3));
                tmp.setEventkeyword(resultSet.getString(4));
                tmp.setSttime(resultSet.getString(5));
                tmp.setEndtime(resultSet.getString(6));
                tmp.setEventdate(resultSet.getString(7));
                tmp.setEventhing(resultSet.getString(8));
                data.add(tmp);
                Log.e("tttt",tmp.getEventkeyword());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            connectToData.closeall(connection,resultSet,preparedStatement);
        }
        return data;
    }
    public int insertEvent(event data){
        int num = 0;
        connection = connectToData.getConn();
        if (connection==null)
            return 0;
        String sql = "INSERT INTO event(userid,eventclass,eventkeyword,sttime,endtime,eventdate,eventhing) VALUES (?,?,?,?,?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,data.getUserid());
            preparedStatement.setString(2,data.getEventclass());
            preparedStatement.setString(3,data.getEventkeyword());
            preparedStatement.setString(4,data.getSttime());
            preparedStatement.setString(5,data.getEndtime());
            preparedStatement.setString(6,data.getEventdate());
            preparedStatement.setString(7,data.getEventhing());
            num = preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            connectToData.closeall(connection,resultSet,preparedStatement);
        }
        return num;
    }
}
