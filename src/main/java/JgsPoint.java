/*
import processing.core.*;

public class JgsPoint extends PVector implements Comparable<JgsPoint> {

  final PVector dir = new PVector();
  final float spd;

  JgsPoint(float speed) {
    spd = speed;
   // init();
  }

  @Override
  public int compareTo(JgsPoint o) {
    return 0;
  }
/*
  void init() {
    final float a = Math.random(Math.TWO_PI);
    dir.set( spd*Math.cos(a), spd*MAth.sin(a), 0 );
    set( (int) Math.random(width), (int) Math.random(height), 0 );
  }

  void update() {
    add(dir);
    if ( isOffScreen() )    init();
  }

  boolean isOffScreen() {
    return x < 0 | x >= width | y < 0 | y >= height;
  }

  void drawPoint() {
    point(x, y);
  }

  void drawLine(JgsPoint other) {
    line(x, y, other.x, other.y);
  }

  public  int compareTo(JgsPoint other) {
    return (int) Math.signum(x - other.x);
  }
}
 */
