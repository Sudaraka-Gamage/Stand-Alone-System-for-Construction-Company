package contract.management;

import java.sql.*;

public class SqliteConnection {
    
    Connection conn = null;
    public static Connection Connector(){
    
            try{
            
                Class.forName("org.sqlite.JDBC");
                Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Savindu\\Desktop\\ITP\\db\\Contractdb.sqlite");
                return conn;
                                    
            }catch(Exception e){

            	return null;
            }
    }
}