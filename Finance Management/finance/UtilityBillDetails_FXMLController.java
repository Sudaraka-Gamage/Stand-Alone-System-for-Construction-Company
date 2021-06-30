/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finance;

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
import models.UtilityBill;

/**
 * FXML Controller class
 *
 * @author Chathura
 */
public class UtilityBillDetails_FXMLController implements Initializable {

    private Stage currentStage = Finance.newStage;
    Connection con;
    PreparedStatement prep;
    ResultSet rs;
    UtilityBill ub = new UtilityBill();
    
    @FXML
    private Label elec;
    @FXML
    private Label water;
    @FXML
    private Label tele;

    ObservableList<String> contracts = FXCollections.observableArrayList();
    @FXML
    private ComboBox<String> contract;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            fillContratComboBox();
        } catch (SQLException ex) {
            Logger.getLogger(UtilityBillDetails_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        contract.setItems(contracts);
    }    

    @FXML
    private void displayCost(ActionEvent event) {
        try {
            con = DBconnect.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(UpdatePayment_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String query = "Select bill_type, charges from Utility_Bill where contract_id = ? ";
        
        try {
            float wat = 0;
            float ele = 0;
            float tel = 0;
            prep = con.prepareStatement(query);
            prep.setString(1, contract.getValue());
            rs = prep.executeQuery();
            while(rs.next()){
                String type = rs.getString("bill_type");
               
                if("Water".equals(type)){
                    wat = wat + rs.getFloat("charges");
                    
                } else if("Electricity".equals(type)){
                    ele = ele + rs.getFloat("charges");   
                } else if("Telephone".equals(type)){
                    tel = tel + rs.getFloat("charges");
                    
                }
            }
            water.setText(String.valueOf(wat));
            elec.setText(String.valueOf(ele));
            tele.setText(String.valueOf(tel));
            
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
    private void exit(MouseEvent event) {
    }
    
}
