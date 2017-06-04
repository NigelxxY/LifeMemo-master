package com.dao;

import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nigel_xxY on 2017/6/3.
 */

public class KeywordsDao {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    public List<String> getKeywords(int userid, String eventClass){
        List<String> data = new ArrayList<>();
        String sql = "select str from keywords where userid = ? and keywordclass = ?";
        connection = connectToData.getConn();
        if (connection==null)
            return null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,userid);
            preparedStatement.setString(2,eventClass);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String str = resultSet.getString(1);
                Log.e("str",str);
                data.add(str);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            connectToData.closeall(connection,resultSet,preparedStatement);
        }
        return data;
    }
    public int deleteKeyword(int userid,String string){
        int num = 0;
        connection = connectToData.getConn();
        if (connection==null)
            return 0;
        String sql = "delete from keywords where userid = ? and str = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,userid);
            preparedStatement.setString(2,string);
            num  = preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            connectToData.closeall(connection,null,preparedStatement);
        }
        return num;
    }
    public int InsertKeyword(int userid,String str,String classstr){
        int num = 0;
        connection = connectToData.getConn();
        if (connection==null)
            return 0;
        String sql = "insert into keywords (str,keywordclass,userid) values (?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,str);
            preparedStatement.setString(2,classstr);
            preparedStatement.setInt(3,userid);
            num  = preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            connectToData.closeall(connection,null,preparedStatement);
        }
        return num;
    }
    public int UpdateKeyword(int userid,String oldstr,String newstr){
        int num = 0;
        connection = connectToData.getConn();
        if (connection==null)
            return 0;
        String sql = "update keywords set str = ? where userid = ? and str = ?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,newstr);
            preparedStatement.setString(3,oldstr);
            preparedStatement.setInt(2,userid);
            num  = preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            connectToData.closeall(connection,null,preparedStatement);
        }
        return num;
    }
}
