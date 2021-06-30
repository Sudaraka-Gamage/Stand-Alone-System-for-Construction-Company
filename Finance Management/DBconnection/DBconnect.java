/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Chathura
 */
public class DBconnect {
		public static Connection getConnection() throws SQLException{
		Connection connection = null;
		if(connection==null || connection.isClosed()) {
			try {
				Class.forName("org.sqlite.JDBC");
				connection=DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Chathura\\Documents\\NetBeansProjects\\Finance\\Database\\Finance.db");		

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return connection;
	}
}

