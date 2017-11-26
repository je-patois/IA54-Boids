package fr.utbm.boids.events;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(14)
@SuppressWarnings("all")
public class StartingSimulation extends Event {
  public final int nombreDePopulations;
  
  public final int nombreDeBoidsParPopulation;
  
  public final int visionBoids;
  
  public StartingSimulation(final int nombreDePopulations, final int nombreDeBoidsParPopulation, final int visionBoids) {
    this.nombreDePopulations = nombreDePopulations;
    this.nombreDeBoidsParPopulation = nombreDeBoidsParPopulation;
    this.visionBoids = visionBoids;
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
    StartingSimulation other = (StartingSimulation) obj;
    if (other.nombreDePopulations != this.nombreDePopulations)
      return false;
    if (other.nombreDeBoidsParPopulation != this.nombreDeBoidsParPopulation)
      return false;
    if (other.visionBoids != this.visionBoids)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + this.nombreDePopulations;
    result = prime * result + this.nombreDeBoidsParPopulation;
    result = prime * result + this.visionBoids;
    return result;
  }
  
  /**
   * Returns a String representation of the StartingSimulation event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("nombreDePopulations  = ").append(this.nombreDePopulations);
    result.append("nombreDeBoidsParPopulation  = ").append(this.nombreDeBoidsParPopulation);
    result.append("visionBoids  = ").append(this.visionBoids);
    return result.toString();
  }
  
  @SyntheticMember
  private final static long serialVersionUID = -4098534001L;
}
