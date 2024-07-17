package rj.training.rest.security;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfig {

    @Bean(name = "digestpasswordencoder")
    public PasswordEncoder passwordEncoder() {
    	String encodingId = "bcrypt";
		Map<String, PasswordEncoder> encoders = new HashMap<>();
		encoders.put(encodingId, new BCryptPasswordEncoder());
    	encoders.put("noop", NoOpPasswordEncoder.getInstance());
    	encoders.put("MD5", new MessageDigestPasswordEncoder("MD5"));
    	return new DelegatingPasswordEncoder("noop", encoders);
      //return PasswordEncoderFactories.createDelegatingPasswordEncoder();// For Digest Authentication
    }
}

