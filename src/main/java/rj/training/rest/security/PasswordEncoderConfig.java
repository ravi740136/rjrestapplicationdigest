package rj.training.rest.security;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfig {

    @Bean(name = "digestpasswordencoder")
    public PasswordEncoder passwordEncoder() {
    	Map<String, PasswordEncoder> encoders = new HashMap<>();
    	encoders.put("noop", NoOpPasswordEncoder.getInstance());
    	return new DelegatingPasswordEncoder("noop", encoders);
      //  return PasswordEncoderFactories.createDelegatingPasswordEncoder();// For Digest Authentication
    }
}

