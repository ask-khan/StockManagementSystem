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
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import config.Configuration;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import models.PrintModel;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import util.DBUtil;

/**
 *
 * @author Ahmed
 */
public class GeneratePDF {

    /**
     *
     */
    protected Font font10;

    /**
     *
     */
    protected Font font10b;

    /**
     *
     */
    protected Font font12;

    /**
     *
     */
    protected Font font12b;

    /**
     *
     */
    protected Font font14;

    /**
     *
     * @param writer
     * @param document
     */
    public void onEndPage(PdfWriter writer, Document document) {

        Font labelBold = new Font(FontFamily.COURIER, 16, Font.BOLD);
        Font labelNormalBold = new Font(FontFamily.COURIER, 8, Font.BOLD);

        PdfContentByte cb = writer.getDirectContent();
        Phrase header = new Phrase("Minart Traders", labelBold);

        ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                header,
                (document.right() - document.left()) / 2 + document.leftMargin(),
                document.top() + 10, 0);
    }

    /**
     * This Method is used to generate PDF.
     *
     * @param invoiceId
     * @return
     * @throws java.io.IOException
     * @throws com.itextpdf.text.DocumentException
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public String generatePDFInvoice(String invoiceId) throws IOException, DocumentException, ClassNotFoundException, SQLException {
        ArrayList<String> customerInvoiceData = new ArrayList<String>();
        customerInvoiceData = getCustomerSelectById(Integer.valueOf(invoiceId));
        ResultSet resultSet = getInvoiceProductListById(Integer.valueOf(invoiceId));

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        int invoiceCount = 0;
        DecimalFormat df = new DecimalFormat("###.##");

        Font labelBold = new Font(FontFamily.COURIER, 9, Font.BOLD);
        Font normal = new Font(FontFamily.COURIER, 9, Font.NORMAL);
        Font AddressNormal = new Font(FontFamily.COURIER, 8, Font.BOLD);
        Font small = new Font(FontFamily.COURIER, 8, Font.BOLD);

        String filePath = "", folderPath = "";
        // Declare Configuration Object.
        Configuration configurationObject = new Configuration();
        
        // Customer Folder Path And File Path.
        folderPath = configurationObject.getFolderPath() + "Customers-Invoice\\" + customerInvoiceData.get(1).replaceAll(" ", "-"); 
        filePath = folderPath + "/" + invoiceId + ".pdf";
        
        File folderObject = new File(folderPath);
        if (!folderObject.exists()) {
            if (folderObject.mkdir()) {
                // Declare filePath.
                File file = new File(filePath);
                // if file is exit
                if (file.exists()) {
                    // file delete.
                    //System.out.println("exit");
                    file.delete();
                }
            } else {
                System.out.println("Failed to create directory!");
            }
        }
        System.out.println(filePath);
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));

        // step 3
        document.open();

        onEndPage(writer, document);

        Chunk addressChunk = new Chunk("      M.A Mujahid Center Arcade,2 Floor Sector 24,Pl#6/4,Shan Circle Korangi Industrial Area, Karachi", AddressNormal);

        Phrase addressPhrase = new Phrase(10);
        addressPhrase.add(addressChunk);

        document.add(addressPhrase);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        //System.out.println( customerInvoiceData );
        PdfPCell seller = getCustomerInformtion("Customer Info",
                customerInvoiceData.get(5),
                customerInvoiceData.get(1),
                "",
                "");

        table.addCell(seller);
        PdfPCell buyer = getInvoiceInformation("Invoice Info",
                invoiceId,
                dtf.format(now),
                "");

        buyer.setPaddingLeft(120);
        table.addCell(buyer);
        document.add(table);

        table = new PdfPTable(9);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10);
        //table.setSpacingAfter(10);
        table.setWidths(new int[]{1, 1, 3, 2, 2, 2, 2, 2, 2});

        table.addCell(getCell("Sr.#", Element.ALIGN_LEFT, labelBold));
        table.addCell(getCell("Qty.", Element.ALIGN_LEFT, labelBold));
        table.addCell(getCell("Product Name:", Element.ALIGN_LEFT, labelBold));
        table.addCell(getCell("Batch No:", Element.ALIGN_LEFT, labelBold));
        table.addCell(getCell("Ex Date:", Element.ALIGN_LEFT, labelBold));
        table.addCell(getCell("Packing:", Element.ALIGN_LEFT, labelBold));
        table.addCell(getCell("T.P:", Element.ALIGN_LEFT, labelBold));
        table.addCell(getCell("Dis %:", Element.ALIGN_LEFT, labelBold));
        table.addCell(getCell("Net Amount", Element.ALIGN_LEFT, labelBold));

        while (resultSet.next()) {
            invoiceCount++;
            String bonus = "";
            System.out.println(resultSet.getString("bonus"));
            if (resultSet.getString("bonus") != null) {
                System.out.println(resultSet.getString("bonus"));
                bonus = !"0".equals(resultSet.getString("bonus")) ? "+" + resultSet.getString("bonus") : "";
            }

            table.addCell(getCell(String.valueOf(invoiceCount), Element.ALIGN_LEFT, normal));
            table.addCell(getCell(resultSet.getString("quanlity") + bonus, Element.ALIGN_LEFT, normal));
            ResultSet productDetail = this.getProductDetailById(resultSet.getString("productid"));
            table.addCell(getCell(productDetail.getString("productname"), Element.ALIGN_LEFT, normal));
            table.addCell(getCell(resultSet.getString("batchno"), Element.ALIGN_LEFT, normal));
            table.addCell(getCell(resultSet.getString("expireddate"), Element.ALIGN_LEFT, normal));
            table.addCell(getCell(String.valueOf(productDetail.getInt("productpacking")), Element.ALIGN_LEFT, normal));
            table.addCell(getCell(String.valueOf(resultSet.getDouble("tradeprice")), Element.ALIGN_LEFT, normal));
            table.addCell(getCell(resultSet.getString("productdiscount"), Element.ALIGN_LEFT, normal));
            table.addCell(getCell(resultSet.getString("amount"), Element.ALIGN_LEFT, normal));
        }

        document.add(table);

        table = new PdfPTable(2);
        table.setWidthPercentage(100);
        PdfPCell itemNo = addItemCount(invoiceCount);
        itemNo.setPaddingBottom(10);
        table.addCell(itemNo);

        PdfPCell totalAmount = addtotalAmount(customerInvoiceData.get(3), getPreviousBalance(String.valueOf(invoiceId), customerInvoiceData.get(1)), customerInvoiceData.get(4), getCurrentBalance(customerInvoiceData.get(1)));
        totalAmount.setPaddingLeft(120);
        totalAmount.setPaddingBottom(10);
        table.addCell(totalAmount);

        document.add(table);

        if ("true".equals(customerInvoiceData.get(6))) {
            Chunk chunk = new Chunk("Warranty:Expired products shall not be taken unless written initimation is given 4 months before expiry date , Please check for Batch no, Leaking, Breaking, shortage and discount before the goods have been delivered.\n", small);
            //chunk.setUnderline(1.5f, -1);
            Phrase phrase = new Phrase(5);
            phrase.add(chunk);
            document.add(phrase);
        }

        Chunk linebreak = new Chunk(new DottedLineSeparator());
        document.add(linebreak);

        document.close();

        return filePath;
    }

    /**
     *
     * @param billAmount
     * @param previousBalance
     * @param received
     * @param balance
     * @return
     */
    public PdfPCell addtotalAmount(String billAmount, float previousBalance, String received, float balance) {
        Font labelBold = new Font(FontFamily.COURIER, 9, Font.BOLD);
        Font normal = new Font(FontFamily.COURIER, 9, Font.NORMAL);

        PdfPCell cell = new PdfPCell();
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setPaddingTop(0);

        // Bill Info Detail    
        Paragraph billAmountParagraphy = new Paragraph();
        Chunk billAmountChunk = new Chunk("Bill Amount: " + String.valueOf((int) Math.round(Float.valueOf(billAmount))), labelBold);
        billAmountParagraphy.add(billAmountChunk);
        cell.addElement(billAmountParagraphy);

        // Previous Amount Detail    
        Paragraph previousBalanceParagraphy = new Paragraph();
        Chunk previousBalanceChunk = new Chunk("Previous Balance: " + String.valueOf((int) Math.round(previousBalance)), labelBold);
        previousBalanceParagraphy.add(previousBalanceChunk);
        cell.addElement(previousBalanceParagraphy);

        // Previous Amount Detail    
        Paragraph receivedParagraphy = new Paragraph();
        Chunk receivedChunk = new Chunk("Received: " + received, labelBold);
        receivedParagraphy.add(receivedChunk);
        cell.addElement(receivedParagraphy);

        // Balance Amount Detail    
        Paragraph balanceParagraphy = new Paragraph();
        Chunk balanceChunk = new Chunk("Balance: " + String.valueOf((int) Math.round(balance)), labelBold);
        balanceParagraphy.add(balanceChunk);
        cell.addElement(balanceParagraphy);

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
        //Font warrantyLabelDefinationBold = new Font(FontFamily.COURIER, 6, Font.NORMAL);
        PdfPCell cell = new PdfPCell();
        cell.setBorder(PdfPCell.NO_BORDER);

        // Customer Info Label    
        Paragraph itemNoParagraphy = new Paragraph();
        Chunk itemNoChunk = new Chunk("No of Items: " + String.valueOf(invoiceCount), labelBold);
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

        Paragraph signatureParagraphy = new Paragraph();
        Chunk signatureChunk = new Chunk("Signature : ", warrantyLabelBold);
        //Chunk warrantyDefinedChunk = new Chunk("Expired products shall not be taken unless written initimation is given 4 months before expiry date , Please check for Batch no, Leaking, Breaking, shortage and discount before the goods have been delivered.", warrantyLabelDefinationBold);
        Chunk linebreak = new Chunk(new DottedLineSeparator());
        signatureParagraphy.add(signatureChunk);
        signatureParagraphy.add(linebreak);

        cell.addElement(signatureParagraphy);
        return cell;
    }

    /**
     *
     * @param value
     * @param alignment
     * @param font
     * @return
     */
    public PdfPCell getCell(String value, int alignment, Font font) {
        PdfPCell cell = new PdfPCell();
        Paragraph p = new Paragraph(value, font);
        p.setAlignment(alignment);
        cell.addElement(p);
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
     * @param invoiceId
     * @param invoiceDate
     * @param invoiceTerm
     * @return
     */
    public PdfPCell getInvoiceInformation(String title, String invoiceId, String invoiceDate, String invoiceTerm) {
        Font labelBold = new Font(FontFamily.COURIER, 9, Font.BOLD);
        Font normal = new Font(FontFamily.COURIER, 9, Font.NORMAL);

        PdfPCell cell = new PdfPCell();
        cell.setBorder(PdfPCell.NO_BORDER);

        // Invoice Info Label    
        Paragraph invoiceInfoLabelParagraphy = new Paragraph();
        Chunk invoiceLabelChunk = new Chunk(title, labelBold);
        invoiceInfoLabelParagraphy.add(invoiceLabelChunk);
        cell.addElement(invoiceInfoLabelParagraphy);

        // Invoice Id Label    
        Paragraph invoiceIdLabelParagraphy = new Paragraph();
        Chunk invoiceIdLabelChunk = new Chunk("Invoice #: ", labelBold);
        Chunk invoiceChunk = new Chunk(invoiceId, normal);
        invoiceIdLabelParagraphy.add(invoiceIdLabelChunk);
        invoiceIdLabelParagraphy.add(invoiceChunk);
        cell.addElement(invoiceIdLabelParagraphy);

        // Invoice Date Label  
        Paragraph invoiceDateLabelParagraphy = new Paragraph();
        Chunk invoiceDateLabelChunk = new Chunk("Date: ", labelBold);
        Chunk invoiceDateChunk = new Chunk(invoiceDate, normal);
        invoiceDateLabelParagraphy.add(invoiceDateLabelChunk);
        invoiceDateLabelParagraphy.add(invoiceDateChunk);
        cell.addElement(invoiceDateLabelParagraphy);

        Paragraph invoiceTermLabelParagraphy = new Paragraph();
        Chunk invoiceTermLabelChunk = new Chunk("Term: ", labelBold);
        Chunk invoiceTermChunk = new Chunk(invoiceTerm, normal);
        invoiceTermLabelParagraphy.add(invoiceTermLabelChunk);
        invoiceTermLabelParagraphy.add(invoiceTermChunk);
        cell.addElement(invoiceTermLabelParagraphy);

        return cell;
    }

    // Get Selected By Id.
    /**
     *
     * @param invoiceId
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
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
            customerInvoiceData.add(String.valueOf(resultSet.getDouble("receivedamount")));
            customerInvoiceData.add(String.valueOf(resultSet.getInt("customerid")));
            customerInvoiceData.add(String.valueOf(resultSet.getBoolean("warranty")));
        }
        return customerInvoiceData;
    }

    // Get Invoice Product List By Id.
    /**
     *
     * @param invoiceId
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
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

    /**
     *
     * @param invoice
     * @param customerName
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public float getPreviousBalance(String invoice, String customerName) throws ClassNotFoundException, SQLException {
        DBUtil dbObject = new DBUtil();
        float previousBalance = 0;
        Connection connection = dbObject.DbConnection();
        connection.setAutoCommit(false);
        Statement statementObject = connection.createStatement();

        String getAllInvoiceQuery = "Select sum( totalamount ) - sum( receivedamount ) as previousbalance from invoice where customername='" + customerName + "' AND id <>'" + invoice + "';";
        System.out.println(getAllInvoiceQuery);
        ResultSet resultSet = statementObject.executeQuery(getAllInvoiceQuery);

        if (resultSet.next()) {
            previousBalance = resultSet.getFloat("previousbalance");
        }

        return previousBalance;
    }

    /**
     *
     * @param customerName
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public float getCurrentBalance(String customerName) throws ClassNotFoundException, SQLException {
        DBUtil dbObject = new DBUtil();
        float previousBalance = 0;
        Connection connection = dbObject.DbConnection();
        connection.setAutoCommit(false);
        Statement statementObject = connection.createStatement();

        String getAllInvoiceQuery = "Select sum( totalamount ) - sum( receivedamount ) as currentbalance from invoice where customername='" + customerName + "';";
        System.out.println(getAllInvoiceQuery);
        ResultSet resultSet = statementObject.executeQuery(getAllInvoiceQuery);

        if (resultSet.next()) {
            previousBalance = resultSet.getFloat("currentbalance");
        }

        return previousBalance;
    }

    /**
     *
     * @param content
     * @param borderWidth
     * @param colspan
     * @param alignment
     * @return
     */
    public PdfPCell createCell(String content, float borderWidth, int colspan, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(content));
        cell.setBorderWidth(borderWidth);
        cell.setColspan(colspan);
        cell.setHorizontalAlignment(alignment);
        return cell;
    }

    /**
     *
     * @param productId
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
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
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     * @see IOException
     * @see PrinterException
     */
    public void PrintPDF(String Path) throws IOException, PrinterException, SQLException, ClassNotFoundException {
        // Create PDDocument
        System.out.println(Path);
        PDDocument document = PDDocument.load(new File(Path));
        System.out.println(Path);
        PrintModel printModel = new PrintModel();
        String printName = printModel.getPrintName();
        System.out.println(printName);
        // Function Call for PrintService.
        PrintService myPrintService = findPrintService(printName);
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
