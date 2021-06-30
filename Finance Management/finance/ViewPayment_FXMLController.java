/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finance;

import models.Payment;
import DBconnection.DBconnect;
import static finance.Finance.newStage;
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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Chathura
 */
public class ViewPayment_FXMLController implements Initializable {
    public static String paymentID;
    Connection con;
    PreparedStatement prep;
    ResultSet rs;
    
    Payment payment=null;
    
    private Stage currentStage = Finance.newStage;
    
    
    @FXML
    private TableView<Payment> table;
    @FXML
    private TableColumn<Payment, String> payment_id;
    @FXML
    private TableColumn<Payment, String> contract_id;
    @FXML
    private TableColumn<Payment, String> installment_num;
    @FXML
    private TableColumn<Payment, String> date;
    @FXML
    private TableColumn<Payment, Float> installment_value;
    
    public ObservableList<Payment> paymentsList = FXCollections.observableArrayList(displayAll()); 
    private TextField searchConract;
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        payment_id.setCellValueFactory(new PropertyValueFactory<Payment, String>("payment_id"));
        contract_id.setCellValueFactory(new PropertyValueFactory<Payment, String>("contract"));
        installment_num.setCellValueFactory(new PropertyValueFactory<Payment, String>("installment_num"));
        date.setCellValueFactory(new PropertyValueFactory<Payment, String>("payment_date"));
        installment_value.setCellValueFactory(new PropertyValueFactory<Payment, Float>("payment_value"));
        
        table.setItems(paymentsList);
        table.setOnMousePressed((MouseEvent event) -> {
            paymentID = table.getSelectionModel().getSelectedItem().getPayment_id();
            System.out.println(paymentID);
            FXMLLoader Loader = new FXMLLoader();
            Loader.setLocation(getClass().getResource("UpdatePayment_FXML.fxml"));
            try {
                Loader.load();
                
            } catch (IOException ex) {
                Logger.getLogger(ViewPayment_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
            UpdatePayment_FXMLController contractId = Loader.getController();
            //contractId.setContractID(paymentID);
            
            Parent p = Loader.getRoot();
            Scene scene = new Scene(p);
            currentStage.setTitle("Finance");
            currentStage.setScene(scene);
            currentStage.show();
        });
    } 

    public ArrayList<Payment> displayAll() {
        ArrayList<Payment> paymentList = new ArrayList<>();
        
        try {
            con=DBconnect.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ViewPayment_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String query="SELECT * FROM Contract_Payments";
        
        try {
            prep = con.prepareStatement(query);
            rs=prep.executeQuery();
            
            while(rs.next()){
                payment = new Payment();
                payment.setPayment_id(rs.getString(1));
                payment.setContract(rs.getString(2));
                payment.setInstallment_num(rs.getString(3));
                payment.setPayment_date(rs.getString(4));
                payment.setPayment_value(rs.getFloat(5));
                
                paymentList.add(payment);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ViewPayment_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        return paymentList;
    }

    @FXML
    private void home(MouseEvent event) {
    }

    @FXML
    private void finance(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Finance_FXML.fxml"));
        
        Scene scene = new Scene(root);
        currentStage.setTitle("Finance");
        newStage.setScene(scene);
        newStage.show();
    }

    @FXML
    private void contractPayments(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Payments_FXML.fxml"));
        
        Scene scene = new Scene(root);
        currentStage.setTitle("Finance");
        newStage.setScene(scene);
        newStage.show();
    }

    @FXML
    private void exit(MouseEvent event) {
    }

    @FXML
    private void addPayment(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddPayment_FXML.fxml"));
        
        Scene scene = new Scene(root);
        currentStage.setTitle("Finance");
        newStage.setScene(scene);
        newStage.show();
    }
    
     
            
        
}
