package com.pn.ui.page;

import com.github.annushko.core.ui.AbstractPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends AbstractPage {

    @FindBy(css = ".content-main")
    private WebElement mainContent;

    public boolean isMainContentDisplayed() {
        return driver.getWaiter().waitForVisibility(mainContent).isDisplayed();
    }

}
