package fr.utbm.boids.gui;

import com.google.common.base.Objects;
import com.google.common.util.concurrent.AtomicDouble;
import fr.utbm.boids.BoidBody;
import fr.utbm.boids.Configuration;
import fr.utbm.boids.environment.Obstacle;
import fr.utbm.boids.events.ConfigureSimulation;
import fr.utbm.boids.events.Pause;
import fr.utbm.boids.events.Resume;
import fr.utbm.boids.gui.fx.FxViewerController;
import fr.utbm.boids.util.Coordinates;
import fr.utbm.boids.util.LineTool;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure0;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure2;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(9)
@SuppressWarnings("all")
public class BoidsFxViewerController extends FxViewerController {
  private boolean launched = false;
  
  private boolean mapCreated = false;
  
  @FXML
  private Pane main_pane;
  
  @FXML
  private Group boids_group;
  
  @FXML
  private Pane UI_pane;
  
  @FXML
  private Group obstacle_group;
  
  @FXML
  private Group perception_group;
  
  @FXML
  private ToggleButton toggle_night_mode;
  
  @FXML
  private ToggleButton toggle_perception;
  
  @FXML
  private Button start_button;
  
  @FXML
  private Circle night_mode_indicator;
  
  @FXML
  private Circle perception_indicator;
  
  @FXML
  private Label boids_quantity_label;
  
  @FXML
  private Label boids_population_label;
  
  @FXML
  private Label boids_vision_label;
  
  @FXML
  private Label boids_distance_deplacement_label;
  
  @FXML
  private ScrollBar boids_quantity_input;
  
  @FXML
  private TextField boids_population_input;
  
  @FXML
  private ScrollBar boids_vision_input;
  
  @FXML
  private ScrollBar boids_distance_deplacement_input;
  
  @FXML
  private Label boids_quantity_display;
  
  @FXML
  private Label boids_vision_display;
  
  @FXML
  private Label boids_distance_deplacement_display;
  
  @FXML
  private Label boids_quantity_min;
  
  @FXML
  private Label boids_quantity_max;
  
  @FXML
  private Label boids_vision_min;
  
  @FXML
  private Label boids_vision_max;
  
  @FXML
  private Label boids_distance_deplacement_min;
  
  @FXML
  private Label boids_distance_deplacement_max;
  
  @FXML
  private Rectangle pause_button;
  
  @FXML
  private Polygon resume_button;
  
  @FXML
  private Label boid_group;
  
  @FXML
  private Label boid_vitesse;
  
  @FXML
  private Label boid_new_vitesse;
  
  @FXML
  private Label boid_group_vitesse;
  
  @FXML
  private Label boid_angle;
  
  @FXML
  private Label boid_distance;
  
  @FXML
  private Label boid_masse;
  
  @FXML
  private Label boid_position;
  
  @FXML
  private Button hide_infos;
  
  @FXML
  private Pane boids_infos_pane;
  
  @FXML
  private Line boids_population_line;
  
  @FXML
  private Label increment_boids_population;
  
  @FXML
  private Label decrement_boids_population;
  
  @FXML
  private Circle boids_population_increment_circle;
  
  @FXML
  private Circle boids_population_decrement_circle;
  
  @FXML
  private ImageView preview_map_1;
  
  @FXML
  private ImageView preview_map_2;
  
  @FXML
  private Rectangle preview_map_1_border;
  
  @FXML
  private Rectangle preview_map_2_border;
  
  @FXML
  private Label map_label_1;
  
  @FXML
  private Label map_label_2;
  
  @FXML
  private Pane simulation_parameters_pane;
  
  @FXML
  private Pane map_selection_pane;
  
  @FXML
  private ImageView tick_map_1;
  
  @FXML
  private ImageView tick_map_2;
  
  private int selectedMap = 1;
  
  private List<Polygon> polygons;
  
  private List<List<Coordinates>> polygonsCoordinates;
  
  private List<Obstacle> obstacles = new ArrayList<Obstacle>();
  
  private Boolean nightMode = Boolean.valueOf(true);
  
  private Boolean togglePerception = Boolean.valueOf(true);
  
  private UUID currentBoid;
  
  private List<Arc> perceptions = new ArrayList<Arc>();
  
  @Pure
  public int getBoidsQuantity() {
    double _value = this.boids_quantity_input.getValue();
    return ((int) _value);
  }
  
  @Pure
  public int getMapSelection() {
    return this.selectedMap;
  }
  
  @Pure
  public int getBoidsPopulation() {
    return Integer.parseInt(this.boids_population_input.getText());
  }
  
  @Pure
  public int getBoidsVision() {
    double _value = this.boids_vision_input.getValue();
    return ((int) _value);
  }
  
  @Pure
  public int getMapWidth() {
    double _width = this.main_pane.getWidth();
    return ((int) _width);
  }
  
  @Pure
  public int getMapHeight() {
    double _height = this.main_pane.getHeight();
    return ((int) _height);
  }
  
  @Pure
  public List<Obstacle> getObstacles() {
    return this.obstacles;
  }
  
  @Pure
  public int getBoidsDistanceDeplacement() {
    double _value = this.boids_distance_deplacement_input.getValue();
    return ((int) _value);
  }
  
