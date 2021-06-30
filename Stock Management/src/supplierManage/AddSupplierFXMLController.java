/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supplierManage;

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
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import model.supplier;

/**
 * FXML Controller class
 *
 * @author Gayan M
 */
public class AddSupplierFXMLController implements Initializable {

    private Stage currentStage = itemManage.Stock.PrimaryStage;
    
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;
    
    supplier supplier;
    
    @FXML
    private TextField supplierID;

    @FXML
    private TextField phoneNo;

    @FXML
    private TextField email;

    @FXML
    private Button saveSuppliers;

    @FXML
    private Button cancel;

    @FXML
    private TextArea address;

    @FXML
    private TextField supplierName;

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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        stockManagement.setOnAction(e-> {
            
            try {
                Parent root;
                root = FXMLLoader.load(getClass().getResource("/stockManage/StockFXML.fxml"));
                Scene scene = new Scene(root);
                
                currentStage.setScene(scene);
                currentStage.show();
                
            } catch (IOException ex) {
                Logger.getLogger(AddSupplierFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        itemManagement.setOnAction( e->{
            
            try {
                Parent root;
                root = FXMLLoader.load(getClass().getResource("/itemManage/ItemFXML.fxml"));
                Scene scene = new Scene(root);

                currentStage.setScene(scene);
                currentStage.show();

            } catch (IOException ex) {
                Logger.getLogger(AddSupplierFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        supplierManagement.setOnAction( e-> {
            
            try {
                Parent root;
                root = FXMLLoader.load(getClass().getResource("SupplierFXML.fxml"));
                Scene scene =  new Scene(root);
                
                currentStage.setScene(scene);
                currentStage.show();
                
            } catch (IOException ex) {
                Logger.getLogger(AddSupplierFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        Exit.setOnAction(e -> {
            alert.setContentText("Do you want to exit?");
            alert.setTitle("Exit");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                System.exit(0);
            } else {
                Parent root;

                try {
                    root = FXMLLoader.load(getClass().getResource("AddSupplierFXML.fxml"));
                    Scene scene = new Scene(root);

                    currentStage.setTitle("NTDB");
                    currentStage.setScene(scene);
                    currentStage.show();
                } catch (IOException ex) {
                    Logger.getLogger(AddSupplierFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    @FXML
    public void insertSupplier(ActionEvent event) throws SQLException {

        try {
            conn = DBConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(AddSupplierFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String query = "INSERT INTO suppliers VALUES(?, ?, ?, ?, ?)";
        
        supplier = new supplier();
        
        supplier.setSupplierID(supplierID.getText());
        supplier.setSupplierName(supplierName.getText());
        supplier.setPhoneNo(Integer.parseInt(phoneNo.getText()));
        supplier.setAddress(address.getText());
        supplier.setEmail(email.getText());
        
        System.out.println(supplier.getSupplierID());
        System.out.println(supplier.getSupplierName());
        System.out.println(supplier.getPhoneNo());
        System.out.println(supplier.getAddress());
        System.out.println(supplier.getEmail());
        
        try {
            ps = conn.prepareStatement(query);
        
            ps.setString(1, supplier.getSupplierID());
            ps.setString(2, supplier.getSupplierName());
            ps.setInt(3, Integer.valueOf(supplier.getPhoneNo()));
            ps.setString(4, supplier.getAddress());
            ps.setString(5, supplier.getEmail());
            
            ps.execute();
            System.out.println("Data added to the database.");
            JOptionPane.showMessageDialog(null, "Supplier has added.");
            clearFields();
            
        } catch (SQLException ex) {
            Logger.getLogger(AddSupplierFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ps.close();
        }
        Parent root;
        
        try {
            root = FXMLLoader.load(getClass().getResource("SupplierFXML.fxml"));
            Scene scene = new Scene(root);
            
            currentStage.setTitle("NTDB");
            currentStage.setScene(scene);
            currentStage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(AddSupplierFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void clearFields() throws SQLException {
        supplierID.clear();
        supplierName.clear();
        phoneNo.clear();
        address.clear();
        email.clear();
    }    
}
