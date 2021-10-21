package software.ensemble.ensemble_light_java.tests;

import com.google.inject.Inject;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.hamcrest.CoreMatchers;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;
import software.ensemble.ensemble_light_java.context.ExampleApiContext;
import software.ensemble.ensemble_light_java.core.EnsembleCore;
import software.ensemble.ensemble_light_java.tdo.api.Endpoint1Obj;
import software.ensemble.ensemble_light_java.tdo.api.Endpoint2Obj;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Example test class for API tests
 *
 * For use concrete client's realisations create context
 */
@Guice(modules = {ExampleApiContext.class})
public class ExampleApiTest extends EnsembleCore {

    /**
     * Inject th API Test Definition Objects (TDO's)
     */
    @Inject
    private Endpoint1Obj<Response> endpoint1;
    @Inject
    private Endpoint2Obj<Response> endpoint2;

    /**
     * There is the example test method
     * For API Request
     */
    @Test
    public void exampleApiTest() throws IOException {
        step("Send request");
        Response response = endpoint1.request(settings.get("service1.baseUrl." + settings.get("env")));
        step("Check response 200 Ok");
        // For example use Assertj matcher
        assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_OK);
        step("Check contract");
        /**
         * For example this is no matter that contract is right
         */
        //assertThat(endpoint1.isRightDefinition("StartSuccessDto")).isTrue();
        step("Check field id value");
        /**
         * Use default Rest Assured matcher (but this rise code connectivity)
         * Better define getter in TDO for receive value from various field and use third-party matcher
         */
        response.then().body("processId", CoreMatchers.notNullValue());
        step("send next request and check 201 Created");
        response = endpoint2.request(
                settings.get("service1.baseUrl." + settings.get("env")),
                response.then().extract().jsonPath().getInt("processId")
        );
        assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_CREATED);
    }
}
