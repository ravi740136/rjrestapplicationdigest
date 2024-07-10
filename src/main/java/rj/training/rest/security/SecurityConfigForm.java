package rj.training.rest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

import rj.training.rest.security.model.User;
import rj.training.rest.security.repository.UserRepository;

@Configuration
public class SecurityConfigForm {

    @Autowired
    private UserRepository userRepository;

    //@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/myrestapp/basicauthuser/**").hasRole("USER")
                    .anyRequest().permitAll()
            )
            .formLogin(formLogin -> 
            formLogin
                .loginPage("/login")
                .permitAll()
        )
            .csrf().disable()
            .headers().frameOptions().disable();

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            User user = userRepository.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }
            return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
        };
    }
}
