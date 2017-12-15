package fr.utbm.boids.agents;

import com.google.common.base.Objects;
import fr.utbm.boids.BoidBody;
import fr.utbm.boids.EnvInfos;
import fr.utbm.boids.Vector;
import fr.utbm.boids.environment.Obstacle;
import fr.utbm.boids.events.BoidInitialized;
import fr.utbm.boids.events.DemandeDeplacement;
import fr.utbm.boids.events.ResultatDeplacement;
import fr.utbm.boids.events.StartPosition;
import fr.utbm.boids.events.ValidationDeplacement;
import fr.utbm.boids.gui.fx.EndSimulation;
import io.sarl.core.DefaultContextInteractions;
import io.sarl.core.Initialize;
import io.sarl.core.Lifecycle;
import io.sarl.core.Logging;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.PerceptGuardEvaluator;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.BuiltinCapacitiesProvider;
import io.sarl.lang.core.DynamicSkillProvider;
import io.sarl.lang.core.Scope;
import io.sarl.lang.core.Skill;
import io.sarl.lang.util.ClearableReference;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.geometry.Point2D;
import javax.inject.Inject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Inline;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure2;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(17)
@SuppressWarnings("all")
public class Boid extends Agent {
  private BoidBody body;
  
  private UUID parentAgent;
  
  private EnvInfos envInfos;
  
