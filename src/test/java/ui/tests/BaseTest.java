package ui.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import ui.framework.Browser;
import ui.framework.WebDriverFactory;

/**
 * @author BZagorski on 6/3/2017.
 */
public class BaseTest {

    private Browser browser = Browser.Chrome;

    @BeforeClass
    public void setUp() {
        Configuration.reopenBrowserOnFail = false;
        Configuration.baseUrl = "http://localhost:8080";
        WebDriverRunner.setWebDriver(WebDriverFactory.get(browser));
        WebDriverRunner.getWebDriver().manage().window().maximize();
    }

    @AfterClass
    public void tearDown() {
        WebDriverRunner.getWebDriver().quit();
    }
}
