package Pages;

import Factory.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class BasePage {

    WebDriver driver = DriverFactory.getDriver();

    public void sleep(int seconds) {
        System.out.println("-----------------user is going to wait for : " + seconds + " seconds ----------------");
        try {
            Thread.sleep(1000*seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("-----------------user waited for : " + seconds + " seconds ----------------");
    }


    public <T> void click(T p)  {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(ElementNotVisibleException.class)
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(TimeoutException.class)
                .ignoring(WebDriverException.class)
                .ignoring(ElementClickInterceptedException.class);
        wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                WebElement el = null;
                if (p instanceof By) {
                    el = driver.findElement((By) p);
                } else if (p instanceof WebElement) {
                    el = ((WebElement) p);
                }else if (p instanceof String){
                    el = (WebElement) ((JavascriptExecutor)driver).executeScript("return "+(p));
                    if (el == null)
                        return null;
                }

                new WebDriverWait(driver, 6)
                        .until(ExpectedConditions.elementToBeClickable(el));
                new WebDriverWait(driver, 6)
                        .until(ExpectedConditions.visibilityOf(el));
                assert el != null;
                el.click();
                return el; }});
    }

    public <P, T> void click(P element, T type)  {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(15))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(ElementNotVisibleException.class)
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(TimeoutException.class)
                .ignoring(WebDriverException.class)
                .ignoring(ElementClickInterceptedException.class);
        wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                WebElement el = null;
                el = driver.findElement(By.xpath("//" + type + "[text()='" + element + "']"));
                if (el == null)
                    return null;

                new WebDriverWait(driver, 6)
                        .until(ExpectedConditions.elementToBeClickable(el));
                new WebDriverWait(driver, 6)
                        .until(ExpectedConditions.visibilityOf(el));
                assert el != null;
                el.click();
                return el; }});
    }

    public <P, T> void type(P element, T entry) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(ElementNotVisibleException.class)
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(ElementClickInterceptedException.class);

        wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                WebElement el = null;
                if (element instanceof By) {
                    el = driver.findElement((By) element);
                } else {
                    el = ((WebElement) element);
                }

                if (entry instanceof String) {
                    el.clear();
                    el.sendKeys(((String) entry));
                } else if (entry instanceof Keys) {
                    el.sendKeys(((Keys) entry));
                }
                return el;
            }
        });
    }


    public <T> List<String> getElementsText(T p){
        String text = null;
        List<String> texts = new ArrayList<>();
        if (p instanceof By) {
            List<WebElement> elements = driver.findElements((By) p);
            for (WebElement elem : elements) {
                text = elem.getText();
                texts.add(text);
            }
        } else {
            text =  ((WebElement) p).getText();
        }
        return texts;
    }


    public <T> void isDisplay(T p)  {
        if(p instanceof By){
            boolean b =  driver.findElement((By) p).isDisplayed();
        }else{
            boolean b = ((WebElement) p).isDisplayed();
        }
    }

    public <T> boolean isNotDisplay(T p) {
        return driver.findElements((By) p).size() <= 0;
    }

    public <T, P> void isDisplay(T element, P type)  {
        driver.findElement(By.xpath("//" + type + "[text()='" + element + "']"));
    }


}
