package rj.training.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import rj.training.rest.security.model.User;
import rj.training.rest.security.repository.UserRepository;
import rj.training.rest.service.api.TokenService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        User storedUser = userRepository.findByUsername(user.getUsername());
           if (storedUser == null) throw new RuntimeException("stored user not found by user name");    
        String storedPassword = storedUser.getPassword();
           if (!tokenService.validatePassword(user.getPassword(), storedPassword)) {
            System.out.println("stored password: "+storedUser.getPassword()+ "actual passoword: "+user.getPassword());
        	throw new RuntimeException("Invalid password");
        }
        return tokenService.generateToken(user.getUsername());
    }
}

