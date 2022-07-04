package com.github.annushko.core.ui;

import com.github.annushko.core.driver.WrappedDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class WebFactory {

    private final WrappedDriver driver;
    private final String webUrl;

    public WebFactory(WrappedDriver driver, String webUrl) {
        this.driver = driver;
        this.webUrl = webUrl;
    }

    public <T extends AbstractWebEntity> T newInstance(Class<T> clazz) {
        return this.newInstance(clazz, null);
    }

    public <T extends AbstractWebEntity> T newInstance(Class<T> clazz, WebElement root) {
        try {
            T webEntity = null;
            if (root != null) {
                Constructor<T> constructor = clazz.getConstructor(WebElement.class);
                webEntity = constructor.newInstance(root);
            } else {
                final var constructor = clazz.getConstructor();
                webEntity = constructor.newInstance();
                PageFactory.initElements(this.driver, webEntity);
            }
            webEntity.setDriver(this.driver);
            webEntity.setFactory(this);
            postProcess(webEntity);
            return webEntity;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(String.format("Page %s should have empty default constructor", clazz), e);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(String.format("Unable to create new instance of class %s", clazz), e);
        }
    }

    private void postProcess(AbstractWebEntity entity) {
        if (entity instanceof AbstractPage) {
            ((AbstractPage) entity).setBaseUrl(webUrl);
        }
    }

}
