package software.ensemble.ensemble_light_java.tdo.api;

import com.google.common.io.Resources;
import com.google.inject.Inject;
import org.json.JSONObject;
import software.ensemble.ensemble_light_java.core.interfaces.BaseClient;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Test Object Definition class (like Endpoint1Obj.kt)
 *
 * There are different description of methods with Endpoint1Obj
 */
public class Endpoint2Obj<T> {

    private final String schema = "contracts/endpoint_2_swagger_schema.json";

    @Inject
    private BaseClient<T> client;

    /**
     * Request execution method with example of payload generation
     */
    public T request(String baseUrl, Integer processId) throws IOException {
        URL url = Resources.getResource(schema);
        String content = Resources.toString(url, Charset.defaultCharset());
        client.configure(baseUrl, content);
        JSONObject payload = new JSONObject();
        payload.put("processId", processId);
        return client.execute(payload.toString());
    }
}
