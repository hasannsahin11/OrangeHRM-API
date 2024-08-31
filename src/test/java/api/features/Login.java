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


    @Test
    public void essAccountCreation() {
        Cookie restAssuredCookie = new Cookie.Builder("orangehrm", sessionCookie)
                .setDomain("opensource-demo.orangehrmlive.com")
                .setPath("/web")
                .setSecured(true)
                .build();


        EssUser essUser = new EssUser();
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
}
