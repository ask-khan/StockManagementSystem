/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handler;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import util.DBUtil;

/**
 *
 * @author Ahmed
 */
public class GeneratePDF {

    /**
     * This Method is used to generate PDF.
     *
     * @param invoiceId
     * @return
     * @throws java.io.IOException
     * @throws com.itextpdf.text.DocumentException
     */
    public String generatePDFInvoice(String invoiceId) throws IOException, DocumentException, ClassNotFoundException, SQLException {
        ArrayList<String> customerInvoiceData = new ArrayList<String>();

        DecimalFormat df = new DecimalFormat("###.##");
        // Declare filePath.
        String filePath = "C:\\Users/Ahmed/Desktop/InvoicePDF/" + invoiceId + ".pdf";
        
        File file = new File(filePath);
        
        // if file is exit
        if ( file.exists() ) {
            // file delete.
            file.delete();
        }
        
        //Create PdfReader instance. 
        PdfReader pdfReader = new PdfReader("C:\\Users/Ahmed/Desktop/InvoicePDF/SamplePDF.pdf");
        
        
        //Create PdfStamper instance. 
        PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(filePath));

        // Declare Font Size.
        BaseFont baseFont = BaseFont.createFont(BaseFont.COURIER_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);

        int pages = pdfReader.getNumberOfPages();

