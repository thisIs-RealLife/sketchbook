package ru.oleg.sketchbook.controllers;

import com.sun.mail.smtp.SMTPAddressFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.oleg.sketchbook.security.RegAndAuthModel.ClientRegistrationDTO;
import ru.oleg.sketchbook.security.service.ClientRepositoryService;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class RegistrationController {


    private final ClientRepositoryService clientRepositoryService;

    public RegistrationController(ClientRepositoryService clientRepositoryService) {
        this.clientRepositoryService = clientRepositoryService;
    }


    @PostMapping("/registration")
    public ResponseEntity registration(@Valid @RequestBody ClientRegistrationDTO clientRegistrationDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ResponseEntity<>("Вы ввели некорректные данные.", HttpStatus.BAD_REQUEST);

        try {
            if (!clientRepositoryService.registration(clientRegistrationDTO))
                return new ResponseEntity<>("Пользователь с таким email уже зарегистрирован.", HttpStatus.CONFLICT);
        } catch (SMTPAddressFailedException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Невозможно отправить пиьсмо с подтверждением на ваш адрес " +
                    "электронной почты. Пожалуйста, проверьте свои данные.", HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<>("Подтвердите Email.", HttpStatus.CREATED);
    }

}
