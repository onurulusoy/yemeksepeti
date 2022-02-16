package Pages;

import Factory.DriverFactory;
import StepDefinitions.ApplicationHooks;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import io.cucumber.datatable.DataTable;

import static org.junit.Assert.*;

public class YemekSepetiMainPage extends BasePage {

    WebDriver driver = DriverFactory.getDriver();
    ApplicationHooks applicationHooks = new ApplicationHooks();

    String cityType = "span";
    String buttonType = "button";
    String emtpyErrorMsgType = "small";
    String tabType = "a";
    String newRestaurants = " Yeni Restoranlar";
    String superRestaurants = " Süper Restoranlar";
    String addToFavs = "Favorilere Ekle";
    public String removeFromFavs = "Favorilerden Çıkar";
    String favouritesType = "b";
    String favouritesTab = " Favorilerim";

    private By usernameEntry = By.id("UserName");
    private By passwordEntry = By.id("password");
    private By submitButton = By.id("ys-fastlogin-button");
    private By modalCloseButton = By.className("modal-header-close");
    private By userInfo = By.id("user-info");
    private By loginErrorMessage = By.cssSelector(".ys-xl");
    private By credentialErrorClass = By.className("help-block");
    private By addressLabel = By.className("address-labels");
    private By restaurantList = By.className("ys-reslist");
    private By newRestaurantItem = By.className("mainpage-tabs-new-restaurant");
    private By superRestaurantItem = By.className("mainpage-tabs-super-restaurant");
    private By mainPageFavs = By.className("mainpage-tabs-fav-restaurant");
    private By restaurantDetails = By.className("restaurantDetailColumn");
    private By restaurantPopupClose = By.className("close-alternative-popup");
    private By addFavouritesActive = By.cssSelector("div.social-button.favorite-button.add.active");
    private By showMore = By.className("show-more");
    private By noFavErrorMsg = By.className("ys-text-error");
    private By jokerModalClose = By.id("cboxClose");

    public YemekSepetiMainPage selectCity(String city){
        click(city, cityType);
        return this;
    }

    public YemekSepetiMainPage fillUsername(String email) {
        type(usernameEntry, email);
        return this;
    }

    public YemekSepetiMainPage fillPassword(String password) {
        type(passwordEntry, password);
        return this;
    }

    public YemekSepetiMainPage clickSubmit(){
        click(submitButton);
        return this;
    }

    public YemekSepetiMainPage closeModal() {
        if (!isNotDisplay(modalCloseButton)) {
            click(modalCloseButton);
        }
        return this;
    }

    public YemekSepetiMainPage observeUserInfo(){
        try {
            isDisplay(userInfo);
        } catch (Exception e) {
            System.out.println("there may be a popup");
            if (!isNotDisplay(modalCloseButton)) {
                click(modalCloseButton);
            }
            isDisplay(userInfo);
        }
        return this;
    }

    public YemekSepetiMainPage observeErrorMessage() {
        isDisplay(loginErrorMessage);
        return this;
    }

    public YemekSepetiMainPage clickErrorPopupConfirmationButton(String buttonText) {
        click(buttonText, buttonType);
        return this;
    }

    public YemekSepetiMainPage emptyEntryMessage(String errorMessage) {
        isDisplay(errorMessage, emtpyErrorMsgType);
        return this;
    }

    public YemekSepetiMainPage clickLoginButton() {
        click(submitButton);
        return this;
    }

    public YemekSepetiMainPage observeCredentialErrormessages(DataTable requiredErrorMsgsData) {
        List<List<String>> rows = requiredErrorMsgsData.asLists(String.class);
        List<String> requiredErrorMsgs = rows.get(0);
        assertEquals(getElementsText(credentialErrorClass), requiredErrorMsgs);
        return this;
    }

    public YemekSepetiMainPage observeNoErrorMessages() {
        assertTrue(isNotDisplay(credentialErrorClass));
        return this;
    }

    public <T> List<String> getFavouriteRestaurantsList(){
        applicationHooks.getProperty();
        DriverFactory.getDriver().navigate().to(applicationHooks.properties.getProperty("mainPageUrl"));
        List<String> favouriteRestaurantsList = new ArrayList<>();
        if(!isNotDisplay(showMore)) {
            click(showMore);
            sleep(1);
        }
        List<WebElement> mainPageFavsList = driver.findElements(mainPageFavs);
        for (WebElement element : mainPageFavsList) {
            favouriteRestaurantsList.add(element.getText());
        }
        return favouriteRestaurantsList;
    }

    public YemekSepetiMainPage openTab(String tabName) {
        try {
            click(tabName, tabType);
        } catch (Exception e) {
            System.out.println("there may be a popup");
            if (!isNotDisplay(modalCloseButton)) {
                click(modalCloseButton);
            }
            click(tabName, tabType);
        }
        return this;
    }

    public YemekSepetiMainPage observerTabErrorMessage() {
        isDisplay(noFavErrorMsg);
        return this;
    }

    public YemekSepetiMainPage listRestaurantsForAddress() {
        click(addressLabel);
        isDisplay(restaurantList);
        return this;
    }

    public <T> List<String> addRestaurantsToFavouritesFromTab(int restaurantCount, String tabName) {
        List<String> favouriteRestaurants = new ArrayList<>();
        By restaurantItem = null;
        if (tabName.equals(newRestaurants)){
            restaurantItem = newRestaurantItem;
        } else if (tabName.equals(superRestaurants)) {
            restaurantItem = superRestaurantItem;
        }
        int restaurantsOnThePage = (driver.findElements(restaurantItem)).size();
        if (restaurantsOnThePage < restaurantCount) { restaurantCount = restaurantsOnThePage; }
        for (int i = 0; i < restaurantCount; i++) {
            List<WebElement> restaurants = driver.findElements(restaurantItem);
            favouriteRestaurants.add(restaurants.get(i).getText());
            click(restaurants.get(i));
            isDisplay(restaurantDetails);
            try {
                click(addToFavs, favouritesType);
            } catch (Exception e) {
                System.out.println("the restaurant already is in favs or there is a popup");
                if (!isNotDisplay(restaurantPopupClose)) {
                    click(restaurantPopupClose);
                } else if (!isNotDisplay(jokerModalClose)) {
                    click(jokerModalClose);
                } else {
                    System.out.println("the restaurant is already in favs");
                }

            }
            isDisplay(removeFromFavs, favouritesType);
            sleep(2);
            driver.navigate().back();
            sleep(1);
            openTab(tabName);
            isDisplay(restaurantItem);
        }
        return favouriteRestaurants;
    }

    public YemekSepetiMainPage removeAllFromFavourites() {
        if(!isNotDisplay(showMore)) {
            click(showMore);
            sleep(1);
        }
        int favouriteRestaurantsSize = driver.findElements(mainPageFavs).size();
        for(int i = 0; i < favouriteRestaurantsSize; i++){
            List<WebElement> mainPageFavsList = driver.findElements(mainPageFavs);
            click(mainPageFavsList.get(0));
            isDisplay(restaurantDetails);
            try {
                click(removeFromFavs, favouritesType);
            } catch (Exception e) {
                System.out.println("there is a popup");
                if (!isNotDisplay(restaurantPopupClose)) {
                    click(restaurantPopupClose);
                } else if (!isNotDisplay(jokerModalClose)) {
                    click(jokerModalClose);
                }
            }
            isDisplay(addToFavs, favouritesType);
            sleep(2);
            driver.navigate().back();
            sleep(1);
            openTab(favouritesTab);
        }
        return this;
    }

}
