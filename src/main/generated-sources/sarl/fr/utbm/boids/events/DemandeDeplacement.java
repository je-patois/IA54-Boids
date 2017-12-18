package fr.utbm.boids.events;

import fr.utbm.boids.BoidBody;
import fr.utbm.boids.environment.Obstacle;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Formule une demande de déplacement auprès d'un boid
 * @param boids - Liste des boids, permettant au boid de décider de son placement
 */
@SarlSpecification("0.6")
@SarlElementType(14)
@SuppressWarnings("all")
public class DemandeDeplacement extends Event {
  public Map<UUID, BoidBody> boids;
  
  public List<Obstacle> obstacles;
  
  public DemandeDeplacement(final Map<UUID, BoidBody> boids, final List<Obstacle> obstacles) {
    this.boids = boids;
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
   * Returns a String representation of the DemandeDeplacement event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("boids  = ").append(this.boids);
    result.append("obstacles  = ").append(this.obstacles);
    return result.toString();
  }
  
  @SyntheticMember
  private final static long serialVersionUID = -2273899086L;
}
