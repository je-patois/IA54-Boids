package fr.utbm.boids.events;

import fr.utbm.boids.BoidBody;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Un IsStarted avec le body en plus
 * @param body - Transfert du `BoidBody`
 */
@SarlSpecification("0.6")
@SarlElementType(14)
@SuppressWarnings("all")
public class BoidInitialized extends Event {
  public BoidBody body;
  
  public String type;
  
  public BoidInitialized(final BoidBody body, final String typeEntity) {
    this.body = body;
    this.type = typeEntity;
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
   * Returns a String representation of the BoidInitialized event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected String attributesToString() {
    throw new Error("Unresolved compilation problems:"
      + "\nThe return type is incompatible with equals(Object). Current method has the return type: void. The super method has the return type: boolean.");
  }
  
  @SyntheticMember
  private final static long serialVersionUID = 2664466909L;
}
