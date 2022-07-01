package com.github.annushko.core.junit.extension;

import com.github.annushko.core.driver.WrappedDriver;
import com.github.annushko.core.driver.WrappedDriverFactory;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public class WrappedDriverParameterResolver implements ParameterResolver {

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return WrappedDriver.class.isAssignableFrom(parameterContext.getParameter().getType());
    }

    @Override
    public WrappedDriver resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        var factory = extensionContext.getRoot().getStore(ExtensionContext.Namespace.GLOBAL)
                                      .get(WrappedDriverFactory.class, WrappedDriverFactory.class);
        var driver = factory.build();
        extensionContext.getRoot().getStore(ExtensionContext.Namespace.GLOBAL).put(Thread.currentThread(), driver);
        return driver;
    }

}
