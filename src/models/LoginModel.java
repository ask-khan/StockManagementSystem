/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import util.DBUtil;

/**
 *
 * @author Ahmed
 */
public class LoginModel {
    
    
    /**
    * LoginFunctionality.
    * @param userInformation ArrayList username/ password.
    * @author Ahmed
    */
    
    public boolean loginFunctionality( ArrayList<String>  userInformation ) throws SQLException, ClassNotFoundException{
        
        boolean checkExist = false;
        
        DBUtil dbObject = new DBUtil();
        Statement statementObject = dbObject.DbConnection();
        
        String userLoginSql = "SELECT count(username) As total FROM users WHERE username='" + userInformation.get(0) + "' AND password='" + userInformation.get(1) + "'";
        ResultSet resultSet = statementObject.executeQuery( userLoginSql );
        
        if (resultSet.next()) {
            // count.
            if ( resultSet.getInt("total") == 1 ) {
                checkExist = true;
            } 
        }
        return checkExist;
    }
    
}
