package fr.utbm.boids.util;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(9)
@SuppressWarnings("all")
public class BoidGroupInfos {
  private int mass;
  
  private int speed;
  
  private int angle;
  
  private int distance;
  
  public BoidGroupInfos(final int mass, final int speed, final int angle, final int distance) {
    this.mass = mass;
    this.speed = speed;
    this.angle = angle;
    this.distance = distance;
  }
  
  @Pure
  public int getMass() {
    return this.mass;
  }
  
  public void setMass(final int mass) {
    this.mass = mass;
  }
  
  @Pure
  public int getSpeed() {
    return this.speed;
  }
  
  public void setSpeed(final int speed) {
    this.speed = speed;
  }
  
  @Pure
  public int getAngle() {
    return this.angle;
  }
  
  public void setAngle(final int angle) {
    this.angle = angle;
  }
  
  @Pure
  public int getDistance() {
    return this.distance;
  }
  
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
