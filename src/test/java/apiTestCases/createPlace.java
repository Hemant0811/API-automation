package apiTestCases;

import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import helper.Base;

import org.testng.Assert;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import requestPojo.LocationChild;
import requestPojo.createPojo;
import responsePojo.CreatePlaceResponsePOJO;

import static io.restassured.RestAssured.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class createPlace {
	String placeId;

	@Test(priority = 0)
	public void createNewPlaceOnGoogle() throws IOException {

		createPojo p = new createPojo();
		LocationChild c = new LocationChild();
		Faker f = new Faker();

		c.setLat(-38.383494);
		c.setLat(33.427362);

		p.setLocation(c);
		p.setAccuracy(50);
		p.setName(f.name().firstName());
		p.setPhone_number(f.phoneNumber().cellPhone());
		p.setAddress(f.address().city());

		List<String> typelist = new ArrayList<String>();
		typelist.add("shoe park");
		typelist.add("shop");

		p.setTypes(typelist);
		p.setWebsite("http://google.com");
		p.setLanguage("Hindi");

		RestAssured.baseURI = "https://rahulshettyacademy.com";

		Response res = given().log().all().spec(Base.setup())
				.body(p).when().post("maps/api/place/add/json").then().log().all().assertThat().statusCode(200)
				.extract().response();

		CreatePlaceResponsePOJO d = res.as(CreatePlaceResponsePOJO.class);

		String ActualStatus = d.getStatus();
		Assert.assertEquals(ActualStatus, "OK");

		String ActualScope = d.getScope();
		Assert.assertEquals(ActualScope, "APP");

		placeId = d.getPlace_id();

	}

	@Test(priority = 1) // Updation or put
	public void updatedata() throws IOException {

		Response updateResponse = given().given().log().all().queryParam("place_id", placeId).spec(Base.setup())
				.body("{\r\n" + "\"place_id\":\"" + placeId + "\",\r\n" + "\"address\":\"Sector 62 Noida\",\r\n"
						+ "\"key\":\"qaclick123\"\r\n" + "}")
			.when().put("maps/api/place/update/json").then().log().all().assertThat().statusCode(200).extract()
			.response();

		String UpdateRes = updateResponse.asString();
		JsonPath js2 = new JsonPath(UpdateRes);

		String SucessMessage = js2.getString("msg");
		Assert.assertEquals(SucessMessage, "Address successfully updated");

	}

//	@Test(priority = 2) // get request
//	public void getData() {
//		Response getResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
//				.when().get("maps/api/place/get/json").then().log().all().assertThat().statusCode(200).extract()
//				.response();
//
//		String GetRes = getResponse.asString();
//		JsonPath js1 = new JsonPath(GetRes);
//
//		String Accuracy = js1.getString("accuracy");
//		Assert.assertEquals(Accuracy, "50");
//
//		String Address = js1.getString("address");
//		Assert.assertEquals(Address, "Sector 62 Noida");
//
//	}

}
