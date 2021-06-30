/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finance;

import models.UtilityBill;
import DBconnection.DBconnect;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Chathura
 */
public class AddUtilityBill_FXMLController implements Initializable {
    
    private Stage currentStage = Finance.newStage;
    
    private int billId;

    Connection con;
    PreparedStatement prep;
    ResultSet rs;
    
    UtilityBill ub = new UtilityBill();
    
    @FXML
    private Label bill_id;
    @FXML
    private TextField charges;
    @FXML
    private DatePicker due_date;
    @FXML
    private DatePicker paid_date;
    @FXML
    private ComboBox<String> contract_id;
    @FXML
    private ComboBox<String> bill_type;
    @FXML
    private ComboBox<String> bill_month;
    
    ObservableList<String> types = FXCollections.observableArrayList("Electricity", "Water", "Telephone");
    ObservableList<String> month = FXCollections.observableArrayList("January", "February", "March", "Apirl", "May", "June", "July", "August", "September", "October", "November", "December");
    ObservableList<String> contracts = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	
    	
        bill_type.setItems(types);
        bill_month.setItems(month);
        
        
        try {
            generateBill_id();
            fillContratComboBox();
        } catch (SQLException ex) {
            Logger.getLogger(AddPayment_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        contract_id.setItems(contracts);
        bill_id.setText(ub.getBill_id());
    }    

    @FXML
    private void addUtilityBill(ActionEvent event) throws SQLException {
    	
        if(validationEmpty() && validateNumber() && validationCombobox()){
            con = DBconnect.getConnection();

            String query = "Insert into Utility_Bill values(?,?,?,?,?,?,?)";

            ub.setContract_id(contract_id.getValue());
            ub.setBill_type(bill_type.getValue());
            ub.setBill_month(bill_month.getValue());
            ub.setDue_date(due_date.getValue().toString());
            ub.setPaid_date(paid_date.getValue().toString());
            ub.setCharges(Float.valueOf(charges.getText()));

            try {
                prep = con.prepareStatement(query);
                prep.setString(1, ub.getBill_id());
                prep.setString(2, ub.getContract_id());
                prep.setString(3, ub.getBill_type());
                prep.setString(4, ub.getBill_month());
                prep.setString(5, ub.getDue_date());
                prep.setString(6, ub.getPaid_date());
                prep.setFloat(7, ub.getCharges());

                prep.execute();
                storeNextPayment_key();
                System.out.println("Added to the database");
                JOptionPane.showMessageDialog(null, "Added to the database");
                clearFields();
            } catch (SQLException ex) {
                Logger.getLogger(AddPayment_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                prep.close();

            }
        }
    }

    
    
    
    private void generateBill_id() throws SQLException {
        String prefix = "BID";
        con = DBconnect.getConnection();
        String query = "Select key_value from Primary_Keys where key_type = 'utilityBill_id'";
        
        try {
            prep = con.prepareStatement(query);
            rs = prep.executeQuery();
            billId = rs.getInt(1);
            billId++;
            ub.setBill_id(prefix + billId);
            
        } catch (SQLException ex) {
            Logger.getLogger(AddPayment_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
       
    
    
    
    
    
    public void fillContratComboBox() throws SQLException {
        con = DBconnect.getConnection();
        String query = "Select contract_id from Contracts";
        try {
            prep = con.prepareStatement(query);
            rs = prep.executeQuery();
            
            while(rs.next()){
                contracts.add(rs.getString("contract_id"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddPayment_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            prep.close();
            rs.close();
        }
        
    }
    
    
    
    
    private void storeNextPayment_key() throws SQLException{
    	
        con = DBconnect.getConnection();
        String query = "Update Primary_Keys set key_value = ? where key_type = 'utilityBill_id'";
        try {
            prep = con.prepareStatement(query);
            prep.setInt(1, billId);
            prep.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AddPayment_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    private void clearFields() throws SQLException{
        generateBill_id();
        bill_id.setText(ub.getBill_id());
        due_date.setValue(null);
        due_date.getEditor().setText(null);
        paid_date.setValue(null);
        paid_date.getEditor().setText(null);
        charges.clear();
        bill_month.setValue(null);
        contract_id.setValue(null);
        bill_type.setValue(null);
    }

    @FXML
    private void home(MouseEvent event) {
    }

    @FXML
    private void finance(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Finance_FXML.fxml"));
        
        Scene scene = new Scene(root);
        
        currentStage.setScene(scene);
        currentStage.show();
    }

    
    
    @FXML
    private void utilityBills(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("UtilityBills_FXML.fxml"));
        
        Scene scene = new Scene(root);
        currentStage.setTitle("Finance");
        currentStage.setScene(scene);
        currentStage.show();
    }

    @FXML
    private void viewUtilityBills(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ViewUtilityBills_FXML.fxml"));
        
        Scene scene = new Scene(root);
        currentStage.setTitle("Finance");
        currentStage.setScene(scene);
        currentStage.show();
    }

    @FXML
    private void exit(MouseEvent event) {
    }


    
    
    
    
    @FXML
    private boolean validationEmpty() {
        if (due_date.getEditor().getText().isEmpty() | paid_date.getEditor().getText().isEmpty()) {
            Alert 	 = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please Select the Date");

            alert.showAndWait();

            return false;
        }
        return true;
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    private boolean validateNumber() {

        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(charges.getText());


        if (m.find() && m.group().equals(charges.getText())) {

            return true;

        } else {

            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Warning Dialog");
            alert2.setHeaderText(null);
            alert2.setContentText("Please enter valid value");

            alert2.showAndWait();

            return false;

        }

    }
   
    
    
    private boolean validationCombobox() {
        if (contract_id.getSelectionModel().isEmpty() | bill_type.getSelectionModel().isEmpty() | bill_month.getSelectionModel().isEmpty()) {
            Alert alert3 = new Alert(Alert.AlertType.WARNING);
            alert3.setTitle("Warning Dialog");
            alert3.setHeaderText(null);
            alert3.setContentText("Please Select an item");

            alert3.showAndWait();

            return false;
        }
        return true;
    }

    
    
    
    
    
    
    @FXML
    private void fillSampleData(ActionEvent event) {
        contract_id.setValue("C0010");
        bill_type.setValue("Water");
        bill_month.setValue("September");
        charges.setText("5500");
    }
    
}
