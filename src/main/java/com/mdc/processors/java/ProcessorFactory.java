package com.mdc.processors.java;

import com.mdc.model.Storage;
import com.mdc.processors.Processor;
import com.intellij.codeInspection.ExplicitTypeCanBeDiamondInspection;
import com.intellij.codeInspection.localCanBeFinal.LocalCanBeFinal;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.siyeh.ig.classlayout.FinalPrivateMethodInspection;
import com.siyeh.ig.inheritance.MissingOverrideAnnotationInspection;
import com.siyeh.ig.maturity.SuppressionAnnotationInspection;
import com.siyeh.ig.style.ControlFlowStatementWithoutBracesInspection;
import com.siyeh.ig.style.FieldMayBeFinalInspection;
import com.siyeh.ig.style.UnnecessaryFinalOnLocalVariableOrParameterInspection;
import com.siyeh.ig.style.UnnecessarySemicolonInspection;
import com.siyeh.ig.style.UnnecessaryThisInspection;
import com.siyeh.ig.style.UnqualifiedFieldAccessInspection;
import com.siyeh.ig.style.UnqualifiedMethodAccessInspection;
import com.siyeh.ig.style.UnqualifiedStaticUsageInspection;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.mdc.model.Action.explicitTypeCanBeDiamond;
import static com.mdc.model.Action.fieldCanBeFinal;
import static com.mdc.model.Action.finalPrivateMethod;
import static com.mdc.model.Action.localCanBeFinal;
import static com.mdc.model.Action.localParameterCanBeFinal;
import static com.mdc.model.Action.missingOverrideAnnotation;
import static com.mdc.model.Action.suppressAnnotation;
import static com.mdc.model.Action.unnecessaryFinalOnLocalVariableOrParameter;
import static com.mdc.model.Action.unnecessarySemicolon;
import static com.mdc.model.Action.unnecessaryThis;
import static com.mdc.model.Action.unqualifiedFieldAccess;
import static com.mdc.model.Action.unqualifiedMethodAccess;
import static com.mdc.model.Action.unqualifiedStaticMemberAccess;
import static com.mdc.model.Action.useBlocks;

public enum ProcessorFactory {

    INSTANCE;

    public List<Processor> getSaveActionsProcessors(final Project project, final PsiFile psiFile, final Storage storage) {
        final List<Processor> processors = new ArrayList<>();
        // Add stuff
        processors.add(getLocalCanBeFinalProcessor(project, psiFile, storage));
        processors.add(getLocalParameterCanBeFinalProcessor(project, psiFile, storage));
        processors.add(getForeachParameterCanBeFinalProcessor(project, psiFile, storage));
        processors.add(getCatchParameterCanBeFinalProcessor(project, psiFile, storage));
        processors.add(getUnqualifiedFieldAccessProcessor(project, psiFile, storage));
        processors.add(getUnqualifiedMethodAccessProcessor(project, psiFile, storage));
        processors.add(getUnqualifiedStaticUsageInspectionProcessor(project, psiFile, storage));
        processors.add(getFieldMayBeFinalProcessor(project, psiFile, storage));
        processors.add(getMissingOverrideAnnotationProcessor(project, psiFile, storage));
        processors.add(getControlFlowStatementWithoutBracesProcessor(project, psiFile, storage));
        // Removes stuff
        processors.add(getExplicitTypeCanBeDiamondProcessor(project, psiFile, storage));
        processors.add(getUnnecessaryThisProcessor(project, psiFile, storage));
        processors.add(getSuppressionAnnotationProcessor(project, psiFile, storage));
        processors.add(getFinalPrivateMethodProcessor(project, psiFile, storage));
        processors.add(getUnnecessarySemicolonProcessor(project, psiFile, storage));
        processors.add(getUnnecessaryFinalOnLocalVariableOrParameterProcessor(project, psiFile, storage));
        return processors;
    }

    @NotNull
    private InspectionProcessor getLocalCanBeFinalProcessor(
            final Project project, final PsiFile psiFile, final Storage storage) {

        final LocalCanBeFinal localCanBeFinalImpl = new LocalCanBeFinal();
        localCanBeFinalImpl.REPORT_CATCH_PARAMETERS = false;
        localCanBeFinalImpl.REPORT_FOREACH_PARAMETERS = false;
        localCanBeFinalImpl.REPORT_PARAMETERS = false;

        return new InspectionProcessor(
                project, psiFile, storage, localCanBeFinal, localCanBeFinalImpl);
    }

    @NotNull
    private InspectionProcessor getLocalParameterCanBeFinalProcessor(
            final Project project, final PsiFile psiFile, final Storage storage) {

        LocalCanBeFinal localCanBeFinal = new LocalCanBeFinal();
        localCanBeFinal.REPORT_CATCH_PARAMETERS = false;
        localCanBeFinal.REPORT_FOREACH_PARAMETERS = false;
        localCanBeFinal.REPORT_VARIABLES = false;


        return new InspectionProcessor(
                project, psiFile, storage, localParameterCanBeFinal, localCanBeFinal);
    }

