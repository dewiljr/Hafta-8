package com.HotelReservation.View;

import com.HotelReservation.Helper.Helper;
import com.HotelReservation.Model.Hotel;
import com.HotelReservation.Model.HotelSeason;
import com.HotelReservation.Model.Worker;
import com.HotelReservation.Model.HotelType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;


public class HotelAdd extends JFrame{
    private JPanel panel1;
    private JPanel wrapper;
    private JPanel twrapper;
    private JPanel lwrapper;
    private JPanel rwrapper;
    private JLabel HotelAdd;
    private JTextField fld_hotelname;
    private JComboBox cmb_yildiz;
    private JTextField fld_telefon;
    private JTextField fld_email;
    private JRadioButton ultraHerşeyDahilRadioButton;
    private JRadioButton herşeyDahilRadioButton;
    private JRadioButton odaKahvaltılıRadioButton;
    private JRadioButton tamPansiyonRadioButton;
    private JRadioButton yarımPansiyonRadioButton;
    private JRadioButton sadeceYatakRadioButton;
    private JRadioButton alkolHariçFullRadioButton;
    private JLabel jbl_Konaklamatipi;
    private JLabel fbl_konaklamadonem;
    private JTextField fld_donem1Start;
    private JTextField fld_donem2Start;
    private JTextField fld_donem1End;
    private JTextField fld_donem2End;
    private JTextArea area_prop;
    private JTextArea area_address;
    private JButton btn_save;
    private int added_hotel_id;



    public HotelAdd(){

        add(wrapper);
        setSize(1000,600);
        setTitle("Otel Kayıt Ekranı");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width)/2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height)/2;
        setLocation(x,y);
        setVisible(true);
        ultraHerşeyDahilRadioButton.setText(Helper.hotelType("1"));
        herşeyDahilRadioButton.setText(Helper.hotelType("2"));
        odaKahvaltılıRadioButton.setText(Helper.hotelType("3"));
        tamPansiyonRadioButton.setText(Helper.hotelType("4"));
        yarımPansiyonRadioButton.setText(Helper.hotelType("5"));
        sadeceYatakRadioButton.setText(Helper.hotelType("6"));
        alkolHariçFullRadioButton.setText(Helper.hotelType("7"));


        btn_save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Helper.isFieldEmpty(fld_hotelname)||Helper.isAreaEmpty(area_prop)||Helper.isAreaEmpty(area_address)||Helper.isFieldEmpty(fld_email)||
                Helper.isFieldEmpty(fld_telefon)||(!alkolHariçFullRadioButton.isSelected()&&!herşeyDahilRadioButton.isSelected()&&!ultraHerşeyDahilRadioButton.isSelected()
                &&!odaKahvaltılıRadioButton.isSelected()&&!sadeceYatakRadioButton.isSelected()&&!tamPansiyonRadioButton.isSelected()&&!yarımPansiyonRadioButton.isSelected())||
                Helper.isFieldEmpty(fld_donem1Start)&&Helper.isFieldEmpty(fld_donem1End)){
                    Helper.showMsg("fill");
                }
                else {
                    String name = fld_hotelname.getText();
                    String star = (String) cmb_yildiz.getSelectedItem();
                    String property = area_prop.getText();
                    String address = area_address.getText();
                    String email = fld_email.getText();
                    String phone = fld_telefon.getText();
                    String season1_start = fld_donem1Start.getText();
                    String season1_end = fld_donem1End.getText();
                    String season2_start = fld_donem2Start.getText();
                    String season2_end = fld_donem2End.getText();

                    if (Hotel.add(name, star, property, address, phone, email)) {
                        Hotel addedHotels =Hotel.FetchByPhoneNumber(phone);
                        added_hotel_id =addedHotels.getId();

                        if (ultraHerşeyDahilRadioButton.isSelected()) {
                            HotelType.add(ultraHerşeyDahilRadioButton.getText(), added_hotel_id);
                        }
                        if (herşeyDahilRadioButton.isSelected()) {
                            HotelType.add(herşeyDahilRadioButton.getText(), added_hotel_id);
                        }
                        if (odaKahvaltılıRadioButton.isSelected()) {
                            HotelType.add(odaKahvaltılıRadioButton.getText(), added_hotel_id);
                        }
                        if (tamPansiyonRadioButton.isSelected()) {
                            HotelType.add(tamPansiyonRadioButton.getText(), added_hotel_id);
                        }
                        if (yarımPansiyonRadioButton.isSelected()) {
                            HotelType.add(yarımPansiyonRadioButton.getText(), added_hotel_id);
                        }
                        if (sadeceYatakRadioButton.isSelected()) {
                            HotelType.add(sadeceYatakRadioButton.getText(), added_hotel_id);
                        }
                        if (alkolHariçFullRadioButton.isSelected()) {
                            HotelType.add(alkolHariçFullRadioButton.getText(), added_hotel_id);
                        }
                        HotelSeason.add(season1_start,season1_end,added_hotel_id);
                        if (!Helper.isFieldEmpty(fld_donem2Start)&&!Helper.isFieldEmpty(fld_donem2End)){
                            HotelSeason.add(season2_start,season2_end,added_hotel_id);
                        }
                    }

                    Helper.showMsg("Success");
                    fld_hotelname.setText(null);
                    cmb_yildiz.setSelectedIndex(0);
                    area_prop.setText(null);
                    area_address.setText(null);
                    fld_telefon.setText(null);
                    fld_email.setText(null);
                    fld_donem1Start.setText(null);
                    fld_donem1End.setText(null);
                    fld_donem2Start.setText(null);
                    fld_donem2End.setText(null);
                    alkolHariçFullRadioButton.setSelected(false);
                    sadeceYatakRadioButton.setSelected(false);
                    yarımPansiyonRadioButton.setSelected(false);
                    tamPansiyonRadioButton.setSelected(false);
                    odaKahvaltılıRadioButton.setSelected(false);
                    herşeyDahilRadioButton.setSelected(false);
                    ultraHerşeyDahilRadioButton.setSelected(false);
                }
            }
        });
    }

    public static void main(String[] args){
        HotelAdd giris = new HotelAdd();
    }
}
