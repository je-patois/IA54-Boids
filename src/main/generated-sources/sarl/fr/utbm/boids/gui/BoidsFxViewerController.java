package fr.utbm.boids.gui;

import fr.utbm.boids.events.ConfigureSimulation;
import fr.utbm.boids.gui.fx.FxViewerController;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure0;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(9)
@SuppressWarnings("all")
public class BoidsFxViewerController extends FxViewerController {
  private boolean launched = false;
  
  private boolean mapCreated = false;
  
  @FXML
  private Group myGroup;
  
  @FXML
  private Label boids_quantity_display;
  
  @FXML
  private ScrollBar boids_quantity_input;
  
  @FXML
  private Button start_button;
  
  @FXML
  private Label map_selection_display;
  
  @FXML
  private ScrollBar map_selection_input;
  
  @FXML
  private Pane myPane;
  
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
  
  @FXML
  protected void startSimu() {
    int _mapSelection = this.getMapSelection();
    int _boidsQuantity = this.getBoidsQuantity();
    ConfigureSimulation event = new ConfigureSimulation(_mapSelection, _boidsQuantity);
    if ((!this.launched)) {
      final Procedure0 _function = () -> {
        this.emitToAgents(event);
      };
      this.startAgentApplication(_function);
      this.launched = true;
      this.mapCreated = false;
      this.start_button.setDisable(true);
      this.boids_quantity_input.setDisable(true);
      this.map_selection_input.setDisable(true);
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
          Polygon centralObstacle = new Polygon();
          centralObstacle.getPoints().addAll(Double.valueOf(250.0), Double.valueOf(200.0), Double.valueOf(365.0), Double.valueOf(250.0), Double.valueOf(400.0), Double.valueOf(300.0), Double.valueOf(325.0), Double.valueOf(400.0), Double.valueOf(205.0), 
            Double.valueOf(225.0));
          BoidsFxViewerController.this.myPane.getChildren().add(0, centralObstacle);
          Polygon uObstacle = new Polygon();
          uObstacle.getPoints().addAll(Double.valueOf(605.0), Double.valueOf(80.0), Double.valueOf(675.0), Double.valueOf(65.0), Double.valueOf(680.0), Double.valueOf(125.0), Double.valueOf(650.0), Double.valueOf(220.0), Double.valueOf(630.0), Double.valueOf(250.0), 
            Double.valueOf(660.0), Double.valueOf(130.0), Double.valueOf(665.0), Double.valueOf(75.0), Double.valueOf(615.0), Double.valueOf(95.0), Double.valueOf(560.0), Double.valueOf(240.0), Double.valueOf(560.0), Double.valueOf(205.0), Double.valueOf(605.0), Double.valueOf(80.0));
          BoidsFxViewerController.this.myPane.getChildren().add(0, uObstacle);
          Polygon stairObstacle = new Polygon();
          stairObstacle.getPoints().addAll(Double.valueOf(450.0), Double.valueOf(450.0), Double.valueOf(575.0), Double.valueOf(500.0), Double.valueOf(575.0), Double.valueOf(420.0), Double.valueOf(700.0), Double.valueOf(500.0), Double.valueOf(590.0), Double.valueOf(450.0), Double.valueOf(590.0), Double.valueOf(520.0));
          BoidsFxViewerController.this.myPane.getChildren().add(0, stairObstacle);
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
