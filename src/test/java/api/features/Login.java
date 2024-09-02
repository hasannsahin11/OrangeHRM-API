package api.features;


import api.models.EssUser;
import api.setup.ApiTestSetup;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Login extends ApiTestSetup {

    Cookies cookies;

    @Test
    public void essLogin() {

        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", "SHaheen11");
        credentials.put("password", "SHaheen11");

        cookies = given()
                .contentType(ContentType.JSON)
                .body(credentials)
//                .log().body()

                .when()
                .post("/web/index.php/auth/validate")

                .then()
                .log().body()
                .statusCode(302)
                .extract().response().getDetailedCookies();
    }
}
