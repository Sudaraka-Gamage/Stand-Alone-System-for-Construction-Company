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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author Chathura
 */
public class Finance_FXMLController implements Initializable {
    private Stage currentStage = Finance.newStage;
    
    Connection con;
    PreparedStatement prep;
    ResultSet rs;
    
    @FXML
    private SplitPane mainPane;
    @FXML
    private Label totalCost;
    @FXML
    private Label material;
    @FXML
    private Label salary;
    @FXML
    private Label depre;
    @FXML
    private Label delivery;
    @FXML
    private Label utility;
    
    private float materi;
    private float sal;
    private float depr;
    private float deli;
    private float util;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        materi = getMaterialCost();
        sal = getSalaryCost();
        depr = getDepreCost();
        deli = getDeliveryCost();
        util = getUtilityBillCost();
        
        float total = materi + sal + depr + deli + util;
        
        material.setText(String.valueOf(materi));
        salary.setText(String.valueOf(sal));
        depre.setText(String.valueOf(depr));
        delivery.setText(String.valueOf(deli));
        utility.setText(String.valueOf(util));
        totalCost.setText(String.valueOf(total));
    }    

    @FXML
    private void home(MouseEvent event) {
    }

    @FXML
    private void exit(MouseEvent event) {
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void contractPayments(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Payments_FXML.fxml"));
        
        Scene scene = new Scene(root);
        currentStage.setTitle("Finance");
        currentStage.setScene(scene);
        currentStage.show();
    }

    @FXML
    private void utilityBill(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("UtilityBills_FXML.fxml"));
        
        Scene scene = new Scene(root);
        currentStage.setTitle("Finance");
        currentStage.setScene(scene);
        currentStage.show();
    }


    @FXML
    private void generateReport(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Report_FXML.fxml"));
        
        Scene scene = new Scene(root);
        currentStage.setTitle("Finance");
        currentStage.setScene(scene);
        currentStage.show();
    }

    @FXML
    private void utilityBillDetails(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("UtilityBillDetails_FXML.fxml"));
        
        Scene scene = new Scene(root);
        currentStage.setTitle("Finance");
        currentStage.setScene(scene);
        currentStage.show();
    }

    private float getMaterialCost() {
        float cost = 0;
        try {
            con = DBconnect.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(UpdatePayment_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String query = "Select * from stock";
        
        try {
            prep = con.prepareStatement(query);
            rs = prep.executeQuery();
            while(rs.next()){
                cost = cost + rs.getFloat("TotatlPrice");
                
            }
            
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
        return cost;
    }

    private float getSalaryCost() {
        float cost = 0;
        
        return cost;
    }

    private float getDepreCost() {
        float cost = 0;
        try {
            con = DBconnect.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(UpdatePayment_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String query = "Select * from AssetDep";
        
        try {
            prep = con.prepareStatement(query);
            rs = prep.executeQuery();
            while(rs.next()){
                cost = cost + rs.getFloat("cost");
                
            }
            
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
        return cost;
    }

    private float getDeliveryCost() {
        float cost = 0;
        float fuel = 0;
        float maintain = 0;
        float accCost = 0;
        float other = 0;
        try {
            con = DBconnect.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(UpdatePayment_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String query = "Select fuelCost, maintenaceCost, accidentRepairCost, otherCost from maintenace";
        
        try {
            prep = con.prepareStatement(query);
            rs = prep.executeQuery();
            while(rs.next()){
                fuel = fuel + rs.getFloat("fuelCost");
                maintain = maintain + rs.getFloat("maintenaceCost");
                accCost = accCost + rs.getFloat("accidentRepairCost");
                other = other + rs.getFloat("otherCost");
            }
            
            cost = fuel + maintain + accCost + other;
            
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
        return cost;
    }

    private float getUtilityBillCost() {
        float cost = 0;
        try {
                con = DBconnect.getConnection();
            } catch (SQLException ex) {
                Logger.getLogger(UpdatePayment_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }

            String query = "Select charges from Utility_Bill";

            try {
                prep = con.prepareStatement(query);
                rs = prep.executeQuery();
                while(rs.next()){
                    cost = cost + rs.getFloat("charges");

                }

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
            return cost;
    }

    
}
