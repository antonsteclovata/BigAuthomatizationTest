package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LogOut {
    private WebDriverWait wait;
    public WebDriver driver;

    public LogOut (WebDriver driver) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy (id = "back-to-products") private WebElement backButton;
    @FindBy (id = "react-burger-menu-btn") private WebElement menuButton;
    @FindBy (id = "logout_sidebar_link") private WebElement logoutButton;

    public void clickBackButton () {backButton.click();}
    public void clickMenuButton () {menuButton.click();}
    public void loadingButton () {wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link")));}
    public void clickLogoutButton () {logoutButton.click();}
}
