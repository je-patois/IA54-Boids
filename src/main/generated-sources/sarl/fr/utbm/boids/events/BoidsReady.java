package fr.utbm.boids.events;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Event;

/**
 * Evenement déclenché lorsque le nombre d'agents `Boid` correspond au nombre de boids demandé
 */
@SarlSpecification("0.6")
@SarlElementType(14)
@SuppressWarnings("all")
public class BoidsReady extends Event {
  @SyntheticMember
  public BoidsReady() {
    super();
  }
  
  @SyntheticMember
  public BoidsReady(final Address source) {
    super(source);
  }
}
