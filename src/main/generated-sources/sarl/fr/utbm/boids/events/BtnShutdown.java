package fr.utbm.boids.events;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Event;

@SarlSpecification("0.6")
@SarlElementType(14)
@SuppressWarnings("all")
public class BtnShutdown extends Event {
  @SyntheticMember
  public BtnShutdown() {
    super();
  }
  
  @SyntheticMember
  public BtnShutdown(final Address source) {
    super(source);
  }
}
