import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class ApiClient {
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

               System.out.println(successCode);

        JSONObject jsonResponse = new JSONObject(response.toString());
        System.out.println(jsonResponse.getString("sessionToken"));

//        Response response = given()
//                .contentType("application/json")
//                //.accept("application/json")
//                .body(userDetails)
//                //.when()
//                .put(path);
//                //.then()
//                //.statusCode(200)
//                //.extract()
//                //.response();
//
//        JSONArray jsonResponse = new JSONArray(response.asString());
//        String sessionToken = jsonResponse.getJSONObject(0).getString("sessionToken");
//        System.out.println(sessionToken);


    }
}
