package de.playground.functional_folders;

import lombok.Getter;

import java.util.List;

@Getter
public class Folder {
  private String name;
  private boolean hidden;
  private Folder parent;
  private List<Folder> children;

  public Folder(String name, boolean hidden, Folder parent, List<Folder> children) {
    this.name = name;
    this.hidden = hidden;
    this.parent = parent;
    if (children != null) {
      this.children = children;
    } else {
      this.children = List.of();
    }
  }

  // Recursive function to set hidden property on a folder and its children
  public void setHiddenRecursively(boolean hidden) {
    this.hidden = hidden;
    for (Folder child : children) {
      child.setHiddenRecursively(hidden);
    }
  }
}