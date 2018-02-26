package com.mdc.processors;

import com.mdc.model.Storage;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.compiler.CompilerManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;

import static com.mdc.core.SaveActionManager.LOGGER;
import static com.mdc.model.Action.compile;
import static com.mdc.processors.ProcessorMessage.toStringBuilder;

class CompileProcessor implements Processor {

    private static final String ID = "Compile";

    private final Project project;

    private final PsiFile file;

    private final Storage storage;

    CompileProcessor(Project project, PsiFile file, Storage storage) {
        this.project = project;
        this.file = file;
        this.storage = storage;
    }

    @Override
    public void run() {
        if (storage.isEnabled(compile)) {
            ApplicationManager.getApplication().invokeLater(() -> {
                try {
                    CompilerManager.getInstance(project).compile(new VirtualFile[]{file.getVirtualFile()}, null);
                } catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                }
            });
        }
    }

    @Override
    public int order() {
        return 3;
    }

    @Override
    public String toString() {
        return toStringBuilder(ID, storage.isEnabled(compile));
    }

}
