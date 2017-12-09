package fr.utbm.boids.util;

import fr.utbm.boids.util.Coordinates;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(9)
@SuppressWarnings("all")
public class LineTool {
  private Coordinates a;
  
  private Coordinates b;
  
  private double coef;
  
  private double ordered;
  
  public LineTool() {
  }
  
  public LineTool(final Coordinates a, final Coordinates b) {
    this.a = a;
    this.b = b;
  }
  
  @Pure
  public Coordinates getA() {
    return this.a;
  }
  
  public void setA(final Coordinates a) {
    this.a = a;
  }
  
  @Pure
  public Coordinates getB() {
    return null;
  }
  
  public void setB(final Coordinates b) {
    this.b = b;
  }
  
  @Pure
  public String toString() {
    double _x = this.a.getX();
    String _plus = ("Line: [A(" + Double.valueOf(_x));
    String _plus_1 = (_plus + ", ");
    double _y = this.a.getY();
    String _plus_2 = (_plus_1 + Double.valueOf(_y));
    String _plus_3 = (_plus_2 + "), B(");
    double _x_1 = this.b.getX();
    String _plus_4 = (_plus_3 + Double.valueOf(_x_1));
    String _plus_5 = (_plus_4 + ", ");
    double _y_1 = this.b.getY();
    String _plus_6 = (_plus_5 + Double.valueOf(_y_1));
    String _plus_7 = (_plus_6 + "), Equation: y = ");
    String _plus_8 = (_plus_7 + 
      Double.valueOf(this.coef));
    String _plus_9 = (_plus_8 + "x + ");
    String _plus_10 = (_plus_9 + Double.valueOf(this.ordered));
    return (_plus_10 + "]");
  }
  
  public void computeLineEquation() {
    double _y = this.a.getY();
    double _y_1 = this.b.getY();
    double _minus = (_y - _y_1);
    double _x = this.a.getX();
    double _x_1 = this.b.getX();
    double _minus_1 = (_x - _x_1);
    double _divide = (_minus / _minus_1);
    this.coef = _divide;
    boolean _isInfinite = Double.isInfinite(this.coef);
    if (_isInfinite) {
      this.coef = 0;
    }
    double _y_2 = this.a.getY();
    double _x_2 = this.a.getX();
    double _multiply = (this.coef * _x_2);
    double _minus_2 = (_y_2 - _multiply);
    this.ordered = _minus_2;
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
    LineTool other = (LineTool) obj;
    if (Double.doubleToLongBits(other.coef) != Double.doubleToLongBits(this.coef))
      return false;
    if (Double.doubleToLongBits(other.ordered) != Double.doubleToLongBits(this.ordered))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + (int) (Double.doubleToLongBits(this.coef) ^ (Double.doubleToLongBits(this.coef) >>> 32));
    result = prime * result + (int) (Double.doubleToLongBits(this.ordered) ^ (Double.doubleToLongBits(this.ordered) >>> 32));
    return result;
  }
}
