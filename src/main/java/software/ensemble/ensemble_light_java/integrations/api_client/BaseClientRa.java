package software.ensemble.ensemble_light_java.integrations.api_client;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSender;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.json.JSONException;
import org.json.JSONObject;
import software.ensemble.ensemble_light_java.core.interfaces.BaseClient;
import software.ensemble.ensemble_light_java.integrations.api_client.helpers.HttpMethod;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

/**
 * Concrete realisation of
 * common API interface
 *
 * For example, here code for based on RestAssured client
 */
public class BaseClientRa implements BaseClient<Response> {

    /**
     * Request and response specifications for logging our requests
     */
    private final RequestSpecification baseRequestSpec = new RequestSpecBuilder().log(LogDetail.ALL).build();
    private final ResponseSpecification baseResponseSpec = new ResponseSpecBuilder().log(LogDetail.ALL).build();

    /**
     * Define URL, path and method
     */
    private String url = "";
    private String path = "";
    private String method = "";

    /**
     * Set default base header's values
     */
    private ContentType accept = ContentType.JSON;
    private ContentType contentType = ContentType.JSON;

    /**
     * Set empty other headers
     */
    private Map<String, String> headers = new HashMap<>();

    /**
     * Configuration for our client
     *
     * This method be used when you want to configure client for new request
     *
     * baseUrl - API url for current test
     * schema - Swagger schema in string (for set some client properties)
     */
    @Override
    public void configure(String baseUrl, String schema) {
        url = baseUrl;
        var schemaJson = new JSONObject(schema);
        path = schemaJson.getJSONObject("paths").keySet().stream().findFirst().get();
        method = schemaJson.getJSONObject("paths").getJSONObject(this.path).keySet().stream().findFirst().get().toUpperCase(Locale.ROOT);
    }

    /**
     * Execution method for interface realisation
     *
     * There we create all of requests for our API
     * Use specifications for set your custom client as params as required your tests
     *
     * request - payload object for request body
     * type - object for body matching
     */
    @Override
    public <R> Response execute(R request) {
        // Prepare request
        Response response = null;
        RequestSpecification requestSpecification = baseRequestSpec.accept(accept);
        requestSpecification = request != null
                ? requestSpecification.contentType(contentType).body(request)
                : requestSpecification;
        // set headers and reponse specs
        RequestSender sender = RestAssured.given(requestSpecification.headers(headers), baseResponseSpec);
        // send request
        switch (method) {
            case HttpMethod.GET:    response = sender.get(url + path);      break;
            case HttpMethod.POST:   response = sender.post(url + path);     break;
            case HttpMethod.PUT:    response = sender.put(url + path);      break;
            case HttpMethod.PATCH:  response = sender.patch(url + path);    break;
            case HttpMethod.DELETE: response = sender.delete(url + path);   break;
        }
        return response;
    }

    /**
     * Check the contract by Swagger scheme
     *
     * response - Rest Assured Response object
     * schema - Swagger schema in string (from resource file)
     * definitionName - name of definition object which we must check (from Swagger file)
     */
    @Override
    public Boolean isRightSchema(Response response, String schema, String definitionName) {
        Boolean result = true;

        // create schema for checking
        JSONObject defSchema = new JSONObject();
        defSchema.append("$schema", "http://json-schema.org/draft-04/schema#");
        defSchema.append("type", "object");

        // set the properties and requires
        JSONObject schemaJson = new JSONObject(schema);
        JSONObject properties = schemaJson.getJSONObject("definitions").getJSONObject(definitionName).getJSONObject("properties");
        defSchema.put("properties", properties);

        //check contract
        try {
            response.then().assertThat().body(matchesJsonSchema(defSchema.toString()));
        } catch(Exception e) {
            result = false;
        }
        return result;
    }
}
