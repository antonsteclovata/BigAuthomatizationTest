import Pages.ProfilePage;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Функциональное тестирование")
public class UnknownUserTest {
    private WebDriver driver;
    private String browser;
    private ProfilePage profilePage;

    @BeforeEach
    public void setUp() {
        browser = System.getProperty("browser", "Safari");
        driver = ManageDriver.getWebDriver(browser);
        profilePage = new ProfilePage(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterEach
    public void ShutDown() {
        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        Allure.addAttachment("Screenshot", "image/png", new ByteArrayInputStream(screenshot), "png");
        driver.quit();
    }

    @Tag("UnknownUserTest")
    @Test
    @Story("Авторизация с неизвестным логином и паролем")
    public void unknownUserTest() {
        openPage();
        enterLoginData();
        clickLoginButton();
        getErrorMessage();
    }

    @Step("OpenPage")
    void openPage() {
        profilePage.openPage(Config.url);
        assertEquals("Swag Labs", driver.getTitle(), "Заголовок страницы не соответствует ожидаемому");
        assertTrue(driver.getCurrentUrl().contains("saucedemo.com"), "URl не соответствует ожидаемому");
    }

    @Step ("EnterLoginData")
    void enterLoginData() {
        assertTrueIsDisplayed("user-name", "Поле \"username\" не видно");
        assertTrueIsDisplayed("password", "Поле \"password\" не видно");
        profilePage.enterUsername("unknown_username");
        profilePage.enterPassword("unknown_password");
    }
    @Step ("ClickLoginButton")
    void clickLoginButton() {
        assertTrueIsEnabled("login-button", "Кнопка логина не активна");
        profilePage.clickLoginButton();}

    @Step("GetErrorMessage")
    void getErrorMessage() {
        String errorText = profilePage.errorMessage.getText();
        Allure.addAttachment("Сообщение об ошибке", "text/plain", errorText);
    }

    private void assertTrueIsEnabled (String id, String errorTxt) {assertTrue(driver.findElement(By.id(id)).isEnabled(), errorTxt);}
    private void assertTrueIsDisplayed (String id, String errorTxt) {assertTrue(driver.findElement(By.id(id)).isDisplayed(), errorTxt);}
}