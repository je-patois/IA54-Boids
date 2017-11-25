package fr.utbm.boids.events;

import fr.utbm.boids.Vecteur;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(14)
@SuppressWarnings("all")
public class ResultatDeplacement extends Event {
  public Vecteur position;
  
  public ResultatDeplacement(final Vecteur pos) {
    this.position = pos;
  }
  
  public ResultatDeplacement(final int x, final int y) {
    this.position.setXY(x, y);
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
   * Returns a String representation of the ResultatDeplacement event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("position  = ").append(this.position);
    return result.toString();
  }
  
  @SyntheticMember
  private final static long serialVersionUID = -375188861L;
}
