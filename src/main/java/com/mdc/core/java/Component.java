package com.mdc.core.java;

import com.mdc.core.SaveActionFactory;
import com.mdc.core.SaveActionManager;
import org.jetbrains.annotations.NotNull;

/**
 * The plugin entry class for java based ide. This is not a singleton, the parent is also instanciated.
 */
public class Component extends com.mdc.core.Component {

    private static final String COMPONENT_NAME = "Save Actions Java";

    public Component() {
        SaveActionFactory.JAVA_AVAILABLE = true;
    }

    @Override
    @NotNull
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    @NotNull
    @Override
    protected SaveActionManager getSaveActionManager() {
        return new com.mdc.core.java.SaveActionManager();
    }

}
