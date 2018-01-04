package fr.utbm.boids.agents;

import com.google.common.base.Objects;
import fr.utbm.boids.body.BoidBody;
import fr.utbm.boids.body.EnvInfos;
import fr.utbm.boids.environment.Obstacle;
import fr.utbm.boids.events.BoidInitialized;
import fr.utbm.boids.events.DemandeDeplacement;
import fr.utbm.boids.events.ResultatDeplacement;
import fr.utbm.boids.events.StartPosition;
import fr.utbm.boids.events.ValidationDeplacement;
import fr.utbm.boids.gui.fx.EndSimulation;
import fr.utbm.boids.util.Edge;
import fr.utbm.boids.util.Sphere;
import fr.utbm.boids.util.Vector;
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
import java.util.HashMap;
import java.util.List;
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
  /**
   * Le corps du boids
   */
  private BoidBody body;
  
  /**
   * L'ID du parent (l'environnement)
   */
  private UUID parentAgent;
  
  /**
   * Les informations relatives à l'environnement qui doivent être connues par le boids
   */
  private EnvInfos envInfos;
  
  @SyntheticMember
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    UUID _iD = this.getID();
    String _plus = ("Boid-" + _iD);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.setLoggingName(_plus);
    this.parentAgent = occurrence.spawner;
    UUID _iD_1 = this.getID();
    Object _get = occurrence.parameters[0];
    Object _get_1 = occurrence.parameters[1];
    Object _get_2 = occurrence.parameters[2];
    Object _get_3 = occurrence.parameters[3];
    Object _get_4 = occurrence.parameters[4];
    BoidBody _boidBody = new BoidBody(_iD_1, (((Integer) _get)).intValue(), (((Integer) _get_1)).intValue(), (((Integer) _get_2)).intValue(), (((Integer) _get_3)).intValue(), (((Integer) _get_4)).intValue());
    this.body = _boidBody;
    Object _get_5 = occurrence.parameters[5];
    this.envInfos = ((EnvInfos) _get_5);
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
  
  @SuppressWarnings("equals_with_null")
  @SyntheticMember
  private void $behaviorUnit$DemandeDeplacement$2(final DemandeDeplacement occurrence) {
    Map<UUID, BoidBody> boids = occurrence.boids;
    Vector forceTot = null;
    Vector _vector = new Vector(0, 0);
    forceTot = _vector;
    Map<UUID, BoidBody> visibleBoids = null;
    Map<UUID, BoidBody> closeBoids = null;
    closeBoids = this.perception(boids, "close");
    visibleBoids = this.perception(closeBoids, "visible");
    boolean _notEquals = (!Objects.equal(boids, null));
    if (_notEquals) {
      forceTot.plus(this.separation(closeBoids));
      forceTot.plus(this.cohesion(visibleBoids));
      forceTot.plus(this.alignement(visibleBoids));
      forceTot.plus(this.repulsion(visibleBoids));
      forceTot.plus(this.forceObstacles(occurrence.obstacles));
    }
    forceTot.fois(100000);
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
    Vector _appliquerForce = this.appliquerForce(forceTot);
    Vector _newVitesse = this.body.getNewVitesse();
    ResultatDeplacement _resultatDeplacement = new ResultatDeplacement(_appliquerForce, _newVitesse);
    _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_resultatDeplacement);
  }
  
  @SyntheticMember
  private void $behaviorUnit$ValidationDeplacement$3(final ValidationDeplacement occurrence) {
    this.body.setPosition(occurrence.position);
    this.body.setVitesse(this.body.getNewVitesse());
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
  
  /**
   * Fonction qui permet de déterminer si un boids est dans le cône de vision de notre boids.Basé uniquement sur l'angle de visibilité de la famille de boids.
   * @param b Le boids pour lequel on vérifie la présence dans le cône de vision.
   * @return true si le boids est dans le cône, false sinon.
   */
  protected boolean visible(final BoidBody b) {
    Vector tmp = null;
    Vector tmp2 = null;
    Vector _position = b.getPosition();
    Vector _vector = new Vector(_position);
    tmp = _vector;
    tmp.moins(this.body.getPosition());
    Vector _vitesse = this.body.getVitesse();
    Vector _vector_1 = new Vector(_vitesse);
    tmp2 = _vector_1;
    if (((tmp2.length() == 0) || (tmp.length() == 0))) {
      return false;
    }
    double _point = tmp2.point(tmp);
    double _length = tmp2.length();
    double _length_1 = tmp.length();
    double _multiply = (_length * _length_1);
    double _divide = (_point / _multiply);
    double _degrees = Math.toDegrees(Math.acos(_divide));
    int _angleVisibilite = this.body.getAngleVisibilite();
    boolean _greaterThan = (_degrees > _angleVisibilite);
    if (_greaterThan) {
      return false;
    } else {
      return true;
    }
  }
  
  /**
   * Fonction qui permet de dire si un boids est dans l'espace de perception de notre boids. Basé sur la distance de visibilité de la famille de boids.
   * @param b Le boids pour lequel on vérifie la présence dans le cercle de perception.
   * @return true si le boids est dans le cercle, false sinon.
   */
  protected boolean proche(final BoidBody b) {
    Vector tmp = null;
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
    return true;
  }
  
  /**
   * Fonction qui permet aux boids d'une même famille de garder une distance minimale entre eux.
   * @param boids La liste des boids qui sont dans le champ de perception et qui peuvent exercer une force sur notre propre boids.
   * @return La force de séparation résultante des intéractions avec les boids de la même famille.
   */
  @SuppressWarnings("equals_with_null")
  protected Vector separation(final Map<UUID, BoidBody> boids) {
    Vector force = null;
    Vector tmp = null;
    double len = 0;
    Vector _vector = new Vector(0, 0);
    force = _vector;
    Vector _vector_1 = new Vector(0, 0);
    tmp = _vector_1;
    Set<Map.Entry<UUID, BoidBody>> _entrySet = boids.entrySet();
    for (final Map.Entry<UUID, BoidBody> elem : _entrySet) {
      if (((!Objects.equal(elem.getKey(), null)) && (elem.getValue().getGroupe() == this.body.getGroupe()))) {
        tmp.setXY(this.body.getPosition());
        tmp.moins(elem.getValue().getPosition());
        len = tmp.length();
        tmp.fois((1 / (len * len)));
        force.plus(tmp);
        force.fois(35);
      }
    }
    return force;
  }
  
  /**
   * Fonction qui permet aux boids d'une même famille de se regrouper.
   * @param boids La liste des boids qui sont dans le cône de vision et qui peuvent exercer une force sur notre propre boids.
   * @return La force d'attraction résultante des intéractions avec les boids de la même famille.
   */
  @SuppressWarnings("equals_with_null")
  protected Vector cohesion(final Map<UUID, BoidBody> boids) {
    int nbTot = 0;
    Vector force = null;
    Vector _vector = new Vector(0, 0);
    force = _vector;
    Set<Map.Entry<UUID, BoidBody>> _entrySet = boids.entrySet();
    for (final Map.Entry<UUID, BoidBody> elem : _entrySet) {
      if (((!Objects.equal(elem.getKey(), null)) && (elem.getValue().getGroupe() == this.body.getGroupe()))) {
        nbTot++;
        force.plus(elem.getValue().getPosition());
      }
    }
    if ((nbTot > 0)) {
      force.fois((1 / nbTot));
      force.moins(this.body.getPosition());
    }
    force.fois(25);
    return force;
  }
  
  /**
   * Fonction qui permet aux boids d'une même famille de s'aligner.
   * @param boids La liste des boids qui sont dans le cône de vision et qui peuvent exercer une force sur notre propre boids.
   * @return La force d'alignement résultante des intéractions avec les boids de la même famille.
   */
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
      if (((!Objects.equal(elem.getKey(), null)) && (elem.getValue().getGroupe() == this.body.getGroupe()))) {
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
    force.fois(50);
    return force;
  }
  
  /**
   * Fonction qui permet aux boids de familles différentes de se repousser.
   * @param boids La liste des boids qui sont dans le cône de vision et qui peuvent exercer une force sur notre propre boids.
   * @return La force de répulsion résultante des intéractions avec les boids des autres familles.
   */
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
      if (((!Objects.equal(elem.getKey(), null)) && (elem.getValue().getGroupe() != this.body.getGroupe()))) {
        tmp.setXY(this.body.getPosition());
        tmp.moins(elem.getValue().getPosition());
        len = tmp.length();
        tmp.fois((1 / (len * len)));
        force.plus(tmp);
        force.fois(100);
      }
    }
    return force;
  }
  
  /**
   * Fonction qui permet aux boids de s'éloigner d'un obstacle.
   * On regarde si la droite d'un obstacle rentre dans notre champs de perception, si c'est le cas, on regarde quelle droite et la plus proche et
   * on choisit sa force normale comme force d'éloignement.
   * @param listeObstacles La liste des obstacles présents sur la map.
   * @return La force d'éloignement résultante des intéractions avec les obstacles proches.
   */
  @SuppressWarnings("equals_with_null")
  protected Vector forceObstacles(final List<Obstacle> listeObstacles) {
    Vector force = null;
    Vector _vector = new Vector(0, 0);
    force = _vector;
    Vector _position = this.body.getPosition();
    Vector _vector_1 = new Vector(_position);
    double _doubleValue = Integer.valueOf(this.body.getDistanceVisibilite()).doubleValue();
    Sphere sphereBoid = new Sphere(_vector_1, _doubleValue);
    if ((listeObstacles != null)) {
      for (final Obstacle o : listeObstacles) {
        {
          Vector forceAdd = null;
          double distanceMin = 0;
          distanceMin = 10000;
          Edge edgeRetenue = null;
          edgeRetenue = null;
          List<Edge> _edges = o.getEdges();
          for (final Edge e : _edges) {
            boolean _intersectRaySphere = this.intersectRaySphere(e, sphereBoid);
            if (_intersectRaySphere) {
              double _calculateDistancePointRay = this.calculateDistancePointRay(e, this.body.getPosition());
              boolean _lessThan = (_calculateDistancePointRay < distanceMin);
              if (_lessThan) {
                edgeRetenue = e;
                distanceMin = this.calculateDistancePointRay(e, this.body.getPosition());
              }
            }
          }
          boolean _notEquals = (!Objects.equal(edgeRetenue, null));
          if (_notEquals) {
            Vector _normal = edgeRetenue.getNormal();
            Vector _vector_2 = new Vector(_normal);
            forceAdd = _vector_2;
            forceAdd.fois((10000000 / distanceMin));
            force.plus(forceAdd);
          }
        }
      }
    }
    return force;
  }
  
  /**
   * Fonction permettant de définir la distance entre un point et une droite
   * D'après wikipédia : distance d'un point à une droite / Dans l'espace
   * @param edge La droite pour laquelle on doit mesurer la distance avec le point.
   * @param point Le point pour lequel on doit mesurer la distance avec la droite.
   * @return La distance entre le point et la droite passés en paramètre.
   */
  @Pure
  protected double calculateDistancePointRay(final Edge edge, final Vector point) {
    double distance = 0;
    double _x = point.getX();
    double _x_1 = edge.getPointDepart().getX();
    double _minus = (_x - _x_1);
    double _y = point.getY();
    double _y_1 = edge.getPointDepart().getY();
    double _minus_1 = (_y - _y_1);
    Vector vecteurBA = new Vector(_minus, _minus_1);
    distance = vecteurBA.produitVectoriel(edge.getDirection()).length();
    double _length = edge.getDirection().length();
    double _divide = (distance / _length);
    distance = _divide;
    return distance;
  }
  
  /**
   * Fonction permettant de savoir si une droite rentre en intersection avec une sphere.
   * D'après le pdf de Christer Ericson : Real Time Detection Collision.
   * @param edge La droite pour laquelle on test l'intersection avec la sphere.
   * @param s La sphere pour laquelle on test l'intersection avec la droite.
   * @return true si la droite rentre en collision avec la sphere, false sinon.
   */
  protected boolean intersectRaySphere(final Edge edge, final Sphere s) {
    Vector m = null;
    Vector directionNormalise = null;
    double _x = edge.getPointDepart().getX();
    double _y = edge.getPointDepart().getY();
    Vector _vector = new Vector(_x, _y);
    m = _vector;
    m.moins(s.getCenter());
    Vector _direction = edge.getDirection();
    Vector _vector_1 = new Vector(_direction);
    directionNormalise = _vector_1;
    directionNormalise.normaliser();
    double b = m.point(directionNormalise);
    double _point = m.point(m);
    double _radius = s.getRadius();
    double _radius_1 = s.getRadius();
    double _multiply = (_radius * _radius_1);
    double c = (_point - _multiply);
    if (((c > 0.0f) && (b > 0.0f))) {
      return false;
    }
    double discr = ((b * b) - c);
    if ((discr < 0.0f)) {
      return false;
    }
    double _sqrt = Math.sqrt(discr);
    double t = ((-b) - _sqrt);
    double _abs = Math.abs(t);
    double _length = edge.getDirection().length();
    boolean _greaterThan = (_abs > _length);
    if (_greaterThan) {
      return false;
    }
    return true;
  }
  
  /**
   * Fonction qui donne la nouvelle position du boids après application d'une force.
   * Stocke en mémoire la nouvelle vitesse du boids.
   * @param force La force appliquée sur le boids.
   * @return La nouvelle position du boids.
   */
  protected Vector appliquerForce(final Vector force) {
    Vector newPosition = null;
    Vector acceleration = null;
    double _length = force.length();
    boolean _greaterThan = (_length > 10);
    if (_greaterThan) {
      force.normaliser();
      force.fois(10);
    }
    Vector _vector = new Vector(force);
    acceleration = _vector;
    double _doubleValue = Integer.valueOf(this.body.getMasse()).doubleValue();
    double _divide = (1 / _doubleValue);
    acceleration.fois(_divide);
    Vector _vitesse = this.body.getVitesse();
    Vector _vector_1 = new Vector(_vitesse);
    this.body.setNewVitesse(_vector_1);
    this.body.getNewVitesse().plus(acceleration);
    double _length_1 = this.body.getNewVitesse().length();
    int _groupeVitesseMax = this.body.getGroupeVitesseMax();
    boolean _greaterThan_1 = (_length_1 > _groupeVitesseMax);
    if (_greaterThan_1) {
      this.body.getNewVitesse().normaliser();
      this.body.getNewVitesse().fois(this.body.getGroupeVitesseMax());
    }
    Vector _position = this.body.getPosition();
    Vector _vector_2 = new Vector(_position);
    newPosition = _vector_2;
    newPosition.plus(this.body.getNewVitesse());
    return newPosition;
  }
  
  /**
   * Fonction permettant de déterminer la liste des boids présent dans le champ de perception, ainsi que dans le champ de vision de notre boids.
   * @param boids La liste de tous les boids pour lesquels on va faire le test de proximité et de visibilité.
   * @param mode Le mode à utiliser : <br/>
   * - 'visible' pour faire un test de visibilité <br/>
   * - 'close' pour faire un test de proximité
   * @return La liste des boids qui répondent aux critères du paramètres (assez proche où dans l'angle de vision).
   */
  protected Map<UUID, BoidBody> perception(final Map<UUID, BoidBody> boids, final String mode) {
    if ((mode == "visible")) {
      HashMap<UUID, BoidBody> visibleBoids = new HashMap<UUID, BoidBody>();
      Set<Map.Entry<UUID, BoidBody>> _entrySet = boids.entrySet();
      for (final Map.Entry<UUID, BoidBody> elem : _entrySet) {
        if ((((elem.getKey() != null) && this.visible(elem.getValue())) && (!Objects.equal(elem.getKey(), this.getID())))) {
          visibleBoids.put(elem.getKey(), elem.getValue());
        }
      }
      return visibleBoids;
    } else {
      if ((mode == "close")) {
        HashMap<UUID, BoidBody> closeBoids = new HashMap<UUID, BoidBody>();
        Set<Map.Entry<UUID, BoidBody>> _entrySet_1 = boids.entrySet();
        for (final Map.Entry<UUID, BoidBody> elem_1 : _entrySet_1) {
          if ((((elem_1.getKey() != null) && this.proche(elem_1.getValue())) && (!Objects.equal(elem_1.getKey(), this.getID())))) {
            closeBoids.put(elem_1.getKey(), elem_1.getValue());
          }
        }
        return closeBoids;
      }
    }
    return null;
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
   * Evénement correspondant à la création du boids
   */
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Initialize(final Initialize occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Initialize$0(occurrence));
  }
  
  /**
   * Evénement qui permet aux boids de s'initialiser à un endroit aléatoire de la map avec une vitesse aléatoire.
   */
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$StartPosition(final StartPosition occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$StartPosition$1(occurrence));
  }
  
  /**
   * Evénement qui permet à un boids de mettre à jour ses informations après la validation d'un déplacement par l'environnement.
   * Les valeurs misent à jour sont la position et la vitesse.
   */
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$ValidationDeplacement(final ValidationDeplacement occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$ValidationDeplacement$3(occurrence));
  }
  
  /**
   * Evénement qui lance le calcul de la future position du boids.
   */
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$DemandeDeplacement(final DemandeDeplacement occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$DemandeDeplacement$2(occurrence));
  }
  
  /**
   * Evénement signalant la fin de  la simulation, qui aura pour conséquence de détruire notre agent.
   */
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
