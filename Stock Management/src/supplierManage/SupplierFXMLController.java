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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.supplier;
import itemManage.Stock;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * FXML Controller class
 *
 * @author Gayan M
 */
public class SupplierFXMLController implements Initializable {

    private final Stage currentStage = Stock.PrimaryStage;

    Connection conn;
    PreparedStatement ps;
    ResultSet rs;
    
    supplier supplier = null;
    
    @FXML
    private TableView<supplier> table;

    @FXML
    private TableColumn<supplier, String> supplierID;

    @FXML
    private TableColumn<supplier, String> name;

    @FXML
    private TableColumn<supplier, Integer> phoneNo;

    @FXML
    private TableColumn<supplier, String> address;

    @FXML
    private TableColumn<supplier, String> email;

    @FXML
    private TextField searchSupplier2;

    @FXML
    private Button search;

    @FXML
    private TextField searchSupplier;

    @FXML
    private Button updateSupplier;

    @FXML
    private Button addSupplier;

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
       
        addSupplier.setOnAction( e->{
        
            try {
                Parent root;
                root = FXMLLoader.load(getClass().getResource("AddSupplierFXML.fxml"));
                Scene scene = new Scene(root);
            
                currentStage.setScene(scene);
                currentStage.show();
                
            } catch (IOException ex) {
                Logger.getLogger(SupplierFXMLController.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(SupplierFXMLController.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(SupplierFXMLController.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(SupplierFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        Exit.setOnAction(e-> {
            alert.setContentText("Do you want to exit?");
            alert.setTitle("Exit");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                System.exit(0);
            } else {
                Parent root;

                try {
                    root = FXMLLoader.load(getClass().getResource("SupplierFXML.fxml"));
                    Scene scene = new Scene(root);

                    currentStage.setTitle("NTDB");
                    currentStage.setScene(scene);
                    currentStage.show();
                } catch (IOException ex) {
                    Logger.getLogger(SupplierFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public ArrayList<supplier> dispalyAll() {
        ArrayList<supplier> itemList = new ArrayList<>();
        
        try {
            conn = DBConnection.getConnection();
                    
        } catch (SQLException ex) {
            Logger.getLogger(SupplierFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String query = "SELECT * FROM suppliers";
        
        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                supplier = new supplier();
                supplier.setSupplierID(rs.getString(1));
                supplier.setSupplierName(rs.getString(2));
                supplier.setPhoneNo(rs.getInt(3));
                supplier.setAddress(rs.getString(4));
                supplier.setEmail(rs.getString(5));
                
                itemList.add(supplier);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SupplierFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return itemList;
    }
}
