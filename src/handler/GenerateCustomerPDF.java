/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handler;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import util.DBUtil;

/**
 *
 * @author Ahmed
 */
public class GenerateCustomerPDF {

    /**
     *
     * @param writer
     * @param document
     */
    public void onEndPage(PdfWriter writer, Document document) {
        Font labelBold = new Font(FontFamily.COURIER, 16, Font.BOLD);
        PdfContentByte cb = writer.getDirectContent();
        Phrase header = new Phrase("Minart Traders", labelBold);
        ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                header,
                (document.right() - document.left()) / 2 + document.leftMargin(),
                document.top() + 10, 0);

    }

    /**
     *
     * @param customerId
     * @param resultSet
     * @param startPickerDate
     * @param endPickerDates
     * @return
     * @throws IOException
     * @throws DocumentException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public String generatePDFCustomer(String customerId, ResultSet resultSet, String startPickerDate, String endPickerDates) throws IOException, DocumentException, ClassNotFoundException, SQLException {
        GeneratePDF generatePDF = new GeneratePDF();
        ArrayList<String> customerInvoiceData = new ArrayList<String>();
        customerInvoiceData = getCustomerInfoById(customerId);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        int invoiceCount = 0;
        double totalAmount = 0, receivedAmount = 0;
        DecimalFormat df = new DecimalFormat("###.##");
        // Declare filePath.
        String filePath = "C:\\Users/ss/Desktop/InvoicePDF/CustomerReports/" + customerId + ".pdf";

        File file = new File(filePath);

        // if file is exit
        if (file.exists()) {
            // file delete.
            //System.out.println("exit");
            file.delete();
        }

        // step 1
        Document document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));

        // step 3
        document.open();

        onEndPage(writer, document);

        Font labelBold = new Font(FontFamily.COURIER, 9, Font.BOLD);
        Font normal = new Font(FontFamily.COURIER, 9, Font.NORMAL);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        //System.out.println(customerInvoiceData);

        PdfPCell customerInfo = getCustomerInformtion("Customer Info",
                customerInvoiceData.get(0),
                customerInvoiceData.get(1),
                "",
                "");

        table.addCell(customerInfo);

        PdfPCell fromToDate = getCustomerDate("",
                startPickerDate,
                endPickerDates
                );
        
        fromToDate.setPaddingLeft(120);
        table.addCell(fromToDate);
        
        document.add(table);

        table = new PdfPTable(7);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        //table.setSpacingAfter(10);
        table.setWidths(new int[]{1, 2, 2, 5, 3, 2, 2});

        table.addCell(generatePDF.getCell("Sr.#", Element.ALIGN_LEFT, labelBold));
        table.addCell(generatePDF.getCell("InvoiceId.", Element.ALIGN_LEFT, labelBold));
        table.addCell(generatePDF.getCell("Date:", Element.ALIGN_LEFT, labelBold));
        table.addCell(generatePDF.getCell("Customer Name:", Element.ALIGN_LEFT, labelBold));
        table.addCell(generatePDF.getCell("Debit:", Element.ALIGN_LEFT, labelBold));
        table.addCell(generatePDF.getCell("Credit:", Element.ALIGN_LEFT, labelBold));
        table.addCell(generatePDF.getCell("Balance:", Element.ALIGN_LEFT, labelBold));
        
        while (resultSet.next()) {
            invoiceCount++;
            totalAmount = totalAmount + resultSet.getDouble("totalamount");
            receivedAmount = receivedAmount + resultSet.getDouble("receivedamount");
            table.addCell(generatePDF.getCell(String.valueOf(invoiceCount), Element.ALIGN_LEFT, normal));
            table.addCell(generatePDF.getCell(resultSet.getString("id"), Element.ALIGN_LEFT, normal));
            table.addCell(generatePDF.getCell(resultSet.getString("datepicker"), Element.ALIGN_LEFT, normal));
            //ResultSet productDetail = this.getProductDetailById(resultSet.getString("productid"));
            table.addCell(generatePDF.getCell(resultSet.getString("customername"), Element.ALIGN_LEFT, normal));
            
            table.addCell(generatePDF.getCell(String.valueOf(resultSet.getDouble("totalamount")), Element.ALIGN_LEFT, normal));
            table.addCell(generatePDF.getCell(String.valueOf(resultSet.getDouble("receivedamount")), Element.ALIGN_LEFT, normal));
            table.addCell(generatePDF.getCell(String.valueOf(totalAmount - receivedAmount), Element.ALIGN_LEFT, normal));
        }
        
        table.addCell(generatePDF.getCell("", Element.ALIGN_LEFT, normal));
            table.addCell(generatePDF.getCell("", Element.ALIGN_LEFT, normal));
            table.addCell(generatePDF.getCell("", Element.ALIGN_LEFT, normal));
            //ResultSet productDetail = this.getProductDetailById(resultSet.getString("productid"));
            table.addCell(generatePDF.getCell("", Element.ALIGN_LEFT, normal));
            
            table.addCell(generatePDF.getCell(String.valueOf(totalAmount), Element.ALIGN_LEFT, labelBold));
            table.addCell(generatePDF.getCell(String.valueOf(receivedAmount), Element.ALIGN_LEFT, labelBold));
            table.addCell(generatePDF.getCell(String.valueOf(totalAmount - receivedAmount), Element.ALIGN_LEFT, labelBold));
        
        document.add(table);

        table = new PdfPTable(1);
        table.setWidthPercentage(100);
        PdfPCell itemNo = addItemCount(invoiceCount);
        itemNo.setPaddingBottom(10);
        table.addCell(itemNo);

//        PdfPCell amountSection = addtotalAmount(totalAmount, receivedAmount);
//        amountSection.setPaddingLeft(120);
//        amountSection.setPaddingBottom(10);
//        table.addCell(amountSection);

        document.add(table);

        document.close();
        return filePath;
    }

    /**
     *
     * @param totalAmount
     * @param previousBalance
     * @return
     */
    public PdfPCell addtotalAmount(double totalAmount, double previousBalance) {
        Font labelBold = new Font(FontFamily.COURIER, 9, Font.BOLD);
        Font normal = new Font(FontFamily.COURIER, 9, Font.NORMAL);

        PdfPCell cell = new PdfPCell();
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setPaddingTop(0);

        // Bill Info Detail    
        Paragraph billAmountParagraphy = new Paragraph();
        Chunk billAmountChunk = new Chunk("Total Amount: " + String.valueOf(totalAmount), labelBold);
        billAmountParagraphy.add(billAmountChunk);
        cell.addElement(billAmountParagraphy);

        // Previous Amount Detail    
        Paragraph previousBalanceParagraphy = new Paragraph();
        Chunk previousBalanceChunk = new Chunk("Received Balance: " + String.valueOf(previousBalance), labelBold);
        previousBalanceParagraphy.add(previousBalanceChunk);
        cell.addElement(previousBalanceParagraphy);

        return cell;
    }

    /**
     *
     * @param title
     * @param customerCode
     * @param customerName
     * @param customerContact
     * @param customerAddress
     * @return
     */
    public PdfPCell getCustomerInformtion(String title, String customerCode, String customerName, String customerContact, String customerAddress) {

        Font labelBold = new Font(FontFamily.COURIER, 9, Font.BOLD);
        Font normal = new Font(FontFamily.COURIER, 9, Font.NORMAL);

        PdfPCell cell = new PdfPCell();
        cell.setBorder(PdfPCell.NO_BORDER);
        //cell.setPadding(0);
        cell.setPadding(0);
        cell.setFixedHeight(60);

        // Customer Info Label    
        Paragraph customerInfoLabelParagraphy = new Paragraph();
        Chunk customerLabelChunk = new Chunk(title, labelBold);
        customerInfoLabelParagraphy.add(customerLabelChunk);
        cell.addElement(customerInfoLabelParagraphy);
        cell.setPadding(0);
        cell.setFixedHeight(60);

        // Customer Code Label.
        Paragraph customerCodeParagraphy = new Paragraph();
        Chunk customerCodeLabelChunk = new Chunk("Code: ", labelBold);
        Chunk customerCodeChunk = new Chunk(customerCode, normal);
        customerCodeParagraphy.add(customerCodeLabelChunk);
        customerCodeParagraphy.add(customerCodeChunk);
        cell.addElement(new Paragraph(customerCodeParagraphy));
        cell.setPadding(0);
        cell.setFixedHeight(60);

        // Customer Name Label.
        Paragraph customerNameParagraphy = new Paragraph();
        Chunk customerNameLabelChunk = new Chunk("Name: ", labelBold);
        Chunk customerNameChunk = new Chunk(customerName, normal);
        customerNameParagraphy.add(customerNameLabelChunk);
        customerNameParagraphy.add(customerNameChunk);
        cell.addElement(new Paragraph(customerNameParagraphy));
        cell.setPadding(0);
        cell.setFixedHeight(60);

        // Customer Address Label.
        Paragraph customerAddressParagraphy = new Paragraph();
        Chunk customerAddressLabelChunk = new Chunk("Address: ", labelBold);
        Chunk customerAddressChunk = new Chunk(customerAddress, normal);
        customerAddressParagraphy.add(customerAddressLabelChunk);
        customerAddressParagraphy.add(customerAddressChunk);
        cell.addElement(new Paragraph(customerAddressParagraphy));

        // Customer Contact Label.
        Paragraph customerContactParagraphy = new Paragraph();
        Chunk customerContactLabelChunk = new Chunk("Contact: ", labelBold);
        Chunk customerContactChunk = new Chunk(customerContact, normal);
        customerContactParagraphy.add(customerContactLabelChunk);
        customerContactParagraphy.add(customerContactChunk);
        cell.addElement(new Paragraph(customerContactParagraphy));

        return cell;
    }
    
    /**
     *
     * @param title
     * @param customerCode
     * @param customerName
     * @param customerContact
     * @param customerAddress
     * @return
     */
    public PdfPCell getCustomerDate(String title, String startPicker, String endPicker) {

        Font labelBold = new Font(FontFamily.COURIER, 9, Font.BOLD);
        Font normal = new Font(FontFamily.COURIER, 9, Font.NORMAL);

        PdfPCell cell = new PdfPCell();
        cell.setBorder(PdfPCell.NO_BORDER);
        //cell.setPadding(0);
        cell.setPadding(0);
        cell.setFixedHeight(60);

        // Customer Info Label    
        Paragraph customerInfoLabelParagraphy = new Paragraph();
        Chunk customerLabelChunk = new Chunk(title, labelBold);
        customerInfoLabelParagraphy.add(customerLabelChunk);
        cell.addElement(customerInfoLabelParagraphy);
        cell.setPadding(0);
        cell.setFixedHeight(60);

        // Customer Code Label.
        Paragraph fromDateParagraphy = new Paragraph();
        Chunk fromLabelChunk = new Chunk("From: ", labelBold);
        Chunk fromDateChunk = new Chunk(startPicker, normal);
        fromDateParagraphy.add(fromLabelChunk);
        fromDateParagraphy.add(fromDateChunk);
        cell.addElement(new Paragraph(fromDateParagraphy));
        cell.setPadding(0);
        cell.setFixedHeight(60);

        // Customer Name Label.
        Paragraph toDateParagraphy = new Paragraph();
        Chunk toLabelChunk = new Chunk("To: ", labelBold);
        Chunk toDateChunk = new Chunk(endPicker, normal);
        toDateParagraphy.add(toLabelChunk);
        toDateParagraphy.add(toDateChunk);
        cell.addElement(new Paragraph(toDateParagraphy));
        cell.setPadding(0);
        cell.setFixedHeight(60);

        return cell;
    }

    /**
     *
     * @param invoiceCount
     * @return
     */
    public PdfPCell addItemCount(int invoiceCount) {

        Font labelBold = new Font(FontFamily.COURIER, 9, Font.BOLD);
        Font normal = new Font(FontFamily.COURIER, 9, Font.NORMAL);
        Font warrantyLabelBold = new Font(FontFamily.COURIER, 8, Font.BOLD);
        PdfPCell cell = new PdfPCell();
        cell.setBorder(PdfPCell.NO_BORDER);

        // Customer Info Label    
        Paragraph itemNoParagraphy = new Paragraph();
        Chunk itemNoChunk = new Chunk("No of Invoice: " + String.valueOf(invoiceCount), labelBold);
        itemNoParagraphy.add(itemNoChunk);
        cell.addElement(itemNoParagraphy);

        Paragraph blankParagraphy = new Paragraph();
        Chunk blankChunk = new Chunk(" ");
        blankParagraphy.add(blankChunk);
        cell.addElement(blankParagraphy);

        Paragraph oneblankParagraphy = new Paragraph();
        Chunk oneblankChunk = new Chunk(" ");
        oneblankParagraphy.add(oneblankChunk);
        cell.addElement(oneblankParagraphy);

        return cell;
    }

    private ArrayList getCustomerInfoById(String customerId) throws ClassNotFoundException, SQLException {
        ArrayList<String> customerInvoiceData = new ArrayList<String>();

        // Declare database parameter
        DBUtil dbObject = new DBUtil();
        Connection connection = dbObject.DbConnection();
        connection.setAutoCommit(false);
        Statement statementObject = connection.createStatement();

        // Get Customer Data by Id.
        String getCustomerDataById = "Select * from customer where id=" + customerId;
        ResultSet resultSet = statementObject.executeQuery(getCustomerDataById);
        connection.commit();

        if (resultSet.next()) {
            customerInvoiceData.add(String.valueOf(resultSet.getInt("id")));
            customerInvoiceData.add(resultSet.getString("customername"));
            customerInvoiceData.add(resultSet.getString("customercontact"));
            customerInvoiceData.add(String.valueOf(resultSet.getString("customerarea")));
            customerInvoiceData.add(String.valueOf(resultSet.getString("customeremailaddress")));
            customerInvoiceData.add(String.valueOf(resultSet.getString("customeraddress")));
        }
        return customerInvoiceData;
    }
}
