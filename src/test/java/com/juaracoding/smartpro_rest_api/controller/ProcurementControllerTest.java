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

import java.time.LocalDate;
import java.util.Random;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ProcurementControllerTest extends AbstractTestNGSpringContextTests {
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
    void request() {
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

            response = given()
                    .header("Content-Type", "application/json")
                    .header("accept", "*/*")
                    .header(AuthControllerTest.AUTH_HEADER, token)
                    .body(req)
                    .request(Method.PATCH, "/procurement/request");

            int intResponse = response.getStatusCode();
            JsonPath jsonPath = response.jsonPath();
            System.out.println(response.getBody().prettyPrint());
            Assert.assertEquals(intResponse,201);
            Assert.assertEquals(jsonPath.getString("message"),"Request submitted!");
            Assert.assertNotNull(jsonPath.getString("data"));
            Assert.assertTrue(Boolean.parseBoolean(jsonPath.getString("success")));
            Assert.assertNotNull(jsonPath.getString("timestamp"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @Test(priority = 1)
    void approve(){
        Response response;
        String idProcurement = dataGenerator.dataProcurement();


        try {
            req.put("id-procurement", idProcurement);
            req.put("status", 1);

            response = given()
                    .header("Content-Type", "application/json")
                    .header("accept", "*/*")
                    .header(AuthControllerTest.AUTH_HEADER, token)
                    .body(req)
                    .request(Method.PUT, "/"+idProcurement+"/approve");

            int intResponse = response.getStatusCode();
            JsonPath jsonPath = response.jsonPath();
            System.out.println(response.getBody().prettyPrint());
            Assert.assertEquals(intResponse,201);
            Assert.assertEquals(jsonPath.getString("message"),"Request submitted!");
            Assert.assertNotNull(jsonPath.getString("data"));
            Assert.assertTrue(Boolean.parseBoolean(jsonPath.getString("success")));
            Assert.assertNotNull(jsonPath.getString("timestamp"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test(priority = 2)
    void reject(){
        Response response;
        String idProcurement = dataGenerator.dataProcurement();


        try {
            req.put("id-procurement", idProcurement);
            req.put("status", 2);

            response = given()
                    .header("Content-Type", "application/json")
                    .header("accept", "*/*")
                    .header(AuthControllerTest.AUTH_HEADER, token)
                    .body(req)
                    .request(Method.PUT, "/"+idProcurement+"/reject");

            int intResponse = response.getStatusCode();
            JsonPath jsonPath = response.jsonPath();
            System.out.println(response.getBody().prettyPrint());
            Assert.assertEquals(intResponse,201);
            Assert.assertEquals(jsonPath.getString("message"),"Request submitted!");
            Assert.assertNotNull(jsonPath.getString("data"));
            Assert.assertTrue(Boolean.parseBoolean(jsonPath.getString("success")));
            Assert.assertNotNull(jsonPath.getString("timestamp"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
