package com.juaracoding.smartpro_rest_api.controller;

import com.juaracoding.smartpro_rest_api.consts.AdminCredential;
import com.juaracoding.smartpro_rest_api.util.DataGenerator;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Random;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AuthControllerTest extends AbstractTestNGSpringContextTests {

    private Random rand;
    private JSONObject req;

    private String username;
    private String password;
    private Boolean isOk;

    public static String authorization;
    public static final String AUTH_HEADER = "Authorization";

    @BeforeClass
    void init() {
        RestAssured.baseURI = "http://localhost:8085";
        rand = new Random();
        req = new JSONObject();
    }

    @Test(priority = 0)
    void loginTest() {
        Response response;
        req.clear();
        if(isOk) {
            try {
                req.put("username", username);
                req.put("password", password);

                response = given().
                        header("Content-Type","application/json").
                        header("accept","*/*").
                        body(req).
                        request(Method.POST,"auth/login");
                int intResponse = response.getStatusCode();
                JsonPath jsonPath = response.jsonPath();
                System.out.println(response.getBody().asPrettyString());
                if(intResponse==200){
//                    authorization = "Bearer " + jsonPath.getString("data.token");
                    isOk = true;
                }
                Assert.assertEquals(intResponse,200);
                Assert.assertEquals(jsonPath.getString("message"),"Logged in!");
                Assert.assertNotNull(jsonPath.getString("data.menu"));
                Assert.assertNotNull(jsonPath.getString("data.token"));
                Assert.assertTrue(Boolean.parseBoolean(jsonPath.getString("success")));
                Assert.assertNotNull(jsonPath.getString("timestamp"));
            }
            catch (Exception e){
                isOk = false;
            }
        }
        else {
            Assert.assertNotNull(null);//untuk menyatakan bahwa ini error atau diskip
        }
    }

    @Test(priority = 10)
    void loginAdminTest() {
        Response response;
        req.clear();
        if(isOk) {
            try {
                req.put("username", AdminCredential.ADMIN_USERNAME);
                req.put("password", AdminCredential.ADMIN_PASSWORD);

                response = given().
                        header("Content-Type","application/json").
                        header("accept","*/*").
                        body(req).
                        request(Method.POST,"auth/login");
                int intResponse = response.getStatusCode();
                JsonPath jsonPath = response.jsonPath();
                System.out.println(response.getBody().asPrettyString());
                if(intResponse==200){
                    authorization = "Bearer " + jsonPath.getString("data.token");
                    isOk = true;
                }
                Assert.assertEquals(intResponse,200);
                Assert.assertEquals(jsonPath.getString("message"),"Logged in!");
                Assert.assertNotNull(jsonPath.getString("data.menu"));
                Assert.assertNotNull(jsonPath.getString("data.token"));
                Assert.assertTrue(Boolean.parseBoolean(jsonPath.getString("success")));
                Assert.assertNotNull(jsonPath.getString("timestamp"));
            }
            catch (Exception e){
                isOk = false;
            }
        }
        else {
            Assert.assertNotNull(null);//untuk menyatakan bahwa ini error atau diskip
        }
    }
}
