package com.patrakar.patrakar.service.aws;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import com.patrakar.patrakar.config.AwsSesConfig;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Properties;

@Service
public class AwsSesService {

    private final String FROM_EMAIL = "your_verified_email@your_domain.com"; // Replace with your verified sender email
    private final String SUBJECT = "Your Email Subject"; // Replace with the email subject
    private final String CHARSET = "UTF-8";

    private AmazonSimpleEmailService sesClient;

    public AwsSesService(AwsSesConfig awsSesConfig) {
        //        BasicAWSCredentials credentials = new BasicAWSCredentials(awsSesConfig.getAccessKey(), awsSesConfig.getSecretKey());
        this.sesClient =
            AmazonSimpleEmailServiceClientBuilder
                .standard()
                .withRegion(Regions.US_EAST_1)
                .build();
    }

    public void sendEmail(String fromEmail, String toEmail, String subject, String bodyText, List<String> attachments) {
        try {
            if (attachments != null && attachments.isEmpty()) {
                sendEmailWithAttachments(fromEmail, List.of(toEmail), subject, bodyText, attachments);
            } else {
                SendEmailRequest request = new SendEmailRequest()
                        .withDestination(new Destination().withToAddresses(toEmail.split(",")))
                        .withMessage(
                                new Message()
                                        .withBody(new Body().withHtml(new Content().withCharset(CHARSET).withData(bodyText)))
                                        .withSubject(new Content().withCharset(CHARSET).withData(subject))
                        )
                        .withSource(fromEmail);

                sesClient.sendEmail(request);
                System.out.println("Email sent successfully to: " + toEmail);
            }


        } catch (Exception ex) {
            System.err.println("Error sending email: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void sendEmailWithAttachments(String fromEmail, List<String> toEmails, String subject, String bodyText, List<String> attachments) {
        try {

            Session session = Session.getDefaultInstance(new Properties());

            // Create a new MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            // Add sender and recipient addresses
            message.setFrom(new InternetAddress(fromEmail));
            InternetAddress[] toEmailList = new InternetAddress[toEmails.size()];
            for (int i = 0; i < toEmails.size(); i++) {
                toEmailList[i] = new InternetAddress(toEmails.get(i));
            }
            message.setRecipients(MimeMessage.RecipientType.TO, toEmailList);
            // Set email subject
            message.setSubject(subject);
            // Create a multipart message to hold the email body and attachments
            MimeMultipart multipart = new MimeMultipart();

            // Add text part
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(bodyText);
            multipart.addBodyPart(textPart);
            System.out.println("add text part ");
            // Add attachments
            for (String attachmentPath : attachments) {
                File attachment = new File(attachmentPath);
                DataSource dataSource = new FileDataSource(attachment);
                DataHandler dataHandler = new DataHandler(dataSource);
                MimeBodyPart attachmentPart = new MimeBodyPart();
                attachmentPart.setDataHandler(dataHandler);
                attachmentPart.setFileName(attachment.getName());
                multipart.addBodyPart(attachmentPart);
            }
            System.out.println("added all the attachments");
            // Set the content of the message
            message.setContent(multipart);

            // Convert the MimeMessage to a raw message
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            message.writeTo(outputStream);
            RawMessage rawMessage = new RawMessage(ByteBuffer.wrap(outputStream.toByteArray()));
            System.out.println("converted message to raw message ");
            // Create a SendRawEmailRequest
            SendRawEmailRequest rawEmailRequest = new SendRawEmailRequest(rawMessage);
            System.out.println("sending mail");
            // Send the email
            sesClient.sendRawEmail(rawEmailRequest);

            System.out.println("Email sent successfully.");

        } catch (MessagingException | IOException e) {
            System.out.println("Error sending email: " + e.getMessage());
        }
    }
}
