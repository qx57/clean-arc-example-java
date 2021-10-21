package software.ensemble.ensemble_light_java.core.interfaces;

/**
 * UI Web driver wrapper interface
 *
 * There are some methods for all of cases in test or test steps
 * There is no final list of methods, in your project you can change it for your requirements
 */
public interface UiWrapper<WE> {

    /**
     * load the page
     */
    void loadPage(String url);

    /**
     * find element in DOM by css selector
     */
    WE getElementByCss(String css);

    /**
     * find element in DOM by xpath
     */
    WE getElementByXpath(String xpath);

    /**
     * set various field value (for filling forms, for example)
     */
    void setFieldValue(WE field, String value);

    /**
     * get field value (if you want check the field filling)
     */
    String getFieldValue(WE field);

    /**
     * Click on various DOM element
     */
    void clickElement(WE el);

    /**
     * get innet text from various DOM element
     */
    String getInnerText(WE el);

    /**
     * one of awaitings
     */
    Boolean awaitVisible(Long duration, String css);

    /**
     * close session for web driver instance
     */
    void close();
}
