package fr.utbm.boids.util;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(9)
@SuppressWarnings("all")
public class Coordinates {
  private double x;
  
  private double y;
  
  public Coordinates() {
    this.x = 0;
    this.y = 0;
  }
  
  public Coordinates(final double x, final double y) {
    this.x = x;
    this.y = y;
  }
  
  public Coordinates(final Coordinates c) {
    this.x = c.x;
    this.y = c.y;
  }
  
  @Pure
  public double getX() {
    return this.x;
  }
  
  public void setX(final double x) {
    this.x = x;
  }
  
  @Pure
  public double getY() {
    return this.y;
  }
  
  public void setY(final double y) {
    this.y = y;
  }
  
  public void setCoordinates(final Coordinates c) {
    this.x = c.x;
    this.y = c.y;
  }
  
  @Pure
  public String toString() {
    return (((("(" + Double.valueOf(this.x)) + ", ") + Double.valueOf(this.y)) + ")");
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
    Coordinates other = (Coordinates) obj;
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
    result = prime * result + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
    result = prime * result + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
    return result;
  }
}
