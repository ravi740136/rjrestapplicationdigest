package rj.training.rest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import rj.training.rest.model.Movie;
import rj.training.rest.model.UploadResponse;

public class MoviesPostTest {

	@BeforeClass
	public void bc() {
		//when one of the urls are configured for https, http is not working,
		//so setting https for all urls
		RestAssured.baseURI = "http://localhost:8080/myrestapp";
				//RestAssured.baseURI = "https://localhost:8443/myrestapp";
		RestAssured.useRelaxedHTTPSValidation();
	}
	
	@Test
	public void testRestAppPost() {
		Map<String, String> address = new HashMap();
		address.put("hno", "5");
		address.put("street", "kknagar");
		address.put("city", "trichy");

		Map<String, Object> m = new HashMap();
		m.put("id", 1);
		// it should not be allowed to set null values, exception should be thrown
		// as column is set as nullable false
		// m.put("name", null);
		m.put("name", "kalki");
		m.put("synopsis", address);

		
		System.out.println(RestAssured.baseURI);
		Response r = given().basePath("/movie")
				// default content type is plain text
				// so this is mandatory if we want to send json type
				.contentType(ContentType.JSON).body(m).log().all().when().post("/");
		System.out.println("log and assert===> ");
		r.then().log().all().assertThat().statusCode(equalTo(HttpStatus.SC_CREATED));
		Movie m1 = r.then().extract().body().as(Movie.class);
		System.out.println("====>end of logging");
		System.out.println("response as prettyprint :" + r.prettyPrint());
		System.out.println("response movie " + m1);
		System.out.println("direct raw response " + r.asPrettyString());
		System.out.println("response body as prettyprint " + r.body().asPrettyString());
	}

	@Test
	public void testRestAppPostDuplicateMovie() {
		Map<String, String> address = new HashMap();
		address.put("hno", "5");
		address.put("street", "kknagar");
		address.put("city", "trichy");

		Map<String, Object> m = new HashMap();
		m.put("id", 99990);
		m.put("name", "kalki");
		m.put("synopsis", address);

		//RestAssured.baseURI = "http://localhost:8080/myrestapp";
		System.out.println(RestAssured.baseURI);
		Response r = given().basePath("/movie")
				// default content type is plain text
				// so this is mandatory if we want to send json type
				.contentType(ContentType.JSON).body(m).log().all().when().post("/");
		System.out.println("log and assert===> ");
		r.then().log().all().assertThat().statusCode(equalTo(HttpStatus.SC_CREATED));

		Response r2 = given().basePath("/movie")
				// default content type is plain text
				// so this is mandatory if we want to send json type
				.contentType(ContentType.JSON).body(m).log().all().when().post("/");
		System.out.println("log and assert===> ");
		r2.then().log().all();
		r2.then().assertThat().statusCode(equalTo(org.springframework.http.HttpStatus.CONFLICT.value())).body("message",
				equalTo("the movie already exists"));

	}

	@Test
	public void testRestAppPostInvalidMovie() {
		Map<String, String> address = new HashMap();
		address.put("hno", "5");
		address.put("street", "kknagar");
		address.put("city", "trichy");

		Map<String, Object> m = new HashMap();
		m.put("id", 0);
		m.put("name", "kalki");
		m.put("synopsis", address);

		//RestAssured.baseURI = "http://localhost:8080/myrestapp";
		System.out.println(RestAssured.baseURI);
		Response r = given().basePath("/movie")
				// default content type is plain text
				// so this is mandatory if we want to send json type
				.contentType(ContentType.JSON).body(m).log().all().when().post("/");
		System.out.println("log and assert===> ");
		r.then().log().all().assertThat().statusCode(equalTo(HttpStatus.SC_BAD_REQUEST)).body("message",
				equalTo("invalid movie id"));
	}

	@Test
	public void verifyMovieCount() {
		//RestAssured.baseURI = "http://localhost:8080/myrestapp";
		Response r = given().basePath("/movies/count").when().get();
		r.then().log().all();
		System.out.println("===" + r.getBody().jsonPath().getLong("$"));
		// r.then().assertThat().body(equalTo(1)); fails as it checks for value <1>
		r.then().assertThat().body("$", equalTo(1));
		Long v = r.then().extract().as(Long.class);
		Assert.assertEquals(v.longValue(), 1);
	}

	@Test
	public void testDeleteMovie() {
		//RestAssured.baseURI = "http://localhost:8080/myrestapp";
		Response r = given().basePath("/movies/1").when().delete();
		r.then().log().all();
	}

	@Test
	public void testGetAllMovies() {
		//RestAssured.baseURI = "http://localhost:8080/myrestapp";
		Response r = given().log().all().basePath("/movies").when().get();
		r.then().log().all();
	}
	
	@Test
	public void testUpdateMovie() {
		//RestAssured.baseURI = "http://localhost:8080/myrestapp";
		Response r = given().basePath("/movie/1").when().get();
		r.then().log().all();
		Movie m = r.as(Movie.class);
		m.setName("modifiedmovie");
		Response r1 = given().basePath("/movie/1").contentType(ContentType.JSON).body(m).when().put();
		r1.then().log().all();
	}

