package fr.utbm.boids.events;

import fr.utbm.boids.BoidBody;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import java.util.Objects;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Un IsStarted avec le body en plus
 * @param body - Transfert du `BoidBody`
 */
@SarlSpecification("0.6")
@SarlElementType(14)
@SuppressWarnings("all")
public class BoidInitialized extends Event {
  public BoidBody body;
  
  public String type;
  
  public BoidInitialized(final BoidBody body, final String typeEntity) {
    this.body = body;
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
    BoidInitialized other = (BoidInitialized) obj;
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
   * Returns a String representation of the BoidInitialized event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("body  = ").append(this.body);
    result.append("type  = ").append(this.type);
    return result.toString();
  }
  
  @SyntheticMember
  private final static long serialVersionUID = 2664466909L;
}
