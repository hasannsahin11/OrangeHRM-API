package api.features;

import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Login {

    Cookies cookies;

    @Test
    public void adminLogin() {

        Map<String, String> adminCredentials = new HashMap<>();
        adminCredentials.put("username", "Admin");
        adminCredentials.put("password", "admin123");

        cookies = given()
                .body(adminCredentials)
                .contentType(ContentType.JSON)

                .when()
                .post("https://opensource-demo.orangehrmlive.com/web/index.php/auth/validate")

                .then()
                .statusCode(302)
                .log().body()
                .extract().response().getDetailedCookies();

    }
}
