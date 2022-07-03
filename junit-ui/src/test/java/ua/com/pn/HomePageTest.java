package ua.com.pn;

import com.github.annushko.UITest;
import com.github.annushko.core.driver.WrappedDriver;
import com.github.annushko.junit.extension.context.TestContext;
import com.pn.ui.page.HomePage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HomePageTest extends UITest {

    public HomePageTest(WrappedDriver driver, TestContext context) {
        super(driver, context);
    }

    @Test
    public void openHomePageTest() {
        var homePage = factory.newInstance(HomePage.class);
        homePage.open();
        homePage.waitForLoad();
        Assertions.assertTrue(homePage.isMainContentDisplayed(),
                              "Main content on home page os not displayed");
    }

}
