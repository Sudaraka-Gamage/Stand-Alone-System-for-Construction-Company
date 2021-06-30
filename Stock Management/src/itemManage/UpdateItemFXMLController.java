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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javax.swing.JOptionPane;

/**
 *
 * @author Gayan M
 */
public class UpdateItemFXMLController implements Initializable {
    
    private Stage currentStage = Stock.PrimaryStage;
    
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;
    
    item i; 
   
    @FXML
    private Text itemID;

    @FXML
    private TextField itemBrand;

    @FXML
    private Text itemName;

    @FXML
    private TextField itemPrice;

    @FXML
    private Button updateDetails;
    
    @FXML
    private Button cancel;

    @FXML
    private Text supplierID;

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
    
    ObservableList <String> supplierList = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //System.out.println(ItemFXMLController.search_item);
        item itemT = displayItem(ItemFXMLController.search_item);
        itemID.setText(itemT.getID());
        supplierID.setText(itemT.getSupplierID());
        itemBrand.setText(itemT.getBrand());
        itemName.setText(itemT.getName());
        itemPrice.setText(String.valueOf(itemT.getPrice()));
          
        stockManagement.setOnAction( e-> {
            
            try {
                Parent root;
                root = FXMLLoader.load(getClass().getResource("/stockManage/StockFXML.fxml"));
                Scene scene = new Scene(root);
                
                currentStage.setScene(scene);
                currentStage.show();
                
            } catch (IOException ex) {
                Logger.getLogger(UpdateItemFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
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
                    root = FXMLLoader.load(getClass().getResource("UpdateItemFXML.fxml"));
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
    
    public void setItemID(String search_item){
        
    }
    
    private item displayItem(String itemID){
        
        try {
            conn = DBConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(UpdateItemFXMLController.class.getName()).log(Level.SEVERE, null, ex);
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
    
    @FXML
    public void updateDetails() throws SQLException, IOException{
        
        String supplier = supplierID.getText();
        String brand = itemBrand.getText();
        String price = itemPrice.getText();
         
        conn = DBConnection.getConnection();
        String query="UPDATE item SET SupplierID = ?, Brand = ?, Price = ? WHERE ID = ?";
        
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, supplier);
            ps.setString(2, brand);
            ps.setString(3, price);
            ps.setString(4, ItemFXMLController.search_item);
            
            ps.execute();
            JOptionPane.showMessageDialog(null, "Item has updated.");
          
        } catch (SQLException ex) {
            Logger.getLogger(UpdateItemFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ps.close();
            conn.close();
        }
        
        Parent root;
        try{
            root = FXMLLoader.load(getClass().getResource("ItemFXML.fxml"));
            
            Scene scene = new Scene(root);
            
            currentStage.setTitle("NTDB");
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
