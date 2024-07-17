package rj.training.rest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import rj.training.rest.service.UserService;

@Component
public class DataInitializer {

	@Autowired
	private UserService service;

	@PostConstruct
	public void init() {
		service.createUser("raviuser", "ravipass");
	}
}
