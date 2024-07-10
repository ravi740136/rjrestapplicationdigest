package rj.training.rest.service.api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import rj.training.rest.security.model.User;
import rj.training.rest.security.repository.UserRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
                //.orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), 
                user.getPassword(), 
                new HashSet<String>(Arrays.asList(user.getRole())).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
    }
}

