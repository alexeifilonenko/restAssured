import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

import com.google.gson.GsonBuilder;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;


public class ApiClient {
    public String sessionToken;
    private final String baseUrl = "https://stage-platform.kino-mo.com";
    private final String pathSession = "/api/admin/0/session";
    private final String pathClient = "/api/admin/0/client/";
    private String clientId;


    public String putSessionToken() throws JSONException {

        JSONObject requestBody = new JSONObject();
        requestBody.put("username", "lenko@hypervsn.com");
        requestBody.put("password", "onen-ko.com");
        //System.out.println(requestBody);

        RequestSpecification requets = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(requestBody.toString());

        Response response = requets.put(baseUrl + pathSession);

        JSONObject jsonResponse = new JSONObject(response.asString());
        //System.out.println(jsonResponse);
        sessionToken = jsonResponse.getJSONObject("message").getString("sessionToken");
        System.out.println("sessionToken: " + sessionToken);
        return sessionToken;
    }

    public String createClient() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("name", "t11e1s22111");
        requestBody.put("legalName", "t1e1122s");

        JSONArray jsonArrayPhone = new JSONArray();
        jsonArrayPhone.put(0, "+1234156789");

        JSONArray jsonArrayEmail = new JSONArray();
        jsonArrayEmail.put(0, "te12211113@test1123.test123");

        requestBody.put("phone", jsonArrayPhone);
        requestBody.put("email", jsonArrayEmail);

        List<Double> coordinates = new ArrayList<>(Arrays.asList(51.5073509, -0.12775829999998223));

        JSONObject defaultLocation = new JSONObject();
        defaultLocation.put("type", "Point");
        defaultLocation.put("coordinates", coordinates);
        requestBody.put("defaultLocation", defaultLocation);

        JSONArray allowedPermissions = new JSONArray();
        allowedPermissions.put(0, "user:user:create:general");
        requestBody.put("allowedPermissions", allowedPermissions);
        //System.out.println(requestBody);

        RequestSpecification requets = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("km-auth", sessionToken)
                .body(requestBody.toString());

        Response response = requets.put(baseUrl + pathClient);

        JSONObject jsonResponse = new JSONObject(response.asString());
        //System.out.println(jsonResponse);
        clientId = jsonResponse.getJSONObject("message").getString("_id");
        System.out.println("ID created client: " + clientId);
        return clientId;
    }

    public void getClientById() {

        RequestSpecification requets = given()
                .baseUri(baseUrl)
                .basePath(pathClient + clientId)
                .header("Content-Type", "application/json")
                .header("km-auth", sessionToken);


        Response response = requets.when().get();


        JSONObject jsonObject = new JSONObject(response.asString());
        String returnClient = jsonObject.getJSONObject("message").getString("_id");
        System.out.println(returnClient);
        Client client = response.jsonPath().getObject("message", Client.class);
        System.out.println("Client ID: " + client.get_id() + " Client name: " + client.getName());
    }

    public String updateClient(String clientId) {

        JSONObject requestBody = new JSONObject();
        requestBody.put("name", "t1e1s11t11121347777");
        requestBody.put("legalName", "te1s1t12134177777");

        JSONArray jsonArrayPhone = new JSONArray();
        jsonArrayPhone.put(0, "+1234156789");

        JSONArray jsonArrayEmail = new JSONArray();
        jsonArrayEmail.put(0, "te111st111123@test1123.test7777");

        requestBody.put("phone", jsonArrayPhone);
        requestBody.put("email", jsonArrayEmail);

        List<Double> coordinates = new ArrayList<>(Arrays.asList(51.5073509, -0.12775829999998223));

        JSONObject defaultLocation = new JSONObject();
        defaultLocation.put("type", "Point");
        defaultLocation.put("coordinates", coordinates);
        requestBody.put("defaultLocation", defaultLocation);

        JSONArray allowedPermissions = new JSONArray();
        allowedPermissions.put(0, "user:user:create:general");
        requestBody.put("allowedPermissions", allowedPermissions);
        System.out.println(requestBody);

        RequestSpecification requets = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("km-auth", sessionToken)
                .body(requestBody.toString());

        Response response = requets.post(baseUrl + pathClient + clientId);

        JSONObject jsonResponse = new JSONObject(response.asString());
        System.out.println(jsonResponse);

        return clientId;
    }
}
