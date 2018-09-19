/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class Configuration {

    // Database configuration.
    private static final String DATABASE_URL = "jdbc:postgresql://127.0.0.1:5432/StockManagementSystem@Live";
    private static final String USER_NAME = "postgres";
    private static final String PASSWORD = "admin";
    
    // File path.
    private static final String FOLDER_PATH = "C:\\Users/Administrator/Desktop/StockManagement/";
    //private static final String FOLDER_PATH = "C:\\Users/Hp/Desktop/StockManagement/";
    
    // Database configuration function.
    public List databaseConfiguration() {
        List databaseConfig = new ArrayList();

        databaseConfig.add(DATABASE_URL);
        databaseConfig.add(USER_NAME);
        databaseConfig.add(PASSWORD);

        return databaseConfig;
    }
    
    public String getFolderPath() {
        return FOLDER_PATH;
    }
    
}
