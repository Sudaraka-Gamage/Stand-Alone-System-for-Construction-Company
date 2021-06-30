/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Chathura
 */
public class Payment {
    private String payment_id;
    private String contract;
    private String installment_num;
    private String payment_date;
    private float payment_value;
    

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getInstallment_num() {
        return installment_num;
    }

    public void setInstallment_num(String installment_num) {
        this.installment_num = installment_num;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(String payment_date) {
        this.payment_date = payment_date;
    }

    public float getPayment_value() {
        return payment_value;
    }

    public void setPayment_value(float payment_value) {
        this.payment_value = payment_value;
    }
    
}
