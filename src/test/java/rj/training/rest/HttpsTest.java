package rj.training.rest;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class HttpsTest {

    @BeforeClass
    public void setup() {
        // Configure RestAssured to relax HTTPS validation
       // RestAssured.useRelaxedHTTPSValidation();
        RestAssured.port = 8443;  // Specify the HTTPS port
    }

    @Test
    public void testSecureEndpoint() {
        given()
        .relaxedHTTPSValidation().log().all()
        .auth().basic("raviuser", "ravipass")
        .when()
            .get("https://localhost:8443/myrestapp/secure/data")
            .then().log().all();
            //.statusCode(200);
    }
}

