package api.setup;

import api.models.EssUser;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;


import static io.restassured.RestAssured.baseURI;


public class ApiTestSetup {

    @BeforeClass
    public void setup() {
        baseURI = "https://opensource-demo.orangehrmlive.com";

    }

}