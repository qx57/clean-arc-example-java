package net.niiqa.clean_arc_example_java.context;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import io.restassured.response.Response;
import net.niiqa.clean_arc_example_java.core.interfaces.BaseClient;
import net.niiqa.clean_arc_example_java.integrations.api_client.BaseClientRa;
import net.niiqa.clean_arc_example_java.tdo.api.Endpoint1Obj;
import net.niiqa.clean_arc_example_java.tdo.api.Endpoint2Obj;

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
