package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utilities.PW;

public class Hooks {

    @Before
    public void beforeScenario() {
        PW.getPage();
    }
    @After
    public void afterScenario(Scenario scenario) {
        PW.tearDown(scenario);
    }
}
