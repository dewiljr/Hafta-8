package com.HotelReservation.View;

import com.HotelReservation.Helper.Helper;
import com.HotelReservation.Model.User;
import com.HotelReservation.Model.Worker;
import com.HotelReservation.Model.operator;

import javax.swing.*;
import java.awt.*;

public class LoginScreen extends JFrame{
    private JPanel wrapper ;
    private JPanel wtop;
    private JPanel wbot;
    private JLabel TopLine;
    private JLabel BotLine;
    private JLabel uname;
    private JTextField fld_uname;
    private JLabel psw;
    private JPasswordField fld_psw;
    private JButton btnLogin;

    public LoginScreen(){
        add(wrapper);
        setSize(600,400);
        setTitle("Kullanıcı Girişi");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width)/2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height)/2;
        setLocation(x,y);
        setVisible(true);
        btnLogin.addActionListener(e -> {
            if (fld_uname.getText().length() == 0 || fld_psw.getText().length() == 0){
                JOptionPane.showMessageDialog(null,"Tüm Alanları Doldurunuz!","Hata", JOptionPane.INFORMATION_MESSAGE);
            }else {
                User u =User.getFetch(fld_uname.getText(),fld_psw.getText());
                if (u==null){
                    Helper.showMsg("Kullanıcı Bulunamadı!");
                }
                else {
                    switch (u.getType()){
                        case "operator":
                            operatorGUI opGUI = new operatorGUI((operator)u);
                            break;
                        case "Worker":
                            WorkerGUI workerGUI = new WorkerGUI((Worker)u);
                            break;
                    }
                    dispose();
                }
            }

        });
    }
}
