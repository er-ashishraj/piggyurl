@Piggyurl
Feature: User would like to get piggyurls
  Background:
    Given the following piggyurls exists in the library
      | code | description                 |
      | A    | Twinkle twinkle little star |
      | B    | Johnny Johnny Yes Papa      |

  Scenario: User should be able to get all piggyurls
    When user requests for all piggyurls
    Then the user gets the following piggyurls
      | code | description                 |
      | A    | Twinkle twinkle little star |
      | B    | Johnny Johnny Yes Papa      |

  Scenario: User should be able to get piggyurls by code
    When user requests for piggyurls by code "A"
    Then the user gets the following piggyurls
      | code | description                 |
      | A    | Twinkle twinkle little star |

  Scenario: User should get an appropriate NOT FOUND message while trying get piggyurls by an invalid code
    When user requests for piggyurls by id "10000" that does not exists
    Then the user gets an exception "Piggyurl with code 10000 does not exist"