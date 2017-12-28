package fr.utbm.boids;

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
  private Vector position;
  
  /**
   * GETTER
   */
  @Pure
  public Vector getPosition() {
    return this.position;
  }
  
  /**
   * SETTER
   */
  public void setPosition(final Vector p) {
    this.position = p;
  }
  
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
