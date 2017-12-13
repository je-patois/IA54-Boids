package fr.utbm.boids.gui;

import com.google.common.util.concurrent.AtomicDouble;
import fr.utbm.boids.BoidBody;
import fr.utbm.boids.Configuration;
import fr.utbm.boids.environment.Obstacle;
import fr.utbm.boids.events.ConfigureSimulation;
import fr.utbm.boids.gui.fx.FxViewerController;
import fr.utbm.boids.util.Coordinates;
import fr.utbm.boids.util.LineTool;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.Glow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
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
  private Label map_selection_label;
  
  @FXML
  private Label boids_population_label;
  
  @FXML
  private Label boids_vision_label;
  
  @FXML
  private ScrollBar boids_quantity_input;
  
  @FXML
  private ScrollBar map_selection_input;
  
  @FXML
  private ScrollBar boids_population_input;
  
  @FXML
  private ScrollBar boids_vision_input;
  
  @FXML
  private Label boids_quantity_display;
  
  @FXML
  private Label map_selection_display;
  
  @FXML
  private Label boids_population_display;
  
  @FXML
  private Label boids_vision_display;
  
  @FXML
  private Label boids_quantity_min;
  
  @FXML
  private Label boids_quantity_max;
  
  @FXML
  private Label map_min;
  
  @FXML
  private Label map_max;
  
  @FXML
  private Label boids_population_min;
  
  @FXML
  private Label boids_population_max;
  
  @FXML
  private Label boids_vision_min;
  
  @FXML
  private Label boids_vision_max;
  
  private List<Polygon> polygons;
  
  private List<List<Coordinates>> polygonsCoordinates;
  
  private List<Obstacle> obstacles = new ArrayList<Obstacle>();
  
  private Boolean nightMode = Boolean.valueOf(false);
  
  private Boolean togglePerception = Boolean.valueOf(true);
  
  @Pure
  public int getBoidsQuantity() {
    double _value = this.boids_quantity_input.getValue();
    return ((int) _value);
  }
  
  @Pure
  public int getMapSelection() {
    double _value = this.map_selection_input.getValue();
    return ((int) _value);
  }
  
  @Pure
  public int getBoidsPopulation() {
    double _value = this.boids_population_input.getValue();
    return ((int) _value);
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
  
  @FXML
  protected void startSimu() {
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
  
  public List<Obstacle> buildMap(final int map) {
    abstract class __BoidsFxViewerController_0 implements Runnable {
      public abstract void run();
    }
    
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
        BoidsFxViewerController.this.boids_group.getChildren().clear();
        for (final BoidBody boid : list) {
          {
            double _x = boid.getPosition().getX();
            double _y = boid.getPosition().getY();
            double _minus = (_y - 7.5);
            double _x_1 = boid.getPosition().getX();
            double _plus = (_x_1 + 5);
            double _y_1 = boid.getPosition().getY();
            double _plus_1 = (_y_1 + 7.5);
            double _x_2 = boid.getPosition().getX();
            double _minus_1 = (_x_2 - 5);
            double _y_2 = boid.getPosition().getY();
            double _plus_2 = (_y_2 + 7.5);
            Polygon boidElement = new Polygon(_x, _minus, _plus, _plus_1, _minus_1, _plus_2);
            double _x_3 = boid.getVitesse().getX();
            double _y_3 = boid.getVitesse().getY();
            double _divide = (_x_3 / _y_3);
            boidElement.setRotate(Math.toDegrees(Math.atan(_divide)));
            boidElement.setFill(Configuration.COLOR_FAMILY.get(Integer.valueOf(boid.getGroupe())));
            if ((BoidsFxViewerController.this.togglePerception).booleanValue()) {
              double _x_4 = boid.getPosition().getX();
              double _y_4 = boid.getPosition().getY();
              double _parseDouble = Double.parseDouble(BoidsFxViewerController.this.boids_vision_display.getText());
              Circle perceptionRadius = new Circle(_x_4, _y_4, _parseDouble);
              if ((BoidsFxViewerController.this.nightMode).booleanValue()) {
                perceptionRadius.setFill(Color.rgb(255, 245, 112, 0.2));
              } else {
                perceptionRadius.setFill(Color.rgb(255, 245, 112, 0.8));
              }
              BoidsFxViewerController.this.boids_group.getChildren().add(0, boidElement);
              BoidsFxViewerController.this.boids_group.getChildren().add(0, perceptionRadius);
            } else {
              BoidsFxViewerController.this.boids_group.getChildren().add(0, boidElement);
            }
          }
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
  protected void actionMapSelectionDisplay() {
    final InvalidationListener _function = (Observable it) -> {
      this.map_selection_display.setText(String.format("%.0f", Double.valueOf(this.map_selection_input.getValue())));
    };
    this.map_selection_input.valueProperty().addListener(_function);
  }
  
  @FXML
  protected void actionPopulationDisplay() {
    final InvalidationListener _function = (Observable it) -> {
      this.boids_population_display.setText(String.format("%.0f", Double.valueOf(this.boids_population_input.getValue())));
    };
    this.boids_population_input.valueProperty().addListener(_function);
  }
  
  @FXML
  protected void actionBoidsVisionDisplay() {
    final InvalidationListener _function = (Observable it) -> {
      this.boids_vision_display.setText(String.format("%.0f", Double.valueOf(this.boids_vision_input.getValue())));
    };
    this.boids_vision_input.valueProperty().addListener(_function);
  }
  
  @FXML
  protected void toggleMode() {
    if ((this.nightMode).booleanValue()) {
      this.nightMode = Boolean.valueOf(false);
      this.night_mode_indicator.setFill(Color.TRANSPARENT);
      this.night_mode_indicator.setStroke(Color.rgb(0, 0, 0, 0.3));
      this.perception_indicator.setStroke(Color.rgb(0, 0, 0, 0.3));
      Color normalTextColor = Color.BLACK;
      BackgroundFill _backgroundFill = new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY);
      Background _background = new Background(_backgroundFill);
      this.main_pane.setBackground(_background);
      this.boids_quantity_label.setTextFill(normalTextColor);
      this.boids_quantity_display.setTextFill(normalTextColor);
      this.boids_quantity_min.setTextFill(normalTextColor);
      this.boids_quantity_max.setTextFill(normalTextColor);
      this.map_selection_label.setTextFill(normalTextColor);
      this.map_selection_display.setTextFill(normalTextColor);
      this.map_min.setTextFill(normalTextColor);
      this.map_max.setTextFill(normalTextColor);
      this.boids_population_label.setTextFill(normalTextColor);
      this.boids_population_display.setTextFill(normalTextColor);
      this.boids_population_min.setTextFill(normalTextColor);
      this.boids_population_max.setTextFill(normalTextColor);
      this.boids_vision_label.setTextFill(normalTextColor);
      this.boids_vision_display.setTextFill(normalTextColor);
      this.boids_vision_min.setTextFill(normalTextColor);
      this.boids_vision_max.setTextFill(normalTextColor);
      this.toggle_night_mode.setTextFill(Color.rgb(0, 0, 0, 0.3));
      this.toggle_perception.setTextFill(Color.rgb(0, 0, 0, 0.3));
      this.start_button.setTextFill(normalTextColor);
    } else {
      this.nightMode = Boolean.valueOf(true);
      this.night_mode_indicator.setFill(Color.rgb(0, 204, 99));
      this.night_mode_indicator.setStroke(Color.rgb(184, 193, 207, 0.3));
      this.perception_indicator.setStroke(Color.rgb(184, 193, 207, 0.3));
      Color nightTextColor = Color.rgb(191, 191, 191);
      Color _rgb = Color.rgb(34, 34, 34);
      BackgroundFill _backgroundFill_1 = new BackgroundFill(_rgb, CornerRadii.EMPTY, Insets.EMPTY);
      Background _background_1 = new Background(_backgroundFill_1);
      this.main_pane.setBackground(_background_1);
      this.boids_quantity_label.setTextFill(nightTextColor);
      this.boids_quantity_display.setTextFill(nightTextColor);
      this.boids_quantity_min.setTextFill(nightTextColor);
      this.boids_quantity_max.setTextFill(nightTextColor);
      this.map_selection_label.setTextFill(nightTextColor);
      this.map_selection_display.setTextFill(nightTextColor);
      this.map_min.setTextFill(nightTextColor);
      this.map_max.setTextFill(nightTextColor);
      this.boids_population_label.setTextFill(nightTextColor);
      this.boids_population_display.setTextFill(nightTextColor);
      this.boids_population_min.setTextFill(nightTextColor);
      this.boids_population_max.setTextFill(nightTextColor);
      this.boids_vision_label.setTextFill(nightTextColor);
      this.boids_vision_display.setTextFill(nightTextColor);
      this.boids_vision_min.setTextFill(nightTextColor);
      this.boids_vision_max.setTextFill(nightTextColor);
      this.toggle_night_mode.setTextFill(Color.rgb(191, 191, 191, 0.3));
      this.toggle_perception.setTextFill(Color.rgb(191, 191, 191, 0.3));
      this.start_button.setTextFill(nightTextColor);
    }
  }
  
  @FXML
  protected void togglePerception() {
    if ((this.togglePerception).booleanValue()) {
      this.togglePerception = Boolean.valueOf(false);
      this.perception_indicator.setFill(Color.TRANSPARENT);
    } else {
      this.togglePerception = Boolean.valueOf(true);
      this.perception_indicator.setFill(Color.rgb(0, 204, 99));
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
    boolean _isDisable_2 = this.map_selection_input.isDisable();
    boolean _equals_2 = (_isDisable_2 == true);
    if (_equals_2) {
      this.map_selection_input.setDisable(false);
    } else {
      this.map_selection_input.setDisable(true);
    }
    boolean _isDisable_3 = this.boids_population_input.isDisable();
    boolean _equals_3 = (_isDisable_3 == true);
    if (_equals_3) {
      this.boids_population_input.setDisable(false);
    } else {
      this.boids_population_input.setDisable(true);
    }
    boolean _isDisable_4 = this.boids_vision_input.isDisable();
    boolean _equals_4 = (_isDisable_4 == true);
    if (_equals_4) {
      this.boids_vision_input.setDisable(false);
    } else {
      this.boids_vision_input.setDisable(true);
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
    if (other.nightMode != this.nightMode)
      return false;
    if (other.togglePerception != this.togglePerception)
      return false;
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
    result = prime * result + (this.nightMode ? 1231 : 1237);
    result = prime * result + (this.togglePerception ? 1231 : 1237);
    return result;
  }
  
  @SyntheticMember
  public BoidsFxViewerController() {
    super();
  }
}
