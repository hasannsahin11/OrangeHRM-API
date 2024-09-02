package api.features;


import api.setup.Setup;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Login extends Setup {

    Cookies cookies;

    @Test
    public void essLogin() {

        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", "SHaheen11");
        credentials.put("password", "SHaheen11");

        cookies = given()
                .body(credentials)
                .spec(requestSpec)

                .when()
                .post("/web/index.php/auth/validate")

                .then()
                .statusCode(302)
                .spec(responseSpec)
                .contentType(ContentType.HTML)
                .extract().response().getDetailedCookies();
    }
}
