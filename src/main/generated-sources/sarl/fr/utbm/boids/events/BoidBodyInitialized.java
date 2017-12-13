package fr.utbm.boids.events;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Event;

/**
 * Evenement déclenché lorsqu'un BoidBody est correctement initialisé
 */
@SarlSpecification("0.6")
@SarlElementType(14)
@SuppressWarnings("all")
public class BoidBodyInitialized extends Event {
  @SyntheticMember
  public BoidBodyInitialized() {
    super();
  }
  
  @SyntheticMember
  public BoidBodyInitialized(final Address source) {
    super(source);
  }
}
