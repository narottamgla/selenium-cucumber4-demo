/**
 * 
 */
package com.ui.actions;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;


import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import com.ui.pages.HomePage;

import net.thucydides.core.annotations.Step;

/**
 * 
 * @version $Id$
 */
public class HomePageActions
{

   HomePage homePage;

   /**
    * 
    */
   @Step
   public void openJungleSocksHomePageurl()
   {
      homePage.open();

   }

   /**
    * 
    */
   public void verifyHomePageJungleSocksTitle(String title)
   {
      assertThat("Verify create asset page:", homePage.getHomePageTitle(), equalTo(title));

   }

   /**
    * 
    */
   public void verifyJungleSocksHomePageHeader(String header)
   {
      assertThat("Verify create asset page:", homePage.getHomePageHeader(), equalTo(header));

   }

   /**
    * 
    */
   public void verifyJungleSocksHomeProductDetails(List productList)
   {
      assertThat("Verify create asset page:", homePage.getPoductList(), equalTo(productList));

   }

   /**
    * 
    */
   public void verifyProductPriceAndQuantityByName(String productName, String price, String quantity)
   {
      int index = homePage.getProductRowIndexByName(productName);
      System.out.println("ProductName:" + productName + ", " + index);

      assertThat("Verify product price:", homePage.getProductPriceByIndex(index), equalTo(price));
      assertThat("Verify product quantity:", homePage.getProductInStockQuantityByIndex(index), equalTo(quantity));

   }

   /**
    * @param productName
    * @param quantity
    */
   public void enterProdcutQuantityByName(String productName, String quantity)
   {
      int index = homePage.getProductRowIndexByName(productName);
      System.out.println("ProductName:" + productName + ", " + index);
      homePage.enterProdcutPurchaseQuantityByIndex(index, quantity);

   }

   /**
    * @param state
    */
   public void selectStateByName(String state)
   {
      homePage.selectStateByStateName(state);

   }

   /**
    * 
    */
   public void clickCheckOutButton()
   {
      homePage.clickCheckOutBtn();

   }

   /**
    * 
    */
   public void verifyConfirmOrderPageNavigation()
   {
      assertThat("Verify product quantity:", homePage.getHomePageHeader(), equalTo("Please Confirm Your Order"));

   }

   /**
    * @param productPrice
    * @param productQuantity
    */

   int subTotalSum;

   public void shouldSeeProductSubTotalAsPerQuantity(List<Map<String, String>> productPrice, HashMap<String, Integer> productQuantity)
   {
      assertThat(
         "Verify product quantity:",
         Float.parseFloat(homePage.getProductSubPrice().substring(1)),
         equalTo(getProductSubPrice(productPrice, productQuantity)));

   }

   /**
    * @param productPrice
    * @param productQuantity
    */
   private float getProductSubPrice(List<Map<String, String>> productPrice, HashMap<String, Integer> productQuantity)
   {
      int i = 0;
      subTotalSum = 0;

      for (Entry<String, Integer> entry : productQuantity.entrySet())
      {
         System.out.println("product:" + productPrice.get(i).get("productName"));
         System.out.println("product price:" + productPrice.get(i).get("price"));
         System.out.println("product quantity:" + entry.getValue());

         subTotalSum = subTotalSum + Integer.parseInt(productPrice.get(i).get("price")) * entry.getValue();
         System.out.println("subTotal:" + subTotalSum);

         i++;
      }

      System.out.println("Product sub price::" + subTotalSum);
      return (float) (subTotalSum);
   }

   /**
    * @param stateTx
    */
   float tax = 0;

   public void verifyProductTotalTax(String stateTx)
   {
      tax = (float) subTotalSum * Float.parseFloat(stateTx);
      System.out.println("Product tax:" + tax);
      
       DecimalFormat df = new DecimalFormat("0.00");

      assertThat("Verify product tax:", Float.parseFloat(homePage.getProductTotalTax().substring(1)), greaterThanOrEqualTo(tax));

   }

   /**
    * @param stateTx
    */
   public void verifyProductTotalPrice()
   {
      assertThat("Verify product Total price:", Float.parseFloat(homePage.getProductTotalPrice().substring(1)), equalTo(tax + subTotalSum));

   }

}
