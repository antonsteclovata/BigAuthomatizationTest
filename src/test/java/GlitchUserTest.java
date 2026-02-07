import Pages.CheckoutInfo;
import Pages.InventoryPage;
import Pages.LogOut;
import Pages.ProfilePage;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Функциональное тестирование")
public class GlitchUserTest {
    private WebDriver driver;
    private String browser;
    private ProfilePage profilePage;
    private InventoryPage inventoryPage;
    private CheckoutInfo checkoutInfo;
    private LogOut logOut;

    @BeforeEach
    public void setUp () {
        browser = System.getProperty("browser", "Safari");
        driver = ManageDriver.getWebDriver(browser);
        profilePage = new ProfilePage(driver);
        inventoryPage = new InventoryPage(driver);
        checkoutInfo = new CheckoutInfo(driver);
        logOut = new LogOut(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterEach
    public void ShutDown() {
        driver.quit();
    }

    @Tag("GlitchUserTest")
    @Test
    @Story("Пользователь с задержками в работе")
    public void glitchUserTest () {
        openPage();
        enterLoginData();
        clickLoginButton();
        putShoppingCartThreeProduct();
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

        profilePage.enterUsername(Config.errorUser);
        profilePage.enterPassword(Config.password);
    }
    @Step ("ClickLoginButton")
    void clickLoginButton() {
        assertTrueIsEnabled("login-button", "Кнопка логина не активна");
        profilePage.clickLoginButton();
        assertTrueGetCurrentUrl("/inventory.html", "Не перешли на страницу с товарами");
    }
    @Step ("PutShoppingCartThreeProduct")
    void putShoppingCartThreeProduct() {
        try {
            inventoryPage.putShoppingCartThreeProduct();
            assertEquals("3", driver.findElement(By.className("shopping_cart_link")).getText(), "Добавили 3 товара, но их количество в корзине не равно 3 (Кнопка добавление товаров не активна)");
        } catch (ElementNotInteractableException e) {
            System.out.println("Не удалось кликнуть на кнопку покупки товара" + e.getMessage());
            throw e;
        } catch (AssertionError e) {
            System.out.println("Проверка условий провалилась:" + e.getMessage());
            throw e;
        }
    }

    private void assertTrueIsEnabled (String id, String errorTxt) {assertTrue(driver.findElement(By.id(id)).isEnabled(), errorTxt);}
    private void assertTrueIsDisplayed (String id, String errorTxt) {assertTrue(driver.findElement(By.id(id)).isDisplayed(), errorTxt);}
    private void assertTrueGetCurrentUrl (String url, String errorTxt) {assertTrue(driver.getCurrentUrl().endsWith(url), errorTxt);}
}
