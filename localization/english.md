# An example of the implementation of a clean architecture in autotests on Kotlin


Please, read ["Clean architecture in autotests"](http://blog.niiqa.net/clean-arc-in-tests)
before.

## Base
Requirements:
- Java 11
- Selenium Web Driver. For correct example work you need download the **_chromedriver_**
  (from [there](https://chromedriver.chromium.org/downloads)) and change driver settings in
  **_UiWebDriver.java_** (line 22):
  ```java
    public UiWebDriver() {
        System.setProperty("webdriver.chrome.driver", "C:/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }
  ```

## Test Definition Objects (TDO)

* **Directory:** src/main/java
* **Pacjage:** net.niiqa.clean_arc_example_java.tdo

The package contains examples of TDO for API and UI tests (in the corresponding packages).
Important: TDO classes are generics, and in the API TDO the response object class is used
as a generalization (for example, Response in RestAssured), and in the UI TDO the web
element class is used (WebElement in Selenium WebDriver).

Implemented when the client is injected:
```java
@Inject
private BaseClient<T> client;
```
and when creating variables:
```java
private T response = null;
```

Dependency Injection pattern is implemented using the **_Guile_** package, the client
and/or web driver implementations are placed in context in tests (see **Test Cases**).

The directory **_src/main/resources/contracts_** contains Swagger files used for
initializing the client and for API tests (and for checking contracts).



## Test cases

### Test case code

* **Directory:** src/test/java
* **Package:** net.niiqa.clean_arc_example_java.tests

An example of the implementation of API and UI tests. In this example, **_rest Assured_**
is used as the client API, and for UI, the tests are based on Selenium WebDriver.

A TDO is injected into the test class (from the context):
```java
@Inject
private Endpoint1Obj<Response> endpoint1;
```
In the UI class, an instance of the web driver is additionally connected to manage
sessions from the test:
```java
@Inject
private UiWrapper<WebElement> webDriver;
```
When declaring, the specific classes of the response and web element used are specified.

### Context

* **Directory:** src/test/java
* **Package:** net.niiqa.clean_arc_example_java.context

The context class implements the Module interface from the package **_juice_**. In the
**_configure_** method, the client/web driver implementation selected for the test is
added to the test context:
```java
bind(new TypeLiteral<BaseClient<Response>>(){}).toInstance(new BaseClientRa());
```
and also TDO - in this way, a specific client is connected to TDO and the results of his
work (Response, WebElement) are used in the test.

### Settings

* **Directory:** src/test/resources

Here is the file **test_settings.properties** containing all the settings and data necessary
for conducting tests. The file is read using the **_environment_** module, after which the
environment variables are available in the test in the **_settings_** parameter
(see **Custom Framework**).


## Custom Framework

* **Directory:** src/main/java
* **Package:** net.niiqa.clean_arc_example_java.core

Framework contains from 3 main parts:
* Framework Core Class (from which tests are inherited)
* Interfaces package (for use in TDO and clients)
* Base context

### Framework Core

A class that is the parent for tests and implements basic functionality, for example,
reading environment variables from the test settings file:
```java
@Inject
protected Environment environment;

protected Map<String, String> settings;

@BeforeSuite
public void getEnv() {
        environment.readSettings("test_settings.properties");
        settings = environment.getEnvironmentSettings();
}
```

### Interfaces

* **Directory:** src/main/java
* **Package:** net.niiqa.clean_arc_example_java.core.interfaces

The package contains interfaces for clients that will be used in TDO in the future.

### Base Context

* **Directory:** src/main/java
* **Package:** net.niiqa.clean_arc_example_java.core.context

Connects the dependencies necessary for all tests (for example, getting environment
variables). Initialization takes place in the base class of the framework **Framework Core**:
```java
@Guice(modules = {BaseContext.class})
public class EnsembleCore {
    // Framework code
}
```


## Integrations

* **Directory:** src/main/java
* **Package:** net.niiqa.clean_arc_example_java.integrations

The example shows the implementation of clients and drivers for tests.

** IMPORTANT!** **_All_** clients must implement the appropriate interfaces from the
package **_core.interfaces_**!

## Maintainers
- [Denis Kudriashov](https://github.com/qx57)