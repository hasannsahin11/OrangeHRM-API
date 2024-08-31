package api.features;

import api.models.EssUser;
import api.setup.ApiTestSetup;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Login extends ApiTestSetup {

    Cookies cookies;

}
