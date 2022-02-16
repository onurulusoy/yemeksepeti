#yemeksepetitest@outlook.com => _DeliveryHero1 => Yemek Sepeti 01.01.90
@Login
Feature: YemekSepeti Login
  Background:
    Given I open the yemeksepeti login page
    Then I select city "İstanbul"

  Scenario Outline: Login with correct credentials
  Description: User should see user info after a successful login

    When I try to login with credentials "<correctUsername>" "<correctPassword>"
    Then I should see user info

    Examples:
      |correctUsername              |correctPassword    |
      |yemeksepetitest@outlook.com  |_DeliveryHero1     |
    #|username??                   |_DeliveryHero1     | Normally user can login with username(not mail). But username cannot be found in anywhere.


  Scenario Outline: Login with wrong credentials
  Description: User should see an error message after login with wrong credentials

    When I try to login with credentials "<wrongUsername>" "<wrongPassword>"
    Then I should see fail message
    Then I close error popup

    Examples:
      |wrongUsername                |wrongPassword      |
      |yemeksepetitest@outlook.com  |wrongPassword      |
      |wrongusername                |_DeliveryHero1     |

  Scenario Outline: Login with empty credentials
  Description: User should see an error message after login with empty credentials

    When I try to login with credentials "<username>" "<password>"
    Then I should see empty entry message "<emptyCredentialErrorMessage>"

    Examples:
      |username                     |password           |emptyCredentialErrorMessage                       |
      |                             |dummyPassword      |Lütfen kullanıcı adınızı/e-postanızı giriniz.     |
      |dummyUsername                |                   |Lütfen şifrenizi giriniz.                         |

  Scenario: Fill credentials after trying to login with empty credentials
  Description: User should see that error messages are gone after filling empty credentials

    When I click login
    Then I should see credentials error messages
    |Lütfen kullanıcı adınızı/e-postanızı giriniz.|Lütfen şifrenizi giriniz.|
    When I fill user credentials "dummyUsername"
    Then I should see credentials error messages
    |Lütfen şifrenizi giriniz.|
    When I fill password credentials "dummyPassword"
    Then I should see that credential error messages are gone

  Scenario: Fail login scenario
  Description: This scenario is for task requirements

    When I try to login with credentials "dummyUserName" "dummyPassword"
    Then I should see user info