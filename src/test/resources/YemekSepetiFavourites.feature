#yemeksepetitest@outlook.com => _DeliveryHero1 => Yemek Sepeti 01.01.90
@Favourites
Feature: YemekSepeti Favourites
  Background:
    Given I open the yemeksepeti login page
    Given I select city "İstanbul"
    Given I try to login with credentials "yemeksepetitest@outlook.com" "_DeliveryHero1"
    Given I should see user info

  Scenario: Check Empty Favourites
  Description: User should see the changes under favourites tab after adding a restaurant to favourites or removing them from favourites

    When I open " Favorilerim" tab
    Then I should see no favourite error
    When I open restaurants from adresses tab
    Then I add first 2 restaurants to favourites and check favourites tab
    When I open " Yeni Restoranlar" tab
    Then I add first 2 restaurants from " Yeni Restoranlar" tab to favourites and check favourites tab
    When I open " Süper Restoranlar" tab
    Then I add first 2 restaurants from " Süper Restoranlar" tab to favourites and check favourites tab
    #We could use "Examples:" for previous 4 steps. but  we need to execute "Background" steps twice as well
    When I open " Favorilerim" tab
    Then I remove all restaurants from favourite
    Then I should see no favourite error
    #These steps wrapped up  in one scenario because of parallel execution. If the steps were in different scenarios, there would be a dependency between them.
    #We can make this test more efficient with using different users