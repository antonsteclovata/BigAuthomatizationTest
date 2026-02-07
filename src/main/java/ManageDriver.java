import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

public class ManageDriver {

    public static WebDriver getWebDriver (String browser) {

        return switch (browser) {
            // selenium 4+ сам управляет драйверами ему не нужно отдельно скачивать Chrome Driver и FireFox driver
            case "Safari" -> new SafariDriver();
            case "Edge" -> new EdgeDriver();
            case "Chrome" -> new ChromeDriver();
            case "FireFox" -> new FirefoxDriver();
            default -> throw new IllegalArgumentException("Unknow browser" + browser);
        };
    }
}
