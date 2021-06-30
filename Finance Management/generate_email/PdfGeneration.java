/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generate_email;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Payment;

/**
 *
 * @author Chathura
 */
public class PdfGeneration {
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 20,Font.BOLD);
    private static Font catFont2 = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
    private static Font catFont3 = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.ITALIC);
    
    public void generatePdf(Payment payment){
        try {
            Document document = new Document();
            
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(payment.getPayment_id()+".pdf"));
            Paragraph paragraph;
            document.open();
            
            paragraph = new Paragraph("NEW TECH DESIGN BUILDERS\n\n", catFont);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
            
            paragraph = new Paragraph("Contract Installment Recipt\n\n", catFont2);
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);
            
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(105);
            table.setSpacingBefore(11f);
            table.setSpacingAfter(11f);
            
            float[] colWidth = {2f, 2f};
            
            table.setWidths(colWidth);
            
            PdfPCell r1 = new PdfPCell(new Paragraph("Payment ID"));
            PdfPCell r12 = new PdfPCell(new Paragraph("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+payment.getPayment_id()));
            
            PdfPCell r2 = new PdfPCell(new Paragraph("Your Contract ID"));
            PdfPCell r22 = new PdfPCell(new Paragraph("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+payment.getContract()));
            
            PdfPCell r3 = new PdfPCell(new Paragraph("Contract Payment Date"));
            PdfPCell r32 = new PdfPCell(new Paragraph("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+payment.getPayment_date()));
            
            PdfPCell r4 = new PdfPCell(new Paragraph("Installment Number"));
            PdfPCell r42 = new PdfPCell(new Paragraph("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tInstallment "+payment.getInstallment_num()));
            
            PdfPCell r5 = new PdfPCell(new Paragraph("Payment Value"));
            PdfPCell r52 = new PdfPCell(new Paragraph("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+payment.getPayment_value()));
            
            
            table.addCell(r1);
            table.addCell(r12);
            table.addCell("");
            table.addCell("");
            table.addCell("");
            table.addCell("");
            table.addCell(r2);
            table.addCell(r22);
            table.addCell("");
            table.addCell("");
            table.addCell("");
            table.addCell("");
            table.addCell(r3);
            table.addCell(r32);
            table.addCell("");
            table.addCell("");
            table.addCell("");
            table.addCell("");
            table.addCell(r4);
            table.addCell(r42);
            table.addCell("");
            table.addCell("");
            table.addCell("");
            table.addCell("");
            table.addCell(r5);
            table.addCell(r52);
            
            document.add(table);
            
            paragraph = new Paragraph("Thanks for your installment payment.\n\n", catFont3);
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);
            
            paragraph = new Paragraph(payment.getPayment_date());
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);
            
            document.close();
            writer.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PdfGeneration.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(PdfGeneration.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
}
