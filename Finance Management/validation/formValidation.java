/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validation;

import java.sql.Date;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author Chathura
 */
public class formValidation {
    public static boolean isTextFieldNotEmpty(TextField tf){
        boolean b = false;
        if(tf.getText().length() != 0 || !tf.getText().isEmpty()){
            b = true;
        }
        return b;
    }
    
    public static boolean isTextFieldNotEmpty(TextField tf, Label lb, String errorMessage){
        boolean b = true;
        String msg = null;
        if(tf.getText().length() != 0 ||!tf.getText().isEmpty()){
            b = false;
            msg = errorMessage;
        }
        lb.setText(msg);
        return b;
    }
    
    public static boolean isDateFieldNotEmpty(DatePicker dp, Label lb, String errorMessage){
        boolean b = true;
        String msg = null;
        if(dp.getValue().toString().length() != 0 || !dp.getValue().toString().isEmpty()){
            b = false;
            msg = errorMessage;
        }
        lb.setText(msg);
        return b;
    }
    
    public static boolean isComboBoxNotEmpty(ComboBox cb, Label lb, String errorMessage){
        boolean b = true;
        String msg = null;
        if(cb.getValue().toString().length() != 0 || !cb.getValue().toString().isEmpty()){
            b = false;
            msg = errorMessage;
        }
        lb.setText(msg);
        return b;
    }

    public static boolean isDateFieldNotEmpty(DatePicker due_date, TextField charges, String please_Select_Date) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
