package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class US05_HomePage {

    private final Page page;

    public US05_HomePage(Page page) {
        this.page = page;
    }

    public Locator shopNowLinks() {
        return page.locator("a.btn-link.alt-font.btn-medium");
    }

    public void closeCookieBannerIfExists() {
        Locator cookieBanner = page.locator("#cmplz-cookiebanner-container button.cmplz-accept");
        if (cookieBanner.isVisible()) {
            cookieBanner.click();
        }
    }
}
