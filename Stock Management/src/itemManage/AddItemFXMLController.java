/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package itemManage;

import stockManage.StockFXMLController;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import model.item;

/**
 *
 * @author Gayan M
 */
public class AddItemFXMLController implements Initializable {
   
    private Stage currentStage = Stock.PrimaryStage;
    
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;
    
    item i = new item();
    
    @FXML
    private TextField itemCode;

    @FXML
    private TextField itemBrand;

    @FXML
    private TextField itemName;

    @FXML
    private TextField itemPrice;

    @FXML
    private Button saveDetails;

    @FXML
    private ComboBox<String> supplierID;

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
    
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    
    ObservableList <String> suppliers = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    try {
        fillSupllierComboBox();
        
    } catch (SQLException ex) {
        Logger.getLogger(AddItemFXMLController.class.getName()).log(Level.SEVERE, null, ex);
    }
        supplierID.setItems(suppliers);
        itemCode.setText(i.getID());
        
        stockManagement.setOnAction( e-> {
            
            try {
                Parent root;
                root = FXMLLoader.load(getClass().getResource("/stockManage/StockFXML.fxml"));
                Scene scene = new Scene(root);
                
                currentStage.setScene(scene);
                currentStage.show();
                
            } catch (IOException ex) {
                Logger.getLogger(ItemFXMLController.class.getName()).log(Level.SEVERE, null, ex);
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
        
        Exit.setOnAction( e-> {
            alert.setContentText("Do you want to exit?");
            alert.setTitle("Exit");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                System.exit(0);
            } else {
                Parent root;

                try {
                    root = FXMLLoader.load(getClass().getResource("AddItemFXML.fxml"));
                    Scene scene = new Scene(root);

                    currentStage.setTitle("NTDB");
                    currentStage.setScene(scene);
                    currentStage.show();
                } catch (IOException ex) {
                    Logger.getLogger(AddItemFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    @FXML
    public void insertDetails(ActionEvent event) throws SQLException {

        try {
            conn = DBConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(AddItemFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String query = "INSERT INTO item VALUES(?, ?, ?, ?, ?)";
        
        i.setID(itemCode.getText());
        i.setSupplierID(supplierID.getValue());
        i.setName(itemName.getText());
        i.setBrand(itemBrand.getText());
        i.setPrice(Float.parseFloat(itemPrice.getText()));
        
        System.out.println(i.getID());
        System.out.println(i.getName());
        System.out.println(i.getBrand());
        System.out.println(i.getPrice());
        
        try {
            ps = conn.prepareStatement(query);
            
            ps.setString(1, i.getID());
            ps.setString(2, i.getSupplierID());
            ps.setString(3, i.getName());
            ps.setString(4, i.getBrand());
            ps.setFloat(5, Float.valueOf(itemPrice.getText()));
            
            ps.execute();
            System.out.println("Data added to the database.");
            JOptionPane.showMessageDialog(null, "Item has Added.");
            clearFields();
                       
        } catch (SQLException ex) {
            Logger.getLogger(AddItemFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
                ps.close();
            
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
  
    private void fillSupllierComboBox() throws SQLException  {
        
        conn = DBConnection.getConnection();
        String query = "SELECT SupplierID from Suppliers";
        
        try {    
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            
            while(rs.next()){
                suppliers.add(rs.getString("SupplierID"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddItemFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ps.close();
            rs.close();
        }
    }

    private void clearFields() throws SQLException {
        itemCode.clear();
        supplierID.setValue(null);
        itemName.clear();
        itemBrand.clear();
        itemPrice.clear();
    }    
}
