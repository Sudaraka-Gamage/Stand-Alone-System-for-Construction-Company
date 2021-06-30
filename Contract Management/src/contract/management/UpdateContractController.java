
package contract.management;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.model_contract;

public class UpdateContractController implements Initializable {
    
    public ContractModel contractmodel = new ContractModel();

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
    
    @FXML
    private TextArea txt_siteaddress;
    @FXML
    private DatePicker dte_startdate;
    @FXML
    private Button btn_addcontract;
    @FXML
    private Button btn_estcost;
    @FXML
    private DatePicker dte_enddate;
    @FXML
    private Label isConnected;
    @FXML
    private TextField txt_estcost;
    @FXML
    private TextField txt_contract_id;
    
    private Connection conn = null;   
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private ObservableList<model_contract> data;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        conn = DB.SqliteConnection.Connector();
        data = FXCollections.observableArrayList();
        setCellTable();
        loadDataFromDatabase();
        setCellValueFromTableToTextField();
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

    @FXML
    private void handleUpdateContract(ActionEvent event) throws IOException, SQLException {
        String sql = "Update Contract set site_address=?, start_date=?, end_date=? where contract_id=?";
        
        String site_address = txt_siteaddress.getText();
        
        LocalDate start_date = dte_startdate.getValue();
        String str_start_date = String.valueOf(start_date);
        
        LocalDate end_date = dte_enddate.getValue();
        String str_end_date = String.valueOf(end_date);
        
        String contract_id = txt_contract_id.getText();
        
        try {
            pst = conn.prepareStatement( sql );
            
            pst.setString(1, site_address);
            pst.setString(2, str_start_date);
            pst.setString(3, str_end_date);
            pst.setString(4, contract_id);
            
            int i = pst.executeUpdate();
            if(i==1)
                System.out.println("Data Inserted Successfully");
            
        } catch (SQLException ex) {
            Logger.getLogger(UpdateContractController.class.getName()).log(Level.SEVERE, null, ex);
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
    private void handleDeleteContract(ActionEvent event) throws IOException, SQLException {
        
        String sql = "Delete from Contract where contract_id=?";
        
        String contract_id = txt_contract_id.getText();
        
        try {
            pst = conn.prepareStatement( sql );
            
            pst.setString(1, contract_id);
            
            int i = pst.executeUpdate();
            if(i==1)
                System.out.println("Data Deleted Successfully");
            
        } catch (SQLException ex) {
            Logger.getLogger(UpdateContractController.class.getName()).log(Level.SEVERE, null, ex);
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

    @FXML
    private void changeScreenEstButtonPushed(ActionEvent event) {
        
    }
    
    
    private void setCellValueFromTableToTextField(){
    
        tbl_contract.setOnMouseClicked(e-> {
            model_contract c1 = tbl_contract.getItems().get(tbl_contract.getSelectionModel().getSelectedIndex());
            
            //convert string-start-date into localdate-start-date
            String Str_s_date = c1.getStartDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
            LocalDate lcl_s_date = LocalDate.parse(Str_s_date, formatter);
            
            //convert string-start-date into localdate-start-date
            String Str_e_date = c1.getEndDate();
            LocalDate lcl_e_date = LocalDate.parse(Str_e_date, formatter);
            
            txt_siteaddress.setText(c1.getSiteAddress());
            dte_startdate.setValue(lcl_s_date);
            dte_enddate.setValue(lcl_e_date);
            txt_estcost.setText(c1.getEstCost());
            txt_contract_id.setText(c1.getContractId());
        });
    }
}
