package rj.training.rest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import rj.training.rest.security.model.Users;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class UserControllerTest {

    @BeforeClass
    public void setup() {
        // Configure RestAssured to relax HTTPS validation
       // RestAssured.useRelaxedHTTPSValidation();
        RestAssured.baseURI = "http://localhost:8080/myrestapp";  // Specify the HTTPS port
    }

    @Test
    public void testRegisterUser() {
    	Users user = new Users();
    	user.setUsername("raviuser2");
    	user.setPassword("password");
        given()
        .contentType(ContentType.JSON)
        .basePath("/users").log().all()
        .body(user)
        .when()
            .post("/register")
            .then().log().all();
            //.statusCode(200);
    }
    
    @Test
    public void testGetUsers() {
    	 
        given()
        .contentType(ContentType.JSON)
        .auth().preemptive().basic("raviuser", "ravipass")
        //.auth().digest("raviuser2", "password")
        .basePath("/basicauthuser").log().all()
        .when()
            .get("/users")
            .then().log().all();
            //.statusCode(200);
    }
}

