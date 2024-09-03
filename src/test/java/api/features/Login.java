package api.features;


import api.setup.Setup;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Login extends Setup {

    Cookies cookies;

    @Test
    public void essLogin() {

        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", "SShaheen11");
        credentials.put("password", "SShaheen11");

        cookies = given()
                .body(credentials)
                .log().all()
                .contentType(ContentType.JSON)

                .when()
                .post("/web/index.php/auth/validate")

                .then()
                .statusCode(302)
                .log().all()
                .contentType(ContentType.HTML)
                .extract().response().getDetailedCookies();
    }

    @Test(dataProvider = "loginCredentials")
    public void essLoginNegative(String username, String password) {

        Map<String, String> jsonBody = new HashMap<>();
        jsonBody.put("username", username);
        jsonBody.put("password", password);

        given()
                .body(jsonBody)
                .contentType(ContentType.JSON)
                .log().uri()
                .log().body()

                .when()
                .post("/web/index.php/auth/validate")

                .then()
                .statusCode(302)
                .log().all();
    }

    @DataProvider(name = "loginCredentials")
    public Object[][] loginData() {
        return new Object[][]{
                {"GButlers", "7Sbhaheen7"},   // both invalid username & password
                {"SShaheen11", "JButlers"},   // valid username, invalid password
                {"GButlers", "SShaheen11"}    // invalid username, valid password
        };
    }
}
