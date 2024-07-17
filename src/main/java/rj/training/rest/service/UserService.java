package rj.training.rest.service;

import java.util.List;

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

    public Users createUser(String username, String rawPassword) {
    //String encodedPassword = rawPassword;
   String encodedPassword = passwordEncoder.encode(rawPassword);
        Users user = new Users();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setRole("ROLE_USER");
        return userRepository.save(user);
    }
    
    public List<Users> getAllUsers() {
    	return userRepository.findAll();	
    }

    // other methods
}
