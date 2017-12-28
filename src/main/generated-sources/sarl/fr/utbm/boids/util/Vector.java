package fr.utbm.boids.util;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(9)
@SuppressWarnings("all")
public class Vector {
  private double x;
  
  private double y;
  
  private double z;
  
  public Vector(final double x, final double y) {
    this.x = x;
    this.y = y;
    this.z = 0;
  }
  
  public Vector(final double x, final double y, final double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }
  
  public Vector(final Vector v) {
    this.x = v.getX();
    this.y = v.getY();
    this.z = v.getZ();
  }
  
  public Vector() {
    this.x = 0;
    this.y = 0;
    this.z = 0;
  }
  
  public void clear() {
    this.x = 0;
    this.y = 0;
  }
  
  public void setX(final double x) {
    this.x = x;
  }
  
  public void setY(final double y) {
    this.y = y;
  }
  
  public void setZ(final double z) {
    this.z = z;
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
  
  @Pure
  public double getZ() {
    return this.z;
  }
  
  public double moins(final Vector vec) {
    double _xblockexpression = (double) 0;
    {
      this.x = (this.x - vec.x);
      this.y = (this.y - vec.y);
      _xblockexpression = this.z = (this.z - vec.z);
    }
    return _xblockexpression;
  }
  
  public double plus(final Vector vec) {
    double _xblockexpression = (double) 0;
    {
      this.x = (this.x + vec.x);
      this.y = (this.y + vec.y);
      _xblockexpression = this.z = (this.z + vec.z);
    }
    return _xblockexpression;
  }
  
  public double fois(final double a) {
    double _xblockexpression = (double) 0;
    {
      this.x = (this.x * a);
      this.y = (this.y * a);
      _xblockexpression = this.z = (this.z * a);
    }
    return _xblockexpression;
  }
  
  @Pure
  public double length() {
    return Math.sqrt((((this.x * this.x) + (this.y * this.y)) + (this.z * this.z)));
  }
  
  public double normaliser() {
    double _xblockexpression = (double) 0;
    {
      double longueur = this.length();
      double _xifexpression = (double) 0;
      if ((longueur > 0)) {
        double _xblockexpression_1 = (double) 0;
        {
          this.x = (this.x / longueur);
          this.y = (this.y / longueur);
          _xblockexpression_1 = this.z = (this.z / longueur);
        }
        _xifexpression = _xblockexpression_1;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  @Pure
  public double point(final Vector vec) {
    return (((this.x * vec.x) + (this.y * vec.y)) + (this.z * vec.z));
  }
  
  public Vector produitVectoriel(final Vector vec) {
    Vector produit = null;
    Vector _vector = new Vector(0, 0);
    produit = _vector;
    produit.setX(((this.y * vec.z) - (this.z * vec.y)));
    produit.setY(((this.z * vec.x) - (this.x * vec.z)));
    produit.setZ(((this.x * vec.y) - (this.y * vec.x)));
    return produit;
  }
  
  @Pure
  public String toString() {
    return (((((("x : " + Double.valueOf(this.x)) + " ,y : ") + Double.valueOf(this.y)) + " ,z : ") + Double.valueOf(this.z)) + "   ");
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
    if (Double.doubleToLongBits(other.z) != Double.doubleToLongBits(this.z))
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
    result = prime * result + (int) (Double.doubleToLongBits(this.z) ^ (Double.doubleToLongBits(this.z) >>> 32));
    return result;
  }
}
