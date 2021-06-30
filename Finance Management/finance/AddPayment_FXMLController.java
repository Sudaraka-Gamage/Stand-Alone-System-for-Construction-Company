/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finance;

import DBconnection.DBconnect;
import generate_email.GenerateMail;
import generate_email.PdfGeneration;
import models.Payment;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class AddPayment_FXMLController implements Initializable {
    
    private Stage currentStage = Finance.newStage;
    
    private int payment_key;

    Connection con;
    PreparedStatement prep;
    ResultSet rs;
    
    Payment payment = new Payment();
    GenerateMail gm = new GenerateMail();
    PdfGeneration gp = new PdfGeneration();
    @FXML
    private Label payment_id;
    @FXML
    private Label payment_value;
    @FXML
    private Label payment_date;
    @FXML
    private ComboBox<String> contract;
    @FXML
    private Label installment_num;
    
    
    ObservableList<String> contracts = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //installment_num.setItems(installment);
        
        try {
            generatePayment_id();
            fillContratComboBox();
        } catch (SQLException ex) {
            Logger.getLogger(AddPayment_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        contract.setItems(contracts);
        payment_id.setText(payment.getPayment_id());
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        payment.setPayment_date(dateFormat.format(date));
        payment_date.setText(payment.getPayment_date());
    }    
     
    @FXML
    public void addPayment(ActionEvent event) throws SQLException{
     /*   boolean isPaymentValueEmpty = formValidation.isTextFieldNotEmpty(payment_id, err_paymentValue, "Payment Value is Empty");
        boolean isPaymentDateEmpty = formValidation.isDateFieldNotEmpty(payment_date, err_date, "Please Select Date");
        boolean isContractIdEmpty = formValidation.isComboBoxNotEmpty(contract, err_contractId, "Please Select Option");
        boolean isInstallmentNumEmpty = formValidation.isComboBoxNotEmpty(installment_num, err_installmentNum, "Please Select Option");
        
        if(isPaymentValueEmpty && isPaymentDateEmpty && isContractIdEmpty && isInstallmentNumEmpty){*/
            con = DBconnect.getConnection();
            String query = "Insert into Contract_Payments values(?,?,?,?,?)";
            String contract_id = contract.getValue();
            payment.setContract(contract_id);
            try {
                prep = con.prepareStatement(query);
                prep.setString(1, payment.getPayment_id());
                prep.setString(2, payment.getContract());
                prep.setString(3, payment.getInstallment_num());
                prep.setString(4, payment.getPayment_date());
                prep.setFloat(5, Float.valueOf(payment_value.getText()));

                prep.execute();
                System.out.println("Added to the database");
                JOptionPane.showMessageDialog(null, "Added to the database");
                gp.generatePdf(payment);
                String emailAdd = getEmailAddress(payment.getContract());
                gm.create_sendEmail(payment.getPayment_id(), emailAdd);
                storeNextPayment_key();
                increInstallmentNum();
                clearFields();
            } catch (SQLException ex) {
                Logger.getLogger(AddPayment_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                prep.close();

            }
       // }
    }
    
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
    
    private void generatePayment_id() throws SQLException{
        String prefix = "PID";
        con = DBconnect.getConnection();
        String query = "Select key_value from Primary_Keys where key_type = 'payment_id'";
        
        try {
            prep = con.prepareStatement(query);
            rs = prep.executeQuery();
            payment_key = rs.getInt(1);
            payment_key++;
            payment.setPayment_id(prefix + payment_key);
            
        } catch (SQLException ex) {
            Logger.getLogger(AddPayment_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            prep.close();
        }
    }
    
    private void storeNextPayment_key() throws SQLException{
        con = DBconnect.getConnection();
        String query = "Update Primary_Keys set key_value = ? where key_type = 'payment_id'";
        try {
            prep = con.prepareStatement(query);
            prep.setInt(1, payment_key);
            prep.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AddPayment_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            prep.close();
        }
        
    }
    @FXML
    private void displayPaymentdetails(ActionEvent event) throws SQLException {
        String contract_id = contract.getValue();
        con = DBconnect.getConnection();
        String query = "Select est_cost, payment_num from Contracts where contract_id = ?" ;
        try {
            prep = con.prepareStatement(query);
            prep.setString(1, contract_id);
            rs = prep.executeQuery();
            
            float estCost = rs.getFloat("est_cost");
            int paymentNum = rs.getInt("payment_num") + 1;
            
            payment.setPayment_value(estCost / 4);
            payment.setInstallment_num(String.valueOf(paymentNum));
            payment_value.setText(String.valueOf(payment.getPayment_value()));
            installment_num.setText(payment.getInstallment_num());
            
            
        } catch (SQLException ex) {
            Logger.getLogger(AddPayment_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            prep.close();
            rs.close();
        }
    }
    
    private void clearFields() {
        try {
            generatePayment_id();
            payment_id.setText(payment.getPayment_id());
            Parent root = FXMLLoader.load(getClass().getResource("AddPayment_FXML.fxml"));
            
            Scene scene = new Scene(root);
            currentStage.setTitle("Finance");
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException ex) {
            Logger.getLogger(AddPayment_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AddPayment_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    
    private void increInstallmentNum() throws SQLException {
        con = DBconnect.getConnection();
        String query = "Update Contracts set payment_num = ? where contract_id = ?" ;
        try {
            prep = con.prepareStatement(query);
            prep.setInt(1, Integer.parseInt(payment.getInstallment_num()));
            prep.setString(2, payment.getContract());
            prep.executeUpdate();
           
        } catch (SQLException ex) {
            Logger.getLogger(AddPayment_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            prep.close();
        }
    }
    
    private String getEmailAddress(String contractId) {
        String email = null;
        try {
            con = DBconnect.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(AddPayment_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        String query = "Select email from Customer where customer_id IN (Select customer_id from Contracts where contract_id = ? )";
        
        try {
            prep = con.prepareStatement(query);
            prep.setString(1, contractId);
            rs = prep.executeQuery();
            email = rs.getString(1);
            
        } catch (SQLException ex) {
            Logger.getLogger(AddPayment_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                prep.close();
            } catch (SQLException ex) {
                Logger.getLogger(AddPayment_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return email;
    }


    @FXML
    private void home(MouseEvent event) {
    }

    @FXML
    private void exit(MouseEvent event) {
    }

    @FXML
    private void viewPayments(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ViewPayment_FXML.fxml"));
        
        Scene scene = new Scene(root);
        currentStage.setTitle("Finance");
        currentStage.setScene(scene);
        currentStage.show();
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
    private void contractPayments(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Payments_FXML.fxml"));
        
        Scene scene = new Scene(root);
        currentStage.setTitle("Finance");
        currentStage.setScene(scene);
        currentStage.show();
    }

    

    

    

    
    
}