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

import static io.restassured.RestAssured.when;
import static io.restassured.config.RestAssuredConfig.config;
import static sun.net.ftp.impl.FtpClient.create;

public class ApiClient {
    public String sessionToken = "9b64d6b8ef5288c57af9a4a308b15dc537514a5c81f3036b65537d85affcdc8c5bd6ff7701cf9b61d3becb48";
    private final String baseUrl = "https://stage-platform.kino-mo.com";
    private final String pathSession = "/api/admin/0/session";
    private final String pathClient = "/api/admin/0/client/";
    private String clientId;


    public String putSessionToken() throws JSONException {

        JSONObject requestBody = new JSONObject();
        requestBody.put("username", "filonenko@hypervsn.com");
        requestBody.put("password", "Filonko.com");
        System.out.println(requestBody);

        RequestSpecification requets = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(requestBody.toString());

        Response response = requets.put(baseUrl + pathSession);
        int statusCode = response.getStatusCode();
        int successCode = response.jsonPath().get("status");

        JSONObject jsonResponse = new JSONObject(response.asString());
        //System.out.println(jsonResponse);
        System.out.println("sessionToken: " + sessionToken);
        String sessionToken = jsonResponse.getJSONObject("message").getString("sessionToken");
        return sessionToken;
    }

    public String createClient() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("name", "t1e1s11t111213456719");
        requestBody.put("legalName", "te1s1t12134156789");

        JSONArray jsonArrayPhone = new JSONArray();
        jsonArrayPhone.put(0, "+1234156789");

        JSONArray jsonArrayEmail = new JSONArray();
        jsonArrayEmail.put(0, "te111st111123@test1123.test123");

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

        Response response = requets.put(baseUrl + pathClient);

        JSONObject jsonResponse = new JSONObject(response.asString());
        System.out.println(jsonResponse);
        String clientId = jsonResponse.getJSONObject("message").getString("_id");
        System.out.println("Id created client " + clientId);
        return clientId;
    }

    public void getClientById(String clientId) {
        /*RestAssured.config = RestAssured.config()
                .objectMapperConfig(ObjectMapperConfig.objectMapperConfig()
                        .gsonObjectMapperFactory( (type, charset) -> new GsonBuilder().create() ));*/

        RequestSpecification requets = given()
                .baseUri(baseUrl)
                .basePath(pathClient + clientId)
                .header("Content-Type", "application/json")
                .header("km-auth", "d58736104ac1b62f240efcd069b502cd6c3ee12d17d0f8737ce274393a39f4b95bd6ff7701cf9b61d3becb48");


        Response response = requets.when().get();


        JSONObject jsonObject = new JSONObject(response.asString());
        System.out.println(response.asString());
        System.out.println(jsonObject);
        //List<Client> returnClient = Arrays.asList(response.getBody().as(Client[].class));
        String returnClient = jsonObject.getJSONObject("message").getString("_id");
        System.out.println(returnClient);

        // List<Client> clientResponse = response.jsonPath().getList("message", Client.class);
        //System.out.println(clientResponse);
        Client client = response.jsonPath().getObject("message", Client.class);
        System.out.println(client.get_id() + client.getName());


    }
}
