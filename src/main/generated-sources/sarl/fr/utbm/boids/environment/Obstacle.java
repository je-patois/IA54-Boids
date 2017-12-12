package fr.utbm.boids.environment;

import fr.utbm.boids.util.LineTool;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.util.List;
import javafx.scene.shape.Polygon;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(9)
@SuppressWarnings("all")
public class Obstacle {
  private List<LineTool> lines;
  
  private Polygon shape;
  
  public Obstacle() {
  }
  
  public Obstacle(final Obstacle obstacle) {
    this.lines = obstacle.lines;
    this.shape = obstacle.shape;
  }
  
  public Obstacle(final List<LineTool> lines, final Polygon shape) {
    this.lines = lines;
    this.shape = shape;
  }
  
  @Pure
  public List<LineTool> getLines() {
    return this.lines;
  }
  
  public void setLines(final List<LineTool> lines) {
    this.lines = lines;
  }
  
  @Pure
  public Polygon getPolygon() {
    return this.shape;
  }
  
  public void setPolygon(final Polygon polygon) {
    this.setPolygon(polygon);
  }
  
  @Pure
  public String toString() {
    String _string = this.lines.toString();
    return ("Obstacle: LineTool: " + _string);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public boolean equals(final Object obj) {
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    return result;
  }
}
