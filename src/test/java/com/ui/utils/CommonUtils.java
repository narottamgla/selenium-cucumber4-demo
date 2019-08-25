package com.ui.utils;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.ElementNotSelectableException;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class CommonUtils.
 */
public class CommonUtils
{

   /** The Constant LOG. */
   private static final Logger LOG = LoggerFactory.getLogger(CommonUtils.class);

   static int maxTimeout = 60;

   public static void waitForSeconds(int seconds)
   {
      try
      {
         Thread.sleep(seconds * 1000);
      }
      catch (InterruptedException e)
      {
         e.printStackTrace();
      }

   }
   
   public static <T> void waitForElementPresent(WebDriver driver, WebElement element)
   {
      Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
         .withTimeout(maxTimeout, TimeUnit.SECONDS)
         .pollingEvery(500, TimeUnit.MICROSECONDS)
         .ignoring(NoSuchElementException.class, StaleElementReferenceException.class)
         .ignoring(ElementNotInteractableException.class);
      wait.until((Function<? super WebDriver, T>) ExpectedConditions.visibilityOf(element));
   }

   public static <T> void waitForElementToBeClickable(WebDriver driver, WebElement element)
   {
      @SuppressWarnings("deprecation")
      Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
         .withTimeout(maxTimeout, TimeUnit.SECONDS)
         .pollingEvery(500, TimeUnit.MILLISECONDS)
         .ignoring(StaleElementReferenceException.class)
         .ignoring(NoSuchElementException.class, StaleElementReferenceException.class)
         .ignoring(ElementNotVisibleException.class)
         .ignoring(Exception.class)
         .withMessage("Waiting for element to be clickable");
      wait.until((Function<? super WebDriver, T>) ExpectedConditions.elementToBeClickable(element));
   }

   public static <T> void waitForElementToBeSelected(WebDriver driver, WebElement element)
   {
      Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
         .withTimeout(maxTimeout, TimeUnit.SECONDS)
         .pollingEvery(500, TimeUnit.MILLISECONDS)
         .ignoring(NoSuchElementException.class, StaleElementReferenceException.class)
         .ignoring(ElementNotSelectableException.class)
         .ignoring(Exception.class);
      wait.until((Function<? super WebDriver, T>) ExpectedConditions.elementToBeSelected(element));
   }

   public static <T> void waitForElementToBeVisible(WebDriver driver, WebElement element)
   {
      Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
         .withTimeout(maxTimeout, TimeUnit.SECONDS)
         .pollingEvery(500, TimeUnit.MILLISECONDS)
         .ignoring(NoSuchElementException.class, StaleElementReferenceException.class)
         .ignoring(ElementNotVisibleException.class)
         .ignoring(Exception.class)
         .withMessage("Waiting for element to visible");
      wait.until((Function<? super WebDriver, T>) ExpectedConditions.visibilityOf(element));
   }
   
   public static int selectOptionByFullText(List<WebElement> elementList, String text)
   {
      LOG.info("Selecting Option with Text: " + text);
      int i = 0;
      for (WebElement category : elementList)
      {
         i++;
         if (category.getText().equalsIgnoreCase(text))
         {
            category.click();
            break;
         }
      }
      return i;
   }
   

}
