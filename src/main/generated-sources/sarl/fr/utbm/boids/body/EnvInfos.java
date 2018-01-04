package fr.utbm.boids.body;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(9)
@SuppressWarnings("all")
public class EnvInfos {
  /**
   * La largeur de la map
   */
  private int width;
  
  /**
   * La hauteur de la map
   */
  private int height;
  
  /**
   * Création d'une instance de classe EnvInfos.
   * Par défaut la hauteur et la largeur de la map sont définies comme nulles.
   */
  public EnvInfos() {
    this.width = 0;
    this.height = 0;
  }
  
  /**
   * Création d'une instance de classe EnvInfos.
   * @param width - La largeur de la map.
   * @param height - La hauteur de la map.
   */
  public EnvInfos(final int width, final int height) {
    this.width = width;
    this.height = height;
  }
  
  /**
   * @return La largeur de la map.
   */
  @Pure
  public int getWidth() {
    return this.width;
  }
  
  /**
   * Permet de setter la largeur de la map.
   * @param width - La  largeur de la map à enregistrer.
   */
  public void setWidth(final int width) {
    this.width = width;
  }
  
  /**
   * @return La hauteur de la map.
   */
  @Pure
  public int getHeight() {
    return this.height;
  }
  
  /**
   * Permet de setter la hauteur de la map.
   * @param height - La hauteur de la map à enregistrer.
   */
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
