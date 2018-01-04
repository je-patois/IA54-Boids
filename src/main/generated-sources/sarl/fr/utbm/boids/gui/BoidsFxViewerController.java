package fr.utbm.boids.gui;

import com.google.common.base.Objects;
import fr.utbm.boids.Configuration;
import fr.utbm.boids.body.BoidBody;
import fr.utbm.boids.environment.Obstacle;
import fr.utbm.boids.events.ConfigureSimulation;
import fr.utbm.boids.events.Pause;
import fr.utbm.boids.events.PositionModification;
import fr.utbm.boids.events.Resume;
import fr.utbm.boids.gui.fx.FxViewerController;
import fr.utbm.boids.util.BoidGroupInfos;
import fr.utbm.boids.util.Coordinates;
import fr.utbm.boids.util.Vector;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
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
import javafx.scene.input.KeyEvent;
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
import org.eclipse.xtext.xbase.lib.DoubleExtensions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure0;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(9)
@SuppressWarnings("all")
public class BoidsFxViewerController extends FxViewerController {
  /**
   * Etat de la simulation
   */
  private boolean launched = false;
  
  /**
   * Etat de la map
   */
  private boolean mapCreated = false;
  
  /**
   * Map sélectionnée
   */
  private int selectedMap = 1;
  
  /**
   * Paramètres des différentes familles de boids
   */
  private List<BoidGroupInfos> boidsSettings = new ArrayList<BoidGroupInfos>();
  
  /**
   * Liste des  polygones de la map
   */
  private List<Polygon> polygons;
  
  /**
   * Liste des coordonnées relatives aux polygones de la map
   */
  private List<List<Coordinates>> polygonsCoordinates;
  
  /**
   * Liste des obstacles de la map
   */
  private List<Obstacle> obstacles = new ArrayList<Obstacle>();
  
  /**
   * Activation/Désactivation du mode nuit
   */
  private Boolean nightMode = Boolean.valueOf(true);
  
  /**
   * Activation/Désactivation de la perception
   */
  private Boolean togglePerception = Boolean.valueOf(true);
  
  /**
   * Boid actuellement suivi
   */
  private UUID currentBoid;
  
  /**
   * Polygone actuellement suivi
   */
  private Polygon currentPolygon = null;
  
  /**
   * Abscisse du polygone drag
   */
  private double orgSceneX;
  
  /**
   * Ordonnée du polygone drag
   */
  private double orgSceneY;
  
  /**
   * Valeur en Y de la translation du polygone drag
   */
  private double orgTranslateX;
  
  /**
   * Valeur en Y de la translation du polygone drag
   */
  private double orgTranslateY;
  
  /**
   * Sauvegarde de la position initiale du polygone drag
   */
  private List<Double> savedPosition = new ArrayList<Double>();
  
  /**
   * Liste des arcs de perception
   */
  private Map<BoidBody, Arc> perceptions = new HashMap<BoidBody, Arc>();
  
  /**
   * Pane principal
   */
  @FXML
  private Pane main_pane;
  
  /**
   * Groupe contenant les boids
   */
  @FXML
  private Group boids_group;
  
  /**
   * Groupe contenant les obstacles
   */
  @FXML
  private Group obstacle_group;
  
  /**
   * Groupe contenant les champs de perceptions des boids
   */
  @FXML
  private Group perception_group;
  
  /**
   * Bouton lançant la simulation
   */
  @FXML
  private Button start_button;
  
  /**
   * Bouton pause
   */
  @FXML
  private Rectangle pause_button;
  
  /**
   * Bouton reprise
   */
  @FXML
  private Polygon resume_button;
  
  /**
   * Active/Désactive le mode nuit
   */
  @FXML
  private ToggleButton toggle_night_mode;
  
  /**
   * Indicateur du mode
   */
  @FXML
  private Circle night_mode_indicator;
  
  /**
   * Active/Désactive les champs perceptions des boids
   */
  @FXML
  private ToggleButton toggle_perception;
  
  /**
   * Indicateur des champs de percetpion des boids
   */
  @FXML
  private Circle perception_indicator;
  
  /**
   * Pane contenant l'UI
   */
  @FXML
  private Pane UI_pane;
  
  /**
   * Intitulé de la quantité de boids
   */
  @FXML
  private Label boids_quantity_label;
  
  /**
   * Intitulé du nombre de population
   */
  @FXML
  private Label boids_population_label;
  
  /**
   * Champ pour le nombre de boids
   */
  @FXML
  private TextField boids_quantity_input;
  
  /**
   * Champ pour le nombre de population
   */
  @FXML
  private TextField boids_population_input;
  
  /**
   * Pane contenant les paramètrages globaux de la simulation
   */
  @FXML
  private Pane simulation_parameters_pane;
  
  /**
   * Intitulé de la fenêtre des paramètres de la simulation
   */
  @FXML
  private Label settings_label;
  
  /**
   * Redirection vers la fenêtre des paramètres avancés
   */
  @FXML
  private Label see_advanced_settings_label;
  
  /**
   * Ligne sous le nombre de population
   */
  @FXML
  private Line boids_population_line;
  
  /**
   * Bouton + pour le nombre de populations
   */
  @FXML
  private Label increment_boids_population;
  
  /**
   * Bouton - pour le nombre de populations
   */
  @FXML
  private Label decrement_boids_population;
  
  /**
   * Cercle entourant le bouton + du nombre de populations
   */
  @FXML
  private Circle boids_population_increment_circle;
  
  /**
   * Cercle entourant le bouton - du nombre de populations
   */
  @FXML
  private Circle boids_population_decrement_circle;
  
  /**
   * Ligne sous le nombre de boids
   */
  @FXML
  private Line boids_quantity_line;
  
  /**
   * Bouton + pour le nombre de boids
   */
  @FXML
  private Label increment_boids_quantity;
  
  /**
   * Bouton - pour le nombre de populations
   */
  @FXML
  private Label decrement_boids_quantity;
  
  /**
   * Cercle entourant le bouton + du nombre de boids
   */
  @FXML
  private Circle boids_quantity_increment_circle;
  
  /**
   * Cercle entourant le bouton - du nombre de boids
   */
  @FXML
  private Circle boids_quantity_decrement_circle;
  
  /**
   * ScrollPane contenant le paramétrage de la map
   */
  @FXML
  private ScrollPane map_selection_pane;
  
  /**
   * Intitulé de la fenêtre de paramétrage de la map
   */
  @FXML
  private Label map_selection_label;
  
  /**
   * Image de preview de la map 1
   */
  @FXML
  private ImageView preview_map_1;
  
  /**
   * Image de preview de la map 2
   */
  @FXML
  private ImageView preview_map_2;
  
  /**
   * Image de preview de la map 3
   */
  @FXML
  private ImageView preview_map_3;
  
  /**
   * Image de preview de la map 4
   */
  @FXML
  private ImageView preview_map_4;
  
  /**
   * Encadre l'image de preview de la map 1
   */
  @FXML
  private Rectangle preview_map_1_border;
  
  /**
   * Encadre l'image de la preview de la map 2
   */
  @FXML
  private Rectangle preview_map_2_border;
  
  /**
   * Encadre l'image de la preview de la map 3
   */
  @FXML
  private Rectangle preview_map_3_border;
  
  /**
   * Encadre l'image de la preview de la map 4
   */
  @FXML
  private Rectangle preview_map_4_border;
  
  /**
   * Intitulé de la map 1
   */
  @FXML
  private Label map_label_1;
  
  /**
   * Intitulé de la map 2
   */
  @FXML
  private Label map_label_2;
  
  /**
   * Intitulé de la map 3
   */
  @FXML
  private Label map_label_3;
  
  /**
   * Intitulé de la map 4
   */
  @FXML
  private Label map_label_4;
  
  /**
   * Image de sélection de la map 1
   */
  @FXML
  private ImageView tick_map_1;
  
  /**
   * Image de sélection de la map 2
   */
  @FXML
  private ImageView tick_map_2;
  
  /**
   * Image de sélection de la map 3
   */
  @FXML
  private ImageView tick_map_3;
  
  /**
   * Image de sélection de la map 4
   */
  @FXML
  private ImageView tick_map_4;
  
  /**
   * ScrollPane contenant les paramètres avancés
   */
  @FXML
  private ScrollPane advanced_settings_pane;
  
  /**
   * Anchor du ScrollPane des paramètres avancés
   */
  @FXML
  private AnchorPane advanced_settings_anchor_pane;
  
  /**
   * Pane relatif au groupe 1
   */
  @FXML
  private Pane pane_group_1;
  
  /**
   * Pane relatif au groupe 2
   */
  @FXML
  private Pane pane_group_2;
  
  /**
   * Pane relatif au groupe 3
   */
  @FXML
  private Pane pane_group_3;
  
  /**
   * Pane relatif au groupe 4
   */
  @FXML
  private Pane pane_group_4;
  
  /**
   * Pane relatif au groupe 5
   */
  @FXML
  private Pane pane_group_5;
  
  /**
   * Pane relatif au groupe 6
   */
  @FXML
  private Pane pane_group_6;
  
  /**
   * Pane relatif au groupe 7
   */
  @FXML
  private Pane pane_group_7;
  
  /**
   * Pane relatif au groupe 8
   */
  @FXML
  private Pane pane_group_8;
  
  /**
   * Intitulé de la fenêtre des paramètres avancés
   */
  @FXML
  private Label advanced_settings_label;
  
  /**
   * Bouton de réinitalisation des valeurs
   */
  @FXML
  private Label reset_group_values;
  
  /**
   * Intitulé pour le groupe 1
   */
  @FXML
  private Label group_label_1;
  
  /**
   * Intitulé pour le groupe 2
   */
  @FXML
  private Label group_label_2;
  
  /**
   * Intitulé pour le groupe 3
   */
  @FXML
  private Label group_label_3;
  
