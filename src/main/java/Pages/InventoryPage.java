package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class InventoryPage {
    public WebDriver driver;
    private WebDriverWait wait;

    public InventoryPage (WebDriver driver) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy (id = "add-to-cart-sauce-labs-bolt-t-shirt") private WebElement product1;
    @FindBy (id = "add-to-cart-sauce-labs-fleece-jacket") private WebElement product2;
    @FindBy (id = "add-to-cart-test.allthethings()-t-shirt-(red)") private WebElement product3;
    @FindBy (className = "shopping_cart_link") private WebElement shoppingCartButton;
    @FindBy (id = "checkout") private WebElement checkoutButton;

    public void putShoppingCartThreeProduct () {product1.click(); product2.click(); product3.click();}
    public void clickShoppingCartButton() {shoppingCartButton.click();}
    public void clickCheckoutButton () {checkoutButton.click();}
}
