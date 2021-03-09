package ru.oleg.sketchbook.security.service;

import com.sun.mail.smtp.SMTPAddressFailedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.oleg.sketchbook.model.Client;
import ru.oleg.sketchbook.model.StatusEmail;
import ru.oleg.sketchbook.security.RegAndAuthModel.ClientRegistrationDTO;
import ru.oleg.sketchbook.repository.UserRepo;

import java.util.Optional;

@Service
@Transactional
public class ClientRepositoryService {

    private final MailSending mailSending;
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    public ClientRepositoryService(MailSending mailSending, PasswordEncoder passwordEncoder, UserRepo userRepo) {
        this.mailSending = mailSending;
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
    }

    /**
     * Регистрирует пользователя и отправляет письмо с активацией Email
     * @param clientRegistrationDTO
     * @return True, если клиент зарегистрирован и письмо отправлено
     *          False, если такой email уже присутствует в базе данных
     */
    public boolean registration(ClientRegistrationDTO clientRegistrationDTO) throws SMTPAddressFailedException {
        Optional<Client> isClient = userRepo.findClientByEmail(clientRegistrationDTO.getEmail());

        if (isClient.isPresent())
            return false;

        Client client = userRepo.save(Client.convert(clientRegistrationDTO, passwordEncoder));
        mailSending.sent(client, "Activation code");
        return true;
    }

    public boolean activateEmail(String code){
        Optional<Client> optional = userRepo.findClientByActivationCode(code);
        if (optional.isPresent()){
            Client client = optional.get();
            client.setStatusEmail(StatusEmail.YES);
            client.setActivationCode(null);
            return true;
        }
        return false;
    }
}
