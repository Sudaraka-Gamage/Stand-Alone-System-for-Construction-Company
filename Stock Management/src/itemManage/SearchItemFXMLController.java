/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package itemManage;

import stockManage.StockFXMLController;
import model.item;
import DBConnection.DBConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Gayan M
 */
public class SearchItemFXMLController implements Initializable {

    private Stage currentStage = Stock.PrimaryStage;
    
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;
    
    item i; 
    
    @FXML
    private Text itemID;

    @FXML
    private Text itemName;

    @FXML
    private Text supplierID;

    @FXML
    private Button cancel;

    @FXML
    private Text itemBrand;

    @FXML
    private Text itemPrice;

    @FXML
    private Button Home;

    @FXML
    private Button Exit;

    @FXML
    private Button stockManagement;

    @FXML
    private Button itemManagement;
    
    @FXML
    private Button supplierManagement;
    
    Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        item itemT=displayItem(ItemFXMLController.search_item2);
        itemID.setText(itemT.getID());
        supplierID.setText(itemT.getSupplierID());
        itemBrand.setText(itemT.getBrand());
        itemName.setText(itemT.getName());
        itemPrice.setText(String.valueOf(itemT.getPrice()));
        
        itemManagement.setOnAction( e->{
            
            try {
                Parent root;
                root = FXMLLoader.load(getClass().getResource("ItemFXML.fxml"));
                Scene scene = new Scene(root);

                currentStage.setScene(scene);
                currentStage.show();

            } catch (IOException ex) {
                Logger.getLogger(ItemFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        stockManagement.setOnAction( e-> {
            
            try {
                Parent root;
                root = FXMLLoader.load(getClass().getResource("/stockManage/StockFXML.fxml"));
                Scene scene = new Scene(root);
                
                currentStage.setScene(scene);
                currentStage.show();
                
            } catch (IOException ex) {
                Logger.getLogger(AddItemFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        supplierManagement.setOnAction( e-> {
            
            try {
                Parent root;
                root = FXMLLoader.load(getClass().getResource("/supplierManage/SupplierFXML.fxml"));
                Scene scene =  new Scene(root);
                
                currentStage.setScene(scene);
                currentStage.show();
                
            } catch (IOException ex) {
                Logger.getLogger(StockFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        Exit.setOnAction( e->{
            alert.setContentText("Do you want to exit?");
            alert.setTitle("Exit");
            
            Optional<ButtonType> result = alert.showAndWait();
            
            if(result.get() == ButtonType.OK){
                System.exit(0);
            } else {
                Parent root;
                
                try {
                    root = FXMLLoader.load(getClass().getResource("SearchItemFXML.fxml"));
                    Scene scene = new Scene(root);
                    
                    currentStage.setTitle("NTDB");
                    currentStage.setScene(scene);
                    currentStage.show();
                } catch (IOException ex) {
                    Logger.getLogger(ItemFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }    
    
    private item displayItem(String itemID){
        
        try {
            conn = DBConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(SearchItemFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String query = "SELECT * FROM item WHERE ID = ?";
        
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, itemID);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                i=new item();
                i.setID(rs.getString(1));
                i.setSupplierID(rs.getString(2));
                i.setName(rs.getString(3));
                i.setBrand(rs.getString(4));
                i.setPrice(rs.getFloat(5));
            }
            //return i;
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            //Logger.getLogger(ItemFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                ps.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                //Logger.getLogger(UpdateItemFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return i;
    } 
    
    public void setItemID(String search_item){
        
    }
}
