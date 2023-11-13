package com.mks.bookingsystemdemo.controller;

import com.mks.bookingsystemdemo.config.JwtTokenProvider;
import com.mks.bookingsystemdemo.dto.UserDto;
import com.mks.bookingsystemdemo.entity.Role;
import com.mks.bookingsystemdemo.entity.User;
import com.mks.bookingsystemdemo.repository.RoleRepository;
import com.mks.bookingsystemdemo.repository.UserRepository;
import com.mks.bookingsystemdemo.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.Collections;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private EmailService emailService;

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody UserDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new  UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDto signUpDto){

        // add check for username exists in a DB
        if(userRepository.existsByUsername(signUpDto.getUsername())){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        // add check for email exists in DB
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        // create user object
        User user = new User();
        user.setUsername(signUpDto.getUsername());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));

        emailService.sendVerifyEmail(user.getEmail(), user.getUsername());

        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);

    }












//    @PostMapping("/register")
//    public ResponseEntity<String> registerUser(@RequestBody User user) {
//        registrationService.registerUser(user);
//        return ResponseEntity.ok("User registered successfully. Check your email for verification.");
//    }
//
//    @GetMapping("/verify")
//    public ResponseEntity<String> verifyUser(@RequestParam String token) {
//        registrationService.verifyUser(token);
//        return ResponseEntity.ok("Email verified successfully.");
//    }
}

