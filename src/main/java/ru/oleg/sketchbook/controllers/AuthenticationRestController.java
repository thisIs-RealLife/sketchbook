package ru.oleg.sketchbook.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import ru.oleg.sketchbook.model.Client;
import ru.oleg.sketchbook.repository.UserRepo;
import ru.oleg.sketchbook.security.JwtTokenProvider;
import ru.oleg.sketchbook.security.RegAndAuthModel.ClientAuthenticationDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationRestController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepo userRepo;

    public AuthenticationRestController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserRepo userRepo) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepo = userRepo;
    }

    @PostMapping("/login")
    public ResponseEntity<?> auth(@RequestBody ClientAuthenticationDTO request) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            Client client = userRepo.findClientByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            System.out.println("User found = " + client.toString());
            String token = jwtTokenProvider.creatToken(request.getEmail(), client.getRole().name());
            Map<String, String> map = new HashMap<>();
            map.put("token", token);
            return ResponseEntity.ok(map);
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid email or password", HttpStatus.FORBIDDEN);
        }

    }
    @GetMapping("/hello")
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("Helloooo");
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
