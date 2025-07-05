package utilities;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.testng.Assert;

import java.util.List;
import java.util.Random;


public class ReusableMethods {
    private final Page page;
    private final Random random = new Random();

    public ReusableMethods(Page page) {
        this.page = page;
    }

    public void myClick(Locator element) {
        element.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED));
        element.click();
    }

    public void clickAndEsc(Locator clickElement, Locator assertElement) {
        clickElement.waitFor();
        clickElement.click();
        assertElement.waitFor();
        Assert.assertTrue(assertElement.isVisible());
        page.keyboard().press("Escape");
    }

    public void mySendKeys(Locator element, String text) {
        element.waitFor();
        element.fill("");
        element.type(text);
    }

    public void hoverOver(Locator element) {
        element.hover();
    }

    public void jsClick(String selector) {
        page.evaluate("document.querySelector('" + selector + "').click();");
    }

    public void scrollToElement(String selector) {
        page.evaluate("document.querySelector('" + selector + "').scrollIntoView();");
    }

    public void verifyContainsText(Locator element, String expectedText) {
        element.waitFor();
        String actualText = element.innerText().toLowerCase();
        Assert.assertTrue(actualText.contains(expectedText.toLowerCase()));
    }

    public void verifyEqualsText(Locator element, String expectedText) {
        element.waitFor();
        String actualText = element.innerText().toLowerCase();
        Assert.assertEquals(actualText, expectedText.toLowerCase());
    }

    public String getCssProperty(Locator element, String property) {
        return element.evaluate("el => getComputedStyle(el).getPropertyValue('" + property + "')").toString();
    }

    public boolean isElementVisible(Locator element) {
        try {
            return element.isVisible();
        } catch (Exception e) {
            return false;
        }
    }

    public int randomGenerator(int range) {
        return random.nextInt(range);
    }

    public void selectByIndex(Locator selectLocator, int index) {
        selectLocator.selectOption(new String[]{selectLocator.nth(index).inputValue()});
    }

    public void Wait(int sn) {
        try {
            Thread.sleep(sn * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // Liste içinden rastgele bir elemana tıklama
    public void clickRandomFromList(List<Locator> elements) {
        if (elements.size() > 0) {
            int index = random.nextInt(elements.size());
            elements.get(index).click();
        }
    }

}
