package com.javiergs.codetown.data;

import java.util.ArrayList;
import java.util.List;

public class JgsFileData {

  private final ArrayList<String> interfaces;
  private final ArrayList<String> attributes;
  private final ArrayList<String> methods;
  private final ArrayList<String> associations;
  private String name;
  private String parent;
  private int linesOfCode;

  public JgsFileData() {
    this.name = null;
    this.linesOfCode = 0;
    this.parent = "";
    this.interfaces = new ArrayList<>();
    this.attributes = new ArrayList<>();
    this.methods = new ArrayList<>();
    this.associations = new ArrayList<>();
  }

  public String toString() {
    return "Class: " + name + "{ \n" +
      "\tParent: " + parent + "\n" +
      "\tInterfaces: " + interfaces + "\n" +
      "\tAttributes: " + attributes + "\n" +
      "\tMethods: " + methods + "\n" +
      "\tAssociations: " + associations + "\n" +
      "\tLines of Code: " + linesOfCode + "\n}";
  }

  public boolean hasMain() {
    return methods.contains("main");
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getLinesOfCode() {
    return linesOfCode;
  }

  public void setLinesOfCode(int linesOfCode) {
    this.linesOfCode = linesOfCode;
  }

  public String getParent() {
    return parent;
  }

  public void setParent(String parent) {
    this.parent = parent;
  }

  public List<String> getAttributes() {
    return attributes;
  }

  public List<String> getAssociations() {
    return associations;
  }

  public ArrayList<String> getInterfaces() {
    return interfaces;
  }

  public List<String> getMethods() {
    return methods;
  }

  public void addInterface(String s) {
    if (s == null) return;
    interfaces.add(s);
  }

  public void addAttribute(String s) {
    if (s == null) return;
    attributes.add(s);
  }

  public void addMethod(String s) {
    if (s == null) return;
    methods.add(s);
  }

  public void addAssociation(String s) {
    if (s == null) return;
    associations.add(s);
  }

}