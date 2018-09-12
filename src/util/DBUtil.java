/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import config.Configuration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DBUtil {

    private Connection connection = null;
    private Statement state = null;

    public Connection DbConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        // Configuration Object.
        Configuration configurationObject = new Configuration();
        List databaseInfo = configurationObject.databaseConfiguration();
        // getConnection.
        connection = DriverManager.getConnection( String.valueOf(databaseInfo.get(0)) , String.valueOf(databaseInfo.get(1)), String.valueOf(databaseInfo.get(2)));
        return connection;
    }

}
