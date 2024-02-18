package com.javiergs.codetown;

public class JgsNode {

  private final String name;
  private String color;
  private int x;
  private int y;

  public JgsNode() {
    this.name = null;
    this.color = null;
  }

  public JgsNode(String name) {
    this.name = name;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public void setPosition(int x, int y) {
    this.x = x;
    this.y = y;
  }

}
