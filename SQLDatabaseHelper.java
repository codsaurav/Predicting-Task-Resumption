/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskresumption;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JButton;

/**
 *
 * @author satyam
 */
public class SQLDatabaseHelper {
    
    Connection connection;
    String url = "jdbc:mysql://localhost:3306/TASK_RESUMPTION?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    String username = "root";
    String password = "satyam";
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat ("yyyy:MM:dd hh:mm:ss");
    
    
    private static SQLDatabaseHelper mDatabaseHelper;
    
    private SQLDatabaseHelper () {
    }
    
    public static SQLDatabaseHelper getInstance () {
        if (mDatabaseHelper == null) {
            mDatabaseHelper = new SQLDatabaseHelper();
        }
        return mDatabaseHelper;
    }
    
    public void initialize () {
        try {
            connection = DriverManager.getConnection(url,username,password);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void putData (String program, String title, Date start, Date end, String file, String executable, boolean b) {
        String query = "INSERT INTO ACTION (program,title,start,end,file,executable,selected) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement (query);
            stmt.setString (1,program);
            stmt.setString (2,title);
            stmt.setString (3,simpleDateFormat.format (start));
            stmt.setString (4,simpleDateFormat.format (end));
            stmt.setString (5,file);
            stmt.setString (6,executable);
            stmt.setBoolean (7,!b);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
    }
    
    public void closeConnection () {
        try {
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public ArrayList<PastRetrieval> getDataForGui (String startDate, String endDate) {
        String query = "SELECT A.program,A.title,A.start,A.end,A.executable,A.file FROM (SELECT program,title,start,end,executable,file,selected,STRCMP(start,?) AS startcmp,STRCMP(start,?) AS endcmp FROM Action) AS A LEFT JOIN Ignored B ON A.program=B.program AND A.title=B.title WHERE B.program IS NULL AND B.title IS NULL AND A.startcmp=1 AND A.endcmp=-1 AND A.selected=false;";
        ArrayList<PastRetrieval> arrayList = null;
        try {
            PreparedStatement stmt = connection.prepareStatement (query);
            stmt.setString (1,startDate);
            stmt.setString(2,endDate);
            ResultSet resultSet = stmt.executeQuery();
            arrayList = new ArrayList<>();
            
            while (resultSet.next()) {
                arrayList.add (new PastRetrieval (resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getString(6)));
            }
            
            resultSet.close();
            stmt.close();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return arrayList;
    }
    
    public void updateSQL (String program, String title, String start, String end) {
        System.out.println (program + " " + title + " " + start + " " + end);
        String query = "UPDATE Action set selected=true where program=? AND title=? AND start=? AND end=?";
        try {
            PreparedStatement stmt = connection.prepareStatement (query);
            stmt.setString (1,program);
            stmt.setString (2,title);
            stmt.setString (3,start);
            stmt.setString (4,end);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
    }
    
    
    
    public ArrayList<ToDoRow> getDataForToDo () {
        String query = "SELECT program,title,executable,file FROM Action where selected=true;";
        ArrayList<ToDoRow> arrayList = null;
        try {
            PreparedStatement stmt = connection.prepareStatement (query);
            ResultSet resultSet = stmt.executeQuery();
            arrayList = new ArrayList<>();
            
            while (resultSet.next()) {
                arrayList.add (new ToDoRow (resultSet.getString(1),resultSet.getString(2),new JButton(), new JButton(), resultSet.getString(3),resultSet.getString(4)));
            }
            
            resultSet.close();
            stmt.close();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return arrayList;
    }
}
