package lab6.hooks;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import lab6.driver.DriverManager;

public class Hooks {

    @Before
    public void beforeScenario() {
        DriverManager.getDriver();
    }

    @After
    public void afterScenario() {
        DriverManager.quitDriver();
    }
}
