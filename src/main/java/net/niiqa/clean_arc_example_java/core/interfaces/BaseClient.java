package net.niiqa.clean_arc_example_java.core.interfaces;

/**
 * Example of common API clients interface
 *
 * There are:
 * - configuration for client
 * - request execution
 * - contract checking (in isRightSchema method)
 */
public interface BaseClient<T> {

    void configure(String baseUrl, String schema);

    <R> T execute(R request);

    Boolean isRightSchema(T response, String schema, String definitionName);
}
