/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import util.DBUtil;

/**
 *
 * @author Ahmed
 */
public class PrintModel {

    /**
     *
     * @param PrintName
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ResultSet insertPrint(String PrintName) throws SQLException, ClassNotFoundException {
        // Initialize Db Instance.
        DBUtil dbObject = new DBUtil();
        Connection connection = dbObject.DbConnection();
        connection.setAutoCommit(false);
        Statement statementObject = connection.createStatement();
        ResultSet getPrintName = null;

        // get total count of print setting.
        String getAllPrintSetting = "Select count(*) as total from printsetting;";
        ResultSet getPrintCount = statementObject.executeQuery(getAllPrintSetting);

        // if next is not null and count is greater than zero.
        if (getPrintCount.next() && getPrintCount.getInt("total") > 0) {
            // delete print setting.
            String deletePrinter = "DELETE FROM printsetting;";
            statementObject.executeUpdate(deletePrinter);
        }

        // Insert print setting query.
        String insertPrintname = "Insert into printsetting ( printname, createddate, modifieddate) values( '" + PrintName + "',current_timestamp, current_timestamp ) RETURNING *;";
        getPrintName = statementObject.executeQuery(insertPrintname);

        connection.commit();
        return getPrintName;
    }

    /**
     *
     * @return @throws SQLException
     * @throws ClassNotFoundException
     */
    public String getPrintName() throws SQLException, ClassNotFoundException {
        String printName = "";
        // Initialize Db Instance.
        DBUtil dbObject = new DBUtil();
        Connection connection = dbObject.DbConnection();
        connection.setAutoCommit(false);
        Statement statementObject = connection.createStatement();
        // get all print name query.
        String getAllPrintSetting = "Select printname from printsetting;";
        ResultSet getPrintData = statementObject.executeQuery(getAllPrintSetting);
        // if print data is next.
        if (getPrintData.next()) {
            printName = getPrintData.getString("printname");
        }
        //System.out.println( printName );
        return printName;

    }

}
