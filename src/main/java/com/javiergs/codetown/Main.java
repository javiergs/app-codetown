package com.javiergs.codetown;

import com.javiergs.codetown.data.JgsGitHandler;
import com.javiergs.codetown.data.JgsFileData;
import com.javiergs.codetown.data.JgsParser;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

  public static final String REPOSITORY = "Temporal";
  public static final String USERID = "javiergs";
  public static final String FOLDER = "/tmp/" + REPOSITORY + "/";

  public static void main(String[] args) {

    // download
    JgsGitHandler jgsGitHandler = new JgsGitHandler()
      .setURI("https://github.com/" + USERID + "/" + REPOSITORY + ".git")
      .setBranch("main")
      .setFolder(FOLDER);
    ArrayList<File> files = null;
    try {
      files = jgsGitHandler.getFiles();
    } catch (IOException e) {
      LoggerFactory.getLogger(Main.class).error("error connecting to " + USERID + "/" + REPOSITORY + ".git");
    }

    // parse
    if (files == null) return;
    ArrayList<JgsFileData> classes = new ArrayList<>();
    for (File file : files) {
      try {
        classes.add(JgsParser.parseJavaFile(file));
      } catch (Exception e) {
        LoggerFactory.getLogger(Main.class).error("error parsing file " + file);
      }
    }

    // delete
    jgsGitHandler.delete();

    // visualize
    JgsGitViewer frame = new JgsGitViewer(classes);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(500, 500);
    frame.setVisible(true);
  }

}