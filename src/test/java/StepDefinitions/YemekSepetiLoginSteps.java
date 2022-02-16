package StepDefinitions;

import Factory.DriverFactory;
import Pages.YemekSepetiMainPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.datatable.DataTable;

public class YemekSepetiLoginSteps {

    ApplicationHooks applicationHooks = new ApplicationHooks();
    YemekSepetiMainPage yemekSepetiMainPage = new YemekSepetiMainPage();

    private String confirm = "TAMAM";

    @Given("I open the yemeksepeti login page")
    public void iOpenTheYemekSepetiLoginPage() {
        applicationHooks.getProperty();
        String Url = applicationHooks.properties.getProperty("mainPageUrl");
        System.out.println("main page url is: " + Url);
        DriverFactory.getDriver().get(Url);
    }

    @Then("I select city {string}")
    public void iSelectCity(String city) {
        yemekSepetiMainPage.selectCity(city);
    }


    @When("I try to login with credentials {string} {string}")
    public void iTryToLoginWithCredentials(String username, String password) {
        yemekSepetiMainPage.fillUsername(username);
        yemekSepetiMainPage.fillPassword(password);
        yemekSepetiMainPage.clickSubmit();
        yemekSepetiMainPage.closeModal();
    }

    @Then("I should see user info")
    public void iShouldSeeUserInfo() { yemekSepetiMainPage.observeUserInfo(); }

    @Then("I should see fail message")
    public void iShouldSeeFailMessage() { yemekSepetiMainPage.observeErrorMessage(); }

    @Then("I close error popup")
    public void iCloseErrorPopup() { yemekSepetiMainPage.clickErrorPopupConfirmationButton(confirm); }

    @Then("I should see empty entry message {string}")
    public void iShouldSeeEmptyEntryMessage(String errorMessage) { yemekSepetiMainPage.emptyEntryMessage(errorMessage); }

    @When("I click login")
    public void iClickLogin() { yemekSepetiMainPage.clickLoginButton(); }

    @Then("I should see credentials error messages")
    public void iShouldSeeCredentialsErrorMessage(DataTable data) { yemekSepetiMainPage.observeCredentialErrormessages(data); }

    @When("I fill user credentials {string}")
    public void iFillUserCredentials(String username) { yemekSepetiMainPage.fillUsername(username); }

    @When("I fill password credentials {string}")
    public void iFillPasswordCredentials(String password) { yemekSepetiMainPage.fillPassword(password); }

    @Then("I should see that credential error messages are gone")
    public void iShouldSeeThatCredentialErrorMessagesAreGone() { yemekSepetiMainPage.observeNoErrorMessages(); }


}
