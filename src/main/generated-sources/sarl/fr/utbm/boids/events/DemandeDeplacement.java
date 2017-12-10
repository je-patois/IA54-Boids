package fr.utbm.boids.events;

import fr.utbm.boids.BoidBody;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import java.util.Map;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(14)
@SuppressWarnings("all")
public class DemandeDeplacement extends Event {
  /**
   * var otherBoids : Collection<BoidBody>
   */
  public Map<UUID, BoidBody> otherBoids;
  
  /**
   * new (pos : Vector, otherBoids : Collection<BoidBody>) {
   * this.otherBoids = otherBoids
   * }
   * 
   * new (x : int, y : int, otherBoids : Collection<BoidBody>) {
   * this.otherBoids = otherBoids
   * }
   */
  public DemandeDeplacement() {
  }
  
  public DemandeDeplacement(final Map<UUID, BoidBody> otherBoids) {
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
  private final static long serialVersionUID = -2192754315L;
}
