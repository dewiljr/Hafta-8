package com.HotelReservation.View;

import com.HotelReservation.Helper.Helper;
import com.HotelReservation.Helper.Item;
import com.HotelReservation.Model.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RoomAdd extends JFrame{
    private JPanel panel1;
    private JButton odaEkleButton;
    private JTextField fld_odaAlan;
    private JTextField fld_yataktip;
    private JRadioButton televizyonRadioButton;
    private JRadioButton minibarRadioButton;
    private JRadioButton oyunKonsoluRadioButton;
    private JRadioButton kasaRadioButton;
    private JRadioButton projeksiyonRadioButton;
    private JComboBox cmb_odatip;
    private JComboBox cmb_oteladi;
    private JTextField fld_stok;
    private JComboBox cmb_pansiyontipi;
    private JComboBox cmb_sezon;
    private JTextField fld_yetiskinfiyat;
    private JTextField fld_cocukfiyat;
    private JPanel wrapper;
    private int added_room_id;


    public RoomAdd() {

        add(wrapper);
        setSize(800,600);
        setTitle("Oda Kayıt Ekranı");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width)/2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height)/2;
        setLocation(x,y);
        setVisible(true);
        loadHotelNameCombo();
        loadHotelTypeCombo();
        loadSeasonCombo();
        cmb_oteladi.addActionListener(event -> {
            loadHotelTypeCombo();
            loadSeasonCombo();
            cmb_odatip.setSelectedIndex(0);
            cmb_sezon.setSelectedIndex(0);
        });
        odaEkleButton.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_cocukfiyat)||Helper.isFieldEmpty(fld_stok)||Helper.isFieldEmpty(fld_yataktip)||Helper.isFieldEmpty(fld_yetiskinfiyat)||Helper.isFieldEmpty(fld_odaAlan)){
                JOptionPane.showMessageDialog(null,"Tüm Alanları Doldurunuz!","Hata", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                String room_type =cmb_odatip.getSelectedItem().toString();
                int stock =Integer.parseInt(fld_stok.getText());
                int season_id = 0;
                int adult_price =Integer.parseInt(fld_yetiskinfiyat.getText().toString());
                int child_price = Integer.parseInt(fld_cocukfiyat.getText().toString());
                Item hotelTypeItem = (Item) cmb_pansiyontipi.getSelectedItem();
                int hotel_type_id =hotelTypeItem.getKey();
                Item hotelItem = (Item) cmb_oteladi.getSelectedItem();
                int hotel_id = hotelItem.getKey();

                for (HotelSeason object : HotelSeason.getListByHotelID(hotel_id)){
                    String season =(object.getSeason_start().toString() + " - " + object.getSeason_end().toString());
                    System.out.println("sezon=" + season);
                    System.out.println("if öncesi cmb_sezon =" + cmb_sezon.getSelectedItem().toString());
                    System.out.println("");
                    String season_room_cmb = cmb_sezon.getSelectedItem().toString();
                    if (season.equals(cmb_sezon.getSelectedItem().toString())){
                        season_id =object.getId();
                        System.out.println("cmb_sezon =" + cmb_sezon.getSelectedItem().toString());
                        System.out.println("sezon_id = "+ season_id);
                        break;
                    }
                }

                if (Room.add(room_type,stock,season_id,adult_price,child_price,hotel_type_id,hotel_id)){
                    ArrayList<Room> roomList =Room.getList();
                    Room addedRooms =roomList.get(Room.getList().size()-1);
                    added_room_id = addedRooms.getId();

                    String roomProperties = "";
                    for (int i =1 ; i<=7 ; i++){
                        switch (i){
                            case 1:
                                if (televizyonRadioButton.isSelected()){
                                    roomProperties += televizyonRadioButton.getText();
                                }
                                break;
                            case 2:
                                if (minibarRadioButton.isSelected()){
                                    roomProperties += "\n" +minibarRadioButton.getText();
                                }
                                break;
                            case 3:
                                if (oyunKonsoluRadioButton.isSelected()){
                                    roomProperties += "\n" +oyunKonsoluRadioButton.getText();
                                }
                                break;
                            case 4:
                                if (kasaRadioButton.isSelected()){
                                    roomProperties += "\n" +kasaRadioButton.getText();
                                }
                                break;
                            case 5:
                                if (projeksiyonRadioButton.isSelected()){
                                    roomProperties += "\n" +projeksiyonRadioButton.getText();
                                }
                                break;
                        }
                    }
                    RoomProperties.add(roomProperties,added_room_id,fld_yataktip.getText(),Integer.parseInt(fld_odaAlan.getText().toString()));
                    Helper.showMsg("Success");

                    cmb_oteladi.setSelectedIndex(0);
                    cmb_odatip.setSelectedIndex(0);
                    fld_stok.setText(null);
                    cmb_pansiyontipi.setSelectedIndex(0);
                    cmb_sezon.setSelectedIndex(0);
                    fld_yetiskinfiyat.setText(null);
                    fld_cocukfiyat.setText(null);
                    fld_yataktip.setText(null);
                    fld_odaAlan.setText(null);
                    televizyonRadioButton.setSelected(false);
                    projeksiyonRadioButton.setSelected(false);
                    kasaRadioButton.setSelected(false);
                    minibarRadioButton.setSelected(false);
                    oyunKonsoluRadioButton.setSelected(false);
                }
            }


        });
    }
    private void loadHotelNameCombo(){
        cmb_oteladi.removeAllItems();
        cmb_oteladi.addItem(new Item(0,null));
        for (Hotel object : Hotel.getList()){
            cmb_oteladi.addItem(new Item(object.getId(),object.getName()));
        }
    }
    private void loadHotelTypeCombo() {
        Item hotelItem = (Item) cmb_oteladi.getSelectedItem();
        cmb_pansiyontipi.removeAllItems();
        cmb_pansiyontipi.addItem(new Item(0, null));
        for (com.HotelReservation.Model.HotelType obj : com.HotelReservation.Model.HotelType.getListByHotelID(hotelItem.getKey())) {

            cmb_pansiyontipi.addItem(new Item(obj.getId(), obj.getType()));
        }
    }
    private void loadSeasonCombo() {
        Item hotelItem = (Item) cmb_oteladi.getSelectedItem();
        cmb_sezon.removeAllItems();
        cmb_sezon.removeAllItems();
        for (HotelSeason obj : HotelSeason.getListByHotelID(hotelItem.getKey())){
            cmb_sezon.addItem(new Item(obj.getId(), (obj.getSeason_start() + " - " + obj.getSeason_end())));
        }
    }


    public static void main(String[] args){
        RoomAdd giris = new RoomAdd();
    }
}
