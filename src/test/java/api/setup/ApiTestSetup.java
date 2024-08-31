package api.setup;

import api.models.EssUser;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v85.network.Network;
import org.openqa.selenium.devtools.v85.network.model.Request;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.swing.text.Document;
import javax.swing.text.Element;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static io.restassured.RestAssured.given;

public class ApiTestSetup {

    WebDriver driver;
    String csrfToken;
    String sessionCookie;

    @BeforeClass
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        ChromeDriver driver = new ChromeDriver(options);
        DevTools devTools = driver.getDevTools();
        devTools.createSession();

        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        devTools.addListener(Network.requestWillBeSent(), request -> {
            Request req = request.getRequest();
            if (req.getUrl().contains("/validate")) {
                String postData = req.getPostData().orElse("");
                if (postData.contains("_token")) {
                    // Extract the _token from the postData
                    csrfToken = postData.split("_token=")[1].split("&")[0];
                    System.out.println("Captured _token: " + csrfToken);
                }
            }
        });


        try {
            // Navigate to the login page
            driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

            Thread.sleep(2000);


            driver.findElement(By.cssSelector("input[name='username']")).sendKeys("Admin");
            driver.findElement(By.cssSelector("input[name='password']")).sendKeys("admin123");
            driver.findElement(By.cssSelector("button[type='submit']")).click();
            // Allow time for the request to be captured
            Thread.sleep(2000);

            // Capture the session cookie
            sessionCookie = driver.manage().getCookieNamed("orangehrm").getValue();
            System.out.println("Session Cookie: " + sessionCookie);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


//    @Test(dependsOnMethods = {"adminLogin"})
//    public void essAccountCreation() {
//
//        EssUser essUser = new EssUser();
//        essUser.setEmpNumber(22);
//        essUser.setPassword("SShaheen11");
//        essUser.setStatus(true);
//        essUser.setUsername("SShaheen11");
//        essUser.setUserRoleId(2);
//
//        given()
//                .body(essUser)
//                .contentType(ContentType.JSON)
//                .cookies(cookies)
//
//                .when()
//                .post("/web/index.php/api/v2/admin/users")
//
//                .then()
//                .statusCode(200)
//                .log().body();
//    }
    }
    }