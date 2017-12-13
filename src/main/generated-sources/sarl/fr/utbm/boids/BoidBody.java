package fr.utbm.boids;

import com.google.common.base.Objects;
import fr.utbm.boids.EnvObjet;
import fr.utbm.boids.Vector;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(9)
@SuppressWarnings("all")
public class BoidBody extends EnvObjet {
  private Vector vitesse;
  
  private Vector newVitesse;
  
  private int groupe;
  
  private int groupeVitesseMax;
  
  private int masse;
  
  private int angleVisibilite;
  
  private int distanceVisibilite;
  
  public BoidBody(final int groupe, final int groupeVitesseMax, final int masse, final int angleVisibilite, final int distanceVisibilite) {
    this.groupe = groupe;
    this.groupeVitesseMax = groupeVitesseMax;
    this.masse = masse;
    this.angleVisibilite = angleVisibilite;
    this.distanceVisibilite = distanceVisibilite;
    Vector _vector = new Vector(0, 0);
    this.vitesse = _vector;
  }
  
  public BoidBody(final BoidBody body) {
    this.groupe = body.groupe;
    this.groupeVitesseMax = body.groupeVitesseMax;
    this.masse = body.masse;
    this.angleVisibilite = body.angleVisibilite;
    this.distanceVisibilite = body.distanceVisibilite;
  }
  
  /**
   * GETTER
   */
  @Pure
  public Vector getVitesse() {
    return this.vitesse;
  }
  
  @Pure
  public int getGroupe() {
    return this.groupe;
  }
  
  @Pure
  public int getAngleVisibilite() {
    return this.angleVisibilite;
  }
  
  @Pure
  public int getDistanceVisibilite() {
    return this.distanceVisibilite;
  }
  
  @Pure
  public int getGroupeVitesseMax() {
    return this.groupeVitesseMax;
  }
  
  @Pure
  public Vector getNewVitesse() {
    return this.newVitesse;
  }
  
  @Pure
  public int getMasse() {
    return this.masse;
  }
  
  /**
   * SETTER
   */
  public void setVitesse(final Vector v) {
    this.vitesse = v;
  }
  
  public void setGroupe(final int i) {
    this.groupe = i;
  }
  
  public void setAngleVisibilite(final int i) {
    this.angleVisibilite = i;
  }
  
  public void setDistanceVisibilite(final int i) {
    this.distanceVisibilite = i;
  }
  
  public void setGroupeVitesseMax(final int vMax) {
    this.groupeVitesseMax = vMax;
  }
  
  public void setNewVitesse(final Vector newV) {
    this.newVitesse = newV;
  }
  
  public void setMasse(final int m) {
    this.masse = m;
  }
  
  @SuppressWarnings("equals_with_null")
  public String toString() {
    String retour = null;
    retour = super.toString();
    String _retour = retour;
    String _xifexpression = null;
    boolean _notEquals = (!Objects.equal(this.vitesse, null));
    if (_notEquals) {
      String _string = this.vitesse.toString();
      _xifexpression = (", vitesse : " + _string);
    } else {
      _xifexpression = "";
    }
    retour = (_retour + _xifexpression);
    String _retour_1 = retour;
    String _xifexpression_1 = null;
    boolean _notEquals_1 = (!Objects.equal(this.newVitesse, null));
    if (_notEquals_1) {
      String _string_1 = this.newVitesse.toString();
      _xifexpression_1 = (", newVitesse : " + _string_1);
    } else {
      _xifexpression_1 = "";
    }
    retour = (_retour_1 + _xifexpression_1);
    String _retour_2 = retour;
    retour = (_retour_2 + (", groupe : " + Integer.valueOf(this.groupe)));
    String _retour_3 = retour;
    retour = (_retour_3 + (", groupeVitesseMax : " + Integer.valueOf(this.groupeVitesseMax)));
    String _retour_4 = retour;
    retour = (_retour_4 + (", masse : " + Integer.valueOf(this.masse)));
    String _retour_5 = retour;
    retour = (_retour_5 + (", angleVisibilite : " + Integer.valueOf(this.angleVisibilite)));
    String _retour_6 = retour;
    retour = (_retour_6 + (", distanceVisibilite : " + Integer.valueOf(this.distanceVisibilite)));
    return retour;
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
    BoidBody other = (BoidBody) obj;
    if (other.groupe != this.groupe)
      return false;
    if (other.groupeVitesseMax != this.groupeVitesseMax)
      return false;
    if (other.masse != this.masse)
      return false;
    if (other.angleVisibilite != this.angleVisibilite)
      return false;
    if (other.distanceVisibilite != this.distanceVisibilite)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + this.groupe;
    result = prime * result + this.groupeVitesseMax;
    result = prime * result + this.masse;
    result = prime * result + this.angleVisibilite;
    result = prime * result + this.distanceVisibilite;
    return result;
  }
}
