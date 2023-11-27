package com.HotelReservation.Model;

import com.HotelReservation.Helper.DBConnector;
import com.HotelReservation.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class User {
    private int id;
    private String name;
    private String uname;
    private String psw;
    private String type;

    public User(){}
    public User(int id, String name, String uname, String psw, String type) {
        this.id = id;
        this.name = name;
        this.uname = uname;
        this.psw = psw;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static ArrayList<User> getList(){
        ArrayList<User> userList = new ArrayList<>();
        String query = "SELECT * FROM user";
        User obj;
        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                obj = new User();
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUname(rs.getString("uname"));
                obj.setPsw(rs.getString("psw"));
                obj.setType(rs.getString("type"));
                userList.add(obj);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }
    public static boolean add(String name, String uname, String psw, String type ){
        String query = "INSERT INTO user (name,uname,psw,type) VALUES (?,?,?,?)";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,name);
            pr.setString(2,uname);
            pr.setString(3,psw);
            pr.setString(4,type);
                int response = pr.executeUpdate();
                if (response == -1){
                    Helper.showMsg("Error");
                }
                return response != -1;
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }return true;
    }

    public static User getFetch(String uname, String psw){
        User obj = null;
        String query = "SELECT * FROM user WHERE uname = ? AND psw = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,uname);
            pr.setString(2,psw);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                switch (rs.getString("type")){
                    case "operator":
                        obj = new operator();
                        break;
                    case "Worker":
                        obj = new Worker();
                        break;
                    default:
                        obj = new User();
                }
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUname(rs.getString("uname"));
                obj.setPsw(rs.getString("psw"));
                obj.setType(rs.getString("type"));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return obj;
    }
}
