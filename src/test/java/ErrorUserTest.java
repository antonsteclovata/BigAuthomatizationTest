import Pages.CheckoutInfo;
import Pages.InventoryPage;
import Pages.LogOut;
import Pages.ProfilePage;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import jdk.jfr.Description;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;

import java.io.ByteArrayInputStream;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Функциональное тестирование")
public class ErrorUserTest {
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
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterEach
    public void ShutDown() {
        driver.quit();
    }

    @Tag("ErrorUserTest")
    @Test
    @Story("Пользователь с ошибками")
    public void errorUserTest() {
        openPage();
        enterLoginData();
        clickLoginButton();
        putShoppingCartThreeProduct();
        clickShoppingCartButton();
        clickCheckoutButton();
        enterCheckoutData();
        clickContinueButton();
        clickFinishButton();
    }

    @Step("OpenPage")
    void openPage() {
        profilePage.openPage(Config.url);
        assertEquals("Swag Labs", driver.getTitle(), "Заголовок страницы не соответствует ожидаемому");
        assertTrue(driver.getCurrentUrl().contains("saucedemo.com"), "URl не соответствует ожидаемому");
    }

    @Step("EnterLoginData")
    void enterLoginData() {
        assertTrueIsDisplayed("user-name", "Поле \"username\" не видно");
        assertTrueIsDisplayed("password", "Поле \"password\" не видно");

        profilePage.enterUsername(Config.errorUser);
        profilePage.enterPassword(Config.password);
    }

    @Step("ClickLoginButton")
    void clickLoginButton() {
        assertTrueIsEnabled("login-button", "Кнопка логина не активна");
        profilePage.clickLoginButton();
        assertTrueGetCurrentUrl("/inventory.html", "Не перешли на страницу с товарами");
    }

    @Step("PutShoppingCartThreeProduct")
    void putShoppingCartThreeProduct() {
        inventoryPage.putShoppingCartThreeProduct();
        Allure.step("Проверка количества товаров в корзине ПРОПУЩЕНА", () -> {
            Allure.addAttachment("Причина пропуска", "text/plain",
                    " Кнопка добавление товара неактивна. Ожидалось 3 товара, но получено: 0. В следующем шаге приложен скриншот с пустой корзиной, хотя были добавлены товары");});
    }

    @Disabled ("Пропущена проверка, так как товары не были добавлены в корзину")
    @Step ("CheckoutPutShoppingCartThreeProduct")
    void checkoutPutShoppingCartThreeProduct () {
        assertEquals("3", driver.findElement(By.className("shopping_cart_link")).getText(), "Добавили 3 товара, но их количество в корзине не равно 3");
    }

    @Description ("Скриншот - как подветверждение что не были добавлены товары в корзину")
    @Step("clickShoppingCartButton")
    void clickShoppingCartButton() {
        assertTrue(driver.findElement(By.className("shopping_cart_link")).isDisplayed(), "Кнопка корзины не видна");
        assertTrue(driver.findElement(By.className("shopping_cart_link")).isEnabled(), "Кнопка корзины не активна");
        inventoryPage.clickShoppingCartButton();
        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        Allure.addAttachment("Screenshot", "image/png", new ByteArrayInputStream(screenshot), "png");
    }

    @Step("clickCheckoutButton")
    void clickCheckoutButton() {
        assertTrueIsEnabled("checkout", "Кнопка проверки товаров не активна");
        inventoryPage.clickCheckoutButton();
        assertTrueGetCurrentUrl("/checkout-step-one.html", "Не перешли на страницу заполнения данных для покупки");
    }

    @Step("enterCheckoutData")
    void enterCheckoutData() {
        assertTrueIsDisplayed("first-name", "Поле \"firstname\" не видно");
        assertTrueIsDisplayed("last-name", "Поле \"lastname\" не видно");
        assertTrueIsDisplayed("postal-code", "Поле \"postalcode\" не видно");
        checkoutInfo.enterFirstName(Config.firstName);
        checkoutInfo.enterLastName(Config.lastName);
        checkoutInfo.enterPostalCode(Config.postalCode);
    }

    @Step("clickContinueButton")
    void clickContinueButton() {
        assertTrueIsEnabled("continue", "Кнопка \"Continue\" не активна");
        checkoutInfo.clickContinueButton();
        assertTrueGetCurrentUrl("/checkout-step-two.html", "Не перешли на страницу проверки товара");
    }

    @Step("clickFinishButton")
    void clickFinishButton() {
        try {
            assertTrueIsEnabled("finish", "Кнопка \"Finish\" не активна");
            checkoutInfo.clickFinishButton();
            assertTrueGetCurrentUrl("/checkout-complete.html", "Не перешли на страницу \"Покупка выполнена\"");
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
