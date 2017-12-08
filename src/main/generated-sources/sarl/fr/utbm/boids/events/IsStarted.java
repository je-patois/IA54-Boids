package fr.utbm.boids.events;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import java.util.Objects;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * event pour prévenir l'environnement que le Boids est lancé
 */
@SarlSpecification("0.6")
@SarlElementType(14)
@SuppressWarnings("all")
public class IsStarted extends Event {
  public String type;
  
  public IsStarted(final String typeEntity) {
    this.type = typeEntity;
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
    IsStarted other = (IsStarted) obj;
    if (!Objects.equals(this.type, other.type)) {
      return false;
    }
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Objects.hashCode(this.type);
    return result;
  }
  
  /**
   * Returns a String representation of the IsStarted event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("type  = ").append(this.type);
    return result.toString();
  }
  
  @SyntheticMember
  private final static long serialVersionUID = 185545343L;
}
