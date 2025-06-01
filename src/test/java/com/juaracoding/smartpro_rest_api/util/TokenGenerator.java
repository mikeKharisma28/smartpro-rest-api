package com.juaracoding.smartpro_rest_api.util;

import com.juaracoding.smartpro_rest_api.consts.AdminCredential;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONObject;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;

public class TokenGenerator {
    private JSONObject req;
    private String token;

    public TokenGenerator(String token) {
        if (token == null) {
            req = new JSONObject();

            RestAssured.baseURI = "http://localhost:8085";
            doLoginManual();
        }
        else {
            this.token = token;
        }
    }

    private void doLoginManual() {
        req.put("username", AdminCredential.ADMIN_USERNAME);
        req.put("password", AdminCredential.ADMIN_PASSWORD);

        Response response;
        try {
            HttpClientConfig httpClientConfig = HttpClientConfig.httpClientConfig().
                    setParam("http.socket.timeout",60000).
                    setParam("http.connection.timeout",60000);
            config = RestAssuredConfig.config().httpClient(httpClientConfig);
            response =  given().
                    header("Content-Type","application/json").
                    header("accept","*/*").
                    body(req).
                    request(Method.POST, "/auth/login");

            int intResponse = response.getStatusCode();
            JsonPath jsonPath = response.jsonPath();

            this.token = "Bearer " + jsonPath.get("data.token");

            if(intResponse != 200 || token == null) {
                System.out.println("No admin's username and password provided!");
                System.exit(0);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String getToken() {
        return token;
    }
}
