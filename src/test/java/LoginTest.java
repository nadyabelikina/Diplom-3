import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import ru.practicum.constants.Endpoints;
import ru.practicum.constants.LoginPages;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class LoginTest extends BaseTest  {
    private final LoginPages page;

    public LoginTest(LoginPages page) { this.page = page; }

    @Parameterized.Parameters(name = "Страница: {0}")
    public static Object[][] data() {
        return new Object[][]{{LoginPages.HOME_PAGE}, {LoginPages.PERSONAL_ACCOUNT}, {LoginPages.REGISTER_FORM}, {LoginPages.RECOVERY_FORM}};
    }

    @Test
    @DisplayName("Авторизация пользователя с разных страниц")
    @Description("Проверка успешного входа в систему")
    public void loginFromDifferentSourcesTest() {
        //System.out.println(page);
        openLoginForm(page);
        login.waitForLoginPageToLoad();
        login.enterEmail(user.getEmail());
        login.enterPassword(user.getPassword());
        login.clickLoginButton();
        main.waitForConstructorToLoad();
        assertEquals(Endpoints.BASE_URI + "/", driver.getCurrentUrl());
    }
}
