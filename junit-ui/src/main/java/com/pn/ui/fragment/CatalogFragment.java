package com.pn.ui.fragment;

import com.github.annushko.core.ui.AbstractFragment;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class CatalogFragment extends AbstractFragment {

    @FindBy(css = "div#column-center")
    private WebElement root;

    @Override
    protected WebElement getRoot() {
        return root;
    }

    public List<String> getProductsName() {
        return waiter.forAnyChildPresentBy(getRoot(), By.cssSelector("div#column-center div.content-shadow-block article div.catalog-block-head"))
                     .stream()
                     .map(WebElement::getText)
                     .collect(Collectors.toList());
    }

}
