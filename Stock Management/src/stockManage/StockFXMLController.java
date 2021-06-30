/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockManage;

import itemManage.ItemFXMLController;
import itemManage.Stock;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventType;
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

/**
 * FXML Controller class
 *
 * @author Gayan M
 */
public class StockFXMLController implements Initializable {

    private Stage currentStage = Stock.PrimaryStage;

    Connection conn;
    PreparedStatement ps;
    ResultSet rs;
    
    @FXML
    private TableView<?> table;

    @FXML
    private TableColumn<?, ?> itemCode;

    @FXML
    private TableColumn<?, ?> unitPrice;

    @FXML
    private TableColumn<?, ?> quantity;

    @FXML
    private TableColumn<?, ?> totalCost;

    @FXML
    private TextField stockItem_search;

    @FXML
    private Button search_btn;

    @FXML
    private Button home;

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
        
        stockManagement.setOnAction( e-> {
           
            try {
                Parent root;
                root = FXMLLoader.load(getClass().getResource("StockFXML.fxml"));
                Scene scene = new Scene(root);
                
                currentStage.setScene(scene);
                currentStage.show();
                
            } catch (IOException ex) {
                Logger.getLogger(StockFXMLController.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(StockFXMLController.class.getName()).log(Level.SEVERE, null, ex);
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
                    root = FXMLLoader.load(getClass().getResource("StockFXML.fxml"));
                    Scene scene = new Scene(root);

                    currentStage.setTitle("NTDB");
                    currentStage.setScene(scene);
                    currentStage.show();
                    
                } catch (IOException ex) {
                    Logger.getLogger(StockFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }    
}
