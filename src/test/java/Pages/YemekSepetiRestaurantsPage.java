package Pages;

import Factory.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class YemekSepetiRestaurantsPage extends BasePage {

    WebDriver driver = DriverFactory.getDriver();
    YemekSepetiMainPage yemekSepetiMainPage = new YemekSepetiMainPage();

    private By restaurantName = By.className("restaurantName");
    private By restaurantDetails = By.className("restaurantDetailColumn");
    private By jokerModalClose = By.id("cboxClose");
    private By restaurantPopupClose = By.className("close-alternative-popup");


    public <T> List<String> addRestaurantsToFavourites(int restaurantCount) {
        List<String> favouriteRestaurants = new ArrayList<>();
        int restaurantsOnThePage = (driver.findElements(restaurantName)).size();
        if (restaurantsOnThePage < restaurantCount) { restaurantCount = restaurantsOnThePage; }
        for (int i = 0; i < restaurantCount; i++) {
            List<WebElement> restaurants = driver.findElements(restaurantName);
            favouriteRestaurants.add(restaurants.get(i).getText());

            try {
                click(restaurants.get(i));
            } catch (Exception e) {
                if(!isNotDisplay(jokerModalClose)) {
                    System.out.println("there is a popup. closing it...");
                    click(jokerModalClose);
                }
                click(restaurants.get(i));
            }
            isDisplay(restaurantDetails);

            try {
                click(yemekSepetiMainPage.addToFavs, yemekSepetiMainPage.favouritesType);
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
            isDisplay(yemekSepetiMainPage.removeFromFavs, yemekSepetiMainPage.favouritesType);
            sleep(2);
            driver.navigate().back();
            isDisplay(restaurantName);
        }
        return favouriteRestaurants;
    }

}
