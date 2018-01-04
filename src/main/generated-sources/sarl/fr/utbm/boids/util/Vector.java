package fr.utbm.boids.util;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(9)
@SuppressWarnings("all")
public class Vector {
  /**
   * première dimension du vecteur
   */
  private double x;
  
  /**
   * Deuxième dimension du vecteur
   */
  private double y;
  
  /**
   * Troisième dimension du vecteur
   */
  private double z;
  
  /**
   * Cration d'un vecteur 2D
   * Par défaut la troisième dimension vaut 0
   * @param x - La valeur de la première dimension du vecteur
   * @param y - La valeur de la deuxième dimension du vecteur
   */
  public Vector(final double x, final double y) {
    this.x = x;
    this.y = y;
    this.z = 0;
  }
  
  /**
   * Création d'un vecteur 3D
   * @param x - La valeur de la première dimension du vecteur
   * @param y - La valeur de la deuxième dimension du vecteur
   * @param z - La valeur de la troisième dimension du vecteur
   */
  public Vector(final double x, final double y, final double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }
  
  /**
   * Création d'une instance de Vector par copie.
   * @param v - Le vecteur à copier.
   */
  public Vector(final Vector v) {
    this.x = v.getX();
    this.y = v.getY();
    this.z = v.getZ();
  }
  
  /**
   * Création d'un vecteur nul
   * Par défaut, les valeurs des trois dimensions sont nuls.
   */
  public Vector() {
    this.x = 0;
    this.y = 0;
    this.z = 0;
  }
  
  /**
   * Permet de remettre les valeurs des dimensions à 0
   */
  public void clear() {
    this.x = 0;
    this.y = 0;
    this.z = 0;
  }
  
  /**
   * Permet de setter la valeur de la première dimension du vecteur
   * @param x - La valeur de la première dimension à modifier
   */
  public void setX(final double x) {
    this.x = x;
  }
  
  /**
   * Permet de setter la valeur de la deuxième dimension du vecteur
   * @param y - La valeur de la deuxième dimension à modifier
   */
  public void setY(final double y) {
    this.y = y;
  }
  
  /**
   * Permet de setter la valeur de la troisième dimension du vecteur
   * @param z - La valeur de la troisième dimension à modifier
   */
  public void setZ(final double z) {
    this.z = z;
  }
  
  /**
   * Permet de setter en une fois la valeur de la première et deuxième dimension du vecteur
   * @param x - La valeur de la première dimension à modifier
   * @param y - La valeur de la deuxième dimension à modifier
   */
  public void setXY(final double x, final double y) {
    this.x = x;
    this.y = y;
  }
  
  /**
   * Permet de setter en une fois la valeur de la première et deuxième dimension du vecteur à l'aide d'un autre vecteur
   * @param vec - Le vecteur duquel il faut récupérer les informations
   */
  public void setXY(final Vector vec) {
    this.x = vec.getX();
    this.y = vec.getY();
  }
  
  /**
   * @return La valeur de la première dimension du vecteur
   */
  @Pure
  public double getX() {
    return this.x;
  }
  
  /**
   * @return La valeur de la deuxième dimension du vecteur
   */
  @Pure
  public double getY() {
    return this.y;
  }
  
  /**
   * @return La valeur de la troisième dimension du vecteur
   */
  @Pure
  public double getZ() {
    return this.z;
  }
  
  /**
   * Permet la soustraction d'un vecteur à notre vecteur
   * @param vec - Le vecteurà soustraire
   */
  public double moins(final Vector vec) {
    double _xblockexpression = (double) 0;
    {
      this.x = (this.x - vec.x);
      this.y = (this.y - vec.y);
      _xblockexpression = this.z = (this.z - vec.z);
    }
    return _xblockexpression;
  }
  
  /**
   * Permet l'addition d'un vecteur à notre vecteur
   * @param vec - Le vecteur à additioner
   */
  public double plus(final Vector vec) {
    double _xblockexpression = (double) 0;
    {
      this.x = (this.x + vec.x);
      this.y = (this.y + vec.y);
      _xblockexpression = this.z = (this.z + vec.z);
    }
    return _xblockexpression;
  }
  
  /**
   * Permet de multiplier notre vecteur par un réel
   * @param a Le réel par lequel notre vecteur va être multiplié
   */
  public double fois(final double a) {
    double _xblockexpression = (double) 0;
    {
      this.x = (this.x * a);
      this.y = (this.y * a);
      _xblockexpression = this.z = (this.z * a);
    }
    return _xblockexpression;
  }
  
  /**
   * Permet de calculer la longueur de notre vecteur
   * @return La longueur de notre vecteur
   */
  @Pure
  public double length() {
    return Math.sqrt((((this.x * this.x) + (this.y * this.y)) + (this.z * this.z)));
  }
  
  /**
   * Permet de normaliser notre vecteur, i.e d'avoir une longueur de 1
   */
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
  
  /**
   * Effectuer le produit scalaire de notre vecteur avec un autre vecteur
   * @param vec - Le vecteur avec lequel on effectue le produit scalaire
   * @return La valeur du produit scalaire
   */
  @Pure
  public double point(final Vector vec) {
    return (((this.x * vec.x) + (this.y * vec.y)) + (this.z * vec.z));
  }
  
  /**
   * Permet de calculer le produit vectoriel de notre vecteur avec un second vecteur
   * @param vec - Le vecteur avec lequel on effectue le produit vectoriel
   * @return Le vecteur résultant de notre produit vectoriel
   */
  public Vector produitVectoriel(final Vector vec) {
    Vector produit = null;
    Vector _vector = new Vector(0, 0);
    produit = _vector;
    produit.setX(((this.y * vec.z) - (this.z * vec.y)));
    produit.setY(((this.z * vec.x) - (this.x * vec.z)));
    produit.setZ(((this.x * vec.y) - (this.y * vec.x)));
    return produit;
  }
  
  /**
   * Permet de transformer un vecteur en une chaîne de caractères
   * @return La chaîne de caractères correpsondant à l'instance de la classe Vector
   */
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
