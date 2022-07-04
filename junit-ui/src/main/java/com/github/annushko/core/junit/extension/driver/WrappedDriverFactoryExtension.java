package com.github.annushko.core.junit.extension.driver;

import com.github.annushko.core.driver.DriverSettings;
import com.github.annushko.core.driver.DriverTimeouts;
import com.github.annushko.core.driver.WrappedDriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.time.Duration;
import java.util.List;

public class WrappedDriverFactoryExtension implements BeforeAllCallback {

    private final Logger log = LogManager.getLogger(getClass());

    @Override
    public void beforeAll(ExtensionContext context) {
        setDriverExeProperties(context);
        var driverClass = context.getConfigurationParameter("selenium.driver.class")
                                 .orElse(null);
        var capabilityFile = context.getConfigurationParameter("selenium.capability.file")
                                    .orElse(null);
        var hubUrl = context.getConfigurationParameter("selenium.hub.url")
                            .orElse(null);
        var timeout = context.getConfigurationParameter("selenium.timeout")
                             .map(Duration::parse)
                             .orElse(Duration.ofSeconds(30));
        var frequency = context.getConfigurationParameter("selenium.polling.frequency")
                               .map(Duration::parse)
                               .orElse(Duration.ofMillis(250));
        var settings = new DriverSettings(driverClass, capabilityFile, hubUrl,
                                          new DriverTimeouts(timeout, frequency));
        log.info(settings);
        var factory = new WrappedDriverFactory(settings);
        context.getRoot()
               .getStore(ExtensionContext.Namespace.GLOBAL)
               .put(WrappedDriverFactory.class, factory);
    }

    private void setDriverExeProperties(ExtensionContext context) {
        List.of("webdriver.chrome.driver", "webdriver.gecko.driver")
            .forEach(key -> {
                context.getConfigurationParameter(key)
                       .ifPresent(value -> System.setProperty(key, value));
            });
    }

}
