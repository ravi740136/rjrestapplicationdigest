package rj.training.rest;

import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.internal.http.Status;
import io.restassured.response.Response;
import rj.training.rest.model.Movie;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.Matchers.*;

public class MoviesPostTest {

	
	
	@Test
	public void testRestAppPost() {
	Map<String, String> address =new HashMap();
	address.put("hno", "5");
	address.put("street", "kknagar");
	address.put("city", "trichy");
		
		Map<String,Object> m = new HashMap();
	m.put("id",1);
	//it should not be allowed to set null values, exception should be thrown
	//as column is set as nullable false
	//m.put("name", null);
	m.put("name", "kalki");
	m.put("synopsis", address);
		
		RestAssured.baseURI = "http://localhost:8080/myrestapp";
	System.out.println(RestAssured.baseURI);
	Response r = 	given().basePath("/movie")
	//default content type is plain text 
	//so this is mandatory if we want to send json type
	.contentType(ContentType.JSON)
	.body(m).log().all()
	.when().post("/");
	System.out.println("log and assert===> ");
	r.then().log().all().assertThat()
	.statusCode(equalTo(HttpStatus.SC_CREATED));
	Movie m1 = r.then().extract().body().as(Movie.class);
	System.out.println("====>end of logging");
	System.out.println("response as prettyprint :" + r.prettyPrint());
	System.out.println("response movie " + m1);
	System.out.println("direct raw response " + r.asPrettyString());
	System.out.println("response body as prettyprint "+ r.body().asPrettyString());
	}
	@Test
	public void testRestAppPostDuplicateMovie() {
	Map<String, String> address =new HashMap();
	address.put("hno", "5");
	address.put("street", "kknagar");
	address.put("city", "trichy");
		
		Map<String,Object> m = new HashMap();
	m.put("id",99990);
	m.put("name", "kalki");
	m.put("synopsis", address);
		
		RestAssured.baseURI = "http://localhost:8080/myrestapp";
	System.out.println(RestAssured.baseURI);
	Response r = 	given().basePath("/movie")
	//default content type is plain text 
	//so this is mandatory if we want to send json type
	.contentType(ContentType.JSON)
	.body(m).log().all()
	.when().post("/");
	System.out.println("log and assert===> ");
	r.then().log().all().assertThat()
	.statusCode(equalTo(HttpStatus.SC_CREATED));
	
	Response r2 = given().basePath("/movie")
	//default content type is plain text 
	//so this is mandatory if we want to send json type
	.contentType(ContentType.JSON)
	.body(m).log().all()
	.when().post("/");
	System.out.println("log and assert===> ");
	r2.then().log().all();
	r2.then().assertThat()
	.statusCode(equalTo(org.springframework.http.HttpStatus.CONFLICT.value()))
	.body("message", equalTo("the movie already exists"));
	
	}

	@Test
	public void testRestAppPostInvalidMovie() {
	Map<String, String> address =new HashMap();
	address.put("hno", "5");
	address.put("street", "kknagar");
	address.put("city", "trichy");
		
		Map<String,Object> m = new HashMap();
	m.put("id",0);
	m.put("name", "kalki");
	m.put("synopsis", address);
		
		RestAssured.baseURI = "http://localhost:8080/myrestapp";
	System.out.println(RestAssured.baseURI);
	Response r = 	given().basePath("/movie")
	//default content type is plain text 
	//so this is mandatory if we want to send json type
	.contentType(ContentType.JSON)
	.body(m).log().all()
	.when().post("/");
	System.out.println("log and assert===> ");
	r.then().log().all().assertThat()
	.statusCode(equalTo(HttpStatus.SC_BAD_REQUEST))
	.body("message", equalTo("invalid movie id"));
}
	
	@Test
	public void verifyMovieCount() {
		RestAssured.baseURI = "http://localhost:8080/myrestapp";
		Response r =given().basePath("/movies/count").when()
		.get();
		r.then().log().all();
	System.out.println("==="+r.getBody().jsonPath().getLong("$"));
	//r.then().assertThat().body(equalTo(1)); fails as it checks for value <1>
	r.then().assertThat().body("$", equalTo(1));
	Long v = r.then().extract().as(Long.class);
	Assert.assertEquals(v.longValue(), 1);
	}
	
	@Test
	public void testDeleteMovie() {
		RestAssured.baseURI = "http://localhost:8080/myrestapp";
		Response r =given().basePath("/movies/1").when()
		.delete();
		r.then().log().all();
	}
	
	@Test
	public void testUpdateMovie() {
		RestAssured.baseURI = "http://localhost:8080/myrestapp";
		Response r =given().basePath("/movie/1").when()
		.get();
		r.then().log().all();
		Movie m = r.as(Movie.class);
		m.setName("modifiedmovie");
		Response r1 =given().basePath("/movie/1")
				.contentType(ContentType.JSON)
				.body(m)
			.when()
			.put();
		r1.then().log().all();
	}
	
	@Test
	public void testPatchMovie() {
		RestAssured.baseURI = "http://localhost:8080/myrestapp";
		Response r =given().basePath("/movie/1").when()
		.get();
		r.then().log().all();
		Movie m = r.as(Movie.class);
		m.setName("modifiedmovie");
		Response r1 =given().basePath("/movie/1")
				.contentType(ContentType.JSON)
				.body(m)
			.when()
			.patch();
		r1.then().log().all();
	}
}
