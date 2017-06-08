package ui.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import ui.framework.Nav;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

/**
 * @author BZagorski on 6/3/2017.
 */
public class BasePage {

    public final SelenideElement NAV_BAR = $(".navbar-fixed");
    public final ElementsCollection GIFS = $$(".gif");
    public final SelenideElement SEARCH = $(By.name("q"));

    public void clickNavOption(Nav option) {
        $(By.linkText(option.toString())).click();
    }

    public void searchForGif(String query) {
        SEARCH.setValue(query).submit();
    }

    public void markFavorite(int gifIndex) {
        SelenideElement targetGif = GIFS.get(gifIndex);
        targetGif.$(".favorite").click();
    }
}
