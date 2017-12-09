package fr.utbm.boids.environment;

import fr.utbm.boids.util.LineTool;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.util.List;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(9)
@SuppressWarnings("all")
public class Obstacle {
  private List<LineTool> lines;
  
  public Obstacle() {
  }
  
  public Obstacle(final List<LineTool> lines) {
    this.lines = lines;
  }
  
  /**
   * def getInfos() : EnvironmentInfos {
   * return this.infos
   * }
   * 
   * def setInfos(infos : EnvironmentInfos) : void {
   * this.infos = infos
   * }
   */
  @Pure
  public String toString() {
    String _string = this.lines.toString();
    String _plus = ("Obstacle: " + _string);
    return (_plus + "");
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
}
