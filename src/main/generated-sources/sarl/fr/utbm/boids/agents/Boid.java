package fr.utbm.boids.agents;

import com.google.common.base.Objects;
import fr.utbm.boids.Vector;
import fr.utbm.boids.events.DemandeDeplacement;
import fr.utbm.boids.events.IsStarted;
import fr.utbm.boids.events.ResultatDeplacement;
import fr.utbm.boids.events.StartPosition;
import io.sarl.core.DefaultContextInteractions;
import io.sarl.core.Initialize;
import io.sarl.core.Logging;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.PerceptGuardEvaluator;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.BuiltinCapacitiesProvider;
import io.sarl.lang.core.DynamicSkillProvider;
import io.sarl.lang.core.Skill;
import io.sarl.lang.util.ClearableReference;
import java.util.Collection;
import java.util.Random;
import java.util.UUID;
import javax.inject.Inject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Inline;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(17)
@SuppressWarnings("all")
public class Boid extends Agent {
  private Vector position;
  
  private Vector vitesse;
  
  private int groupe;
  
  private int groupeVitesseMax;
  
  private int angleVisibilite;
  
  private int distanceVisibilite;
  
  @SyntheticMember
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.setLoggingName("Boid");
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info("A Boid agent was started.");
    Object _get = occurrence.parameters[0];
    Integer _integer = new Integer((((Integer) _get)).intValue());
    this.groupe = (_integer).intValue();
    Object _get_1 = occurrence.parameters[1];
    Integer _integer_1 = new Integer((((Integer) _get_1)).intValue());
    this.angleVisibilite = (_integer_1).intValue();
    Object _get_2 = occurrence.parameters[2];
    Integer _integer_2 = new Integer((((Integer) _get_2)).intValue());
    this.distanceVisibilite = (_integer_2).intValue();
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2.info(("group " + Integer.valueOf(this.groupe)));
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
    IsStarted _isStarted = new IsStarted();
    _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_isStarted);
  }
  
  @SyntheticMember
  private void $behaviorUnit$StartPosition$1(final StartPosition occurrence) {
    Vector maPosition = null;
    final Random rnd = new Random();
    int x = rnd.nextInt();
    int y = rnd.nextInt();
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(((("X, Y \n" + Integer.valueOf(x)) + " ") + Integer.valueOf(y)));
    int _x = x;
    x = (_x % 50);
    y = (y % 50);
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info(((("X, Y \n" + Integer.valueOf(x)) + " ") + Integer.valueOf(y)));
    Vector _vector = new Vector(x, y);
    maPosition = _vector;
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
    ResultatDeplacement _resultatDeplacement = new ResultatDeplacement(maPosition, this);
    _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_resultatDeplacement);
  }
  
  @SyntheticMember
  private void $behaviorUnit$ResultatDeplacement$2(final ResultatDeplacement occurrence) {
    this.position = occurrence.position;
  }
  
  @SyntheticMember
  private void $behaviorUnit$DemandeDeplacement$3(final DemandeDeplacement occurrence) {
    Collection<Boid> otherBoids = occurrence.otherBoids;
    Vector forceTot = null;
    boolean _notEquals = (!Objects.equal(otherBoids, null));
    if (_notEquals) {
      Vector _vector = new Vector(0, 0);
      forceTot = _vector;
      forceTot.plus(this.separation(otherBoids));
      forceTot.plus(this.cohesion(otherBoids));
      forceTot.plus(this.alignement(otherBoids));
      forceTot.plus(this.repulsion(otherBoids));
    }
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
    ResultatDeplacement _resultatDeplacement = new ResultatDeplacement(forceTot, this);
    _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_resultatDeplacement);
  }
  
  /**
   * GETTER
   */
  @Pure
  protected Vector getPosition() {
    return this.position;
  }
  
  @Pure
  protected Vector getVitesse() {
    return this.vitesse;
  }
  
  @Pure
  protected int getGroupe() {
    return this.groupe;
  }
  
  @Pure
  protected int getAngleVisibilite() {
    return this.angleVisibilite;
  }
  
  @Pure
  protected int getDistanceVisibilite() {
    return this.distanceVisibilite;
  }
  
  /**
   * SETTER
   */
  protected void setPosition(final Vector p) {
    this.position = p;
  }
  
  protected void setVitesse(final Vector v) {
    this.vitesse = v;
  }
  
  protected void setGroupe(final int i) {
    this.groupe = i;
  }
  
  protected void setAngleVisibilite(final int i) {
    this.angleVisibilite = i;
  }
  
  protected void setDistanceVisibilite(final int i) {
    this.distanceVisibilite = i;
  }
  
  /**
   * fonction qui permet de déterminer si le boids en question est visible dans notre perception
   */
  protected boolean visible(final Boid b) {
    Vector tmp = null;
    Vector tmp2 = null;
    Vector _vector = new Vector(b.position);
    tmp = _vector;
    tmp.moins(this.position);
    double _length = tmp.length();
    boolean _greaterThan = (_length > this.distanceVisibilite);
    if (_greaterThan) {
      return false;
    }
    Vector _vector_1 = new Vector(this.vitesse);
    tmp2 = _vector_1;
    tmp2.normaliser(this.groupeVitesseMax);
    tmp.normaliser(this.groupeVitesseMax);
    double _point = tmp2.point(tmp);
    boolean _lessThan = (_point < this.angleVisibilite);
    if (_lessThan) {
      return false;
    }
    return true;
  }
  
  /**
   * Création des fonctions comportementales du Boids
   */
  protected Vector separation(final Collection<Boid> otherBoids) {
    Vector force = null;
    Vector tmp = null;
    double len = 0;
    Vector _vector = new Vector(0, 0);
    force = _vector;
    Vector _vector_1 = new Vector(0, 0);
    tmp = _vector_1;
    for (final Boid boid : otherBoids) {
      if ((((!Objects.equal(boid, null)) && (boid.groupe == this.groupe)) && this.visible(boid))) {
        tmp.setXY(this.position);
        tmp.moins(boid.position);
        len = tmp.length();
        tmp.fois((1 / (len * len)));
        force.plus(tmp);
      }
    }
    return force;
  }
  
  protected Vector cohesion(final Collection<Boid> otherBoids) {
    int nbTot = 0;
    Vector force = null;
    Vector _vector = new Vector(0, 0);
    force = _vector;
    for (final Boid boid : otherBoids) {
      if ((((!Objects.equal(boid, null)) && (boid.groupe == this.groupe)) && this.visible(boid))) {
        nbTot++;
        force.plus(boid.position);
      }
    }
    if ((nbTot > 0)) {
      force.fois((1 / nbTot));
      force.moins(this.position);
    }
    return force;
  }
  
  protected Vector alignement(final Collection<Boid> otherBoids) {
    int nbTot = 0;
    Vector force = null;
    Vector tmp = null;
    Vector _vector = new Vector(0, 0);
    force = _vector;
    Vector _vector_1 = new Vector(0, 0);
    tmp = _vector_1;
    for (final Boid boid : otherBoids) {
      if ((((!Objects.equal(boid, null)) && (boid.groupe == this.groupe)) && this.visible(boid))) {
        nbTot++;
        tmp.setXY(boid.vitesse);
        double _length = tmp.length();
        double _divide = (1 / _length);
        tmp.fois(_divide);
        force.plus(tmp);
      }
    }
    if ((nbTot > 0)) {
      force.fois((1 / nbTot));
    }
    return force;
  }
  
  protected Vector repulsion(final Collection<Boid> otherBoids) {
    Vector force = null;
    Vector tmp = null;
    double len = 0;
    Vector _vector = new Vector(0, 0);
    force = _vector;
    Vector _vector_1 = new Vector(0, 0);
    tmp = _vector_1;
    for (final Boid boid : otherBoids) {
      if ((((!Objects.equal(boid, null)) && (boid.groupe != this.groupe)) && this.visible(boid))) {
        tmp.setXY(this.position);
        tmp.moins(boid.position);
        len = tmp.length();
        tmp.fois((1 / (len * len)));
        force.plus(tmp);
      }
    }
    return force;
  }
  
  @Extension
  @ImportedCapacityFeature(Logging.class)
  @SyntheticMember
  private transient ClearableReference<Skill> $CAPACITY_USE$IO_SARL_CORE_LOGGING;
  
  @SyntheticMember
  @Pure
  @Inline(value = "$castSkill(Logging.class, ($0$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || $0$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? ($0$CAPACITY_USE$IO_SARL_CORE_LOGGING = $0$getSkill(Logging.class)) : $0$CAPACITY_USE$IO_SARL_CORE_LOGGING)", imported = Logging.class)
  private Logging $CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = $getSkill(Logging.class);
    }
    return $castSkill(Logging.class, this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
  }
  
  @Extension
  @ImportedCapacityFeature(DefaultContextInteractions.class)
  @SyntheticMember
  private transient ClearableReference<Skill> $CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS;
  
  @SyntheticMember
  @Pure
  @Inline(value = "$castSkill(DefaultContextInteractions.class, ($0$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || $0$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? ($0$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = $0$getSkill(DefaultContextInteractions.class)) : $0$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS)", imported = DefaultContextInteractions.class)
  private DefaultContextInteractions $CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = $getSkill(DefaultContextInteractions.class);
    }
    return $castSkill(DefaultContextInteractions.class, this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
  }
  
  /**
   * gestion des évènements
   */
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Initialize(final Initialize occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Initialize$0(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$StartPosition(final StartPosition occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$StartPosition$1(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$ResultatDeplacement(final ResultatDeplacement occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$ResultatDeplacement$2(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$DemandeDeplacement(final DemandeDeplacement occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$DemandeDeplacement$3(occurrence));
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
    Boid other = (Boid) obj;
    if (other.groupe != this.groupe)
      return false;
    if (other.groupeVitesseMax != this.groupeVitesseMax)
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
    result = prime * result + this.angleVisibilite;
    result = prime * result + this.distanceVisibilite;
    return result;
  }
  
  @SyntheticMember
  public Boid(final UUID parentID, final UUID agentID) {
    super(parentID, agentID);
  }
  
  @SyntheticMember
  @Inject
  @Deprecated
  public Boid(final BuiltinCapacitiesProvider provider, final UUID parentID, final UUID agentID) {
    super(provider, parentID, agentID);
  }
  
  @SyntheticMember
  @Inject
  public Boid(final UUID parentID, final UUID agentID, final DynamicSkillProvider skillProvider) {
    super(parentID, agentID, skillProvider);
  }
}
