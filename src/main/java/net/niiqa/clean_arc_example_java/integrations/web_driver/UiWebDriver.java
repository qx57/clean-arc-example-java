package net.niiqa.clean_arc_example_java.integrations.web_driver;

import net.niiqa.clean_arc_example_java.core.interfaces.UiWrapper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class UiWebDriver implements UiWrapper<WebElement> {

    private ChromeDriver driver;

    /**
     * Web driver initialization
     *
     * You can move properties into settings file
     */
    public UiWebDriver() {
        System.setProperty("webdriver.chrome.driver", "C:/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Override
    public void loadPage(String url) {
        driver.get(url);
    }

    @Override
    public WebElement getElementByCss(String css) {
        return driver.findElement(By.cssSelector(css));
    }

    @Override
    public WebElement getElementByXpath(String xpath) {
        return driver.findElement(By.xpath(xpath));
    }

    @Override
    public void setFieldValue(WebElement field, String value) {
        field.sendKeys(value);
    }

    @Override
    public String getFieldValue(WebElement field) {
        return field.getAttribute("value");
    }

    @Override
    public void clickElement(WebElement el) {
        el.click();
    }

    @Override
    public String getInnerText(WebElement el) {
        return el.getText();
    }

    @Override
    public Boolean awaitVisible(Long duration, String css) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(duration));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(css)));
        return driver.findElement(By.cssSelector(css)).isDisplayed();
    }

    @Override
    public void close() {
        driver.close();
    }
}
