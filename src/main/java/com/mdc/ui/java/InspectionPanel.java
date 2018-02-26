package com.mdc.ui.java;

import com.mdc.model.Action;
import com.intellij.ui.IdeBorderFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

import static com.mdc.core.SaveActionFactory.JAVA_AVAILABLE;
import static com.mdc.model.Action.catchParameterCanBeFinal;
import static com.mdc.model.Action.explicitTypeCanBeDiamond;
import static com.mdc.model.Action.fieldCanBeFinal;
import static com.mdc.model.Action.finalPrivateMethod;
import static com.mdc.model.Action.foreachParameterCanBeFinal;
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

public class InspectionPanel {

    private static final String TEXT_TITLE_INSPECTIONS = "Java inspection and quick fix";

    private final Map<Action, JCheckBox> checkboxes;

    public InspectionPanel(final Map<Action, JCheckBox> checkboxes) {
        this.checkboxes = checkboxes;
    }

    public JPanel getPanel() {
        final JPanel panel = new JPanel();
        if (!JAVA_AVAILABLE) {
            return panel;
        }
        panel.setBorder(IdeBorderFactory.createTitledBorder(TEXT_TITLE_INSPECTIONS));
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(checkboxes.get(fieldCanBeFinal));
        panel.add(checkboxes.get(localCanBeFinal));
        panel.add(checkboxes.get(localParameterCanBeFinal));
        panel.add(checkboxes.get(foreachParameterCanBeFinal));
        panel.add(checkboxes.get(catchParameterCanBeFinal));
        panel.add(checkboxes.get(unqualifiedFieldAccess));
        panel.add(checkboxes.get(unqualifiedMethodAccess));
        panel.add(checkboxes.get(unqualifiedStaticMemberAccess));
        panel.add(checkboxes.get(missingOverrideAnnotation));
        panel.add(checkboxes.get(useBlocks));
        panel.add(checkboxes.get(unnecessaryThis));
        panel.add(checkboxes.get(finalPrivateMethod));
        panel.add(checkboxes.get(unnecessaryFinalOnLocalVariableOrParameter));
        panel.add(checkboxes.get(explicitTypeCanBeDiamond));
        panel.add(checkboxes.get(suppressAnnotation));
        panel.add(checkboxes.get(unnecessarySemicolon));
        panel.add(Box.createHorizontalGlue());
        panel.setMinimumSize(new Dimension(Short.MAX_VALUE, 0));
        return panel;
    }

}
