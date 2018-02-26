package com.mdc.core;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SaveActionFactory {

    private SaveActionFactory() {
        // static only
    }

    public static boolean JAVA_AVAILABLE = false;

    public static boolean COMPILING_AVAILABLE = initCompilingAvailable();

    @NotNull
    static List<SaveActionManager> getSaveActionManagers() {
        List<SaveActionManager> managers = new ArrayList<>();
        managers.add(new SaveActionManager());
        if (JAVA_AVAILABLE) {
            managers.add(new com.mdc.core.java.SaveActionManager());
        }
        return managers;
    }

    private static boolean initCompilingAvailable() {
        try {
            return Class.forName("com.intellij.openapi.compiler.CompilerManager") != null;
        } catch (Exception e) {
            return false;
        }
    }

}
