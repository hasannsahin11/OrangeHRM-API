package api.setup;

import io.restassured.RestAssured;

public class ApiTestSetup {

    static {
        RestAssured.baseURI = "https://opensource-demo.orangehrmlive.com";
    }

}
