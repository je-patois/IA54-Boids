package fr.utbm.boids.gui;

import com.google.common.util.concurrent.AtomicDouble;
import fr.utbm.boids.BoidBody;
import fr.utbm.boids.environment.Obstacle;
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
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
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
  private Label boids_distance_deplacement_label;
  
  @FXML
  private ScrollBar boids_quantity_input;
  
  @FXML
  private ScrollBar map_selection_input;
  
  @FXML
  private TextField boids_population_input;
  
  @FXML
  private ScrollBar boids_vision_input;
  
  @FXML
  private ScrollBar boids_distance_deplacement_input;
  
  @FXML
  private Label boids_quantity_display;
  
  @FXML
  private Label map_selection_display;
  
  @FXML
  private Label boids_vision_display;
  
  @FXML
  private Label boids_distance_deplacement_display;
  
  @FXML
  private Label boids_quantity_min;
  
  @FXML
  private Label boids_quantity_max;
  
  @FXML
  private Label map_min;
  
  @FXML
  private Label map_max;
  
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
  
  private List<Polygon> polygons;
  
  private List<List<Coordinates>> polygonsCoordinates;
  
  private List<Obstacle> obstacles = new ArrayList<Obstacle>();
  
  private Boolean nightMode = Boolean.valueOf(true);
  
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
    throw new Error("Unresolved compilation problems:"
      + "\nThe method outputQuality(String) is undefined"
      + "\nThe method toggleUIState() is undefined"
      + "\nThe method toggleMenuUIVisibility() is undefined"
      + "\nThe method toggleSimuUIVisibility() is undefined");
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
    throw new Error("Unresolved compilation problems:"
      + "\nno viable alternative at input \'<\'"
      + "\nno viable alternative at input \'<\'"
      + "\nmissing \'}\' at \'===\'"
      + "\nno viable alternative at input \'===\'"
      + "\nno viable alternative at input \'=\'"
      + "\nmissing \'}\' at \'>\'"
      + "\nno viable alternative at input \'>\'"
      + "\nno viable alternative at input \'>\'"
      + "\n\'else\' is a reserved keyword which is not allowed as identifier. Please choose another word."
      + "\nThe method or field boidElement is undefined"
      + "\n=== cannot be resolved."
      + "\nThe method or field < is undefined"
      + "\nThe method or field < is undefined"
      + "\nThe method or field HEAD is undefined"
      + "\nThe method or field perceptionArc is undefined"
      + "\nThe method or field master is undefined"
      + "\nThe method or field perceptionArc is undefined"
      + "\nThe method or field perceptionArc is undefined"
      + "\nThe method or field perceptionArc is undefined"
      + "\nThe method or field angleRotation is undefined"
      + "\nThe method or field perceptionArc is undefined"
      + "\nThe method or field perceptionArc is undefined"
      + "\nThe method or field perceptionArc is undefined"
      + "\nThe method or field perceptionArc is undefined"
      + "\nThe method or field boidElement is undefined"
      + "\nThe method or field perceptionArc is undefined"
      + "\nThe method showInfosVisibility() is undefined"
      + "\nType mismatch: type void is not applicable at this location"
      + "\n<<< cannot be resolved"
      + "\n=== cannot be resolved"
      + "\nsetCenterY cannot be resolved"
      + "\n>>> cannot be resolved"
      + "\n>>> cannot be resolved"
      + "\n> cannot be resolved"
      + "\nsetRadiusX cannot be resolved"
      + "\nsetRadiusY cannot be resolved"
      + "\nsetStartAngle cannot be resolved"
      + "\nsetLength cannot be resolved"
      + "\nsetType cannot be resolved"
      + "\nfill cannot be resolved"
      + "\nfill cannot be resolved");
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
