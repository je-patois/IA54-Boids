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
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ConfigureSimulation other = (ConfigureSimulation) obj;
    if (other.mapSelection != this.mapSelection)
      return false;
    if (other.boidsQuantity != this.boidsQuantity)
      return false;
    if (other.boidsPopulation != this.boidsPopulation)
      return false;
    if (other.boidsVision != this.boidsVision)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + this.mapSelection;
    result = prime * result + this.boidsQuantity;
    result = prime * result + this.boidsPopulation;
    result = prime * result + this.boidsVision;
    return result;
  }
  
  /**
   * Returns a String representation of the ConfigureSimulation event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("mapSelection  = ").append(this.mapSelection);
    result.append("boidsQuantity  = ").append(this.boidsQuantity);
    result.append("boidsPopulation  = ").append(this.boidsPopulation);
    result.append("boidsVision  = ").append(this.boidsVision);
    return result.toString();
  }
  
  @SyntheticMember
  private final static long serialVersionUID = 654767120L;
}
