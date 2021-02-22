package ru.oleg.sketchbook.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.oleg.sketchbook.model.security.RegisterAndAuthModel.UserRegistration;
import ru.oleg.sketchbook.service.ClientRepositoryService;

import javax.validation.Valid;

@RestController
@CrossOrigin
public class RegistrationController {


    private final ClientRepositoryService clientRepositoryService;

    public RegistrationController(ClientRepositoryService clientRepositoryService) {
        this.clientRepositoryService = clientRepositoryService;
    }


    @PostMapping("/registration")
    public ResponseEntity registration(@Valid @RequestBody UserRegistration userRegistration, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return new ResponseEntity<>("Invalid data", HttpStatus.BAD_REQUEST);

        if (!clientRepositoryService.registration(userRegistration))
            return new ResponseEntity<>("Пользователь зарегистрирован", HttpStatus.CREATED);


        return new ResponseEntity<>("Подтвердите Email", HttpStatus.CONFLICT);
    }

}