  /**
   * Intitulé pour le groupe 4
   */
  @FXML
  private Label group_label_4;
  
  /**
   * Initutlé pour le groupe 5
   */
  @FXML
  private Label group_label_5;
  
  /**
   * Intitulé pour le groupe 6
   */
  @FXML
  private Label group_label_6;
  
  /**
   * Intitulé pour le groupe 7
   */
  @FXML
  private Label group_label_7;
  
  /**
   * Intitulé pour le groupe 8
   */
  @FXML
  private Label group_label_8;
  
  /**
   * Intitulé pour la masse du groupe 1
   */
  @FXML
  private Label mass_label_1;
  
  /**
   * Scrollbar pour la masse du groupe 1
   */
  @FXML
  private ScrollBar mass_1;
  
  /**
   * masse minimale pour le groupe 1
   */
  @FXML
  private Label mass_min_1;
  
  /**
   * Masse maximale pour le groupe 1
   */
  @FXML
  private Label mass_max_1;
  
  /**
   * Affichage de la masse pour le groupe 1
   */
  @FXML
  private Label mass_display_1;
  
  /**
   * Intitulé pour la masse du groupe 2
   */
  @FXML
  private Label mass_label_2;
  
  /**
   * Scrollbar pour la masse du groupe 2
   */
  @FXML
  private ScrollBar mass_2;
  
  /**
   * Masse minimale pour le groupe 2
   */
  @FXML
  private Label mass_min_2;
  
  /**
   * Masse maximale pour le groupe 2
   */
  @FXML
  private Label mass_max_2;
  
  /**
   * Affichage de la masse pour le groupe 2
   */
  @FXML
  private Label mass_display_2;
  
  /**
   * Intitulé pour la masse du groupe 3
   */
  @FXML
  private Label mass_label_3;
  
  /**
   * Scrollbar pour la masse du groupe 3
   */
  @FXML
  private ScrollBar mass_3;
  
  /**
   * Masse minimale pour le groupe 3
   */
  @FXML
  private Label mass_min_3;
  
  /**
   * Masse maximale pour le groupe 3
   */
  @FXML
  private Label mass_max_3;
  
  /**
   * Affichage de la masse pour le groupe 3
   */
  @FXML
  private Label mass_display_3;
  
  /**
   * Intitulé pour la masse du groupe 4
   */
  @FXML
  private Label mass_label_4;
  
  /**
   * Scrollbar pour la masse du groupe 4
   */
  @FXML
  private ScrollBar mass_4;
  
  /**
   * Masse minimale pour le groupe 4
   */
  @FXML
  private Label mass_min_4;
  
  /**
   * Masse maximale pour le groupe 4
   */
  @FXML
  private Label mass_max_4;
  
  /**
   * Affichage de la masse pour le groupe 4
   */
  @FXML
  private Label mass_display_4;
  
  /**
   * Intitulé pour la masse du groupe 5
   */
  @FXML
  private Label mass_label_5;
  
  /**
   * Scrollbar pour la masse du groupe 5
   */
  @FXML
  private ScrollBar mass_5;
  
  /**
   * Masse minimale pour le groupe 5
   */
  @FXML
  private Label mass_min_5;
  
  /**
   * Masse maximale pour le groupe 5
   */
  @FXML
  private Label mass_max_5;
  
  /**
   * Affichage de la masse pour le groupe 5
   */
  @FXML
  private Label mass_display_5;
  
  /**
   * Initutlé pour la masse du groupe 6
   */
  @FXML
  private Label mass_label_6;
  
  /**
   * Scrollbar pour la masse du groupe 6
   */
  @FXML
  private ScrollBar mass_6;
  
  /**
   * Masse minimale pour le groupe 6
   */
  @FXML
  private Label mass_min_6;
  
  /**
   * Masse maximale pour le groupe 6
   */
  @FXML
  private Label mass_max_6;
  
  /**
   * Affichage de la masse pour le groupe 6
   */
  @FXML
  private Label mass_display_6;
  
  /**
   * Intitulé pour la masse du groupe 7
   */
  @FXML
  private Label mass_label_7;
  
  /**
   * Scrollbar pour la masse du groupe 7
   */
  @FXML
  private ScrollBar mass_7;
  
  /**
   * Masse minimale pour le groupe 7
   */
  @FXML
  private Label mass_min_7;
  
  /**
   * Masse maximale pour le groupe 7
   */
  @FXML
  private Label mass_max_7;
  
  /**
   * Affichage de la masse pour le groupe 7
   */
  @FXML
  private Label mass_display_7;
  
  /**
   * Intitulé pour la masse du grouep 8
   */
  @FXML
  private Label mass_label_8;
  
  /**
   * Scrollbar pour la masse du groupe 8
   */
  @FXML
  private ScrollBar mass_8;
  
  /**
   * Masse minimale pour le groupe 8
   */
  @FXML
  private Label mass_min_8;
  
  /**
   * Masse maximale pour le groupe 8
   */
  @FXML
  private Label mass_max_8;
  
  /**
   * Affichage de la masse pour le groupe 8
   */
  @FXML
  private Label mass_display_8;
  
  /**
   * Intitulé pour la vitesse du groupe 1
   */
  @FXML
  private Label speed_label_1;
  
  /**
   * Scrollbar pour la vitesse du groupe 1
   */
  @FXML
  private ScrollBar speed_1;
  
  /**
   * Vitesse minimale pour le groupe 1
   */
  @FXML
  private Label speed_min_1;
  
  /**
   * Vitesse maximale pour le groupe 1
   */
  @FXML
  private Label speed_max_1;
  
  /**
   * Affichage de la vitesse pour le groupe 1
   */
  @FXML
  private Label speed_display_1;
  
  /**
   * Intitulé pour la vitesse du groupe 2
   */
  @FXML
  private Label speed_label_2;
  
  /**
   * Scrollbar pour la vitesse du groupe 2
   */
  @FXML
  private ScrollBar speed_2;
  
  /**
   * Vitesse minimale pour le groupe 2
   */
  @FXML
  private Label speed_min_2;
  
  /**
   * Vitesse maximale pour le groupe 2
   */
  @FXML
  private Label speed_max_2;
  
  /**
   * Affichage de la vitesse pour le groupe 2
   */
  @FXML
  private Label speed_display_2;
  
  /**
   * Intitulé pour la vitesse du groupe 3
   */
  @FXML
  private Label speed_label_3;
  
  /**
   * Scrollbar pour la vitesse du groupe 3
   */
  @FXML
  private ScrollBar speed_3;
  
  /**
   * Vitesse minimale pour le groupe 3
   */
  @FXML
  private Label speed_min_3;
  
  /**
   * Vitesse maximale pour le groupe 3
   */
  @FXML
  private Label speed_max_3;
  
  /**
   * Affichage de la vitesse pour le groupe 3
   */
  @FXML
  private Label speed_display_3;
  
  /**
   * Intitulé pour la vitesse du groupe 4
   */
  @FXML
  private Label speed_label_4;
  
  /**
   * Scrollbar pour la vitesse du groupe 4
   */
  @FXML
  private ScrollBar speed_4;
  
  /**
   * Vitesse minimale pour le groupe 4
   */
  @FXML
  private Label speed_min_4;
  
  /**
   * Vitesse maximale pour le groupe 4
   */
  @FXML
  private Label speed_max_4;
  
  /**
   * Affichage de la vitesse pour le groupe 4
   */
  @FXML
  private Label speed_display_4;
  
  /**
   * Intitulé pour la vitesse du groupe 5
   */
  @FXML
  private Label speed_label_5;
  
  /**
   * Scrollbar pour la vitesse du groupe 5
   */
  @FXML
  private ScrollBar speed_5;
  
  /**
   * Vitesse minimale pour le groupe 5
   */
  @FXML
  private Label speed_min_5;
  
  /**
   * Vitesse maximale pour le groupe 5
   */
  @FXML
  private Label speed_max_5;
  
  /**
   * Affichage de la vitesse pour le groupe 5
   */
  @FXML
  private Label speed_display_5;
  
  /**
   * Initutlé pour la vitesse du groupe 6
   */
  @FXML
  private Label speed_label_6;
  
  /**
   * Scrollbar pour la vitesse du groupe 6
   */
  @FXML
  private ScrollBar speed_6;
  
  /**
   * Vitesse minimale pour le groupe 6
   */
  @FXML
  private Label speed_min_6;
  
  /**
   * Vitesse maximale pour le groupe 6
   */
  @FXML
  private Label speed_max_6;
  
  /**
   * Affichage de la vitesse pour le groupe 6
   */
  @FXML
  private Label speed_display_6;
  
  /**
   * Intitulé pour la vitesse du groupe 7
   */
  @FXML
  private Label speed_label_7;
  
  /**
   * Scrollbar pour la vitesse du groupe 7
   */
  @FXML
  private ScrollBar speed_7;
  
  /**
   * Vitesse minimale pour le groupe 7
   */
  @FXML
  private Label speed_min_7;
  
  /**
   * Vitesse maximale pour le groupe 7
   */
  @FXML
  private Label speed_max_7;
  
  /**
   * Affichage de la vitesse pour le groupe 7
   */
  @FXML
  private Label speed_display_7;
  
  /**
   * Intitulé pour la vitesse du grouep 8
   */
  @FXML
  private Label speed_label_8;
  
  /**
   * Scrollbar pour la vitesse du groupe 8
   */
  @FXML
  private ScrollBar speed_8;
  
  /**
   * Vitesse minimale pour le groupe 8
   */
  @FXML
  private Label speed_min_8;
  
  /**
   * Vitesse maximale pour le groupe 8
   */
  @FXML
  private Label speed_max_8;
  
  /**
   * Affichage de la vitesse pour le groupe 8
   */
  @FXML
  private Label speed_display_8;
  
