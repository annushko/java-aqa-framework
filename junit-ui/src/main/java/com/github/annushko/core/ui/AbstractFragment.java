package com.github.annushko.core.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public abstract class AbstractFragment extends AbstractWebEntity {

    protected abstract WebElement getRootElement();

    protected WebElement getChildElement(By by) {
        return getRootElement().findElement(by);
    }

    protected List<WebElement> getChildElements(By by) {
        return getRootElement().findElements(by);
    }

    @Override
    public AbstractFragment waitForLoad() {
        driver.getWaiter().waitForVisibility(getRootElement());
        return this;
    }

}
