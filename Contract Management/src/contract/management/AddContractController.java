
package contract.management;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddContractController implements Initializable {

    public ContractModel contractmodel = new ContractModel();
    
    @FXML
    private Label isConnected;
    
    @FXML
    private TextArea txt_siteaddress;
    
    @FXML
    private DatePicker dte_startdate;
    
    @FXML
    private Button btn_addcontract;
    
    @FXML
    private DatePicker dte_enddate;
    
    @FXML
    private Button btn_estcost;
    
    @FXML
    private TextField txt_estcost;
    
    @FXML
    private TableView<?> tbl_contract;
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        if(ContractModel.isDbConnected())
            isConnected.setText("Conntected");
        else
            
            isConnected.setText("Not Conntected");
        
        conn = DB.SqliteConnection.Connector();
    }   

    @FXML
    private void handleAddContract(javafx.event.ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        
        String sql = "INSERT INTO Contract( site_address, start_date,end_date) VALUES(?,?,?)";
        
        String site_address = txt_siteaddress.getText();
        
        LocalDate start_date = dte_startdate.getValue();
        String str_start_date = String.valueOf(start_date);
        
        LocalDate end_date = dte_enddate.getValue();
        String str_end_date = String.valueOf(end_date);
        
        //String estimated_cost = txt_estcost.getText();
        
        try {
                
            pst = conn.prepareStatement( sql );
            pst.setString(1, site_address);
            pst.setString(2, str_start_date);
            pst.setString(3, str_end_date);
            //pst.setString(4, estimated_cost);
            
            int i = pst.executeUpdate();
            if(i==1)
                System.out.println("Data Inserted Successfully");
            
            
        } catch (SQLException ex) {
            Logger.getLogger(AddContractController.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            pst.close();
        }
        
        Parent ContractParent = FXMLLoader.load(getClass().getResource("ContractManagement.fxml"));
        Scene ContractScene = new Scene(ContractParent);
        
        Stage window = (Stage)( (Node)event.getSource()).getScene().getWindow();
        window.setScene(ContractScene);
        window.show();
    }
    
    @FXML
    public void changeScreenCancleButtonPushed(ActionEvent event) throws IOException{
        
        Parent ContractParent = FXMLLoader.load(getClass().getResource("ContractManagement.fxml"));
        Scene ContractScene = new Scene(ContractParent);
        
        Stage window = (Stage)( (Node)event.getSource()).getScene().getWindow();
        window.setScene(ContractScene);
        window.show();
    }
    
    public void changeScreenNewCusButtonPushed(ActionEvent event) throws IOException{
        
        Parent AddCustomerParent = FXMLLoader.load(getClass().getResource("AddCustomer.fxml"));
        Scene AddcustomerScene = new Scene(AddCustomerParent);
        
        Stage window = (Stage)( (Node)event.getSource()).getScene().getWindow();
        window.setScene(AddcustomerScene);
        window.show();
    }
    
    @FXML
    public void changeScreenEstButtonPushed(ActionEvent event) throws IOException{
        
        Parent CostEstimationParent = FXMLLoader.load(getClass().getResource("CostEstimation.fxml"));
        Scene CostEstimationScene = new Scene(CostEstimationParent);
        
        Stage window = (Stage)( (Node)event.getSource()).getScene().getWindow();
        window.setScene(CostEstimationScene);
        window.show();
    }
    
}
