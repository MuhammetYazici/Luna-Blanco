package stepDefinitions;

import static org.testng.Assert.*;

import com.microsoft.playwright.Page;
import io.cucumber.java.en.*;
import pages.US05_HomePage;
import utilities.ConfigReader;
import utilities.PW;

public class US05_HomePages {

    Page page = PW.getPage();
    US05_HomePage US05HomePage = new US05_HomePage(page);

    @Given("The user navigates to the homepage")
    public void the_user_navigates_to_the_homepage() {
        String url = ConfigReader.getProperty("baseUrl");
        page.navigate(url);
    }

    @And("Closes the cookie banner if it appears")
    public void closes_the_cookie_banner_if_it_appears() {
        US05HomePage.closeCookieBannerIfExists();
    }

    @Then("The page should display 4 SHOP NOW links")
    public void the_page_should_display_4_shop_now_links() {
        int linkCount = US05HomePage.shopNowLinks().count();
        assertEquals(linkCount, 4, "Expected 4 SHOP NOW links, but found: " + linkCount);
    }

    @When("The user clicks each SHOP NOW link one by one and verifies the URL")
    public void the_user_clicks_each_shop_now_link_and_verifies_url() {
        int linkCount = US05HomePage.shopNowLinks().count();
        assertTrue(linkCount >= 1, "At least 1 SHOP NOW link should be visible");

        for (int i = 0; i < linkCount; i++) {
            US05HomePage.shopNowLinks().nth(i).click();
            page.waitForLoadState();

            String currentUrl = page.url();
            System.out.println("Navigated to: " + currentUrl);

                     assertTrue(currentUrl.startsWith("https://lunablanco.com/store/"),
                    "Unexpected URL format: " + currentUrl);

            page.goBack();
            page.waitForLoadState();
            US05HomePage.closeCookieBannerIfExists();
        }
    }
}
