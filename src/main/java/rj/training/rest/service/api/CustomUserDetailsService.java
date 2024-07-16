package rj.training.rest.service.api;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import rj.training.rest.security.model.Users;
import rj.training.rest.security.repository.UserRepository;

@Service("digestuserdetail")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username);
                //.orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new User(
                user.getUsername(), 
              //  "ravipass",
                user.getPassword(), 
                new HashSet<String>(Arrays.asList(user.getRole())).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
    }
}

