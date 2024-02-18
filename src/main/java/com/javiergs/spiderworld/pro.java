
import processing.core.PApplet;
import java.util.Arrays;

public class pro extends PApplet {

  final static int BG = -1;
  final static int FG = 0;
  final static short DOT_DIST = 50, DOT_OPAC = 60;
  final static float DOT_SPEED = 3, FPS = 60, BOLD = 1;
  final static short NUM_DOTS = 5000;
  final static JgsPoint[] points = new JgsPoint[NUM_DOTS];
  final static String GFX = P2D;  // Use P2D or JAVA2D

  int dotConnects;

  public static void main(String[] args) {
    String[] processingArgs = {"MySketch"};
    pro mySketch = new pro();
    PApplet.runSketch(processingArgs, mySketch);
  }

  public void settings() {
    size(800, 800);
  }

  public void setup() {
    frameRate(FPS);
    noSmooth();
    strokeWeight(BOLD);
    stroke(FG, DOT_OPAC);

    for (int i = 0; i != NUM_DOTS;
         points[i++] = new JgsPoint(random(.5f, DOT_SPEED)))
      ;
  }
/*
  public void draw() {
    background(BG);
    Arrays.sort(points);
    final float maxDist = map(mouseX, 0, width, 1, DOT_DIST);
    dotConnects = 0;

    for (int i = 0; i != NUM_DOTS; ) {
      final JgsPoint p = points[i++];

      p.update();
      p.drawPoint();

      nearestNeighbors(p, i, maxDist);
    }


    surface.setTitle(" Nearest neighbor"
      + " | fps " + String.format("%5.2f", frameRate)
      + " | maxDist " + String.format("%5.2f", maxDist)
      + " | numPoints " + NUM_DOTS
      + " | numConnects " + dotConnects);
  }

  void nearestNeighbors(JgsPoint p1, int i, float maxDist) {
    final float maxDistSq = maxDist * maxDist;
    final float x = p1.x, y = p1.y;

    for (int j = i; j != NUM_DOTS; ) {
      final JgsPoint p2 = points[j++];

      final float dx = p2.x - x;
      if (dx > maxDist) return;

      final float dy = abs(p2.y - y);
      if (dy > maxDist) continue;

      if (dx * dx + dy * dy < maxDistSq) {
        p1.drawLine(p2);
        dotConnects++;
      }
    }
  }
}


