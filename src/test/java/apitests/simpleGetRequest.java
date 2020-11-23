package apitests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class simpleGetRequest {
    String hrurl="http://3.80.189.73:1000/ords/hr/regions";

    @Test
    public void test1(){
        Response response =RestAssured.get(hrurl);
        // print status code
        System.out.println("response.statusCode() = " + response.statusCode());
        // print json body
        response.prettyPrint();
    }

    /*
        Given accept type is json
        When user sends get request to regions endpoint
        Then response status code must be 200
        and body is json format
     */

    @ Test
    public void test2(){
        Response response=RestAssured.given().accept(ContentType.JSON)
                .when().get(hrurl);
            // verify status code
        Assert.assertEquals(response.statusCode(),200);
        // verify content type
        System.out.println("response.contentType() = " + response.contentType());

        Assert.assertEquals(response.contentType(),"application/json");
    }

    @Test
    public void test3(){
        RestAssured.given().accept(hrurl)
                .when().get(hrurl)
                .then()
                .assertThat().statusCode(200)
                .and().contentType("application/json");
    }
 /*
        Given accept type is json
        When user sends get request to regions/2
        Then response status code must be 200
        and body is json format
        and response body contains Americas
     */

    @Test
    public void test4(){
        Response response=given().accept(ContentType.JSON)
                .when().get(hrurl+"/2");
        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertEquals(response.contentType(),"application/json");
        // verify contains America
        Assert.assertTrue(response.body().asString().contains("Americas"));
    }

}
