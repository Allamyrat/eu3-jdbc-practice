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

public class hw1 {

    @BeforeClass
    public void beforeclass(){
        baseURI= ConfigurationReader.get("hr_api_url");
    }

    /*
   - Given accept type is Json
- Path param value- US
- When users sends request to /countries
- Then status code is 200
- And Content - Type is Json
- And country_id is US
- And Country_name is United States of America
- And Region_id is
     */

    @Test
    public void q1(){
        Response response=given().accept(ContentType.JSON)
                .and().pathParam("countries","US")
                .when().get("/countries/{countries}");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");
        assertEquals(response.path("country_id"),"US");
        assertEquals(response.path("country_name"),"United States of America");
        int regionId=response.path("region_id");
        assertEquals(regionId,2);

    }

    /*
     Given accept type is Json
- Query param value - q={"department_id":80}
- When users sends request to /employees
- Then status code is 200
- And Content - Type is Json
- And all job_ids start with 'SA'
- And all department_ids are 80
- Count is 25
     */

    @Test
    public void q2(){
        Response response=given().accept(ContentType.JSON)
                .and().queryParam("q","{\"department_id\":80}")
                .when().get("/employees");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");

        List<String>jobIDs=response.path("items.job_id");
        System.out.println("jobIDs.size() = " + jobIDs.size());
        for (String jobID : jobIDs) {
            assertTrue(jobID.startsWith("SA"));
        }
        List<Integer> departmentIDs=response.path("items.department_id");
        System.out.println("departmentIDs.size() = " + departmentIDs.size());
        for (int departmentID : departmentIDs) {
            assertEquals(departmentID,80);
        }
        int count=response.path("count");
        assertEquals(count,25);
    }
    /*
    Q3:
- Given accept type is Json
-Query param value q= region_id 3
- When users sends request to /countries
- Then status code is 200
- And all regions_id is 3
- And count is 6
- And hasMore is false
- And Country_name are;
Australia,China,India,Japan,Malaysia,Singapore
     */

    @Test
    public void q3(){
        Response response=given().accept(ContentType.JSON)
                .and().queryParam("q","{\"region_id\":3}")
                .when().get("/countries");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");

        List<Integer> regionIDs=response.path("items.region_id");
        System.out.println("regionIDs.size() = " + regionIDs.size());
        for (int regionID : regionIDs) {
            assertEquals(regionID,3);
        }


        int count=response.path("count");
        System.out.println(count);
        assertEquals(count,6);

        assertFalse(response.path("hasMore"));

        List<String> countryNames=response.path("items.country_name");
        System.out.println("countryNames.toString() = " + countryNames.toString());
        assertEquals(countryNames.toString(),"[Australia, China, India, Japan, Malaysia, Singapore]");


    }
    }

