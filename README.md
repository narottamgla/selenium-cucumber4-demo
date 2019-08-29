# Getting started with Serenity and Cucumber 4

Serenity BDD is a library that makes it easier to write high quality automated acceptance tests, with powerful reporting and living documentation features. It has strong support for both web testing with Selenium, and API testing using RestAssured. 

Serenity strongly encourages good test automation design, and supports several design patterns, including classic Page Objects, the newer Lean Page Objects/ Action Classes approach, and the more sophisticated and flexible Screenplay pattern.

The latest version of Serenity supports both Cucumber 2.4 and the more recent Cucumber 4.x. Cucumber 4 is not backward compatible with Cucumber 2. This article walks you through how to get started with Serenity and Cucumber 4, and also gives you a quick introduction to some of Cucumber 4’s new features.

## Get the code

Git:

    git clone https://github.com/narottamgla/selenium-cucumber4-demo
    cd selenium-cucumber4-demo


Or simply [download a zip](https://github.com/narottamgla/selenium-cucumber4-demo/archive/master.zip) file.

## The starter project
The best place to start with Serenity and Cucumber is to clone or download the starter project on Github ([https://github.com/narottamgla/selenium-cucumber4-demo](https://github.com/narottamgla/selenium-cucumber4-demo)). This project gives you a basic project setup, along with some sample tests and supporting classes. The master branch uses a more classic approach, using action classes and lightweight page objects.
### The project directory structure
The project has build scripts for both Maven and Gradle, and follows the standard directory structure used in most Serenity projects:
```Gherkin
src
  + main
  + test
    + java                          Test runners and supporting code
    + resources
      + features                    Feature files
      + webdriver                   Bundled webdriver binaries
        + linux
        + mac
        + windows
          chromedriver.exe          OS-specific Webdriver binaries
          geckodriver.exe

```

### Adding the Cucumber 4 dependency
Serenity seamlessly supports both Cucumber 2.x and Cucumber 4. However, this flexibility requires a little tweaking in the build dependencies. 

If you are using Maven, you need to do the following:
- exclude the default `cucumber-core` dependency from your `serenity-core` dependency
- Replace your `serenity-cucumber` dependency with the `serenity-cucumber4` dependency
- Add dependencies on the Cucumber 4.x version of `cucumber-java` and `cucumber-junit` into your project

An example of the correctly configured dependencies is shown below:
```xml
<dependency>
    <groupId>net.serenity-bdd</groupId>
    <artifactId>serenity-core</artifactId>
    <version>2.0.38</version>
    <scope>test</scope>
    <exclusions>
        <exclusion>
            <groupId>io.cucumber</groupId>
            <artifactId>cucumber-core</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<dependency>
    <groupId>net.serenity-bdd</groupId>
    <artifactId>serenity-cucumber4</artifactId>
    <version>1.0.4</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>io.cucumber</groupId>
    <artifactId>cucumber-java</artifactId>
    <version>4.2.0</version>
</dependency>
<dependency>
    <groupId>io.cucumber</groupId>
    <artifactId>cucumber-junit</artifactId>
    <version>4.2.0</version>
</dependency>
```

If you are using Gradle, you need to ensure that the 4.x version of `cucumber-core` is used using the _resolutionStrategy_ element, and also add the Cucumber 4.x version of `cucumber-java` and `cucumber-junit` dependencies as mentioned above:
```Gradle
configurations.all {
    resolutionStrategy {
        force "io.cucumber:cucumber-core:4.2.0"
    }
}

dependencies {
    testCompile "net.serenity-bdd:serenity-core:2.0.38",
                "net.serenity-bdd:serenity-cucumber4:1.0.4",
                "io.cucumber:cucumber-core:4.2.0",
                "io.cucumber:cucumber-junit:4.2.0"
}
```

In the rest of this article, we will walk through some of the highlights of both versions. Let’s start off with the version on the master branch, which uses lightweight page objects and actions.

## The Cucumber 4 sample scenario
Both variations of the sample project uses the sample Cucumber scenario. In this scenario, Sergey (who likes to search for stuff) is performing a search on the DuckDuckGo search engine:

```Gherkin
Feature: Verify Jungle socks home page

  Scenario: Verify jungle socks home page navigation
    Given I open the jungle socks home page url
    Then I should see jungle socks home page title as "JungleSocks"
    Then I should see jungle socks home page header as "Welcome To Jungle Socks!"
```

This scenario lets us explore a few of the new Cucumber 4 expressions. Cucumber 4 supports both the classic regular expressions, and the new _Cucumber Expressions_, which are more readable albeit not as powerful in some cases. 

The glue code for this scenario uses both regular expressions and cucumber expressions. The glue code looks this this:

```java
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
```

The `@Given` step uses a regular expression; the action class approach we use here is action-centric, not actor-centric, so we ignore the name of the actor. 

The `@When` and `@Then` steps uses Cucumber expressions, and highlights two useful features. Rather than using a regular expression to match the search term, we use the more readable Cucumber expression _{string}_. This matches a single or double-quoted string (the quotes themselves are dropped). Cucumber 4 also supports other typed expressions, such as _{int}_, _{word}_, and _ {float}_. 

Parentheses can be used to indicate optional text, so _“(s)he”_ will match both “he” and “she”. We could also write this using a slash: _“she/he”_.

### Lean Page Objects and Action Classes
The glue code shown above uses Serenity step libraries as _action classes_ to make the tests easier to read, and to improve maintainability.

These classes are declared using the Serenity `@Steps` annotation, shown below:
```java
    @Steps
    HomePageActions homePageActions;

    @Steps
    HomePageActions homePageActions;
    
```

The `@Steps`annotation tells Serenity to create a new instance of the class, and inject any other steps or page objects that this instance might need. 

Each action class models a particular facet of user behaviour: navigating to a particular page, performing a search, or retrieving the results of a search. These classes are designed to be small and self-contained, which makes them more stable and easier to maintain.

```java
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

```

```java
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
   
   ```
The main advantage of the approach used in this example is not in the lines of code written, although Serenity does reduce a lot of the boilerplate code that you would normally need to write in a web test. The real advantage is in the use of many small, stable classes, each of which focuses on a single job. This application of the _Single Responsibility Principle_ goes a long way to making the test code more stable, easier to understand, and easier to maintain.


## Executing the tests
To run the sample project, you can either just run the `CucumberTestSuite` test runner class, or run either `mvn verify` or `gradle test` from the command line.

By default, the tests will run using Chrome. You can run them in Firefox by overriding the `driver` system property, e.g.
```json
$ mvn clean verify -Ddriver=firefox
```
Or 
```json
$ gradle clean test -Pdriver=firefox
```

The test results will be recorded in the `target/site/serenity` directory.

## Simplified WebDriver configuration and other Serenity extras
The sample projects both use some Serenity features which make configuring the tests easier. In particular, Serenity uses the `serenity.conf` file in the `src/test/resources` directory to configure test execution options.  
### Webdriver configuration
The WebDriver configuration is managed entirely from this file, as illustrated below:
```java
webdriver {
    driver = chrome
}
headless.mode = true

chrome.switches="""--start-maximized;--test-type;--no-sandbox;--ignore-certificate-errors;
                   --disable-popup-blocking;--disable-default-apps;--disable-extensions-file-access-check;
                   --incognito;--disable-infobars,--disable-gpu"""

```

The project also bundles some of the WebDriver binaries that you need to run Selenium tests in the `src/test/resources/webdriver` directories. These binaries are configured in the `drivers` section of the `serenity.conf` config file:
```json
drivers {
  windows {
    webdriver.chrome.driver = "src/test/resources/webdriver/windows/chromedriver.exe"
    webdriver.gecko.driver = "src/test/resources/webdriver/windows/geckodriver.exe"
  }
  mac {
    webdriver.chrome.driver = "src/test/resources/webdriver/mac/chromedriver"
    webdriver.gecko.driver = "src/test/resources/webdriver/mac/geckodriver"
  }
  linux {
    webdriver.chrome.driver = "src/test/resources/webdriver/linux/chromedriver"
    webdriver.gecko.driver = "src/test/resources/webdriver/linux/geckodriver"
  }
}
```
This configuration means that development machines and build servers do not need to have a particular version of the WebDriver drivers installed for the tests to run correctly.

### Environment-specific configurations
We can also configure environment-specific properties and options, so that the tests can be run in different environments. Here, we configure three environments, __dev__, _staging_ and _prod_, with different starting URLs for each:
```json
environments {
  default {
    webdriver.base.url = "https://jungle-socks.herokuapp.com/"
  }
  dev {
    webdriver.base.url = "https://jungle-socks.herokuapp.com/dev"
  }
  staging {
    webdriver.base.url = "https://jungle-socks.herokuapp.com/staging"
  }
  prod {
    webdriver.base.url = "https://jungle-socks.herokuapp.com/prod"
  }
}
```
  
You use the `environment` system property to determine which environment to run against. For example to run the tests in the staging environment, you could run:
```json
$ mvn clean verify -Denvironment=staging
```

See [**this article**](https://johnfergusonsmart.com/environment-specific-configuration-in-serenity-bdd/) for more details about this feature.

## Want to learn more?
For more information about Serenity BDD, you can read the [**Serenity BDD Book**](https://serenity-bdd.github.io/theserenitybook/latest/index.html), the official online Serenity documentation source. Other sources include:
* **[Byte-sized Serenity BDD](https://www.youtube.com/channel/UCav6-dPEUiLbnu-rgpy7_bw/featured)** - tips and tricks about Serenity BDD
* [**Serenity BDD Blog**](https://johnfergusonsmart.com/category/serenity-bdd/) - regular articles about Serenity BDD
* [**The Serenity BDD Dojo**](https://serenitydojo.teachable.com) - Online training on Serenity BDD and on test automation and BDD in general. 