  @SyntheticMember
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    UUID _iD = this.getID();
    String _plus = ("Boid-" + _iD);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.setLoggingName(_plus);
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info("A Boid agent was started.");
    this.parentAgent = occurrence.spawner;
    UUID _iD_1 = this.getID();
    Object _get = occurrence.parameters[0];
    Object _get_1 = occurrence.parameters[1];
    Object _get_2 = occurrence.parameters[2];
    BoidBody _boidBody = new BoidBody(_iD_1, (((Integer) _get)).intValue(), 10, 2, (((Integer) _get_1)).intValue(), (((Integer) _get_2)).intValue());
    this.body = _boidBody;
    Object _get_3 = occurrence.parameters[3];
    this.envInfos = ((EnvInfos) _get_3);
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
    BoidInitialized _boidInitialized = new BoidInitialized(this.body, "Boid");
    _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_boidInitialized);
  }
  
  @SyntheticMember
  private void $behaviorUnit$StartPosition$1(final StartPosition occurrence) {
    AtomicBoolean validPosition = new AtomicBoolean(true);
    Vector maPosition = null;
    Random rnd = new Random();
    AtomicInteger x = new AtomicInteger(0);
    AtomicInteger y = new AtomicInteger(0);
    AtomicInteger vx = new AtomicInteger(0);
    AtomicInteger vy = new AtomicInteger(0);
    do {
      {
        validPosition.set(true);
        int _nextInt = rnd.nextInt(this.envInfos.getWidth());
        int _plus = (_nextInt + 1);
        x.set(_plus);
        int _nextInt_1 = rnd.nextInt(this.envInfos.getHeight());
        int _plus_1 = (_nextInt_1 + 1);
        y.set(_plus_1);
        final Procedure2<Obstacle, Integer> _function = (Obstacle o, Integer index) -> {
          int _get = x.get();
          int _get_1 = y.get();
          Point2D _point2D = new Point2D(_get, _get_1);
          boolean _contains = o.getPolygon().contains(_point2D);
          if (_contains) {
            validPosition.set(false);
          }
        };
        IterableExtensions.<Obstacle>forEach(occurrence.obstacles, _function);
      }
    } while((validPosition.get() == false));
    int _get = x.get();
    int _get_1 = y.get();
    Vector _vector = new Vector(_get, _get_1);
    maPosition = _vector;
    vx.set(rnd.nextInt(this.body.getGroupeVitesseMax()));
    vy.set(rnd.nextInt(this.body.getGroupeVitesseMax()));
    int _get_2 = vx.get();
    int _get_3 = vy.get();
    Vector _vector_1 = new Vector(_get_2, _get_3);
    this.body.setNewVitesse(_vector_1);
    double _length = this.body.getNewVitesse().length();
    int _groupeVitesseMax = this.body.getGroupeVitesseMax();
    boolean _greaterThan = (_length > _groupeVitesseMax);
    if (_greaterThan) {
      this.body.getNewVitesse().normaliser();
      this.body.getNewVitesse().fois(this.body.getGroupeVitesseMax());
    }
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
    Vector _newVitesse = this.body.getNewVitesse();
    ResultatDeplacement _resultatDeplacement = new ResultatDeplacement(maPosition, _newVitesse);
    _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_resultatDeplacement);
  }
  
  @SyntheticMember
  private void $behaviorUnit$ValidationDeplacement$2(final ValidationDeplacement occurrence) {
    this.body.setPosition(occurrence.position);
    this.body.setVitesse(this.body.getNewVitesse());
  }
  
  @SuppressWarnings("equals_with_null")
  @SyntheticMember
  private void $behaviorUnit$DemandeDeplacement$3(final DemandeDeplacement occurrence) {
    Map<UUID, BoidBody> boids = occurrence.boids;
    Vector forceTot = null;
    Vector _vector = new Vector(0, 0);
    forceTot = _vector;
    boolean _notEquals = (!Objects.equal(boids, null));
    if (_notEquals) {
      forceTot.plus(this.separation(boids));
      forceTot.plus(this.cohesion(boids));
      forceTot.plus(this.alignement(boids));
      forceTot.plus(this.repulsion(boids));
    }
    forceTot.fois(100000);
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
    Vector _appliquerForce = this.appliquerForce(forceTot);
    Vector _newVitesse = this.body.getNewVitesse();
    ResultatDeplacement _resultatDeplacement = new ResultatDeplacement(_appliquerForce, _newVitesse);
    _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_resultatDeplacement);
  }
  
  /**
   * fonction qui permet de déterminer si le boids en question est visible dans notre perception
   */
  protected boolean visible(final BoidBody b) {
    Vector tmp = null;
    Vector tmp2 = null;
    Vector _position = b.getPosition();
    Vector _vector = new Vector(_position);
    tmp = _vector;
    tmp.moins(this.body.getPosition());
    double _length = tmp.length();
    int _distanceVisibilite = this.body.getDistanceVisibilite();
    boolean _greaterThan = (_length > _distanceVisibilite);
    if (_greaterThan) {
      return false;
    }
    Vector _vitesse = this.body.getVitesse();
    Vector _vector_1 = new Vector(_vitesse);
    tmp2 = _vector_1;
    double _point = tmp2.point(tmp);
    double _length_1 = tmp2.length();
    double _length_2 = tmp.length();
    double _multiply = (_length_1 * _length_2);
    double _divide = (_point / _multiply);
    double _abs = Math.abs(Math.toDegrees(Math.acos(_divide)));
    int _angleVisibilite = this.body.getAngleVisibilite();
    boolean _greaterThan_1 = (_abs > _angleVisibilite);
    if (_greaterThan_1) {
      return false;
    }
    return true;
  }
  
  /**
   * Création des fonctions comportementales du Boids
   */
  @SuppressWarnings("equals_with_null")
  protected Vector separation(final Map<UUID, BoidBody> boids) {
    Vector force = null;
    Vector tmp = null;
    double len = 0;
    double xelem = 0;
    double yelem = 0;
    Vector tmpelem = null;
    Vector _vector = new Vector(0, 0);
    force = _vector;
    Vector _vector_1 = new Vector(0, 0);
    tmp = _vector_1;
    Set<Map.Entry<UUID, BoidBody>> _entrySet = boids.entrySet();
    for (final Map.Entry<UUID, BoidBody> elem : _entrySet) {
      if (((((!Objects.equal(elem.getKey(), null)) && (elem.getValue().getGroupe() == this.body.getGroupe())) && this.visible(elem.getValue())) && (!Objects.equal(elem.getKey(), this.getID())))) {
        int _width = this.envInfos.getWidth();
        double _modulo = (xelem % _width);
        int _height = this.envInfos.getHeight();
        double _modulo_1 = (yelem % _height);
        Vector _vector_2 = new Vector(_modulo, _modulo_1);
        tmpelem = _vector_2;
        tmp.setXY(this.body.getPosition());
        tmp.moins(elem.getValue().getPosition());
        len = tmp.length();
        tmp.fois((1 / (len * len)));
        force.plus(tmp);
      }
    }
    return force;
  }
  
  @SuppressWarnings("equals_with_null")
  protected Vector cohesion(final Map<UUID, BoidBody> boids) {
    int nbTot = 0;
    Vector force = null;
    Vector _vector = new Vector(0, 0);
    force = _vector;
    Set<Map.Entry<UUID, BoidBody>> _entrySet = boids.entrySet();
    for (final Map.Entry<UUID, BoidBody> elem : _entrySet) {
      if (((((!Objects.equal(elem.getKey(), null)) && (!Objects.equal(elem.getKey(), this.getID()))) && (elem.getValue().getGroupe() == this.body.getGroupe())) && this.visible(elem.getValue()))) {
        nbTot++;
        force.plus(elem.getValue().getPosition());
      }
    }
    if ((nbTot > 0)) {
      force.fois((1 / nbTot));
      force.moins(this.body.getPosition());
    }
    return force;
  }
  
  @SuppressWarnings("equals_with_null")
  protected Vector alignement(final Map<UUID, BoidBody> boids) {
    int nbTot = 0;
    Vector force = null;
    Vector tmp = null;
    Vector _vector = new Vector(0, 0);
    force = _vector;
    Vector _vector_1 = new Vector(0, 0);
    tmp = _vector_1;
    Set<Map.Entry<UUID, BoidBody>> _entrySet = boids.entrySet();
    for (final Map.Entry<UUID, BoidBody> elem : _entrySet) {
      if (((((!Objects.equal(elem.getKey(), null)) && (!Objects.equal(elem.getKey(), this.getID()))) && (elem.getValue().getGroupe() == this.body.getGroupe())) && this.visible(elem.getValue()))) {
        nbTot++;
        tmp.setXY(elem.getValue().getPosition());
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
  
  @SuppressWarnings("equals_with_null")
  protected Vector repulsion(final Map<UUID, BoidBody> boids) {
    Vector force = null;
    Vector tmp = null;
    double len = 0;
    Vector _vector = new Vector(0, 0);
    force = _vector;
    Vector _vector_1 = new Vector(0, 0);
    tmp = _vector_1;
    Set<Map.Entry<UUID, BoidBody>> _entrySet = boids.entrySet();
    for (final Map.Entry<UUID, BoidBody> elem : _entrySet) {
      if ((((!Objects.equal(elem.getKey(), null)) && (elem.getValue().getGroupe() != this.body.getGroupe())) && this.visible(elem.getValue()))) {
        tmp.setXY(this.body.getPosition());
        tmp.moins(elem.getValue().getPosition());
        len = tmp.length();
        tmp.fois((1 / (len * len)));
        force.plus(tmp);
      }
    }
    return force;
  }
  
  protected Vector appliquerForce(final Vector force) {
    Vector newPosition = null;
    Vector acceleration = null;
    Vector _vector = new Vector(force);
    acceleration = _vector;
    Vector _vitesse = this.body.getVitesse();
    Vector _vector_1 = new Vector(_vitesse);
    this.body.setNewVitesse(_vector_1);
    this.body.getNewVitesse().plus(acceleration);
    double _length = this.body.getNewVitesse().length();
    int _groupeVitesseMax = this.body.getGroupeVitesseMax();
    boolean _greaterThan = (_length > _groupeVitesseMax);
    if (_greaterThan) {
      this.body.getNewVitesse().normaliser();
      this.body.getNewVitesse().fois(this.body.getGroupeVitesseMax());
    }
    Vector _position = this.body.getPosition();
    Vector _vector_2 = new Vector(_position);
    newPosition = _vector_2;
    newPosition.plus(this.body.getNewVitesse());
    return newPosition;
  }
  
  @Pure
  protected double distance(final double XA, final double XB) {
    return Math.abs((XA - XB));
  }
  
  @SyntheticMember
  private void $behaviorUnit$EndSimulation$4(final EndSimulation occurrence) {
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
    EndSimulation _endSimulation = new EndSimulation();
    final Scope<Address> _function = (Address it) -> {
      UUID _uUID = it.getUUID();
      return Objects.equal(_uUID, this.parentAgent);
    };
    _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_endSimulation, _function);
    Lifecycle _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER = this.$castSkill(Lifecycle.class, (this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE == null || this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE = this.$getSkill(Lifecycle.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE);
    _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER.killMe();
  }
  
  @SyntheticMember
  @Pure
  private boolean $behaviorUnitGuard$EndSimulation$4(final EndSimulation it, final EndSimulation occurrence) {
    boolean _isFrom = it.isFrom(this.parentAgent);
    return _isFrom;
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
  
  @Extension
  @ImportedCapacityFeature(Lifecycle.class)
  @SyntheticMember
  private transient ClearableReference<Skill> $CAPACITY_USE$IO_SARL_CORE_LIFECYCLE;
  
  @SyntheticMember
  @Pure
  @Inline(value = "$castSkill(Lifecycle.class, ($0$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE == null || $0$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE.get() == null) ? ($0$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE = $0$getSkill(Lifecycle.class)) : $0$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE)", imported = Lifecycle.class)
  private Lifecycle $CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE == null || this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE = $getSkill(Lifecycle.class);
    }
    return $castSkill(Lifecycle.class, this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE);
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
  private void $guardEvaluator$ValidationDeplacement(final ValidationDeplacement occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$ValidationDeplacement$2(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$DemandeDeplacement(final DemandeDeplacement occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$DemandeDeplacement$3(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$EndSimulation(final EndSimulation occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    if ($behaviorUnitGuard$EndSimulation$4(occurrence, occurrence)) {
      ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$EndSimulation$4(occurrence));
    }
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
    if (!java.util.Objects.equals(this.parentAgent, other.parentAgent)) {
      return false;
    }
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + java.util.Objects.hashCode(this.parentAgent);
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
