package com.numan947.pmbackend.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * EmailService is a service class that handles the sending of account activation emails.
 *
 * Fields:
 * - from: The email address from which the email is sent, injected from application properties.
 * - mailSender: The JavaMailSender used to send the email.
 * - templateEngine: The SpringTemplateEngine used to process the email template.
 *
 * Methods:
 * - sendAccountActivationEmail: Sends an account activation email to the specified recipient.
 */
@Service
@RequiredArgsConstructor
public class EmailService {
    @Value("${application.security.mailing.frontend.sender}")
    private String from;
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    /**
     * Sends an account activation email to the specified recipient.
     *
     * @param to The recipient's email address.
     * @param name The recipient's name.
     * @param activationUrl The URL for account activation.
     * @param activationCode The activation code for account activation.
     * @param subject The subject of the email.
     * @throws MessagingException if an error occurs while sending the email.
     */
    public void sendAccountActivationEmail(String to, String name, String activationUrl, String activationCode, String subject) throws MessagingException {
        // Prepare the template using Thymeleaf -> email body
        Map<String, Object> properties = Map.of(
                "name", name,
                "confirmationUrl", activationUrl,
                "activationCode", activationCode
        );
        Context context = new Context();
        context.setVariables(properties);
        String htmlBody = templateEngine.process(EmailTemplates.ACCOUNT_ACTIVATION_TEMPLATE, context);
        prepareAndSend(to, subject, htmlBody);
    }

    public void sendPasswordResetEmail(String to, String name, String resetUrl, String resetCode, String subject) throws MessagingException {
        // Prepare the template using Thymeleaf -> email body
        Map<String, Object> properties = Map.of(
                "name", name,
                "resetUrl", resetUrl,
                "resetCode", resetCode
        );
        Context context = new Context();
        context.setVariables(properties);
        String htmlBody = templateEngine.process(EmailTemplates.PASSWORD_RESET_TEMPLATE, context);
        prepareAndSend(to, subject, htmlBody);
    }
    public void sendPasswordResetCompleteEmail(String email, String fullName, String subject) throws MessagingException {
        // Prepare the template using Thymeleaf -> email body
        Map<String, Object> properties = Map.of(
                "name", fullName
        );
        Context context = new Context();
        context.setVariables(properties);
        String htmlBody = templateEngine.process(EmailTemplates.PASSWORD_RESET_COMPLETE_TEMPLATE, context);
        prepareAndSend(email, subject, htmlBody);
    }
    public void sendInvitationEmail(String userEmail, String invitationCode, String projectName, String invitationAcceptURL) throws MessagingException {
        // Prepare the template using Thymeleaf -> email body
        Map<String, Object> properties = Map.of(
                "projectName", projectName,
                "invitationCode", invitationCode,
                "invitationAcceptURL",invitationAcceptURL
        );
        Context context = new Context();
        context.setVariables(properties);
        String htmlBody = templateEngine.process(EmailTemplates.INVITATION_TEMPLATE, context);
        prepareAndSend(userEmail, "Invitation to Project: "+projectName, htmlBody);
    }
    private void prepareAndSend(String to, String subject, String htmlBody) throws MessagingException {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            var helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED, UTF_8.name());
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom(from);
            helper.setText(htmlBody, true);
            // Send the email
            mailSender.send(mimeMessage);

    }


}