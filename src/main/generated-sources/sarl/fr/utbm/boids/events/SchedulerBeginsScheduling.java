package fr.utbm.boids.events;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(14)
@SuppressWarnings("all")
public class SchedulerBeginsScheduling extends Event {
  public long freqRafraichissement;
  
  public SchedulerBeginsScheduling(final long freqRafraichissement) {
    this.freqRafraichissement = freqRafraichissement;
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
    SchedulerBeginsScheduling other = (SchedulerBeginsScheduling) obj;
    if (other.freqRafraichissement != this.freqRafraichissement)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + (int) (this.freqRafraichissement ^ (this.freqRafraichissement >>> 32));
    return result;
  }
  
  /**
   * Returns a String representation of the SchedulerBeginsScheduling event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("freqRafraichissement  = ").append(this.freqRafraichissement);
    return result.toString();
  }
  
  @SyntheticMember
  private final static long serialVersionUID = 2365250911L;
}
