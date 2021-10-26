package net.niiqa.clean_arc_example_java.tdo.ui;

import com.google.inject.Inject;
import net.niiqa.clean_arc_example_java.core.interfaces.UiWrapper;

/**
 * This is Test Definition Object for UI page
 *
 * It used for define all of impact points
 * of real test pages
 */
public class LoginPage<WE> {

    /**
     * Inject the various web driver wrapper
     */
    @Inject
    private UiWrapper<WE> webDriver;

    /**
     * Page elements
     *
     * You can hide field's implementations through CUSTOM annotations (like @FindBy for example)
     * Use AOP for beautifulize code
     */
    private WE loginField() { return webDriver.getElementByCss("#login_field"); }
    private WE passwordField() { return webDriver.getElementByXpath("/html/body/form/input[2]"); }
    private WE formButton() { return webDriver.getElementByCss("#submit"); }

    /**
     * Get signin page
     */
    public void loadPage(String url) {
        webDriver.loadPage(url);
    }

    /**
     * Set the login field value
     */
    public void setLogin(String login) {
        webDriver.setFieldValue(loginField(), login);
    }

    /**
     * Getter for login field
     */
    public String getLoginValue() {
        return webDriver.getFieldValue(loginField());
    }

    /**
     * Set the password field value
     */
    public void setPassword(String pwd) {
        webDriver.setFieldValue(passwordField(), pwd);
    }

    /**
     * Getter for password field
     */
    public String getPasswordValue() {
        return webDriver.getFieldValue(passwordField());
    }

    /**
     * send signin form
     */
    public void sendForm() {
        webDriver.clickElement(formButton());
    }
}
