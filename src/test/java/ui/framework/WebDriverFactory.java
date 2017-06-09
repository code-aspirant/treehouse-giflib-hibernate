package ui.framework;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import io.github.bonigarcia.wdm.PhantomJsDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

/**
 * @author BZagorski on 6/3/2017.
 */
public class WebDriverFactory {

    public static WebDriver get(Browser browser) {
        switch (browser) {
            case Chrome:
                ChromeDriverManager.getInstance().setup();
                return new ChromeDriver();
            case Firefox:
                FirefoxDriverManager.getInstance().setup();
                return new FirefoxDriver();
            case Internet_Explorer:
                InternetExplorerDriverManager.getInstance().setup();
                return new InternetExplorerDriver();
            case Phantom:
                PhantomJsDriverManager.getInstance().setup();
                return new PhantomJSDriver();
            default:
                throw new IllegalArgumentException("Please select a valid browser.");
        }
    }
}
