package fr.utbm.boids.body;

import com.google.common.base.Objects;
import fr.utbm.boids.body.EnvObjet;
import fr.utbm.boids.util.Vector;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(9)
@SuppressWarnings("all")
public class BoidBody extends EnvObjet {
  /**
   * UUID de l'agent à qui appartient le corps
   */
  private UUID id;
  
  /**
   * Vitesse du boids
   */
  private Vector vitesse;
  
  /**
   * Future vitesse du boids si le déplacement est accepté par l'environnement
   */
  private Vector newVitesse;
  
  /**
   * Groupe auquel appartient le boids
   */
  private int groupe;
  
  /**
   * Vitesse max possible pour le boids
   */
  private int groupeVitesseMax;
  
  /**
   * Masse du boids
   */
  private int masse;
  
  /**
   * L'angle de vision du boids
   */
  private int angleVisibilite;
  
  /**
   * La distance de vision et de perception du boids
   */
  private int distanceVisibilite;
  
  /**
   * Création d'une instance BoidBody
   * @param id - L'UUID de l'agent à qui appartient le corps
   * @param groupe - Le groupe auquel appartient le boids
   * @param groupeVitesseMax - La vitesse max que pourra avoir le boids
   * @param masse - La masse du boids
   * @param angleVisibilite - L'angle de vision que le boids aura
   * @param distanceVisibilite - La distance de vision et de perception dont disposera le boids
   */
  public BoidBody(final UUID id, final int groupe, final int groupeVitesseMax, final int masse, final int angleVisibilite, final int distanceVisibilite) {
    this.id = id;
    this.groupe = groupe;
    this.groupeVitesseMax = groupeVitesseMax;
    this.masse = masse;
    this.angleVisibilite = angleVisibilite;
    this.distanceVisibilite = distanceVisibilite;
    Vector _vector = new Vector(0, 0);
    this.vitesse = _vector;
  }
  
  /**
   * Création d'une instance Boidbody par copie
   * @param body - La corps à copier
   */
  public BoidBody(final BoidBody body) {
    this.groupe = body.groupe;
    this.groupeVitesseMax = body.groupeVitesseMax;
    this.masse = body.masse;
    this.angleVisibilite = body.angleVisibilite;
    this.distanceVisibilite = body.distanceVisibilite;
  }
  
  /**
   * @return L'UUID de l'agent qui possède le corps
   */
  @Pure
  public UUID getID() {
    return this.id;
  }
  
  /**
   * @return La vitesse du boids
   */
  @Pure
  public Vector getVitesse() {
    return this.vitesse;
  }
  
  /**
   * @return Le groupe auquel appartient le boids
   */
  @Pure
  public int getGroupe() {
    return this.groupe;
  }
  
  /**
   * @return L'angle de vision du boids
   */
  @Pure
  public int getAngleVisibilite() {
    return this.angleVisibilite;
  }
  
  /**
   * @return La distance de vision et de perception du boids
   */
  @Pure
  public int getDistanceVisibilite() {
    return this.distanceVisibilite;
  }
  
  /**
   * @return La vitesse maximum du boids
   */
  @Pure
  public int getGroupeVitesseMax() {
    return this.groupeVitesseMax;
  }
  
  /**
   * @return La future vitesse du boids
   */
  @Pure
  public Vector getNewVitesse() {
    return this.newVitesse;
  }
  
  /**
   * @return La masse du boids
   */
  @Pure
  public int getMasse() {
    return this.masse;
  }
  
  /**
   * Permet de setter l'UUID de l'agent qui possède ce corps
   * @param id - L'UUID de l'agent à enregistrer
   */
  public void setID(final UUID id) {
    this.id = id;
  }
  
  /**
   * Permet de setter la vitesse du boids
   * @param v - La vitesse à enregistrer
   */
  public void setVitesse(final Vector v) {
    this.vitesse = v;
  }
  
  /**
   * Permet de setter le groupedu boids
   * @param i - Le groupe à enregistrer
   */
  public void setGroupe(final int i) {
    this.groupe = i;
  }
  
  /**
   * Permet de setter l'angle de vision du boids
   * @param i - L'angle de vision à enregistrer
   */
  public void setAngleVisibilite(final int i) {
    this.angleVisibilite = i;
  }
  
  /**
   * Permet de setter la distance de vision et de perception du boids
   * @param i - La distance de vision et de perception à enregistrer
   */
  public void setDistanceVisibilite(final int i) {
    this.distanceVisibilite = i;
  }
  
  /**
   * Permet de setter la vitesse maximum du boids
   * @param vMax - La vitesse maximum à enregistrer
   */
  public void setGroupeVitesseMax(final int vMax) {
    this.groupeVitesseMax = vMax;
  }
  
  /**
   * Permet de setter la future vitesse du boids
   * @param newV - La future vitesse à enregistrer
   */
  public void setNewVitesse(final Vector newV) {
    this.newVitesse = newV;
  }
  
  /**
   * Permet de setter la masse du boids
   * @param m - La masse à enregistrer
   */
  public void setMasse(final int m) {
    this.masse = m;
  }
  
  /**
   * Permet de convertir un BoidBody en une chaîne de caractères.
   * @return La chaîne de caractères correspondant au BoidBody
   */
  @SuppressWarnings("equals_with_null")
  @Pure
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
    if (!java.util.Objects.equals(this.id, other.id)) {
      return false;
    }
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
    result = prime * result + java.util.Objects.hashCode(this.id);
    result = prime * result + this.groupe;
    result = prime * result + this.groupeVitesseMax;
    result = prime * result + this.masse;
    result = prime * result + this.angleVisibilite;
    result = prime * result + this.distanceVisibilite;
    return result;
  }
}
