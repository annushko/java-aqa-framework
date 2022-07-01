package com.github.annushko.core.ui;

import org.openqa.selenium.WebElement;

public class AbstractComponent extends AbstractFragment {

    protected WebElement root;

    public AbstractComponent(WebElement root) {
        this.root = root;
    }

    @Override
    protected WebElement getRootElement() {
        return root;
    }

}
