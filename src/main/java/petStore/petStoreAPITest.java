package petStore;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;

import org.junit.BeforeClass;
import org.junit.Test;



public class petStoreAPITest {

	@BeforeClass
	public static void urlSetup() {
		baseURI = "https://petstore.swagger.io";
		basePath = "/v2";
	}

	@Test
	public void addNewPet() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{\"id\": 1515,\"category\": {\"id\": 1,\"name\": \"cat\"},\"name\": \"Albert\",\"photoUrls\": [\"string\"],\"tags\": [{\"id\": 0,\"name\": \"string\"}],\"status\": \"available\"}")
		.when()
			.post("/pet")
		.then()
			.log().all()
			.statusCode(200)
			.body("id", is(1515))
			.body("name", is("Albert"));
	}
	
	@Test
	public void changePetName() {
		given()
			.log().all()
			.contentType("application/json")
			.body("{\"id\": 1515,\"category\": {\"id\": 1,\"name\": \"cat\"},\"name\": \"Peter\",\"photoUrls\": [\"string\"],\"tags\": [{\"id\": 0,\"name\": \"string\"}],\"status\": \"available\"}")
		.when()
			.put("/pet")
		.then()
			.log().all()
			.statusCode(200)
			.body("id", is(1515))
			.body("name", is("Peter"));
	}

	@Test
	public void verifyPetStatus() {
		given()
		.when()
			.get("/pet/123")
		.then()
			.log().all()
			.statusCode(200)
			.body("id", is(123))
			.body("status",is("available"));
	}

	@Test
	public void placeOrder() {

		given()
			.log().all()
			.contentType("application/json")
			.body("{\"id\": 1234,\"petId\": 200,\"quantity\": 2,\"shipDate\": \"2022-01-25T06:10:42.687Z\",\"status\": \"placed\",\"complete\": true}")
		.when()
			.post("/store/order")
		.then()
			.log().all()
			.statusCode(200)
			.body("id", is(1234))
			.body("status", is("placed"));
	}

	@Test
	public void notDeleteInexistentOrder() {

		given()
			.log().all()
		.when()
			.delete("/store/order/12")
		.then()
			.log().all()
			.statusCode(404)
			.body("message",is("Order Not Found"));
	}

}

