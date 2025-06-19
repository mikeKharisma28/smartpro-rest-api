package com.juaracoding.smartpro_rest_api.controller;

import com.juaracoding.smartpro_rest_api.config.OtherConfig;
import com.juaracoding.smartpro_rest_api.dto.relation.RelRoleDTO;
import com.juaracoding.smartpro_rest_api.model.Role;
import com.juaracoding.smartpro_rest_api.model.Staff;
import com.juaracoding.smartpro_rest_api.repo.RoleRepo;
import com.juaracoding.smartpro_rest_api.repo.StaffRepo;
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

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class StaffControllerTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private StaffRepo staffRepo;

    @Autowired
    private RoleRepo roleRepo;

    private Role role;
    private JSONObject req;
    private Staff staff;
    private Random rand ;
    private String token;
    private DataGenerator dataGenerator;


    @BeforeClass
    private void init(){
        token = new TokenGenerator(AuthControllerTest.authorization).getToken();
        rand  = new Random();
        req = new JSONObject();
        staff = new Staff();
        dataGenerator = new DataGenerator();
        Optional<Staff> op = staffRepo.findTop1ByOrderByIdDesc();
        Optional<Role> opRole =roleRepo.findTop1ByOrderByIdDesc();

        staff = op.get();
        role = opRole.get();
    }

    @BeforeTest
    private void setup(){
        /** sifatnya optional */
    }

    @Test(priority = 0)
    void save(){
        Response response ;
        String nama = dataGenerator.dataFullName();
        String path = "/"+nama.toLowerCase().replace(" ","-");
        try{
            req.put("username", dataGenerator.dataUsername());
            req.put("password", dataGenerator.dataPassword());
            req.put("barang", dataGenerator.dataBarang());
            req.put("full-name", dataGenerator.dataFullName());
            RelRoleDTO relRoleDTO = new RelRoleDTO();
            relRoleDTO.setId(staff.getRole().getId());
            req.put("role",relRoleDTO);

            response = given().
                    header("Content-Type","application/json").
                    header("accept","*/*").
                    header(AuthControllerTest.AUTH_HEADER,token).
                    body(req).
                    request(Method.POST,"staff");

            int intResponse = response.getStatusCode();
            JsonPath jsonPath = response.jsonPath();
            System.out.println(response.getBody().prettyPrint());
            Assert.assertEquals(intResponse,201);
            Assert.assertEquals(jsonPath.getString("message"),"SAVE SUCCESS !!");
            Assert.assertNotNull(jsonPath.getString("data"));
            Assert.assertTrue(Boolean.parseBoolean(jsonPath.getString("success")));
            Assert.assertNotNull(jsonPath.getString("timestamp"));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test(priority = 10)
    void update(){
        Response response ;
        req.clear();
        try{
            String reqUsername = dataGenerator.dataUsername();
            String reqPassword = dataGenerator.dataPassword();
            String reqFullname = dataGenerator.dataFullName();

            staff.setUsername(reqUsername);
            staff.setPassword(reqPassword);
            staff.setFullName(reqFullname);
//            staff.setRole(staff.getRole()); // tidak perlu di set karena diambil dari sumber yang sama

            req.put("username", reqUsername);
            req.put("password", reqPassword);
            req.put("full-name", reqFullname);
            RelRoleDTO relRoleDTO = new RelRoleDTO();
            relRoleDTO.setId(staff.getRole().getId());
            req.put("role",relRoleDTO);

            response = given().
                    header("Content-Type","application/json").
                    header("accept","*/*").
                    header(AuthControllerTest.AUTH_HEADER,token).
                    body(req).
                    request(Method.PUT,"staff/"+ staff.getId());

            int intResponse = response.getStatusCode();
            JsonPath jsonPath = response.jsonPath();
//            System.out.println(response.getBody().prettyPrint());
            Assert.assertEquals(intResponse,200);
            Assert.assertEquals(jsonPath.getString("message"),"DATA CHANGED SUCCESSFULLY");
            Assert.assertNotNull(jsonPath.getString("data"));
            Assert.assertTrue(Boolean.parseBoolean(jsonPath.getString("success")));
            Assert.assertNotNull(jsonPath.getString("timestamp"));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test(priority = 20)
    void findById(){
        Response response ;
        try{
            response = given().
                    header("Content-Type","application/json").
                    header("accept","*/*").
                    header(AuthControllerTest.AUTH_HEADER,token).
                    request(Method.GET,"/staff/"+ staff.getId());

            int intResponse = response.getStatusCode();
            JsonPath jsonPath = response.jsonPath();
//            System.out.println(response.getBody().prettyPrint());
            Assert.assertEquals(intResponse,200);
            Assert.assertEquals(jsonPath.getString("message"),"DATA FOUND");
            Assert.assertEquals(Long.parseLong(jsonPath.getString("data.id")), staff.getId());
            Assert.assertEquals(jsonPath.getString("data.full-name"), staff.getFullName());
            Assert.assertEquals(jsonPath.getString("data.phone-number"), staff.getPhoneNumber());
            Assert.assertEquals(jsonPath.getString("data.division"), staff.getDivision());
            Assert.assertTrue(Boolean.parseBoolean(jsonPath.getString("success")));
            Assert.assertNotNull(jsonPath.getString("timestamp"));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test(priority = 30)
    void findAll(){
        Response response ;
        try{
            response = given().
                    header("Content-Type","application/json").
                    header("accept","*/*").
                    header(AuthControllerTest.AUTH_HEADER,token).
                    request(Method.GET,"/staff/");

            int intResponse = response.getStatusCode();
            JsonPath jsonPath = response.jsonPath();
            List ltData = jsonPath.getList("data.content");
            int intData = ltData.size();
//            System.out.println(response.getBody().prettyPrint());
            Assert.assertEquals(intResponse,200);
            Assert.assertEquals(jsonPath.getString("message"),"DATA FOUND");
            Assert.assertTrue(Boolean.parseBoolean(jsonPath.getString("success")));
            Assert.assertNotNull(jsonPath.getString("timestamp"));
// ======================================================================================================================================================
            Assert.assertEquals(jsonPath.getString("data.sort-by"),"id");
            Assert.assertEquals(Integer.parseInt(jsonPath.getString("data.current-page")),0);
            Assert.assertEquals(jsonPath.getString("data.column-name"),"id");
            Assert.assertNotNull(jsonPath.getString("data.total-pages"));
            Assert.assertEquals(jsonPath.getString("data.sort"),"asc");
            Assert.assertEquals(Integer.parseInt(jsonPath.getString("data.size-per-page")), OtherConfig.getDefaultPaginationSize());
            Assert.assertEquals(jsonPath.getString("data.value"),"");
            Assert.assertEquals(Integer.parseInt(jsonPath.getString("data.total-data")),intData);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test(priority = 40)
    void findByParam(){
        Response response ;
        String pathVariable = "/staff/asc/id/0";
        String strValue = staff.getFullName();

        try{
            response = given().
                    header("Content-Type","application/json").
                    header("accept","*/*").
                    header(AuthControllerTest.AUTH_HEADER,token).
                    params("column","full-name").
                    params("value",strValue).
                    params("size",10).
                    request(Method.GET,pathVariable);

            int intResponse = response.getStatusCode();
            JsonPath jsonPath = response.jsonPath();
            List<Map<String,Object>> ltData = jsonPath.getList("data.content");
            int intData = ltData.size();
            Map<String,Object> map = ltData.get(0);

            System.out.println(response.getBody().prettyPrint());
            Assert.assertEquals(intResponse,200);
            Assert.assertEquals(jsonPath.getString("message"),"DATA FOUND");
            Assert.assertTrue(Boolean.parseBoolean(jsonPath.getString("success")));
            Assert.assertNotNull(jsonPath.getString("timestamp"));
// ======================================================================================================================================================
            Assert.assertEquals(jsonPath.getString("data.sort-by"),"id");
            Assert.assertEquals(Integer.parseInt(jsonPath.getString("data.current-page")),0);
            Assert.assertEquals(jsonPath.getString("data.column-name"),"name");
            Assert.assertNotNull(jsonPath.getString("data.total-pages"));
            Assert.assertEquals(Integer.parseInt(jsonPath.getString("data.total-data")),intData);
            Assert.assertEquals(jsonPath.getString("data.sort"),"asc");
            Assert.assertEquals(Integer.parseInt(jsonPath.getString("data.size-per-page")), 10);
            Assert.assertEquals(jsonPath.getString("data.value"),strValue);
// ======================================================================================================================================================

            Assert.assertEquals(Long.parseLong(jsonPath.getString("data.id")), staff.getId());
            Assert.assertEquals(jsonPath.getString("data.full-name"), staff.getFullName());
            Assert.assertEquals(jsonPath.getString("data.phone-number"), staff.getPhoneNumber());
            Assert.assertEquals(jsonPath.getString("data.username"), strValue);//khusus ini karena pencarian berdasarkan staffname
            Assert.assertEquals(jsonPath.getString("data.division"), staff.getDivision());
            Assert.assertEquals(Long.parseLong(map.get("id").toString()), staff.getId());

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test(priority = 50)
    void uploadExcel(){
        Response response ;
        try{
            response = given().
                    header("Content-Type","multipart/form-data").
                    header("accept","*/*").
                    header(AuthControllerTest.AUTH_HEADER,token).
                    multiPart("file",new File(System.getProperty("staff.dir")+"/src/test/resources/data-test/staffr.xlsx"),
                            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet").
                    request(Method.POST,"staff/upload-excel");

            int intResponse = response.getStatusCode();
            JsonPath jsonPath = response.jsonPath();
            Assert.assertEquals(intResponse,201);
            Assert.assertEquals(jsonPath.getString("message"),"EXCEL FILE UPLOAD SUCCESSFUL");
            Assert.assertNotNull(jsonPath.getString("data"));
            Assert.assertTrue(Boolean.parseBoolean(jsonPath.getString("success")));
            Assert.assertNotNull(jsonPath.getString("timestamp"));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    @Test(priority = 50)
    void downloadExcel(){
        Response response ;
        try{
            response = given().
                    header("Content-Type","application/json").
                    header("accept","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet").
                    header(AuthControllerTest.AUTH_HEADER,token).
                    params("column","staffname").
                    params("value", staff.getUsername()).
                    request(Method.GET,"staff/download-excel");

            int intResponse = response.getStatusCode();
            Assert.assertEquals(intResponse,200);
            /** khusus untuk download file harus di cek header nya */
            Assert.assertTrue(response.getHeader("Content-Disposition").contains(".xlsx"));// file nya memiliki extension .xlsx
            Assert.assertEquals(response.getHeader("Content-Type"),"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");// content type wajib ini untuk excel
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test(priority = 60)
    void downloadPdf(){
        Response response ;
        try{
            response = given().
                    header("Content-Type","application/json").
                    header("accept","application/pdf").
                    header(AuthControllerTest.AUTH_HEADER,token).
                    params("column","username").
                    params("value", staff.getUsername()).
                    request(Method.GET,"staff/download-pdf");

            int intResponse = response.getStatusCode();
            Assert.assertEquals(intResponse,200);
            /** khusus untuk download file harus di cek header nya */
            Assert.assertTrue(response.getHeader("Content-Disposition").contains(".pdf"));// file nya memiliki extension .xlsx
            Assert.assertEquals(response.getHeader("Content-Type"),"application/pdf");// content type wajib ini untuk excel
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test(priority = 999)
    void delete(){
        Response response ;
        try{
            response = given().
                    header("Content-Type","application/json").
                    header("accept","*/*").
                    header(AuthControllerTest.AUTH_HEADER,token).
                    request(Method.DELETE,"/staff/"+ staff.getId());

            int intResponse = response.getStatusCode();
            JsonPath jsonPath = response.jsonPath();
//            System.out.println(response.getBody().prettyPrint());
            Assert.assertEquals(intResponse,200);
            Assert.assertEquals(jsonPath.getString("message"),"DATA WAS DELETED SUCCESSFULLY");
            Assert.assertNotNull(jsonPath.getString("data"));
            Assert.assertTrue(Boolean.parseBoolean(jsonPath.getString("success")));
            Assert.assertNotNull(jsonPath.getString("timestamp"));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}