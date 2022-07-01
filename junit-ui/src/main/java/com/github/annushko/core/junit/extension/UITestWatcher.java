package com.github.annushko.core.junit.extension;

import com.github.annushko.core.driver.WrappedDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UITestWatcher implements TestWatcher {

    private final Logger log = LogManager.getLogger(getClass());

    @Override
    public void testSuccessful(ExtensionContext context) {
        finishTest(context, null);
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        finishTest(context, cause);
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        finishTest(context, cause);
    }

    private void finishTest(ExtensionContext context, Throwable cause) {
        var driver = extractFromStore(context);
        if (driver != null) {
            if (cause != null) {
                makeScreenShot(context, driver);
            }
            driver.quit();
        }
    }

    private WrappedDriver extractFromStore(ExtensionContext context) {
        return context.getRoot()
                      .getStore(ExtensionContext.Namespace.GLOBAL)
                      .remove(Thread.currentThread(), WrappedDriver.class);
    }

    public void makeScreenShot(ExtensionContext context, WrappedDriver driver) {
        var fileName = context.getRequiredTestMethod().getName() + "_" + System.currentTimeMillis();
        try {
            var dir = Files.createDirectories(Paths.get("build/screenshots/"));
            var bytes = driver.getScreenshotAs(OutputType.BYTES);
            Files.write(dir.resolve(fileName + ".png"), bytes);
        } catch (IOException e) {
            log.error("Exception while creating screenshot: {}", fileName, e);
        }
    }

}
