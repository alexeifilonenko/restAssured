import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class ApiClient {
    public String sessionToken;
    private final String path = "https://stage-platform.kino-mo.com/api/admin/0/session";

    public void getRequestExampleTest() {
        Response response = get("http://restcountries.eu/rest/v1/name/uk");
        JSONArray jsonResponse = new JSONArray(response.asString());
        String capital = jsonResponse.getJSONObject(0).getString("capital");
        System.out.println(capital);
    }

    public void putRequestExampleTest() throws JSONException {

        JSONObject requestBody = new JSONObject();
        requestBody.put("username", "a.filonenko@hypervsn.com");
        requestBody.put("password", "Filonen-ko.com");
        System.out.println(requestBody);

        RequestSpecification requets = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(requestBody.toString());

        Response response = requets.put(path);


        int statusCode = response.getStatusCode();
        int successCode = response.jsonPath().get("status");




       JSONObject jsonResponse = new JSONObject(response.asString());

       System.out.println(jsonResponse.getJSONObject("message").getString("sessionToken"));
       String sessionToken = jsonResponse.getJSONObject("message").getString("sessionToken");
    }

    public void createClient() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("name", "test123456789");
        requestBody.put("legalName", "test123456789");

        JSONArray jsonArrayPhone = new JSONArray();
        jsonArrayPhone.put(0, "+123456789");

        JSONArray jsonArrayEmail = new JSONArray();
        jsonArrayEmail.put(0, "test123@test123.test123");

        requestBody.put("phone", jsonArrayPhone);
        requestBody.put("email", jsonArrayEmail);

        List<Double> coordinates = new ArrayList<>(Arrays.asList(51.5073509,-0.12775829999998223));

        JSONObject defaultLocation = new JSONObject();
        defaultLocation.put("type", "Point");
        defaultLocation.put("coordinates", coordinates);
        requestBody.put("defaultLocation", defaultLocation);


        JSONArray allowedPermissions = new JSONArray();
        allowedPermissions.put(0, "user:user:create:general");

        requestBody.put("allowedPermissions", allowedPermissions);

        System.out.println(requestBody);



       /* RequestSpecification requets = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("km-auth", sessionToken)
                .body(requestBody.toString());

        Response response = requets.put(path);*/
    }
}
