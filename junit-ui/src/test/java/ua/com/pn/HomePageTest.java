package ua.com.pn;

import com.github.annushko.UITest;
import com.github.annushko.core.driver.WrappedDriver;
import com.github.annushko.junit.extension.context.TestContext;
import com.pn.ui.fragment.HeaderFragment;
import com.pn.ui.page.HomePage;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HomePageTest extends UITest {

    public HomePageTest(WrappedDriver driver, TestContext context) {
        super(driver, context);
    }

    @Test
    public void searchTipsTest() {
        var homePage = factory.newInstance(HomePage.class);
        homePage.open();
        HeaderFragment header = homePage.header();
        String query = "Apple MacBook";
        header.enterSearch(query);

        assertThat(header.getAllSearchTips())
                .as("Incorrect search tip!")
                .allSatisfy(tip -> {
                    assertThat(tip)
                            .as("Wrong search tip!")
                            .containsIgnoringCase(query);
                });
    }

}
