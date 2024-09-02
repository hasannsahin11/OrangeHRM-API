package api.setup;

import api.models.EssUser;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v85.network.Network;
import org.openqa.selenium.devtools.v85.network.model.Request;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;


public class ApiTestSetup {

    protected WebDriver driver;
    protected String csrfToken;
    protected String sessionCookie;
    EssUser essUser = new EssUser();

    public void adminLogin() {
        baseURI = "https://opensource-demo.orangehrmlive.com";

        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        DevTools devTools = ((ChromeDriver) driver).getDevTools();
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

            // Perform the login
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

    }

    @Test
    public void essAccountCreation() {
        Cookie restAssuredCookie = new Cookie.Builder("orangehrm", sessionCookie)
                .setDomain("opensource-demo.orangehrmlive.com")
                .setPath("/web")
                .setSecured(true)
                .build();


        essUser.setEmpNumber(22);
        essUser.setPassword("SShaheen11");
        essUser.setStatus(true);
        essUser.setUsername("SShaheen11");
        essUser.setUserRoleId(2);

        // Add the _token to the JSON payload if required
        Map<String, Object> payload = new HashMap<>();
        payload.put("empNumber", essUser.getEmpNumber());
        payload.put("password", essUser.getPassword());
        payload.put("status", essUser.isStatus());
        payload.put("username", essUser.getUsername());
        payload.put("userRoleId", essUser.getUserRoleId());


        given()
                .cookie(restAssuredCookie)
//                .header("X-CSRF-Token", csrfToken)
                .header("_token", csrfToken)
                .contentType(ContentType.JSON)
                .log().body()
                .body(payload)

                .when()
                .post("/web/index.php/api/v2/admin/users")

                .then()
                .statusCode(200)
                .log().body();
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}