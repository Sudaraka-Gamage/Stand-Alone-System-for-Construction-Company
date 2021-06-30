/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Gayan M
 */
public class item {
    
    //private static String prefix = "IID0";
    //private static int iterate = 1;
    private String ID;
    private String SupplierID;
    private String Name;
    private String Brand;
    private float Price;
    
    public item(String ID, String SupplierID, String Name, String Brand, float Price){
        this.ID = ID;
        this.SupplierID = SupplierID;
        this.Name = Name;
        this.Brand = Brand;
        this.Price = Price;
    }

    public item() {}

    public String getID() {
        return ID;
    }
    
    public void setID(String ID){
        this.ID = ID;
    }
    
    /*public void generateID(){
        this.ID = item.prefix + item.iterate;
        item.iterate++;
    }*/
    
    public String getSupplierID(){
        return SupplierID;
    }
    
    public void setSupplierID(String SupplierID){
        this.SupplierID = SupplierID;
    }
    
    public String getName(){
        return Name;
    }     
   
    public void setName(String Name){
        this.Name = Name;
    }
    
    public String getBrand(){
        return Brand;
    }
 
    public void setBrand(String Brand){
        this.Brand = Brand;
    }
    
    public float getPrice(){
        return Price;
    }
 
    public void setPrice(float Price){
        this.Price = Price;
    }
}
