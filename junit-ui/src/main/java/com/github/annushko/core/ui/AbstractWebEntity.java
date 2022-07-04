package com.github.annushko.core.ui;

import com.github.annushko.core.driver.Waiter;
import com.github.annushko.core.driver.WrappedDriver;

public abstract class AbstractWebEntity {

    protected WrappedDriver driver;
    protected Waiter waiter;
    protected WebFactory factory;

    public AbstractWebEntity() {
    }

    void setDriver(WrappedDriver driver) {
        this.driver = driver;
        this.waiter = driver.getWaiter();
    }

    void setFactory(WebFactory factory) {
        this.factory = factory;
    }

    public abstract AbstractWebEntity waitForLoad();
}
