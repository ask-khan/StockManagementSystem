/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
public class DBUtil {
    
    private Connection connection = null;
    private Statement state = null;
    private final String url = "jdbc:postgresql://127.0.0.1:5432/DisturbutionManagmentSystem";
    private final String user = "postgres";
    private final String password = "admin";
    
    public Statement DbConnection () throws ClassNotFoundException {
        try {
            Class.forName("org.postgresql.Driver");
            // getConnection.    
            connection = DriverManager.getConnection( url, user, password);
            connection.setAutoCommit(false);
            state = connection.createStatement();
            
        } catch (SQLException e) {

            e.printStackTrace();
            
        }
        return state;
    }
    
}
