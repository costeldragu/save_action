package com.mdc.core.java;

import com.mdc.model.EpfStorage;
import com.mdc.model.Storage;
import com.mdc.processors.Processor;
import com.mdc.processors.Processor.ProcessorComparator;
import com.mdc.processors.java.ProcessorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;

import java.util.List;

import static java.util.Collections.sort;

/**
 * Event handler class, instanciated by {@link com.mdc.core.java.Component}. The
 * {@link #getSaveActionsProcessors(Project, PsiFile)} returns the java specific processors.
 */
public class SaveActionManager extends com.mdc.core.SaveActionManager {

    @Override
    protected List<Processor> getSaveActionsProcessors(Project project, PsiFile psiFile) {
        Storage storage = getStorage(project);
        List<Processor> processors = ProcessorFactory.INSTANCE.getSaveActionsProcessors(project, psiFile, storage);
        sort(processors, new ProcessorComparator());
        return processors;
    }

    @Override
    protected Storage getStorage(Project project) {
        Storage defaultStorage = super.getStorage(project);
        return EpfStorage.INSTANCE.getStorageOrDefault(defaultStorage.getConfigurationPath(), defaultStorage);
    }

}