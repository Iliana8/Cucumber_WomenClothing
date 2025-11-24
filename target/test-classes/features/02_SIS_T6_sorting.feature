Feature: SIS-T6 - Sorting by price and name

  @run @T6
  Scenario: Verify sorting options on Women Clothing page
    Given I am on the Women Clothing page
    And I capture the original product order

    When I sort products by option "Price(Low - High)"
    Then products should be sorted by price ascending

    When I sort products by option "Price(High - Low)"
    Then products should be sorted by price descending

    When I sort products by option "Name(A - Z)"
    Then products should be sorted by name ascending

    When I reset the sort to default
    Then the product list should be back to the original order