  @FXML
  protected void startSimu() {
    if (((!Objects.equal(this.boids_population_input.getText(), "")) && (this.outputQuality(this.boids_population_input.getText())).booleanValue())) {
      int _mapSelection = this.getMapSelection();
      int _boidsQuantity = this.getBoidsQuantity();
      int _boidsPopulation = this.getBoidsPopulation();
      int _boidsVision = this.getBoidsVision();
      ConfigureSimulation event = new ConfigureSimulation(_mapSelection, _boidsQuantity, _boidsPopulation, _boidsVision);
      if ((!this.launched)) {
        final Procedure0 _function = () -> {
          this.emitToAgents(event);
        };
        this.startAgentApplication(_function);
        this.launched = true;
        this.mapCreated = false;
        this.toggleUIState();
        this.toggleMenuUIVisibility();
        this.toggleSimuUIVisibility();
        BackgroundFill _backgroundFill = new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY);
        Background _background = new Background(_backgroundFill);
        this.toggle_night_mode.setBackground(_background);
        if ((this.nightMode).booleanValue()) {
          this.toggle_night_mode.setTextFill(Color.rgb(191, 191, 191, 0.3));
        } else {
          this.toggle_night_mode.setTextFill(Color.rgb(0, 0, 0, 0.3));
        }
      } else {
        this.emitToAgents(event);
      }
    }
  }
  
  public List<Obstacle> buildMap(final int map) {
    abstract class __BoidsFxViewerController_0 implements Runnable {
      public abstract void run();
    }
    
    this.pause_button.setVisible(true);
    if ((map == 1)) {
      return new ArrayList<Obstacle>();
    } else {
      if ((map == 2)) {
        ArrayList<Polygon> _arrayList = new ArrayList<Polygon>();
        this.polygons = _arrayList;
        ArrayList<List<Coordinates>> _arrayList_1 = new ArrayList<List<Coordinates>>();
        this.polygonsCoordinates = _arrayList_1;
        ArrayList<Obstacle> _arrayList_2 = new ArrayList<Obstacle>();
        this.obstacles = _arrayList_2;
        Polygon _polygon = new Polygon(250.0, 200.0, 365.0, 250.0, 400.0, 300.0, 325.0, 400.0, 205.0, 225.0);
        this.polygons.add(_polygon);
        Polygon _polygon_1 = new Polygon(605.0, 80.0, 675.0, 65.0, 680.0, 125.0, 650.0, 220.0, 630.0, 250.0, 660.0, 130.0, 
          665.0, 75.0, 615.0, 95.0, 560.0, 240.0, 560.0, 205.0, 605.0, 80.0);
        this.polygons.add(_polygon_1);
        Polygon _polygon_2 = new Polygon(450.0, 450.0, 575.0, 500.0, 575.0, 420.0, 700.0, 500.0, 590.0, 450.0, 590.0, 520.0);
        this.polygons.add(_polygon_2);
        final Consumer<Polygon> _function = (Polygon p) -> {
          this.polygonsCoordinates.add(this.generateCoordinates(p));
          p.setFill(Color.GRAY);
          p.setStroke(Color.TRANSPARENT);
          p.setStrokeWidth(20);
        };
        this.polygons.forEach(_function);
        __BoidsFxViewerController_0 command = new __BoidsFxViewerController_0() {
          @Override
          public void run() {
            final Consumer<Polygon> _function = (Polygon p) -> {
              BoidsFxViewerController.this.obstacle_group.getChildren().add(0, p);
            };
            BoidsFxViewerController.this.polygons.forEach(_function);
          }
        };
        boolean _isFxApplicationThread = Platform.isFxApplicationThread();
        if (_isFxApplicationThread) {
          command.run();
          this.generateObstacles();
          return this.obstacles;
        } else {
          Platform.runLater(command);
          this.generateObstacles();
          return this.obstacles;
        }
      }
    }
    return null;
  }
  
  @Pure
  public List<Coordinates> generateCoordinates(final Polygon p) {
    AtomicDouble abscissa = new AtomicDouble();
    List<Coordinates> coordinates = new ArrayList<Coordinates>();
    final Procedure2<Double, Integer> _function = (Double ordered, Integer index) -> {
      if (((index % 2) == 0)) {
        abscissa.set((ordered).doubleValue());
      } else {
        double _get = abscissa.get();
        double _doubleValue = ordered.doubleValue();
        Coordinates _coordinates = new Coordinates(_get, _doubleValue);
        coordinates.add(_coordinates);
      }
    };
    IterableExtensions.<Double>forEach(p.getPoints(), _function);
    return coordinates;
  }
  
  @Pure
  public void generateObstacles() {
    final Procedure2<List<Coordinates>, Integer> _function = (List<Coordinates> p, Integer currentItem) -> {
      List<LineTool> lines = new ArrayList<LineTool>();
      final Procedure2<Coordinates, Integer> _function_1 = (Coordinates c, Integer index) -> {
        if ((index != 0)) {
          Coordinates _get = p.get((index - 1));
          LineTool line = new LineTool(_get, c);
          line.computeLineEquation();
          lines.add(line);
        }
      };
      IterableExtensions.<Coordinates>forEach(p, _function_1);
      Coordinates _last = IterableExtensions.<Coordinates>last(p);
      Coordinates _get = p.get(0);
      LineTool line = new LineTool(_last, _get);
      line.computeLineEquation();
      lines.add(line);
      Polygon _get_1 = this.polygons.get(currentItem);
      Obstacle _obstacle = new Obstacle(lines, _get_1);
      this.obstacles.add(_obstacle);
    };
    IterableExtensions.<List<Coordinates>>forEach(this.polygonsCoordinates, _function);
  }
  
  public void updateGraphics(final Collection<BoidBody> list) {
    abstract class __BoidsFxViewerController_1 implements Runnable {
      public abstract void run();
    }
    
    __BoidsFxViewerController_1 command = new __BoidsFxViewerController_1() {
      @Override
      public void run() {
        abstract class ____BoidsFxViewerController_1_0_1 implements EventHandler<MouseEvent> {
          public abstract void handle(final MouseEvent event);
        }
        
        BoidsFxViewerController.this.perception_group.getChildren().clear();
        BoidsFxViewerController.this.boids_group.getChildren().clear();
        BoidsFxViewerController.this.perceptions.clear();
        for (final BoidBody boid : list) {
          {
            double _x = boid.getPosition().getX();
            int _mapHeight = BoidsFxViewerController.this.getMapHeight();
            double _y = boid.getPosition().getY();
            double _minus = (_mapHeight - _y);
            double _minus_1 = (_minus - 7.5);
            double _x_1 = boid.getPosition().getX();
            double _plus = (_x_1 + 5);
            int _mapHeight_1 = BoidsFxViewerController.this.getMapHeight();
            double _y_1 = boid.getPosition().getY();
            double _minus_2 = (_mapHeight_1 - _y_1);
            double _plus_1 = (_minus_2 + 7.5);
            double _x_2 = boid.getPosition().getX();
            double _minus_3 = (_x_2 - 5);
            int _mapHeight_2 = BoidsFxViewerController.this.getMapHeight();
            double _y_2 = boid.getPosition().getY();
            double _minus_4 = (_mapHeight_2 - _y_2);
            double _plus_2 = (_minus_4 + 7.5);
            Polygon boidElement = new Polygon(_x, _minus_1, _plus, _plus_1, _minus_3, _plus_2);
            double _x_3 = boid.getVitesse().getX();
            double _y_3 = boid.getVitesse().getY();
            double _divide = (_x_3 / _y_3);
            double angleRotation = Math.toDegrees(Math.atan(_divide));
            if ((angleRotation < 0)) {
              double _angleRotation = angleRotation;
              angleRotation = (_angleRotation + 180);
            }
            double _x_4 = boid.getVitesse().getX();
            boolean _lessThan = (_x_4 < 0);
            if (_lessThan) {
              double _y_4 = boid.getVitesse().getY();
              boolean _lessThan_1 = (_y_4 < 0);
              if (_lessThan_1) {
                double _angleRotation_1 = angleRotation;
                angleRotation = (_angleRotation_1 - 180);
              } else {
                double _angleRotation_2 = angleRotation;
                angleRotation = (_angleRotation_2 + 180);
              }
            }
            boidElement.setRotate(angleRotation);
            boidElement.setFill(Configuration.COLOR_FAMILY.get(Integer.valueOf(boid.getGroupe())));
            ____BoidsFxViewerController_1_0_1 _____BoidsFxViewerController_1_0_1 = new ____BoidsFxViewerController_1_0_1() {
              public void handle(final MouseEvent event) {
                BoidsFxViewerController.this.currentBoid = boid.getID();
                BoidsFxViewerController.this.updateInfos(boid);
                BoidsFxViewerController.this.showInfosVisibility();
              }
            };
            boidElement.setOnMousePressed(_____BoidsFxViewerController_1_0_1);
            if ((BoidsFxViewerController.this.togglePerception).booleanValue()) {
              Arc perceptionArc = new Arc();
              perceptionArc.setCenterX(boid.getPosition().getX());
              int _mapHeight_3 = BoidsFxViewerController.this.getMapHeight();
              double _y_5 = boid.getPosition().getY();
              double _minus_5 = (_mapHeight_3 - _y_5);
              perceptionArc.setCenterY(_minus_5);
              perceptionArc.setRadiusX(Double.parseDouble(BoidsFxViewerController.this.boids_distance_deplacement_display.getText()));
              perceptionArc.setRadiusY(Double.parseDouble(BoidsFxViewerController.this.boids_distance_deplacement_display.getText()));
              double _parseDouble = Double.parseDouble(BoidsFxViewerController.this.boids_vision_display.getText());
              double _minus_6 = ((90 - angleRotation) - _parseDouble);
              perceptionArc.setStartAngle(_minus_6);
              double _parseDouble_1 = Double.parseDouble(BoidsFxViewerController.this.boids_vision_display.getText());
              double _multiply = (_parseDouble_1 * 2);
              perceptionArc.setLength(_multiply);
              perceptionArc.setType(ArcType.ROUND);
              if ((BoidsFxViewerController.this.nightMode).booleanValue()) {
                perceptionArc.setFill(Color.rgb(255, 245, 112, 0.2));
              } else {
                perceptionArc.setFill(Color.rgb(255, 245, 112, 0.8));
              }
              BoidsFxViewerController.this.boids_group.getChildren().add(0, boidElement);
              BoidsFxViewerController.this.perception_group.getChildren().add(0, perceptionArc);
              BoidsFxViewerController.this.perceptions.add(perceptionArc);
            } else {
              BoidsFxViewerController.this.boids_group.getChildren().add(0, boidElement);
            }
          }
        }
        boolean _isVisible = BoidsFxViewerController.this.boids_infos_pane.isVisible();
        if (_isVisible) {
          final Function1<BoidBody, Boolean> _function = (BoidBody item) -> {
            UUID _iD = item.getID();
            return Boolean.valueOf(Objects.equal(_iD, BoidsFxViewerController.this.currentBoid));
          };
          BoidBody boidBody = IterableExtensions.<BoidBody>findFirst(list, _function);
          BoidsFxViewerController.this.updateInfos(boidBody);
        }
      }
    };
    boolean _isFxApplicationThread = Platform.isFxApplicationThread();
    if (_isFxApplicationThread) {
      command.run();
    } else {
      Platform.runLater(command);
    }
  }
  
  @FXML
  protected void actionBoidsQuantityDisplay() {
    final InvalidationListener _function = (Observable it) -> {
      this.boids_quantity_display.setText(String.format("%.0f", Double.valueOf(this.boids_quantity_input.getValue())));
    };
    this.boids_quantity_input.valueProperty().addListener(_function);
  }
  
  @FXML
  protected void actionBoidsVisionDisplay() {
    final InvalidationListener _function = (Observable it) -> {
      this.boids_vision_display.setText(String.format("%.0f", Double.valueOf(this.boids_vision_input.getValue())));
    };
    this.boids_vision_input.valueProperty().addListener(_function);
  }
  
  @FXML
  protected void actionBoidsDistanceDeplacementDisplay() {
    final InvalidationListener _function = (Observable it) -> {
      this.boids_distance_deplacement_display.setText(String.format("%.0f", Double.valueOf(this.boids_distance_deplacement_input.getValue())));
    };
    this.boids_distance_deplacement_input.valueProperty().addListener(_function);
  }
  
  @FXML
  protected void resetBoidsPopulation() {
    this.boids_population_input.setText("");
  }
  
  @FXML
  protected void updateBoidsPopulation() {
    String _text = this.boids_population_input.getText();
    boolean _notEquals = (!Objects.equal(_text, ""));
    if (_notEquals) {
      Boolean _outputQuality = this.outputQuality(this.boids_population_input.getText());
      if ((_outputQuality).booleanValue()) {
        int currentValue = Integer.parseInt(this.boids_population_input.getText());
        if ((currentValue >= 10)) {
          this.boids_population_input.setText(("" + Integer.valueOf(10)));
        } else {
          if ((currentValue <= 1)) {
            this.boids_population_input.setText(("" + Integer.valueOf(1)));
          }
        }
      }
    }
  }
  
  @FXML
  protected void incrementBoidsPopulation() {
    if (((!Objects.equal(this.boids_population_input.getText(), "")) && (this.outputQuality(this.boids_population_input.getText())).booleanValue())) {
      int currentValue = Integer.parseInt(this.boids_population_input.getText());
      if ((currentValue >= 10)) {
        this.boids_population_input.setText(("" + Integer.valueOf(10)));
      } else {
        this.boids_population_input.setText(("" + Integer.valueOf((currentValue + 1))));
      }
    } else {
      this.boids_population_input.setText(("" + Integer.valueOf(1)));
    }
  }
  
  @FXML
  protected void decrementBoidsPopulation() {
    if (((!Objects.equal(this.boids_population_input.getText(), "")) && (this.outputQuality(this.boids_population_input.getText())).booleanValue())) {
      int currentValue = Integer.parseInt(this.boids_population_input.getText());
      if ((currentValue <= 1)) {
        this.boids_population_input.setText(("" + Integer.valueOf(1)));
      } else {
        this.boids_population_input.setText(("" + Integer.valueOf((currentValue - 1))));
      }
    } else {
      this.boids_population_input.setText(("" + Integer.valueOf(1)));
    }
  }
  
  @FXML
  protected void toggleMode() {
    if ((this.nightMode).booleanValue()) {
      this.nightMode = Boolean.valueOf(false);
      this.night_mode_indicator.setFill(Color.TRANSPARENT);
      this.night_mode_indicator.setStroke(Color.rgb(0, 0, 0, 0.3));
      this.perception_indicator.setStroke(Color.rgb(0, 0, 0, 0.3));
      Color normalTextColor = Color.BLACK;
      Color _rgb = Color.rgb(244, 244, 244);
      BackgroundFill _backgroundFill = new BackgroundFill(_rgb, CornerRadii.EMPTY, Insets.EMPTY);
      Background _background = new Background(_backgroundFill);
      this.main_pane.setBackground(_background);
      this.boids_quantity_label.setTextFill(normalTextColor);
      this.boids_quantity_display.setTextFill(normalTextColor);
      this.boids_quantity_min.setTextFill(normalTextColor);
      this.boids_quantity_max.setTextFill(normalTextColor);
      this.boids_population_label.setTextFill(normalTextColor);
      this.decrement_boids_population.setTextFill(normalTextColor);
      this.increment_boids_population.setTextFill(normalTextColor);
      this.boids_population_decrement_circle.setStroke(normalTextColor);
      this.boids_population_increment_circle.setStroke(normalTextColor);
      this.boids_population_line.setStroke(normalTextColor);
      this.boids_population_input.setStyle("-fx-text-fill: rgb(0, 0, 0); -fx-background-color: transparent");
      this.boids_vision_label.setTextFill(normalTextColor);
      this.boids_vision_display.setTextFill(normalTextColor);
      this.boids_vision_min.setTextFill(normalTextColor);
      this.boids_vision_max.setTextFill(normalTextColor);
      this.boids_distance_deplacement_label.setTextFill(normalTextColor);
      this.boids_distance_deplacement_display.setTextFill(normalTextColor);
      this.boids_distance_deplacement_min.setTextFill(normalTextColor);
      this.boids_distance_deplacement_max.setTextFill(normalTextColor);
      this.boid_group.setTextFill(Color.rgb(0, 0, 0, 0.7));
      this.boid_vitesse.setTextFill(Color.rgb(0, 0, 0, 0.7));
      this.boid_group_vitesse.setTextFill(Color.rgb(0, 0, 0, 0.7));
      this.boid_masse.setTextFill(Color.rgb(0, 0, 0, 0.7));
      this.boid_angle.setTextFill(Color.rgb(0, 0, 0, 0.7));
      this.boid_distance.setTextFill(Color.rgb(0, 0, 0, 0.7));
      this.boid_new_vitesse.setTextFill(Color.rgb(0, 0, 0, 0.7));
      this.boid_position.setTextFill(Color.rgb(0, 0, 0, 0.7));
      this.toggle_night_mode.setTextFill(Color.rgb(0, 0, 0, 0.3));
      this.toggle_perception.setTextFill(Color.rgb(0, 0, 0, 0.3));
      this.hide_infos.setTextFill(Color.rgb(0, 0, 0, 0.3));
      this.start_button.setTextFill(normalTextColor);
      if ((this.selectedMap != 1)) {
        this.preview_map_1_border.setStroke(Color.rgb(0, 0, 0, 0.6));
        this.map_label_1.setTextFill(normalTextColor);
      } else {
        if ((this.selectedMap == 1)) {
          this.preview_map_1_border.setStroke(Color.rgb(0, 0, 0));
          this.map_label_1.setTextFill(Color.rgb(0, 0, 0));
        }
      }
      if ((this.selectedMap != 2)) {
        this.preview_map_2_border.setStroke(Color.rgb(0, 0, 0, 0.6));
        this.map_label_2.setTextFill(normalTextColor);
      } else {
        if ((this.selectedMap == 2)) {
          this.preview_map_2_border.setStroke(Color.rgb(0, 0, 0));
          this.map_label_2.setTextFill(Color.rgb(0, 0, 0));
        }
      }
    } else {
      this.nightMode = Boolean.valueOf(true);
      this.night_mode_indicator.setFill(Color.rgb(0, 204, 99));
      this.night_mode_indicator.setStroke(Color.rgb(184, 193, 207, 0.3));
      this.perception_indicator.setStroke(Color.rgb(184, 193, 207, 0.3));
      Color nightTextColor = Color.rgb(191, 191, 191);
      Color _rgb_1 = Color.rgb(34, 34, 34);
      BackgroundFill _backgroundFill_1 = new BackgroundFill(_rgb_1, CornerRadii.EMPTY, Insets.EMPTY);
      Background _background_1 = new Background(_backgroundFill_1);
      this.main_pane.setBackground(_background_1);
      this.boids_quantity_label.setTextFill(nightTextColor);
      this.boids_quantity_display.setTextFill(nightTextColor);
      this.boids_quantity_min.setTextFill(nightTextColor);
      this.boids_quantity_max.setTextFill(nightTextColor);
      this.boids_population_label.setTextFill(nightTextColor);
      this.decrement_boids_population.setTextFill(nightTextColor);
      this.increment_boids_population.setTextFill(nightTextColor);
      this.boids_population_decrement_circle.setStroke(nightTextColor);
      this.boids_population_increment_circle.setStroke(nightTextColor);
      this.boids_population_line.setStroke(nightTextColor);
      this.boids_population_input.setStyle("-fx-text-fill: rgb(191, 191, 191); -fx-background-color: transparent");
      this.boids_vision_label.setTextFill(nightTextColor);
      this.boids_vision_display.setTextFill(nightTextColor);
      this.boids_vision_min.setTextFill(nightTextColor);
      this.boids_vision_max.setTextFill(nightTextColor);
      this.boids_distance_deplacement_label.setTextFill(nightTextColor);
      this.boids_distance_deplacement_display.setTextFill(nightTextColor);
      this.boids_distance_deplacement_min.setTextFill(nightTextColor);
      this.boids_distance_deplacement_max.setTextFill(nightTextColor);
      this.boid_group.setTextFill(Color.rgb(191, 191, 191, 0.7));
      this.boid_vitesse.setTextFill(Color.rgb(191, 191, 191, 0.7));
      this.boid_group_vitesse.setTextFill(Color.rgb(191, 191, 191, 0.7));
      this.boid_masse.setTextFill(Color.rgb(191, 191, 191, 0.7));
      this.boid_angle.setTextFill(Color.rgb(191, 191, 191, 0.7));
      this.boid_distance.setTextFill(Color.rgb(191, 191, 191, 0.7));
      this.boid_new_vitesse.setTextFill(Color.rgb(191, 191, 191, 0.7));
      this.boid_position.setTextFill(Color.rgb(191, 191, 191, 0.7));
      this.toggle_night_mode.setTextFill(Color.rgb(191, 191, 191, 0.3));
      this.toggle_perception.setTextFill(Color.rgb(191, 191, 191, 0.3));
      this.hide_infos.setTextFill(Color.rgb(191, 191, 191, 0.3));
      this.start_button.setTextFill(nightTextColor);
      if ((this.selectedMap != 1)) {
        this.preview_map_1_border.setStroke(nightTextColor);
        this.map_label_1.setTextFill(nightTextColor);
      } else {
        if ((this.selectedMap == 1)) {
          this.preview_map_1_border.setStroke(Color.rgb(235, 221, 26));
          this.map_label_1.setTextFill(Color.rgb(235, 221, 26));
        }
      }
      if ((this.selectedMap != 2)) {
        this.preview_map_2_border.setStroke(nightTextColor);
        this.map_label_2.setTextFill(nightTextColor);
      } else {
        if ((this.selectedMap == 2)) {
          this.preview_map_2_border.setStroke(Color.rgb(235, 221, 26));
          this.map_label_2.setTextFill(Color.rgb(235, 221, 26));
        }
      }
    }
  }
  
  @FXML
  protected void togglePerception() {
    if ((this.togglePerception).booleanValue()) {
      this.togglePerception = Boolean.valueOf(false);
      this.perception_indicator.setFill(Color.TRANSPARENT);
      final Consumer<Arc> _function = (Arc item) -> {
        item.setFill(Color.TRANSPARENT);
      };
      this.perceptions.forEach(_function);
    } else {
      this.togglePerception = Boolean.valueOf(true);
      this.perception_indicator.setFill(Color.rgb(0, 204, 99));
      final Consumer<Arc> _function_1 = (Arc item) -> {
        if ((this.nightMode).booleanValue()) {
          item.setFill(Color.rgb(255, 245, 112, 0.2));
        } else {
          item.setFill(Color.rgb(255, 245, 112, 0.8));
        }
      };
      this.perceptions.forEach(_function_1);
    }
  }
  
  @FXML
  protected void toggleButtonGlow() {
    if ((this.nightMode).booleanValue()) {
      this.toggle_night_mode.setTextFill(Color.rgb(235, 221, 26));
    } else {
      this.toggle_night_mode.setTextFill(Color.rgb(0, 0, 0));
    }
    Glow glowEffect = new Glow();
    glowEffect.setLevel(0.8);
    this.toggle_night_mode.setEffect(glowEffect);
  }
  
  @FXML
  protected void toggleButtonReset() {
    if (this.launched) {
      if ((this.nightMode).booleanValue()) {
        this.toggle_night_mode.setTextFill(Color.rgb(191, 191, 191, 0.3));
      } else {
        this.toggle_night_mode.setTextFill(Color.rgb(0, 0, 0, 0.3));
      }
    } else {
      if ((this.nightMode).booleanValue()) {
        this.toggle_night_mode.setTextFill(Color.rgb(191, 191, 191));
      } else {
        this.toggle_night_mode.setTextFill(Color.rgb(0, 0, 0));
      }
    }
    this.toggle_night_mode.setEffect(null);
  }
  
  @FXML
  protected void startButtonGlow() {
    if ((this.nightMode).booleanValue()) {
      this.start_button.setTextFill(Color.rgb(235, 221, 26));
    } else {
      this.start_button.setTextFill(Color.rgb(0, 0, 0));
    }
    Glow glowEffect = new Glow();
    glowEffect.setLevel(0.8);
    this.start_button.setEffect(glowEffect);
  }
  
  @FXML
  protected void startButtonReset() {
    if ((this.nightMode).booleanValue()) {
      this.start_button.setTextFill(Color.rgb(191, 191, 191));
    } else {
      this.start_button.setTextFill(Color.rgb(0, 0, 0));
    }
    this.start_button.setEffect(null);
  }
  
  @FXML
  protected void perceptionButtonGlow() {
    if ((this.nightMode).booleanValue()) {
      this.toggle_perception.setTextFill(Color.rgb(235, 221, 26));
    } else {
      this.toggle_perception.setTextFill(Color.rgb(0, 0, 0));
    }
    Glow glowEffect = new Glow();
    glowEffect.setLevel(0.8);
    this.toggle_perception.setEffect(glowEffect);
  }
  
  @FXML
  protected void perceptionButtonReset() {
    if ((this.nightMode).booleanValue()) {
      this.toggle_perception.setTextFill(Color.rgb(191, 191, 191, 0.3));
    } else {
      this.toggle_perception.setTextFill(Color.rgb(0, 0, 0, 0.3));
    }
    this.toggle_perception.setEffect(null);
  }
  
  @FXML
  protected void infosButtonGlow() {
    if ((this.nightMode).booleanValue()) {
      this.hide_infos.setTextFill(Color.rgb(235, 221, 26));
    } else {
      this.hide_infos.setTextFill(Color.rgb(0, 0, 0));
    }
    Glow glowEffect = new Glow();
    glowEffect.setLevel(0.8);
    this.hide_infos.setEffect(glowEffect);
  }
  
  @FXML
  protected void infosButtonReset() {
    if ((this.nightMode).booleanValue()) {
      this.hide_infos.setTextFill(Color.rgb(191, 191, 191, 0.3));
    } else {
      this.hide_infos.setTextFill(Color.rgb(0, 0, 0, 0.3));
    }
    this.hide_infos.setEffect(null);
  }
  
  @FXML
  protected void incrementBoidsPopulationGlow() {
    if ((this.nightMode).booleanValue()) {
      this.boids_population_increment_circle.setStroke(Color.rgb(235, 221, 26));
      this.increment_boids_population.setTextFill(Color.rgb(235, 221, 26));
    } else {
      this.boids_population_increment_circle.setStroke(Color.rgb(0, 0, 0));
      this.increment_boids_population.setTextFill(Color.rgb(0, 0, 0));
    }
    Glow glowEffect = new Glow();
    glowEffect.setLevel(0.8);
    this.boids_population_increment_circle.setEffect(glowEffect);
  }
  
  @FXML
  protected void incrementBoidsPopulationReset() {
    if ((this.nightMode).booleanValue()) {
      this.boids_population_increment_circle.setStroke(Color.rgb(191, 191, 191));
      this.increment_boids_population.setTextFill(Color.rgb(191, 191, 191));
    } else {
      this.boids_population_increment_circle.setStroke(Color.rgb(0, 0, 0, 0.3));
      this.increment_boids_population.setTextFill(Color.rgb(0, 0, 0));
    }
    this.boids_population_increment_circle.setEffect(null);
    this.increment_boids_population.setEffect(null);
  }
  
  @FXML
  protected void decrementBoidsPopulationGlow() {
    if ((this.nightMode).booleanValue()) {
      this.boids_population_decrement_circle.setStroke(Color.rgb(235, 221, 26));
      this.decrement_boids_population.setTextFill(Color.rgb(235, 221, 26));
    } else {
      this.boids_population_decrement_circle.setStroke(Color.rgb(0, 0, 0));
      this.decrement_boids_population.setTextFill(Color.rgb(0, 0, 0));
    }
    Glow glowEffect = new Glow();
    glowEffect.setLevel(0.8);
    this.boids_population_decrement_circle.setEffect(glowEffect);
  }
  
  @FXML
  protected void decrementBoidsPopulationReset() {
    if ((this.nightMode).booleanValue()) {
      this.boids_population_decrement_circle.setStroke(Color.rgb(191, 191, 191));
      this.decrement_boids_population.setTextFill(Color.rgb(191, 191, 191));
    } else {
      this.boids_population_decrement_circle.setStroke(Color.rgb(0, 0, 0, 0.3));
      this.increment_boids_population.setTextFill(Color.rgb(0, 0, 0));
    }
    this.boids_population_decrement_circle.setEffect(null);
    this.decrement_boids_population.setEffect(null);
  }
  
  @FXML
  protected void boidsPopulationGlow() {
    if ((this.nightMode).booleanValue()) {
      this.boids_population_input.setStyle("-fx-text-fill: rgb(235, 221, 26); -fx-background-color: transparent");
    } else {
      this.boids_population_input.setStyle("-fx-text-fill: rgb(0, 0, 0); -fx-background-color: transparent");
    }
    this.boids_population_line.setStrokeWidth(3);
    Glow glowEffect = new Glow();
    glowEffect.setLevel(0.8);
    this.boids_population_input.setEffect(glowEffect);
  }
  
  @FXML
  protected void boidsPopulationReset() {
    if ((this.nightMode).booleanValue()) {
      this.boids_population_input.setStyle("-fx-text-fill: rgb(191, 191, 191); -fx-background-color: transparent");
    } else {
      this.boids_population_input.setStyle("-fx-text-fill: rgb(0, 0, 0); -fx-background-color: transparent");
    }
    this.boids_population_line.setStrokeWidth(1);
    this.boids_population_input.setEffect(null);
  }
  
  public void toggleUIState() {
    boolean _isDisable = this.start_button.isDisable();
    boolean _equals = (_isDisable == true);
    if (_equals) {
      this.start_button.setDisable(false);
    } else {
      this.start_button.setDisable(true);
    }
    boolean _isDisable_1 = this.boids_quantity_input.isDisable();
    boolean _equals_1 = (_isDisable_1 == true);
    if (_equals_1) {
      this.boids_quantity_input.setDisable(false);
    } else {
      this.boids_quantity_input.setDisable(true);
    }
    boolean _isDisable_2 = this.boids_population_input.isDisable();
    boolean _equals_2 = (_isDisable_2 == true);
    if (_equals_2) {
      this.boids_population_input.setDisable(false);
    } else {
      this.boids_population_input.setDisable(true);
    }
    boolean _isDisable_3 = this.boids_vision_input.isDisable();
    boolean _equals_3 = (_isDisable_3 == true);
    if (_equals_3) {
      this.boids_vision_input.setDisable(false);
    } else {
      this.boids_vision_input.setDisable(true);
    }
    boolean _isDisable_4 = this.boids_distance_deplacement_input.isDisable();
    boolean _equals_4 = (_isDisable_4 == true);
    if (_equals_4) {
      this.boids_distance_deplacement_input.setDisable(false);
    } else {
      this.boids_distance_deplacement_input.setDisable(true);
    }
  }
  
  public void toggleMenuUIVisibility() {
    boolean _isVisible = this.UI_pane.isVisible();
    if (_isVisible) {
      this.UI_pane.setVisible(false);
    } else {
      this.UI_pane.setVisible(true);
    }
  }
  
  public void toggleSimuUIVisibility() {
    boolean _isVisible = this.toggle_perception.isVisible();
    boolean _not = (!_isVisible);
    if (_not) {
      this.toggle_perception.setVisible(true);
    } else {
      this.toggle_perception.setVisible(false);
    }
    boolean _isVisible_1 = this.perception_indicator.isVisible();
    boolean _not_1 = (!_isVisible_1);
    if (_not_1) {
      this.perception_indicator.setVisible(true);
    } else {
      this.perception_indicator.setVisible(false);
    }
  }
  
  @FXML
  public void pause() {
    this.pause_button.setVisible(false);
    this.resume_button.setVisible(true);
    Pause _pause = new Pause();
    this.emitToAgents(_pause);
  }
  
  @FXML
  public void resume() {
    this.pause_button.setVisible(true);
    this.resume_button.setVisible(false);
    Resume _resume = new Resume();
    this.emitToAgents(_resume);
  }
  
  public void hideInfosVisibility() {
    this.resetTexts();
    this.boids_infos_pane.setVisible(false);
  }
  
  public void resetTexts() {
    this.boid_group.setText("");
    this.boid_vitesse.setText("");
    this.boid_group_vitesse.setText("");
    this.boid_masse.setText("");
    this.boid_angle.setText("");
    this.boid_distance.setText("");
    this.boid_new_vitesse.setText("");
  }
  
  public void showInfosVisibility() {
    this.boids_infos_pane.setVisible(true);
  }
  
  public Boolean outputQuality(final String output) {
    Boolean outputQuality = Boolean.valueOf(false);
    try {
      Integer.parseInt(this.boids_population_input.getText());
      outputQuality = Boolean.valueOf(true);
    } catch (final Throwable _t) {
      if (_t instanceof NumberFormatException) {
        final NumberFormatException e = (NumberFormatException)_t;
        System.out.println("Invalid number of populations!");
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    return outputQuality;
  }
  
  public void updateInfos(final BoidBody boidBody) {
    int _groupe = boidBody.getGroupe();
    String _plus = ("Groupe: " + Integer.valueOf(_groupe));
    this.boid_group.setText(_plus);
    String _format = String.format("%.3f", Double.valueOf(boidBody.getVitesse().getX()));
    String _plus_1 = ("Vitesse: (" + _format);
    String _plus_2 = (_plus_1 + ", ");
    String _format_1 = String.format("%.3f", Double.valueOf(boidBody.getVitesse().getY()));
    String _plus_3 = (_plus_2 + _format_1);
    String _plus_4 = (_plus_3 + ")");
    this.boid_vitesse.setText(_plus_4);
    int _groupeVitesseMax = boidBody.getGroupeVitesseMax();
    String _plus_5 = ("Vitesse max. groupe: " + Integer.valueOf(_groupeVitesseMax));
    this.boid_group_vitesse.setText(_plus_5);
    int _masse = boidBody.getMasse();
    String _plus_6 = ("Masse: " + Integer.valueOf(_masse));
    this.boid_masse.setText(_plus_6);
    int _angleVisibilite = boidBody.getAngleVisibilite();
    String _plus_7 = ("Angle: " + Integer.valueOf(_angleVisibilite));
    this.boid_angle.setText(_plus_7);
    int _distanceVisibilite = boidBody.getDistanceVisibilite();
    String _plus_8 = ("Distance percep.: " + Integer.valueOf(_distanceVisibilite));
    this.boid_distance.setText(_plus_8);
    double _x = boidBody.getNewVitesse().getX();
    String _plus_9 = ("Nouvelle vitesse: (" + Double.valueOf(_x));
    String _plus_10 = (_plus_9 + ", ");
    double _y = boidBody.getNewVitesse().getY();
    String _plus_11 = (_plus_10 + Double.valueOf(_y));
    String _plus_12 = (_plus_11 + ")");
    this.boid_new_vitesse.setText(_plus_12);
    String _format_2 = String.format("%.3f", Double.valueOf(boidBody.getPosition().getX()));
    String _plus_13 = ("Position: (" + _format_2);
    String _plus_14 = (_plus_13 + ", ");
    String _format_3 = String.format("%.3f", Double.valueOf(boidBody.getPosition().getY()));
    String _plus_15 = (_plus_14 + _format_3);
    String _plus_16 = (_plus_15 + ")");
    this.boid_position.setText(_plus_16);
  }
  
  @FXML
  protected void changeFocus() {
    this.main_pane.requestFocus();
  }
  
  /**
   * BOIDS QUANTITY GLOWING EFFECT
   */
  @FXML
  protected void boidsQuantityMinGlow() {
    if ((this.nightMode).booleanValue()) {
      this.boids_quantity_min.setTextFill(Color.rgb(235, 221, 26));
    } else {
      this.boids_quantity_min.setTextFill(Color.rgb(0, 0, 0));
    }
    Glow glowEffect = new Glow();
    glowEffect.setLevel(0.8);
    this.boids_quantity_min.setEffect(glowEffect);
  }
  
  @FXML
  protected void boidsQuantityMaxGlow() {
    if ((this.nightMode).booleanValue()) {
      this.boids_quantity_max.setTextFill(Color.rgb(235, 221, 26));
    } else {
      this.boids_quantity_max.setTextFill(Color.rgb(0, 0, 0));
    }
    Glow glowEffect = new Glow();
    glowEffect.setLevel(0.8);
    this.boids_quantity_max.setEffect(glowEffect);
  }
  
  @FXML
  protected void boidsQuantityMinReset() {
    if ((this.nightMode).booleanValue()) {
      this.boids_quantity_min.setTextFill(Color.rgb(191, 191, 191));
    } else {
      this.boids_quantity_min.setTextFill(Color.rgb(0, 0, 0));
    }
    this.boids_quantity_min.setEffect(null);
  }
  
  @FXML
  protected void boidsQuantityMaxReset() {
    if ((this.nightMode).booleanValue()) {
      this.boids_quantity_max.setTextFill(Color.rgb(191, 191, 191));
    } else {
      this.boids_quantity_max.setTextFill(Color.rgb(0, 0, 0));
    }
    this.boids_quantity_max.setEffect(null);
  }
  
  /**
   * BOIDS PERCEPTION ANGLE GLOWING EFFECT
   */
  @FXML
  protected void boidsVisionMinGlow() {
    if ((this.nightMode).booleanValue()) {
      this.boids_vision_min.setTextFill(Color.rgb(235, 221, 26));
    } else {
      this.boids_vision_min.setTextFill(Color.rgb(0, 0, 0));
    }
    Glow glowEffect = new Glow();
    glowEffect.setLevel(0.8);
    this.boids_vision_min.setEffect(glowEffect);
  }
  
  @FXML
  protected void boidsVisionMaxGlow() {
    if ((this.nightMode).booleanValue()) {
      this.boids_vision_max.setTextFill(Color.rgb(235, 221, 26));
    } else {
      this.boids_vision_max.setTextFill(Color.rgb(0, 0, 0));
    }
    Glow glowEffect = new Glow();
    glowEffect.setLevel(0.8);
    this.boids_vision_max.setEffect(glowEffect);
  }
  
  @FXML
  protected void boidsVisionMinReset() {
    if ((this.nightMode).booleanValue()) {
      this.boids_vision_min.setTextFill(Color.rgb(191, 191, 191));
    } else {
      this.boids_vision_min.setTextFill(Color.rgb(0, 0, 0));
    }
    this.boids_vision_min.setEffect(null);
  }
  
  @FXML
  protected void boidsVisionMaxReset() {
    if ((this.nightMode).booleanValue()) {
      this.boids_vision_max.setTextFill(Color.rgb(191, 191, 191));
    } else {
      this.boids_vision_max.setTextFill(Color.rgb(0, 0, 0));
    }
    this.boids_vision_max.setEffect(null);
  }
  
  /**
   * BOIDS DISTANCE PERCEPTION GLOWING EFFECT
   */
  @FXML
  protected void boidsDistanceVisionMinGlow() {
    if ((this.nightMode).booleanValue()) {
      this.boids_distance_deplacement_min.setTextFill(Color.rgb(235, 221, 26));
    } else {
      this.boids_distance_deplacement_min.setTextFill(Color.rgb(0, 0, 0));
    }
    Glow glowEffect = new Glow();
    glowEffect.setLevel(0.8);
    this.boids_distance_deplacement_min.setEffect(glowEffect);
  }
  
  @FXML
  protected void boidsDistanceVisionMaxGlow() {
    if ((this.nightMode).booleanValue()) {
      this.boids_distance_deplacement_max.setTextFill(Color.rgb(235, 221, 26));
    } else {
      this.boids_distance_deplacement_max.setTextFill(Color.rgb(0, 0, 0));
    }
    Glow glowEffect = new Glow();
    glowEffect.setLevel(0.8);
    this.boids_distance_deplacement_max.setEffect(glowEffect);
  }
  
  @FXML
  protected void boidsDistanceVisionMinReset() {
    if ((this.nightMode).booleanValue()) {
      this.boids_distance_deplacement_min.setTextFill(Color.rgb(191, 191, 191));
    } else {
      this.boids_distance_deplacement_min.setTextFill(Color.rgb(0, 0, 0));
    }
    this.boids_distance_deplacement_min.setEffect(null);
  }
  
  @FXML
  protected void boidsDistanceVisionMaxReset() {
    if ((this.nightMode).booleanValue()) {
      this.boids_distance_deplacement_max.setTextFill(Color.rgb(191, 191, 191));
    } else {
      this.boids_distance_deplacement_max.setTextFill(Color.rgb(0, 0, 0));
    }
    this.boids_distance_deplacement_max.setEffect(null);
  }
  
  /**
   * MAX/MIN values fast setters
   */
  @FXML
  protected void boidsQuantitySetToMin() {
    this.boids_quantity_input.setValue(Integer.parseInt(this.boids_quantity_min.getText()));
    this.boids_quantity_display.setText(this.boids_quantity_min.getText());
  }
  
  @FXML
  protected void boidsQuantitySetToMax() {
    this.boids_quantity_input.setValue(Integer.parseInt(this.boids_quantity_max.getText()));
    this.boids_quantity_display.setText(this.boids_quantity_max.getText());
  }
  
  @FXML
  protected void boidsVisionSetToMin() {
    this.boids_vision_input.setValue(Integer.parseInt(this.boids_vision_min.getText()));
    this.boids_vision_display.setText(this.boids_vision_min.getText());
  }
  
  @FXML
  protected void boidsVisionSetToMax() {
    this.boids_vision_input.setValue(Integer.parseInt(this.boids_vision_max.getText()));
    this.boids_vision_display.setText(this.boids_vision_max.getText());
  }
  
  @FXML
  protected void boidsDistanceVisionSetToMin() {
    this.boids_distance_deplacement_input.setValue(Integer.parseInt(this.boids_distance_deplacement_min.getText()));
    this.boids_distance_deplacement_display.setText(this.boids_distance_deplacement_min.getText());
  }
  
  @FXML
  protected void boidsDistanceVisionSetToMax() {
    this.boids_distance_deplacement_input.setValue(Integer.parseInt(this.boids_distance_deplacement_max.getText()));
    this.boids_distance_deplacement_display.setText(this.boids_distance_deplacement_max.getText());
  }
  
  @FXML
  protected void previewMap1Glow() {
    if ((this.selectedMap != 1)) {
      if ((this.nightMode).booleanValue()) {
        this.preview_map_1_border.setStroke(Color.rgb(235, 221, 26, 0.6));
        this.map_label_1.setTextFill(Color.rgb(235, 221, 26, 0.6));
      } else {
        this.preview_map_1_border.setStroke(Color.rgb(0, 0, 0, 0.35));
        this.map_label_1.setTextFill(Color.rgb(0, 0, 0, 0.6));
      }
      Glow glowEffect = new Glow();
      glowEffect.setLevel(1);
      this.preview_map_1_border.setEffect(glowEffect);
      this.map_label_1.setEffect(glowEffect);
    }
  }
  
  @FXML
  protected void previewMap1Reset() {
    if ((this.selectedMap != 1)) {
      if ((this.nightMode).booleanValue()) {
        this.preview_map_1_border.setStroke(Color.rgb(191, 191, 191));
        this.map_label_1.setTextFill(Color.rgb(191, 191, 191));
      } else {
        this.preview_map_1_border.setStroke(Color.rgb(0, 0, 0, 0.6));
        this.map_label_1.setTextFill(Color.rgb(0, 0, 0, 0.6));
      }
      this.preview_map_1_border.setEffect(null);
      this.map_label_1.setEffect(null);
    }
  }
  
  @FXML
  protected void previewMap2Glow() {
    if ((this.selectedMap != 2)) {
      if ((this.nightMode).booleanValue()) {
        this.preview_map_2_border.setStroke(Color.rgb(235, 221, 26, 0.6));
        this.map_label_2.setTextFill(Color.rgb(235, 221, 26, 0.6));
      } else {
        this.preview_map_2_border.setStroke(Color.rgb(0, 0, 0, 0.6));
        this.map_label_2.setTextFill(Color.rgb(0, 0, 0, 0.6));
      }
      Glow glowEffect = new Glow();
      glowEffect.setLevel(1);
      this.preview_map_2_border.setEffect(glowEffect);
      this.map_label_2.setEffect(glowEffect);
    }
  }
  
  @FXML
  protected void previewMap2Reset() {
    if ((this.selectedMap != 2)) {
      if ((this.nightMode).booleanValue()) {
        this.preview_map_2_border.setStroke(Color.rgb(191, 191, 191));
        this.map_label_2.setTextFill(Color.rgb(191, 191, 191));
      } else {
        this.preview_map_2_border.setStroke(Color.rgb(0, 0, 0, 0.6));
        this.map_label_2.setTextFill(Color.rgb(0, 0, 0, 0.6));
      }
      this.preview_map_2_border.setEffect(null);
      this.map_label_2.setEffect(null);
    }
  }
  
  @FXML
  protected void selectMap(final MouseEvent e) {
    String _substring = e.getSource().toString().substring(0, 1);
    boolean _equals = Objects.equal(_substring, "I");
    if (_equals) {
      Object _source = e.getSource();
      Object _source_1 = e.getSource();
      int _length = ((ImageView) _source_1).getId().length();
      int _minus = (_length - 1);
      this.selectedMap = Integer.parseInt(((ImageView) _source).getId().substring(_minus));
    } else {
      Object _source_2 = e.getSource();
      Object _source_3 = e.getSource();
      int _length_1 = ((Label) _source_3).getId().length();
      int _minus_1 = (_length_1 - 1);
      this.selectedMap = Integer.parseInt(((Label) _source_2).getId().substring(_minus_1));
    }
    this.resetMaps();
    final int _switchValue = this.selectedMap;
    switch (_switchValue) {
      case 1:
        if ((this.nightMode).booleanValue()) {
          this.preview_map_1_border.setStroke(Color.rgb(235, 221, 26));
          this.map_label_1.setTextFill(Color.rgb(235, 221, 26));
        } else {
          this.preview_map_1_border.setStroke(Color.rgb(0, 0, 0));
          this.map_label_1.setTextFill(Color.rgb(0, 0, 0));
        }
        Glow glowEffect = new Glow();
        glowEffect.setLevel(0.8);
        this.preview_map_1_border.setEffect(glowEffect);
        this.map_label_1.setEffect(glowEffect);
        this.tick_map_1.setVisible(true);
        break;
      case 2:
        if ((this.nightMode).booleanValue()) {
          this.preview_map_2_border.setStroke(Color.rgb(235, 221, 26));
          this.map_label_2.setTextFill(Color.rgb(235, 221, 26));
        } else {
          this.preview_map_2_border.setStroke(Color.rgb(0, 0, 0));
          this.map_label_2.setTextFill(Color.rgb(0, 0, 0));
        }
        Glow glowEffect_1 = new Glow();
        glowEffect_1.setLevel(0.8);
        this.preview_map_2_border.setEffect(glowEffect_1);
        this.map_label_2.setEffect(glowEffect_1);
        this.tick_map_2.setVisible(true);
        break;
    }
  }
  
  protected void resetMaps() {
    this.previewMap1Reset();
    this.previewMap2Reset();
    this.tick_map_1.setVisible(false);
    this.tick_map_2.setVisible(false);
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
    BoidsFxViewerController other = (BoidsFxViewerController) obj;
    if (other.launched != this.launched)
      return false;
    if (other.mapCreated != this.mapCreated)
      return false;
    if (other.selectedMap != this.selectedMap)
      return false;
    if (other.nightMode != this.nightMode)
      return false;
    if (other.togglePerception != this.togglePerception)
      return false;
    if (!java.util.Objects.equals(this.currentBoid, other.currentBoid)) {
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
    result = prime * result + (this.launched ? 1231 : 1237);
    result = prime * result + (this.mapCreated ? 1231 : 1237);
    result = prime * result + this.selectedMap;
    result = prime * result + (this.nightMode ? 1231 : 1237);
    result = prime * result + (this.togglePerception ? 1231 : 1237);
    result = prime * result + java.util.Objects.hashCode(this.currentBoid);
    return result;
  }
  
  @SyntheticMember
  public BoidsFxViewerController() {
    super();
  }
}
