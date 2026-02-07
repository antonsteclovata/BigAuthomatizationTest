import Pages.CheckoutInfo;
import Pages.InventoryPage;
import Pages.LogOut;
import Pages.ProfilePage;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Функциональное тестирование")
public class StandardUserTest {
    private WebDriver driver;
    private String browser;
    private ProfilePage profilePage;
    private InventoryPage inventoryPage;
    private CheckoutInfo checkoutInfo;
    private LogOut logOut;

    @BeforeEach
    public void setUp() {
        browser = System.getProperty("browser", "Safari");
        driver = ManageDriver.getWebDriver(browser);
        profilePage = new ProfilePage(driver);
        inventoryPage = new InventoryPage(driver);
        checkoutInfo = new CheckoutInfo(driver);
        logOut = new LogOut(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterEach public void ShutDown() {
        driver.quit();
    }

    @Tag("StandardTest")
    @Test
    @Story("Успешный вход стандартного пользователя")
    void standardLoginTest () {
        openPage();
        enterLoginData();
        clickLoginButton();
        putShoppingCartThreeProduct();
        clickShoppingCartButton();
        clickCheckoutButton();
        enterCheckoutData();
        clickContinueButton();
        clickFinishButton();
        clickBackButton();
        clickMenuButton();
        clickLogoutButton();
    }

    @Step ("Открыть стартовую страницу")
    void openPage() {
            profilePage.openPage(Config.url);
            assertTrue(driver.getCurrentUrl().contains("saucedemo.com"), "URl не соответствует ожидаемому");
        }
    @Step ("Ввести данные для входа")
    void enterLoginData() {
            assertTrueIsDisplayed("user-name", "Поле \"username\" не видно");
            assertTrueIsDisplayed("password", "Поле \"password\" не видно");

            profilePage.enterUsername(Config.defaultUsername);
            profilePage.enterPassword(Config.password);
        }
    @Step ("Нажать кнопку входа")
    void clickLoginButton() {
            assertTrueIsEnabled("login-button", "Кнопка логина не активна");
            profilePage.clickLoginButton();
            assertTrueGetCurrentUrl("/inventory.html", "Не перешли на страницу с товарами");
        }
    @Step ("Добавить три товара в корзину")
    void putShoppingCartThreeProduct() {
            inventoryPage.putShoppingCartThreeProduct();
            assertEquals("3", driver.findElement(By.className("shopping_cart_link")).getText(), "Добавили 3 товара, но их количество в корзине не равно 3");
        }
    @Step ("Перейти в корзину")
    void clickShoppingCartButton () {
            assertTrue(driver.findElement(By.className("shopping_cart_link")).isDisplayed(), "Кнопка корзины не видна");
            assertTrue(driver.findElement(By.className("shopping_cart_link")).isEnabled(), "Кнопка корзины не активна");
            inventoryPage.clickShoppingCartButton();
        }
    @Step ("Начать оформление заказа")
    void clickCheckoutButton () {
            assertTrueIsEnabled("checkout", "Кнопка проверки товаров не активна");
            inventoryPage.clickCheckoutButton();
            assertTrueGetCurrentUrl("/checkout-step-one.html", "Не перешли на страницу заполнения данных для покупки");
        }
    @Step ("Заполнить данные для оформления заказа")
    void enterCheckoutData () {
            assertTrueIsDisplayed("first-name", "Поле \"firstname\" не видно");
            assertTrueIsDisplayed("last-name", "Поле \"lastname\" не видно");
            assertTrueIsDisplayed("postal-code", "Поле \"postalcode\" не видно");
            checkoutInfo.enterFirstName(Config.firstName);
            checkoutInfo.enterLastName(Config.lastName);
            checkoutInfo.enterPostalCode(Config.postalCode);
        }
    @Step ("Продолжить оформление заказа")
    void clickContinueButton () {
            assertTrueIsEnabled("continue", "Кнопка \"Continue\" не активна");
            checkoutInfo.clickContinueButton();
            assertTrueGetCurrentUrl("/checkout-step-two.html", "Не перешли на страницу проверки товара");
        }
    @Step ("Завершить оформление заказа")
    void clickFinishButton () {
            assertTrueIsEnabled("finish", "Кнопка \"Finish\" не активна");
            checkoutInfo.clickFinishButton();
            assertTrueGetCurrentUrl("/checkout-complete.html", "Не перешли на страницу \"Покупка выполнена\"");
        }
    @Step ("Вернуться обратно к товарам")
    void clickBackButton () {
            assertTrueIsEnabled("back-to-products", "Кнопка \"Back Hone\" не активна");
            logOut.clickBackButton();
            assertTrueGetCurrentUrl("/inventory.html", "Не перешли на страницу с товарами");
        }
    @Step ("Открыть всплывающее меню")
    void clickMenuButton () {
            assertTrueIsEnabled("react-burger-menu-btn", "Всплывающее окно не активно");
            logOut.clickMenuButton();
            logOut.loadingButton();
        }
    @Step ("Выйти из системы")
    void clickLogoutButton () {
            assertTrueIsEnabled("logout_sidebar_link", "Кнопка \"Logout\" не активна");
            logOut.clickLogoutButton();
            assertTrueGetCurrentUrl("saucedemo.com", "После выхода не перекинуло на страницу логина");
    }

    private void assertTrueIsEnabled (String id, String errorTxt) {assertTrue(driver.findElement(By.id(id)).isEnabled(), errorTxt);}
    private void assertTrueIsDisplayed (String id, String errorTxt) {assertTrue(driver.findElement(By.id(id)).isDisplayed(), errorTxt);}
    private void assertTrueGetCurrentUrl (String url, String errorTxt) {assertTrue(driver.getCurrentUrl().endsWith(url), errorTxt);}
}
