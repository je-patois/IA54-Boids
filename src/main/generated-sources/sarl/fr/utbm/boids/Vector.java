package fr.utbm.boids;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Du mal à réutiliser une classe Vector java, malgré l'import il veut pas reconnaitre le type de la variable, je passe par cette classe en attendant
 */
@SarlSpecification("0.6")
@SarlElementType(9)
@SuppressWarnings("all")
public class Vector {
  private double x;
  
  private double y;
  
  public Vector(final double x, final double y) {
    this.x = x;
    this.y = y;
  }
  
  public Vector() {
    this.x = 0;
    this.y = 0;
  }
  
  public void setX(final double x) {
    this.x = x;
  }
  
  public void setY(final double y) {
    this.y = y;
  }
  
  public void setXY(final double x, final double y) {
    this.x = x;
    this.y = y;
  }
  
  public void setXY(final Vector vec) {
    this.x = vec.getX();
    this.y = vec.getY();
  }
  
  @Pure
  public double getX() {
    return this.x;
  }
  
  @Pure
  public double getY() {
    return this.y;
  }
  
  public double moins(final Vector vec) {
    double _xblockexpression = (double) 0;
    {
      this.x = (this.x - vec.x);
      _xblockexpression = this.y = (this.y - vec.y);
    }
    return _xblockexpression;
  }
  
  public double plus(final Vector vec) {
    double _xblockexpression = (double) 0;
    {
      this.x = (this.x + vec.x);
      _xblockexpression = this.y = (this.y + vec.y);
    }
    return _xblockexpression;
  }
  
  public double fois(final double a) {
    double _xblockexpression = (double) 0;
    {
      this.x = (this.x * a);
      _xblockexpression = this.y = (this.y * a);
    }
    return _xblockexpression;
  }
  
  @Pure
  public double length() {
    return Math.sqrt(((this.x * this.x) + (this.y * this.y)));
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
    Vector other = (Vector) obj;
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
