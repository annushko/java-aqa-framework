package ua.com.pn;

import com.github.annushko.UITest;
import com.github.annushko.core.driver.WrappedDriver;
import com.github.annushko.junit.extension.context.TestContext;
import com.pn.ui.fragment.HeaderFragment;
import com.pn.ui.page.HomePage;
import com.pn.ui.page.SearchResultPage;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchAndFilterTest extends UITest {

    public SearchAndFilterTest(WrappedDriver driver, TestContext context) {
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
                            .as(tip + " dont`t contains query: [" + query + "]")
                            .containsIgnoringCase(query);
                });
    }

    @Test
    public void searchBarTest() {
        var homePage = factory.newInstance(HomePage.class);
        homePage.open();
        HeaderFragment header = homePage.header();
        String query = "Apple MacBook";
        header.enterSearch(query);
        header.clickOnSearchButton();

        SearchResultPage searchResultPage = factory.newInstance(SearchResultPage.class);

        assertThat(searchResultPage.catalog().getProductsName())
                .as("Incorrect products in search result catalog!")
                .allSatisfy(s -> {
                    assertThat(s)
                            .as(s + " don`t contains query: [" + query + "]")
                            .containsIgnoringCase(query);
                });
    }

}
