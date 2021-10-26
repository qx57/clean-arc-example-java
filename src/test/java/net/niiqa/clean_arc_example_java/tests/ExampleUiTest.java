package net.niiqa.clean_arc_example_java.tests;

import com.google.inject.Inject;
import net.niiqa.clean_arc_example_java.core.EnsembleCore;
import net.niiqa.clean_arc_example_java.core.interfaces.UiWrapper;
import net.niiqa.clean_arc_example_java.tdo.ui.LoginPage;
import net.niiqa.clean_arc_example_java.tdo.ui.SuccessAuthPage;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;
import net.niiqa.clean_arc_example_java.context.ExampleUiContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Example test class for UI tests with Selenium web driver
 *
 * For use concrete client's realisations create context
 */
@Guice(modules = {ExampleUiContext.class})
public class ExampleUiTest extends EnsembleCore {

    /**
     * Inject web driver (or wrapper) for session control possibility
     */
    @Inject
    private UiWrapper<WebElement> webDriver;
    /**
     * Inject the page objects from context (basically you don't need add it to context class, only 3rd-party objects)
     */
    @Inject
    private LoginPage<WebElement> loginPage;
    @Inject
    private SuccessAuthPage<WebElement> successPage;

    /**
     * Test data for example UI test
     *
     * If needed you can push it to test settings
     */
    private static final String LOGIN = "example_login";
    private static final String PASSWD = "very_strong_password";

    /**
     * !!! THIS IS A FEATURE !!!
     *
     * Have no idea why we get empty settings when tests started by **mvn test**
     */
    @BeforeClass
    public void initEnv() {
        super.getEnv();
    }

    /**
     * There is the example test method
     * For UI tests
     */
    @Test
    public void exampleUITest() {
        step("Load main page");
        loginPage.loadPage(settings.get("login_page.url." + settings.get("env")));
        step("Set login n password fields");
        loginPage.setLogin(LOGIN);
        assertThat(loginPage.getLoginValue()).isEqualTo(LOGIN);
        loginPage.setPassword(PASSWD);
        assertThat(loginPage.getPasswordValue()).isEqualTo(PASSWD);
        step("Send auth form");
        loginPage.sendForm();
        step("Check authorization");
        assertThat(successPage.init().getMessage()).isEqualTo("Access granded!");
    }

    /**
     * Close sessions manually
     */
    @AfterClass
    public void deinit() {
        webDriver.close();
    }
}
