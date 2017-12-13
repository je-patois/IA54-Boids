package fr.utbm.boids.events;

import fr.utbm.boids.Vector;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(14)
@SuppressWarnings("all")
public class ResultatDeplacement extends Event {
  public Vector position;
  
  public Vector newVitesse;
  
  public ResultatDeplacement(final Vector pos, final Vector vit) {
    this.position = pos;
    this.newVitesse = vit;
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
    result.append("newVitesse  = ").append(this.newVitesse);
    return result.toString();
  }
  
  @SyntheticMember
  private final static long serialVersionUID = 1298862813L;
}
