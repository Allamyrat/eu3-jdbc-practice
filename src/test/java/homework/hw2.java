package homework;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

    public class hw2 {
    /*
    Q1:
    Given accept type is json
    And path param id is 20
    When user sends a get request to "/api/spartans/{id}"
    Then status code is 200
    And content-type is "application/json;charset=UTF-8"
    And response header contains Date
    And Transfer-Encoding is chunked
    And response payload values match the following:
    id is 20,
    name is "Lothario",
    gender is "Male",
    phone is 7551551687
     */
    @BeforeClass
    public void beforeclass(){
        baseURI= ConfigurationReader.get("spartan_api_url");
    }

    @Test
    public void q1(){

        Response response=given().accept(ContentType.JSON)
                .and().pathParam("id","20")
                .when().get("/api/spartans/{id}");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json;charset=UTF-8");

        assertTrue(response.headers().hasHeaderWithName("Date"));
        assertEquals(response.headers().getValue("Transfer-Encoding"),"chunked");

        assertEquals(response.getBody().asString(),"{\"id\":20," +
                "\"name\":\"Lothario\"," +
                "\"gender\":\"Male\"," +
                "\"phone\":7551551687}");

}
/*
Q2:
Given accept type is json
And query param gender = Female
And queary param nameContains = r
When user sends a get request to "/api/spartans/search"
Then status code is 200
And content-type is "application/json;charset=UTF-8"
And all genders are Female
And all names contains r
And size is 20
And totalPages is 1
And sorted is false
 */
    @Test
    public void q2(){
        Response response=given().accept(ContentType.JSON)
                .and().queryParam("gender","Female")
                .and().queryParam("nameContains","r")
                .when().get("/api/spartans/search");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json;charset=UTF-8");

        List<String> genderList=response.path("content.gender");
        System.out.println("genderList.size() = " + genderList.size());
        for (String genders : genderList) {
            assertEquals(genders,"Female");
        }

        List<String> names=response.path("content.name");
        System.out.println("names.toString() = " + names.toString());
        for (String name : names) {
            assertTrue(name.contains("r")|name.contains("R"));
        }
        assertEquals(names.size(),17);

        int page=response.path("totalPages");
        assertEquals(page,1);

        Boolean sorted=response.path("sort.sorted");
        assertFalse(sorted);
    }


}
