package com.github.annushko.core.ui;

public class AbstractPage extends AbstractWebEntity {

    private String baseUrl;

    @Override
    public AbstractWebEntity waitForLoad() {
        driver.getWaiter().documentReady();
        return this;
    }

    void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void open() {
        driver.get(baseUrl + getPageUrl());
    }

    protected String getPageUrl() {
        return "";
    }

}
