package rj.training.rest.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rj.training.rest.security.model.User;

public interface UserRepository extends JpaRepository<User, String> {
    User  findByUsername(String username);

	    User findByToken(String token);
}
