package rj.training.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rj.training.rest.security.model.Users;
import rj.training.rest.service.UserService;

@RestController
@RequestMapping("/myrestapp/basicauthuser")
public class LoginController {

    @Autowired
    private UserService service;

    @GetMapping("/users")
    public ResponseEntity<List<Users>> getAllUsers() {
        return ResponseEntity.ok(service.getAllUsers());
    }
}


