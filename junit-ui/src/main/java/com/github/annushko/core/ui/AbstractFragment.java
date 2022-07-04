package com.github.annushko.core.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public abstract class AbstractFragment extends AbstractWebEntity {

    protected abstract WebElement getRoot();

    protected WebElement getChildElement(By by) {
        return getRoot().findElement(by);
    }

    protected List<WebElement> getChildElements(By by) {
        return getRoot().findElements(by);
    }

    @Override
    public AbstractFragment waitForLoad() {
        waiter.forElementVisibleBy(getRoot());
        return this;
    }

}
