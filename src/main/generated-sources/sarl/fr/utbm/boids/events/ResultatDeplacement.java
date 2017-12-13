package fr.utbm.boids.events;

import fr.utbm.boids.Vector;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Evenement lancé lorsqu'un boid désire se déplacer. Il transfert ici la prochaine position qu'il veut, pour que l'environnement puisse décider de si il s'agit d'une position viable ou non
 * @param position - Nouvelle position du boid
 * @param newVitesse - Nouveau vecteur vitesse du boid
 */
@SarlSpecification("0.6")
@SarlElementType(14)
@SuppressWarnings("all")
public class ResultatDeplacement extends Event {
  public Vector position;
  
  public Vector newSpeed;
  
  public ResultatDeplacement(final Vector pos, final Vector vit) {
    this.position = pos;
    this.newSpeed = vit;
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
    result.append("newSpeed  = ").append(this.newSpeed);
    return result.toString();
  }
  
  @SyntheticMember
  private final static long serialVersionUID = 1602694147L;
}
