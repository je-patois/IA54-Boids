package fr.utbm.boids.events;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import java.util.Objects;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Signalement de la modification de la position d'un boids par drag & drop
 * @param boid - L'UUID du boids déplacé
 * @param x - La nouvelle abscisse du boids
 * @param y - a nouvelle ordonnée du boids
 */
@SarlSpecification("0.6")
@SarlElementType(14)
@SuppressWarnings("all")
public class PositionModification extends Event {
  public UUID boid;
  
  public double x;
  
  public double y;
  
  public PositionModification(final UUID boid, final double x, final double y) {
    this.boid = boid;
    this.x = x;
    this.y = y;
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
    PositionModification other = (PositionModification) obj;
    if (!Objects.equals(this.boid, other.boid)) {
      return false;
    }
    if (Double.doubleToLongBits(other.x) != Double.doubleToLongBits(this.x))
      return false;
    if (Double.doubleToLongBits(other.y) != Double.doubleToLongBits(this.y))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Objects.hashCode(this.boid);
    result = prime * result + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
    result = prime * result + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
    return result;
  }
  
  /**
   * Returns a String representation of the PositionModification event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("boid  = ").append(this.boid);
    result.append("x  = ").append(this.x);
    result.append("y  = ").append(this.y);
    return result.toString();
  }
  
  @SyntheticMember
  private final static long serialVersionUID = -1922652896L;
}