  /**
   * Intitulé pour l'angle du groupe 1
   */
  @FXML
  private Label angle_label_1;
  
  /**
   * Scrollbar pour l'angle du groupe 1
   */
  @FXML
  private ScrollBar angle_1;
  
  /**
   * Angle minimal pour le groupe 1
   */
  @FXML
  private Label angle_min_1;
  
  /**
   * Angle maximal pour le groupe 1
   */
  @FXML
  private Label angle_max_1;
  
  /**
   * Affichage de l'angle pour le groupe 1
   */
  @FXML
  private Label angle_display_1;
  
  /**
   * Intitulé pour l'angle du groupe 2
   */
  @FXML
  private Label angle_label_2;
  
  /**
   * Scrollbar pour l'angle du groupe 2
   */
  @FXML
  private ScrollBar angle_2;
  
  /**
   * Angle minimal pour le groupe 2
   */
  @FXML
  private Label angle_min_2;
  
  /**
   * Angle maximal pour le groupe 2
   */
  @FXML
  private Label angle_max_2;
  
  /**
   * Affichage de l'angle pour le groupe 2
   */
  @FXML
  private Label angle_display_2;
  
  /**
   * Intitulé pour l'angle du groupe 3
   */
  @FXML
  private Label angle_label_3;
  
  /**
   * Scrollbar pour l'angle du groupe 3
   */
  @FXML
  private ScrollBar angle_3;
  
  /**
   * Angle minimal pour le groupe 3
   */
  @FXML
  private Label angle_min_3;
  
  /**
   * Angle maximal pour le groupe 3
   */
  @FXML
  private Label angle_max_3;
  
  /**
   * Affichage de l'angle pour le groupe 3
   */
  @FXML
  private Label angle_display_3;
  
  /**
   * Intitulé pour l'angle du groupe 4
   */
  @FXML
  private Label angle_label_4;
  
  /**
   * Scrollbar pour l'angle du groupe 4
   */
  @FXML
  private ScrollBar angle_4;
  
  /**
   * Angle minimal pour le groupe 4
   */
  @FXML
  private Label angle_min_4;
  
  /**
   * Angle maximal pour le groupe 4
   */
  @FXML
  private Label angle_max_4;
  
  /**
   * Affichage de l'angle pour le groupe 4
   */
  @FXML
  private Label angle_display_4;
  
  /**
   * Intitulé pour l'angle du groupe 5
   */
  @FXML
  private Label angle_label_5;
  
  /**
   * Scrollbar pour l'angle du groupe 5
   */
  @FXML
  private ScrollBar angle_5;
  
  /**
   * Angle minimal pour le groupe 5
   */
  @FXML
  private Label angle_min_5;
  
  /**
   * Angle maximal pour le groupe 5
   */
  @FXML
  private Label angle_max_5;
  
  /**
   * Affichage de l'angle pour le groupe 5
   */
  @FXML
  private Label angle_display_5;
  
  /**
   * Initutlé pour l'angle du groupe 6
   */
  @FXML
  private Label angle_label_6;
  
  /**
   * Scrollbar pour l'angle du groupe 6
   */
  @FXML
  private ScrollBar angle_6;
  
  /**
   * Angle minimal pour le groupe 6
   */
  @FXML
  private Label angle_min_6;
  
  /**
   * Angle maximal pour le groupe 6
   */
  @FXML
  private Label angle_max_6;
  
  /**
   * Affichage de l'angle pour le groupe 6
   */
  @FXML
  private Label angle_display_6;
  
  /**
   * Intitulé pour l'angle du groupe 7
   */
  @FXML
  private Label angle_label_7;
  
  /**
   * Scrollbar pour l'angle du groupe 7
   */
  @FXML
  private ScrollBar angle_7;
  
  /**
   * Angle minimal pour le groupe 7
   */
  @FXML
  private Label angle_min_7;
  
  /**
   * Angle maximal pour le groupe 7
   */
  @FXML
  private Label angle_max_7;
  
  /**
   * Affichage de l'angle pour le groupe 7
   */
  @FXML
  private Label angle_display_7;
  
  /**
   * Intitulé pour l'angle du grouep 8
   */
  @FXML
  private Label angle_label_8;
  
  /**
   * Scrollbar pour l'angle du groupe 8
   */
  @FXML
  private ScrollBar angle_8;
  
  /**
   * Angle minimal pour le groupe 8
   */
  @FXML
  private Label angle_min_8;
  
  /**
   * Angle maximal pour le groupe 8
   */
  @FXML
  private Label angle_max_8;
  
  /**
   * Affichage de l'angle pour le groupe 8
   */
  @FXML
  private Label angle_display_8;
  
  /**
   * Intitulé pour la distance du groupe 1
   */
  @FXML
  private Label distance_label_1;
  
  /**
   * Scrollbar pour la distance du groupe 1
   */
  @FXML
  private ScrollBar distance_1;
  
  /**
   * Distance minimale pour le groupe 1
   */
  @FXML
  private Label distance_min_1;
  
  /**
   * Distance maximale pour le groupe 1
   */
  @FXML
  private Label distance_max_1;
  
  /**
   * Affichage de la distance pour le groupe 1
   */
  @FXML
  private Label distance_display_1;
  
  /**
   * Intitulé pour la distance du groupe 2
   */
  @FXML
  private Label distance_label_2;
  
  /**
   * Scrollbar pour la distance du groupe 2
   */
  @FXML
  private ScrollBar distance_2;
  
  /**
   * Distance minimale pour le groupe 2
   */
  @FXML
  private Label distance_min_2;
  
  /**
   * Distance maximale pour le groupe 2
   */
  @FXML
  private Label distance_max_2;
  
  /**
   * Affichage de la distance pour le groupe 2
   */
  @FXML
  private Label distance_display_2;
  
  /**
   * Intitulé pour la distance du groupe 3
   */
  @FXML
  private Label distance_label_3;
  
  /**
   * Scrollbar pour la distance du groupe 3
   */
  @FXML
  private ScrollBar distance_3;
  
  /**
   * Distance minimale pour le groupe 3
   */
  @FXML
  private Label distance_min_3;
  
  /**
   * Distance maximale pour le groupe 3
   */
  @FXML
  private Label distance_max_3;
  
  /**
   * Affichage de la distance pour le groupe 3
   */
  @FXML
  private Label distance_display_3;
  
  /**
   * Intitulé pour la distance du groupe 4
   */
  @FXML
  private Label distance_label_4;
  
  /**
   * Scrollbar pour la distance du groupe 4
   */
  @FXML
  private ScrollBar distance_4;
  
  /**
   * Distance minimale pour le groupe 4
   */
  @FXML
  private Label distance_min_4;
  
  /**
   * Distance maximale pour le groupe 4
   */
  @FXML
  private Label distance_max_4;
  
  /**
   * Affichage de la distance pour le groupe 4
   */
  @FXML
  private Label distance_display_4;
  
  /**
   * Intitulé pour la distance du groupe 5
   */
  @FXML
  private Label distance_label_5;
  
  /**
   * Scrollbar pour la distance du groupe 5
   */
  @FXML
  private ScrollBar distance_5;
  
  /**
   * Distance minimale pour le groupe 5
   */
  @FXML
  private Label distance_min_5;
  
  /**
   * Distance maximale pour le groupe 5
   */
  @FXML
  private Label distance_max_5;
  
  /**
   * Affichage de la distance pour le groupe 5
   */
  @FXML
  private Label distance_display_5;
  
  /**
   * Initutlé pour la distance du groupe 6
   */
  @FXML
  private Label distance_label_6;
  
  /**
   * Scrollbar pour la distance du groupe 6
   */
  @FXML
  private ScrollBar distance_6;
  
  /**
   * Distance minimale pour le groupe 6
   */
  @FXML
  private Label distance_min_6;
  
  /**
   * Distance maximale pour le groupe 6
   */
  @FXML
  private Label distance_max_6;
  
  /**
   * Affichage de la distance pour le groupe 6
   */
  @FXML
  private Label distance_display_6;
  
  /**
   * Intitulé pour la distance du groupe 7
   */
  @FXML
  private Label distance_label_7;
  
  /**
   * Scrollbar pour la distance du groupe 7
   */
  @FXML
  private ScrollBar distance_7;
  
  /**
   * Distance minimale pour le groupe 7
   */
  @FXML
  private Label distance_min_7;
  
  /**
   * Distance maximale pour le groupe 7
   */
  @FXML
  private Label distance_max_7;
  
  /**
   * Affichage de la distance pour le groupe 7
   */
  @FXML
  private Label distance_display_7;
  
  /**
   * Intitulé pour la distance du grouep 8
   */
  @FXML
  private Label distance_label_8;
  
  /**
   * Scrollbar pour la distance du groupe 8
   */
  @FXML
  private ScrollBar distance_8;
  
  /**
   * Distance minimale pour le groupe 8
   */
  @FXML
  private Label distance_min_8;
  
  /**
   * Distance maximale pour le groupe 8
   */
  @FXML
  private Label distance_max_8;
  
  /**
   * Affichage de la distance pour le groupe 8
   */
  @FXML
  private Label distance_display_8;
  
  /**
   * Fenêtre de visualisation des infos relatives aux boids
   */
  @FXML
  private Pane boids_infos_pane;
  
  /**
   * Indicateur du groupe du Boid
   */
  @FXML
  private Label boid_group;
  
  /**
   * Indicateur de la vitesse actuelle du boid
   */
  @FXML
  private Label boid_vitesse;
  
  /**
   * Indicateur de la nouvelle vitesse du boid
   */
  @FXML
  private Label boid_new_vitesse;
  
  /**
   * Indicateur de la vitesse maximale du groupe du boid
   */
  @FXML
  private Label boid_group_vitesse;
  
  /**
   * Indicateur de l'angle de vision du boid
   */
  @FXML
  private Label boid_angle;
  
