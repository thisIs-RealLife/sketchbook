package ru.oleg.sketchbook.security.RegAndAuthModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientRegistrationDTO {
    @NotBlank
    @NotBlank(message = "Name не должен быть пуст")
    private String name;
    @NotBlank
    @NotBlank(message = "Password не должен быть пуст")
    private String password;

    @NotBlank(message = "Email не должен быть пуст")
    @Email
    private String email;

    @NotBlank
    @NotBlank(message = "Surname не должен быть пуст")
    private String surname;
}
