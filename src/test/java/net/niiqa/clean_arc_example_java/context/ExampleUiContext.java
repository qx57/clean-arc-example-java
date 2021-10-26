package net.niiqa.clean_arc_example_java.context;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import net.niiqa.clean_arc_example_java.core.interfaces.UiWrapper;
import net.niiqa.clean_arc_example_java.integrations.web_driver.UiWebDriver;
import net.niiqa.clean_arc_example_java.tdo.ui.LoginPage;
import net.niiqa.clean_arc_example_java.tdo.ui.SuccessAuthPage;
import org.openqa.selenium.WebElement;

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
