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
import util.DBUtil;

/**
 *
 * @author Ahmed
 */
public class CustomerViewModel {

    /**
     *
     * @param customerId
     * @return @throws ClassNotFoundException
     * @throws SQLException
     */
    public ResultSet getCustomerInvoiceData(String customerId) throws ClassNotFoundException, SQLException {
        boolean checkExist = false;
        // Initialize Db Instance.
        DBUtil dbObject = new DBUtil();
        Connection connection = dbObject.DbConnection();
        connection.setAutoCommit(false);
        Statement statementObject = connection.createStatement();
        // User Login Query 
        String getCustomerView = "SELECT \n"
                + "	invoice.id,\n"
                + "    invoice.customerid,\n"
                + "    count( invoiceproduct.id ) as invoicecount,\n"
                + "    invoice.totalamount,\n"
                + "    invoice.receivedamount,\n"
                + "    invoice.modifieddate::text\n"
                + "FROM \n"
                + "	invoice\n"
                + "INNER JOIN invoiceproduct ON invoice.id = (invoiceproduct.invoiceid)::int\n"
                + "WHERE customerid = '" + customerId + "'\n"
                + "GROUP BY invoice.id;";
        //System.out.print( getCustomerView );
        ResultSet resultSet = statementObject.executeQuery(getCustomerView);

        return resultSet;
    }
}
