package ru.oleg.sketchbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.oleg.sketchbook.model.Client;

import java.util.Optional;

public interface UserRepo extends JpaRepository<Client, Long> {
    Optional<Client> findClientByEmail(String email);
    Optional<Client> findClientByActivationCode(String code);
}
