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
import fr.utbm.boids.util.BoidGroupInfos;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
  private ScrollBar boids_quantity_input;
  
  @FXML
  private TextField boids_population_input;
  
  @FXML
  private Label boids_quantity_display;
  
  @FXML
  private Label boids_quantity_min;
  
  @FXML
  private Label boids_quantity_max;
  
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
  
  @FXML
  private Pane pane_group_1;
  
  @FXML
  private Pane pane_group_2;
  
  @FXML
  private Pane pane_group_3;
  
  @FXML
  private Pane pane_group_4;
  
  @FXML
  private Pane pane_group_5;
  
  @FXML
  private Pane pane_group_6;
  
  @FXML
  private Pane pane_group_7;
  
  @FXML
  private Pane pane_group_8;
  
  @FXML
  private Label group_label_1;
  
  @FXML
  private Label group_label_2;
  
  @FXML
  private Label group_label_3;
  
  @FXML
  private Label group_label_4;
  
  @FXML
  private Label group_label_5;
  
  @FXML
  private Label group_label_6;
  
  @FXML
  private Label group_label_7;
  
  @FXML
  private Label group_label_8;
  
  @FXML
  private Label mass_label_1;
  
  @FXML
  private Label mass_label_2;
  
  @FXML
  private Label mass_label_3;
  
  @FXML
  private Label mass_label_4;
  
  @FXML
  private Label mass_label_5;
  
  @FXML
  private Label mass_label_6;
  
  @FXML
  private Label mass_label_7;
  
  @FXML
  private Label mass_label_8;
  
  @FXML
  private ScrollBar mass_1;
  
  @FXML
  private ScrollBar mass_2;
  
  @FXML
  private ScrollBar mass_3;
  
  @FXML
  private ScrollBar mass_4;
  
  @FXML
  private ScrollBar mass_5;
  
  @FXML
  private ScrollBar mass_6;
  
  @FXML
  private ScrollBar mass_7;
  
  @FXML
  private ScrollBar mass_8;
  
  @FXML
  private Label mass_min_1;
  
  @FXML
  private Label mass_max_1;
  
  @FXML
  private Label mass_display_1;
  
  @FXML
  private Label mass_min_2;
  
  @FXML
  private Label mass_max_2;
  
  @FXML
  private Label mass_display_2;
  
  @FXML
  private Label mass_min_3;
  
  @FXML
  private Label mass_max_3;
  
  @FXML
  private Label mass_display_3;
  
  @FXML
  private Label mass_min_4;
  
  @FXML
  private Label mass_max_4;
  
  @FXML
  private Label mass_display_4;
  
  @FXML
  private Label mass_min_5;
  
  @FXML
  private Label mass_max_5;
  
  @FXML
  private Label mass_display_5;
  
  @FXML
  private Label mass_min_6;
  
  @FXML
  private Label mass_max_6;
  
  @FXML
  private Label mass_display_6;
  
  @FXML
  private Label mass_min_7;
  
  @FXML
  private Label mass_max_7;
  
  @FXML
  private Label mass_display_7;
  
  @FXML
  private Label mass_min_8;
  
  @FXML
  private Label mass_max_8;
  
  @FXML
  private Label mass_display_8;
  
  @FXML
  private Label speed_label_1;
  
  @FXML
  private ScrollBar speed_1;
  
  @FXML
  private Label speed_min_1;
  
  @FXML
  private Label speed_max_1;
  
  @FXML
  private Label speed_display_1;
  
  @FXML
  private Label speed_label_2;
  
  @FXML
  private ScrollBar speed_2;
  
  @FXML
  private Label speed_min_2;
  
  @FXML
  private Label speed_max_2;
  
  @FXML
  private Label speed_display_2;
  
  @FXML
  private Label speed_label_3;
  
  @FXML
  private ScrollBar speed_3;
  
  @FXML
  private Label speed_min_3;
  
  @FXML
  private Label speed_max_3;
  
  @FXML
  private Label speed_display_3;
  
  @FXML
  private Label speed_label_4;
  
  @FXML
  private ScrollBar speed_4;
  
  @FXML
  private Label speed_min_4;
  
  @FXML
  private Label speed_max_4;
  
  @FXML
  private Label speed_display_4;
  
  @FXML
  private Label speed_label_5;
  
  @FXML
  private ScrollBar speed_5;
  
  @FXML
  private Label speed_min_5;
  
  @FXML
  private Label speed_max_5;
  
  @FXML
  private Label speed_display_5;
  
  @FXML
  private Label speed_label_6;
  
  @FXML
  private ScrollBar speed_6;
  
  @FXML
  private Label speed_min_6;
  
  @FXML
  private Label speed_max_6;
  
  @FXML
  private Label speed_display_6;
  
  @FXML
  private Label speed_label_7;
  
  @FXML
  private ScrollBar speed_7;
  
  @FXML
  private Label speed_min_7;
  
  @FXML
  private Label speed_max_7;
  
  @FXML
  private Label speed_display_7;
  
  @FXML
  private Label speed_label_8;
  
  @FXML
  private ScrollBar speed_8;
  
  @FXML
  private Label speed_min_8;
  
  @FXML
  private Label speed_max_8;
  
  @FXML
  private Label speed_display_8;
  
  @FXML
  private Label angle_label_1;
  
  @FXML
  private ScrollBar angle_1;
  
  @FXML
  private Label angle_min_1;
  
  @FXML
  private Label angle_max_1;
  
  @FXML
  private Label angle_display_1;
  
  @FXML
  private Label angle_label_2;
  
  @FXML
  private ScrollBar angle_2;
  
  @FXML
  private Label angle_min_2;
  
  @FXML
  private Label angle_max_2;
  
  @FXML
  private Label angle_display_2;
  
  @FXML
  private Label angle_label_3;
  
  @FXML
  private ScrollBar angle_3;
  
  @FXML
  private Label angle_min_3;
  
  @FXML
  private Label angle_max_3;
  
  @FXML
  private Label angle_display_3;
  
  @FXML
  private Label angle_label_4;
  
  @FXML
  private ScrollBar angle_4;
  
  @FXML
  private Label angle_min_4;
  
  @FXML
  private Label angle_max_4;
  
  @FXML
  private Label angle_display_4;
  
  @FXML
  private Label angle_label_5;
  
  @FXML
  private ScrollBar angle_5;
  
  @FXML
  private Label angle_min_5;
  
  @FXML
  private Label angle_max_5;
  
  @FXML
  private Label angle_display_5;
  
  @FXML
  private Label angle_label_6;
  
  @FXML
  private ScrollBar angle_6;
  
  @FXML
  private Label angle_min_6;
  
  @FXML
  private Label angle_max_6;
  
  @FXML
  private Label angle_display_6;
  
  @FXML
  private Label angle_label_7;
  
  @FXML
  private ScrollBar angle_7;
  
  @FXML
  private Label angle_min_7;
  
  @FXML
  private Label angle_max_7;
  
  @FXML
  private Label angle_display_7;
  
  @FXML
  private Label angle_label_8;
  
  @FXML
  private ScrollBar angle_8;
  
  @FXML
  private Label angle_min_8;
  
  @FXML
  private Label angle_max_8;
  
  @FXML
  private Label angle_display_8;
  
  @FXML
  private Label distance_label_1;
  
  @FXML
  private ScrollBar distance_1;
  
  @FXML
  private Label distance_min_1;
  
  @FXML
  private Label distance_max_1;
  
  @FXML
  private Label distance_display_1;
  
  @FXML
  private Label distance_label_2;
  
  @FXML
  private ScrollBar distance_2;
  
  @FXML
  private Label distance_min_2;
  
  @FXML
  private Label distance_max_2;
  
  @FXML
  private Label distance_display_2;
  
  @FXML
  private Label distance_label_3;
  
  @FXML
  private ScrollBar distance_3;
  
  @FXML
  private Label distance_min_3;
  
  @FXML
  private Label distance_max_3;
  
  @FXML
  private Label distance_display_3;
  
  @FXML
  private Label distance_label_4;
  
  @FXML
  private ScrollBar distance_4;
  
  @FXML
  private Label distance_min_4;
  
  @FXML
  private Label distance_max_4;
  
  @FXML
  private Label distance_display_4;
  
  @FXML
  private Label distance_label_5;
  
  @FXML
  private ScrollBar distance_5;
  
  @FXML
  private Label distance_min_5;
  
  @FXML
  private Label distance_max_5;
  
  @FXML
  private Label distance_display_5;
  
  @FXML
  private Label distance_label_6;
  
  @FXML
  private ScrollBar distance_6;
  
  @FXML
  private Label distance_min_6;
  
  @FXML
  private Label distance_max_6;
  
  @FXML
  private Label distance_display_6;
  
  @FXML
  private Label distance_label_7;
  
  @FXML
  private ScrollBar distance_7;
  
  @FXML
  private Label distance_min_7;
  
  @FXML
  private Label distance_max_7;
  
  @FXML
  private Label distance_display_7;
  
  @FXML
  private Label distance_label_8;
  
  @FXML
  private ScrollBar distance_8;
  
  @FXML
  private Label distance_min_8;
  
  @FXML
  private Label distance_max_8;
  
  @FXML
  private Label distance_display_8;
  
  @FXML
  private ScrollPane advanced_settings_pane;
  
  @FXML
  private AnchorPane advanced_settings_anchor_pane;
  
  @FXML
  private Label map_selection_label;
  
  @FXML
  private Label settings_label;
  
  @FXML
  private Label advanced_settings_label;
  
  @FXML
  private Label see_advanced_settings_label;
  
  @FXML
  private Label reset_group_values;
  
  private int selectedMap = 1;
  
  private List<BoidGroupInfos> boidsSettings = new ArrayList<BoidGroupInfos>();
  
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
  public List<BoidGroupInfos> getBoidsSettings() {
    return this.boidsSettings;
  }
  
  @FXML
  protected void startSimu() {
    if (((!Objects.equal(this.boids_population_input.getText(), "")) && (this.outputQuality(this.boids_population_input.getText())).booleanValue())) {
      for (int i = 1; (i < (this.getBoidsPopulation() + 1)); i++) {
        if ((i == 1)) {
          int _parseInt = Integer.parseInt(this.mass_display_1.getText());
          int _parseInt_1 = Integer.parseInt(this.speed_display_1.getText());
          int _parseInt_2 = Integer.parseInt(this.angle_display_1.getText());
          int _parseInt_3 = Integer.parseInt(this.distance_display_1.getText());
          BoidGroupInfos _boidGroupInfos = new BoidGroupInfos(_parseInt, _parseInt_1, _parseInt_2, _parseInt_3);
          this.boidsSettings.add(_boidGroupInfos);
        } else {
          if ((i == 2)) {
            int _parseInt_4 = Integer.parseInt(this.mass_display_2.getText());
            int _parseInt_5 = Integer.parseInt(this.speed_display_2.getText());
            int _parseInt_6 = Integer.parseInt(this.angle_display_2.getText());
            int _parseInt_7 = Integer.parseInt(this.distance_display_2.getText());
            BoidGroupInfos _boidGroupInfos_1 = new BoidGroupInfos(_parseInt_4, _parseInt_5, _parseInt_6, _parseInt_7);
            this.boidsSettings.add(_boidGroupInfos_1);
          } else {
            if ((i == 3)) {
              int _parseInt_8 = Integer.parseInt(this.mass_display_3.getText());
              int _parseInt_9 = Integer.parseInt(this.speed_display_3.getText());
              int _parseInt_10 = Integer.parseInt(this.angle_display_3.getText());
              int _parseInt_11 = Integer.parseInt(this.distance_display_3.getText());
              BoidGroupInfos _boidGroupInfos_2 = new BoidGroupInfos(_parseInt_8, _parseInt_9, _parseInt_10, _parseInt_11);
              this.boidsSettings.add(_boidGroupInfos_2);
            } else {
              if ((i == 4)) {
                int _parseInt_12 = Integer.parseInt(this.mass_display_4.getText());
                int _parseInt_13 = Integer.parseInt(this.speed_display_4.getText());
                int _parseInt_14 = Integer.parseInt(this.angle_display_4.getText());
                int _parseInt_15 = Integer.parseInt(this.distance_display_4.getText());
                BoidGroupInfos _boidGroupInfos_3 = new BoidGroupInfos(_parseInt_12, _parseInt_13, _parseInt_14, _parseInt_15);
                this.boidsSettings.add(_boidGroupInfos_3);
              } else {
                if ((i == 5)) {
                  int _parseInt_16 = Integer.parseInt(this.mass_display_5.getText());
                  int _parseInt_17 = Integer.parseInt(this.speed_display_5.getText());
                  int _parseInt_18 = Integer.parseInt(this.angle_display_5.getText());
                  int _parseInt_19 = Integer.parseInt(this.distance_display_5.getText());
                  BoidGroupInfos _boidGroupInfos_4 = new BoidGroupInfos(_parseInt_16, _parseInt_17, _parseInt_18, _parseInt_19);
                  this.boidsSettings.add(_boidGroupInfos_4);
                } else {
                  if ((i == 6)) {
                    int _parseInt_20 = Integer.parseInt(this.mass_display_6.getText());
                    int _parseInt_21 = Integer.parseInt(this.speed_display_6.getText());
                    int _parseInt_22 = Integer.parseInt(this.angle_display_6.getText());
                    int _parseInt_23 = Integer.parseInt(this.distance_display_6.getText());
                    BoidGroupInfos _boidGroupInfos_5 = new BoidGroupInfos(_parseInt_20, _parseInt_21, _parseInt_22, _parseInt_23);
                    this.boidsSettings.add(_boidGroupInfos_5);
                  } else {
                    if ((i == 7)) {
                      int _parseInt_24 = Integer.parseInt(this.mass_display_7.getText());
                      int _parseInt_25 = Integer.parseInt(this.speed_display_7.getText());
                      int _parseInt_26 = Integer.parseInt(this.angle_display_7.getText());
                      int _parseInt_27 = Integer.parseInt(this.distance_display_7.getText());
                      BoidGroupInfos _boidGroupInfos_6 = new BoidGroupInfos(_parseInt_24, _parseInt_25, _parseInt_26, _parseInt_27);
                      this.boidsSettings.add(_boidGroupInfos_6);
                    } else {
                      if ((i == 8)) {
                        int _parseInt_28 = Integer.parseInt(this.mass_display_8.getText());
                        int _parseInt_29 = Integer.parseInt(this.speed_display_8.getText());
                        int _parseInt_30 = Integer.parseInt(this.angle_display_8.getText());
                        int _parseInt_31 = Integer.parseInt(this.distance_display_8.getText());
                        BoidGroupInfos _boidGroupInfos_7 = new BoidGroupInfos(_parseInt_28, _parseInt_29, _parseInt_30, _parseInt_31);
                        this.boidsSettings.add(_boidGroupInfos_7);
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
      int _mapSelection = this.getMapSelection();
      int _boidsQuantity = this.getBoidsQuantity();
      int _boidsPopulation = this.getBoidsPopulation();
      ConfigureSimulation event = new ConfigureSimulation(_mapSelection, _boidsQuantity, _boidsPopulation);
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
              int _distanceVisibilite = boid.getDistanceVisibilite();
              perceptionArc.setRadiusX(((double) _distanceVisibilite));
              int _distanceVisibilite_1 = boid.getDistanceVisibilite();
              perceptionArc.setRadiusY(((double) _distanceVisibilite_1));
              int _angleVisibilite = boid.getAngleVisibilite();
              double _minus_6 = ((90 - angleRotation) - _angleVisibilite);
              perceptionArc.setStartAngle(_minus_6);
              int _angleVisibilite_1 = boid.getAngleVisibilite();
              int _multiply = (_angleVisibilite_1 * 2);
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
        if ((currentValue >= 8)) {
          this.boids_population_input.setText(("" + Integer.valueOf(8)));
        } else {
          if ((currentValue <= 1)) {
            this.boids_population_input.setText(("" + Integer.valueOf(1)));
          }
        }
        this.showGroupConfiguration();
      }
    }
  }
  
  @FXML
  protected void incrementBoidsPopulation() {
    if (((!Objects.equal(this.boids_population_input.getText(), "")) && (this.outputQuality(this.boids_population_input.getText())).booleanValue())) {
      int currentValue = Integer.parseInt(this.boids_population_input.getText());
      if ((currentValue >= 8)) {
        this.boids_population_input.setText(("" + Integer.valueOf(8)));
      } else {
        this.boids_population_input.setText(("" + Integer.valueOf((currentValue + 1))));
      }
      this.showGroupConfiguration();
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
      this.showGroupConfiguration();
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
      this.see_advanced_settings_label.setTextFill(normalTextColor);
      this.group_label_1.setTextFill(normalTextColor);
      this.mass_label_1.setTextFill(normalTextColor);
      this.mass_min_1.setTextFill(normalTextColor);
      this.mass_max_1.setTextFill(normalTextColor);
      this.mass_display_1.setTextFill(normalTextColor);
      this.speed_label_1.setTextFill(normalTextColor);
      this.speed_min_1.setTextFill(normalTextColor);
      this.speed_max_1.setTextFill(normalTextColor);
      this.speed_display_1.setTextFill(normalTextColor);
      this.angle_label_1.setTextFill(normalTextColor);
      this.angle_min_1.setTextFill(normalTextColor);
      this.angle_max_1.setTextFill(normalTextColor);
      this.angle_display_1.setTextFill(normalTextColor);
      this.distance_label_1.setTextFill(normalTextColor);
      this.distance_min_1.setTextFill(normalTextColor);
      this.distance_max_1.setTextFill(normalTextColor);
      this.distance_display_1.setTextFill(normalTextColor);
      this.group_label_2.setTextFill(normalTextColor);
      this.mass_label_2.setTextFill(normalTextColor);
      this.mass_min_2.setTextFill(normalTextColor);
      this.mass_max_2.setTextFill(normalTextColor);
      this.mass_display_2.setTextFill(normalTextColor);
      this.speed_label_2.setTextFill(normalTextColor);
      this.speed_min_2.setTextFill(normalTextColor);
      this.speed_max_2.setTextFill(normalTextColor);
      this.speed_display_2.setTextFill(normalTextColor);
      this.angle_label_2.setTextFill(normalTextColor);
      this.angle_min_2.setTextFill(normalTextColor);
      this.angle_max_2.setTextFill(normalTextColor);
      this.angle_display_2.setTextFill(normalTextColor);
      this.distance_label_2.setTextFill(normalTextColor);
      this.distance_min_2.setTextFill(normalTextColor);
      this.distance_max_2.setTextFill(normalTextColor);
      this.distance_display_2.setTextFill(normalTextColor);
      this.group_label_3.setTextFill(normalTextColor);
      this.mass_label_3.setTextFill(normalTextColor);
      this.mass_min_3.setTextFill(normalTextColor);
      this.mass_max_3.setTextFill(normalTextColor);
      this.mass_display_3.setTextFill(normalTextColor);
      this.speed_label_3.setTextFill(normalTextColor);
      this.speed_min_3.setTextFill(normalTextColor);
      this.speed_max_3.setTextFill(normalTextColor);
      this.speed_display_3.setTextFill(normalTextColor);
      this.angle_label_3.setTextFill(normalTextColor);
      this.angle_min_3.setTextFill(normalTextColor);
      this.angle_max_3.setTextFill(normalTextColor);
      this.angle_display_3.setTextFill(normalTextColor);
      this.distance_label_3.setTextFill(normalTextColor);
      this.distance_min_3.setTextFill(normalTextColor);
      this.distance_max_3.setTextFill(normalTextColor);
      this.distance_display_3.setTextFill(normalTextColor);
      this.group_label_4.setTextFill(normalTextColor);
      this.mass_label_4.setTextFill(normalTextColor);
      this.mass_min_4.setTextFill(normalTextColor);
      this.mass_max_4.setTextFill(normalTextColor);
      this.mass_display_4.setTextFill(normalTextColor);
      this.speed_label_4.setTextFill(normalTextColor);
      this.speed_min_4.setTextFill(normalTextColor);
      this.speed_max_4.setTextFill(normalTextColor);
      this.speed_display_4.setTextFill(normalTextColor);
      this.angle_label_4.setTextFill(normalTextColor);
      this.angle_min_4.setTextFill(normalTextColor);
      this.angle_max_4.setTextFill(normalTextColor);
      this.angle_display_4.setTextFill(normalTextColor);
      this.distance_label_4.setTextFill(normalTextColor);
      this.distance_min_4.setTextFill(normalTextColor);
      this.distance_max_4.setTextFill(normalTextColor);
      this.distance_display_4.setTextFill(normalTextColor);
      this.group_label_5.setTextFill(normalTextColor);
      this.mass_label_5.setTextFill(normalTextColor);
      this.mass_min_5.setTextFill(normalTextColor);
      this.mass_max_5.setTextFill(normalTextColor);
      this.mass_display_5.setTextFill(normalTextColor);
      this.speed_label_5.setTextFill(normalTextColor);
      this.speed_min_5.setTextFill(normalTextColor);
      this.speed_max_5.setTextFill(normalTextColor);
      this.speed_display_5.setTextFill(normalTextColor);
      this.angle_label_5.setTextFill(normalTextColor);
      this.angle_min_5.setTextFill(normalTextColor);
      this.angle_max_5.setTextFill(normalTextColor);
      this.angle_display_5.setTextFill(normalTextColor);
      this.distance_label_5.setTextFill(normalTextColor);
      this.distance_min_5.setTextFill(normalTextColor);
      this.distance_max_5.setTextFill(normalTextColor);
      this.distance_display_5.setTextFill(normalTextColor);
      this.group_label_6.setTextFill(normalTextColor);
      this.mass_label_6.setTextFill(normalTextColor);
      this.mass_min_6.setTextFill(normalTextColor);
      this.mass_max_6.setTextFill(normalTextColor);
      this.mass_display_6.setTextFill(normalTextColor);
      this.speed_label_6.setTextFill(normalTextColor);
      this.speed_min_6.setTextFill(normalTextColor);
      this.speed_max_6.setTextFill(normalTextColor);
      this.speed_display_6.setTextFill(normalTextColor);
      this.angle_label_6.setTextFill(normalTextColor);
      this.angle_min_6.setTextFill(normalTextColor);
      this.angle_max_6.setTextFill(normalTextColor);
      this.angle_display_6.setTextFill(normalTextColor);
      this.distance_label_6.setTextFill(normalTextColor);
      this.distance_min_6.setTextFill(normalTextColor);
      this.distance_max_6.setTextFill(normalTextColor);
      this.distance_display_6.setTextFill(normalTextColor);
      this.group_label_7.setTextFill(normalTextColor);
      this.mass_label_7.setTextFill(normalTextColor);
      this.mass_min_7.setTextFill(normalTextColor);
      this.mass_max_7.setTextFill(normalTextColor);
      this.mass_display_7.setTextFill(normalTextColor);
      this.speed_label_7.setTextFill(normalTextColor);
      this.speed_min_7.setTextFill(normalTextColor);
      this.speed_max_7.setTextFill(normalTextColor);
      this.speed_display_7.setTextFill(normalTextColor);
      this.angle_label_7.setTextFill(normalTextColor);
      this.angle_min_7.setTextFill(normalTextColor);
      this.angle_max_7.setTextFill(normalTextColor);
      this.angle_display_7.setTextFill(normalTextColor);
      this.distance_label_7.setTextFill(normalTextColor);
      this.distance_min_7.setTextFill(normalTextColor);
      this.distance_max_7.setTextFill(normalTextColor);
      this.distance_display_7.setTextFill(normalTextColor);
      this.group_label_8.setTextFill(normalTextColor);
      this.mass_label_8.setTextFill(normalTextColor);
      this.mass_min_8.setTextFill(normalTextColor);
      this.mass_max_8.setTextFill(normalTextColor);
      this.mass_display_8.setTextFill(normalTextColor);
      this.speed_label_8.setTextFill(normalTextColor);
      this.speed_min_8.setTextFill(normalTextColor);
      this.speed_max_8.setTextFill(normalTextColor);
      this.speed_display_8.setTextFill(normalTextColor);
      this.angle_label_8.setTextFill(normalTextColor);
      this.angle_min_8.setTextFill(normalTextColor);
      this.angle_max_8.setTextFill(normalTextColor);
      this.angle_display_8.setTextFill(normalTextColor);
      this.distance_label_8.setTextFill(normalTextColor);
      this.distance_min_8.setTextFill(normalTextColor);
      this.distance_max_8.setTextFill(normalTextColor);
      this.distance_display_8.setTextFill(normalTextColor);
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
      this.see_advanced_settings_label.setTextFill(nightTextColor);
      this.group_label_1.setTextFill(nightTextColor);
      this.mass_label_1.setTextFill(nightTextColor);
      this.mass_min_1.setTextFill(nightTextColor);
      this.mass_max_1.setTextFill(nightTextColor);
      this.mass_display_1.setTextFill(nightTextColor);
      this.speed_label_1.setTextFill(nightTextColor);
      this.speed_min_1.setTextFill(nightTextColor);
      this.speed_max_1.setTextFill(nightTextColor);
      this.speed_display_1.setTextFill(nightTextColor);
      this.angle_label_1.setTextFill(nightTextColor);
      this.angle_min_1.setTextFill(nightTextColor);
      this.angle_max_1.setTextFill(nightTextColor);
      this.angle_display_1.setTextFill(nightTextColor);
      this.distance_label_1.setTextFill(nightTextColor);
      this.distance_min_1.setTextFill(nightTextColor);
      this.distance_max_1.setTextFill(nightTextColor);
      this.distance_display_1.setTextFill(nightTextColor);
      this.group_label_2.setTextFill(nightTextColor);
      this.mass_label_2.setTextFill(nightTextColor);
      this.mass_min_2.setTextFill(nightTextColor);
      this.mass_max_2.setTextFill(nightTextColor);
      this.mass_display_2.setTextFill(nightTextColor);
      this.speed_label_2.setTextFill(nightTextColor);
      this.speed_min_2.setTextFill(nightTextColor);
      this.speed_max_2.setTextFill(nightTextColor);
      this.speed_display_2.setTextFill(nightTextColor);
      this.angle_label_2.setTextFill(nightTextColor);
      this.angle_min_2.setTextFill(nightTextColor);
      this.angle_max_2.setTextFill(nightTextColor);
      this.angle_display_2.setTextFill(nightTextColor);
      this.distance_label_2.setTextFill(nightTextColor);
      this.distance_min_2.setTextFill(nightTextColor);
      this.distance_max_2.setTextFill(nightTextColor);
      this.distance_display_2.setTextFill(nightTextColor);
      this.group_label_3.setTextFill(nightTextColor);
      this.mass_label_3.setTextFill(nightTextColor);
      this.mass_min_3.setTextFill(nightTextColor);
      this.mass_max_3.setTextFill(nightTextColor);
      this.mass_display_3.setTextFill(nightTextColor);
      this.speed_label_3.setTextFill(nightTextColor);
      this.speed_min_3.setTextFill(nightTextColor);
      this.speed_max_3.setTextFill(nightTextColor);
      this.speed_display_3.setTextFill(nightTextColor);
      this.angle_label_3.setTextFill(nightTextColor);
      this.angle_min_3.setTextFill(nightTextColor);
      this.angle_max_3.setTextFill(nightTextColor);
      this.angle_display_3.setTextFill(nightTextColor);
      this.distance_label_3.setTextFill(nightTextColor);
      this.distance_min_3.setTextFill(nightTextColor);
      this.distance_max_3.setTextFill(nightTextColor);
      this.distance_display_3.setTextFill(nightTextColor);
      this.group_label_4.setTextFill(nightTextColor);
      this.mass_label_4.setTextFill(nightTextColor);
      this.mass_min_4.setTextFill(nightTextColor);
      this.mass_max_4.setTextFill(nightTextColor);
      this.mass_display_4.setTextFill(nightTextColor);
      this.speed_label_4.setTextFill(nightTextColor);
      this.speed_min_4.setTextFill(nightTextColor);
      this.speed_max_4.setTextFill(nightTextColor);
      this.speed_display_4.setTextFill(nightTextColor);
      this.angle_label_4.setTextFill(nightTextColor);
      this.angle_min_4.setTextFill(nightTextColor);
      this.angle_max_4.setTextFill(nightTextColor);
      this.angle_display_4.setTextFill(nightTextColor);
      this.distance_label_4.setTextFill(nightTextColor);
      this.distance_min_4.setTextFill(nightTextColor);
      this.distance_max_4.setTextFill(nightTextColor);
      this.distance_display_4.setTextFill(nightTextColor);
      this.group_label_5.setTextFill(nightTextColor);
      this.mass_label_5.setTextFill(nightTextColor);
      this.mass_min_5.setTextFill(nightTextColor);
      this.mass_max_5.setTextFill(nightTextColor);
      this.mass_display_5.setTextFill(nightTextColor);
      this.speed_label_5.setTextFill(nightTextColor);
      this.speed_min_5.setTextFill(nightTextColor);
      this.speed_max_5.setTextFill(nightTextColor);
      this.speed_display_5.setTextFill(nightTextColor);
      this.angle_label_5.setTextFill(nightTextColor);
      this.angle_min_5.setTextFill(nightTextColor);
      this.angle_max_5.setTextFill(nightTextColor);
      this.angle_display_5.setTextFill(nightTextColor);
      this.distance_label_5.setTextFill(nightTextColor);
      this.distance_min_5.setTextFill(nightTextColor);
      this.distance_max_5.setTextFill(nightTextColor);
      this.distance_display_5.setTextFill(nightTextColor);
      this.group_label_6.setTextFill(nightTextColor);
      this.mass_label_6.setTextFill(nightTextColor);
      this.mass_min_6.setTextFill(nightTextColor);
      this.mass_max_6.setTextFill(nightTextColor);
      this.mass_display_6.setTextFill(nightTextColor);
      this.speed_label_6.setTextFill(nightTextColor);
      this.speed_min_6.setTextFill(nightTextColor);
      this.speed_max_6.setTextFill(nightTextColor);
      this.speed_display_6.setTextFill(nightTextColor);
      this.angle_label_6.setTextFill(nightTextColor);
      this.angle_min_6.setTextFill(nightTextColor);
      this.angle_max_6.setTextFill(nightTextColor);
      this.angle_display_6.setTextFill(nightTextColor);
      this.distance_label_6.setTextFill(nightTextColor);
      this.distance_min_6.setTextFill(nightTextColor);
      this.distance_max_6.setTextFill(nightTextColor);
      this.distance_display_6.setTextFill(nightTextColor);
      this.group_label_7.setTextFill(nightTextColor);
      this.mass_label_7.setTextFill(nightTextColor);
      this.mass_min_7.setTextFill(nightTextColor);
      this.mass_max_7.setTextFill(nightTextColor);
      this.mass_display_7.setTextFill(nightTextColor);
      this.speed_label_7.setTextFill(nightTextColor);
      this.speed_min_7.setTextFill(nightTextColor);
      this.speed_max_7.setTextFill(nightTextColor);
      this.speed_display_7.setTextFill(nightTextColor);
      this.angle_label_7.setTextFill(nightTextColor);
      this.angle_min_7.setTextFill(nightTextColor);
      this.angle_max_7.setTextFill(nightTextColor);
      this.angle_display_7.setTextFill(nightTextColor);
      this.distance_label_7.setTextFill(nightTextColor);
      this.distance_min_7.setTextFill(nightTextColor);
      this.distance_max_7.setTextFill(nightTextColor);
      this.distance_display_7.setTextFill(nightTextColor);
      this.group_label_8.setTextFill(nightTextColor);
      this.mass_label_8.setTextFill(nightTextColor);
      this.mass_min_8.setTextFill(nightTextColor);
      this.mass_max_8.setTextFill(nightTextColor);
      this.mass_display_8.setTextFill(nightTextColor);
      this.speed_label_8.setTextFill(nightTextColor);
      this.speed_min_8.setTextFill(nightTextColor);
      this.speed_max_8.setTextFill(nightTextColor);
      this.speed_display_8.setTextFill(nightTextColor);
      this.angle_label_8.setTextFill(nightTextColor);
      this.angle_min_8.setTextFill(nightTextColor);
      this.angle_max_8.setTextFill(nightTextColor);
      this.angle_display_8.setTextFill(nightTextColor);
      this.distance_label_8.setTextFill(nightTextColor);
      this.distance_min_8.setTextFill(nightTextColor);
      this.distance_max_8.setTextFill(nightTextColor);
      this.distance_display_8.setTextFill(nightTextColor);
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
  
  @FXML
  protected void applyGlowEffectOnLabel(final MouseEvent e) {
    if ((this.nightMode).booleanValue()) {
      Object _source = e.getSource();
      ((Label) _source).setTextFill(Color.rgb(235, 221, 26));
    } else {
      Object _source_1 = e.getSource();
      ((Label) _source_1).setTextFill(Color.rgb(0, 0, 0));
    }
    Glow glowEffect = new Glow();
    glowEffect.setLevel(0.8);
    Object _source_2 = e.getSource();
    ((Label) _source_2).setEffect(glowEffect);
  }
  
  @FXML
  protected void unapplyGlowEffectOnLabel(final MouseEvent e) {
    if ((this.nightMode).booleanValue()) {
      Object _source = e.getSource();
      ((Label) _source).setTextFill(Color.rgb(191, 191, 191));
    } else {
      Object _source_1 = e.getSource();
      ((Label) _source_1).setTextFill(Color.rgb(0, 0, 0));
    }
    Object _source_2 = e.getSource();
    ((Label) _source_2).setEffect(null);
  }
  
  @FXML
  protected void paneTitleGlow(final MouseEvent e) {
    Glow glowEffect = new Glow();
    glowEffect.setLevel(0.8);
    String _substring = e.getSource().toString().substring(0, 1);
    boolean _equals = Objects.equal(_substring, "P");
    if (_equals) {
      Object _source = e.getSource();
      String pane = ((Pane) _source).getId();
      boolean _equals_1 = Objects.equal(pane, "map_selection_pane");
      if (_equals_1) {
        this.map_selection_label.setEffect(glowEffect);
      } else {
        boolean _equals_2 = Objects.equal(pane, "simulation_parameters_pane");
        if (_equals_2) {
          this.settings_label.setEffect(glowEffect);
        }
      }
    } else {
      Object _source_1 = e.getSource();
      String pane_1 = ((ScrollPane) _source_1).getId();
      boolean _equals_3 = Objects.equal(pane_1, "advanced_settings_pane");
      if (_equals_3) {
        this.advanced_settings_label.setEffect(glowEffect);
      }
    }
  }
  
  @FXML
  protected void toggleAdvancedSettingsVisibility() {
    boolean _isVisible = this.advanced_settings_pane.isVisible();
    if (_isVisible) {
      this.advanced_settings_pane.setVisible(false);
    } else {
      this.advanced_settings_pane.setVisible(true);
    }
    this.showGroupConfiguration();
  }
  
  @FXML
  protected void showGroupConfiguration() {
    int _boidsPopulation = this.getBoidsPopulation();
    boolean _lessEqualsThan = (_boidsPopulation <= 4);
    if (_lessEqualsThan) {
      this.advanced_settings_anchor_pane.setPrefHeight(330);
    } else {
      this.advanced_settings_anchor_pane.setPrefHeight(600);
    }
    int _boidsPopulation_1 = this.getBoidsPopulation();
    boolean _greaterEqualsThan = (_boidsPopulation_1 >= 1);
    if (_greaterEqualsThan) {
      this.pane_group_1.setVisible(true);
    } else {
      this.pane_group_1.setVisible(false);
    }
    int _boidsPopulation_2 = this.getBoidsPopulation();
    boolean _greaterEqualsThan_1 = (_boidsPopulation_2 >= 2);
    if (_greaterEqualsThan_1) {
      this.pane_group_2.setVisible(true);
    } else {
      this.pane_group_2.setVisible(false);
    }
    int _boidsPopulation_3 = this.getBoidsPopulation();
    boolean _greaterEqualsThan_2 = (_boidsPopulation_3 >= 3);
    if (_greaterEqualsThan_2) {
      this.pane_group_3.setVisible(true);
    } else {
      this.pane_group_3.setVisible(false);
    }
    int _boidsPopulation_4 = this.getBoidsPopulation();
    boolean _greaterEqualsThan_3 = (_boidsPopulation_4 >= 4);
    if (_greaterEqualsThan_3) {
      this.pane_group_4.setVisible(true);
    } else {
      this.pane_group_4.setVisible(false);
    }
    int _boidsPopulation_5 = this.getBoidsPopulation();
    boolean _greaterEqualsThan_4 = (_boidsPopulation_5 >= 5);
    if (_greaterEqualsThan_4) {
      this.pane_group_5.setVisible(true);
    } else {
      this.pane_group_5.setVisible(false);
    }
    int _boidsPopulation_6 = this.getBoidsPopulation();
    boolean _greaterEqualsThan_5 = (_boidsPopulation_6 >= 6);
    if (_greaterEqualsThan_5) {
      this.pane_group_6.setVisible(true);
    } else {
      this.pane_group_6.setVisible(false);
    }
    int _boidsPopulation_7 = this.getBoidsPopulation();
    boolean _greaterEqualsThan_6 = (_boidsPopulation_7 >= 7);
    if (_greaterEqualsThan_6) {
      this.pane_group_7.setVisible(true);
    } else {
      this.pane_group_7.setVisible(false);
    }
    int _boidsPopulation_8 = this.getBoidsPopulation();
    boolean _greaterEqualsThan_7 = (_boidsPopulation_8 >= 8);
    if (_greaterEqualsThan_7) {
      this.pane_group_8.setVisible(true);
    } else {
      this.pane_group_8.setVisible(false);
    }
  }
  
  @FXML
  protected void paneTitleReset() {
    this.map_selection_label.setEffect(null);
    this.settings_label.setEffect(null);
    this.advanced_settings_label.setEffect(null);
  }
  
  @FXML
  protected void subPaneTitleGlow(final MouseEvent e) {
    Glow glowEffect = new Glow();
    glowEffect.setLevel(0.8);
    Object _source = e.getSource();
    String _id = ((Pane) _source).getId();
    boolean _equals = Objects.equal(_id, "pane_group_1");
    if (_equals) {
      this.group_label_1.setEffect(glowEffect);
    } else {
      Object _source_1 = e.getSource();
      String _id_1 = ((Pane) _source_1).getId();
      boolean _equals_1 = Objects.equal(_id_1, "pane_group_2");
      if (_equals_1) {
        this.group_label_2.setEffect(glowEffect);
      } else {
        Object _source_2 = e.getSource();
        String _id_2 = ((Pane) _source_2).getId();
        boolean _equals_2 = Objects.equal(_id_2, "pane_group_3");
        if (_equals_2) {
          this.group_label_3.setEffect(glowEffect);
        } else {
          Object _source_3 = e.getSource();
          String _id_3 = ((Pane) _source_3).getId();
          boolean _equals_3 = Objects.equal(_id_3, "pane_group_4");
          if (_equals_3) {
            this.group_label_4.setEffect(glowEffect);
          } else {
            Object _source_4 = e.getSource();
            String _id_4 = ((Pane) _source_4).getId();
            boolean _equals_4 = Objects.equal(_id_4, "pane_group_5");
            if (_equals_4) {
              this.group_label_5.setEffect(glowEffect);
            } else {
              Object _source_5 = e.getSource();
              String _id_5 = ((Pane) _source_5).getId();
              boolean _equals_5 = Objects.equal(_id_5, "pane_group_6");
              if (_equals_5) {
                this.group_label_6.setEffect(glowEffect);
              } else {
                Object _source_6 = e.getSource();
                String _id_6 = ((Pane) _source_6).getId();
                boolean _equals_6 = Objects.equal(_id_6, "pane_group_7");
                if (_equals_6) {
                  this.group_label_7.setEffect(glowEffect);
                } else {
                  Object _source_7 = e.getSource();
                  String _id_7 = ((Pane) _source_7).getId();
                  boolean _equals_7 = Objects.equal(_id_7, "pane_group_8");
                  if (_equals_7) {
                    this.group_label_8.setEffect(glowEffect);
                  }
                }
              }
            }
          }
        }
      }
    }
  }
  
  @FXML
  protected void subPaneTitleReset() {
    this.group_label_1.setEffect(null);
    this.group_label_2.setEffect(null);
    this.group_label_3.setEffect(null);
    this.group_label_4.setEffect(null);
    this.group_label_5.setEffect(null);
    this.group_label_6.setEffect(null);
    this.group_label_7.setEffect(null);
    this.group_label_8.setEffect(null);
  }
  
  @FXML
  protected void resetGroupValues() {
    this.mass_1.setValue(Configuration.DEFAULT_MASS);
    this.mass_display_1.setText(Integer.valueOf(Configuration.DEFAULT_MASS).toString());
    this.speed_1.setValue(Configuration.DEFAULT_SPEED);
    this.speed_display_1.setText(Integer.valueOf(Configuration.DEFAULT_SPEED).toString());
    this.angle_1.setValue(Configuration.DEFAULT_ANGLE);
    this.angle_display_1.setText(Integer.valueOf(Configuration.DEFAULT_ANGLE).toString());
    this.distance_1.setValue(Configuration.DEFAULT_DISTANCE);
    this.distance_display_1.setText(Integer.valueOf(Configuration.DEFAULT_DISTANCE).toString());
    this.mass_2.setValue(Configuration.DEFAULT_MASS);
    this.mass_display_2.setText(Integer.valueOf(Configuration.DEFAULT_MASS).toString());
    this.speed_2.setValue(Configuration.DEFAULT_SPEED);
    this.speed_display_2.setText(Integer.valueOf(Configuration.DEFAULT_SPEED).toString());
    this.angle_2.setValue(Configuration.DEFAULT_ANGLE);
    this.angle_display_2.setText(Integer.valueOf(Configuration.DEFAULT_ANGLE).toString());
    this.distance_2.setValue(Configuration.DEFAULT_DISTANCE);
    this.distance_display_2.setText(Integer.valueOf(Configuration.DEFAULT_DISTANCE).toString());
    this.mass_3.setValue(Configuration.DEFAULT_MASS);
    this.mass_display_3.setText(Integer.valueOf(Configuration.DEFAULT_MASS).toString());
    this.speed_3.setValue(Configuration.DEFAULT_SPEED);
    this.speed_display_3.setText(Integer.valueOf(Configuration.DEFAULT_SPEED).toString());
    this.angle_3.setValue(Configuration.DEFAULT_ANGLE);
    this.angle_display_3.setText(Integer.valueOf(Configuration.DEFAULT_ANGLE).toString());
    this.distance_3.setValue(Configuration.DEFAULT_DISTANCE);
    this.distance_display_3.setText(Integer.valueOf(Configuration.DEFAULT_DISTANCE).toString());
    this.mass_4.setValue(Configuration.DEFAULT_MASS);
    this.mass_display_4.setText(Integer.valueOf(Configuration.DEFAULT_MASS).toString());
    this.speed_4.setValue(Configuration.DEFAULT_SPEED);
    this.speed_display_4.setText(Integer.valueOf(Configuration.DEFAULT_SPEED).toString());
    this.angle_4.setValue(Configuration.DEFAULT_ANGLE);
    this.angle_display_4.setText(Integer.valueOf(Configuration.DEFAULT_ANGLE).toString());
    this.distance_4.setValue(Configuration.DEFAULT_DISTANCE);
    this.distance_display_4.setText(Integer.valueOf(Configuration.DEFAULT_DISTANCE).toString());
    this.mass_5.setValue(Configuration.DEFAULT_MASS);
    this.mass_display_5.setText(Integer.valueOf(Configuration.DEFAULT_MASS).toString());
    this.speed_5.setValue(Configuration.DEFAULT_SPEED);
    this.speed_display_5.setText(Integer.valueOf(Configuration.DEFAULT_SPEED).toString());
    this.angle_5.setValue(Configuration.DEFAULT_ANGLE);
    this.angle_display_5.setText(Integer.valueOf(Configuration.DEFAULT_ANGLE).toString());
    this.distance_5.setValue(Configuration.DEFAULT_DISTANCE);
    this.distance_display_5.setText(Integer.valueOf(Configuration.DEFAULT_DISTANCE).toString());
    this.mass_6.setValue(Configuration.DEFAULT_MASS);
    this.mass_display_6.setText(Integer.valueOf(Configuration.DEFAULT_MASS).toString());
    this.speed_6.setValue(Configuration.DEFAULT_SPEED);
    this.speed_display_6.setText(Integer.valueOf(Configuration.DEFAULT_SPEED).toString());
    this.angle_6.setValue(Configuration.DEFAULT_ANGLE);
    this.angle_display_6.setText(Integer.valueOf(Configuration.DEFAULT_ANGLE).toString());
    this.distance_6.setValue(Configuration.DEFAULT_DISTANCE);
    this.distance_display_6.setText(Integer.valueOf(Configuration.DEFAULT_DISTANCE).toString());
    this.mass_7.setValue(Configuration.DEFAULT_MASS);
    this.mass_display_7.setText(Integer.valueOf(Configuration.DEFAULT_MASS).toString());
    this.speed_7.setValue(Configuration.DEFAULT_SPEED);
    this.speed_display_7.setText(Integer.valueOf(Configuration.DEFAULT_SPEED).toString());
    this.angle_7.setValue(Configuration.DEFAULT_ANGLE);
    this.angle_display_7.setText(Integer.valueOf(Configuration.DEFAULT_ANGLE).toString());
    this.distance_7.setValue(Configuration.DEFAULT_DISTANCE);
    this.distance_display_7.setText(Integer.valueOf(Configuration.DEFAULT_DISTANCE).toString());
    this.mass_8.setValue(Configuration.DEFAULT_MASS);
    this.mass_display_8.setText(Integer.valueOf(Configuration.DEFAULT_MASS).toString());
    this.speed_8.setValue(Configuration.DEFAULT_SPEED);
    this.speed_display_8.setText(Integer.valueOf(Configuration.DEFAULT_SPEED).toString());
    this.angle_8.setValue(Configuration.DEFAULT_ANGLE);
    this.angle_display_8.setText(Integer.valueOf(Configuration.DEFAULT_ANGLE).toString());
    this.distance_8.setValue(Configuration.DEFAULT_DISTANCE);
    this.distance_display_8.setText(Integer.valueOf(Configuration.DEFAULT_DISTANCE).toString());
  }
  
  @FXML
  protected void massListener(final MouseEvent e) {
    Object _source = e.getSource();
    final InvalidationListener _function = (Observable it) -> {
      Object _source_1 = e.getSource();
      String _id = ((ScrollBar) _source_1).getId();
      boolean _equals = Objects.equal(_id, "mass_1");
      if (_equals) {
        Object _source_2 = e.getSource();
        this.mass_display_1.setText(String.format("%.0f", Double.valueOf(((ScrollBar) _source_2).getValue())));
      } else {
        Object _source_3 = e.getSource();
        String _id_1 = ((ScrollBar) _source_3).getId();
        boolean _equals_1 = Objects.equal(_id_1, "mass_2");
        if (_equals_1) {
          Object _source_4 = e.getSource();
          this.mass_display_2.setText(String.format("%.0f", Double.valueOf(((ScrollBar) _source_4).getValue())));
        } else {
          Object _source_5 = e.getSource();
          String _id_2 = ((ScrollBar) _source_5).getId();
          boolean _equals_2 = Objects.equal(_id_2, "mass_3");
          if (_equals_2) {
            Object _source_6 = e.getSource();
            this.mass_display_3.setText(String.format("%.0f", Double.valueOf(((ScrollBar) _source_6).getValue())));
          } else {
            Object _source_7 = e.getSource();
            String _id_3 = ((ScrollBar) _source_7).getId();
            boolean _equals_3 = Objects.equal(_id_3, "mass_4");
            if (_equals_3) {
              Object _source_8 = e.getSource();
              this.mass_display_4.setText(String.format("%.0f", Double.valueOf(((ScrollBar) _source_8).getValue())));
            } else {
              Object _source_9 = e.getSource();
              String _id_4 = ((ScrollBar) _source_9).getId();
              boolean _equals_4 = Objects.equal(_id_4, "mass_5");
              if (_equals_4) {
                Object _source_10 = e.getSource();
                this.mass_display_5.setText(String.format("%.0f", Double.valueOf(((ScrollBar) _source_10).getValue())));
              } else {
                Object _source_11 = e.getSource();
                String _id_5 = ((ScrollBar) _source_11).getId();
                boolean _equals_5 = Objects.equal(_id_5, "mass_6");
                if (_equals_5) {
                  Object _source_12 = e.getSource();
                  this.mass_display_6.setText(String.format("%.0f", Double.valueOf(((ScrollBar) _source_12).getValue())));
                } else {
                  Object _source_13 = e.getSource();
                  String _id_6 = ((ScrollBar) _source_13).getId();
                  boolean _equals_6 = Objects.equal(_id_6, "mass_7");
                  if (_equals_6) {
                    Object _source_14 = e.getSource();
                    this.mass_display_7.setText(String.format("%.0f", Double.valueOf(((ScrollBar) _source_14).getValue())));
                  } else {
                    Object _source_15 = e.getSource();
                    String _id_7 = ((ScrollBar) _source_15).getId();
                    boolean _equals_7 = Objects.equal(_id_7, "mass_8");
                    if (_equals_7) {
                      Object _source_16 = e.getSource();
                      this.mass_display_8.setText(String.format("%.0f", Double.valueOf(((ScrollBar) _source_16).getValue())));
                    }
                  }
                }
              }
            }
          }
        }
      }
    };
    ((ScrollBar) _source).valueProperty().addListener(_function);
  }
  
  @FXML
  protected void speedListener(final MouseEvent e) {
    Object _source = e.getSource();
    final InvalidationListener _function = (Observable it) -> {
      Object _source_1 = e.getSource();
      String _id = ((ScrollBar) _source_1).getId();
      boolean _equals = Objects.equal(_id, "speed_1");
      if (_equals) {
        Object _source_2 = e.getSource();
        this.speed_display_1.setText(String.format("%.0f", Double.valueOf(((ScrollBar) _source_2).getValue())));
      } else {
        Object _source_3 = e.getSource();
        String _id_1 = ((ScrollBar) _source_3).getId();
        boolean _equals_1 = Objects.equal(_id_1, "speed_2");
        if (_equals_1) {
          Object _source_4 = e.getSource();
          this.speed_display_2.setText(String.format("%.0f", Double.valueOf(((ScrollBar) _source_4).getValue())));
        } else {
          Object _source_5 = e.getSource();
          String _id_2 = ((ScrollBar) _source_5).getId();
          boolean _equals_2 = Objects.equal(_id_2, "speed_3");
          if (_equals_2) {
            Object _source_6 = e.getSource();
            this.speed_display_3.setText(String.format("%.0f", Double.valueOf(((ScrollBar) _source_6).getValue())));
          } else {
            Object _source_7 = e.getSource();
            String _id_3 = ((ScrollBar) _source_7).getId();
            boolean _equals_3 = Objects.equal(_id_3, "speed_4");
            if (_equals_3) {
              Object _source_8 = e.getSource();
              this.speed_display_4.setText(String.format("%.0f", Double.valueOf(((ScrollBar) _source_8).getValue())));
            } else {
              Object _source_9 = e.getSource();
              String _id_4 = ((ScrollBar) _source_9).getId();
              boolean _equals_4 = Objects.equal(_id_4, "speed_5");
              if (_equals_4) {
                Object _source_10 = e.getSource();
                this.speed_display_5.setText(String.format("%.0f", Double.valueOf(((ScrollBar) _source_10).getValue())));
              } else {
                Object _source_11 = e.getSource();
                String _id_5 = ((ScrollBar) _source_11).getId();
                boolean _equals_5 = Objects.equal(_id_5, "speed_6");
                if (_equals_5) {
                  Object _source_12 = e.getSource();
                  this.speed_display_6.setText(String.format("%.0f", Double.valueOf(((ScrollBar) _source_12).getValue())));
                } else {
                  Object _source_13 = e.getSource();
                  String _id_6 = ((ScrollBar) _source_13).getId();
                  boolean _equals_6 = Objects.equal(_id_6, "speed_7");
                  if (_equals_6) {
                    Object _source_14 = e.getSource();
                    this.speed_display_7.setText(String.format("%.0f", Double.valueOf(((ScrollBar) _source_14).getValue())));
                  } else {
                    Object _source_15 = e.getSource();
                    String _id_7 = ((ScrollBar) _source_15).getId();
                    boolean _equals_7 = Objects.equal(_id_7, "speed_8");
                    if (_equals_7) {
                      Object _source_16 = e.getSource();
                      this.speed_display_8.setText(String.format("%.0f", Double.valueOf(((ScrollBar) _source_16).getValue())));
                    }
                  }
                }
              }
            }
          }
        }
      }
    };
    ((ScrollBar) _source).valueProperty().addListener(_function);
  }
  
  @FXML
  protected void angleListener(final MouseEvent e) {
    Object _source = e.getSource();
    final InvalidationListener _function = (Observable it) -> {
      Object _source_1 = e.getSource();
      String _id = ((ScrollBar) _source_1).getId();
      boolean _equals = Objects.equal(_id, "angle_1");
      if (_equals) {
        Object _source_2 = e.getSource();
        this.angle_display_1.setText(String.format("%.0f", Double.valueOf(((ScrollBar) _source_2).getValue())));
      } else {
        Object _source_3 = e.getSource();
        String _id_1 = ((ScrollBar) _source_3).getId();
        boolean _equals_1 = Objects.equal(_id_1, "angle_2");
        if (_equals_1) {
          Object _source_4 = e.getSource();
          this.angle_display_2.setText(String.format("%.0f", Double.valueOf(((ScrollBar) _source_4).getValue())));
        } else {
          Object _source_5 = e.getSource();
          String _id_2 = ((ScrollBar) _source_5).getId();
          boolean _equals_2 = Objects.equal(_id_2, "angle_3");
          if (_equals_2) {
            Object _source_6 = e.getSource();
            this.angle_display_3.setText(String.format("%.0f", Double.valueOf(((ScrollBar) _source_6).getValue())));
          } else {
            Object _source_7 = e.getSource();
            String _id_3 = ((ScrollBar) _source_7).getId();
            boolean _equals_3 = Objects.equal(_id_3, "angle_4");
            if (_equals_3) {
              Object _source_8 = e.getSource();
              this.angle_display_4.setText(String.format("%.0f", Double.valueOf(((ScrollBar) _source_8).getValue())));
            } else {
              Object _source_9 = e.getSource();
              String _id_4 = ((ScrollBar) _source_9).getId();
              boolean _equals_4 = Objects.equal(_id_4, "angle_5");
              if (_equals_4) {
                Object _source_10 = e.getSource();
                this.angle_display_5.setText(String.format("%.0f", Double.valueOf(((ScrollBar) _source_10).getValue())));
              } else {
                Object _source_11 = e.getSource();
                String _id_5 = ((ScrollBar) _source_11).getId();
                boolean _equals_5 = Objects.equal(_id_5, "angle_6");
                if (_equals_5) {
                  Object _source_12 = e.getSource();
                  this.angle_display_6.setText(String.format("%.0f", Double.valueOf(((ScrollBar) _source_12).getValue())));
                } else {
                  Object _source_13 = e.getSource();
                  String _id_6 = ((ScrollBar) _source_13).getId();
                  boolean _equals_6 = Objects.equal(_id_6, "angle_7");
                  if (_equals_6) {
                    Object _source_14 = e.getSource();
                    this.angle_display_7.setText(String.format("%.0f", Double.valueOf(((ScrollBar) _source_14).getValue())));
                  } else {
                    Object _source_15 = e.getSource();
                    String _id_7 = ((ScrollBar) _source_15).getId();
                    boolean _equals_7 = Objects.equal(_id_7, "angle_8");
                    if (_equals_7) {
                      Object _source_16 = e.getSource();
                      this.angle_display_8.setText(String.format("%.0f", Double.valueOf(((ScrollBar) _source_16).getValue())));
                    }
                  }
                }
              }
            }
          }
        }
      }
    };
    ((ScrollBar) _source).valueProperty().addListener(_function);
  }
  
  @FXML
  protected void distanceListener(final MouseEvent e) {
    Object _source = e.getSource();
    final InvalidationListener _function = (Observable it) -> {
      Object _source_1 = e.getSource();
      String _id = ((ScrollBar) _source_1).getId();
      boolean _equals = Objects.equal(_id, "distance_1");
      if (_equals) {
        Object _source_2 = e.getSource();
        this.distance_display_1.setText(String.format("%.0f", Double.valueOf(((ScrollBar) _source_2).getValue())));
      } else {
        Object _source_3 = e.getSource();
        String _id_1 = ((ScrollBar) _source_3).getId();
        boolean _equals_1 = Objects.equal(_id_1, "distance_2");
        if (_equals_1) {
          Object _source_4 = e.getSource();
          this.distance_display_2.setText(String.format("%.0f", Double.valueOf(((ScrollBar) _source_4).getValue())));
        } else {
          Object _source_5 = e.getSource();
          String _id_2 = ((ScrollBar) _source_5).getId();
          boolean _equals_2 = Objects.equal(_id_2, "distance_3");
          if (_equals_2) {
            Object _source_6 = e.getSource();
            this.distance_display_3.setText(String.format("%.0f", Double.valueOf(((ScrollBar) _source_6).getValue())));
          } else {
            Object _source_7 = e.getSource();
            String _id_3 = ((ScrollBar) _source_7).getId();
            boolean _equals_3 = Objects.equal(_id_3, "distance_4");
            if (_equals_3) {
              Object _source_8 = e.getSource();
              this.distance_display_4.setText(String.format("%.0f", Double.valueOf(((ScrollBar) _source_8).getValue())));
            } else {
              Object _source_9 = e.getSource();
              String _id_4 = ((ScrollBar) _source_9).getId();
              boolean _equals_4 = Objects.equal(_id_4, "distance_5");
              if (_equals_4) {
                Object _source_10 = e.getSource();
                this.distance_display_5.setText(String.format("%.0f", Double.valueOf(((ScrollBar) _source_10).getValue())));
              } else {
                Object _source_11 = e.getSource();
                String _id_5 = ((ScrollBar) _source_11).getId();
                boolean _equals_5 = Objects.equal(_id_5, "distance_6");
                if (_equals_5) {
                  Object _source_12 = e.getSource();
                  this.distance_display_6.setText(String.format("%.0f", Double.valueOf(((ScrollBar) _source_12).getValue())));
                } else {
                  Object _source_13 = e.getSource();
                  String _id_6 = ((ScrollBar) _source_13).getId();
                  boolean _equals_6 = Objects.equal(_id_6, "distance_7");
                  if (_equals_6) {
                    Object _source_14 = e.getSource();
                    this.distance_display_7.setText(String.format("%.0f", Double.valueOf(((ScrollBar) _source_14).getValue())));
                  } else {
                    Object _source_15 = e.getSource();
                    String _id_7 = ((ScrollBar) _source_15).getId();
                    boolean _equals_7 = Objects.equal(_id_7, "distance_8");
                    if (_equals_7) {
                      Object _source_16 = e.getSource();
                      this.distance_display_8.setText(String.format("%.0f", Double.valueOf(((ScrollBar) _source_16).getValue())));
                    }
                  }
                }
              }
            }
          }
        }
      }
    };
    ((ScrollBar) _source).valueProperty().addListener(_function);
  }
  
  @FXML
  protected void subMinMaxValueGlow(final MouseEvent e) {
    if ((this.nightMode).booleanValue()) {
      Object _source = e.getSource();
      ((Label) _source).setTextFill(Color.rgb(235, 221, 26));
    } else {
      Object _source_1 = e.getSource();
      ((Label) _source_1).setTextFill(Color.rgb(0, 0, 0));
    }
    Glow glowEffect = new Glow();
    glowEffect.setLevel(0.8);
    Object _source_2 = e.getSource();
    ((Label) _source_2).setEffect(glowEffect);
  }
  
  @FXML
  protected void subMinMaxValueReset(final MouseEvent e) {
    if ((this.nightMode).booleanValue()) {
      Object _source = e.getSource();
      ((Label) _source).setTextFill(Color.rgb(191, 191, 191));
    } else {
      Object _source_1 = e.getSource();
      ((Label) _source_1).setTextFill(Color.rgb(0, 0, 0));
    }
    Object _source_2 = e.getSource();
    ((Label) _source_2).setEffect(null);
  }
  
  @FXML
  protected void setFastMassValues(final MouseEvent e) {
    Object _source = e.getSource();
    Object _source_1 = e.getSource();
    int _length = ((Label) _source_1).getId().length();
    int _minus = (_length - 1);
    String _substring = ((Label) _source).getId().substring(_minus);
    boolean _equals = Objects.equal(_substring, "1");
    if (_equals) {
      Object _source_2 = e.getSource();
      this.mass_1.setValue(Integer.parseInt(((Label) _source_2).getText()));
      Object _source_3 = e.getSource();
      this.mass_display_1.setText(((Label) _source_3).getText());
    } else {
      Object _source_4 = e.getSource();
      Object _source_5 = e.getSource();
      int _length_1 = ((Label) _source_5).getId().length();
      int _minus_1 = (_length_1 - 1);
      String _substring_1 = ((Label) _source_4).getId().substring(_minus_1);
      boolean _equals_1 = Objects.equal(_substring_1, "2");
      if (_equals_1) {
        Object _source_6 = e.getSource();
        this.mass_2.setValue(Integer.parseInt(((Label) _source_6).getText()));
        Object _source_7 = e.getSource();
        this.mass_display_2.setText(((Label) _source_7).getText());
      } else {
        Object _source_8 = e.getSource();
        Object _source_9 = e.getSource();
        int _length_2 = ((Label) _source_9).getId().length();
        int _minus_2 = (_length_2 - 1);
        String _substring_2 = ((Label) _source_8).getId().substring(_minus_2);
        boolean _equals_2 = Objects.equal(_substring_2, "3");
        if (_equals_2) {
          Object _source_10 = e.getSource();
          this.mass_3.setValue(Integer.parseInt(((Label) _source_10).getText()));
          Object _source_11 = e.getSource();
          this.mass_display_3.setText(((Label) _source_11).getText());
        } else {
          Object _source_12 = e.getSource();
          Object _source_13 = e.getSource();
          int _length_3 = ((Label) _source_13).getId().length();
          int _minus_3 = (_length_3 - 1);
          String _substring_3 = ((Label) _source_12).getId().substring(_minus_3);
          boolean _equals_3 = Objects.equal(_substring_3, "4");
          if (_equals_3) {
            Object _source_14 = e.getSource();
            this.mass_4.setValue(Integer.parseInt(((Label) _source_14).getText()));
            Object _source_15 = e.getSource();
            this.mass_display_4.setText(((Label) _source_15).getText());
          } else {
            Object _source_16 = e.getSource();
            Object _source_17 = e.getSource();
            int _length_4 = ((Label) _source_17).getId().length();
            int _minus_4 = (_length_4 - 1);
            String _substring_4 = ((Label) _source_16).getId().substring(_minus_4);
            boolean _equals_4 = Objects.equal(_substring_4, "5");
            if (_equals_4) {
              Object _source_18 = e.getSource();
              this.mass_5.setValue(Integer.parseInt(((Label) _source_18).getText()));
              Object _source_19 = e.getSource();
              this.mass_display_5.setText(((Label) _source_19).getText());
            } else {
              Object _source_20 = e.getSource();
              Object _source_21 = e.getSource();
              int _length_5 = ((Label) _source_21).getId().length();
              int _minus_5 = (_length_5 - 1);
              String _substring_5 = ((Label) _source_20).getId().substring(_minus_5);
              boolean _equals_5 = Objects.equal(_substring_5, "6");
              if (_equals_5) {
                Object _source_22 = e.getSource();
                this.mass_6.setValue(Integer.parseInt(((Label) _source_22).getText()));
                Object _source_23 = e.getSource();
                this.mass_display_6.setText(((Label) _source_23).getText());
              } else {
                Object _source_24 = e.getSource();
                Object _source_25 = e.getSource();
                int _length_6 = ((Label) _source_25).getId().length();
                int _minus_6 = (_length_6 - 1);
                String _substring_6 = ((Label) _source_24).getId().substring(_minus_6);
                boolean _equals_6 = Objects.equal(_substring_6, "7");
                if (_equals_6) {
                  Object _source_26 = e.getSource();
                  this.mass_7.setValue(Integer.parseInt(((Label) _source_26).getText()));
                  Object _source_27 = e.getSource();
                  this.mass_display_7.setText(((Label) _source_27).getText());
                } else {
                  Object _source_28 = e.getSource();
                  Object _source_29 = e.getSource();
                  int _length_7 = ((Label) _source_29).getId().length();
                  int _minus_7 = (_length_7 - 1);
                  String _substring_7 = ((Label) _source_28).getId().substring(_minus_7);
                  boolean _equals_7 = Objects.equal(_substring_7, "8");
                  if (_equals_7) {
                    Object _source_30 = e.getSource();
                    this.mass_8.setValue(Integer.parseInt(((Label) _source_30).getText()));
                    Object _source_31 = e.getSource();
                    this.mass_display_8.setText(((Label) _source_31).getText());
                  }
                }
              }
            }
          }
        }
      }
    }
  }
  
  @FXML
  protected void setFastSpeedValues(final MouseEvent e) {
    Object _source = e.getSource();
    Object _source_1 = e.getSource();
    int _length = ((Label) _source_1).getId().length();
    int _minus = (_length - 1);
    String _substring = ((Label) _source).getId().substring(_minus);
    boolean _equals = Objects.equal(_substring, "1");
    if (_equals) {
      Object _source_2 = e.getSource();
      this.speed_1.setValue(Integer.parseInt(((Label) _source_2).getText()));
      Object _source_3 = e.getSource();
      this.speed_display_1.setText(((Label) _source_3).getText());
    } else {
      Object _source_4 = e.getSource();
      Object _source_5 = e.getSource();
      int _length_1 = ((Label) _source_5).getId().length();
      int _minus_1 = (_length_1 - 1);
      String _substring_1 = ((Label) _source_4).getId().substring(_minus_1);
      boolean _equals_1 = Objects.equal(_substring_1, "2");
      if (_equals_1) {
        Object _source_6 = e.getSource();
        this.speed_2.setValue(Integer.parseInt(((Label) _source_6).getText()));
        Object _source_7 = e.getSource();
        this.speed_display_2.setText(((Label) _source_7).getText());
      } else {
        Object _source_8 = e.getSource();
        Object _source_9 = e.getSource();
        int _length_2 = ((Label) _source_9).getId().length();
        int _minus_2 = (_length_2 - 1);
        String _substring_2 = ((Label) _source_8).getId().substring(_minus_2);
        boolean _equals_2 = Objects.equal(_substring_2, "3");
        if (_equals_2) {
          Object _source_10 = e.getSource();
          this.speed_3.setValue(Integer.parseInt(((Label) _source_10).getText()));
          Object _source_11 = e.getSource();
          this.speed_display_3.setText(((Label) _source_11).getText());
        } else {
          Object _source_12 = e.getSource();
          Object _source_13 = e.getSource();
          int _length_3 = ((Label) _source_13).getId().length();
          int _minus_3 = (_length_3 - 1);
          String _substring_3 = ((Label) _source_12).getId().substring(_minus_3);
          boolean _equals_3 = Objects.equal(_substring_3, "4");
          if (_equals_3) {
            Object _source_14 = e.getSource();
            this.speed_4.setValue(Integer.parseInt(((Label) _source_14).getText()));
            Object _source_15 = e.getSource();
            this.speed_display_4.setText(((Label) _source_15).getText());
          } else {
            Object _source_16 = e.getSource();
            Object _source_17 = e.getSource();
            int _length_4 = ((Label) _source_17).getId().length();
            int _minus_4 = (_length_4 - 1);
            String _substring_4 = ((Label) _source_16).getId().substring(_minus_4);
            boolean _equals_4 = Objects.equal(_substring_4, "5");
            if (_equals_4) {
              Object _source_18 = e.getSource();
              this.speed_5.setValue(Integer.parseInt(((Label) _source_18).getText()));
              Object _source_19 = e.getSource();
              this.speed_display_5.setText(((Label) _source_19).getText());
            } else {
              Object _source_20 = e.getSource();
              Object _source_21 = e.getSource();
              int _length_5 = ((Label) _source_21).getId().length();
              int _minus_5 = (_length_5 - 1);
              String _substring_5 = ((Label) _source_20).getId().substring(_minus_5);
              boolean _equals_5 = Objects.equal(_substring_5, "6");
              if (_equals_5) {
                Object _source_22 = e.getSource();
                this.speed_6.setValue(Integer.parseInt(((Label) _source_22).getText()));
                Object _source_23 = e.getSource();
                this.speed_display_6.setText(((Label) _source_23).getText());
              } else {
                Object _source_24 = e.getSource();
                Object _source_25 = e.getSource();
                int _length_6 = ((Label) _source_25).getId().length();
                int _minus_6 = (_length_6 - 1);
                String _substring_6 = ((Label) _source_24).getId().substring(_minus_6);
                boolean _equals_6 = Objects.equal(_substring_6, "7");
                if (_equals_6) {
                  Object _source_26 = e.getSource();
                  this.speed_7.setValue(Integer.parseInt(((Label) _source_26).getText()));
                  Object _source_27 = e.getSource();
                  this.speed_display_7.setText(((Label) _source_27).getText());
                } else {
                  Object _source_28 = e.getSource();
                  Object _source_29 = e.getSource();
                  int _length_7 = ((Label) _source_29).getId().length();
                  int _minus_7 = (_length_7 - 1);
                  String _substring_7 = ((Label) _source_28).getId().substring(_minus_7);
                  boolean _equals_7 = Objects.equal(_substring_7, "8");
                  if (_equals_7) {
                    Object _source_30 = e.getSource();
                    this.speed_8.setValue(Integer.parseInt(((Label) _source_30).getText()));
                    Object _source_31 = e.getSource();
                    this.speed_display_8.setText(((Label) _source_31).getText());
                  }
                }
              }
            }
          }
        }
      }
    }
  }
  
  @FXML
  protected void setFastAngleValues(final MouseEvent e) {
    Object _source = e.getSource();
    Object _source_1 = e.getSource();
    int _length = ((Label) _source_1).getId().length();
    int _minus = (_length - 1);
    String _substring = ((Label) _source).getId().substring(_minus);
    boolean _equals = Objects.equal(_substring, "1");
    if (_equals) {
      Object _source_2 = e.getSource();
      this.angle_1.setValue(Integer.parseInt(((Label) _source_2).getText()));
      Object _source_3 = e.getSource();
      this.angle_display_1.setText(((Label) _source_3).getText());
    } else {
      Object _source_4 = e.getSource();
      Object _source_5 = e.getSource();
      int _length_1 = ((Label) _source_5).getId().length();
      int _minus_1 = (_length_1 - 1);
      String _substring_1 = ((Label) _source_4).getId().substring(_minus_1);
      boolean _equals_1 = Objects.equal(_substring_1, "2");
      if (_equals_1) {
        Object _source_6 = e.getSource();
        this.angle_2.setValue(Integer.parseInt(((Label) _source_6).getText()));
        Object _source_7 = e.getSource();
        this.angle_display_2.setText(((Label) _source_7).getText());
      } else {
        Object _source_8 = e.getSource();
        Object _source_9 = e.getSource();
        int _length_2 = ((Label) _source_9).getId().length();
        int _minus_2 = (_length_2 - 1);
        String _substring_2 = ((Label) _source_8).getId().substring(_minus_2);
        boolean _equals_2 = Objects.equal(_substring_2, "3");
        if (_equals_2) {
          Object _source_10 = e.getSource();
          this.angle_3.setValue(Integer.parseInt(((Label) _source_10).getText()));
          Object _source_11 = e.getSource();
          this.angle_display_3.setText(((Label) _source_11).getText());
        } else {
          Object _source_12 = e.getSource();
          Object _source_13 = e.getSource();
          int _length_3 = ((Label) _source_13).getId().length();
          int _minus_3 = (_length_3 - 1);
          String _substring_3 = ((Label) _source_12).getId().substring(_minus_3);
          boolean _equals_3 = Objects.equal(_substring_3, "4");
          if (_equals_3) {
            Object _source_14 = e.getSource();
            this.angle_4.setValue(Integer.parseInt(((Label) _source_14).getText()));
            Object _source_15 = e.getSource();
            this.angle_display_4.setText(((Label) _source_15).getText());
          } else {
            Object _source_16 = e.getSource();
            Object _source_17 = e.getSource();
            int _length_4 = ((Label) _source_17).getId().length();
            int _minus_4 = (_length_4 - 1);
            String _substring_4 = ((Label) _source_16).getId().substring(_minus_4);
            boolean _equals_4 = Objects.equal(_substring_4, "5");
            if (_equals_4) {
              Object _source_18 = e.getSource();
              this.angle_5.setValue(Integer.parseInt(((Label) _source_18).getText()));
              Object _source_19 = e.getSource();
              this.angle_display_5.setText(((Label) _source_19).getText());
            } else {
              Object _source_20 = e.getSource();
              Object _source_21 = e.getSource();
              int _length_5 = ((Label) _source_21).getId().length();
              int _minus_5 = (_length_5 - 1);
              String _substring_5 = ((Label) _source_20).getId().substring(_minus_5);
              boolean _equals_5 = Objects.equal(_substring_5, "6");
              if (_equals_5) {
                Object _source_22 = e.getSource();
                this.angle_6.setValue(Integer.parseInt(((Label) _source_22).getText()));
                Object _source_23 = e.getSource();
                this.angle_display_6.setText(((Label) _source_23).getText());
              } else {
                Object _source_24 = e.getSource();
                Object _source_25 = e.getSource();
                int _length_6 = ((Label) _source_25).getId().length();
                int _minus_6 = (_length_6 - 1);
                String _substring_6 = ((Label) _source_24).getId().substring(_minus_6);
                boolean _equals_6 = Objects.equal(_substring_6, "7");
                if (_equals_6) {
                  Object _source_26 = e.getSource();
                  this.angle_7.setValue(Integer.parseInt(((Label) _source_26).getText()));
                  Object _source_27 = e.getSource();
                  this.angle_display_7.setText(((Label) _source_27).getText());
                } else {
                  Object _source_28 = e.getSource();
                  Object _source_29 = e.getSource();
                  int _length_7 = ((Label) _source_29).getId().length();
                  int _minus_7 = (_length_7 - 1);
                  String _substring_7 = ((Label) _source_28).getId().substring(_minus_7);
                  boolean _equals_7 = Objects.equal(_substring_7, "8");
                  if (_equals_7) {
                    Object _source_30 = e.getSource();
                    this.angle_8.setValue(Integer.parseInt(((Label) _source_30).getText()));
                    Object _source_31 = e.getSource();
                    this.angle_display_8.setText(((Label) _source_31).getText());
                  }
                }
              }
            }
          }
        }
      }
    }
  }
  
  @FXML
  protected void setFastDistanceValues(final MouseEvent e) {
    Object _source = e.getSource();
    Object _source_1 = e.getSource();
    int _length = ((Label) _source_1).getId().length();
    int _minus = (_length - 1);
    String _substring = ((Label) _source).getId().substring(_minus);
    boolean _equals = Objects.equal(_substring, "1");
    if (_equals) {
      Object _source_2 = e.getSource();
      this.distance_1.setValue(Integer.parseInt(((Label) _source_2).getText()));
      Object _source_3 = e.getSource();
      this.distance_display_1.setText(((Label) _source_3).getText());
    } else {
      Object _source_4 = e.getSource();
      Object _source_5 = e.getSource();
      int _length_1 = ((Label) _source_5).getId().length();
      int _minus_1 = (_length_1 - 1);
      String _substring_1 = ((Label) _source_4).getId().substring(_minus_1);
      boolean _equals_1 = Objects.equal(_substring_1, "2");
      if (_equals_1) {
        Object _source_6 = e.getSource();
        this.distance_2.setValue(Integer.parseInt(((Label) _source_6).getText()));
        Object _source_7 = e.getSource();
        this.distance_display_2.setText(((Label) _source_7).getText());
      } else {
        Object _source_8 = e.getSource();
        Object _source_9 = e.getSource();
        int _length_2 = ((Label) _source_9).getId().length();
        int _minus_2 = (_length_2 - 1);
        String _substring_2 = ((Label) _source_8).getId().substring(_minus_2);
        boolean _equals_2 = Objects.equal(_substring_2, "3");
        if (_equals_2) {
          Object _source_10 = e.getSource();
          this.distance_3.setValue(Integer.parseInt(((Label) _source_10).getText()));
          Object _source_11 = e.getSource();
          this.distance_display_3.setText(((Label) _source_11).getText());
        } else {
          Object _source_12 = e.getSource();
          Object _source_13 = e.getSource();
          int _length_3 = ((Label) _source_13).getId().length();
          int _minus_3 = (_length_3 - 1);
          String _substring_3 = ((Label) _source_12).getId().substring(_minus_3);
          boolean _equals_3 = Objects.equal(_substring_3, "4");
          if (_equals_3) {
            Object _source_14 = e.getSource();
            this.distance_4.setValue(Integer.parseInt(((Label) _source_14).getText()));
            Object _source_15 = e.getSource();
            this.distance_display_4.setText(((Label) _source_15).getText());
          } else {
            Object _source_16 = e.getSource();
            Object _source_17 = e.getSource();
            int _length_4 = ((Label) _source_17).getId().length();
            int _minus_4 = (_length_4 - 1);
            String _substring_4 = ((Label) _source_16).getId().substring(_minus_4);
            boolean _equals_4 = Objects.equal(_substring_4, "5");
            if (_equals_4) {
              Object _source_18 = e.getSource();
              this.distance_5.setValue(Integer.parseInt(((Label) _source_18).getText()));
              Object _source_19 = e.getSource();
              this.distance_display_5.setText(((Label) _source_19).getText());
            } else {
              Object _source_20 = e.getSource();
              Object _source_21 = e.getSource();
              int _length_5 = ((Label) _source_21).getId().length();
              int _minus_5 = (_length_5 - 1);
              String _substring_5 = ((Label) _source_20).getId().substring(_minus_5);
              boolean _equals_5 = Objects.equal(_substring_5, "6");
              if (_equals_5) {
                Object _source_22 = e.getSource();
                this.distance_6.setValue(Integer.parseInt(((Label) _source_22).getText()));
                Object _source_23 = e.getSource();
                this.distance_display_6.setText(((Label) _source_23).getText());
              } else {
                Object _source_24 = e.getSource();
                Object _source_25 = e.getSource();
                int _length_6 = ((Label) _source_25).getId().length();
                int _minus_6 = (_length_6 - 1);
                String _substring_6 = ((Label) _source_24).getId().substring(_minus_6);
                boolean _equals_6 = Objects.equal(_substring_6, "7");
                if (_equals_6) {
                  Object _source_26 = e.getSource();
                  this.distance_7.setValue(Integer.parseInt(((Label) _source_26).getText()));
                  Object _source_27 = e.getSource();
                  this.distance_display_7.setText(((Label) _source_27).getText());
                } else {
                  Object _source_28 = e.getSource();
                  Object _source_29 = e.getSource();
                  int _length_7 = ((Label) _source_29).getId().length();
                  int _minus_7 = (_length_7 - 1);
                  String _substring_7 = ((Label) _source_28).getId().substring(_minus_7);
                  boolean _equals_7 = Objects.equal(_substring_7, "8");
                  if (_equals_7) {
                    Object _source_30 = e.getSource();
                    this.distance_8.setValue(Integer.parseInt(((Label) _source_30).getText()));
                    Object _source_31 = e.getSource();
                    this.distance_display_8.setText(((Label) _source_31).getText());
                  }
                }
              }
            }
          }
        }
      }
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
