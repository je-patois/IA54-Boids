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
 * @param obstacles - Liste des obstacles avec les données relatives à leur traitement
 */
@SarlSpecification("0.6")
@SarlElementType(14)
@SuppressWarnings("all")
public class StartPosition extends Event {
  public List<Obstacle> obstacles;
  
  public StartPosition(final List<Obstacle> obstacles) {
    this.obstacles = obstacles;
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
  
  /**
   * Returns a String representation of the StartPosition event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("obstacles  = ").append(this.obstacles);
    return result.toString();
  }
  
  @SyntheticMember
  private final static long serialVersionUID = -1015182199L;
}
