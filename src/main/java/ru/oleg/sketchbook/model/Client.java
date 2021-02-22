package ru.oleg.sketchbook.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.oleg.sketchbook.model.security.RegisterAndAuthModel.UserRegistration;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Email не должен быть пуст")
    @Email
    @Column(unique = true)
    private String email;
    @NotBlank
    @NotBlank(message = "Name не должен быть пуст")
    private String name;
    @NotBlank
    @NotBlank(message = "Password не должен быть пуст")
    private String password;
    @NotBlank
    @NotBlank(message = "Surname не должен быть пуст")
    private String surname;

    private String activationCode;

    @Enumerated(EnumType.STRING)
    private StatusAccount statusAccount;
    @Enumerated(EnumType.STRING)
    private StatusEmail statusEmail;
    @Enumerated(EnumType.STRING)
    private Role role;

    public static Client convert(UserRegistration userRegistration, PasswordEncoder passwordEncoder){
        Client client = new Client();
        client.setEmail(userRegistration.getEmail());
        client.setName(userRegistration.getName());
        client.setPassword(passwordEncoder.encode(userRegistration.getPassword()));
        client.setSurname(userRegistration.getSurname());
        client.setStatusAccount(StatusAccount.ACTIVE);
        client.setStatusEmail(StatusEmail.NO);
        client.setRole(Role.USER);
        client.setActivationCode(UUID.randomUUID().toString());
        return client;
    }
}
