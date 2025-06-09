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
public class PurchaseOrderControllerTest extends AbstractTestNGSpringContextTests {
    private String token;
    private DataGenerator dataGenerator;
    private Random rand;
    private JSONObject req;

    @BeforeClass
    private void init() {
        token = new TokenGenerator(AuthControllerTest.authorization).getToken();  // Mendapatkan token otorisasi
        dataGenerator = new DataGenerator();
        rand = new Random();
        req = new JSONObject();
    }

    @Test(priority = 0)
    void createPurchaseOrder() {
        Response response;
        Integer quantityOrdered = rand.nextInt(150);  // Menghasilkan angka acak untuk quantity ordered
        BigDecimal unitPrice = new BigDecimal(rand.nextInt(1000) + 10000);  // Menghasilkan harga unit acak antara 10000 dan 11000
        String unit = dataGenerator.dataDescription(10);  // Menghasilkan deskripsi unit acak
        BigDecimal totalAmount = unitPrice.multiply(new BigDecimal(quantityOrdered));  // Menghitung total amount
        Integer status = rand.nextInt(4);  // Status acak antara 0 hingga 3

        try {
            req.put("quantityOrdered", quantityOrdered);
            req.put("unitPrice", unitPrice);
            req.put("unit", unit);
            req.put("totalAmount", totalAmount);
            req.put("status", status);

            response = given()
                    .header("Content-Type", "application/json")
                    .header("accept", "*/*")
                    .header(AuthControllerTest.AUTH_HEADER, token)
                    .body(req)
                    .request(Method.POST, "/purchase-order/create");

            int intResponse = response.getStatusCode();
            JsonPath jsonPath = response.jsonPath();
            System.out.println(response.getBody().prettyPrint());

            // Validasi status code dan pesan response
            Assert.assertEquals(intResponse, 200);  // Pastikan status code adalah 200 OK
            Assert.assertEquals(jsonPath.getString("message"), "Purchase Order created!");  // Pastikan pesan sesuai
            Assert.assertNotNull(jsonPath.getString("data"));  // Pastikan data tidak null
            Assert.assertTrue(Boolean.parseBoolean(jsonPath.getString("success")));  // Pastikan success true
            Assert.assertNotNull(jsonPath.getString("timestamp"));  // Pastikan timestamp ada
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test(priority = 1)
    void getPurchaseOrderByNo() {
        Response response;
        String purchaseOrderNo = "PO1234567890";  // Misal ini purchase order yang sudah ada

        try {
            response = given()
                    .header("Content-Type", "application/json")
                    .header("accept", "*/*")
                    .header(AuthControllerTest.AUTH_HEADER, token)
                    .request(Method.GET, "/purchase-order/" + purchaseOrderNo);

            int intResponse = response.getStatusCode();
            JsonPath jsonPath = response.jsonPath();
            System.out.println(response.getBody().prettyPrint());

            // Validasi status code dan response data
            Assert.assertEquals(intResponse, 200);  // Pastikan status code adalah 200 OK
            Assert.assertEquals(jsonPath.getString("message"), "Purchase Order found!");  // Pastikan pesan sesuai
            Assert.assertNotNull(jsonPath.getString("data"));  // Pastikan data tidak null
            Assert.assertTrue(Boolean.parseBoolean(jsonPath.getString("success")));  // Pastikan success true
            Assert.assertNotNull(jsonPath.getString("timestamp"));  // Pastikan timestamp ada
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