        // If Page Values is One.
        if (pages == 1) {
            // Get customer select by id function call.
            customerInvoiceData = getCustomerSelectById(Integer.valueOf(invoiceId));
            ResultSet resultSet = getInvoiceProductListById(Integer.valueOf(invoiceId));
            System.out.println(customerInvoiceData);
            PdfContentByte CustomerName = pdfStamper.getOverContent(pages);
            CustomerName.beginText();
            //Set text font and size. 
            CustomerName.setFontAndSize(baseFont, 13);
            CustomerName.setTextMatrix(75, 665);
            //Write text 
            //System.out.println( CustomerData[0]);
            CustomerName.showText(customerInvoiceData.get(1));
            CustomerName.endText();

            PdfContentByte billNo = pdfStamper.getOverContent(pages);
            billNo.beginText();
            //Set text font and size. 
            billNo.setFontAndSize(baseFont, 13);
            billNo.setTextMatrix(85, 695);
            //Write text 
            billNo.showText(customerInvoiceData.get(0));
            billNo.endText();

            PdfContentByte submitDate = pdfStamper.getOverContent(pages);
            submitDate.beginText();
            //Set text font and size. 
            submitDate.setFontAndSize(baseFont, 13);
            submitDate.setTextMatrix(465, 695);
            //Write text 
            submitDate.showText(customerInvoiceData.get(2));
            submitDate.endText();

            int heightValue = 590;

            while (resultSet.next()) {
                // Set Product Quanlity.
                PdfContentByte ProductQuanlity = pdfStamper.getOverContent(pages);
                ProductQuanlity.beginText();
                //Set text font and size. 
                ProductQuanlity.setFontAndSize(baseFont, 13);
                ProductQuanlity.setTextMatrix(50, heightValue);
                ProductQuanlity.showText(resultSet.getString("quanlity"));
                ProductQuanlity.endText();

                // Set Product Packing.
                PdfContentByte productpacking = pdfStamper.getOverContent(pages);
                productpacking.beginText();
                //Set text font and size. 
                productpacking.setFontAndSize(baseFont, 13);
                productpacking.setTextMatrix(100, heightValue);

                ResultSet productDetail = this.getProductDetailById(resultSet.getString("productid"));
                //System.out.println( productDetail.getInt("productpacking") );
                //Write text 
                productpacking.showText(String.valueOf(productDetail.getInt("productpacking")));
                productpacking.endText();

                // Set Product Name.
                PdfContentByte productName = pdfStamper.getOverContent(pages);
                productName.beginText();
                //Set text font and size. 
                productName.setFontAndSize(baseFont, 13);
                productName.setTextMatrix(165, heightValue);

                //Write text 
                productName.showText(productDetail.getString("productname"));
                productName.endText();

                // Set Product Trade Price.
                PdfContentByte ProductTradePrice = pdfStamper.getOverContent(pages);
                ProductTradePrice.beginText();
                //Set text font and size. 
                ProductTradePrice.setFontAndSize(baseFont, 13);
                ProductTradePrice.setTextMatrix(380, heightValue);
                if (resultSet.getDouble("tradeprice") == productDetail.getDouble("tradeprice")) {
                    //Write text 
                    ProductTradePrice.showText(String.valueOf(resultSet.getDouble("tradeprice")));
                    ProductTradePrice.endText();
                } else {
                    //Write text 
                    ProductTradePrice.showText(String.valueOf(resultSet.getDouble("tradeprice")));
                    ProductTradePrice.endText();
                }

                // Set Product Trade Price.
                PdfContentByte ProductNetAmount = pdfStamper.getOverContent(pages);
                ProductNetAmount.beginText();
                //Set text font and size. 
                ProductNetAmount.setFontAndSize(baseFont, 13);
                ProductNetAmount.setTextMatrix(500, heightValue);

                //Write text 
                ProductNetAmount.showText(resultSet.getString("amount"));
                ProductNetAmount.endText();

                heightValue = heightValue - 13;
            }

            // Set Product Total Price.
            PdfContentByte ProductTotalMainPrice = pdfStamper.getOverContent(pages);
            ProductTotalMainPrice.beginText();
            //Set text font and size. 
            ProductTotalMainPrice.setFontAndSize(baseFont, 13);
            ProductTotalMainPrice.setTextMatrix(510, 112);
            //Write text 
            ProductTotalMainPrice.showText(customerInvoiceData.get(3));
            ProductTotalMainPrice.endText();

            pdfStamper.close();
        }
        return filePath;
    }

    // Get Selected By Id.
    public ArrayList getCustomerSelectById(int invoiceId) throws ClassNotFoundException, SQLException {
        ArrayList<String> customerInvoiceData = new ArrayList<String>();

        // Declare database parameter
        DBUtil dbObject = new DBUtil();
        Connection connection = dbObject.DbConnection();
        connection.setAutoCommit(false);
        Statement statementObject = connection.createStatement();

        // Get Customer Data by Id.
        String getCustomerDataById = "Select * from invoice where id=" + invoiceId;
        ResultSet resultSet = statementObject.executeQuery(getCustomerDataById);
        connection.commit();

        if (resultSet.next()) {
            customerInvoiceData.add(String.valueOf(resultSet.getInt("id")));
            customerInvoiceData.add(resultSet.getString("customername"));
            customerInvoiceData.add(resultSet.getString("datepicker"));
            customerInvoiceData.add(String.valueOf(resultSet.getDouble("totalamount")));
        }
        return customerInvoiceData;
    }

    // Get Invoice Product List By Id.
    public ResultSet getInvoiceProductListById(int invoiceId) throws ClassNotFoundException, SQLException {
        ArrayList<String> customerInvoiceData = new ArrayList<String>();

        // Declare database parameter
        DBUtil dbObject = new DBUtil();
        Connection connection = dbObject.DbConnection();
        connection.setAutoCommit(false);
        Statement statementObject = connection.createStatement();

        // Get inovice product list data by Id.
        String getCustomerDataById = "Select * from invoiceproduct where invoiceid='" + invoiceId + "';";

        ResultSet resultSet = statementObject.executeQuery(getCustomerDataById);
        connection.commit();

        return resultSet;
    }

    public ResultSet getProductDetailById(String productId) throws ClassNotFoundException, SQLException {
        ArrayList<String> customerInvoiceData = new ArrayList<String>();

        // Declare database parameter
        DBUtil dbObject = new DBUtil();
        Connection connection = dbObject.DbConnection();
        connection.setAutoCommit(false);
        Statement statementObject = connection.createStatement();

        // Get inovice product list data by Id.
        String getProductDetailById = "Select * from products where id='" + productId + "';";

        ResultSet resultSet = statementObject.executeQuery(getProductDetailById);
        connection.commit();

        if (resultSet.next()) {
            return resultSet;
        } else {
            return null;
        }

    }

    /**
     * This method is used for print PDF
     *
     * @param Path Path of the PDF.
     * @exception IOException On input error.
     * @exception PrinterException On print error.
     * @see IOException
     * @see PrinterException
     */
    public void PrintPDF(String Path) throws IOException, PrinterException {
        // Create PDDocument
        PDDocument document = PDDocument.load(new File(Path));
        // Function Call for PrintService.
        PrintService myPrintService = findPrintService("hp LaserJet 3015 UPD PCL 5");
        // Get PrinterJob.
        PrinterJob job = PrinterJob.getPrinterJob();
        // Show Dialog
        job.printDialog();
        // Set Page for Path
        job.setPageable(new PDFPageable(document));
        // Set Print Service
        job.setPrintService(myPrintService);
        // Job Print Function
        job.print();
    }

    /**
     * This Method is used to find the Print Service
     *
     * @param printerName This is the name of the Printer
     * @return PrintService This will return the PrinterServuce Object.
     */
    private static PrintService findPrintService(String printerName) {
        // Declare PrintService Object.
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        // For Loop 
        for (PrintService printService : printServices) {
            //System.out.println(printService.getName().trim());
            if (printService.getName().trim().equals(printerName)) {
                return printService;
            }
        }
        return null;
    }
}
