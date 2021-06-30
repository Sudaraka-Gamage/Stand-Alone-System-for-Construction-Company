/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generate_email;

import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Chathura
 */
public class GenerateMail {
    public void create_sendEmail(String paymentId, String email){
        try {
            String host = "smtp.gmail.com";
            String user = "ntdbuilders@gmail.com";
            String password = "ntdb@itp";
            String to = email;
            String from = "ntdbuilders@gmail.com";
            String subject = "Your contract installment payment has been recived";
            String emailContent ="payment recived";
            String sourceFile = "C:\\Users\\Chathura\\Documents\\NetBeansProjects\\Finance\\"+paymentId+".pdf";
            String fileName = "ContractInstallmentPaymentRecipt.pdf";
            
            boolean sessionDebug = false;
            
            Properties prop = System.getProperties();
            
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.host", host);
            prop.put("mail.smtp.port", "587");
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.starttls.required", "true");
            
            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            Session mailSession = Session.getDefaultInstance(prop, null);
            mailSession.setDebug(sessionDebug);
            Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.setText(emailContent);
            MimeBodyPart messageBody = new MimeBodyPart();
            Multipart multipart = new MimeMultipart();
            messageBody = new MimeBodyPart();
            DataSource source = new FileDataSource(sourceFile);
            messageBody.setDataHandler(new DataHandler(source));
            messageBody.setFileName(fileName);
            multipart.addBodyPart(messageBody);
            msg.setContent(multipart);
            
            
            Transport transport = mailSession.getTransport("smtp");
            transport.connect(host, user, password);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
            System.out.println("Message sent");
        } catch (MessagingException ex) {
            Logger.getLogger(GenerateMail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
