package com.HotelReservation.Helper;

import javax.swing.*;
import java.awt.*;

public class Helper {
    public static int screenCenter(String eksen, Dimension size){
        int point;
        switch (eksen){
            case "x":
                point = (Toolkit.getDefaultToolkit().getScreenSize().width - size.width)/2;
                break;
            case "y":
                point = (Toolkit.getDefaultToolkit().getScreenSize().height - size.height)/2;
                break;
            default:
                point = 0;
        }
        return point;
    }
    public static boolean isFieldEmpty(JTextField field){
        return field.getText().trim().isEmpty();
    }
    public static boolean isAreaEmpty(JTextArea area){
        return area.getText().trim().isEmpty();
    }
    public static void showMsg(String str){
        optionPageTR();
        String msg;
        String title;
        switch (str){
            case "fill":
                msg = "Lütfen Tüm Alanları Doldurunuz.";
                title = "Hata!";
                break;
            case "Success":
                msg = "İşlem Başarılı!";
                title = "Sonuç";
                break;
            case "Error"    :
                msg = "İşlem Hatalı!";
                title = "Hata!";
            default:
                msg = str;
                title="Mesaj";
        }
        JOptionPane.showMessageDialog(null,msg,title, JOptionPane.INFORMATION_MESSAGE);
    }
    public static void optionPageTR(){
        UIManager.put("OptionPane.okButtonText", "Tamam");
        UIManager.put("OptionPane.yesButtonText", "Evet");
        UIManager.put("OptionPane.noButtonText", "Hayır");
    }
    public static String searchQuery (String name, String uname, String type){
        String query = "SELECT * FROM user WHERE name LIKE '%{{name}}%' AND uname LIKE '%{{uname}}%' AND type LIKE '%{{type]]%'";
        query = query.replace("{{name}}",name);
        query = query.replace("{{uname}}", uname);
        query = query.replace("{{type}}", type);
        return query;
    }
    public static String hotelType(String number){
        String type = "";
        switch (number){
            case "1":
                type="Ultra Herşey Dahil";
                break;
            case "2":
                type="Herşey Dahil";
                break;
            case "3":
                type = "Oda Kahvaltı";
                break;
            case "4":
                type="Tam Pansiyon";
                break;
            case "5":
                type = "Yarım Pansiyon";
                break;
            case "6":
                type="Sadece Yatak";
                break;
            case "7":
                type="Alkol Hariç Full";
                break;
        }
        return type;
    }

}
