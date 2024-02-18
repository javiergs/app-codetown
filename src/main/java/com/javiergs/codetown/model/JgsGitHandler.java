package com.javiergs.codetown.model;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class is responsible for downloading a Git repository
 *
 * @author javierGS
 * @version 1.0
 */
public class JgsGitHandler {

  private String uri;
  private String branch;
  private String folder;

  public void download() throws IOException {
    if (uri == null || branch == null || folder == null) {
      throw new IOException("The URI, branch and folder must be set");
    } else if (!uri.endsWith(".git")) {
      throw new IOException("The repository URL must end with .git");
    }
    File destinationDirectory = new File(this.folder);
    if (destinationDirectory.exists()) {
      deleteRecursive(destinationDirectory);
    }
    try {
      Git.cloneRepository()
        .setURI(uri)
        .setBranch(branch)
        .setDirectory(destinationDirectory)
        .call();
    } catch (GitAPIException e) {
      throw new IOException(e);
    }
  }

  public void delete() {
    deleteRecursive(new File(folder));
  }

  private void deleteRecursive(File file) {
    File[] contents = file.listFiles();
    if (contents != null) {
      for (File f : contents) {
        deleteRecursive(f);
      }
    }
    file.delete();
  }

  private ArrayList<File> getFilesRecursive(File path) throws IOException {
    ArrayList<File> files = new ArrayList<>();
    if (!(path.exists() && path.isDirectory() && path.canRead() && path.canRead())) {
      throw new IOException("The repository does not exist or is not a directory");
    }
    if (path.listFiles() != null)
      for (File item : path.listFiles())
        if (item.isDirectory()) {
          files.addAll(getFilesRecursive(item));
        } else {
          if (item.isFile() && item.getName().endsWith(".java") && !item.getName().startsWith(".")) {
            files.add(item);
          }
        }
    return files;
  }

  public ArrayList<File> getFiles() throws IOException {
    download();
    return getFilesRecursive(new File(folder));
  }

  public JgsGitHandler setURI(String uri) {
    this.uri = uri;
    return this;
  }

  public JgsGitHandler setBranch(String branch) {
    this.branch = branch;
    return this;
  }

  public JgsGitHandler setFolder(String folder) {
    this.folder = folder;
    return this;
  }

}

