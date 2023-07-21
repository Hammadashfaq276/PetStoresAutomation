package api.test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;


import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;
import io.restassured.response.Response;

import api.endpoints.UserEndpoints;
import api.payload.User;


public class UserTest {
   
	Faker faker;
	User userPayload;
	
	@BeforeClass
	public void SetUp()
	{
		faker = new Faker();
		userPayload = new User();
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password());
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		
		
	}
	
	@Test(priority=1)
	public void Testpostdata()
	{
	
		Response response = UserEndpoints.CreateUser(userPayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(),200);
		
	}
	
	@Test(priority=2)
	public void TestGetUserByName()
	{
		
	   Response response = UserEndpoints.GetUser(this.userPayload.getUsername());
	   response.then().log().all();
	   Assert.assertEquals(response.getStatusCode(),200);
	   
	}
	
	@Test(priority=3)
	public void TestUpdateUserByName()
	{
		// update data 
		
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		
		Response response = UserEndpoints.UpdateUser(this.userPayload.getUsername(), userPayload);
		response.then().log().body();
		Assert.assertEquals(response.getStatusCode(),200);
		
		// get after update
		Response responseafterupdate = UserEndpoints.GetUser(this.userPayload.getUsername());
		Assert.assertEquals(responseafterupdate.getStatusCode(),200);
	}
	
	@Test(priority=4)
	public void TestDeleteUserByName()
	{
		
		Response response = UserEndpoints.DeleteUser(this.userPayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(),200);
		
	}
	
	
}
