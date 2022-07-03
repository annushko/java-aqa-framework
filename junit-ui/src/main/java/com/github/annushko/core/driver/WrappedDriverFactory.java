package com.github.annushko.core.driver;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.annushko.core.jackson.JacksonHolder;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class WrappedDriverFactory {

    private final String driverClass;
    private final String capabilityFile;
    private final String hubUrl;
    private final DriverSettings settings;

    public WrappedDriverFactory(String driverClass, String capabilityFile, String hubUrl, DriverSettings settings) {
        this.driverClass = driverClass;
        this.capabilityFile = capabilityFile;
        this.hubUrl = hubUrl;
        this.settings = settings;
    }

    public WrappedDriver build() {
        var capabilities = readCapabilities(this.capabilityFile);
        if (this.hubUrl != null && !this.hubUrl.isEmpty()) {
            return buildRemoteDriver(this.hubUrl, capabilities);
        } else {
            return buildLocalDriver(capabilities);
        }
    }

    private DesiredCapabilities readCapabilities(String capabilityFile) {
        try {
            var is = ClassLoader.getSystemResourceAsStream(capabilityFile);
            var raw = JacksonHolder.DEFAULT.readValue(is, new TypeReference<HashMap<String, Object>>() {});
            return new DesiredCapabilities(raw);
        } catch (IOException e) {
            throw new UncheckedIOException("Exception while reading capability file: " + capabilityFile, e);
        }
    }

    private WrappedDriver buildLocalDriver(Capabilities capabilities) {
        try {
            Class<?> clazz = Class.forName(this.driverClass);
            Constructor<?> constructor = clazz.getConstructor(Capabilities.class);
            return new WrappedDriver((WebDriver) constructor.newInstance(capabilities), this.settings.getTimeouts());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("WebDriver class not found: " + this.driverClass, e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Required 'Capabilities.class' constructor not found", e);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(String.format("Exception while creating new WebDriver [%s] instance with capabilities: %s",
                                                     this.driverClass, capabilities), e);
        }
    }

    private WrappedDriver buildRemoteDriver(String hubUrl, Capabilities capabilities) {
        try {
            var url = new URL(hubUrl);
            return new WrappedDriver(new RemoteWebDriver(url, capabilities), this.settings.getTimeouts());
        } catch (MalformedURLException e) {
            throw new UncheckedIOException("Cannot parse URL: " + hubUrl, e);
        }
    }

}
