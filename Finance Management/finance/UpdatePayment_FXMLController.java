/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finance;

import models.Payment;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Chathura
 */
public class UpdatePayment_FXMLController implements Initializable {
    
    private Stage currentStage = Finance.newStage;
    
    public String  paymentID;
    
    Connection con;
    PreparedStatement prep;
    ResultSet rs;
    
    Payment payment = new Payment();
    Payment payment2 = new Payment();
    @FXML
    private Label payment_id;
    @FXML
    private Label payment_value;
    @FXML
    private Label installment_num;
    @FXML
    private ComboBox<String> contract;
    @FXML
    private Label payment_date;
    
    ObservableList<String> contracts = FXCollections.observableArrayList();

    
    
    
    
    /**
     * Initializes the controller class.
     */
    
    
    
    
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    	
    	
        try {
            fillContratComboBox();
        } catch (SQLException ex) {
            Logger.getLogger(UpdatePayment_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        contract.setItems(contracts);
        displayPayment(ViewPayment_FXMLController.paymentID);
        payment_id.setText(payment.getPayment_id());
        contract.setValue(payment.getContract());
        installment_num.setText(payment.getInstallment_num());
        payment_date.setText(payment.getPayment_date());
        payment_value.setText(String.valueOf(payment.getPayment_value()));
        System.out.println(ViewPayment_FXMLController.paymentID);
    }  
    
    /*public void setContractID(String contractID){
        this.contractID = contractID;
    }*/
    
    
    
    
    
    
    
    
    
    
    
    public void fillContratComboBox() throws SQLException {
        con = DBconnect.getConnection();
        String query = "Select contract_id from Contracts where payment_num < 4";
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
    
    
    
    
    
    
    
    
    
    
    
    
    private Payment displayPayment(String payment_id) {
        try {
            con = DBconnect.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(UpdatePayment_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String query = "Select * from Contract_Payments where payment_id = ? ";
        
        try {
            prep = con.prepareStatement(query);
            prep.setString(1, payment_id);
            rs = prep.executeQuery();
            while(rs.next()){
                payment.setPayment_id(rs.getString(1));
                payment.setContract(rs.getString(2));
                payment.setInstallment_num(rs.getString(3));
                payment.setPayment_date(rs.getString(4));
                payment.setPayment_value(rs.getFloat(5));
            }
            return payment;
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
    
    
    
    
    
    
    
    
    
    
    
    
    @FXML
    public void updatePayment(ActionEvent event) throws SQLException, IOException{
        con = DBconnect.getConnection();
        if(payment.getContract() != payment2.getContract()){
        	
            String query = "Update Contract_Payments set contract_id = ?, installment_num = ?, payment_date = ?, payment_value = ? where payment_id = ?";
            
            
            
            System.out.println(payment2.getPayment_id());
            System.out.println(payment2.getPayment_date());
            System.out.println(payment2.getPayment_value());
            System.out.println(payment2.getInstallment_num());

            try {
                prep = con.prepareStatement(query);

                prep.setString(1, payment2.getContract());
                prep.setString(2, payment2.getInstallment_num());
                prep.setString(3, payment2.getPayment_date());
                prep.setFloat(4, payment2.getPayment_value());
                prep.setString(5, payment2.getPayment_id());

                prep.executeUpdate();
                increInstallmentNum(payment2.getContract());
                decreInstallmentNum(payment.getContract());
                System.out.println("Added to the database");
                JOptionPane.showMessageDialog(null, "Record Updated");
                Parent root = FXMLLoader.load(getClass().getResource("ViewPayment_FXML.fxml"));

                Scene scene = new Scene(root);
                currentStage.setTitle("Finance");
                currentStage.setScene(scene);
                currentStage.show();
            } catch (SQLException ex) {
                Logger.getLogger(AddPayment_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                prep.close();

            }
        }
        else{
            Parent root = FXMLLoader.load(getClass().getResource("ViewPayment_FXML.fxml"));
            
            Scene scene = new Scene(root);
            currentStage.setTitle("Finance");
            currentStage.setScene(scene);
            currentStage.show();
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    @FXML
    private void displayPaymentdetails(ActionEvent event) throws SQLException {
        payment2.setContract(contract.getValue());
        con = DBconnect.getConnection();
        String query = "Select est_cost, payment_num from Contracts where contract_id = ?" ;
        try {
            prep = con.prepareStatement(query);
            prep.setString(1, payment2.getContract());
            rs = prep.executeQuery();
            
            float estCost = rs.getFloat("est_cost");
            int paymentNum = rs.getInt("payment_num") + 1;
            
            payment2.setPayment_value(estCost / 4);
            payment2.setInstallment_num(String.valueOf(paymentNum));
            payment2.setPayment_date(payment.getPayment_date());
            payment2.setPayment_id(payment.getPayment_id());
            payment_id.setText(payment2.getPayment_id());
            payment_value.setText(String.valueOf(payment2.getPayment_value()));
            installment_num.setText(payment2.getInstallment_num());
            payment_date.setText(payment2.getPayment_date());
            
        } catch (SQLException ex) {
            Logger.getLogger(AddPayment_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            prep.close();
            rs.close();
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    private void increInstallmentNum(String contractID) throws SQLException {
        con = DBconnect.getConnection();
        String query = "Update Contracts set payment_num = ? where contract_id = ?" ;
        try {
            prep = con.prepareStatement(query);
            prep.setInt(1, Integer.parseInt(payment2.getInstallment_num()));
            prep.setString(2, contractID);
            prep.executeUpdate();
           
        } catch (SQLException ex) {
            Logger.getLogger(AddPayment_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            prep.close();
        }
    }
    
    private void decreInstallmentNum(String contractID) throws SQLException {
        con = DBconnect.getConnection();
        int installmentNum = Integer.parseInt(payment.getInstallment_num());
        installmentNum--;
        String query = "Update Contracts set payment_num = ? where contract_id = ?" ;
        try {
            prep = con.prepareStatement(query);
            prep.setInt(1, installmentNum);
            prep.setString(2, contractID);
            prep.executeUpdate();
           
        } catch (SQLException ex) {
            Logger.getLogger(AddPayment_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            prep.close();
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
    private void contractPayment(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Payments_FXML.fxml"));
        
        Scene scene = new Scene(root);
        currentStage.setTitle("Finance");
        currentStage.setScene(scene);
        currentStage.show();
    }

    @FXML
    private void viewPayment(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ViewPayment_FXML.fxml"));
        
        Scene scene = new Scene(root);
        currentStage.setTitle("Finance");
        currentStage.setScene(scene);
        currentStage.show();
    }

    @FXML
    private void addPayment(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddPayment_FXML.fxml"));
        
        Scene scene = new Scene(root);
        currentStage.setTitle("Finance");
        currentStage.setScene(scene);
        currentStage.show();
    }

    @FXML
    private void exit(MouseEvent event) {
    }

    
    
}
