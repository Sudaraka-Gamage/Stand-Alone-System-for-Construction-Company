
package contract.management;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.model_contract;


public class ContractManagementController implements Initializable {
    
    public ContractModel contractmodel = new ContractModel();
    
    @FXML
    private Label isConnected;
    @FXML
    private TableView<model_contract> tbl_contract;
    @FXML
    private TableColumn<?, ?> clm_contract_id;
    @FXML
    private TableColumn<?, ?> clm_customer_id;
    @FXML
    private TableColumn<?, ?> clm_site_address;
    @FXML
    private TableColumn<?, ?> clm_start_date;
    @FXML
    private TableColumn<?, ?> clm_end_date;
    @FXML
    private TableColumn<?, ?> clm_completed;
    @FXML
    private TableColumn<?, ?> clm_estimated_cost;
    @FXML
    private TableColumn<?, ?> clm_warranty_period;
    
    private Connection conn = null;   
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private ObservableList<model_contract> data;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        if(ContractModel.isDbConnected())
            isConnected.setText("Conntected");
        else
            
            isConnected.setText("Not Conntected");
        
        conn = DB.SqliteConnection.Connector();
        data = FXCollections.observableArrayList();
        setCellTable();
        loadDataFromDatabase();
    }
    
    private void setCellTable(){
        
        clm_contract_id.setCellValueFactory( new PropertyValueFactory<>("contractId") );
        clm_customer_id.setCellValueFactory( new PropertyValueFactory<>("customerId") );
        clm_site_address.setCellValueFactory( new PropertyValueFactory<>("siteAddress") );
        clm_start_date.setCellValueFactory( new PropertyValueFactory<>("startDate") );
        clm_end_date.setCellValueFactory( new PropertyValueFactory<>("endDate") );
        clm_completed.setCellValueFactory( new PropertyValueFactory<>("completed") );
        clm_estimated_cost.setCellValueFactory( new PropertyValueFactory<>("estCost") );
        clm_warranty_period.setCellValueFactory( new PropertyValueFactory<>("warrPeriod") ); 
    }
    
    private void loadDataFromDatabase(){
    
        try {
            pst = conn.prepareStatement( "select * from Contract" );
            rs = pst.executeQuery();
            while(rs.next()){
                data.add(new model_contract(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6), rs.getString(7), rs.getString(8)));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ContractManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tbl_contract.setItems(data);
    }
    
    public void changeScreenButtonPushed(ActionEvent event) throws IOException{
        
        Parent addContractParent = FXMLLoader.load(getClass().getResource("AddContract.fxml"));
        Scene addContractScene = new Scene(addContractParent);
        
        Stage window = (Stage)( (Node)event.getSource()).getScene().getWindow();
        window.setScene(addContractScene);
        window.show();
    }
    
    public void changeScreenUpdateButtonPushed(ActionEvent event) throws IOException{
        
        Parent updateContractParent = FXMLLoader.load(getClass().getResource("UpdateContract.fxml"));
        Scene updateContractScene = new Scene(updateContractParent);
        
        Stage window = (Stage)( (Node)event.getSource()).getScene().getWindow();
        window.setScene(updateContractScene);
        window.show();
    }
    
}
