package api.setup;

import api.models.EssUser;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.swing.text.Document;
import javax.swing.text.Element;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiTestSetup {

    Cookies cookies;
    Response response;
    String csrfToken;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://opensource-demo.orangehrmlive.com";
    }

    @Test
    public void adminLogin() {
        // Perform the login request
        Map<String, String> adminCredentials = new HashMap<>();
        adminCredentials.put("username", "Admin");
        adminCredentials.put("password", "admin123");

        Response loginResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .formParams(adminCredentials)
                .when()
                .post("/web/index.php/auth/validate")
                .then()
                .statusCode(302)  // Expecting a redirect status code
                .extract().response();

        // Extract and log all cookies
        cookies = loginResponse.getDetailedCookies();
        System.out.println("All Cookies: " + cookies);

        // Check if _token is present in the cookies
        Cookie tokenCookie = cookies.get("_token");
        if (tokenCookie != null) {
            csrfToken = tokenCookie.getValue();
            System.out.println("Captured _token from cookies: " + csrfToken);
        } else {
            System.out.println("_token not found in cookies. Checking response body...");

            // If _token is not in cookies, try checking the response body or headers
            String responseBody = loginResponse.getBody().asString();
            System.out.println("Response Body: " + responseBody);

            // You might need to parse the response body to find the _token, if it's not in cookies
        }
    }


    @Test(dependsOnMethods = {"adminLogin"})
    public void essAccountCreation() {

        EssUser essUser = new EssUser();
        essUser.setEmpNumber(22);
        essUser.setPassword("SShaheen11");
        essUser.setStatus(true);
        essUser.setUsername("SShaheen11");
        essUser.setUserRoleId(2);

        given()
                .body(essUser)
                .contentType(ContentType.JSON)
                .cookies(cookies)

                .when()
                .post("/web/index.php/api/v2/admin/users")

                .then()
                .statusCode(200)
                .log().body();
    }
}
