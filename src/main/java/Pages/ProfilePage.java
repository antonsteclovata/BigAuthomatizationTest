package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ProfilePage {
    public WebDriver driver;
    private WebDriverWait wait;

    public ProfilePage(WebDriver driver) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy (id = "user-name") private WebElement usernameField;
    @FindBy (id = "password") private WebElement passwordField;
    @FindBy (id = "login-button") private WebElement loginButton;
    @FindBy (xpath = "//*[@id=\"login_button_container\"]/div/form/div[3]/h3") public WebElement errorMessage;

    public void openPage (String url) {driver.get(url);}
    public void enterUsername (String username) {usernameField.sendKeys(username);}
    public void enterPassword (String password) {passwordField.sendKeys(password);}
    public void clickLoginButton () {loginButton.click();}
}
