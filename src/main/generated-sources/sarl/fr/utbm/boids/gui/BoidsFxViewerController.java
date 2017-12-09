package fr.utbm.boids.gui;

import com.google.common.util.concurrent.AtomicDouble;
import fr.utbm.boids.BoidBody;
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
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.Pane;
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
  private Button start_button;
  
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
  
  private List<Obstacle> obstacles;
  
  private List<Polygon> polygons;
  
  private List<List<Coordinates>> polygonsCoordinates;
  
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
      this.toggleUIVisibility();
    } else {
      this.emitToAgents(event);
    }
  }
  
  public void buildMap(final int map) {
    abstract class __BoidsFxViewerController_0 implements Runnable {
      public abstract void run();
    }
    
    __BoidsFxViewerController_0 command = new __BoidsFxViewerController_0() {
      @Override
      public void run() {
        if ((map == 2)) {
          ArrayList<Polygon> _arrayList = new ArrayList<Polygon>();
          BoidsFxViewerController.this.polygons = _arrayList;
          ArrayList<List<Coordinates>> _arrayList_1 = new ArrayList<List<Coordinates>>();
          BoidsFxViewerController.this.polygonsCoordinates = _arrayList_1;
          ArrayList<Obstacle> _arrayList_2 = new ArrayList<Obstacle>();
          BoidsFxViewerController.this.obstacles = _arrayList_2;
          Polygon _polygon = new Polygon(250.0, 200.0, 365.0, 250.0, 400.0, 300.0, 325.0, 400.0, 205.0, 
            225.0);
          BoidsFxViewerController.this.polygons.add(_polygon);
          Polygon _polygon_1 = new Polygon(605.0, 80.0, 675.0, 65.0, 680.0, 125.0, 650.0, 220.0, 630.0, 250.0, 660.0, 130.0, 
            665.0, 75.0, 615.0, 95.0, 560.0, 240.0, 560.0, 205.0, 605.0, 80.0);
          BoidsFxViewerController.this.polygons.add(_polygon_1);
          Polygon _polygon_2 = new Polygon(450.0, 450.0, 575.0, 500.0, 575.0, 420.0, 700.0, 500.0, 590.0, 450.0, 590.0, 520.0);
          BoidsFxViewerController.this.polygons.add(_polygon_2);
          final Consumer<Polygon> _function = (Polygon p) -> {
            BoidsFxViewerController.this.obstacle_group.getChildren().add(0, p);
            BoidsFxViewerController.this.polygonsCoordinates.add(BoidsFxViewerController.this.generateCoordinates(p));
          };
          BoidsFxViewerController.this.polygons.forEach(_function);
          System.out.println("Affichage de polygonsCoordinates");
          final Procedure2<List<Coordinates>, Integer> _function_1 = (List<Coordinates> pc, Integer index) -> {
            System.out.println(("Polygon #" + Integer.valueOf(index)));
            final Consumer<Coordinates> _function_2 = (Coordinates c) -> {
              System.out.println(c.toString());
            };
            pc.forEach(_function_2);
          };
          IterableExtensions.<List<Coordinates>>forEach(BoidsFxViewerController.this.polygonsCoordinates, _function_1);
          BoidsFxViewerController.this.generateObstacles();
          final Consumer<Obstacle> _function_2 = (Obstacle o) -> {
            System.out.println(o.toString());
          };
          BoidsFxViewerController.this.obstacles.forEach(_function_2);
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
  
  public void generateObstacles() {
    final Consumer<List<Coordinates>> _function = (List<Coordinates> p) -> {
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
      Obstacle _obstacle = new Obstacle(lines);
      this.obstacles.add(_obstacle);
    };
    this.polygonsCoordinates.forEach(_function);
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
            Circle cercle = new Circle();
            double _x = boid.getPosition().getX();
            int _mapWidth = BoidsFxViewerController.this.getMapWidth();
            int _divide = (_mapWidth / 2);
            double _plus = (_x + _divide);
            cercle.setCenterX(_plus);
            double _y = boid.getPosition().getY();
            int _mapHeight = BoidsFxViewerController.this.getMapHeight();
            int _divide_1 = (_mapHeight / 2);
            double _plus_1 = (_y + _divide_1);
            cercle.setCenterY(_plus_1);
            cercle.setRadius(10.0f);
            BoidsFxViewerController.this.boids_group.getChildren().add(0, cercle);
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
  
  public void toggleUIVisibility() {
    boolean _isVisible = this.UI_pane.isVisible();
    if (_isVisible) {
      this.UI_pane.setVisible(false);
    } else {
      this.UI_pane.setVisible(true);
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
    return result;
  }
  
  @SyntheticMember
  public BoidsFxViewerController() {
    super();
  }
}
