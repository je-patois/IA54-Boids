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
  /**
   * Origine de l'arête
   */
  private Coordinates pointDepart;
  
  /**
   * Vecteur de direction de l'arête
   */
  private Vector direction;
  
  /**
   * Vecteur normal à l'arêtes
   */
  private Vector normal;
  
  /**
   * Création de la classe Edge
   * @param depart - Coordonnées du point de départ de l'arête.
   * @param arrivee - Coordonnées du point d'arrivée de l'arête.
   */
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
   * @return Les coordonées du point de départ
   */
  @Pure
  public Coordinates getPointDepart() {
    return this.pointDepart;
  }
  
  /**
   * @return Le vecteur de direction.
   */
  @Pure
  public Vector getDirection() {
    return this.direction;
  }
  
  /**
   * @return Le vecteur normal à l'arête. (vecteur normalisé)
   */
  @Pure
  public Vector getNormal() {
    return this.normal;
  }
  
  /**
   * Permet de setter le point de départ.
   * @param c - Les coordonées du point de départ de l'arête.
   */
  public void setPointDepart(final Coordinates c) {
    this.pointDepart = c;
  }
  
  /**
   * Permet de setter la direction.
   * @param v - Le vecteur de direction de l'arête.
   */
  public void setDirection(final Vector v) {
    this.direction = v;
  }
  
  /**
   * Permet de setter le vecteur normal.
   * @param v - Le  vecteur normal à l'arrête
   */
  public double setNormal(final Vector v) {
    double _xblockexpression = (double) 0;
    {
      this.normal = v;
      _xblockexpression = this.normal.normaliser();
    }
    return _xblockexpression;
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
