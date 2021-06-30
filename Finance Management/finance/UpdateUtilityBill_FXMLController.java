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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Chathura
 */
public class UpdateUtilityBill_FXMLController implements Initializable {
    
    private Stage currentStage = Finance.newStage;
    
    public String  billID;
    
    Connection con;
    PreparedStatement prep;
    ResultSet rs;
    UtilityBill ub = new UtilityBill();
    
    
    @FXML
    private Label bill_id;
    @FXML
    private TextField charges;
    @FXML
    private AnchorPane due_date;
    @FXML
    private Label due_Date;
    @FXML
    private Label paid_date;
    @FXML
    private ComboBox<String> contract_id;
    @FXML
    private ComboBox<String> bill_type;
    @FXML
    private ComboBox<String> month;
    
    ObservableList<String> types = FXCollections.observableArrayList("Electricity", "Water", "Telephone");
    ObservableList<String> months = FXCollections.observableArrayList("January", "February", "March", "Apirl", "May", "June", "July", "August", "September", "October", "November", "December");
    ObservableList<String> contracts = FXCollections.observableArrayList();
    


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bill_type.setItems(types);
        month.setItems(months);
        try {
            fillContratComboBox();
        } catch (SQLException ex) {
            Logger.getLogger(UpdateUtilityBill_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        contract_id.setItems(contracts);
        
        displayBill(ViewUtilityBills_FXMLController.billID);
        bill_id.setText(ub.getBill_id());
        contract_id.setValue(ub.getContract_id());
        bill_type.setValue(ub.getBill_type());
        month.setValue(ub.getBill_month());
        due_Date.setText(ub.getDue_date());
        paid_date.setText(ub.getPaid_date());
        charges.setText(String.valueOf(ub.getCharges()));
    }  
    
    public void setBillID(String bill_id){
        this.billID = bill_id;
    }

    @FXML
    private void updateBill(ActionEvent event) throws SQLException, IOException {
        if(validateNumber() && validationCombobox()){
            con = DBconnect.getConnection();

            String query = "Update Utility_Bill set contract_id = ?, bill_type = ?, bill_month = ?, due_date = ?, paid_date = ?, charges = ? where bill_id = ?";

            ub.setContract_id(contract_id.getValue());
            ub.setBill_type(bill_type.getValue());
            ub.setBill_month(month.getValue());
            ub.setDue_date(due_Date.getText());
            ub.setPaid_date(paid_date.getText());
            ub.setCharges(Float.valueOf(charges.getText()));

            try {
                prep = con.prepareStatement(query);

                prep.setString(1, ub.getContract_id());
                prep.setString(2, ub.getBill_type());
                prep.setString(3, ub.getBill_month());
                prep.setString(4, ub.getDue_date());
                prep.setString(5, ub.getPaid_date());
                prep.setFloat(6, ub.getCharges());
                prep.setString(7, ub.getBill_id());

                prep.executeUpdate();
                System.out.println("Added to the database");
                JOptionPane.showMessageDialog(null, "Record Updated");
                Parent root = FXMLLoader.load(getClass().getResource("ViewUtilityBills_FXML.fxml"));

                Scene scene = new Scene(root);

                currentStage.setScene(scene);
                currentStage.show();
            } catch (SQLException ex) {
                Logger.getLogger(AddPayment_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                prep.close();

            }
        }
    }

    @FXML
    private void home(MouseEvent event) {
    }

    @FXML
    private void finance(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Finance_FXML.fxml"));
        
        Scene scene = new Scene(root);
        currentStage.setTitle("Finance");
        currentStage.setScene(scene);
        currentStage.show();
    }

    @FXML
    private void utillBill(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("UtilityBills_FXML.fxml"));
        
        Scene scene = new Scene(root);
        currentStage.setTitle("Finance");
        currentStage.setScene(scene);
        currentStage.show();
    }

    @FXML
    private void viewUtillBills(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ViewUtilityBills_FXML.fxml"));
        
        Scene scene = new Scene(root);
        currentStage.setTitle("Finance");
        currentStage.setScene(scene);
        currentStage.show();
    }

    @FXML
    private void addUtillBill(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddUtilityBill_FXML.fxml"));
        
        Scene scene = new Scene(root);
        currentStage.setTitle("Finance");
        currentStage.setScene(scene);
        currentStage.show();
    }

    @FXML
    private void exit(MouseEvent event) {
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
    private UtilityBill displayBill(String Bill_id) {
        try {
            con = DBconnect.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(UpdatePayment_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String query = "Select * from Utility_Bill where bill_id = ? ";
        
        try {
            prep = con.prepareStatement(query);
            prep.setString(1, Bill_id);
            rs = prep.executeQuery();
            while(rs.next()){
                ub.setBill_id(rs.getString(1));
                ub.setContract_id(rs.getString(2));
                ub.setBill_type(rs.getString(3));
                ub.setBill_month(rs.getString(4));
                ub.setDue_date(rs.getString(5));
                ub.setPaid_date(rs.getString(6));
                ub.setCharges(rs.getFloat(7));
            }
            return ub;
        } catch (SQLException ex) {
            Logger.getLogger(ViewPayment_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                prep.close();
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(UpdatePayment_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        return null;
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
        if (contract_id.getSelectionModel().isEmpty() | bill_type.getSelectionModel().isEmpty() | month.getSelectionModel().isEmpty()) {
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
        month.setValue("August");
        charges.setText("6500");
    }
    
    
}
