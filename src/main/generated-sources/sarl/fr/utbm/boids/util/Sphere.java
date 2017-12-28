package fr.utbm.boids.util;

import fr.utbm.boids.util.Vector;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(9)
@SuppressWarnings("all")
public class Sphere {
  private Vector center;
  
  private double radius;
  
  public Sphere(final Vector c, final double r) {
    this.center = c;
    this.radius = r;
  }
  
  /**
   * SETTER
   */
  public void setCenter(final Vector c) {
    this.center = c;
  }
  
  public void setRadius(final double r) {
    this.radius = r;
  }
  
  /**
   * GETTER
   */
  @Pure
  public Vector getCenter() {
    return this.center;
  }
  
  @Pure
  public double getRadius() {
    return this.radius;
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
    Sphere other = (Sphere) obj;
    if (Double.doubleToLongBits(other.radius) != Double.doubleToLongBits(this.radius))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + (int) (Double.doubleToLongBits(this.radius) ^ (Double.doubleToLongBits(this.radius) >>> 32));
    return result;
  }
}
