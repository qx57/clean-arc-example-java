package software.ensemble.ensemble_light_java.context;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import io.restassured.response.Response;
import software.ensemble.ensemble_light_java.core.interfaces.BaseClient;
import software.ensemble.ensemble_light_java.integrations.api_client.BaseClientRa;
import software.ensemble.ensemble_light_java.tdo.api.Endpoint1Obj;
import software.ensemble.ensemble_light_java.tdo.api.Endpoint2Obj;

/**
 * API test context class
 */
public class ExampleApiContext extends AbstractModule {

    /**
     * All of dependings needed for test run
     * can be defined there
     */
    @Override
    protected void configure() {
        /**
         * Like API client...
         */
        bind(new TypeLiteral<BaseClient<Response>>(){}).toInstance(new BaseClientRa());
        /**
         * Or Test Definition Objects...
         */
        bind(new TypeLiteral<Endpoint1Obj<Response>>(){});
        bind(new TypeLiteral<Endpoint2Obj<Response>>(){});
        /**
         * And so more
         */
    }
}
