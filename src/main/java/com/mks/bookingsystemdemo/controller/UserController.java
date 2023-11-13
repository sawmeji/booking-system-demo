package com.mks.bookingsystemdemo.controller;

import com.mks.bookingsystemdemo.dto.UserDto;
import com.mks.bookingsystemdemo.entity.User;
import com.mks.bookingsystemdemo.repository.UserRepository;
import com.mks.bookingsystemdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/getProfile")
    public User getUser(@RequestParam String email){
        return userService.findUserByEmail(email);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody UserDto dto) {
         userService.changePassword(dto);
        return new ResponseEntity<>("Password Changed successfully!.", HttpStatus.OK);
    }

    @PostMapping("/updateProfile")
    public ResponseEntity<String> updateProfile(@RequestBody UserDto dto) {
        userService.updateProfile(dto);
        return new ResponseEntity<>("Profile Updated successfully!.", HttpStatus.OK);
    }
}
