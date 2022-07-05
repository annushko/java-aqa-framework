package com.pn.ui.fragment;

import com.github.annushko.core.ui.AbstractFragment;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class HeaderFragment extends AbstractFragment {

    @FindBy(css = "header.header")
    private WebElement root;

    @Override
    protected WebElement getRoot() {
        return root;
    }

    public void enterSearch(String query) {
        var input = waiter.forChildVisibleBy(getRoot(), By.cssSelector("input.search-text-input"));
        input.click();
        input.sendKeys(query);
    }

    public void clickOnSearchButton() {
        waiter.forChildVisibleBy(getRoot(), By.cssSelector("form#search [type='submit']")).click();
    }

    public List<String> getAllSearchTips() {
        return allSearchTips()
                       .stream()
                       .map(WebElement::getText)
                       .collect(Collectors.toList());
    }

    private List<WebElement> allSearchTips() {
        waitForLoad();
        return waiter.forAnyChildPresentBy(getRoot(), By.cssSelector("div.search-tips-body li a"));
    }

}
