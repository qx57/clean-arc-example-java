package software.ensemble.ensemble_light_java.core.interfaces;

import java.util.Map;

/**
 * Interface for Environment Configurator
 */
public interface Env {

    /**
     * Read settings from source (like files, urls etc)
     */
    void readSettings(String resource);

    /**
     * Get Environment params
     */
    Map<String, String> getEnvironmentSettings();
}
