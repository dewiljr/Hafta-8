package com.HotelReservation.View;

import com.HotelReservation.Helper.DBConnector;
import com.HotelReservation.Helper.Helper;
import com.HotelReservation.Helper.Item;
import com.HotelReservation.Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReservationGUI extends JFrame{
    private JPanel panel1;
    private JPanel wrapper;
    private JTextField fld_clientname;
    private JTextField fld_clientphone;
    private JTextField fld_clientemail;
    private JTextArea area_clientnote;
    private JTextField fld_check_in_date;
    private JTextField fld_check_out_date;
    private JTextField fld_adult_numb;
    private JTextField fld_child_numb;
    private JTextField fld_clienttc;
    private JButton btn_rezervasyonekle;
    private JComboBox cmb_oteladi;
    private JComboBox cmb_pansiyontipi;
    private JTextField fld_total_price;
    private JTextField fld_hotel_name;
    private JTextField fld_room_type;
    private final Room room  ;
    private int adult_numb = 0;
    private int child_numb = 0;
    private String check_in;
    private String check_out;
    private int total_price;
    public ReservationGUI(Room room, int adult_numb, int child_numb, String check_in, String check_out){
        this.room = room;
        this.adult_numb = adult_numb;
        this.child_numb = child_numb;
        this.check_in = check_in;
        this.check_out = check_out;
        add(wrapper);
        setSize(1000,600);
        setTitle("Oda Kayıt Ekranı");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width)/2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height)/2;
        setLocation(x,y);
        setVisible(true);
        Hotel hotel = Hotel.getFetch(room.getHotel_id());
        RoomProperties roomProperties = RoomProperties.getFetch(room.getId());
        fld_hotel_name.setText(hotel.getName());
        fld_room_type.setText(room.getRoom_type());
        fld_adult_numb.setText(Integer.toString(adult_numb));
        fld_child_numb.setText(Integer.toString(child_numb));
        fld_check_in_date.setText(check_in);
        fld_check_out_date.setText(check_out);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date check_in_date = null;
        Date check_out_date = null;
        try {
            check_in_date = formatter.parse(check_in);
            check_out_date = formatter.parse(check_out);
        } catch (ParseException ex) {

        }
        long diff = check_out_date.getTime() - check_in_date.getTime();
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        total_price = (int) (days * ((room.getAdult_price() * adult_numb) + (room.getChild_price() * child_numb)));
        fld_total_price.setText(String.valueOf(total_price) + " TL");

        btn_rezervasyonekle.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_clientname) || Helper.isFieldEmpty(fld_clientphone) || Helper.isFieldEmpty(fld_clientemail) || Helper.isAreaEmpty(area_clientnote)){
                Helper.showMsg("fill");
            }
            else{
                String client_name = fld_clientname.getText();
                String client_phone = fld_clientphone.getText();
                String client_email = fld_clientemail.getText();
                String client_note = area_clientnote.getText();
                int room_id = room.getId();
                if (Reservation.add(client_name, client_phone, client_email, client_note, room_id, check_in, check_out, adult_numb, child_numb, total_price)){
                    Helper.showMsg("Success");
                    int newStock = room.getStock() - 1;
                    updateRoomStock(newStock, room_id);
                }
            }
        });

    }
    public static boolean updateRoomStock (int stock, int id){
        String query = "UPDATE room SET stock = ? WHERE id = ?";
        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,stock);
            pr.setInt(2,id);
            int response = pr.executeUpdate();
            if (response == -1){
                Helper.showMsg("Error");
            }
            return response != -1;
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }return true;
    }

    private void loadHotelTypeCombo(){
        Item hotelItem = (Item) cmb_oteladi.getSelectedItem();
        cmb_pansiyontipi.removeAllItems();

        for (HotelType object:HotelType.getList()){
            cmb_pansiyontipi.addItem(new Item(object.getId(),object.getType()));
        }
    }
    private void loadHotelNameCombo(){
        cmb_oteladi.removeAllItems();
        cmb_oteladi.addItem(new Item(0,null));
        for (Hotel object : Hotel.getList()){
            cmb_oteladi.addItem(new Item(object.getId(),object.getName()));
        }
    }

}
