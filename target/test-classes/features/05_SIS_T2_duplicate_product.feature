Feature: SIS-T2 - Duplicate products

  @run @T2
  Scenario: Check that product Black Basic Shorts is not duplicated
    Given I am on the Women Clothing page
    Then product "Black Basic Shorts" should appear only once in the product list
