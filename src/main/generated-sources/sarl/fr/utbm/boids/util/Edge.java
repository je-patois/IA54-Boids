package fr.utbm.boids.util;

import fr.utbm.boids.util.Coordinates;
import fr.utbm.boids.util.Vector;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(9)
@SuppressWarnings("all")
public class Edge {
  private Coordinates pointDepart;
  
  private Vector direction;
  
  private Vector normal;
  
  public Edge(final Coordinates depart, final Coordinates arrivee) {
    this.pointDepart = depart;
    double _x = arrivee.getX();
    double _x_1 = depart.getX();
    double _minus = (_x - _x_1);
    double _y = arrivee.getY();
    double _y_1 = depart.getY();
    double _minus_1 = (_y - _y_1);
    Vector _vector = new Vector(_minus, _minus_1);
    this.direction = _vector;
    Vector vecteurUnitaireZ = new Vector(0, 0, 1);
    this.normal = this.direction.produitVectoriel(vecteurUnitaireZ);
    this.normal.normaliser();
  }
  
  /**
   * GETTER
   */
  @Pure
  public Coordinates getPointDepart() {
    return this.pointDepart;
  }
  
  @Pure
  public Vector getDirection() {
    return this.direction;
  }
  
  @Pure
  public Vector getNormal() {
    return this.normal;
  }
  
  /**
   * SETTER
   */
  public void setPointDepart(final Coordinates c) {
    this.pointDepart = c;
  }
  
  public void setDirection(final Vector v) {
    this.direction = v;
  }
  
  public void setNormal(final Vector v) {
    this.normal = v;
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
}
