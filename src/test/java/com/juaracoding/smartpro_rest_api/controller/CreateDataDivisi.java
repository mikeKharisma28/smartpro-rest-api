package com.juaracoding.smartpro_rest_api.controller;


import com.juaracoding.smartpro_rest_api.model.Division;
import com.juaracoding.smartpro_rest_api.repo.DivisionRepo;
import com.juaracoding.smartpro_rest_api.util.DataGenerator;
import com.juaracoding.smartpro_rest_api.util.TokenGenerator;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Optional;
import java.util.Random;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CreateDataDivisi extends AbstractTestNGSpringContextTests {

    @Autowired
    private DivisionRepo divisionRepo;

    private JSONObject req;
    private Division division;
    private Random rand ;
    private String token;
    private DataGenerator dataGenerator;

    @BeforeClass
    private void init(){
        token = new TokenGenerator(AuthControllerTest.authorization).getToken();
        rand  = new Random();
        req = new JSONObject();
        division = new Division();
        dataGenerator = new DataGenerator();
        Optional<Division> op = divisionRepo.findTop1ByOrderByIdDesc();
        division = op.get();
    }

    @BeforeTest
    private void setup(){
        /** sifatnya optional */        
    }

    @Test(priority = 0)
    void save(){
        Response response ;
        try{
            req.put("name", dataGenerator.dataFullName());

            response = given().
                    header("Content-Type","application/json").
                    header("accept","*/*").
                    header(AuthControllerTest.AUTH_HEADER,token).
                    body(req).
                    request(Method.POST,"division");

            int intResponse = response.getStatusCode();
            JsonPath jsonPath = response.jsonPath();
            Assert.assertEquals(intResponse,201);
            Assert.assertEquals(jsonPath.getString("message"),"SAVE SUCCESS !!");
            Assert.assertNotNull(jsonPath.getString("data"));
            Assert.assertTrue(Boolean.parseBoolean(jsonPath.getString("success")));
            Assert.assertNotNull(jsonPath.getString("timestamp"));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    private String genDataDescription(){
        String strArr = "----";
        StringBuilder sb = new StringBuilder();
        int intLength =  rand.nextInt(20,200);
        for (int i = 0; i < intLength; i++) {
            sb.append(strArr.charAt(rand.nextInt(strArr.length())));
        }

        return sb.toString();
    }
}