	@Test
	public void testPatchMovie() {
		//RestAssured.baseURI = "http://localhost:8080/myrestapp";
		Response r = given().basePath("/movie/1").when().get();
		r.then().log().all();
		Movie m = r.as(Movie.class);
		m.setName("modifiedmovie");
		Response r1 = given().basePath("/movie/1").contentType(ContentType.JSON).body(m).when().patch();
		r1.then().log().all();
	}

	@Test
	public void testUploadMultiPartDescription() {
		//RestAssured.baseURI = "http://localhost:8080/myrestapp";
		Response r = given().basePath("/movie/{fu}")
//as it is multipart request rest assured
//will by default set the content type as multipart/form-data
//.contentType("multipart/form-data")		
				.pathParam("fu", "fileupload").multiPart("file", new File("src/test/resources/movieslist.txt"))
//both query param and form param are working fine		
				.queryParam("multifilename", "mymovieslist")
				// .formParam("multifilename", "mymovieslist")
				.log().all().when().post();
		r.then().log().all();
	}

	@Test
	public void testQueryParamsRequest() {
		//RestAssured.baseURI = "http://localhost:8080/myrestapp";
		Response r = given().basePath("/movie/uploaddata")
				.contentType("application/json")
				.queryParam("multifilename", "mymovieslist")
				.queryParam("originalFileName", "movieslist.txt")	
				.queryParam("fileType", "application/octet-stream")
				.queryParam("bytes", 100)
				.log().all().when().post();
		r.then().log().all();
	}
	
	/*
	 * form parameters are accepted by @requestparam annotation when format is 
	 * form url encoded or when it is multipart request
	 */
	@Test
	public void testFormParamsRequest() {
		//RestAssured.baseURI = "http://localhost:8080/myrestapp";
		Response r = given().
			//config(RestAssured.config().jsonConfig(jsonConfig)).	
			basePath("/movie/uploaddataarr")
				.contentType("application/x-www-form-urlencoded")
				.formParam("multifilename", "mymovieslist")
				.formParam("originalFileName", "movieslist.txt")	
				.formParam("fileType", "application/octet-stream")
				.formParam("bytes", 100)
				.log().all().when().post();
	r.then().log().all()
		.assertThat()
		.body("[0].multiFileName", equalTo("mymovieslist"))
		.body("[0].originalFileName", equalTo("movieslist.txt"))
		.body("[0].bytes", equalTo(100));
	
	long resp = r.then().extract().jsonPath().getLong("[1].bytes");
	System.out.println(resp);
	System.out.println(JsonPath.from(r.asString()).get("[1].bytes").toString());
System.out.println("array of upload datat: "+Arrays.toString(r.as(UploadResponse[].class)));
	}
	
	@Test
	public void testFormParamsRequestReturnAsList() {
		//RestAssured.baseURI = "http://localhost:8080/myrestapp";
		Response r = given().	
			basePath("/movie/uploaddatalist") 
				.contentType("application/x-www-form-urlencoded")
				.formParam("multifilename", "mymovieslist")
				.formParam("originalFileName", "movieslist.txt")	
				.formParam("fileType", "application/octet-stream")
				.formParam("bytes", 100)
				.log().all().when().post();
	r.then().log().all()
		.assertThat() 
		.body("[0].multiFileName", equalTo("mymovieslist"))
		.body("[0].originalFileName", equalTo("movieslist.txt"))
		.body("[0].bytes", equalTo(100));
	
	long resp = r.then().extract().jsonPath().getLong("[1].bytes");
	System.out.println(resp);
	System.out.println(JsonPath.from(r.asString()).get("[1].bytes").toString());
	List<UploadResponse> lu = r.as(new TypeRef<List<UploadResponse>>() {
	});
	System.out.println(lu.get(0));
	System.out.println("total list: "+lu);
	String jps = r.then().assertThat().extract().jsonPath().getString("[0].originalFileName");
	System.out.println("extract string "+ jps);	
	}
	
	@Test
	public void testGpathFind() {
		//RestAssured.baseURI = "http://localhost:8080/myrestapp";
		Response r = given().	
			basePath("/movie/uploaddatalist") 
				.contentType("application/x-www-form-urlencoded")
				.formParam("multifilename", "mymovieslist")
				.formParam("originalFileName", "movieslist.txt")	
				.formParam("fileType", "application/octet-stream")
				.formParam("bytes", 100)
				.log().all().when().post();
	r.then().log().all()
		.assertThat() 
		.body("[0].multiFileName", equalTo("mymovieslist"))
		.body("[0].originalFileName", equalTo("movieslist.txt"))
		.body("[0].bytes", equalTo(100));
	//String targetName = "mymovieslist2";
	//int index = r.then().extract().body().jsonPath().indexOf(item -> item.get("multiFileName").equals(targetName));

	Map<String, Object> result = r.then().extract().body().jsonPath().get("find {it.bytes == 100}");
	System.out.println("found response: " + result);
	
	ObjectMapper objectMapper = new ObjectMapper();
	UploadResponse ur = objectMapper.convertValue(result, UploadResponse.class);

	System.out.println(ur);
UploadResponse ur2 =	r.then().extract().body().jsonPath().getObject("find {it.bytes == 100}", UploadResponse.class);
	System.out.println("ur2: "+ur2);
	}
	
	@Test
	public void testGetFromJsonPath() {
		//RestAssured.baseURI = "http://localhost:8080/myrestapp";
		Response r = given().	
			basePath("/movie/uploaddatalist") 
				.contentType("application/x-www-form-urlencoded")
				.formParam("multifilename", "mymovieslist")
				.formParam("originalFileName", "movieslist.txt")	
				.formParam("fileType", "application/octet-stream")
				.formParam("bytes", 100)
				.log().all().when().post();
	r.then().log().all();
	//here it will return as list of map objects
	System.out.println(r.then().extract().jsonPath().get().toString());
	//object mapper can be used to convert object graph or map to specific complex object
	Map<String, Object> omap = r.then().extract().jsonPath().get("[0]");
	UploadResponse ur = new ObjectMapper().convertValue(omap, UploadResponse.class);
	System.out.println("upload response=>" + ur);
	
	//using get object to fetch the complex object
UploadResponse ur2= r.then().extract().jsonPath().getObject("[0]", UploadResponse.class);
	
	System.out.println("upload response2=>" + ur2);
	}
	
	//response jsonpath is same as 
	//response extract jsonpath or response body jsonpath
	// or response extract body jsonpath, all are same
	@Test
	public void testJsonPathFromResponseDirectly() {
		//RestAssured.baseURI = "http://localhost:8080/myrestapp";
		Response r = given().	
			basePath("/movie/uploaddatalist") 
				.contentType("application/x-www-form-urlencoded")
				.formParam("multifilename", "mymovieslist")
				.formParam("originalFileName", "movieslist.txt")	
				.formParam("fileType", "application/octet-stream")
				.formParam("bytes", 100)
				.log().all().when().post();
	r.then().log().all();
	//here it will return as list of map objects
	System.out.println(r.jsonPath().get().toString());
	//object mapper can be used to convert object graph or map to specific complex object
	Map<String, Object> omap = r.jsonPath().get("[0]");
	UploadResponse ur = new ObjectMapper().convertValue(omap, UploadResponse.class);
	System.out.println("upload response=>" + ur);
	
	//using get object to fetch the complex object
UploadResponse ur2= r.body().jsonPath().getObject("[0]", UploadResponse.class);
	
	System.out.println("upload response2=>" + ur2);
	}
	
	@Test
	public void testGetObjectArraytAndGetList() {
		//RestAssured.baseURI = "http://localhost:8080/myrestapp";
		Response r = given().	
			basePath("/movie/uploaddatalist") 
				.contentType("application/x-www-form-urlencoded")
				.formParam("multifilename", "mymovieslist")
				.formParam("originalFileName", "movieslist.txt")	
				.formParam("fileType", "application/octet-stream")
				.formParam("bytes", 100)
				.log().all().when().post();
	r.then().log().all();
	
	//getobject doesnot work, as writing list class listeral is difficult, also converting object to list is difficult
	//List<UploadResponse> movieslist = r.then().extract().jsonPath().getObject("", new TypeRef<List<UploadResponse>>() {});
	UploadResponse[] moviesarr = r.then().extract().jsonPath().getObject("", UploadResponse[].class);
	//System.out.println("getobject list " + movieslist);
	
	System.out.println("upload response array items: "+Arrays.toString(moviesarr));
	for (UploadResponse ur:moviesarr) {
		System.out.println(ur);
	}
	
	List<UploadResponse> movieslist = r.then().extract().jsonPath()
			.getList("", UploadResponse.class);
	System.out.println("getlist list " + movieslist);
	System.out.println("upload response list items: ");
	for (UploadResponse ur:movieslist) {
		System.out.println(ur);
	}
	}
	
	@Test
	public void testJsonPathFindMethod() {
		//RestAssured.baseURI = "http://localhost:8080/myrestapp";
		Response r = given().	
			basePath("/movie/uploaddatalist") 
				.contentType("application/x-www-form-urlencoded")
				.formParam("multifilename", "mymovieslist")
				.formParam("originalFileName", "movieslist.txt")	
				.formParam("fileType", "application/octet-stream")
				.formParam("bytes", 100)
				.log().all().when().post();
	r.then().log().all();
	//here it will return as list of map objects
	System.out.println("find results: =>");
	//find returns only one result, findAll returns all the results, we can ommit it ->
	//we can use find method with get, getobject and getlist methods
	System.out.println("find and get " + r.jsonPath().get("find{it->it.bytes > 50}").toString());
	System.out.println("findall and get " + r.jsonPath().get("findAll{it.bytes > 50}").toString());
	System.out.println("findall and getList " + r.jsonPath().getList("findAll{it.bytes > 50}", UploadResponse.class).toString());
	UploadResponse ur = r.jsonPath().getObject("find{it->it.bytes > 50}",UploadResponse.class );
    System.out.println("getobject "+ur);
	}
}
