import io.qameta.allure.Step;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.practicum.constants.Endpoints;
import ru.practicum.pages.*;
import ru.practicum.constants.LoginPages;
import ru.practicum.steps.UserStep;

import ru.practicum.generate.UserDataFactory;
import ru.practicum.model.UserData;

import java.io.File;
import java.time.Duration;

/**
 * Базовый класс для UI-тестов
 */
public abstract class BaseTest {
    protected WebDriver driver;
    protected Main main;
    protected Login login;
    protected Registration registration;
    protected Recovery recovery;
    protected UserStep userStep;
    protected UserData user;
    protected String accessToken;

    @Before
    public void setUp() {
        String browser = System.getProperty("browser", "chrome");
        ChromeOptions options = new ChromeOptions();

        if ("yandex".equalsIgnoreCase(browser)) {
            // настройки Yandex Browser
            String[] paths = {"/Applications/Yandex.app/Contents/MacOS/Yandex",
                    "C:\\Users\\%USERNAME%\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe",
                    "/usr/bin/yandex-browser"};
            boolean found = false;
            for (String path : paths) {
                File bin = new File(path);
                if (bin.exists()) { options.setBinary(bin); found = true; break; }
            }
            if (!found) throw new RuntimeException("Не найден Yandex Browser");
        } else {
            WebDriverManager.chromedriver().setup();
        }

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
        driver.get(Endpoints.BASE_URI);

        main     = new Main(driver);
        login    = new Login(driver);
        registration = new Registration(driver);
        recovery = new Recovery(driver);
        userStep   = new UserStep();


        if (!(this instanceof RegisterTest)) {
            user = UserDataFactory.generateValidUser();
            accessToken = userStep.create(user).extract().path("accessToken");
        }
    }

    @After
    public void tearDown() {
        if (accessToken != null) userStep.delete(accessToken);
        if (driver != null) driver.quit();
    }

    @Step("Открытие формы логина через: {source}")
    public void openLoginForm(LoginPages source) {
        switch (source) {
            case HOME_PAGE:
                main.clickLoginButton(); break;
            case PERSONAL_ACCOUNT:
                main.clickPersonalAccount(); break;
            case REGISTER_FORM:
                main.clickPersonalAccount();
                login.waitForLoginPageToLoad();
                login.clickRegisterLink();
                registration.clickLoginLink();
                break;
            case RECOVERY_FORM:
                main.clickPersonalAccount();
                login.waitForLoginPageToLoad();
                login.clickForgotPasswordLink();
                recovery.clickLoginLink();
                break;
        }
    }
}