package com.github.annushko;

import com.github.annushko.core.driver.WrappedDriver;
import com.github.annushko.core.junit.extension.driver.WrappedDriverParameterResolver;
import com.github.annushko.core.junit.extension.UITestWatcher;
import com.github.annushko.core.junit.extension.driver.WrappedDriverFactoryExtension;
import com.github.annushko.core.ui.WebFactory;
import com.github.annushko.junit.extension.context.TestContext;
import com.github.annushko.junit.extension.context.TestContextParameterResolver;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({UITestWatcher.class,
             WrappedDriverFactoryExtension.class,
             WrappedDriverParameterResolver.class,
             TestContextParameterResolver.class})
public abstract class UITest {

    protected WrappedDriver driver;
    protected TestContext context;
    protected WebFactory factory;

    public UITest(WrappedDriver driver, TestContext context) {
        this.driver = driver;
        this.context = context;
        this.factory = new WebFactory(driver, context.getUrlWeb());
    }

}
