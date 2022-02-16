package StepDefinitions;

import Factory.DriverFactory;
import Utils.ConfigReader;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import java.util.Properties;

public class ApplicationHooks {
    private DriverFactory driverFactory;
    private WebDriver driver;
    private ConfigReader configReader;
    public Properties properties;

    @Before(order = 0)
    public void getProperty() {
        configReader = new ConfigReader();
        properties = configReader.initialize_properties();
    }

    @Before(order = 1)
    public void launchBrowser() {
        String browserName = System.getProperty("browserName");
        driverFactory = new DriverFactory();
        driver = driverFactory.init_driver(browserName);
    }


    @After
    public void embedScreenshot(Scenario scenario) throws Exception {
        if (scenario.isFailed()) {
            try {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                String testName = scenario.getName();
                scenario.attach(screenshot,"image/png","failedScreenShot");
                System.out.println(testName);
            } catch (WebDriverException wde) {
                System.err.println(wde.getMessage());
            } catch (ClassCastException cce) {
                cce.printStackTrace();}
        }
        driver.quit();
    }

}
