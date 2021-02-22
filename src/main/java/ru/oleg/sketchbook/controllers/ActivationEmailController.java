package ru.oleg.sketchbook.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.oleg.sketchbook.service.ClientRepositoryService;

@Controller
public class ActivationEmailController {

    private final ClientRepositoryService clientRepositoryService;

    public ActivationEmailController(ClientRepositoryService clientRepositoryService) {
        this.clientRepositoryService = clientRepositoryService;
    }



    @GetMapping("/activate/{code}")
    public String activate(@PathVariable String code, Model model) {
        boolean bo = clientRepositoryService.activateEmail(code);
        System.out.println(bo);
        if (bo)
            model.addAttribute("message", "Email активирован!");
        else
            model.addAttribute("message", "Activation cod is not found!");
        return "active";
    }
}
