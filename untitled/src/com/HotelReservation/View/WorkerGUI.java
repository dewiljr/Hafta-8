package com.HotelReservation.View;

import com.HotelReservation.Helper.Config;
import com.HotelReservation.Helper.Helper;
import com.HotelReservation.Model.*;
import com.HotelReservation.Model.HotelSeason;
import com.HotelReservation.Model.HotelType;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WorkerGUI extends JFrame {

    private JPanel panel1;
    private JPanel wrapper;
    private JTabbedPane tb_all;
    private JTable tbl_roomlist;
    private JTable tbl_hotellist;
    private JTable tbl_reservationList;
    private JButton btn_rezervasyonyap;
    private JButton btn_otelEkle;
    private JTable tbl_hoteltype;
    private JTable tbl_hotelseason;
    private JButton btn_res_delete;
    private JButton odaEkleButton;
    private JTextField fld_src_hotelname;
    private JTextField fld_check_in;
    private JTextField fld_check_out;
    private JButton odaAraButton;
    private JTable tbl_room_property;
    private JTextField fld_room_id;
    private JTextField fld_adult_numb;
    private JTextField fld_child_numb;
    private JTextField fld_res_id;
    private JTable mdl_room_properties;
    DefaultTableModel mdl_hotellist;
    private Object[] row_hotellist;
    DefaultTableModel mdl_hoteltype;
    private Object[] row_hoteltype;
    private int select_hotelid;
    DefaultTableModel mdl_hotelseason;
    private Object[] row_hotelseason;
    DefaultTableModel mdl_roomlist;
    private Object[] row_roomlist;
    DefaultTableModel mdl_roomproperties;
    private Object[] row_roomproperties;
    private int select_roomid;
    private int adult_num=0;
    private int child_num=0;
    private String check_in;
    private String check_out;
    private int reservation_roomid;
    DefaultTableModel mdl_reservationlist;
    private Object[] row_reservationlist;


    public WorkerGUI(Worker u){
        add(wrapper);
        setSize(1000,500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        int x = Helper.screenCenter("x", getSize());
        int y = Helper.screenCenter("y", getSize());
        setLocation(x,y);
        setVisible(true);
        mdl_hotellist = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                if (column == 0)
                    return false;
                return super.isCellEditable(row,column);
            }
        };

        Object[] col_hotellist = {"id","Otel Adı","Yıldız","Tesis Özellikleri","Adres","Telefon","E-mail"};
        mdl_hotellist.setColumnIdentifiers(col_hotellist);
        row_hotellist = new  Object[col_hotellist.length];
        tbl_hotellist.setModel(mdl_hotellist);
        tbl_hotellist.getTableHeader().setReorderingAllowed(false);
        tbl_hotellist.getColumnModel().getColumn(0).setMaxWidth(75);

        tbl_hotellist.getSelectionModel().addListSelectionListener(e->{
            try {
                select_hotelid = Integer.parseInt(tbl_hotellist.getValueAt(tbl_hotellist.getSelectedRow(),0).toString());
            }catch (Exception exception){
                System.out.println(exception.getMessage());
            }
            loadHotelType(select_hotelid);
            loadHotelSeasonModel(select_hotelid);
        });


        for (Hotel obj : Hotel.getList()){
            Object[] row = new  Object[col_hotellist.length];
            row_hotellist[0] = obj.getId();
            row_hotellist[1] = obj.getName();
            row_hotellist[2] = obj.getStar();
            row_hotellist[3] = obj.getProperty();
            row_hotellist[4] = obj.getAddress();
            row_hotellist[5] = obj.getPhone();
            row_hotellist[6] = obj.getEmail();
            mdl_hotellist.addRow(row_hotellist);
        }
        mdl_hotelseason = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                if (column==0)
                    return false;
                return super.isCellEditable(row,column);
            }
        };

        mdl_hoteltype = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                if (column==0)
                    return false;
                return super.isCellEditable(row,column);
            }
        };

        tbl_roomlist.getSelectionModel().addListSelectionListener(e->{
            try {
                select_roomid = Integer.parseInt(tbl_roomlist.getValueAt(tbl_roomlist.getSelectedRow(),0).toString());
            }catch (Exception exception){
                System.out.println(exception.getMessage());
            }
        });

        mdl_reservationlist = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_reservation_list = {"id", "Ad Soyad", "Telefon", "E-mail", "Not", "Room id", "Giriş Tarihi", "Çıkış Tarihi", "Yetişkin Sayısı", "Çocuk Sayısı", "Toplam Ücret"};
        mdl_reservationlist.setColumnIdentifiers(col_reservation_list);
        row_reservationlist = new Object[col_reservation_list.length];
        //loadReservationModel();
        tbl_reservationList.setModel(mdl_reservationlist);
        tbl_reservationList.getTableHeader().setReorderingAllowed(false);
        tbl_reservationList.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_reservationList.getSelectionModel().addListSelectionListener(e -> {
            try{
                String selected_res_id = tbl_reservationList.getValueAt(tbl_reservationList.getSelectedRow(), 0).toString();
                fld_res_id.setText(selected_res_id);
            }catch (Exception exception){
                System.out.println(exception.getMessage());
            }
        });
        for (Reservation obj : Reservation.getList()){
            Object[] row = new  Object[col_reservation_list.length];
            row_reservationlist[0] = obj.getId();
            row_reservationlist[1] = obj.getClient_name();
            row_reservationlist[2] = obj.getClient_phone();
            row_reservationlist[3] = obj.getClient_email();
            row_reservationlist[4] = obj.getClient_note();
            row_reservationlist[5] = obj.getRoom_id();
            row_reservationlist[6] = obj.getCheck_in();
            row_reservationlist[7] = obj.getCheck_out();
            row_reservationlist[8] = obj.getAdult_numb();
            row_reservationlist[9] = obj.getChild_numb();
            row_reservationlist[10] = obj.getTotal_price();
            mdl_reservationlist.addRow(row_reservationlist);
        }



        Object[] col_hotel_type = {"Pansiyon Tipleri"};
        mdl_hoteltype.setColumnIdentifiers(col_hotel_type);
        row_hoteltype= new Object[col_hotel_type.length];
        tbl_hoteltype.setModel(mdl_hoteltype);
        tbl_hoteltype.getTableHeader().setReorderingAllowed(false);




        Object[] col_hotel_season = {"Dönem Başlangıcı","Dönem Bitişi"};
        mdl_hotelseason.setColumnIdentifiers(col_hotel_season);
        row_hotelseason = new Object[col_hotel_season.length];
        tbl_hotelseason.setModel(mdl_hotelseason);
        tbl_hotelseason.getTableHeader().setReorderingAllowed(false);


        btn_otelEkle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HotelAdd hotelAdd = new HotelAdd();
            }
        });
        odaEkleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RoomAdd roomAdd = new RoomAdd();
            }
        });

        mdl_roomlist = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                if (column==0)
                    return false;
                return super.isCellEditable(row,column);
            }
        };

        Object[] col_roomlist = {"id","Hotel İd","Oda Tipi","Stok","Sezon Tarihleri","Yetişkin Fiyatı","Çocuk Fiyatı","Pansiyon Tipi"};
        mdl_roomlist.setColumnIdentifiers(col_roomlist);
        row_roomlist = new Object[col_roomlist.length];
        tbl_roomlist.setModel(mdl_roomlist);
        tbl_roomlist.getTableHeader().setReorderingAllowed(false);
        tbl_roomlist.getColumnModel().getColumn(0).setMaxWidth(75);
        for (Room obj : Room.getList()){
            Object[] row = new  Object[col_hotellist.length];
            row_roomlist[0] = obj.getId();
            row_roomlist[1]= obj.getHotel_id();
            row_roomlist[2] = obj.getRoom_type();
            row_roomlist[3] = obj.getStock();
            row_roomlist[4] = obj.getSeason_id();
            row_roomlist[5] = obj.getAdult_price();
            row_roomlist[6] = obj.getChild_price();
            row_roomlist[7] = obj.getHotel_type_id();
            mdl_roomlist.addRow(row_roomlist);
        }
        mdl_roomproperties = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                if (column==0)
                    return false;
                return super.isCellEditable(row,column);
            }
        };
        Object[] col_roomproperties = {"Oda Özellikleri","Yatak Bilgisi","Alan(m^2)"};
        mdl_roomproperties.setColumnIdentifiers(col_roomproperties);
        row_roomproperties = new Object[col_roomproperties.length];
        tbl_room_property.setModel(mdl_roomproperties);
        tbl_room_property.getTableHeader().setReorderingAllowed(false);
        tbl_room_property.getColumnModel().getColumn(0).setMaxWidth(75);

        tbl_roomlist.getSelectionModel().addListSelectionListener(e -> {
            try {
                select_roomid = Integer.parseInt(tbl_roomlist.getValueAt(tbl_roomlist.getSelectedRow(),0).toString());
            }
            catch (Exception ex){

            }
            fld_room_id.setText(Integer.toString(select_roomid));
            loadRoomPropertiesModel(select_roomid);
            reservation_roomid = select_roomid;
            select_roomid = 0;
        });


        btn_rezervasyonyap.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_room_id) || Helper.isFieldEmpty(fld_check_in) || Helper.isFieldEmpty(fld_check_out) || Helper.isFieldEmpty(fld_adult_numb) || Helper.isFieldEmpty(fld_child_numb)){
                Helper.showMsg("Rezervasyon yapılacak odayı seçin. Giriş-Çıkış tarihlerini ve misafir sayıları kısmını doldurun.");
            }
            else {
                Room room = Room.getFetch(reservation_roomid);
                adult_num = Integer.parseInt(fld_adult_numb.getText());
                child_num = Integer.parseInt(fld_child_numb.getText());
                check_in = fld_check_in.getText().trim();
                check_out = fld_check_out.getText().trim();

                ReservationGUI resGUI = new ReservationGUI(room, adult_num, child_num, check_in, check_out);
                resGUI.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        fld_check_in.setText(null);
                        fld_check_out.setText(null);
                        fld_adult_numb.setText(null);
                        fld_child_numb.setText(null);
                        fld_room_id.setText(String.valueOf(0));
                        super.windowClosed(e);
                        loadRoomListModel();
                        //loadReservationModel();
                    }
                });
            }
        });
        odaAraButton.addActionListener(e -> {
            String regionHotelName = fld_src_hotelname.getText();

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            check_in = fld_check_in.getText().trim();
            check_out = fld_check_out.getText().trim();
            Date check_in_date = null;
            Date check_out_date = null;
            try {
                check_in_date = formatter.parse(check_in);
                check_out_date = formatter.parse(check_out);
            } catch (ParseException ex) {

            }
            String query = "SELECT * FROM hotel WHERE name LIKE '%{{name}}%' OR address LIKE '%{{address}}%'";
            query = query.replace("{{name}}", regionHotelName);
            query = query.replace("{{address}}", regionHotelName);
            ArrayList<Hotel> searchingHotel = Hotel.searchHotelList(query);
            ArrayList<Room> searchingRoom = new ArrayList<>();
            if (Helper.isFieldEmpty(fld_check_in) && Helper.isFieldEmpty(fld_check_out) && Helper.isFieldEmpty(fld_src_hotelname)){
                loadRoomListModel();
            }
            else if (Helper.isFieldEmpty(fld_check_in) && Helper.isFieldEmpty(fld_check_out)){
                for (Hotel hotel : searchingHotel){
                    Room obj = Room.getFetchByHotelID(hotel.getId());
                    searchingRoom.add(obj);
                }
                if (searchingRoom.size() == 0){
                    Helper.showMsg("Aradığınız kriterlere uygun oda bulunamadı!");
                }
                else {
                    loadRoomListModel(searchingRoom);
                }
            }
            else {
                for (Hotel obj : searchingHotel){
                    ArrayList<HotelSeason> searchingSeason = HotelSeason.getListByHotelID(obj.getId());
                    for (HotelSeason season : searchingSeason){
                        String season_start = season.getSeason_start();
                        String season_end = season.getSeason_end();
                        Date season_start_date = null;
                        Date season_end_date = null;
                        try {
                            season_start_date = formatter.parse(season_start);
                            season_end_date = formatter.parse(season_end);
                        } catch (ParseException ex) {
                            ex.printStackTrace();
                        }

                        if (season_start_date.before(check_in_date) && season_end_date.after(check_out_date)){
                            Room room = Room.getFetchByHotelIDSeasonID(season.getId(), obj.getId());
                            if (room != null){
                                searchingRoom.add(room);
                            }

                        }
                    }
                }

                if (searchingRoom.size() == 0){
                    Helper.showMsg("Aradığınız kriterlere uygun oda bulunamadı!");
                }
                else {
                    loadRoomListModel(searchingRoom);
                }
            }
            fld_src_hotelname.setText(null);
        });
    }


    public void loadHotelModel(){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_hotellist.getModel();
        clearModel.setRowCount(0);

        int i ;
        for (Hotel object:Hotel.getList()){
            i = 0;
            row_hotellist[i++] = object.getId();
            row_hotellist[i++] = object.getName();
            row_hotellist[i++] = object.getAddress();
            row_hotellist[i++] = object.getEmail();
            row_hotellist[i++] = object.getPhone();
            row_hotellist[i++] = object.getStar();
            row_hotellist[i++] = object.getProperty();

            mdl_hotellist.addRow(row_hotellist);
        }
    }


    public void loadHotelType(int id){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_hoteltype.getModel();
        clearModel.setRowCount(0);

        int i;
        for (HotelType object:HotelType.getListByHotelID(id)){
            i = 0;

            row_hoteltype[i++] = object.getType();
            mdl_hoteltype.addRow(row_hoteltype);
        }
    }
    public void loadHotelSeasonModel(int id){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_hotelseason.getModel();
        clearModel.setRowCount(0);

        int i;
        for (HotelSeason object : HotelSeason.getListByHotelID(id)){
            i = 0;
            row_hotelseason[i++] = object.getSeason_start();
            row_hotelseason[i++] = object.getSeason_end();
            mdl_hotelseason.addRow(row_hotelseason);
        }
    }

    public void loadHotelSeasonModel(){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_hotelseason.getModel();
        clearModel.setRowCount(0);

        int i;
        for (HotelSeason object : HotelSeason.getList()){
            i = 0;

            row_hotelseason[i++] = object.getSeason_start();
            row_hotelseason[i++] = object.getSeason_end();
            mdl_hotelseason.addRow(row_hotelseason);
        }
    }
    private void loadRoomListModel(){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_roomlist.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Room obj : Room.getList()){
            i = 0;
            row_roomlist[i++] = obj.getId();
            row_roomlist[i++] = Hotel.getFetch(obj.getHotel_id()).getName();
            row_roomlist[i++] = obj.getRoom_type();
            row_roomlist[i++] = obj.getStock();
            row_roomlist[i++] = HotelSeason.getFetch(obj.getSeason_id()).getSeason_start() + " - " + HotelSeason.getFetch(obj.getSeason_id()).getSeason_end();
            row_roomlist[i++] = obj.getAdult_price();
            row_roomlist[i++] = obj.getChild_price();
            row_roomlist[i++] = HotelType.getFetch(obj.getHotel_type_id()).getType();
            mdl_roomlist.addRow(row_roomlist);
        }
    }
    private void loadRoomListModel(ArrayList<Room> list) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_roomlist.getModel();
        clearModel.setRowCount(0);
        int i;
        if (list == null){
            Helper.showMsg("Aradığınız kriterlere uygun oda bulunamadı!");
        }
        else{
            for (Room obj : list){
                i = 0;
                row_roomlist[i++] = obj.getId();
                row_roomlist[i++] = Hotel.getFetch(obj.getHotel_id()).getName();
                row_roomlist[i++] = obj.getRoom_type();
                row_roomlist[i++] = obj.getStock();
                row_roomlist[i++] = HotelSeason.getFetch(obj.getSeason_id()).getSeason_start() + " - " + HotelSeason.getFetch(obj.getSeason_id()).getSeason_end();
                row_roomlist[i++] = obj.getAdult_price();
                row_roomlist[i++] = obj.getChild_price();
                row_roomlist[i++] = com.HotelReservation.Model.HotelType.getFetch(obj.getHotel_type_id()).getType();
                mdl_roomlist.addRow(row_roomlist);
            }
        }

    }
    private void loadRoomPropertiesModel(int id){
        DefaultTableModel clearModel = (DefaultTableModel) tbl_room_property.getModel();
        clearModel.setRowCount(0);
        int i = 0;
        for (RoomProperties obj : RoomProperties.getListByRoomID(id)){
            row_roomproperties[i++] = obj.getProperty();
            row_roomproperties[i++] = obj.getBed();
            row_roomproperties[i++] = obj.getArea();
            mdl_roomproperties.addRow(row_roomproperties);
        }
    }
    private void loadReservationModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_reservationList.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Reservation obj : Reservation.getList()){
            i = 0;
            row_reservationlist[i++] = obj.getId();
            row_reservationlist[i++] = obj.getClient_name();
            row_reservationlist[i++] = obj.getClient_phone();
            row_reservationlist[i++] = obj.getClient_email();
            row_reservationlist[i++] = obj.getClient_note();
            row_reservationlist[i++] = Hotel.getFetch(Room.getFetch(obj.getRoom_id()).getHotel_id()).getName();
            row_reservationlist[i++] = Room.getFetch(obj.getRoom_id()).getRoom_type();
            row_reservationlist[i++] = obj.getCheck_in();
            row_reservationlist[i++] = obj.getCheck_out();
            row_reservationlist[i++] = obj.getAdult_numb();
            row_reservationlist[i++] = obj.getChild_numb();
            row_reservationlist[i++] = obj.getTotal_price();

            mdl_reservationlist.addRow(row_reservationlist);
        }
    }
}
