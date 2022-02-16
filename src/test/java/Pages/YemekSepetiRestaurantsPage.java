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
    private By addFavouritesActive = By.cssSelector("div.social-button.favorite-button.add.active");
    private By jokerModalClose = By.id("cboxClose");


    public <T> List<String> addRestaurantsToFavourites(int restaurantCount) {
        List<String> favouriteRestaurants = new ArrayList<>();
        int restaurantsOnThePage = (driver.findElements(restaurantName)).size();
        if (restaurantsOnThePage < restaurantCount) { restaurantCount = restaurantsOnThePage; }
        for (int i = 0; i < restaurantCount; i++) {
            List<WebElement> restaurants = driver.findElements(restaurantName);
            favouriteRestaurants.add(restaurants.get(i).getText());
            clickIfDisplayed(jokerModalClose);
            click(restaurants.get(i));
            isDisplay(restaurantDetails);
            clickIfDisplayed(jokerModalClose);
            if (!isNotDisplay(addFavouritesActive)) {
                click(yemekSepetiMainPage.addToFavs, yemekSepetiMainPage.favouritesType);
            }
            isDisplay(yemekSepetiMainPage.removeFromFavs, yemekSepetiMainPage.favouritesType);
            sleep(2);
            driver.navigate().back();
            isDisplay(restaurantName);
        }
        return favouriteRestaurants;
    }

}
