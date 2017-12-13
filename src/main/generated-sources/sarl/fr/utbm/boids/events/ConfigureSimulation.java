package fr.utbm.boids.events;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(14)
@SuppressWarnings("all")
public class ConfigureSimulation extends Event {
  public final int mapSelection;
  
  public final int boidsQuantity;
  
  public final int boidsPopulation;
  
  public final int boidsVision;
  
  public ConfigureSimulation(final int mS, final int bQ, final int bP, final int bV) {
    this.mapSelection = mS;
    this.boidsQuantity = bQ;
    this.boidsPopulation = bP;
    this.boidsVision = bV;
  }
  
  @Override
  @Pure
  @SyntheticMember
  public boolean equals(final Object obj) {
    throw new Error("Unresolved compilation problems:"
      + "\nThe return type is incompatible with equals(Object). Current method has the return type: void. The super method has the return type: boolean."
      + "\nThe return type is incompatible with equals(Object). Current method has the return type: void. The super method has the return type: boolean.");
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    throw new Error("Unresolved compilation problems:"
      + "\nThe return type is incompatible with equals(Object). Current method has the return type: void. The super method has the return type: boolean.");
  }
  
  /**
   * Returns a String representation of the ConfigureSimulation event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected String attributesToString() {
    throw new Error("Unresolved compilation problems:"
      + "\nThe return type is incompatible with equals(Object). Current method has the return type: void. The super method has the return type: boolean.");
  }
}
