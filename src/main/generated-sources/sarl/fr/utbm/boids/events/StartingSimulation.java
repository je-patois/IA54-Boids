package fr.utbm.boids.events;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Event;

/**
 * Débute la simulation
 */
@SarlSpecification("0.6")
@SarlElementType(14)
@SuppressWarnings("all")
public class StartingSimulation extends Event {
  @SyntheticMember
  public StartingSimulation() {
    super();
  }
  
  @SyntheticMember
  public StartingSimulation(final Address source) {
    super(source);
  }
}
