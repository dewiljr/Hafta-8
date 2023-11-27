package com.HotelReservation.Model;

import com.HotelReservation.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class HotelType {
    private int id;
    private String type;
    private int hotel_id;
    private Hotel hotel;

    public HotelType(){
    }

    public HotelType(int id, String type, int hotelId) {
        this.id=id;
        this.type=type;
        this.hotel_id = hotel_id;
        this.hotel =Hotel.getFetch(hotel_id);

    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public Hotel getHotel() {
        return hotel;
    }
    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public static boolean add(String type, int hotel_id){
        String query = "INSERT INTO type_hotel (type, hotel_id) VALUES (?,?)";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,type);
            pr.setInt(2, hotel_id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    public static HotelType getFetch(int id) {
        HotelType obj = null;
        String query = "SELECT * FROM type_hotel WHERE id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                obj = new HotelType(rs.getInt("id"), rs.getString("type"), rs.getInt("hotel_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }
    public static ArrayList<HotelType> getList(){
        ArrayList<HotelType> hotelTypeList = new ArrayList<>();
        String query = "SELECT * FROM type_hotel";
        HotelType obj;

        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                obj = new HotelType();
                obj.setId(rs.getInt("id"));
                obj.setType(rs.getString("type"));
                obj.setHotel_id(rs.getInt("hotel_id"));

                hotelTypeList.add(obj);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return hotelTypeList;
    }
    public static boolean delete(int hotel_id){
        String query = "DELETE FROM type_hotel WHERE hotel_id = ?";
        try {
            PreparedStatement pr =DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,hotel_id);

            return pr.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }
    public static ArrayList<HotelType> getListByHotelID(int id) {
        ArrayList<HotelType> hotelTypeList = new ArrayList<>();
        HotelType obj;
        String query = "SELECT * FROM type_hotel WHERE hotel_id =?";

        try {
            PreparedStatement pr =DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs = pr.executeQuery();
            while (rs.next()){
                obj = new HotelType();
                obj.setId(rs.getInt("id"));
                obj.setType(rs.getString("type"));
                obj.setHotel_id(rs.getInt("hotel_id"));
                hotelTypeList.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotelTypeList;
    }
}
