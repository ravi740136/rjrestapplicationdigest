package rj.training.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import rj.training.rest.security.model.Users;
import rj.training.rest.security.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createUser(String username, String rawPassword) {
        String encodedPassword = "{noop}" + rawPassword;
        // or "{bcrypt}" + passwordEncoder.encode(rawPassword);
        Users user = new Users();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setRole("USER");
        userRepository.save(user);
    }

    // other methods
}
