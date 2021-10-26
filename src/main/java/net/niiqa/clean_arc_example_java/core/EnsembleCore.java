package net.niiqa.clean_arc_example_java.core;

import com.google.inject.Inject;
import net.niiqa.clean_arc_example_java.integrations.environment.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Guice;
import net.niiqa.clean_arc_example_java.core.context.BaseContext;

import java.util.Map;

/**
 * Custom framework class
 *
 * Used for logging, some before/after common operations etc
 */
@Guice(modules = {BaseContext.class})
public class EnsembleCore {

    private static final Logger logger = LoggerFactory.getLogger(EnsembleCore.class);

    /**
     * Inject of environment module
     *
     * Available in test classes
     */
    @Inject
    protected Environment environment;

    /**
     * Property with environment settings
     */
    protected Map<String, String> settings;

    /**
     * Before suite method where we receive environment settings (from file, configserver etc)
     *
     * If you want to configure test environment on-air - use environment integration module directly
     */
    @BeforeSuite
    public void getEnv() {
        environment.readSettings("test_settings.properties");
        settings = environment.getEnvironmentSettings();
    }

    @BeforeMethod
    public void before() {
        /**
         * There you can write your code if it required
         * Also you can write all Before/After methods in core too
         * And also you can change base test framework (TesnTG to JUnit for example etc)
         */
    }

    /**
     * Utility methods also can be write there
     * Like this, for example
     */
    public void step(String name) {
        logger.info(name);
    }
}
