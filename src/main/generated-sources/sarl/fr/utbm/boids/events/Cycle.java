package fr.utbm.boids.events;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Event;

/**
 * Signalement du début d'un nouveau cycle
 */
@SarlSpecification("0.6")
@SarlElementType(14)
@SuppressWarnings("all")
public class Cycle extends Event {
  @SyntheticMember
  public Cycle() {
    super();
  }
  
  @SyntheticMember
  public Cycle(final Address source) {
    super(source);
  }
  
  @SyntheticMember
  private final static long serialVersionUID = 588368462L;
}
