package rj.training.rest;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import rj.training.rest.model.Department;
import rj.training.rest.model.Employee;
import static org.hamcrest.Matchers.*; 

import static io.restassured.RestAssured.*;

/*
 * Here we are trying find closure on a complex object company, 
 * we are using multiple find closures in same statement
 * Depending on the context we can use it with get, getlist and getobject 
 * methods
 */
public class AuthTest {
@Test
public void testGetCompany() {
	RestAssured.baseURI = "http://localhost:8080/myrestapp";
   Response r =  given()
		   //preemptive basic and basic both will work
		   .auth().preemptive().basic("raviuser", "ravipass")
		   .basePath("/basicauthuser/getMovies").log().all().get();
   r.then().log().all();
}

@Test
public void testFormLoginGetCompany() {
    Response loginResponse = given().log().all() 
        .formParam("username", "raviuser")
        .formParam("password", "ravipass")
        .post("/login");
    loginResponse.then().log().all();
    loginResponse.then()
        .statusCode(302);  // Form login typically results in a redirect

    String sessionId = loginResponse.getCookie("JSESSIONID");

    given().log().all()
        .cookie("JSESSIONID", sessionId)
        .get("/myrestapp/basicauthuser/getMovies")
        .then().log().all()
        .statusCode(200);
}

@Test
public void testAPITokenGetCompany() {
	String requestbody = "{\"username\": \"raviuser\", \"password\": \"ravipass\"}";
	requestbody.replace("'", "\"");
	Response response = given()
             .contentType("application/json")
             .body(requestbody)
         .when()
             .post("/auth/login");
	response.then()
.log().all();
	String token = response.asString();
	Response r = given().log().all()
    .header("Authorization", "Bearer " + token)
.when()
    .get("/myrestapp/basicauthuser/getMovies");
r.then().log().all();
    //.statusCode(200)
   // .body("message", equalTo("Hello, secured world!"));
}

}


