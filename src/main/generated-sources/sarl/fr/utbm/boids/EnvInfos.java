package fr.utbm.boids;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(9)
@SuppressWarnings("all")
public class EnvInfos {
  private int width;
  
  private int height;
  
  public EnvInfos() {
    this.width = 0;
    this.height = 0;
  }
  
  public EnvInfos(final int width, final int height) {
    this.width = width;
    this.height = height;
  }
  
  @Pure
  public int getWidth() {
    return this.width;
  }
  
  public void setWidth(final int width) {
    this.width = width;
  }
  
  @Pure
  public int getHeight() {
    return this.height;
  }
  
  public void setHeight(final int height) {
    this.height = height;
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
    EnvInfos other = (EnvInfos) obj;
    if (other.width != this.width)
      return false;
    if (other.height != this.height)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + this.width;
    result = prime * result + this.height;
    return result;
  }
}
