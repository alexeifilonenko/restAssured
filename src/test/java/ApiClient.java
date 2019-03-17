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

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", "a.filonenko@hypervsn.com");
        jsonObject.put("password", "Filonen-ko.com");
        System.out.println(jsonObject);

        RequestSpecification requets = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(jsonObject.toString());

        Response response = requets.put(path);



        JSONObject jsonResponse = new JSONObject(response);
        String sessionToken = jsonResponse.getString("sessionToken");
        System.out.println(sessionToken);

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
