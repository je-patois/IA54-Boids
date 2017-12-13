package fr.utbm.boids.events;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Demande au Time Manager de démarrer ses cycles
 * @param frequency - Fréquence de raffraichissement entre 2 cycles
 */
@SarlSpecification("0.6")
@SarlElementType(14)
@SuppressWarnings("all")
public class TimeManagingStart extends Event {
  public long frequency;
  
  public TimeManagingStart(final long frequency) {
    this.frequency = frequency;
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
    TimeManagingStart other = (TimeManagingStart) obj;
    if (other.frequency != this.frequency)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + (int) (this.frequency ^ (this.frequency >>> 32));
    return result;
  }
  
  /**
   * Returns a String representation of the TimeManagingStart event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("frequency  = ").append(this.frequency);
    return result.toString();
  }
}
