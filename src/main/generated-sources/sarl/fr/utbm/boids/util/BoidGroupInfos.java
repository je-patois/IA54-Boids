package fr.utbm.boids.util;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(9)
@SuppressWarnings("all")
public class BoidGroupInfos {
  /**
   * Masse de la famille de boids
   */
  private int mass;
  
  /**
   * Vitesse max de la famille de boids
   */
  private int speed;
  
  /**
   * Angle de vision de la famille de boids
   */
  private int angle;
  
  /**
   * Distance de vision et de perception de la famille de boids
   */
  private int distance;
  
  /**
   * Création de la classe BoidGroupInfos
   * @param mass - La masse de la famille
   * @param speed - La vitesse max de la famille
   * @param angle - L'angle de vision de la famille
   * @param distance - La distance de vision et  de  perception de la famille
   */
  public BoidGroupInfos(final int mass, final int speed, final int angle, final int distance) {
    this.mass = mass;
    this.speed = speed;
    this.angle = angle;
    this.distance = distance;
  }
  
  /**
   * @return La masse de la  famille
   */
  @Pure
  public int getMass() {
    return this.mass;
  }
  
  /**
   * Permet de setter la masse d'une famille.
   * @param mass - La masseà enregistrer
   */
  public void setMass(final int mass) {
    this.mass = mass;
  }
  
  /**
   * @return La vitesse max de la famille
   */
  @Pure
  public int getSpeed() {
    return this.speed;
  }
  
  /**
   * Permet de setter la vitesse max de la famille
   * @param speed - La vitesse maxà enregistrer
   */
  public void setSpeed(final int speed) {
    this.speed = speed;
  }
  
  /**
   * @return L'angle de vision de la famille
   */
  @Pure
  public int getAngle() {
    return this.angle;
  }
  
  /**
   * Permet de setter l'angle de vision de la famille
   * @param angle -L'angle de vision à enregistrer
   */
  public void setAngle(final int angle) {
    this.angle = angle;
  }
  
  /**
   * @return La distance de vision et de perception de la famille
   */
  @Pure
  public int getDistance() {
    return this.distance;
  }
  
  /**
   * Permet de setter la distance de vision et de perception de la famille
   * @param distance - La distance de vision et de perception à enregistrer
   */
  public void setDistance(final int distance) {
    this.distance = distance;
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
    BoidGroupInfos other = (BoidGroupInfos) obj;
    if (other.mass != this.mass)
      return false;
    if (other.speed != this.speed)
      return false;
    if (other.angle != this.angle)
      return false;
    if (other.distance != this.distance)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + this.mass;
    result = prime * result + this.speed;
    result = prime * result + this.angle;
    result = prime * result + this.distance;
    return result;
  }
}
