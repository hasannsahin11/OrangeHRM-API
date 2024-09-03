package api.setup;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import static io.restassured.RestAssured.baseURI;

public class Setup {

  public static   RequestSpecification requestSpec;
  public static   ResponseSpecification responseSpec;

    @BeforeClass
    public void setup() {
        baseURI = "https://opensource-demo.orangehrmlive.com";

//        requestSpec = new RequestSpecBuilder()
//                .log(LogDetail.URI)                      // Prints request uri
//                .log(LogDetail.BODY)                     // Prints request body
//                .setContentType(ContentType.JSON)        // Sets the data format as JSON
//                .build();

//        responseSpec = new ResponseSpecBuilder()
////                .expectStatusCode(200)   // Checks if the status code is 200 from all responses
////                .expectContentType(ContentType.JSON)      // Checks if the response type is in JSON format
//                .log(LogDetail.BODY)                      // prints the body of all responses
//                .build();
    }
}