  /**
   * Indicateur de la distancce de vision du boid
   */
  @FXML
  private Label boid_distance;
  
  /**
   * Indicateur de la masse du boid
   */
  @FXML
  private Label boid_masse;
  
  /**
   * Indicateur de la position du boid
   */
  @FXML
  private Label boid_position;
  
  /**
   * Bouton permettant de masquer la fenêtre de visualisation des informations du boid
   */
  @FXML
  private Button hide_infos;
  
  /**
   * Retourne le nombre de boids
   */
  @Pure
  public int getBoidsQuantity() {
    return Integer.parseInt(this.boids_quantity_input.getText());
  }
  
  /**
   * Retourne la map sélectionnée
   */
  @Pure
  public int getMapSelection() {
    return this.selectedMap;
  }
  
  /**
   * Retourne le nombre de populations
   */
  @Pure
  public int getBoidsPopulation() {
    return Integer.parseInt(this.boids_population_input.getText());
  }
  
  /**
   * Retourne la largeur de la map
   */
  @Pure
  public int getMapWidth() {
    double _width = this.main_pane.getWidth();
    return ((int) _width);
  }
  
  /**
   * Retourne la hauteur de la map
   */
  @Pure
  public int getMapHeight() {
    double _height = this.main_pane.getHeight();
    return ((int) _height);
  }
  
  /**
   * Retourne la liste des obstacles
   */
  @Pure
  public List<Obstacle> getObstacles() {
    return this.obstacles;
  }
  
  /**
   * Retourne la liste des infos relatives aux boids
   */
  @Pure
  public List<BoidGroupInfos> getBoidsSettings() {
    return this.boidsSettings;
  }
  
