package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutInfo {
    public WebDriver driver;

    public CheckoutInfo (WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(id = "first-name") private WebElement firstName;
    @FindBy (id = "last-name") private WebElement lastName;
    @FindBy (id = "postal-code") private WebElement postalCode;
    @FindBy (id = "continue") private WebElement continueButton;
    @FindBy (id = "finish") private WebElement finishButton;
    @FindBy (xpath =  "//*[@id=\"checkout_info_container\"]/div/form/div[1]/div[4]/h3") private WebElement errorMessage;

    public void enterFirstName (String first_name) {firstName.sendKeys(first_name);}
    public void enterLastName (String last_name) {lastName.sendKeys(last_name);}
    public void enterPostalCode (String postal_code) {postalCode.sendKeys(postal_code);}
    public void clickContinueButton () {continueButton.click();}
    public void clickFinishButton () {finishButton.click();}
    public void getErrorMessage () {errorMessage.getText();}
}
