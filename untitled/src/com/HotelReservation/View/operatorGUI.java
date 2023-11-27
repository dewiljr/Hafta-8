package com.HotelReservation.View;

import com.HotelReservation.Helper.Config;
import com.HotelReservation.Helper.Helper;
import com.HotelReservation.Model.User;
import com.HotelReservation.Model.operator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class operatorGUI extends JFrame {

    private JPanel wrapper;
    private JTabbedPane tab_operator;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JPanel pnl_uList;
    private JScrollPane scrl_userList;
    private JTable tbl_userList;
    private JPanel pnl_userForm;
    private JTextField fld_userName;
    private JTextField fld_uname;
    private JPasswordField fld_userPsw;
    private JComboBox cmb_userType;
    private JButton btn_userAdd;
    private JButton btn_logOut;
    private DefaultTableModel mdl_userList;
    private Object row_userList;

    public operatorGUI(operator u){
        add(wrapper);
        setSize(1000,500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        int x = Helper.screenCenter("x", getSize());
        int y = Helper.screenCenter("y", getSize());
        setLocation(x,y);
        setVisible(true);

        mdl_userList = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                if (column == 0)
                    return false;
            return super.isCellEditable(row,column);
            }
        };

        //ModelUserList
        Object[] col_userList = {"ID" , "Ad Soyad" , "Kullanıcı Adı" , "Şifre" , "Üyelik Tipi"};
        mdl_userList.setColumnIdentifiers(col_userList);
        row_userList = new Object[col_userList.length];
        tbl_userList.setModel(mdl_userList);
        tbl_userList.getTableHeader().setReorderingAllowed(false);
        tbl_userList.getColumnModel().getColumn(0).setMaxWidth(75);

        for (User obj : User.getList()){
            Object[] row = new  Object[col_userList.length];
            row[0] = obj.getId();
            row[1] = obj.getName();
            row[2] = obj.getUname();
            row[3] = obj.getPsw();
            row[4] = obj.getType();
            mdl_userList.addRow(row);
        }


        tbl_userList.setModel(mdl_userList);
        tbl_userList.getTableHeader().setReorderingAllowed(false);

        btn_userAdd.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_userName) || Helper.isFieldEmpty(fld_uname) ||  Helper.isFieldEmpty(fld_userPsw)) {
            Helper.showMsg("fill");
            }
            else {
                String name = fld_userName.getText();
                String uname = fld_uname.getText();
                String psw = fld_userPsw.getText();
                String type = cmb_userType.getSelectedItem().toString();
                if (User.add(name,uname,psw,type)){
                    Helper.showMsg("Success");

                    fld_userName.setText(null);
                    fld_uname.setText(null);
                    fld_userPsw.setText(null);
                    cmb_userType.setSelectedIndex(0);
                }
                else {
                    Helper.showMsg("Error");
                }
            }
        });
        btn_logOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
