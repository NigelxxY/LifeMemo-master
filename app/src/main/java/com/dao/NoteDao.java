package com.dao;

import com.models.note;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nigel_xxY on 2017/5/29.
 */

public class NoteDao {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public List<note> getNote(int userid, String date){
        List<note> data = new ArrayList<>();
        connection  = connectToData.getConn();
        if (connection ==null)
            return null;
        String sql = "select * from note where userid = ? and notedate = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,userid);
            preparedStatement.setString(2,date);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                note tmp = new note();
                tmp.setNoteid(resultSet.getInt(1));
                tmp.setUserid(resultSet.getInt(2));
                tmp.setNotes(resultSet.getString(3));
                tmp.setDate(resultSet.getString(4));
                data.add(tmp);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            connectToData.closeall(connection,resultSet,preparedStatement);
        }
        return data;
    }
    public int insertNote(note data){
        int num = 0;
        connection = connectToData.getConn();
        if (connection == null){
            return 0;
        }
        String sql = "INSERT INTO note (userid,notes,notedate) VALUES (?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,data.getUserid());
            preparedStatement.setString(2,data.getNotes());
            preparedStatement.setString(3,data.getDate());
            num = preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            connectToData.closeall(connection,null,preparedStatement);
        }
        return num;
    }
}
