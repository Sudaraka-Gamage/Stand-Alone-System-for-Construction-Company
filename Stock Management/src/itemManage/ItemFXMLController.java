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
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author Gayan M
 */
public class ItemFXMLController implements Initializable {

    private Stage currentStage = Stock.PrimaryStage;

    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    item i = null;

    public static String search_item;
    public static String search_item2;

    @FXML
    private TableView<item> table;

    @FXML
    private TableColumn<item, String> itemID;

    @FXML
    private TableColumn<item, String> itemName;

    @FXML
    private TableColumn<item, String> itemBrand;

    @FXML
    private TableColumn<item, Float> itemPrice;

    @FXML
    private TableColumn<item, String> supplierID;

    @FXML
    private TextField searchItem2;

    @FXML
    private Button search;

    @FXML
    private Button addItem;

    @FXML
    private TextField searchItem;

    @FXML
    private Button updateItem;

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

    public ObservableList<item> supplierList = FXCollections.observableArrayList(displayAll());

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	
    	
    	
        itemID.setCellValueFactory(new PropertyValueFactory<item, String>("ID"));
        itemName.setCellValueFactory(new PropertyValueFactory<item, String>("Name"));
        itemBrand.setCellValueFactory(new PropertyValueFactory<item, String>("Brand"));
        itemPrice.setCellValueFactory(new PropertyValueFactory<item, Float>("Price"));
        supplierID.setCellValueFactory(new PropertyValueFactory<item, String>("SupplierID"));

        table.setItems(supplierList);
        
       
        
        
        
        
        
        
        
        
        
        
        
        
        
        updateItem.setOnAction(e-> {
            search_item = searchItem.getText();
            System.out.println(search_item);
            try {        
                Parent parent = FXMLLoader.load(getClass().getResource("UpdateItemFXML.fxml"));
                Scene scene = new Scene(parent);
        
                currentStage.setScene(scene);
                currentStage.show();
            } catch (IOException ex) {
                Logger.getLogger(ItemFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        search.setOnAction(e-> {
            search_item2 = searchItem2.getText();
            
            System.out.println(search_item2);
            
            try {
                Parent parent = FXMLLoader.load(getClass().getResource("SearchItemFXML.fxml"));
                Scene scene = new Scene(parent);
                
                currentStage.setScene(scene);
                currentStage.show();
                
            } catch (IOException ex) {
                Logger.getLogger(ItemFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        addItem.setOnAction( e-> {
            try {
                Parent root;
                root = FXMLLoader.load(getClass().getResource("AddItemFXML.fxml"));
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
        
        Exit.setOnAction(e-> {
            alert.setContentText("Do you want to exit?");
            alert.setTitle("Exit");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                System.exit(0);
            } else {
                Parent root;

                try {
                    root = FXMLLoader.load(getClass().getResource("ItemFXML.fxml"));
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

   
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public ArrayList<item> displayAll() {
        ArrayList<item> itemsList = new ArrayList<>();

        try {
            conn = DBConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ItemFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

        String query = "SELECT * FROM item";

        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
            	
                i = new item();
                i.setID(rs.getString(1));
                i.setSupplierID(rs.getString(2));
                i.setName(rs.getString(3));
                i.setBrand(rs.getString(4));
                i.setPrice(rs.getFloat(5));

                itemsList.add(i);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItemFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return itemsList;
    }
}
