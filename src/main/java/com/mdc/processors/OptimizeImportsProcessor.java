package com.mdc.processors;

import com.mdc.model.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;

import static com.mdc.core.SaveActionManager.LOGGER;
import static com.mdc.model.Action.organizeImports;
import static com.mdc.processors.ProcessorMessage.toStringBuilder;

class OptimizeImportsProcessor extends com.intellij.codeInsight.actions.OptimizeImportsProcessor implements Processor {

    private static final String ID = "OptimizeImports";

    private final Storage storage;

    OptimizeImportsProcessor(Project project, PsiFile file, Storage storage) {
        super(project, file);
        this.storage = storage;
    }

    @Override
    public void run() {
        if (storage.isEnabled(organizeImports)) {
            try {
                super.run();
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public int order() {
        return 1;
    }

    @Override
    public String toString() {
        return toStringBuilder(ID, storage.isEnabled(organizeImports));
    }

}
