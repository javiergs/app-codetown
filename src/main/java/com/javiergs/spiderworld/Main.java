package com.javiergs.spiderworld;

import processing.core.PApplet;


public class Main extends PApplet {

  private int x = 200;
  private int y = 200;
  private int radius = 25;
  private int vx = 1;
  private int vy = 2;
  private int c = color(255);

  public void settings() {
    size(400, 400);
  }


  public void draw() {
    background(0);
    fill(c);
    ellipse(x, y, radius*2, radius*2);
    x += vx;
    y += vy;
    if (x<radius || x>width-radius) vx = -vx;
    if (y<radius || y>height-radius) vy = -vy;
  }

  public void keyPressed() {
    if (key == ' ') c = color(random(255), random(255), random(255));
  }

  public void mousePressed() {
    float distance = dist(mouseX, mouseY, x, y);
    if (distance < radius) {
      println("Circle clicked!");
    }
  }

  public static void main(String[] args) {
    PApplet.main("com.javiergs.spiderworld.pro");
  }

}