  /**
   * Démarre la simulation et transmets les paramètres utilisateur au BootAgent
   */
  @FXML
  protected void startSimu() {
    if (((((!Objects.equal(this.boids_population_input.getText(), "")) && (this.outputQuality(this.boids_population_input.getText())).booleanValue()) && (!Objects.equal(this.boids_quantity_input.getText(), ""))) && (this.outputQuality(this.boids_quantity_input.getText())).booleanValue())) {
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
  
  /**
   * Construit la map
   */
  public List<Obstacle> buildMap(final int map) {
    abstract class __BoidsFxViewerController_0 implements Runnable {
      public abstract void run();
    }
    
    abstract class __BoidsFxViewerController_1 implements Runnable {
      public abstract void run();
    }
    
    this.pause_button.setVisible(true);
    if ((map == 1)) {
      ArrayList<Polygon> _arrayList = new ArrayList<Polygon>();
      this.polygons = _arrayList;
      return new ArrayList<Obstacle>();
    } else {
      if ((map == 2)) {
        ArrayList<Polygon> _arrayList_1 = new ArrayList<Polygon>();
        this.polygons = _arrayList_1;
        ArrayList<List<Coordinates>> _arrayList_2 = new ArrayList<List<Coordinates>>();
        this.polygonsCoordinates = _arrayList_2;
        ArrayList<Obstacle> _arrayList_3 = new ArrayList<Obstacle>();
        this.obstacles = _arrayList_3;
        Polygon _polygon = new Polygon(250.0, 200.0, 365.0, 250.0, 400.0, 300.0, 325.0, 400.0, 205.0, 225.0);
        this.polygons.add(_polygon);
        Polygon _polygon_1 = new Polygon(605.0, 80.0, 675.0, 65.0, 680.0, 125.0, 650.0, 220.0, 630.0, 250.0, 660.0, 130.0, 
          665.0, 75.0, 615.0, 95.0, 560.0, 240.0, 560.0, 205.0);
        this.polygons.add(_polygon_1);
        Polygon _polygon_2 = new Polygon(450.0, 450.0, 575.0, 500.0, 575.0, 420.0, 700.0, 500.0, 590.0, 450.0, 590.0, 520.0);
        this.polygons.add(_polygon_2);
        final Consumer<Polygon> _function = (Polygon p) -> {
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
      } else {
        if ((map == 3)) {
          ArrayList<Polygon> _arrayList_4 = new ArrayList<Polygon>();
          this.polygons = _arrayList_4;
          ArrayList<List<Coordinates>> _arrayList_5 = new ArrayList<List<Coordinates>>();
          this.polygonsCoordinates = _arrayList_5;
          ArrayList<Obstacle> _arrayList_6 = new ArrayList<Obstacle>();
          this.obstacles = _arrayList_6;
          Polygon _polygon_3 = new Polygon(315.0, 720.0, 315.0, 250.0, 325.0, 250.0, 325.0, 720.0);
          this.polygons.add(_polygon_3);
          Polygon _polygon_4 = new Polygon(635.0, 470.0, 635.0, 0.0, 645.0, 0.0, 645.0, 470.0);
          this.polygons.add(_polygon_4);
          Polygon _polygon_5 = new Polygon(955.0, 720.0, 955.0, 250.0, 965.0, 250.0, 965.0, 720.0);
          this.polygons.add(_polygon_5);
          final Consumer<Polygon> _function_1 = (Polygon p) -> {
            p.setFill(Color.GRAY);
            p.setStroke(Color.TRANSPARENT);
            p.setStrokeWidth(20);
          };
          this.polygons.forEach(_function_1);
          __BoidsFxViewerController_1 command_1 = new __BoidsFxViewerController_1() {
            @Override
            public void run() {
              final Consumer<Polygon> _function = (Polygon p) -> {
                BoidsFxViewerController.this.obstacle_group.getChildren().add(0, p);
              };
              BoidsFxViewerController.this.polygons.forEach(_function);
            }
          };
          boolean _isFxApplicationThread_1 = Platform.isFxApplicationThread();
          if (_isFxApplicationThread_1) {
            command_1.run();
            this.generateObstacles();
            return this.obstacles;
          } else {
            Platform.runLater(command_1);
            this.generateObstacles();
            return this.obstacles;
          }
        }
      }
    }
    return null;
  }
  
  /**
   * Génère les obstacles
   */
  public void generateObstacles() {
    for (final Polygon p : this.polygons) {
      Obstacle _obstacle = new Obstacle(p);
      this.obstacles.add(_obstacle);
    }
  }
  
  /**
   * Update l'affichage à chaque cycle des boids
   */
  public void updateGraphics(final Collection<BoidBody> list) {
    abstract class __BoidsFxViewerController_2 implements Runnable {
      public abstract void run();
    }
    
    __BoidsFxViewerController_2 command = new __BoidsFxViewerController_2() {
      @Override
      public void run() {
        abstract class ____BoidsFxViewerController_2_0_1 implements EventHandler<MouseEvent> {
          public abstract void handle(final MouseEvent event);
        }
        
        abstract class ____BoidsFxViewerController_2_1_2 implements EventHandler<MouseEvent> {
          public abstract void handle(final MouseEvent event);
        }
        
        abstract class ____BoidsFxViewerController_2_2_3 implements EventHandler<MouseEvent> {
          public abstract void handle(final MouseEvent event);
        }
        
        BoidsFxViewerController.this.perception_group.getChildren().clear();
        BoidsFxViewerController.this.boids_group.getChildren().clear();
        BoidsFxViewerController.this.perceptions.clear();
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
            double angleRotation = 0;
            double _y_3 = boid.getVitesse().getY();
            boolean _notEquals = (_y_3 != 0);
            if (_notEquals) {
              double _x_3 = boid.getVitesse().getX();
              double _y_4 = boid.getVitesse().getY();
              double _minus_2 = (-_y_4);
              double _divide = (_x_3 / _minus_2);
              angleRotation = Math.toDegrees(Math.atan(_divide));
              if ((angleRotation < 0)) {
                double _angleRotation = angleRotation;
                angleRotation = (_angleRotation + 180);
              }
              double _x_4 = boid.getVitesse().getX();
              boolean _lessEqualsThan = (_x_4 <= 0);
              if (_lessEqualsThan) {
                double _y_5 = boid.getVitesse().getY();
                boolean _lessThan = (_y_5 < 0);
                if (_lessThan) {
                  double _angleRotation_1 = angleRotation;
                  angleRotation = (_angleRotation_1 + 180);
                } else {
                  double _angleRotation_2 = angleRotation;
                  angleRotation = (_angleRotation_2 - 180);
                }
              }
            } else {
              angleRotation = 0;
            }
            boidElement.setRotate(angleRotation);
            boidElement.setFill(Configuration.COLOR_FAMILY.get(Integer.valueOf(boid.getGroupe())));
            BoidsFxViewerController.this.perceptionManagement(boid, angleRotation, boidElement, boid.getPosition().getX(), boid.getPosition().getY(), Boolean.valueOf(false));
            ____BoidsFxViewerController_2_0_1 _____BoidsFxViewerController_2_0_1 = new ____BoidsFxViewerController_2_0_1() {
              public void handle(final MouseEvent event) {
                if ((BoidsFxViewerController.this.currentPolygon != null)) {
                  BoidsFxViewerController.this.currentPolygon.setStrokeWidth(0);
                }
                BoidsFxViewerController.this.currentBoid = boid.getID();
                Object _source = event.getSource();
                BoidsFxViewerController.this.currentPolygon = ((Polygon) _source);
                BoidsFxViewerController.this.updateInfos(boid);
                BoidsFxViewerController.this.showInfosVisibility();
                Object _source_1 = event.getSource();
                ((Polygon) _source_1).setStrokeWidth(3);
                if ((BoidsFxViewerController.this.nightMode).booleanValue()) {
                  Object _source_2 = event.getSource();
                  ((Polygon) _source_2).setStroke(Color.rgb(255, 255, 255, 0.5));
                } else {
                  Object _source_3 = event.getSource();
                  ((Polygon) _source_3).setStroke(Color.rgb(0, 0, 0, 0.5));
                }
                BoidsFxViewerController.this.orgSceneX = event.getSceneX();
                BoidsFxViewerController.this.orgSceneY = event.getSceneY();
                Object _source_4 = event.getSource();
                BoidsFxViewerController.this.orgTranslateX = ((Polygon) _source_4).getTranslateX();
                Object _source_5 = event.getSource();
                BoidsFxViewerController.this.orgTranslateY = ((Polygon) _source_5).getTranslateY();
                BoidsFxViewerController.this.savedPosition.clear();
                Object _source_6 = event.getSource();
                final Consumer<Double> _function = (Double point) -> {
                  BoidsFxViewerController.this.savedPosition.add(Double.valueOf(point));
                };
                ((Polygon) _source_6).getPoints().forEach(_function);
                BoidsFxViewerController.this.perception_group.getChildren().remove(BoidsFxViewerController.this.perceptions.get(boid));
                BoidsFxViewerController.this.perceptions.remove(boid);
              }
            };
            boidElement.setOnMousePressed(_____BoidsFxViewerController_2_0_1);
            ____BoidsFxViewerController_2_1_2 _____BoidsFxViewerController_2_1_2 = new ____BoidsFxViewerController_2_1_2() {
              @Override
              public void handle(final MouseEvent event) {
                double _sceneX = event.getSceneX();
                double offsetX = (_sceneX - BoidsFxViewerController.this.orgSceneX);
                double _sceneY = event.getSceneY();
                double offsetY = (_sceneY - BoidsFxViewerController.this.orgSceneY);
                double newTranslateX = (BoidsFxViewerController.this.orgTranslateX + offsetX);
                double newTranslateY = (BoidsFxViewerController.this.orgTranslateY + offsetY);
                Object _source = event.getSource();
                ((Polygon) _source).setTranslateX(newTranslateX);
                Object _source_1 = event.getSource();
                ((Polygon) _source_1).setTranslateY(newTranslateY);
              }
            };
            boidElement.setOnMouseDragged(_____BoidsFxViewerController_2_1_2);
            ____BoidsFxViewerController_2_2_3 _____BoidsFxViewerController_2_2_3 = new ____BoidsFxViewerController_2_2_3() {
              @Override
              public void handle(final MouseEvent event) {
                Object _source = event.getSource();
                Double _get = ((Polygon) _source).getPoints().get(0);
                Object _source_1 = event.getSource();
                Double _get_1 = ((Polygon) _source_1).getPoints().get(2);
                double _plus = DoubleExtensions.operator_plus(_get, _get_1);
                Object _source_2 = event.getSource();
                Double _get_2 = ((Polygon) _source_2).getPoints().get(4);
                double _plus_1 = (_plus + (_get_2).doubleValue());
                final double calculX = (_plus_1 / 3);
                Object _source_3 = event.getSource();
                Double _get_3 = ((Polygon) _source_3).getPoints().get(1);
                Object _source_4 = event.getSource();
                Double _get_4 = ((Polygon) _source_4).getPoints().get(3);
                double _plus_2 = DoubleExtensions.operator_plus(_get_3, _get_4);
                Object _source_5 = event.getSource();
                Double _get_5 = ((Polygon) _source_5).getPoints().get(5);
                double _plus_3 = (_plus_2 + (_get_5).doubleValue());
                final double calculY = (_plus_3 / 3);
                AtomicBoolean valid = new AtomicBoolean(true);
                final Consumer<Polygon> _function = (Polygon p) -> {
                  Object _source_6 = event.getSource();
                  double _translateX = ((Polygon) _source_6).getTranslateX();
                  double _plus_4 = (calculX + _translateX);
                  Object _source_7 = event.getSource();
                  double _translateY = ((Polygon) _source_7).getTranslateY();
                  double _plus_5 = (calculY + _translateY);
                  boolean _contains = p.contains(_plus_4, _plus_5);
                  if (_contains) {
                    valid.set(false);
                  }
                };
                BoidsFxViewerController.this.polygons.forEach(_function);
                boolean _get_6 = valid.get();
                if (_get_6) {
                  Vector _position = boid.getPosition();
                  Object _source_6 = event.getSource();
                  double _translateX = ((Polygon) _source_6).getTranslateX();
                  double _plus_4 = (calculX + _translateX);
                  _position.setX(_plus_4);
                  Vector _position_1 = boid.getPosition();
                  Object _source_7 = event.getSource();
                  double _translateY = ((Polygon) _source_7).getTranslateY();
                  double _plus_5 = (calculY + _translateY);
                  _position_1.setY(_plus_5);
                  Object _source_8 = event.getSource();
                  double _translateX_1 = ((Polygon) _source_8).getTranslateX();
                  double _plus_6 = (calculX + _translateX_1);
                  Object _source_9 = event.getSource();
                  double _translateY_1 = ((Polygon) _source_9).getTranslateY();
                  double _plus_7 = (calculY + _translateY_1);
                  BoidsFxViewerController.this.changePosition(BoidsFxViewerController.this.currentBoid, _plus_6, _plus_7);
                  BoidsFxViewerController.this.updateInfos(boid);
                } else {
                  Object _source_10 = event.getSource();
                  ((Polygon) _source_10).setTranslateX(0);
                  Object _source_11 = event.getSource();
                  ((Polygon) _source_11).setTranslateY(0);
                }
                Object _source_12 = event.getSource();
                Object _source_13 = event.getSource();
                Object _source_14 = event.getSource();
                double _translateX_2 = ((Polygon) _source_14).getTranslateX();
                double _plus_8 = (calculX + _translateX_2);
                Object _source_15 = event.getSource();
                double _translateY_2 = ((Polygon) _source_15).getTranslateY();
                double _plus_9 = (calculY + _translateY_2);
                BoidsFxViewerController.this.perceptionManagement(boid, ((Polygon) _source_12).getRotate(), ((Polygon) _source_13), _plus_8, _plus_9, Boolean.valueOf(true));
              }
            };
            boidElement.setOnMouseReleased(_____BoidsFxViewerController_2_2_3);
            UUID _iD = boid.getID();
            boolean _equals = Objects.equal(_iD, BoidsFxViewerController.this.currentBoid);
            if (_equals) {
              boidElement.setStrokeWidth(3);
              if ((BoidsFxViewerController.this.nightMode).booleanValue()) {
                boidElement.setStroke(Color.rgb(255, 255, 255, 0.5));
              } else {
                boidElement.setStroke(Color.rgb(0, 0, 0, 0.5));
              }
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
  public Arc perceptionManagement(final BoidBody boid, final double angleRotation, final Polygon boidElement, final double x, final double y, final Boolean erase) {
    Arc _xifexpression = null;
    if ((this.togglePerception).booleanValue()) {
      Arc _xblockexpression = null;
      {
        Arc perceptionArc = new Arc();
        perceptionArc.setCenterX(x);
        if ((!(erase).booleanValue())) {
          perceptionArc.setCenterY(boid.getPosition().getY());
        } else {
          perceptionArc.setCenterY((y - 3));
        }
        int _distanceVisibilite = boid.getDistanceVisibilite();
        perceptionArc.setRadiusX(((double) _distanceVisibilite));
        int _distanceVisibilite_1 = boid.getDistanceVisibilite();
        perceptionArc.setRadiusY(((double) _distanceVisibilite_1));
        int _angleVisibilite = boid.getAngleVisibilite();
        double _minus = ((90 - angleRotation) - _angleVisibilite);
        perceptionArc.setStartAngle(_minus);
        int _angleVisibilite_1 = boid.getAngleVisibilite();
        int _multiply = (_angleVisibilite_1 * 2);
        perceptionArc.setLength(_multiply);
        perceptionArc.setType(ArcType.ROUND);
        if ((this.nightMode).booleanValue()) {
          perceptionArc.setFill(Color.rgb(255, 245, 112, 0.2));
        } else {
          perceptionArc.setFill(Color.rgb(255, 245, 112, 0.8));
        }
        if ((!(erase).booleanValue())) {
          this.boids_group.getChildren().add(0, boidElement);
        }
        this.perception_group.getChildren().add(0, perceptionArc);
        _xblockexpression = this.perceptions.put(boid, perceptionArc);
      }
      _xifexpression = _xblockexpression;
    } else {
      if ((!(erase).booleanValue())) {
        this.boids_group.getChildren().add(0, boidElement);
      }
    }
    return _xifexpression;
  }
  
  @FXML
  public void changePosition(final UUID id, final double x, final double y) {
    PositionModification _positionModification = new PositionModification(id, x, y);
    this.emitToAgents(_positionModification);
  }
  
  /**
   * Pause la simulation
   */
  @FXML
  public void pause() {
    this.pause_button.setVisible(false);
    this.resume_button.setVisible(true);
    Pause _pause = new Pause();
    this.emitToAgents(_pause);
  }
  
  /**
   * Reprend la simulation
   */
  @FXML
  public void resume() {
    this.pause_button.setVisible(true);
    this.resume_button.setVisible(false);
    Resume _resume = new Resume();
    this.emitToAgents(_resume);
  }
  
  /**
   * Active/Désactive l'affichage des champs de perception des boids
   */
  @FXML
  protected void togglePerception() {
    if ((this.togglePerception).booleanValue()) {
      this.togglePerception = Boolean.valueOf(false);
      this.perception_indicator.setFill(Color.TRANSPARENT);
      final BiConsumer<BoidBody, Arc> _function = (BoidBody body, Arc item) -> {
        item.setFill(Color.TRANSPARENT);
      };
      this.perceptions.forEach(_function);
    } else {
      this.togglePerception = Boolean.valueOf(true);
      this.perception_indicator.setFill(Color.rgb(0, 204, 99));
      final BiConsumer<BoidBody, Arc> _function_1 = (BoidBody body, Arc item) -> {
        if ((this.nightMode).booleanValue()) {
          item.setFill(Color.rgb(255, 245, 112, 0.2));
        } else {
          item.setFill(Color.rgb(255, 245, 112, 0.8));
        }
      };
      this.perceptions.forEach(_function_1);
    }
  }
  
  /**
   * Actualise les informations du boids suivi
   * @param boidBody - Informations du boids suivi
   */
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
  
  /**
   * Prise de focus par la main_pane
   */
  @FXML
  protected void changeFocus() {
    this.main_pane.requestFocus();
  }
  
  /**
   * Vérifie la validité de la saisie de l'utilisateur
   * @param ouptput - Saisie de l'utilisateur
   */
  @Pure
  public Boolean outputQuality(final String output) {
    Boolean outputQuality = Boolean.valueOf(false);
    try {
      Integer.parseInt(this.boids_population_input.getText());
      outputQuality = Boolean.valueOf(true);
    } catch (final Throwable _t) {
      if (_t instanceof NumberFormatException) {
        final NumberFormatException e = (NumberFormatException)_t;
      } else {
        throw Exceptions.sneakyThrow(_t);
      }
    }
    return outputQuality;
  }
  
  /**
   * Reset la valeur du champ sélectionné
   * @param e - MouseEvent
   */
  @FXML
  protected void resetField(final MouseEvent e) {
    Object _source = e.getSource();
    ((TextField) _source).setText("");
  }
  
  /**
   * Modifie la valeur du champ en appliquant les limites
   * @param e - KeyEvent
   */
  @FXML
  protected void updateField(final KeyEvent e) {
    Object _source = e.getSource();
    String _text = ((TextField) _source).getText();
    boolean _notEquals = (!Objects.equal(_text, ""));
    if (_notEquals) {
      Object _source_1 = e.getSource();
      Boolean _outputQuality = this.outputQuality(((TextField) _source_1).getText());
      if ((_outputQuality).booleanValue()) {
        Object _source_2 = e.getSource();
        int currentValue = Integer.parseInt(((TextField) _source_2).getText());
        Object _source_3 = e.getSource();
        String _id = ((TextField) _source_3).getId();
        boolean _equals = Objects.equal(_id, "boids_population_input");
        if (_equals) {
          if ((currentValue >= 8)) {
            Object _source_4 = e.getSource();
            ((TextField) _source_4).setText(("" + Integer.valueOf(8)));
          } else {
            if ((currentValue <= 1)) {
              Object _source_5 = e.getSource();
              ((TextField) _source_5).setText(("" + Integer.valueOf(1)));
            }
          }
          this.showGroupConfiguration();
        } else {
          Object _source_6 = e.getSource();
          String _id_1 = ((TextField) _source_6).getId();
          boolean _equals_1 = Objects.equal(_id_1, "boids_quantity_input");
          if (_equals_1) {
            if ((currentValue >= 50)) {
              Object _source_7 = e.getSource();
              ((TextField) _source_7).setText(("" + Integer.valueOf(50)));
            } else {
              if ((currentValue < 1)) {
                Object _source_8 = e.getSource();
                ((TextField) _source_8).setText(("" + Integer.valueOf(1)));
              }
            }
          }
        }
      }
    }
  }
  
  /**
   * Incrémente manuellement le nombre de populations
   */
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
  
  /**
   * Décrémente manuellement le nombre de populations
   */
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
  
  /**
   * Incrémente manuellement le nombre de boids
   */
  @FXML
  protected void incrementBoidsQuantity() {
    if (((!Objects.equal(this.boids_quantity_input.getText(), "")) && (this.outputQuality(this.boids_quantity_input.getText())).booleanValue())) {
      int currentValue = Integer.parseInt(this.boids_quantity_input.getText());
      if ((currentValue >= 50)) {
        this.boids_quantity_input.setText(("" + Integer.valueOf(50)));
      } else {
        this.boids_quantity_input.setText(("" + Integer.valueOf((currentValue + 1))));
      }
    } else {
      this.boids_quantity_input.setText(("" + Integer.valueOf(1)));
    }
  }
  
  /**
   * Décrémente manuellement le nombre de boids
   */
  @FXML
  protected void decrementBoidsQuantity() {
    if (((!Objects.equal(this.boids_quantity_input.getText(), "")) && (this.outputQuality(this.boids_quantity_input.getText())).booleanValue())) {
      int currentValue = Integer.parseInt(this.boids_quantity_input.getText());
      if ((currentValue <= 1)) {
        this.boids_quantity_input.setText(("" + Integer.valueOf(1)));
      } else {
        this.boids_quantity_input.setText(("" + Integer.valueOf((currentValue - 1))));
      }
    } else {
      this.boids_quantity_input.setText(("" + Integer.valueOf(1)));
    }
  }
  
  /**
   * Applique l'effet Glow sur les Labels
   * @param e - MouseEvent
   */
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
  
  /**
   * Désapplique l'effet Glow sur les Labels
   * @param e - MouseEvent
   */
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
  
  /**
   * Applique l'effet Glow sur les ToggleButtons
   * @param e - MouseEvent
   */
  @FXML
  protected void applyGlowEffectOnToggleButton(final MouseEvent e) {
    if ((this.nightMode).booleanValue()) {
      Object _source = e.getSource();
      ((ToggleButton) _source).setTextFill(Color.rgb(235, 221, 26));
    } else {
      Object _source_1 = e.getSource();
      ((ToggleButton) _source_1).setTextFill(Color.rgb(0, 0, 0));
    }
    Glow glowEffect = new Glow();
    glowEffect.setLevel(0.8);
    Object _source_2 = e.getSource();
    ((ToggleButton) _source_2).setEffect(glowEffect);
  }
  
  /**
   * Désapplique l'effet Glow sur les ToggleButtons
   * @param e - MouseEvent
   */
  @FXML
  protected void unapplyGlowEffectOnToggleButton(final MouseEvent e) {
    if ((this.nightMode).booleanValue()) {
      Object _source = e.getSource();
      ((ToggleButton) _source).setTextFill(Color.rgb(191, 191, 191));
    } else {
      Object _source_1 = e.getSource();
      ((ToggleButton) _source_1).setTextFill(Color.rgb(0, 0, 0));
    }
    Object _source_2 = e.getSource();
    ((ToggleButton) _source_2).setEffect(null);
  }
  
  /**
   * Applique l'effet Glow sur les Buttons
   * @param e - MouseEvent
   */
  @FXML
  protected void applyGlowEffectOnButton(final MouseEvent e) {
    if ((this.nightMode).booleanValue()) {
      Object _source = e.getSource();
      ((Button) _source).setTextFill(Color.rgb(235, 221, 26));
    } else {
      Object _source_1 = e.getSource();
      ((Button) _source_1).setTextFill(Color.rgb(0, 0, 0));
    }
    Glow glowEffect = new Glow();
    glowEffect.setLevel(0.8);
    Object _source_2 = e.getSource();
    ((Button) _source_2).setEffect(glowEffect);
  }
  
  /**
   * Désapplique l'effet Glow sur les Buttons
   * @param e - MouseEvent
   */
  @FXML
  protected void unapplyGlowEffectOnButton(final MouseEvent e) {
    if ((this.nightMode).booleanValue()) {
      Object _source = e.getSource();
      ((Button) _source).setTextFill(Color.rgb(191, 191, 191));
    } else {
      Object _source_1 = e.getSource();
      ((Button) _source_1).setTextFill(Color.rgb(0, 0, 0));
    }
    Object _source_2 = e.getSource();
    ((Button) _source_2).setEffect(null);
  }
  
  /**
   * Applique l'effet Glow sur l'incrémenteur du nombre de populations
   */
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
  
  /**
   * Applique l'effet Glow sur le décrémenteur du nombre de populations
   */
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
  
  /**
   * Désapplique l'effet Glow sur l'incrémenteur du nombre de populations
   */
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
  
  /**
   * Désapplique l'effet Glow sur le décrémenteur du nombre de populations
   */
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
  
  /**
   * Applique l'effet Glow sur le TextField du nombre de populations
   */
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
  
  /**
   * Désapplique l'effet Glow sur le TextField du nombre de populations
   */
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
  
  /**
   * Applique l'effet Glow sur l'incrémenteur du nombre de boids
   */
  @FXML
  protected void incrementBoidsQuantityGlow() {
    if ((this.nightMode).booleanValue()) {
      this.boids_quantity_increment_circle.setStroke(Color.rgb(235, 221, 26));
      this.increment_boids_quantity.setTextFill(Color.rgb(235, 221, 26));
    } else {
      this.boids_quantity_increment_circle.setStroke(Color.rgb(0, 0, 0));
      this.increment_boids_quantity.setTextFill(Color.rgb(0, 0, 0));
    }
    Glow glowEffect = new Glow();
    glowEffect.setLevel(0.8);
    this.boids_quantity_increment_circle.setEffect(glowEffect);
  }
  
  /**
   * Applique l'effet Glow sur le décrémenteur du nombre de boids
   */
  @FXML
  protected void decrementBoidsQuantityGlow() {
    if ((this.nightMode).booleanValue()) {
      this.boids_quantity_decrement_circle.setStroke(Color.rgb(235, 221, 26));
      this.decrement_boids_quantity.setTextFill(Color.rgb(235, 221, 26));
    } else {
      this.boids_quantity_decrement_circle.setStroke(Color.rgb(0, 0, 0));
      this.decrement_boids_quantity.setTextFill(Color.rgb(0, 0, 0));
    }
    Glow glowEffect = new Glow();
    glowEffect.setLevel(0.8);
    this.boids_quantity_decrement_circle.setEffect(glowEffect);
  }
  
  /**
   * Désapplique l'effet Glow sur le l'incrémenteur du nombre de boids
   */
  @FXML
  protected void incrementBoidsQuantityReset() {
    if ((this.nightMode).booleanValue()) {
      this.boids_quantity_increment_circle.setStroke(Color.rgb(191, 191, 191));
      this.increment_boids_quantity.setTextFill(Color.rgb(191, 191, 191));
    } else {
      this.boids_quantity_increment_circle.setStroke(Color.rgb(0, 0, 0, 0.3));
      this.increment_boids_quantity.setTextFill(Color.rgb(0, 0, 0));
    }
    this.boids_quantity_increment_circle.setEffect(null);
    this.increment_boids_quantity.setEffect(null);
  }
  
  /**
   * Désapplique l'effet Glow sur le le décrémenteur du nombre de boids
   */
  @FXML
  protected void decrementBoidsQuantityReset() {
    if ((this.nightMode).booleanValue()) {
      this.boids_quantity_decrement_circle.setStroke(Color.rgb(191, 191, 191));
      this.decrement_boids_quantity.setTextFill(Color.rgb(191, 191, 191));
    } else {
      this.boids_quantity_decrement_circle.setStroke(Color.rgb(0, 0, 0, 0.3));
      this.decrement_boids_quantity.setTextFill(Color.rgb(0, 0, 0));
    }
    this.boids_quantity_decrement_circle.setEffect(null);
    this.decrement_boids_quantity.setEffect(null);
  }
  
  /**
   * Applique l'effet Glow sur le TextField du nombre de boids
   */
  @FXML
  protected void boidsQuantityGlow() {
    if ((this.nightMode).booleanValue()) {
      this.boids_quantity_input.setStyle("-fx-text-fill: rgb(235, 221, 26); -fx-background-color: transparent");
    } else {
      this.boids_quantity_input.setStyle("-fx-text-fill: rgb(0, 0, 0); -fx-background-color: transparent");
    }
    this.boids_quantity_line.setStrokeWidth(3);
    Glow glowEffect = new Glow();
    glowEffect.setLevel(0.8);
    this.boids_quantity_input.setEffect(glowEffect);
  }
  
  /**
   * Désapplique l'effet Glow sur le TextField du nombre de boids
   */
  @FXML
  protected void boidsQuantityReset() {
    if ((this.nightMode).booleanValue()) {
      this.boids_quantity_input.setStyle("-fx-text-fill: rgb(191, 191, 191); -fx-background-color: transparent");
    } else {
      this.boids_quantity_input.setStyle("-fx-text-fill: rgb(0, 0, 0); -fx-background-color: transparent");
    }
    this.boids_quantity_line.setStrokeWidth(1);
    this.boids_quantity_input.setEffect(null);
  }
  
  /**
   * Active/Désactive l'UI de paramétrage global
   */
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
  
  /**
   * Montre/Cache l'UI de paramétrage global
   */
  public void toggleMenuUIVisibility() {
    boolean _isVisible = this.UI_pane.isVisible();
    if (_isVisible) {
      this.UI_pane.setVisible(false);
    } else {
      this.UI_pane.setVisible(true);
    }
  }
  
  /**
   * Montre/Cache l'UI de la simulation
   */
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
  
  /**
   * Cache l'UI de suivi d'un boid
   */
  public void hideInfosVisibility() {
    this.resetTexts();
    this.boids_infos_pane.setVisible(false);
    this.currentPolygon.setStrokeWidth(0);
    this.currentPolygon = null;
    this.currentBoid = null;
  }
  
  /**
   * Montre l'UI de suivi d'un boid
   */
  public void showInfosVisibility() {
    this.boids_infos_pane.setVisible(true);
  }
  
  /**
   * Reset des textes de suivi d'un boid
   */
  public void resetTexts() {
    this.boid_group.setText("");
    this.boid_vitesse.setText("");
    this.boid_group_vitesse.setText("");
    this.boid_masse.setText("");
    this.boid_angle.setText("");
    this.boid_distance.setText("");
    this.boid_new_vitesse.setText("");
  }
  
  /**
   * Montre/Cache l'UI des paramètres avancés
   */
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
  
  /**
   * Active l'effet de Glow sur le titre de la pane actuellement utilisée
   * @param e - MouseEvent
   */
  @FXML
  protected void paneTitleGlow(final MouseEvent e) {
    Glow glowEffect = new Glow();
    glowEffect.setLevel(0.8);
    String _substring = e.getSource().toString().substring(0, 1);
    boolean _equals = Objects.equal(_substring, "P");
    if (_equals) {
      Object _source = e.getSource();
      String pane = ((Pane) _source).getId();
      boolean _equals_1 = Objects.equal(pane, "simulation_parameters_pane");
      if (_equals_1) {
        this.settings_label.setEffect(glowEffect);
      }
    } else {
      Object _source_1 = e.getSource();
      String pane_1 = ((ScrollPane) _source_1).getId();
      boolean _equals_2 = Objects.equal(pane_1, "advanced_settings_pane");
      if (_equals_2) {
        this.advanced_settings_label.setEffect(glowEffect);
      } else {
        boolean _equals_3 = Objects.equal(pane_1, "map_selection_pane");
        if (_equals_3) {
          this.map_selection_label.setEffect(glowEffect);
        }
      }
    }
  }
  
  /**
   * Désactive l'effet de Glow sur les Panes
   */
  @FXML
  protected void paneTitleReset() {
    this.map_selection_label.setEffect(null);
    this.settings_label.setEffect(null);
    this.advanced_settings_label.setEffect(null);
  }
  
  /**
   * Active l'effet de Glow sur le titre de la sous Pane utilisée
   * @param e - MouseEvent
   */
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
  
  /**
   * Désactive l'effet de Glow sur les sous Panes
   */
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
  
  /**
   * Applique l'effet de Glow sur les previews des maps
   * @param e - MouseEvent
   */
  @FXML
  protected void previewMapGlow(final MouseEvent e) {
    int targetMap = 0;
    String _substring = e.getSource().toString().substring(0, 1);
    boolean _equals = Objects.equal(_substring, "I");
    if (_equals) {
      Object _source = e.getSource();
      Object _source_1 = e.getSource();
      int _length = ((ImageView) _source_1).getId().length();
      int _minus = (_length - 1);
      targetMap = Integer.parseInt(((ImageView) _source).getId().substring(_minus));
    } else {
      Object _source_2 = e.getSource();
      Object _source_3 = e.getSource();
      int _length_1 = ((Label) _source_3).getId().length();
      int _minus_1 = (_length_1 - 1);
      targetMap = Integer.parseInt(((Label) _source_2).getId().substring(_minus_1));
    }
    Glow glowEffect = new Glow();
    glowEffect.setLevel(1);
    switch (targetMap) {
      case 1:
        if ((this.selectedMap != 1)) {
          if ((this.nightMode).booleanValue()) {
            this.preview_map_1_border.setStroke(Color.rgb(235, 221, 26, 0.6));
            this.map_label_1.setTextFill(Color.rgb(235, 221, 26, 0.6));
          } else {
            this.preview_map_1_border.setStroke(Color.rgb(0, 0, 0, 0.35));
            this.map_label_1.setTextFill(Color.rgb(0, 0, 0, 0.6));
          }
          this.preview_map_1_border.setEffect(glowEffect);
          this.map_label_1.setEffect(glowEffect);
        }
        break;
      case 2:
        if ((this.selectedMap != 2)) {
          if ((this.nightMode).booleanValue()) {
            this.preview_map_2_border.setStroke(Color.rgb(235, 221, 26, 0.6));
            this.map_label_2.setTextFill(Color.rgb(235, 221, 26, 0.6));
          } else {
            this.preview_map_2_border.setStroke(Color.rgb(0, 0, 0, 0.6));
            this.map_label_2.setTextFill(Color.rgb(0, 0, 0, 0.6));
          }
          this.preview_map_2_border.setEffect(glowEffect);
          this.map_label_2.setEffect(glowEffect);
        }
        break;
      case 3:
        if ((this.selectedMap != 3)) {
          if ((this.nightMode).booleanValue()) {
            this.preview_map_3_border.setStroke(Color.rgb(235, 221, 26, 0.6));
            this.map_label_3.setTextFill(Color.rgb(235, 221, 26, 0.6));
          } else {
            this.preview_map_3_border.setStroke(Color.rgb(0, 0, 0, 0.6));
            this.map_label_3.setTextFill(Color.rgb(0, 0, 0, 0.6));
          }
          this.preview_map_3_border.setEffect(glowEffect);
          this.map_label_3.setEffect(glowEffect);
        }
        break;
      case 4:
        if ((this.selectedMap != 4)) {
          if ((this.nightMode).booleanValue()) {
            this.preview_map_4_border.setStroke(Color.rgb(235, 221, 26, 0.6));
            this.map_label_4.setTextFill(Color.rgb(235, 221, 26, 0.6));
          } else {
            this.preview_map_4_border.setStroke(Color.rgb(0, 0, 0, 0.6));
            this.map_label_4.setTextFill(Color.rgb(0, 0, 0, 0.6));
          }
          this.preview_map_4_border.setEffect(glowEffect);
          this.map_label_4.setEffect(glowEffect);
        }
        break;
    }
  }
  
  /**
   * Désapplique l'effet de Glow sur les previews des maps
   * @param e - MouseEvent
   */
  @FXML
  protected void previewMapReset(final MouseEvent e) {
    if ((e != null)) {
      int targetMap = 0;
      String _substring = e.getSource().toString().substring(0, 1);
      boolean _equals = Objects.equal(_substring, "I");
      if (_equals) {
        Object _source = e.getSource();
        Object _source_1 = e.getSource();
        int _length = ((ImageView) _source_1).getId().length();
        int _minus = (_length - 1);
        targetMap = Integer.parseInt(((ImageView) _source).getId().substring(_minus));
      } else {
        Object _source_2 = e.getSource();
        Object _source_3 = e.getSource();
        int _length_1 = ((Label) _source_3).getId().length();
        int _minus_1 = (_length_1 - 1);
        targetMap = Integer.parseInt(((Label) _source_2).getId().substring(_minus_1));
      }
      switch (targetMap) {
        case 1:
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
          break;
        case 2:
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
          break;
        case 3:
          if ((this.selectedMap != 3)) {
            if ((this.nightMode).booleanValue()) {
              this.preview_map_3_border.setStroke(Color.rgb(191, 191, 191));
              this.map_label_3.setTextFill(Color.rgb(191, 191, 191));
            } else {
              this.preview_map_3_border.setStroke(Color.rgb(0, 0, 0, 0.6));
              this.map_label_3.setTextFill(Color.rgb(0, 0, 0, 0.6));
            }
            this.preview_map_3_border.setEffect(null);
            this.map_label_3.setEffect(null);
          }
          break;
        case 4:
          if ((this.selectedMap != 4)) {
            if ((this.nightMode).booleanValue()) {
              this.preview_map_4_border.setStroke(Color.rgb(191, 191, 191));
              this.map_label_4.setTextFill(Color.rgb(191, 191, 191));
            } else {
              this.preview_map_4_border.setStroke(Color.rgb(0, 0, 0, 0.6));
              this.map_label_4.setTextFill(Color.rgb(0, 0, 0, 0.6));
            }
            this.preview_map_4_border.setEffect(null);
            this.map_label_4.setEffect(null);
          }
          break;
      }
    } else {
      if ((this.nightMode).booleanValue()) {
        this.preview_map_1_border.setStroke(Color.rgb(191, 191, 191));
        this.map_label_1.setTextFill(Color.rgb(191, 191, 191));
      } else {
        this.preview_map_1_border.setStroke(Color.rgb(0, 0, 0, 0.6));
        this.map_label_1.setTextFill(Color.rgb(0, 0, 0, 0.6));
      }
      this.preview_map_1_border.setEffect(null);
      this.map_label_1.setEffect(null);
      if ((this.nightMode).booleanValue()) {
        this.preview_map_2_border.setStroke(Color.rgb(191, 191, 191));
        this.map_label_2.setTextFill(Color.rgb(191, 191, 191));
      } else {
        this.preview_map_2_border.setStroke(Color.rgb(0, 0, 0, 0.6));
        this.map_label_2.setTextFill(Color.rgb(0, 0, 0, 0.6));
      }
      this.preview_map_2_border.setEffect(null);
      this.map_label_2.setEffect(null);
      if ((this.nightMode).booleanValue()) {
        this.preview_map_3_border.setStroke(Color.rgb(191, 191, 191));
        this.map_label_3.setTextFill(Color.rgb(191, 191, 191));
      } else {
        this.preview_map_3_border.setStroke(Color.rgb(0, 0, 0, 0.6));
        this.map_label_3.setTextFill(Color.rgb(0, 0, 0, 0.6));
      }
      this.preview_map_3_border.setEffect(null);
      this.map_label_3.setEffect(null);
      if ((this.nightMode).booleanValue()) {
        this.preview_map_4_border.setStroke(Color.rgb(191, 191, 191));
        this.map_label_4.setTextFill(Color.rgb(191, 191, 191));
      } else {
        this.preview_map_4_border.setStroke(Color.rgb(0, 0, 0, 0.6));
        this.map_label_4.setTextFill(Color.rgb(0, 0, 0, 0.6));
      }
      this.preview_map_4_border.setEffect(null);
      this.map_label_4.setEffect(null);
    }
  }
  
  /**
   * Sélectionne une map
   * @param e - MouseEvent
   */
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
      case 3:
        if ((this.nightMode).booleanValue()) {
          this.preview_map_3_border.setStroke(Color.rgb(235, 221, 26));
          this.map_label_3.setTextFill(Color.rgb(235, 221, 26));
        } else {
          this.preview_map_3_border.setStroke(Color.rgb(0, 0, 0));
          this.map_label_3.setTextFill(Color.rgb(0, 0, 0));
        }
        Glow glowEffect_2 = new Glow();
        glowEffect_2.setLevel(0.8);
        this.preview_map_3_border.setEffect(glowEffect_2);
        this.map_label_3.setEffect(glowEffect_2);
        this.tick_map_3.setVisible(true);
        break;
    }
  }
  
  /**
   * Reset la sélection
   */
  protected void resetMaps() {
    this.previewMapReset(null);
    this.tick_map_1.setVisible(false);
    this.tick_map_2.setVisible(false);
    this.tick_map_3.setVisible(false);
    this.tick_map_4.setVisible(false);
  }
  
  /**
   * Dévoile les panes correspondant au nombre de populations saisi
   */
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
  
  /**
   * Reset tout le paramétrage utilisateur
   */
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
  
  /**
   * Listeners sur les masses
   * @param e - MouseEvent
   */
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
  
  /**
   * Listeners sur les vitesses
   * @param e - MouseEvent
   */
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
  
  /**
   * Listeners sur les angles
   * @param e - MouseEvent
   */
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
  
  /**
   * Listeners sur les distances
   * @param e - MouseEvent
   */
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
  
  /**
   * Applique rapidement les valeurs maximales et minimales pour les masses
   * @param e - MouseEvent
   */
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
  
  /**
   * Applique rapidement les valeurs maximales et minimales pour les vitesses
   * @param e - MouseEvent
   */
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
  
  /**
   * Applique rapidement les valeurs maximales et minimales pour les angles
   * @param e - MouseEvent
   */
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
  
  /**
   * Applique rapidement les valeurs maximales et minimales pour les distances
   * @param e - MouseEvent
   */
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
  
  /**
   * --- --- --- MODE JOUR NUIT --- --- ---
   */
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
      this.decrement_boids_quantity.setTextFill(normalTextColor);
      this.increment_boids_quantity.setTextFill(normalTextColor);
      this.boids_quantity_decrement_circle.setStroke(normalTextColor);
      this.boids_quantity_increment_circle.setStroke(normalTextColor);
      this.boids_quantity_line.setStroke(normalTextColor);
      this.boids_quantity_input.setStyle("-fx-text-fill: rgb(0, 0, 0); -fx-background-color: transparent");
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
      this.reset_group_values.setTextFill(normalTextColor);
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
      if ((this.selectedMap != 3)) {
        this.preview_map_3_border.setStroke(Color.rgb(0, 0, 0, 0.6));
        this.map_label_3.setTextFill(normalTextColor);
      } else {
        if ((this.selectedMap == 3)) {
          this.preview_map_3_border.setStroke(Color.rgb(0, 0, 0));
          this.map_label_3.setTextFill(Color.rgb(0, 0, 0));
        }
      }
      if ((this.selectedMap != 4)) {
        this.preview_map_4_border.setStroke(Color.rgb(0, 0, 0, 0.6));
        this.map_label_4.setTextFill(normalTextColor);
      } else {
        if ((this.selectedMap == 3)) {
          this.preview_map_4_border.setStroke(Color.rgb(0, 0, 0));
          this.map_label_4.setTextFill(Color.rgb(0, 0, 0));
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
      this.decrement_boids_quantity.setTextFill(nightTextColor);
      this.increment_boids_quantity.setTextFill(nightTextColor);
      this.boids_quantity_decrement_circle.setStroke(nightTextColor);
      this.boids_quantity_increment_circle.setStroke(nightTextColor);
      this.boids_quantity_line.setStroke(nightTextColor);
      this.boids_quantity_input.setStyle("-fx-text-fill: rgb(191, 191, 191); -fx-background-color: transparent");
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
      this.reset_group_values.setTextFill(nightTextColor);
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
      if ((this.selectedMap != 3)) {
        this.preview_map_3_border.setStroke(nightTextColor);
        this.map_label_3.setTextFill(nightTextColor);
      } else {
        if ((this.selectedMap == 3)) {
          this.preview_map_3_border.setStroke(Color.rgb(235, 221, 26));
          this.map_label_3.setTextFill(Color.rgb(235, 221, 26));
        }
      }
      if ((this.selectedMap != 4)) {
        this.preview_map_4_border.setStroke(nightTextColor);
        this.map_label_4.setTextFill(nightTextColor);
      } else {
        if ((this.selectedMap == 3)) {
          this.preview_map_4_border.setStroke(Color.rgb(235, 221, 26));
          this.map_label_4.setTextFill(Color.rgb(235, 221, 26));
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
    if (Double.doubleToLongBits(other.orgSceneX) != Double.doubleToLongBits(this.orgSceneX))
      return false;
    if (Double.doubleToLongBits(other.orgSceneY) != Double.doubleToLongBits(this.orgSceneY))
      return false;
    if (Double.doubleToLongBits(other.orgTranslateX) != Double.doubleToLongBits(this.orgTranslateX))
      return false;
    if (Double.doubleToLongBits(other.orgTranslateY) != Double.doubleToLongBits(this.orgTranslateY))
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
    result = prime * result + this.selectedMap;
    result = prime * result + (this.nightMode ? 1231 : 1237);
    result = prime * result + (this.togglePerception ? 1231 : 1237);
    result = prime * result + java.util.Objects.hashCode(this.currentBoid);
    result = prime * result + (int) (Double.doubleToLongBits(this.orgSceneX) ^ (Double.doubleToLongBits(this.orgSceneX) >>> 32));
    result = prime * result + (int) (Double.doubleToLongBits(this.orgSceneY) ^ (Double.doubleToLongBits(this.orgSceneY) >>> 32));
    result = prime * result + (int) (Double.doubleToLongBits(this.orgTranslateX) ^ (Double.doubleToLongBits(this.orgTranslateX) >>> 32));
    result = prime * result + (int) (Double.doubleToLongBits(this.orgTranslateY) ^ (Double.doubleToLongBits(this.orgTranslateY) >>> 32));
    return result;
  }
  
  @SyntheticMember
  public BoidsFxViewerController() {
    super();
  }
}
