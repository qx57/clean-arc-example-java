package software.ensemble.ensemble_light_java.tdo.api;

import com.google.common.io.Resources;
import com.google.inject.Inject;
import software.ensemble.ensemble_light_java.core.interfaces.BaseClient;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

import static com.google.common.io.Resources.getResource;

/**
 * This is Test Definition Object for API endpoint
 *
 * It used for define all of impact points
 * of real test object
 */
public class Endpoint1Obj<T> {

    /**
     * Scheme from Swagger for current endpoint
     */
    private String schema = "contracts/endpoint_1_swagger_schema.json";

    /**
     * Inject the client and our test settings below
     */
    @Inject
    private BaseClient<T> client;

    /**
     * other params of test obj condition
     */
    private T response = null;

    /**
     * send request and save response for future operations
     *
     * baseUrl - test service url for current test environment (may be different for different test environments)
     */
    public T request(String baseUrl) throws IOException {
        URL url = Resources.getResource(schema);
        String content = Resources.toString(url, Charset.defaultCharset());
        client.configure(baseUrl, content);
        response = client.execute(null);
        return response;
    }

    /**
     * Check the contract (by swagger schema)
     *
     * definitionName - name of definition in Swagger schema for body checking (may be different, see JSON in file)
     */
    public Boolean isRightDefinition(String definitionName) throws IOException {
        URL url = Resources.getResource(schema);
        String content = Resources.toString(url, Charset.defaultCharset());
        return client.isRightSchema(response, content, definitionName);
    }
}
