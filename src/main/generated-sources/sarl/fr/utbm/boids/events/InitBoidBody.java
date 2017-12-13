package fr.utbm.boids.events;

import fr.utbm.boids.BoidBody;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Demande au boid d'initialiser son BoidBody
 * @parameter body - BoidBody du boid
 */
@SarlSpecification("0.6")
@SarlElementType(14)
@SuppressWarnings("all")
public class InitBoidBody extends Event {
  public BoidBody body;
  
  public InitBoidBody(final BoidBody body) {
    this.body = body;
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
  
  /**
   * Returns a String representation of the InitBoidBody event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("body  = ").append(this.body);
    return result.toString();
  }
  
  @SyntheticMember
  private final static long serialVersionUID = -1612128729L;
}
