/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handler;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import config.Configuration;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author Ahmed
 */
public class GenerateSalesPDF {

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
     * @param startDate
     * @param endDate
     * @param resultSet
     * @return
     * @throws IOException
     * @throws DocumentException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public String generatePDFSales(String startDate, String endDate, ResultSet resultSet) throws IOException, DocumentException, ClassNotFoundException, SQLException {
        GeneratePDF generatePDF = new GeneratePDF();
        ArrayList<String> customerInvoiceData = new ArrayList<String>();
        //customerInvoiceData = getCustomerInfoById(customerId);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        int invoiceCount = 0;
        double totalAmount = 0, receivedAmount = 0, profitAmount = 0;
        DecimalFormat df = new DecimalFormat("###.##");
        
        // Declare Configuration Object.
        Configuration configurationObject = new Configuration();
        // Declare filePath.
        String filePath = configurationObject.getFolderPath() + "SalesReports/" + startDate + "-" + endDate + ".pdf";

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

        Font labelBold = new Font(Font.FontFamily.COURIER, 9, Font.BOLD);
        Font normal = new Font(Font.FontFamily.COURIER, 9, Font.NORMAL);

        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100);
        //System.out.println(customerInvoiceData);
        // declare seller.
        PdfPCell seller = setSalesInformation("Sales Information", startDate, endDate, String.valueOf(now));
        
        table.addCell(seller);
        
        document.add(table);
        
        
        table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        //table.setSpacingAfter(10);
        table.setWidths(new int[]{1, 2, 5, 2, 4, 3});

        table.addCell(generatePDF.getCell("Sr.#", Element.ALIGN_LEFT, labelBold));
        table.addCell(generatePDF.getCell("InvoiceId.", Element.ALIGN_LEFT, labelBold));
        table.addCell(generatePDF.getCell("Customer Name:", Element.ALIGN_LEFT, labelBold));
        table.addCell(generatePDF.getCell("Date Picker:", Element.ALIGN_LEFT, labelBold));
        table.addCell(generatePDF.getCell("Total Amount:", Element.ALIGN_LEFT, labelBold));
        table.addCell(generatePDF.getCell("Received Amount:", Element.ALIGN_LEFT, labelBold));

        while (resultSet.next()) {
            invoiceCount++;
            profitAmount = profitAmount + resultSet.getDouble("profit");
            totalAmount = totalAmount + resultSet.getDouble("totalamount");
            receivedAmount = receivedAmount + resultSet.getDouble("receivedamount");
            table.addCell(generatePDF.getCell(String.valueOf(invoiceCount), Element.ALIGN_LEFT, normal));
            table.addCell(generatePDF.getCell(resultSet.getString("id"), Element.ALIGN_LEFT, normal));
            //ResultSet productDetail = this.getProductDetailById(resultSet.getString("productid"));
            table.addCell(generatePDF.getCell(resultSet.getString("customername"), Element.ALIGN_LEFT, normal));
            table.addCell(generatePDF.getCell(resultSet.getString("datepicker"), Element.ALIGN_LEFT, normal));
            table.addCell(generatePDF.getCell(String.valueOf(resultSet.getDouble("totalamount")), Element.ALIGN_LEFT, normal));
            table.addCell(generatePDF.getCell(String.valueOf(resultSet.getDouble("receivedamount")), Element.ALIGN_LEFT, normal));

        }

        document.add(table);
        
        table = new PdfPTable(2);
        table.setWidthPercentage(100);
        PdfPCell itemNo = addItemCount(invoiceCount, profitAmount);
        itemNo.setPaddingBottom(10);
        table.addCell(itemNo);

        PdfPCell amountSection = addtotalAmount( totalAmount, receivedAmount );
        amountSection.setPaddingLeft(120);
        amountSection.setPaddingBottom(10);
        table.addCell(amountSection);
        
        document.add(table);
        
        document.close();
        return filePath;
    }
    
    /**
     *
     * @param invoiceCount
     * @param profitAmount
     * @return
     */
    public PdfPCell addItemCount(int invoiceCount, double profitAmount) {

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
        Chunk porfitChunk = new Chunk("Profit Amount:" + String.valueOf(profitAmount), labelBold);
        blankParagraphy.add(porfitChunk);
        cell.addElement(blankParagraphy);
        
        Paragraph oneblankParagraphy = new Paragraph();
        Chunk oneblankChunk = new Chunk(" ");
        oneblankParagraphy.add(oneblankChunk);
        cell.addElement(oneblankParagraphy);
        
        return cell;
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
     * @param fromDate
     * @param toDate
     * @param printDate
     * @return
     */
    public PdfPCell setSalesInformation(String title, String fromDate, String toDate, String printDate) {

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
        

        // From Label.
        Paragraph fromCodeParagraphy = new Paragraph();
        Chunk fromLabelChunk = new Chunk("From: ", labelBold);
        Chunk fromChunk = new Chunk(fromDate, normal);
        fromCodeParagraphy.add(fromLabelChunk);
        fromCodeParagraphy.add(fromChunk);
        cell.addElement(new Paragraph(fromCodeParagraphy));
        
        // To Label.
        Paragraph toParagraphy = new Paragraph();
        Chunk toLabelChunk = new Chunk("To: ", labelBold);
        Chunk toDateChunk = new Chunk(toDate, normal);
        toParagraphy.add(toLabelChunk);
        toParagraphy.add(toDateChunk);
        cell.addElement(new Paragraph(toParagraphy));
        

        // Customer Address Label.
        Paragraph currentDateParagraphy = new Paragraph();
        Chunk currentDateLabelChunk = new Chunk("Current Date: ", labelBold);
        Chunk currentChunk = new Chunk(printDate, normal);
        currentDateParagraphy.add(currentDateLabelChunk);
        currentDateParagraphy.add(currentChunk);
        cell.addElement(new Paragraph(currentDateParagraphy));

        return cell;
    }
}
