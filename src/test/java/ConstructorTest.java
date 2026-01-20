import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ru.practicum.constants.Endpoints;
import ru.practicum.constants.BurgerIngredients;
import ru.practicum.pages.Main;

import static org.junit.Assert.assertTrue;

/**
 * Параметризованный тест конструктора
 */
@RunWith(Parameterized.class)
public class ConstructorTest {
    private WebDriver driver;
    private Main main;
    private final BurgerIngredients ingredients;

    public ConstructorTest(BurgerIngredients ingredients) {
        this.ingredients = ingredients;
    }

    @Parameterized.Parameters(name = "Вкладка конструктора: {0}")
    public static Object[][] data() {
        return new Object[][]{{BurgerIngredients.BUN}, {BurgerIngredients.SAUCE}, {BurgerIngredients.FILLING}};
    }

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        main = new Main(driver);
        driver.get(Endpoints.BASE_URI);
        main.waitForConstructorToLoad();
    }

    @After
    public void tearDown() { driver.quit(); }

    @Test
    @DisplayName("Переход по разделам конструктора")
    @Description("Проверяет, что вкладка активируется при клике")
    public void switchConstructorSection() {
        if (ingredients == BurgerIngredients.BUN) main.clickSection(BurgerIngredients.SAUCE);
        else main.clickSection(BurgerIngredients.BUN);

        main.clickSection(ingredients);
        String cls = main.getClassName(ingredients);
        assertTrue("Секция не активна: " + ingredients, cls.contains("tab_tab_type_current__2BEPc"));
    }
}
