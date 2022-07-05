package com.pn.ui.page;

import com.github.annushko.core.ui.AbstractPage;
import com.pn.ui.fragment.CatalogFragment;
import com.pn.ui.fragment.HeaderFragment;

abstract class AbstractPnComUaPage extends AbstractPage {

    public HeaderFragment header() {
        return factory.newInstance(HeaderFragment.class);
    }

    public CatalogFragment catalog() {
        return factory.newInstance(CatalogFragment.class);
    }

}
