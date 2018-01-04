package fr.utbm.boids.util;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(9)
@SuppressWarnings("all")
public class Coordinates {
  /**
   * Abscisse de la coordonée
   */
  private double x;
  
  /**
   * Ordonnée de la coordonnée
   */
  private double y;
  
  /**
   * Création de la classe Coordinates.
   * Par defaut x et y valent 0.
   */
  public Coordinates() {
    this.x = 0;
    this.y = 0;
  }
  
  /**
   * Création de la classe Coordinates.
   * @param x - Abscisse de la coordonée.
   * @param y - Ordonnée de la coordonée.
   */
  public Coordinates(final double x, final double y) {
    this.x = x;
    this.y = y;
  }
  
  /**
   * Création de la classe Coordinates par copie d'une instance de classe Coordinates.
   * @param c - L'instance de classe à copier.
   */
  public Coordinates(final Coordinates c) {
    this.x = c.x;
    this.y = c.y;
  }
  
  /**
   * @return La valeur de l'abscisse.
   */
  @Pure
  public double getX() {
    return this.x;
  }
  
  /**
   * Permet de setter la valeur de l'abscisse.
   * @param x - La valeur de l'abscisse à enregistrer.
   */
  public void setX(final double x) {
    this.x = x;
  }
  
  /**
   * @return La  valeur de l'ordonnée.
   */
  @Pure
  public double getY() {
    return this.y;
  }
  
  /**
   * Permet de setter la valeur de l'ordonnée.
   * @param y - La valeur de l'ordonnée à enregistrer.
   */
  public void setY(final double y) {
    this.y = y;
  }
  
  /**
   * Permet de setter les coordinées en une fois
   * @param c - Les coordonées sous forme de classe Coordinates.
   */
  public void setCoordinates(final Coordinates c) {
    this.x = c.x;
    this.y = c.y;
  }
  
  /**
   * Permet de transformer la classe Coordinates en une chaîne de caractères.
   * @return La chaîne de caractères correspondant à l'instance de la classe.
   */
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