    @NotNull
    private InspectionProcessor getForeachParameterCanBeFinalProcessor(
            final Project project, final PsiFile psiFile, final Storage storage) {

        final LocalCanBeFinal localCanBeFinal = new LocalCanBeFinal();
        localCanBeFinal.REPORT_CATCH_PARAMETERS = false;
        localCanBeFinal.REPORT_PARAMETERS = false;
        localCanBeFinal.REPORT_VARIABLES = false;

        return new InspectionProcessor(
                project, psiFile, storage, localParameterCanBeFinal, localCanBeFinal);
    }

    @NotNull
    private InspectionProcessor getCatchParameterCanBeFinalProcessor(
            final Project project, final PsiFile psiFile, final Storage storage) {

        final LocalCanBeFinal localCanBeFinal = new LocalCanBeFinal();
        localCanBeFinal.REPORT_FOREACH_PARAMETERS = false;
        localCanBeFinal.REPORT_PARAMETERS = false;
        localCanBeFinal.REPORT_VARIABLES = false;

        return new InspectionProcessor(
                project, psiFile, storage, localParameterCanBeFinal, localCanBeFinal);
    }

    @NotNull
    private InspectionProcessor getUnqualifiedFieldAccessProcessor(
            final Project project, final PsiFile psiFile, final Storage storage) {
        return new InspectionProcessor(
                project, psiFile, storage, unqualifiedFieldAccess, new UnqualifiedFieldAccessInspection());
    }

    @NotNull
    private InspectionProcessor getUnqualifiedMethodAccessProcessor(
            final Project project, final PsiFile psiFile, final Storage storage) {
        return new InspectionProcessor(
                project, psiFile, storage, unqualifiedMethodAccess, new UnqualifiedMethodAccessInspection());
    }

    @NotNull
    private InspectionProcessor getUnqualifiedStaticUsageInspectionProcessor(
            final Project project, final PsiFile psiFile, final Storage storage) {
        final UnqualifiedStaticUsageInspection inspection = new UnqualifiedStaticUsageInspection();
        inspection.m_ignoreStaticFieldAccesses = false;
        inspection.m_ignoreStaticMethodCalls = false;
        inspection.m_ignoreStaticAccessFromStaticContext = false;
        return new InspectionProcessor(
                project, psiFile, storage, unqualifiedStaticMemberAccess, inspection);
    }

    @NotNull
    private InspectionProcessor getFieldMayBeFinalProcessor(
            final Project project, final PsiFile psiFile, final Storage storage) {
        return new InspectionProcessor(
                project, psiFile, storage, fieldCanBeFinal, new FieldMayBeFinalInspection());
    }

    @NotNull
    private InspectionProcessor getMissingOverrideAnnotationProcessor(
            final Project project, final PsiFile psiFile, final Storage storage) {
        final MissingOverrideAnnotationInspection inspection = new MissingOverrideAnnotationInspection();
        inspection.ignoreObjectMethods = false;
        return new InspectionProcessor(
                project, psiFile, storage, missingOverrideAnnotation, inspection);
    }

    @NotNull
    private InspectionProcessor getControlFlowStatementWithoutBracesProcessor(
            final Project project, final PsiFile psiFile, final Storage storage) {
        return new InspectionProcessor(
                project, psiFile, storage, useBlocks, new ControlFlowStatementWithoutBracesInspection());
    }

    @NotNull
    private InspectionProcessor getExplicitTypeCanBeDiamondProcessor(
            final Project project, final PsiFile psiFile, final Storage storage) {
        return new InspectionProcessor(
                project, psiFile, storage, explicitTypeCanBeDiamond, new ExplicitTypeCanBeDiamondInspection());
    }

    @NotNull
    private InspectionProcessor getUnnecessaryThisProcessor(
            final Project project, final PsiFile psiFile, final Storage storage) {
        return new InspectionProcessor(
                project, psiFile, storage, unnecessaryThis, new UnnecessaryThisInspection());
    }

    @NotNull
    private InspectionProcessor getSuppressionAnnotationProcessor(
            final Project project, final PsiFile psiFile, final Storage storage) {
        return new InspectionProcessor(
                project, psiFile, storage, suppressAnnotation, new SuppressionAnnotationInspection());
    }

    @NotNull
    private InspectionProcessor getFinalPrivateMethodProcessor(
            final Project project, final PsiFile psiFile, final Storage storage) {
        return new InspectionProcessor(
                project, psiFile, storage, finalPrivateMethod, new FinalPrivateMethodInspection());
    }

    @NotNull
    private InspectionProcessor getUnnecessarySemicolonProcessor(
            final Project project, final PsiFile psiFile, final Storage storage) {
        return new InspectionProcessor(
                project, psiFile, storage, unnecessarySemicolon, new UnnecessarySemicolonInspection());
    }

    @NotNull
    private InspectionProcessor getUnnecessaryFinalOnLocalVariableOrParameterProcessor(
            final Project project, final PsiFile psiFile, final Storage storage) {
        return new InspectionProcessor(
                project, psiFile, storage, unnecessaryFinalOnLocalVariableOrParameter,
                new UnnecessaryFinalOnLocalVariableOrParameterInspection());
    }

}
