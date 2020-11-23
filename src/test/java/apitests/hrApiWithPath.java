package apitests;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class hrApiWithPath {
    @BeforeClass
    public void beforeclass(){
        baseURI= ConfigurationReader.get("hr_api_url");
    }

    @Test
    public void getCountriesWithPath(){
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"region_id\":2}")
                .when().get("/countries");

        assertEquals(response.statusCode(),200);

        //print limit value
        System.out.println("response.path(\"limit\") = " + response.path("limit"));

        System.out.println("response.path(\"hasMore\").toString() = " + response.path("hasMore").toString());

        String firstCountryId = response.path("items.country_id[0]");
        System.out.println("firstCountryId = " + firstCountryId);

        String secondCountryName = response.path("items.country_name[1]");
        System.out.println("secondCountryName = " + secondCountryName);

        String thirdCountryLink = response.path("items.links[2].href[0]");
        System.out.println("thirdCountryLink = " + thirdCountryLink);

        // get all countries
        List<String> CountryName = response.path("items.country_name");
        System.out.println("CountryName = " + CountryName);

        // assert that all region id s are equal to 2

        List<Integer> regionIds = response.path("items.region_id");
        for (int regionId : regionIds) {
            System.out.println(regionId);
            assertEquals(regionId,2);
        }

    }
    @Test
    public void testtt(){
        // make sure that we have only IT_PROG as a job_id
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"job_id\":\"IT_PROG\"}")
                .when().get("/employees");
            assertEquals(response.statusCode(),200);
            assertEquals(response.contentType(),"application/json");
        List<String>jobIDs=response.path("items.job_id");
        for (String jobID : jobIDs) {
            System.out.println("jobID = " + jobID);
            assertEquals(jobID,"IT_PROG");
        }

    }

}
