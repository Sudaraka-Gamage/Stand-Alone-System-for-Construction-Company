/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finance;

import DBconnection.DBconnect;
import static finance.Finance.newStage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.log4j.BasicConfigurator;

/**
 * FXML Controller class
 *
 * @author Chathura
 */
public class Report_FXMLController implements Initializable {
    private Stage currentStage = Finance.newStage;
    @FXML
    private ChoiceBox<String> type;
    @FXML
    private ChoiceBox<String> ptype;
    @FXML
    private ChoiceBox<String> contract;
    
    ObservableList<String> types = FXCollections.observableArrayList("Payments", "Utility Bills");
    ObservableList<String> contracts = FXCollections.observableArrayList();
    
    Connection con;
    PreparedStatement prep;
    ResultSet rs;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            fillContratComboBox();
            type.setItems(types);
            ptype.setItems(types);
            contract.setItems(contracts);
        } catch (SQLException ex) {
            Logger.getLogger(Report_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    private void exit(MouseEvent event) {
    }

    @FXML
    private void generateNonReport(ActionEvent event) {
        String typ = type.getValue();
        
        if("Payments".equals(typ)){
            InputStream input = null;
            try {
                BasicConfigurator.configure();
                String path = "C:\\Users\\Chathura\\Documents\\NetBeansProjects\\Finance\\Reports\\payments.jrxml";
                input = new FileInputStream(new File(path));
                JRDesignQuery jrquery = new JRDesignQuery();
                jrquery.setText("Select * from Contract_Payments");
                JasperDesign jasperDesign = JRXmlLoader.load(input);
                jasperDesign.setQuery(jrquery);
                JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBconnect.getConnection());
                //                File pdf = File.createTempFile("D\\aa", ".pdf");
    //                JasperExportManager.exportReportToPdfStream(jasperPrint, new FileOutputStream(pdf));
                JasperViewer viewer = new JasperViewer(jasperPrint, false);
                viewer.setVisible(true);

            } catch (FileNotFoundException ex) {
                Logger.getLogger(Report_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Report_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JRException ex) {
                Logger.getLogger(Report_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    input.close();
                } catch (IOException ex) {
                    Logger.getLogger(Report_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } 
        } else if("Utility Bills".equals(typ)){
            InputStream input = null;
            try {
                BasicConfigurator.configure();
                String path = "C:\\Users\\Chathura\\Documents\\NetBeansProjects\\Finance\\Reports\\utilityBills.jrxml";
                input = new FileInputStream(new File(path));
                JRDesignQuery jrquery = new JRDesignQuery();
                jrquery.setText("Select * from Utility_Bill");
                JasperDesign jasperDesign = JRXmlLoader.load(input);
                jasperDesign.setQuery(jrquery);
                JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBconnect.getConnection());
                //                File pdf = File.createTempFile("D\\aa", ".pdf");
    //                JasperExportManager.exportReportToPdfStream(jasperPrint, new FileOutputStream(pdf));
                JasperViewer viewer = new JasperViewer(jasperPrint, false);
                viewer.setVisible(true);

            } catch (FileNotFoundException ex) {
                Logger.getLogger(Report_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Report_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JRException ex) {
                Logger.getLogger(Report_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    input.close();
                } catch (IOException ex) {
                    Logger.getLogger(Report_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } 
        }
    }

    @FXML
    private void generateReport(ActionEvent event) {
        String typ = ptype.getValue();
        String contractId = contract.getValue();
        
        if("Payments".equals(typ)){
            InputStream input = null;
            try {
                BasicConfigurator.configure();
                String path = "C:\\Users\\Chathura\\Documents\\NetBeansProjects\\Finance\\Reports\\payments.jrxml";
                input = new FileInputStream(new File(path));
                JRDesignQuery jrquery = new JRDesignQuery();
                jrquery.setText("Select * from Contract_Payments where contract_id ='"+contractId+"'");
                JasperDesign jasperDesign = JRXmlLoader.load(input);
                jasperDesign.setQuery(jrquery);
                JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBconnect.getConnection());
                //                File pdf = File.createTempFile("D\\aa", ".pdf");
    //                JasperExportManager.exportReportToPdfStream(jasperPrint, new FileOutputStream(pdf));
                JasperViewer viewer = new JasperViewer(jasperPrint, false);
                viewer.setVisible(true);

            } catch (FileNotFoundException ex) {
                Logger.getLogger(Report_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Report_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JRException ex) {
                Logger.getLogger(Report_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    input.close();
                } catch (IOException ex) {
                    Logger.getLogger(Report_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } 
        } else if("Utility Bills".equals(typ)){
            InputStream input = null;
            try {
                BasicConfigurator.configure();
                String path = "C:\\Users\\Chathura\\Documents\\NetBeansProjects\\Finance\\Reports\\utilityBills.jrxml";
                input = new FileInputStream(new File(path));
                JRDesignQuery jrquery = new JRDesignQuery();
                jrquery.setText("Select * from Utility_Bill where contract_id ='"+contractId+"'");
                JasperDesign jasperDesign = JRXmlLoader.load(input);
                jasperDesign.setQuery(jrquery);
                JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBconnect.getConnection());
                //                File pdf = File.createTempFile("D\\aa", ".pdf");
    //                JasperExportManager.exportReportToPdfStream(jasperPrint, new FileOutputStream(pdf));
                JasperViewer viewer = new JasperViewer(jasperPrint, false);
                viewer.setVisible(true);

            } catch (FileNotFoundException ex) {
                Logger.getLogger(Report_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(Report_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JRException ex) {
                Logger.getLogger(Report_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    input.close();
                } catch (IOException ex) {
                    Logger.getLogger(Report_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } 
        }
    }
    
    public void fillContratComboBox() throws SQLException {
        con = DBconnect.getConnection();
        String query = "Select contract_id from Contracts";
        try {
            prep = con.prepareStatement(query);
            rs = prep.executeQuery();
            
            while(rs.next()){
                contracts.add(rs.getString("contract_id"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddPayment_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            prep.close();
            rs.close();
        }
        
    }
}
