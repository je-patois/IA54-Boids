package fr.utbm.boids.events;

import fr.utbm.boids.environment.Obstacle;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import java.util.List;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Initialise les positions intiales des boids
 * @param height - Position du boid en y
 * @param width - Position du boid en x
 * @param obstacles - Liste des obstacles avec les données relatives à leur traitement
 */
@SarlSpecification("0.6")
@SarlElementType(14)
@SuppressWarnings("all")
public class StartPosition extends Event {
  public int height;
  
  public int width;
  
  public List<Obstacle> obstacles;
  
  public StartPosition(final int h, final int l, final List<Obstacle> obstacles) {
    this.height = h;
    this.width = l;
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
    StartPosition other = (StartPosition) obj;
    if (other.height != this.height)
      return false;
    if (other.width != this.width)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + this.height;
    result = prime * result + this.width;
    return result;
  }
  
  /**
   * Returns a String representation of the StartPosition event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("height  = ").append(this.height);
    result.append("width  = ").append(this.width);
    result.append("obstacles  = ").append(this.obstacles);
    return result.toString();
  }
  
  @SyntheticMember
  private final static long serialVersionUID = -3205458700L;
}
