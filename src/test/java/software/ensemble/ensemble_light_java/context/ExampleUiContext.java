package software.ensemble.ensemble_light_java.context;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import org.openqa.selenium.WebElement;
import software.ensemble.ensemble_light_java.core.interfaces.UiWrapper;
import software.ensemble.ensemble_light_java.integrations.web_driver.UiWebDriver;
import software.ensemble.ensemble_light_java.tdo.ui.LoginPage;
import software.ensemble.ensemble_light_java.tdo.ui.SuccessAuthPage;

/**
 * UI test context class
 */
public class ExampleUiContext extends AbstractModule {

    /**
     * All of dependings needed for test run
     * can be defined there
     */
    @Override
    protected void configure() {
        bind(new TypeLiteral<UiWrapper<WebElement>>(){}).toInstance(new UiWebDriver());
        bind(new TypeLiteral<LoginPage<WebElement>>(){});
        bind(new TypeLiteral<SuccessAuthPage<WebElement>>(){});
    }
}
