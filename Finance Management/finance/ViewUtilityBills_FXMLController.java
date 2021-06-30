
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finance;

import models.UtilityBill;
import DBconnection.DBconnect;
import static finance.ViewPayment_FXMLController.paymentID;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Chathura
 */
public class ViewUtilityBills_FXMLController implements Initializable {
    public static String billID;
    Connection con;
    PreparedStatement prep;
    ResultSet rs;
    
    UtilityBill ub=null;
    
    private Stage currentStage = Finance.newStage;
    @FXML
    private AnchorPane bill_id;
    @FXML
    private TableView<UtilityBill> bill_table;
    @FXML
    private TableColumn<UtilityBill, String> billId;
    @FXML
    private TableColumn<UtilityBill, String> contract_id;
    @FXML
    private TableColumn<UtilityBill, String> bill_type;
    @FXML
    private TableColumn<UtilityBill, String> bill_month;
    @FXML
    private TableColumn<UtilityBill, String> due_date;
    @FXML
    private TableColumn<UtilityBill, String> paid_date;
    @FXML
    private TableColumn<UtilityBill, Float> charges;
    private TextField search_bill_id;
    
     public ObservableList<UtilityBill> billList = FXCollections.observableArrayList(displayAll()); 
    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        billId.setCellValueFactory(new PropertyValueFactory<UtilityBill, String>("bill_id"));
        contract_id.setCellValueFactory(new PropertyValueFactory<UtilityBill, String>("contract_id"));
        bill_type.setCellValueFactory(new PropertyValueFactory<UtilityBill, String>("bill_type"));
        bill_month.setCellValueFactory(new PropertyValueFactory<UtilityBill, String>("bill_month"));
        due_date.setCellValueFactory(new PropertyValueFactory<UtilityBill, String>("due_date"));
        paid_date.setCellValueFactory(new PropertyValueFactory<UtilityBill, String>("paid_date"));
        charges.setCellValueFactory(new PropertyValueFactory<UtilityBill, Float>("charges"));
        
        bill_table.setItems(billList);
        bill_table.setOnMousePressed((MouseEvent event) -> {
            billID = bill_table.getSelectionModel().getSelectedItem().getBill_id();
            
            FXMLLoader Loader = new FXMLLoader();
            Loader.setLocation(getClass().getResource("UpdateUtilityBill_FXML.fxml"));
            try {
                Loader.load();
                
            } catch (IOException ex) {
                Logger.getLogger(ViewPayment_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
            UpdateUtilityBill_FXMLController BillId = Loader.getController();
            BillId.setBillID(billID);
            
            Parent p = Loader.getRoot();
            Scene scene = new Scene(p);
            currentStage.setTitle("Finance");
            currentStage.setScene(scene);
            currentStage.show();
        });
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
    private void utilityBill(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("UtilityBills_FXML.fxml"));
        
        Scene scene = new Scene(root);
        currentStage.setTitle("Finance");
        currentStage.setScene(scene);
        currentStage.show();
    }

    @FXML
    private void addutilityBill(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddUtilityBill_FXML.fxml"));
        
        Scene scene = new Scene(root);
        currentStage.setTitle("Finance");
        currentStage.setScene(scene);
        currentStage.show();
    }

    @FXML
    private void exit(MouseEvent event) {
    }
    
    public ArrayList<UtilityBill> displayAll() {
        ArrayList<UtilityBill> billList = new ArrayList<>();
        
        try {
            con=DBconnect.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ViewPayment_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String query="SELECT * FROM Utility_Bill";
        
        try {
            prep = con.prepareStatement(query);
            rs=prep.executeQuery();
            
            while(rs.next()){
                ub = new UtilityBill();
                ub.setBill_id(rs.getString(1));
                ub.setContract_id(rs.getString(2));
                ub.setBill_type(rs.getString(3));
                ub.setBill_month(rs.getString(4));
                ub.setDue_date(rs.getString(5));
                ub.setPaid_date(rs.getString(6));
                ub.setCharges(rs.getFloat(7));
                
                billList.add(ub);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ViewPayment_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        return billList;
    }
}
