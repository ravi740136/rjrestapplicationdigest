package rj.training.rest.service.api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rj.training.rest.security.model.User;
import rj.training.rest.security.repository.UserRepository;

import java.util.UUID;

@Service
public class TokenService {

    @Autowired
    private UserRepository userRepository;

    // Generate a token for a given username and save it to the user record
    public String generateToken(String username) {
        String token = UUID.randomUUID().toString();
        User user = userRepository.findByUsername(username);
                
        user.setToken(token);
        System.out.println("user====="+user);
        userRepository.save(user);
        return token;
    }

    // Validate the token and retrieve the associated username
    public boolean validateToken(String token, String username) {
         User user = userRepository.findByToken(token);
                if (username.equals(user.getUsername()))
                return true;
                
                return false;
    }

    // Retrieve the username associated with the token
    public String getUsernameFromToken(String token) {
        User user =  userRepository.findByToken(token);
        if (user == null) throw new RuntimeException("User not found with given token");
                return user.getUsername();
    }
    

    public boolean validatePassword(String userPassword, String storedPassword) {
        if (storedPassword.startsWith("{noop}")) {
            return userPassword.equals(storedPassword.substring(6));
        }        
        	return userPassword.equals(storedPassword);
    }
}

