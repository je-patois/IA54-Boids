package fr.utbm.boids.events;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(14)
@SuppressWarnings("all")
public class StartPosition extends Event {
  public int hauteur;
  
  public int largeur;
  
  public StartPosition(final int h, final int l) {
    this.hauteur = h;
    this.largeur = l;
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
    StartPosition other = (StartPosition) obj;
    if (other.hauteur != this.hauteur)
      return false;
    if (other.largeur != this.largeur)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + this.hauteur;
    result = prime * result + this.largeur;
    return result;
  }
  
  /**
   * Returns a String representation of the StartPosition event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("hauteur  = ").append(this.hauteur);
    result.append("largeur  = ").append(this.largeur);
    return result.toString();
  }
  
  @SyntheticMember
  private final static long serialVersionUID = 1343571395L;
}
