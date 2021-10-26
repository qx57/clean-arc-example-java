package net.niiqa.clean_arc_example_java.integrations.environment;

import net.niiqa.clean_arc_example_java.core.interfaces.Env;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Environment Configurator class
 */
public class Environment implements Env {

    /**
     * Env params
     */
    private Map<String, String> settings = new HashMap<>();

    /**
     * read env params from source
     */
    @Override
    public void readSettings(String resource) {
        InputStream inSettings = this.getClass().getClassLoader().getResourceAsStream(resource);
        Properties props = new Properties();
        try {
            props.load(inSettings);
            props.propertyNames().asIterator().forEachRemaining(
                    it -> settings.put(it.toString(), props.getProperty(it.toString()))
            );
        } catch (IOException e) {
            throw new RuntimeException("Cannot read environment settings!");
        }
    }

    /**
     * take the env params
     */
    @Override
    public Map<String, String> getEnvironmentSettings() {
        return settings;
    }
}
