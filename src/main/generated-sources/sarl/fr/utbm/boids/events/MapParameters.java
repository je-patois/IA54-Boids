package fr.utbm.boids.events;

import fr.utbm.boids.environment.Obstacle;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import java.util.List;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * event MapParameters {
 * var mapWidth : int
 * var mapHeight : int
 * 
 * new(width : int, height : int) {
 * this.mapWidth = width
 * this.mapHeight = height
 * }
 * }
 */
@SarlSpecification("0.6")
@SarlElementType(14)
@SuppressWarnings("all")
public class MapParameters extends Event {
  public int mapWidth;
  
  public int mapHeight;
  
  public List<Obstacle> obstacles;
  
  public MapParameters(final int width, final int height, final List<Obstacle> obstacles) {
    this.mapWidth = width;
    this.mapHeight = height;
    this.obstacles = obstacles;
  }
  
  @Override
  @Pure
  @SyntheticMember
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    MapParameters other = (MapParameters) obj;
    if (other.mapWidth != this.mapWidth)
      return false;
    if (other.mapHeight != this.mapHeight)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + this.mapWidth;
    result = prime * result + this.mapHeight;
    return result;
  }
  
  /**
   * Returns a String representation of the MapParameters event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("mapWidth  = ").append(this.mapWidth);
    result.append("mapHeight  = ").append(this.mapHeight);
    result.append("obstacles  = ").append(this.obstacles);
    return result.toString();
  }
  
  @SyntheticMember
  private final static long serialVersionUID = -1342368396L;
}
