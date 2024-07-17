package rj.training.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rj.training.rest.security.model.Users;
import rj.training.rest.service.UserService;

@RestController
@RequestMapping("/myrestapp/users")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Users registrationDto) {
        Users u = service.createUser(registrationDto.getUsername(), registrationDto.getPassword());
        return ResponseEntity.ok("User registered successfully" + u.toString());
    }
}


