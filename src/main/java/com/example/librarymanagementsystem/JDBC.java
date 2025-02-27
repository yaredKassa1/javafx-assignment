package com.example.librarymanagementsystem;

import java.io.*;
import java.sql.*;

public class JDBC {

    public static Connection getConnection() {
        Connection con =null;
        String url="jdbc:mysql://localhost:3306/LibraryMS";
        String username="root";
        String password="";

        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection(url,username,password);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        return con;
    }



    public static String adminID(String userId){
        Connection conn = getConnection();
        String id = null;
        try {
            Statement  statment = conn.createStatement();
            String sql = "select * from admins where adminName  = '" + userId + "'";
            ResultSet rest = statment.executeQuery(sql);
            while (rest.next()){
                id = rest.getString(1);
            }
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return id;
    }

    public static String selectStudID(String studentID){
        Connection conn = getConnection();
        String id = null;
        try {
            Statement  statment = conn.createStatement();
            String sql = "select * from students where studentName  = '" + studentID + "'";
            ResultSet rest = statment.executeQuery(sql);
            while (rest.next()){
                id=rest.getString(2);
            }
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return id;
    }

    public static String  adminPassword(String userId,String Password) {
        Connection conn = getConnection();
        String  id = null;
        try {
            Statement statment = conn.createStatement();
            String sql = "select * from admins where password = '" + Password + "' and adminName  = '" + userId + "'"; // �����ѯSQL���
            ResultSet rest = statment.executeQuery(sql);
            while (rest.next()){
                id = rest.getString(2);
            }
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return id;
    }

    public static String studPassword(String adminId,String Password1) {
        Connection conn = getConnection();
        String id = null;
        try {
            Statement statment = conn.createStatement();
            String sql = "select * from students where studentPassword = '" + Password1 + "' and studentName  = '" + adminId + "'"; // �����ѯSQL���
            ResultSet rest = statment.executeQuery(sql);
            while (rest.next()){
                id = rest.getString(3);
            }
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return id;
    }

    public static int addBooks(String bookID,String Title,String Author,String Edition,String Publisher,String PubliicationDate, String file) {
        Connection conn = getConnection();

        try {
            PreparedStatement statement = conn
                    .prepareStatement("insert into books values(?,?,?,?,?,?,?)");

            FileInputStream inputStream=new FileInputStream(file);
            statement.setInt(1,Integer.parseInt(bookID));
            statement.setString(2,Title);
            statement.setString(3,Author);
            statement.setInt(4,Integer.parseInt(Edition));
            statement.setString(5,Publisher);
            statement.setString(6,PubliicationDate);
            statement.setBinaryStream(7,inputStream,inputStream.available());
            statement.executeUpdate();
        }catch (SQLException | FileNotFoundException e) {
            e.fillInStackTrace();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return 1;

    }
    public static int addStudents(String studID,String studName,String password,String Department,String YOA) {
        Connection conn = getConnection();
        try{

            PreparedStatement statement = conn
                    .prepareStatement("insert into students values('" +studID + "','" + studName + "','" + password + "','" + Department +  "','" + YOA + "')");

            statement.executeUpdate();
        }catch(Exception e){
            e.fillInStackTrace();

        }
        return 1;

    }

    public static String select(String BookId){
        Connection conn = getConnection();
        int ID=0;
        try{
            Statement statment = conn.createStatement();
            String sql = "select BookID from books where BookID  = '" + BookId + "'";
            ResultSet rest = statment.executeQuery(sql);
            while(rest.next()) {
                ID = rest.getInt(1);
            }
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return String.valueOf(ID);
    }

    public static int UpdateBooks(String bookID,String Title,String Author,String Edition,String Publisher,String PubliicationDate, String file) {
        Connection conn = getConnection();
        if(bookID.equals(select(bookID))){
            try {
                PreparedStatement statement1 = conn
                        .prepareStatement("update books set Title = '" + Title + "' where BookID = '" + bookID + "'");
                PreparedStatement statement2 = conn
                        .prepareStatement("update books set Author = '" + Author + "' where BookID = '" + bookID + "'");
                PreparedStatement statement3 = conn
                        .prepareStatement("update books set Edition = '" + Edition + "' where BookID = '" + bookID + "'");
                PreparedStatement statement4 = conn
                        .prepareStatement("update books set Publisher = '" + Publisher + "' where BookID = '" + bookID + "'");
                PreparedStatement statement5 = conn
                        .prepareStatement("update books set PublicationDate = '" + PubliicationDate + "' where BookID = '" + bookID + "'");
                PreparedStatement statement6 = conn
                        .prepareStatement("update books set File = '" + file + "' where BookID = '" + bookID + "'");
                statement1.executeUpdate();
                statement2.executeUpdate();
                statement3.executeUpdate();
                statement4.executeUpdate();
                statement5.executeUpdate();
                statement6.executeUpdate();
                System.out.println("UPDATED");
            }catch (SQLException e) {
                e.fillInStackTrace();
            }
            return 1;
        }
        else
            return 0;

    }

    public static int DeleteBooks(String DeleteID) {
        Connection conn = getConnection();
        if(DeleteID.equals(select(DeleteID))){
            try {
                PreparedStatement statement1 = conn
                        .prepareStatement("delete from books where BookID = '" + DeleteID + "'");
                statement1.executeUpdate();
            }catch (SQLException e) {
                e.fillInStackTrace();
            }
            return 1;
        }
        else
            return 0;

    }
    public static int Search(String SearchBookID) {
        Connection conn = getConnection();
        if(SearchBookID.equals(select(SearchBookID))){
            try {
                PreparedStatement statement1 = conn
                        .prepareStatement("select BookID from books where BookID = '" + SearchBookID + "'");
                statement1.executeUpdate();
            }catch (SQLException e) {
                e.fillInStackTrace();
            }
            return 1;
        }
        else
            return 0;

    }
}
