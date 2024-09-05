package com.crypto.trading.controller;

import com.crypto.trading.config.JwtProvider;
import com.crypto.trading.modal.AuthResponse;
import com.crypto.trading.modal.User;
import com.crypto.trading.repository.UserRepository;
import com.crypto.trading.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @PostMapping("/signUp")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody User user) throws Exception {


        User emailExists = userRepository.findByEmail(user.getEmail());

        if (emailExists != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
        User newUser = new User();
        newUser.setFullName(user.getFullName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());

        userRepository.save(newUser);

        Authentication auth = new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                user.getPassword()
        );

        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = JwtProvider.generateToken(auth);

        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("Successfully registered");



        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("/signIn")
    public ResponseEntity<AuthResponse> logIn(@RequestBody User user) throws Exception {

        String userName = user.getEmail();
        String password = user.getPassword();

        Authentication auth = getAuthenticate(userName, password);

        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = JwtProvider.generateToken(auth);

        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("Login Success");



        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    private Authentication getAuthenticate(String userName, String password) {

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);

        if(userDetails == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        if (!password.equals(userDetails.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong password");
        }


        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
