package com.mdc.processors.java;

import com.mdc.model.Action;
import com.mdc.model.Storage;
import com.mdc.processors.Processor;
import com.intellij.codeInspection.GlobalInspectionContext;
import com.intellij.codeInspection.InspectionEngine;
import com.intellij.codeInspection.InspectionManager;
import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.QuickFix;
import com.intellij.codeInspection.ex.InspectionToolWrapper;
import com.intellij.codeInspection.ex.LocalInspectionToolWrapper;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.IndexNotReadyException;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;

import java.util.List;

import static com.mdc.core.SaveActionManager.LOGGER;
import static com.mdc.processors.ProcessorMessage.toStringBuilder;

class InspectionProcessor implements Processor {

    private final Project project;

    private final PsiFile psiFile;

    private final Storage storage;

    private final Action action;

    private final LocalInspectionTool inspectionTool;

    InspectionProcessor(Project project, PsiFile psiFile, Storage storage, Action action, LocalInspectionTool inspectionTool) {
        this.project = project;
        this.psiFile = psiFile;
        this.storage = storage;
        this.action = action;
        this.inspectionTool = inspectionTool;
    }

    @Override
    public void run() {
        if (storage.isEnabled(action)) {
            ApplicationManager.getApplication()
                    .invokeLater(() -> new InspectionWriteQuickFixesAction(project).execute());
        }
    }

    @Override
    public int order() {
        return 0;
    }

    @Override
    public String toString() {
        return toStringBuilder(inspectionTool.getID(), storage.isEnabled(action));
    }

    private class InspectionWriteQuickFixesAction extends WriteCommandAction.Simple {

        private InspectionWriteQuickFixesAction(Project project, PsiFile... files) {
            super(project, files);
        }

        @Override
        protected void run() {
            InspectionManager inspectionManager = InspectionManager.getInstance(project);
            GlobalInspectionContext context = inspectionManager.createNewGlobalContext(false);
            InspectionToolWrapper toolWrapper = new LocalInspectionToolWrapper(inspectionTool);
            List<ProblemDescriptor> problemDescriptors;
            try {
                problemDescriptors = InspectionEngine.runInspectionOnFile(psiFile, toolWrapper, context);
            } catch (IndexNotReadyException exception) {
                return;
            }
            for (ProblemDescriptor problemDescriptor : problemDescriptors) {
                QuickFix[] fixes = problemDescriptor.getFixes();
                if (fixes != null) {
                    writeQuickFixes(problemDescriptor, fixes);
                }
            }
        }

        private void writeQuickFixes(ProblemDescriptor problemDescriptor, QuickFix[] fixes) {
            for (QuickFix fix : fixes) {
                if (fix != null) {
                    try {
                        fix.applyFix(project, problemDescriptor);
                    } catch (Exception e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                }
            }
        }
    }

}
