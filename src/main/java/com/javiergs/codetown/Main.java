package com.javiergs.codetown;

import com.javiergs.codetown.model.JgsGitHandler;
import com.javiergs.codetown.data.JgsFileData;
import com.javiergs.codetown.model.JgsFileParser;
import com.javiergs.codetown.ui.JgsTownPanel;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 *  This class creates a diagram of a Java project from a GitHub repository
 *  It uses the JGit library to download the repository, the JavaParser library to parse the files, and
 *  the mxGraph library for creating the diagram
 *
 *  @version 1.0
 *  @author javiergs
 */
public class Main extends JFrame implements ActionListener {

  private JPanel panel;
  private JTextField repositoryField;
  private JButton button;
  private String extractRepositoryName(String uri) {
    int startIndex = uri.lastIndexOf('/') + 1;
    int endIndex = uri.length()-4;
    return uri.substring(startIndex, endIndex);
  }

  private ArrayList<JgsFileData> getData (String repository, String branch, String folder) {
    // download
    JgsGitHandler jgsGitHandler = new JgsGitHandler()
      .setURI(repository)
      .setBranch(branch)
      .setFolder(extractRepositoryName(folder));
    ArrayList<File> files = null;
    try {
      files = jgsGitHandler.getFiles();
    } catch (IOException e) {
      LoggerFactory.getLogger(Main.class).error("error connecting to repository.\n" + e);
    }
    // parse
    if (files == null)
      return null;
    ArrayList<JgsFileData> classes = new ArrayList<>();
    for (File file : files) {
      try {
        classes.add(JgsFileParser.parseJavaFile(file));
      } catch (Exception e) {
        LoggerFactory.getLogger(Main.class).error("error parsing file " + file);
      }
    }
    // delete
    jgsGitHandler.delete();
    return classes;
  }

  public Main(String title) {
    super(title);
    // download, parse and delete
    String repository = "https://github.com/javiergs/Temporal.git";
    ArrayList<JgsFileData> classes = getData(
      repository,
      "main",
      extractRepositoryName(repository)
    );
    panel = new JgsTownPanel(classes);
    // gui
    setLayout(new BorderLayout());
    add(panel, BorderLayout.CENTER);
    // input panel
    JPanel inputPanel = new JPanel();
    inputPanel.setLayout(new FlowLayout());
    repositoryField = new JTextField(repository, 40);
    button = new JButton("Load");
    inputPanel.add(repositoryField);
    inputPanel.add(button);
    add(inputPanel, BorderLayout.NORTH);
    // action
    button.addActionListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
      ArrayList<JgsFileData> newClasses = getData(
        repositoryField.getText(),
        "main",
        extractRepositoryName(repositoryField.getText())
      );

      JgsTownPanel p = new JgsTownPanel(newClasses);
      // remove panel and change for p
      remove(panel);
      add(p, BorderLayout.CENTER);
      panel = p;
      revalidate();
      repaint();
  }
  public static void main(String[] args) {
    Main frame = new Main("JGS Lab | GitHub Repository Viewer");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800, 600);
    frame.setVisible(true);
  }

}