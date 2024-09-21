package com.numan947.pmbackend.security.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@RequiredArgsConstructor
public class EmailService {
    @Value("${application.security.mailing.frontend.sender}")
    private String from;
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    public void sendAccountActivationEmail( String to,
                                            String name,
                                            String activationUrl,
                                            String activationCode,
                                            String subject) throws MessagingException {

        // Prepare the template using Thymeleaf -> email body
        Map<String, Object>properties = Map.of(
                "name",name,
                "confirmationUrl",activationUrl,
                "activationCode",activationCode
        );
        Context context = new Context();
        context.setVariables(properties);
        String htmlBody = templateEngine.process(EmailTemplates.ACCOUNT_ACTIVATION_TEMPLATE,context);

        // Prepare the email
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        var helper = new MimeMessageHelper(mimeMessage,MimeMessageHelper.MULTIPART_MODE_MIXED,UTF_8.name());
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setFrom(from);
        helper.setText(htmlBody,true);

        // Send the email
        mailSender.send(mimeMessage);
    }
}
