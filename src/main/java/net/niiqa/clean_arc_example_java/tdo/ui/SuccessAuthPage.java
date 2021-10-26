package net.niiqa.clean_arc_example_java.tdo.ui;

import com.google.inject.Inject;
import net.niiqa.clean_arc_example_java.core.interfaces.UiWrapper;

/**
 * This is Test Definition Object for UI page
 *
 * It used for define all of impact points
 * of real test pages
 */
public class SuccessAuthPage<WE> {

    /**
     * Inject the various web driver wrapper
     */
    @Inject
    private UiWrapper<WE> webDriver;

    /**
     * set all needed constants (like css, xpath etc)
     */
    private static final String MESSAGE_FIELD = "div.message";

    /**
     * initiate page object state after loading
     */
    public SuccessAuthPage<WE> init() {
        webDriver.awaitVisible(30L, MESSAGE_FIELD);
        return this;
    }

    /**
     * Something do
     */
    public String getMessage() {
        return webDriver.getInnerText(webDriver.getElementByCss(MESSAGE_FIELD));
    }
}
