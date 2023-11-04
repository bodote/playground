package de.playground.folderFunctional;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class FolderUtilTest {
  @Test
  void testFolder() {
    Folder f6 = new Folder("the 6th", false, null, null);
    Folder f5 = new Folder("the 5th", false, null, null);
    Folder f4 = new Folder("the 4th", false, null, null);
    Folder f3 = new Folder("the 3rd", false, null, List.of(f5));
    Folder f2 = new Folder("the 2nd", false, null, List.of(f3));
    Folder f1 = new Folder("_the first", false, null, List.of(f2, f4));
    List<Folder> folders = List.of(f1, f2, f3, f4, f5, f6);
    FolderUtil.updateHiddenProperty(folders, true); // or false, depending on what you want to set
    for (Folder folder : folders) {
      if (!folder.getName().equals("the 6th")) {
        assertThat(folder.isHidden()).isTrue();
      }
    }

  }

}