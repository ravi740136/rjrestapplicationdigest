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
public class CompanyFindTest {
@Test
public void testGetCompany() {
	RestAssured.baseURI = "http://localhost:8080/myrestapp";
   Response r =  given().basePath("/company").log().all().get();
  //it represents current entity evem if parameter it-> is not passed, it is special variable
   Department d = r.then().log().all().extract().jsonPath().getObject("myCompany.departments.find{it.name == 'Engineering'}", Department.class);
   System.out.println("Department "+d);
  
   //to use any other variables like emp we need to pass them as parameter like emp->, otherwise directly they do not work
   Employee e = r.then().log().all().extract().jsonPath().getObject("myCompany.departments.find{dept->dept.name == 'Engineering'}.employees.find{emp->emp.name == 'sonika'}", Employee.class);
   System.out.println("Employee "+e);
}

@Test
public void testGetCompanyAssertions() {
	RestAssured.baseURI = "http://localhost:8080/myrestapp";
   Response r =  given().basePath("/company").log().all().get();
   //after then we can use assertions
   r.then()
   .statusCode(200)
   .body("myCompany.departments", hasSize(1))
   .body("myCompany.departments[0].employees", hasSize(2))
   //equalto and is are almost same
   .body("myCompany.departments[0].name", equalTo("Engineering"))
   .body("myCompany.departments[0].name", is("Engineering"))
   .body("myCompany.departments[0].name", startsWith("Engi"))
   .body("myCompany.departments[0].name", endsWith("ing"))
   .body("myCompany.departments[0].name", hasLength(11))
   //partial match
   .body("myCompany.departments[0].name", containsString("Engineer"))
   //here we are checking in ids of all the departments
   .body("myCompany.departments.id", hasItem(1))
   //here we are checking in names of all the departments
   //has item is used to check individual value is present in list of values
   .body("myCompany.departments.name", hasItem("Engineering"))
   .body("myCompany.departments[0].employees.name", hasItems("ravi","sonika"))
  //an object json can be verified using haskey, hasvalue, hasentry
   .body("myCompany.departments[0]", hasKey("employees"))
   .body("myCompany.departments[0]", hasKey("id"))
   .body("myCompany.departments[0]", hasValue(1))
   .body("myCompany.departments[0]", hasEntry("id", 1))
   //checking for multiple things and all should be correct
   .body("myCompany.departments[0]", allOf(hasEntry("id", 1), hasKey("id")))
   .body("myCompany.departments.name", anyOf(hasItem("Engineering"), hasItem("medicine")))
   //specify condition that checks all the items in the list
   .body("myCompany.departments[0].employees.id", everyItem(greaterThan(0)))
   //hasItems and containsInAnyOrder are almost the same regardingFunctionality
   .body("myCompany.departments[0].employees.id", containsInAnyOrder(2,1))
   .body("myCompany.departments[0].employees.id", containsInRelativeOrder(1,2));
  }
}

