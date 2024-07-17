package rj.training.rest.security.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "users")
public class Users {

    @Id
    private String username;
    private String password;
    private String role;
    private String token;

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

	public void setToken(String token) {
		// TODO Auto-generated method stub
		this.token = token;
	}
	
	public String getToken() {
		return token;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName()+" [username=" + username + ", password=" + password + ", role=" + role + ", token=" + token + "]";
	}
	
}
