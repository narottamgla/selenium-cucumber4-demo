/**
 * 
 */
package com.ui.steps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ui.actions.HomePageActions;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.model.DataTableRow;

/**
 * 
 * @version $Id$
 */
public class ProductPurchaseSteps
{
   @Steps
   HomePageActions homePageActions;

   String state;
   String stateTx;
   HashMap<String, Integer> productQuantity = new HashMap<String, Integer>();

   @When("I enter {string} product quantity as {string}")
   public void i_enter_product_quantity_as(String productName, String quantity)
   {
      productQuantity.put(productName, Integer.parseInt(quantity));
      homePageActions.enterProdcutQuantityByName(productName, quantity);
   }

   @When("I select ship to state as {string} and with state tax as {string}")
   public void i_seelct_ship_to_state_as_and_with_state_tax_as(String state, String tax)
   {
      this.state = state;
      this.stateTx = tax;
      homePageActions.selectStateByName(state);
   }

   @When("I clicks on checkout button of jungle socks")
   public void i_clciks_on_checkout_button_of_jungle_socks()
   {
      homePageActions.clickCheckOutButton();
   }

   @Then("I should see Confirm order page with header as {string}")
   public void i_should_see_Confirm_order_page_with_header_as(String string)
   {
      homePageActions.verifyConfirmOrderPageNavigation();
   }

   @Then("I should see product subtotal as per quantity")
   public void i_should_see_product_subtotal_as_per_quantity(DataTable dataTable)
   {
      List<Map<String, String>> productPrice = dataTable.asMaps(String.class, String.class);
      homePageActions.shouldSeeProductSubTotalAsPerQuantity(productPrice,productQuantity);
   }

   @Then("I should see product tax as per state selected")
   public void i_should_see_product_tax_as_per_state_selected()
   {
      homePageActions.verifyProductTotalTax(stateTx);
   }

   @Then("I should see total purchase as sum of subtotal and taxes")
   public void i_should_see_total_purchase_as_sum_of_subtotal_and_taxes()
   {
      homePageActions.verifyProductTotalPrice();
   }
}
