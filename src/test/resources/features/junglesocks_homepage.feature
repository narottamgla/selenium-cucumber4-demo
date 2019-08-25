Feature: Verify Jungle socks home page

  Scenario: Verify jungle socks home page navigation
    Given I open the jungle socks home page url
    Then I should see jungle socks home page title as "JungleSocks"
    Then I should see jungle socks home page header as "Welcome To Jungle Socks!"

  Scenario: Verify diffrent product available on JungleSocks home page
    Given I open the jungle socks home page url
    Then I should see jungle socks home page title as "JungleSocks"
    Then I should see different products on jungle socks home page
      | zebra    |
      | lion     |
      | elephant |
      | giraffe  |

  Scenario Outline: Verify diffrent product product catalog price and quantity
    Given I open the jungle socks home page url
    Then I should see jungle socks home page title as "JungleSocks"
    Then I should see product as "<productName>" with price as "<price>" and instock quantity as "<instock>"

    Examples: 
      | productName | price | instock |
      | zebra       |    13 |      23 |
      | lion        |    20 |      12 |
      | elephant    |    35 |       3 |
      | giraffe     |    17 |      15 |

  Scenario Outline: Verify diffrent product catalog price and quantity
    Given I open the jungle socks home page url
    Then I should see jungle socks home page title as "JungleSocks"
    When I enter "zebra" product quantity as "<zebraQuantity>"
    When I enter "lion" product quantity as "<lionQuantity>"
    When I enter "elephant" product quantity as "<elephantQuantity>"
    When I enter "giraffe" product quantity as "<giraffeQuantity>"
    When I select ship to state as "<state>" and with state tax as "<tax>"
    When I clicks on checkout button of jungle socks
    Then I should see Confirm order page with header as "Please Confirm Your Order"
    Then I should see product subtotal as per quantity
      | productName | price |
      | zebra       |    13 |
      | lion        |    20 |
      | elephant    |    35 |
      | giraffe     |    17 |
    Then I should see product tax as per state selected
    Then I should see total purchase as sum of subtotal and taxes

    Examples: 
      | state      | tax | zebraQuantity | lionQuantity | elephantQuantity | giraffeQuantity |
      | Alabama    | .05 |             1 |            2 |                2 |               2 |
      | Alaska     | .05 |             1 |            2 |                2 |               2 |
      | Arizona    | .05 |             1 |            1 |                1 |               1 |
      | Arkansas   | .05 |             1 |            1 |                1 |               1 |
      | California | .08 |             1 |            1 |                1 |               1 |
      | Nebraska   | .05 |             1 |            1 |                1 |               1 |
      | New York   | .06 |             2 |            2 |                2 |               2 |
      | Minnesota  |  .0 |             1 |            1 |                1 |               1 |
