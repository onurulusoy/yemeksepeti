package StepDefinitions;

import Pages.YemekSepetiMainPage;
import Pages.YemekSepetiRestaurantsPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class YemekSepetiFavouritesSteps {

    YemekSepetiMainPage yemekSepetiMainPage = new YemekSepetiMainPage();
    YemekSepetiRestaurantsPage yemekSepetiRestaurantsPage = new YemekSepetiRestaurantsPage();

    @When("I open {string} tab")
    public void iOpenTab(String tabName) { yemekSepetiMainPage.openTab(tabName); }

    @Then("I should see no favourite error")
    public void iShouldSeeNoFavouriteError() { yemekSepetiMainPage.observerTabErrorMessage(); }

    @When("I open restaurants from adresses tab")
    public void iOpenRestaurantsFromAdressesTab() { yemekSepetiMainPage.listRestaurantsForAddress(); }

    @Then("I add first {int} restaurants to favourites and check favourites tab")
    public void iAddRestaurantsToFavouritesAndCheck(int restaurantCount) {
        List<String> favouritedRestaurants = yemekSepetiRestaurantsPage.addRestaurantsToFavourites(restaurantCount);
        List<String> myFavourites = yemekSepetiMainPage.getFavouriteRestaurantsList();
        Collections.sort(favouritedRestaurants);
        System.out.println("Restaurant(s) added to Favourites: " + favouritedRestaurants);
        Collections.sort(myFavourites);
        System.out.printf("Restaurant(s) observed under Favourites Tab: " + myFavourites);
        assertEquals(favouritedRestaurants, myFavourites);
    }


    @Then("I add first {int} restaurants from {string} tab to favourites and check favourites tab")
    public void iAddFirstRestaurantsFromTabToFavouritesAndCheckFavouritesTab(int count, String tabName) {
        List<String> newFavs = yemekSepetiMainPage.addRestaurantsToFavouritesFromTab(count, tabName);
        List<String> myFavourites = yemekSepetiMainPage.getFavouriteRestaurantsList();
        for (String favourite : newFavs) {
            System.out.println("Restaurant added to Favourites: " + favourite);
            System.out.println("Restaurant(s) observed under Favourites Tab: " + myFavourites);
            assertTrue(myFavourites.contains(favourite));
        }
    }

    @Then("I remove all restaurants from favourite")
    public void iRemoveAllRestaurantsFromFavourite() { yemekSepetiMainPage.removeAllFromFavourites(); }
}
