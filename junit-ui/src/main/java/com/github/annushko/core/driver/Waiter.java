package com.github.annushko.core.driver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.Function;

public class Waiter {

    private final WrappedDriver driver;
    private final Duration timeout;
    private final Duration pollingFrequency;

    public Waiter(WrappedDriver driver, DriverTimeouts timeouts) {
        this.driver = driver;
        this.timeout = timeouts.getTimeout();
        this.pollingFrequency = timeouts.getPollingFrequency();
    }

    public WebElement waitForVisibility(WebElement element) {
        return waiter().until(ExpectedConditions.visibilityOf(element));
    }

    public WebDriverWait waiter() {
        return this.waiter(this.timeout);
    }

    public WebDriverWait waiter(Duration timeout) {
        return new WebDriverWait(this.driver, timeout, this.pollingFrequency);
    }

    public boolean documentReady() {
        return waiter().until(new Function<>() {

            @Override
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }

            @Override
            public String toString() {
                return "Document to be ready";
            }
        });
    }

}
