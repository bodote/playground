package de.playground.folderFunctional;

import java.util.List;

public class FolderUtil {
  private FolderUtil() {
  }

  public static void updateHiddenProperty(List<Folder> folders, boolean newHiddenValue) {
    for (Folder folder : folders) {
      if (folder.getName().startsWith("_")) {
        folder.setHiddenRecursively(newHiddenValue);
      }
    }
  }
}

// Assuming you have a List<Folder> folders that you want to update:
