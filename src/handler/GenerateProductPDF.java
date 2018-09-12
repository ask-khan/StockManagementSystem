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
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
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
public class GenerateProductPDF {

    /**
     *
     * @param writer
     * @param document
     */
    public void onEndPage(PdfWriter writer, Document document) {
        Font labelBold = new Font(Font.FontFamily.COURIER, 16, Font.BOLD);
        PdfContentByte cb = writer.getDirectContent();
        Phrase header = new Phrase("Minart Traders", labelBold);
        ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                header,
                (document.right() - document.left()) / 2 + document.leftMargin(),
                document.top() + 10, 0);

    }

    /**
     *
     * @param productId
     * @param productInfo
     * @param resultSet
     * @return
     * @throws IOException
     * @throws DocumentException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public String generatePDFProduct(String productId, ResultSet productInfo, ResultSet resultSet) throws IOException, DocumentException, ClassNotFoundException, SQLException {
        GeneratePDF generatePDF = new GeneratePDF();
        ArrayList<String> customerInvoiceData = new ArrayList<String>();
        //customerInvoiceData = getCustomerInfoById(customerId);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        int invoiceCount = 0, productTotalQuanlity = 0;
        double totalAmount = 0;
        DecimalFormat df = new DecimalFormat("###.##");
        
        // Declare Configuration Object.
        Configuration configurationObject = new Configuration();
        // Declare filePath.
        String filePath =  configurationObject.getFolderPath() + "ProductReports/" + productId + ".pdf";

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
        PdfPCell seller = new PdfPCell();
        
        if (productInfo.next()) {
            seller = getProductInformtion("Product Info",
                    String.valueOf(productInfo.getInt("id")),
                    String.valueOf(productInfo.getString("productname")),
                    String.valueOf(productInfo.getString("brandname")));

        }
        table.addCell(seller);
        document.add(table);

        table = new PdfPTable(8);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        //table.setSpacingAfter(10);
        table.setWidths(new int[]{1, 2, 2, 3, 3, 2, 2, 2});

        table.addCell(generatePDF.getCell("Sr.#", Element.ALIGN_LEFT, labelBold));
        table.addCell(generatePDF.getCell("Invoice Id.", Element.ALIGN_LEFT, labelBold));
        table.addCell(generatePDF.getCell("Product Id:", Element.ALIGN_LEFT, labelBold));
        table.addCell(generatePDF.getCell("Date:", Element.ALIGN_LEFT, labelBold));
        table.addCell(generatePDF.getCell("Quanlity:", Element.ALIGN_LEFT, labelBold));
        table.addCell(generatePDF.getCell("Trade Price:", Element.ALIGN_LEFT, labelBold));
        table.addCell(generatePDF.getCell("Product Discount:", Element.ALIGN_LEFT, labelBold));
        table.addCell(generatePDF.getCell("Amount:", Element.ALIGN_LEFT, labelBold));

        while (resultSet.next()) {
            invoiceCount++;
            totalAmount = totalAmount + Double.valueOf(resultSet.getString("amount"));
            productTotalQuanlity = productTotalQuanlity + Integer.valueOf(resultSet.getString("quanlity"));
            table.addCell(generatePDF.getCell(String.valueOf(invoiceCount), Element.ALIGN_LEFT, normal));
            table.addCell(generatePDF.getCell(resultSet.getString("invoiceid"), Element.ALIGN_LEFT, normal));
            table.addCell(generatePDF.getCell(resultSet.getString("productid"), Element.ALIGN_LEFT, normal));
            table.addCell(generatePDF.getCell(String.valueOf(resultSet.getDate("modifieddate")), Element.ALIGN_LEFT, normal));
            table.addCell(generatePDF.getCell(resultSet.getString("quanlity"), Element.ALIGN_LEFT, normal));
            table.addCell(generatePDF.getCell(String.valueOf(resultSet.getDouble("tradeprice")), Element.ALIGN_LEFT, normal));
            table.addCell(generatePDF.getCell(String.valueOf(resultSet.getString("productdiscount")), Element.ALIGN_LEFT, normal));
            table.addCell(generatePDF.getCell(String.valueOf(resultSet.getString("amount")), Element.ALIGN_LEFT, normal));

        }

        document.add(table);

        table = new PdfPTable(2);
        table.setWidthPercentage(100);
        PdfPCell itemNo = addProductCount(invoiceCount);
        itemNo.setPaddingBottom(10);
        table.addCell(itemNo);

        PdfPCell amountSection = addtotalAmount(totalAmount, productTotalQuanlity);
        amountSection.setPaddingLeft(120);
        amountSection.setPaddingBottom(10);
        table.addCell(amountSection);

        document.add(table);
        document.close();
        return filePath;
    }

    /**
     *
     * @param title
     * @param productId
     * @param productName
     * @param productCompany
     * @return
     */
    public PdfPCell getProductInformtion(String title, String productId, String productName, String productCompany) {

        Font labelBold = new Font(Font.FontFamily.COURIER, 9, Font.BOLD);
        Font normal = new Font(Font.FontFamily.COURIER, 9, Font.NORMAL);

        PdfPCell cell = new PdfPCell();
        cell.setBorder(PdfPCell.NO_BORDER);
        //cell.setPadding(0);
        
        // Product Info Label    
        Paragraph productInfoLabelParagraphy = new Paragraph();
        Chunk productLabelChunk = new Chunk(title, labelBold);
        productInfoLabelParagraphy.add(productLabelChunk);
        cell.addElement(productInfoLabelParagraphy);
        
        // Product Code Label.
        Paragraph productCodeParagraphy = new Paragraph();
        Chunk productCodeLabelChunk = new Chunk("Product Id: ", labelBold);
        Chunk productCodeChunk = new Chunk(productId, normal);
        productCodeParagraphy.add(productCodeLabelChunk);
        productCodeParagraphy.add(productCodeChunk);
        cell.addElement(new Paragraph(productCodeParagraphy));
        

        // Product Name Label.
        Paragraph productNameParagraphy = new Paragraph();
        Chunk productNameLabelChunk = new Chunk("Name: ", labelBold);
        Chunk productNameChunk = new Chunk(productName, normal);
        productNameParagraphy.add(productNameLabelChunk);
        productNameParagraphy.add(productNameChunk);
        cell.addElement(new Paragraph(productNameParagraphy));
        

        Paragraph companyParagraphy = new Paragraph();
        Chunk invoiceTermLabelChunk = new Chunk("Company: ", labelBold);
        Chunk companyNameChunk = new Chunk(productCompany, normal);
        companyParagraphy.add(invoiceTermLabelChunk);
        companyParagraphy.add(companyNameChunk);
        cell.addElement(companyParagraphy);
     
        return cell;
    }
    
    /**
     *
     * @param invoiceCount
     * @return
     */
    public PdfPCell addProductCount(int invoiceCount) { 
        Font labelBold = new Font(Font.FontFamily.COURIER, 9, Font.BOLD);
        Font normal = new Font(Font.FontFamily.COURIER, 9, Font.NORMAL);
        Font warrantyLabelBold = new Font(Font.FontFamily.COURIER, 8, Font.BOLD);
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
    
    /**
     *
     * @param totalAmount
     * @param productQuanlity
     * @return
     */
    public PdfPCell addtotalAmount(double totalAmount, double productQuanlity) {
        Font labelBold = new Font(Font.FontFamily.COURIER, 9, Font.BOLD);
        Font normal = new Font(Font.FontFamily.COURIER, 9, Font.NORMAL);

        PdfPCell cell = new PdfPCell();
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setPaddingTop(0);
        
        // Previous Quanlity   
        Paragraph productQuanlityParagraphy = new Paragraph();
        Chunk productQuanlityChunk = new Chunk("Product Quanlity: " + String.valueOf(productQuanlity), labelBold);
        productQuanlityParagraphy.add(productQuanlityChunk);
        cell.addElement(productQuanlityParagraphy);
        
        //Total Amount   
        Paragraph totalAmountParagraphy = new Paragraph();
        Chunk totalAmountChunk = new Chunk("Total Amount: " + String.valueOf(totalAmount), labelBold);
        totalAmountParagraphy.add(totalAmountChunk);
        cell.addElement(totalAmountParagraphy);

        return cell;
    }
}
