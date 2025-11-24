Feature: SIS-T4 - Price consistency between listing and cart

  As a customer
  I want the price of a product to be the same on the list, in the cart and after refresh
  So that I am charged correctly

  @run @T4
  Scenario: Verify price consistency for Aero Canvas Loafers
    Given I am on the Women Clothing page
    And I remember the price for product "Aero Canvas Loafers"
    When I add the product "Aero Canvas Loafers" to the cart
    And I open the Cart page
    Then the price for product "Aero Canvas Loafers" in the cart should be the same as the remembered price
    When I refresh the current page
    Then the price for product "Aero Canvas Loafers" in the cart should be the same as the remembered price
