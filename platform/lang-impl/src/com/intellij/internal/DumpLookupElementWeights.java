package com.intellij.internal;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupManager;
import com.intellij.codeInsight.lookup.impl.LookupImpl;
import com.intellij.codeInsight.completion.CompletionLookupArranger;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.DumbAware;

import javax.swing.*;
import java.util.Arrays;

/**
 * @author peter
 */
public class DumpLookupElementWeights extends AnAction implements DumbAware {

  public void actionPerformed(final AnActionEvent e) {
    final Editor editor = e.getData(PlatformDataKeys.EDITOR);
    dumpLookupElementWeights((LookupImpl)LookupManager.getActiveLookup(editor));
  }

  @Override
  public void update(final AnActionEvent e) {
    final Presentation presentation = e.getPresentation();
    final Editor editor = e.getData(PlatformDataKeys.EDITOR);
    presentation.setEnabled(editor != null && LookupManager.getActiveLookup(editor) != null);
  }

  public static void dumpLookupElementWeights(final LookupImpl lookup) {
    final ListModel model = lookup.getList().getModel();
    final int count = lookup.getPreferredItemsCount();
    for (int i = 0; i < model.getSize(); i++) {
      final LookupElement item = (LookupElement)model.getElementAt(i);
      System.out.println(item.getLookupString() + Arrays.toString(item.getUserData(CompletionLookupArranger.WEIGHT)));
      if (i == count - 1) {
        System.out.println("------------");
      }
    }
  }

}