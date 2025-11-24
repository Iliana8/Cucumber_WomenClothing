Feature: SIS-T5 - Product search returns error 404 / no results

  # Bonus scenario using Cucumber Data Tables
  @run @T5 @datatable
  Scenario: Search with valid and invalid terms
    Given I am on the main page
    When I search using the following terms
      | term  | expectation |
      | dress | results     |
      | bag   | results     |
      | @@@@  | noResults   |

  @run @T5
  Scenario: Submitting empty search does not navigate away
    Given I am on the main page
    And I remember the current page URL
    When I submit an empty search
    Then the current page URL should be the remembered one
