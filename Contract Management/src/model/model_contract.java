
package model;

import java.sql.Date;
import java.time.LocalDate;

public class model_contract {
    
    private String contractId;
    private String customerId; 
    private String siteAddress;
    private String startDate;
    private String endDate;
    private String completed;
    private String estCost;
    private String warrPeriod;

    public model_contract(String contractId, String customerId, String siteAddress, String startDate, String endDate, String completed, String estCost, String warrPeriod) {
        this.contractId = contractId;
        this.customerId = customerId;
        this.siteAddress = siteAddress;
        this.startDate = startDate;
        this.endDate = endDate;
        this.completed = completed;
        this.estCost = estCost;
        this.warrPeriod = warrPeriod;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getSiteAddress() {
        return siteAddress;
    }

    public void setSiteAddress(String siteAddress) {
        this.siteAddress = siteAddress;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    public String getEstCost() {
        return estCost;
    }

    public void setEstCost(String estCost) {
        this.estCost = estCost;
    }

    public String getWarrPeriod() {
        return warrPeriod;
    }

    public void setWarrPeriod(String warrPeriod) {
        this.warrPeriod = warrPeriod;
    }

    
}
