package com.github.annushko.core.driver;

public final class DriverSettings {

    private final String driverClass;
    private final String capabilityFile;
    private final String hubUrl;
    private final DriverTimeouts timeouts;

    public DriverSettings(String driverClass, String capabilityFile, String hubUrl, DriverTimeouts timeouts) {
        this.driverClass = driverClass;
        this.capabilityFile = capabilityFile;
        this.hubUrl = hubUrl;
        this.timeouts = timeouts;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public String getCapabilityFile() {
        return capabilityFile;
    }

    public String getHubUrl() {
        return hubUrl;
    }

    public DriverTimeouts getTimeouts() {
        return timeouts;
    }

    @Override
    public String toString() {
        return "DriverSettings:" +
               "\t\ndriverClass = " + driverClass +
               "\t\ncapabilityFile = " + capabilityFile +
               "\t\nhubUrl = " + hubUrl +
               timeouts;
    }
}
