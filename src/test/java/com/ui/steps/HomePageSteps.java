/**
 * 
 */
package com.ui.steps;

import java.util.List;

import com.ui.actions.HomePageActions;
import com.ui.utils.CommonUtils;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import net.thucydides.core.annotations.Steps;

/**
 * 
 * @version $Id$
 */
public class HomePageSteps
{
   @Steps
   HomePageActions homePageActions;
   

   @Given("I open the jungle socks home page url")
   public void i_open_the_jungle_socks_home_page_url()
   {
      homePageActions.openJungleSocksHomePageurl();
   }

   @Then("I should see jungle socks home page title as {string}")
   public void i_should_see_jungle_socks_home_page(String title)
   {
      homePageActions.verifyHomePageJungleSocksTitle(title);
   }

   @Then("I should see jungle socks home page header as {string}")
   public void i_should_see_jungle_socks_home_page_header_as(String header)
   {
      homePageActions.verifyJungleSocksHomePageHeader(header);
   }

   @Then("I should see different products on jungle socks home page")
   public void i_should_see_different_products_on_jungle_socks_home_page(List productList)
   {
      homePageActions.verifyJungleSocksHomeProductDetails(productList);
   }

   @Then("I should see product as {string} with price as {string} and instock quantity as {string}")
   public void i_should_see_product_as_with_price_as_and_instock_quantity_as(String productName, String price, String quantity)
   {
      homePageActions.verifyProductPriceAndQuantityByName(productName, price, quantity);
   }
}
