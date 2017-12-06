package fr.utbm.boids.events;

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
public class BoidsToDisplay extends Event {
  public Collection<Boid> boids;
  
  public BoidsToDisplay(final Collection<Boid> BoidsToDisplay) {
    this.boids = BoidsToDisplay;
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
   * Returns a String representation of the BoidsToDisplay event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("boids  = ").append(this.boids);
    return result.toString();
  }
  
  @SyntheticMember
  private final static long serialVersionUID = 2192377104L;
}
