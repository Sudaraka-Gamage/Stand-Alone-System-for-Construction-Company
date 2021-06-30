package contract.management;

import java.sql.*;

public class ContractModel{
    
    static Connection connection;

    public ContractModel(){
    
        connection = SqliteConnection.Connector();

        if(connection == null){ 
            System.out.println("connection not successful");
            System.exit(1);
        }   
    }

    public static boolean isDbConnected(){
    
        try{
            return !connection.isClosed();

        }catch(SQLException e){

            e.printStackTrace();
            return false;  
        }
    }  
}