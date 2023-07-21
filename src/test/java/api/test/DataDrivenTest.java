package api.test;

import org.testng.Assert;
import org.testng.annotations.Test;
import api.endpoints.UserEndpoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class DataDrivenTest 
{	
	
	@Test(priority=1,dataProvider="Data",dataProviderClass=DataProviders.class)
	public void Testpostdata(String userId,String userName,String fname,String lname,String useremail,String pwd,String ph )
	{
		User userPayload = new User();
		userPayload.setId(Integer.parseInt(userId));
		userPayload.setUsername(userName);
		userPayload.setFirstName(fname);
		userPayload.setLastName(lname);
		userPayload.setEmail(useremail);
		userPayload.setPassword(pwd);
		userPayload.setPhone(ph);
		Response response = UserEndpoints.CreateUser(userPayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(),200);
	}
   
	@Test(priority=2,dataProvider="UserNames",dataProviderClass=DataProviders.class)
	public void TestDeleteByUserName(String userName)
	{
		Response response = UserEndpoints.DeleteUser(userName);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(),200);
	}
}

