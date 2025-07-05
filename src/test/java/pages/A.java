package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import utilities.ReusableMethods;

public class A extends ReusableMethods {
    public Locator usernameInput;
    public Locator passwordInput;
    public Locator loginClick;

    public A(Page page) {
        super(page);
        usernameInput =page.locator("#username");
        passwordInput =page.locator("#password");
        loginClick =page.locator("#submitBtn");
        // locator lar buraya
    }

    public void login (String username ,String password){
        usernameInput.fill(username);
        passwordInput.fill(password);
        loginClick.click();
    }
}
