package ui.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ui.pages.BasePage;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

/**
 * @author BZagorski on 6/3/2017.
 */
public class BasePageTest extends BaseTest {

    private BasePage basePage = new BasePage();

    @BeforeMethod
    public void goToHomePage() {
        open("/");
    }

    @Test
    public void gifsDisplay() throws Exception {
        basePage.GIFS
                .shouldHaveSize(6);
    }

    @Test
    public void navShouldBeVisible() throws Exception {
        basePage.NAV_BAR
                .shouldBe(visible);
    }

    @Test
    public void searchShouldBeVisible() throws Exception {
        basePage.SEARCH
                .shouldBe(visible);
    }

    @Test
    public void clickingThumbnailShouldTakeYouToGifDetail() throws Exception {
        basePage.GIFS.first().click();
        $(".gif-detail.container")
                .shouldBe(visible);
    }

    @Test
    public void markGifAsFavoriteUpdatesClass() {
        int gifIndex = 2;
        try {
            basePage.markFavorite(gifIndex);
            basePage.GIFS.get(gifIndex).$("button")
                    .shouldHave(cssClass("unmark"));
            basePage.markFavorite(gifIndex);
            basePage.GIFS.get(gifIndex).$("button")
                    .shouldHave(cssClass("mark"));
        } finally {
            if (basePage.GIFS.get(gifIndex).$("button").has(cssClass("unmark"))) {
                basePage.markFavorite(gifIndex);
            }
        }
    }

    @Test
    public void SearchingByGifDescriptionShouldReturnGifInResults() throws Exception {
        String gifDescription = "heavy";
        basePage.searchForGif(gifDescription);
        basePage.GIFS
                .shouldHaveSize(1)
                .first()
                .click();
        $("h4").shouldHave(text("Heavy Breathing"));
    }
}
