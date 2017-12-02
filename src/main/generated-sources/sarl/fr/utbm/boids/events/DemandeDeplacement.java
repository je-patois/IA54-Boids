package fr.utbm.boids.events;

import fr.utbm.boids.Vector;
import fr.utbm.boids.agents.Boid;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import java.util.Collection;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(14)
@SuppressWarnings("all")
public class DemandeDeplacement extends Event {
  public Collection<Boid> otherBoids;
  
  public DemandeDeplacement(final Vector pos, final Collection<Boid> otherBoids) {
    this.otherBoids = otherBoids;
  }
  
  public DemandeDeplacement(final int x, final int y, final Collection<Boid> otherBoids) {
    this.otherBoids = otherBoids;
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
    result.append("otherBoids  = ").append(this.otherBoids);
    return result.toString();
  }
  
  @SyntheticMember
  private final static long serialVersionUID = 780105604L;
}
