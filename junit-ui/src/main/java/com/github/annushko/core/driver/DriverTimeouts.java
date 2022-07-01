package com.github.annushko.core.driver;

import java.time.Duration;

public class DriverTimeouts {

    private final Duration timeout;
    private final Duration pollingFrequency;

    public DriverTimeouts(Duration timeout, Duration pollingFrequency) {
        this.timeout = timeout;
        this.pollingFrequency = pollingFrequency;
    }

    public Duration getTimeout() {
        return timeout;
    }

    public Duration getPollingFrequency() {
        return pollingFrequency;
    }

    @Override
    public String toString() {
        return "\t\nDriverTimeouts:" +
               "\t\ntimeout = " + timeout +
               "\t\npollingFrequency = " + pollingFrequency;
    }
}
