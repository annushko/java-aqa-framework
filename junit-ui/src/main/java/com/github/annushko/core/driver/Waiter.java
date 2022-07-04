package com.github.annushko.core.driver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
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

    public WebElement forElementVisibleBy(WebElement element) {
        return waiter().until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement forChildVisibleBy(WebElement root, By by) {
        return waiter().until(new Function<>() {
            @Override
            public WebElement apply(WebDriver webDriver) {
                try {
                    WebElement element = root.findElement(by);
                    return element.isDisplayed() ? element : null;
                } catch (StaleElementReferenceException | NoSuchElementException e) {
                    return null;
                }
            }

            @Override
            public String toString() {
                return String.format("for child [%s] visible from: %s", by, root);
            }
        });
    }

    public List<WebElement> forAllChildVisibleBy(WebElement root, By by) {
        return waiter().until(new Function<>() {
            @Override
            public List<WebElement> apply(WebDriver webDriver) {
                try {
                    var elements = root.findElements(by);
                    if (elements.size() != 0) {
                        if (elements.stream().allMatch(WebElement::isDisplayed)) {
                            return elements;
                        }
                    }
                    return null;
                } catch (StaleElementReferenceException e) {
                    return null;
                }
            }

            @Override
            public String toString() {
                return String.format("for all child [%s] visible from: %s", by, root);
            }
        });
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

    public WebDriverWait waiter() {
        return this.waiter(this.timeout);
    }

    public WebDriverWait waiter(Duration timeout) {
        return new WebDriverWait(this.driver, timeout, this.pollingFrequency);
    }

}
