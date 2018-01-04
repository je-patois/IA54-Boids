package fr.utbm.boids.body;

import com.google.common.base.Objects;
import fr.utbm.boids.util.Vector;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(9)
@SuppressWarnings("all")
public class EnvObjet {
  /**
   * Position de l'objet dans la map
   */
  private Vector position;
  
  /**
   * @ return Le vecteur position de l'ojet
   */
  @Pure
  public Vector getPosition() {
    return this.position;
  }
  
  /**
   * Permet de setter la position de l'ojet
   * @param p - Le vecteur position de l'objet
   */
  public void setPosition(final Vector p) {
    this.position = p;
  }
  
  /**
   * Permet de transformer un EnvObjet en une chaîne de caractères
   * @return La chaîne de caractère correspondant à un EnvObjet
   */
  @SuppressWarnings("equals_with_null")
  @Pure
  public String toString() {
    boolean _notEquals = (!Objects.equal(this.position, null));
    if (_notEquals) {
      String _string = this.position.toString();
      return ("position : " + _string);
    } else {
      return "";
    }
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
  
  @SyntheticMember
  public EnvObjet() {
    super();
  }
}
