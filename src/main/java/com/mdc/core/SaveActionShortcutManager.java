package com.mdc.core;

import com.mdc.model.Storage;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;

import java.util.List;

import static com.mdc.core.SaveActionManager.LOGGER;
import static com.mdc.model.Action.activateOnShortcut;
import static com.intellij.openapi.actionSystem.CommonDataKeys.PSI_FILE;

public class SaveActionShortcutManager extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        LOGGER.debug("Running SaveActionShortcutManager on " + event);
        PsiFile psiFile = event.getData(PSI_FILE);
        List<SaveActionManager> saveActionManagers = SaveActionFactory.getSaveActionManagers();
        Project project = event.getProject();
        for (SaveActionManager saveActionManager : saveActionManagers) {
            Storage storage = saveActionManager.getStorage(project);
            if (storage.isEnabled(activateOnShortcut)) {
                saveActionManager.checkAndProcessPsiFile(project, psiFile);
            }
        }
    }

}
