package helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class Base {
	RequestSpecification RES;
	
	public static RequestSpecification setup() throws IOException {
		
	RequestSpecification RES =	new RequestSpecBuilder().setBaseUri(getConflitData("BaseURI")).addQueryParam("key","qaclick123")
		.addHeader("Content-Type", "application/json").build();
	return RES;	
		
		
	}
	
	public static String getConflitData(String key) throws IOException {
		
		FileInputStream file=new FileInputStream(System.getProperty("user.dir")+"/src/test/java/resources/env.properties/");
		Properties prob=new Properties();
		prob.load(file);
		return prob.getProperty(key);
	}

}
