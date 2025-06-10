package com.juaracoding.smartpro_rest_api.controller;

import com.juaracoding.smartpro_rest_api.util.DataGenerator;
import com.juaracoding.smartpro_rest_api.util.TokenGenerator;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PurchaseRequestControllerTest extends AbstractTestNGSpringContextTests {
    private String token;
    private DataGenerator dataGenerator;
    private Random rand;
    private JSONObject req;

    @BeforeClass
    private void init() {
        token = new TokenGenerator(AuthControllerTest.authorization).getToken();
        dataGenerator = new DataGenerator();
        rand = new Random();
        req = new JSONObject();
    }

    @Test(priority = 0)
    void createPurchaseRequest() {
        Response response;
        String itemName = dataGenerator.dataBarang();
        String itemDesc = dataGenerator.dataDescription(255);
        Integer reqQuantity = rand.nextInt(150);
        String unit = dataGenerator.dataDescription(10);
        LocalDate expectedDate = LocalDate.now().plusDays(rand.nextInt(1, 31));

        try {
            req.put("item-name", itemName);
            req.put("item-description", itemDesc);
            req.put("unit", unit);
            req.put("requested-quantity", reqQuantity);
            req.put("expected-date", expectedDate);
            req.put("status", 0);

            // Sending POST request to create PurchaseRequest
            response = given()
                    .header("Content-Type", "application/json")
                    .header("accept", "*/*")
                    .header(AuthControllerTest.AUTH_HEADER, token)
                    .body(req)
                    .request(Method.POST, "/purchase-request");

            int intResponse = response.getStatusCode();
            JsonPath jsonPath = response.jsonPath();
            System.out.println(response.getBody().prettyPrint());

            // Validating the response
            Assert.assertEquals(intResponse, 201);  // Check status code 201
            Assert.assertEquals(jsonPath.getString("message"), "Request submitted!");  // Check message
            Assert.assertNotNull(jsonPath.getString("data"));  // Ensure that data is returned
            Assert.assertTrue(Boolean.parseBoolean(jsonPath.getString("success")));  // Check success flag
            Assert.assertNotNull(jsonPath.getString("timestamp"));  // Check timestamp field
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test(priority = 1)
    void getPurchaseRequestByNo() {
        Response response;
        String purchaseRequestNo = "PCH-23-1234567890";  // Example PurchaseRequestNo

        try {
            response = given()
                    .header("Content-Type", "application/json")
                    .header("accept", "*/*")
                    .header(AuthControllerTest.AUTH_HEADER, token)
                    .request(Method.GET, "/purchase-request/" + purchaseRequestNo);

            int intResponse = response.getStatusCode();
            JsonPath jsonPath = response.jsonPath();
            System.out.println(response.getBody().prettyPrint());

            // Validating the response for GET request
            Assert.assertEquals(intResponse, 200);  // Check status code 200 OK
            Assert.assertEquals(jsonPath.getString("message"), "Request found!");  // Check message
            Assert.assertNotNull(jsonPath.getString("data"));  // Ensure that data is returned
            Assert.assertTrue(Boolean.parseBoolean(jsonPath.getString("success")));  // Check success flag
            Assert.assertNotNull(jsonPath.getString("timestamp"));  // Check timestamp field
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

