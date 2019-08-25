/**
 * 
 */
package com.ui.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.ui.utils.CommonUtils;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;

/**
 * 
 * @version $Id$
 */
public class HomePage extends PageObject
{

   @FindBy(css = "body > h1")
   private WebElement homePageHeader;

   @FindBy(xpath = "//*[contains(@class,'line_item')]/td[1]")
   private List<WebElement> productListWebElement;
   
   @FindBy(css = "[name=\"state\"]")
   private WebElement stateDropDown;
   
   @FindBy(css = "[name=\"state\"] option")
   private List<WebElement> stateList;
   
   @FindBy(name = "commit")
   private WebElement checkoutButton;
   
   @FindBy(css ="#subtotal")
   private WebElement productPriceSubTotal;
   
   @FindBy(css ="#taxes")
   private WebElement productTaxesTotal;
   
   @FindBy(css ="#total")
   private WebElement productTotalPrice;


   public String getHomePageTitle()
   {
      return getDriver().getTitle();
   }

   public String getHomePageHeader()
   {
      CommonUtils.waitForElementToBeVisible(getDriver(), homePageHeader);
      return homePageHeader.getText();
   }

   public List getPoductList()
   {

      List<String> productList = new ArrayList<>();

      int productSize = productListWebElement.size();
      for (int i = 0; i < productSize ; i++)
      {
         productList.add(productListWebElement.get(i).getText());
      }
      return productList;

   }

   public int getProductRowIndexByName(String productName)
   {
      for (int i = 0; i < productListWebElement.size(); i++)
      {
         if (productListWebElement.get(i).getText().equalsIgnoreCase(productName))
         {
            return i + 2;
         }
      }
      return 1;
   }

   public String getProductInStockQuantityByIndex(int index)
   {
      return getDriver().findElement(By.xpath("//tr["+index+"][contains(@class,'line_item')]/td[3]")).getText();
   }

   public String getProductPriceByIndex(int index)
   {

      return getDriver().findElement(By.xpath("//tr["+index+"][contains(@class,'line_item')]/td[2]")).getText();
   }
   
   public void enterProdcutPurchaseQuantityByIndex(int index, String quantity)
   {
       getDriver().findElement(By.xpath("//tr["+index+"][contains(@class,'line_item')]/td[4]/input")).sendKeys(quantity);
   }
   
   public void selectStateByStateName(String state) {
      
    //  stateList
      
      CommonUtils.selectOptionByFullText(stateList, state);
       
   }
   
   public void clickCheckOutBtn() {
      checkoutButton.click();
   }

   /**
    * @return
    */
   public String getProductSubPrice()
   {
     return productPriceSubTotal.getText();
   }
   
   
   /**
    * @return
    */
   public String getProductTotalTax()
   {
     return productTaxesTotal.getText();
   }
   
   /**
    * @return
    */
   public String getProductTotalPrice()
   {
     return productTotalPrice.getText();
   }
}
