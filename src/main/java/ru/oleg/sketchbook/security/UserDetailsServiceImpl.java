package ru.oleg.sketchbook.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.oleg.sketchbook.model.Client;
import ru.oleg.sketchbook.repository.UserRepo;


@Service()
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepo userRepo;

    public UserDetailsServiceImpl( UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Получаем клиента....");
        Client client = userRepo.findClientByEmail(email).orElseThrow(()-> new UsernameNotFoundException("Email is invalid"));



        return SecurityUser.convert(client);
    }
}
