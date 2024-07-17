package rj.training.rest.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rj.training.rest.security.model.Users;

public interface UserRepository extends JpaRepository<Users, String> {
	Users findByUsername(String username);
	Users findByToken(String token);
}
