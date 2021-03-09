package ru.oleg.sketchbook.security.service;


import com.sun.mail.smtp.SMTPAddressFailedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.oleg.sketchbook.model.Client;

@Service
public class MailSending {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String username;

    public MailSending(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sent(Client client, String subject) throws SMTPAddressFailedException {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom(username);
        simpleMailMessage.setTo(client.getEmail());
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(defaultMessageActivation(client));
        javaMailSender.send(simpleMailMessage);

    }

    public String defaultMessageActivation(Client client){
        return String.format("Hello, %s! \n" +
                        "Welcome to Sketchbook. Please, visit new link for activation account: \n"+
                        "http://localhost:8080/activate/%s",
                client.getName(),
                client.getActivationCode());
    }
}
