package ui.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.testng.annotations.Test;
import ui.framework.Nav;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

/**
 * @author BZagorski on 6/3/2017.
 */
public class GifUploadPage  {

    @FindBy(id = "#description") private SelenideElement gifDesciption;
    @FindBy(id = "#category.id") private SelenideElement categoryOptions;
    @FindBy(css = "submit") private SelenideElement uploadButton;
    @FindBy(linkText = "Cancel") private SelenideElement cancelButton;

    public GifUploadPage fillDescription(String description) {
        gifDesciption.setValue(description);
        return this;
    }

    public GifUploadPage selectCategory(String category) {
        categoryOptions.selectOption(category);
        return this;
    }
}
